package io.frame.modules.job.task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.frame.common.enums.Constant;
import io.frame.common.enums.Constant.ChangeType;
import io.frame.common.enums.Constant.Status;
import io.frame.common.exception.RRException;
import io.frame.common.utils.DateUtils;
import io.frame.common.utils.SqlTools;
import io.frame.dao.custom.entity.CustomeRecommendVo;
import io.frame.dao.entity.Config;
import io.frame.dao.entity.Recommend;
import io.frame.dao.entity.RecommendExample;
import io.frame.dao.entity.Report;
import io.frame.dao.entity.ReportExample;
import io.frame.dao.entity.TeamleaderRecord;
import io.frame.dao.entity.User;
import io.frame.dao.entity.UserExample;
import io.frame.dao.entity.WalletChange;
import io.frame.dao.entity.Welfare;
import io.frame.dao.entity.WelfareExample;
import io.frame.dao.mapper.RecommendMapper;
import io.frame.dao.mapper.ReportMapper;
import io.frame.dao.mapper.TeamleaderRecordMapper;
import io.frame.dao.mapper.UserMapper;
import io.frame.dao.mapper.WelfareMapper;
import io.frame.modules.happytrip.service.WalletService;
import io.frame.modules.sys.service.SysConfigService;

/**
 * 团队领导奖定时任务 每天凌晨0点跑
 * 
 * 
 * @author Fury
 *
 */
@Component("teamLeaderTask")
public class TeamLeaderTask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	SysConfigService sysConfigService;

	@Autowired
	UserMapper userMapper;

	@Autowired
	ReportMapper reportMapper;

	@Autowired
	WelfareMapper welfareMapper;

	@Autowired
	RecommendMapper recommendMapper;

	@Autowired
	TeamleaderRecordMapper teamleaderRecordMapper;

	@Autowired
	WalletService walletService;

	@Transactional(readOnly = false)
	public void run() {
		logger.info("团队领导奖定时任务--------------------------启动");
		Date currentDate = new Date();
		try {

			// 获取昨日注册的新用户
			List<User> userList = this.getYesterdayRegisterUserList(currentDate);
			if (CollectionUtils.isEmpty(userList)) {
				logger.info("昨日无注册用户...");
				return;
			}

			// 保存父级ID,与直推有效会员人数
			Map<Long, Integer> map = Maps.newHashMap();
			for (User user : userList) {
				// 查看当前用户昨日是否有消费
				if (!this.getCustomReport(user.getUserId(), currentDate)) {
					continue;
				}
				if (!map.containsKey(user.getParentId())) {
					map.put(user.getParentId(), 1);
				} else {
					Integer num = map.get(user.getParentId());
					map.put(user.getParentId(), num + 1);
				}
			}

			if (CollectionUtils.isEmpty(map)) {
				return;
			}

			// 获取团队奖励规则
			List<Welfare> ruleList = this.getRuleList();
			if (CollectionUtils.isEmpty(ruleList)) {
				logger.info("无匹配规则...");
				return;
			}

			Map<String, Integer> rulesMap = Maps.newHashMap();
			// 有效数据集合
			List<CustomeRecommendVo> recommendList = Lists.newArrayList();
			// 统计每个规则各占多少人
			for (Entry<Long, Integer> m : map.entrySet()) {
				Long userId = m.getKey();
				Integer recommendNum = m.getValue();
				// 获取当前用户的昨日团队业绩
				User user = userMapper.selectByPrimaryKey(userId);
				BigDecimal teamAchievement = this.getYesTerdayTeamAchievement(user.getGroupUserIds(), currentDate);
				Welfare welfareObj = null; // 最终匹配到的条件
				for (Welfare welfare : ruleList) {
					String[] rules = welfare.getWelfareValue().split(",");
					Integer nums = Integer.parseInt(rules[0]);
					BigDecimal moeny = new BigDecimal(rules[1]);
					if (recommendNum >= nums && teamAchievement.compareTo(moeny) >= 0) {
						welfareObj = welfare;// 取得最后一个匹配的结果
					}
				}

				if (welfareObj == null) {
					continue;// 没匹配到, 下一个.
				}

				CustomeRecommendVo record = new CustomeRecommendVo();
				record.setUserId(userId);
				record.setRecommendNum(recommendNum);
				record.setTeamAchievement(teamAchievement);
				record.setPercent(welfareObj.getPercent());
				record.setWelfareKey(welfareObj.getWelfareKey());
				record.setWelfareValue(welfareObj.getWelfareValue());
				record.setBonusPool(welfareObj.getBonusPool());
				recommendList.add(record);
				// 保存当前各项规则达标的总人数
				String welfareValue = welfareObj.getWelfareValue();
				if (!rulesMap.containsKey(welfareValue)) {
					rulesMap.put(welfareValue, 1);
				} else {
					Integer countNum = rulesMap.get(welfareValue);
					rulesMap.put(welfareValue, countNum + 1);
				}

			}

			if (CollectionUtils.isEmpty(recommendList)) {
				logger.info("无满足条件数据...");
				return;
			}

			// 获取昨日报表中的订单总业绩金额
			BigDecimal totalsMoney = this.getOrderTotasMoney(currentDate);
			// 最终入库数据
			List<TeamleaderRecord> recordList = Lists.newArrayList();
			for (CustomeRecommendVo recommend : recommendList) {
				BigDecimal calcMoney = totalsMoney; // 每次重新赋值
				User user = userMapper.selectByPrimaryKey(recommend.getUserId());
				TeamleaderRecord record = new TeamleaderRecord();
				record.setUserId(user.getUserId());
				record.setUserName(user.getUserName());
				record.setUserMobile(user.getUserMobile());
				record.setUserLevel(user.getUserLevel());
				record.setParentId(user.getParentId());
				record.setGroupUserIds(user.getGroupUserIds());
				record.setCreateTime(currentDate);
				record.setGenerateTime(DateUtils.addDateDays(currentDate, -1));// 生成时间实际是生成昨天的数据
				record.setRecommendNum(recommend.getRecommendNum());
				record.setAchievement(recommend.getTeamAchievement());
				// 如果奖金池不为空,则使用奖金池中的金额计算
				if (recommend.getBonusPool() != null && recommend.getBonusPool().compareTo(BigDecimal.ZERO) == 1) {
					calcMoney = recommend.getBonusPool();
				}

				// 获取昨日达到要求总人数
				Integer totalsPeopleNum = rulesMap.get(recommend.getWelfareValue());
				record.setTotalsNum(totalsPeopleNum);
				// 奖金池或订单总业绩*百分比/达标人数
				record.setMoney(calcMoney.multiply(recommend.getPercent()).divide(new BigDecimal(totalsPeopleNum), 4,
						RoundingMode.HALF_UP));

				recordList.add(record);
			}

			if (CollectionUtils.isEmpty(recordList)) {
				logger.info("无满足条件数据...");
				return;
			}

			// 判断是否开启自动派发开关
			boolean flag = this.getSwitch();
			Date date = new Date();
			for (TeamleaderRecord record : recordList) {
				if (record.getMoney().compareTo(BigDecimal.ZERO) <= 0) {
					continue;
				}
				// 入库
				if (flag) {
					logger.info("开关没开启,后台手动派发...");
					record.setIsGrant(Status.ZERO.getValue());
				} else {
					record.setIsGrant(Status.ONE.getValue());
					record.setGrantTime(date);
					record.setUpdateTime(date);
					record.setUpdateUser("系统");
				}
				teamleaderRecordMapper.insertSelective(record);

				if (!flag) {// 开启自动派发，需要加钱，账变
					// 钱包加钱 帐变 刷新报表
					WalletChange walletChange = new WalletChange();
					walletChange.setUserId(record.getUserId());
					walletChange.setOperatorMoney(record.getMoney());
					walletChange.setRelationId(record.getId());
					walletChange.setRemark(ChangeType.TEAM_LEADERSHIP_AWARD_KEY.getName());
					walletService.addWallet(walletChange, ChangeType.TEAM_LEADERSHIP_AWARD_KEY);

				}

			}

		} catch (Exception e) {
			logger.error("团队领导奖定时任务--------------------------异常");
			throw new RRException("团队领导奖定时任务异常", e);
		}

		logger.info("团队领导奖定时任务--------------------------结束");
	}

	/**
	 * 获取用户昨日团队业绩
	 * 
	 * @param groupUserIds
	 * @param currentDate
	 * @return
	 */
	private BigDecimal getYesTerdayTeamAchievement(String groupUserIds, Date currentDate) {
		RecommendExample example = new RecommendExample();
		RecommendExample.Criteria cr = example.createCriteria();
		cr.andGroupUserIdsLike(groupUserIds + "%");
		if (currentDate != null) {
			cr.andCreateTimeEqualTo(currentDate);
		}
		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.sumField(Recommend.FD_TEAMACHIEVEMENT));
		Recommend recommend = recommendMapper.selectOneByExampleShowField(showField, example);
		return recommend == null ? BigDecimal.ZERO : recommend.getTeamAchievement();
	}

	/**
	 * 判断昨日注册用户昨日是否有消费
	 * 
	 * @param userId
	 * @param currentDate
	 * @return
	 */
	private boolean getCustomReport(Long userId, Date currentDate) {
		ReportExample example = new ReportExample();
		example.or().andUserIdEqualTo(userId).andCreateTimeEqualTo(DateUtils.addDateDays(currentDate, -1));
		List<String> showField = Lists.newArrayList();
		showField.add(Report.FD_ORDERMONEY);
		Report report = reportMapper.selectOneByExampleShowField(showField, example);
		if (report == null || report.getOrderMoney() == null
				|| report.getOrderMoney().compareTo(BigDecimal.ZERO) == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 获取昨日注册用户数
	 * 
	 * @param currentDate
	 * @return
	 */
	private List<User> getYesterdayRegisterUserList(Date currentDate) {
		UserExample example = new UserExample();
		UserExample.Criteria cr = example.createCriteria();
		cr.andCreateTimeGreaterThanOrEqualTo(DateUtils.addDateDays(DateUtils.parse(currentDate, "yyyy-MM-dd"), -1)); // >=昨日0点
		cr.andCreateTimeLessThan(DateUtils.parse(currentDate, "yyyy-MM-dd")); // 小于今日0点
		List<String> showField = Lists.newArrayList();
		showField.add(User.FD_USERID);
		showField.add(User.FD_USERNAME);
		showField.add(User.FD_PARENTID);
		showField.add(User.FD_GROUPUSERIDS);
		showField.add(User.FD_USERLEVEL);
		showField.add(User.FD_USERMOBILE);
		return userMapper.selectByExampleShowField(showField, example);
	}

	/**
	 * 获取昨日报表中的订单总业绩
	 * 
	 * @return
	 */
	private BigDecimal getOrderTotasMoney(Date currentDate) {
		ReportExample example = new ReportExample();
		ReportExample.Criteria cr = example.createCriteria();
		cr.andCreateTimeEqualTo(DateUtils.addDateDays(currentDate, -1)); // 取昨天
		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.sumField(Report.FD_ORDERMONEY));
		Report report = reportMapper.selectOneByExampleShowField(showField, example);
		return report == null ? BigDecimal.ZERO : report.getOrderMoney();
	}

	/**
	 * 获取奖励派发规则
	 * 
	 * @return
	 */
	private List<Welfare> getRuleList() {
		WelfareExample example = new WelfareExample();
		example.or().andStatusEqualTo(Status.ONE.getValue())
				.andWelfareKeyEqualTo(Constant.WelfareKey.TEAM_LEADERSHIP_AWARD_KEY.getValue());
		example.setOrderByClause(SqlTools.orderByAscField(Welfare.FD_CREATETIME));
		List<String> showField = Lists.newArrayList();
		showField.add(Welfare.FD_WELFAREKEY);
		showField.add(Welfare.FD_WELFARENAME);
		showField.add(Welfare.FD_WELFAREVALUE);
		showField.add(Welfare.FD_BONUSPOOL);
		showField.add(Welfare.FD_PERCENT);
		return welfareMapper.selectByExampleShowField(showField, example);
	}

	/**
	 * 获取配置派发开关
	 * 
	 * @return
	 */
	private boolean getSwitch() {
		Config config = new Config();
		config.setConfigKey(Constant.WelfareSwitch.GLOBAL_BONUS_KEY.getValue());
		config.setConfigStatus(Constant.Status.ONE.getValue());
		Config newConfig = sysConfigService.getInfo(config);
		if (newConfig == null || "0".equals(newConfig.getConfigVal())) {
			return true;
		}
		return false;
	}

}
