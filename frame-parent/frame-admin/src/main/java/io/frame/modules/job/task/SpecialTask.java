package io.frame.modules.job.task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

import io.frame.common.enums.Constant;
import io.frame.common.enums.Constant.ChangeType;
import io.frame.common.enums.Constant.Status;
import io.frame.common.exception.RRException;
import io.frame.common.utils.DateUtils;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.Config;
import io.frame.dao.entity.Recommend;
import io.frame.dao.entity.RecommendExample;
import io.frame.dao.entity.Report;
import io.frame.dao.entity.ReportExample;
import io.frame.dao.entity.SpecialRecord;
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
 * 特别贡献奖定时任务 每天凌晨0点跑
 * 
 * 
 * @author Fury
 *
 */
@Component("specialTask")
public class SpecialTask {
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
		logger.info("特别贡献奖定时任务--------------------------启动");
		Date currentDate = new Date();
		try {

			// 获取所有用户
			List<User> userList = this.getUserList();
			if (CollectionUtils.isEmpty(userList)) {
				logger.info("无用户数据...");
				return;
			}
			// 获取特别贡献奖奖励规则
			List<Welfare> ruleList = this.getRuleList();
			if (CollectionUtils.isEmpty(ruleList)) {
				logger.info("无匹配规则数据...");
				return;
			}
			// 获取昨日报表中的订单总业绩金额
			BigDecimal totalsMoney = this.getOrderTotasMoney(currentDate);

			// 最终入库数据
			List<SpecialRecord> recordList = Lists.newArrayList();
			for (User user : userList) {
				BigDecimal calcMoney = totalsMoney; // 每次重新赋值
				// 获取当前用户的累计直推人数,
				Integer recommendNum = this.getTotalsCumulativeNum(user.getUserId());
				if (recommendNum == 0) {
					continue;// 不达标下一个
				}
				// 团队总人数,团队业绩
				Recommend recommend = this.getTeamNumAndAchievement(user.getUserId(), user.getGroupUserIds());
				if (recommend == null || recommend.getRecommendNumber() == 0
						|| recommend.getTeamAchievement().compareTo(BigDecimal.ZERO) == 0) {
					continue;// 不达标下一个
				}

				// 团队总人数
				Integer teamNum = recommend.getRecommendNumber();
				// 团队总业绩
				BigDecimal teamAchievement = recommend.getTeamAchievement();
				Welfare ruleWelfare = this.getRuleWelfare(ruleList, recommendNum, teamNum, teamAchievement);
				if (ruleWelfare == null) {
					// 无匹配规则
					continue;
				}

				SpecialRecord record = new SpecialRecord();
				record.setUserId(user.getUserId());
				record.setUserName(user.getUserName());
				record.setUserMobile(user.getUserMobile());
				record.setUserLevel(user.getUserLevel());
				record.setParentId(user.getParentId());
				record.setGroupUserIds(user.getGroupUserIds());
				record.setCreateTime(currentDate);
				record.setGenerateTime(DateUtils.addDateDays(currentDate, -1));// 生成时间实际是生成昨天的数据
				record.setRecommendNum(recommendNum);
				record.setTeamNum(recommendNum);
				record.setTeamAchievement(teamAchievement);
				// 如果奖金池不为空,则使用奖金池中的金额计算
				if (ruleWelfare.getBonusPool() != null) {
					calcMoney = ruleWelfare.getBonusPool();
				}

				// 获取昨日达到要求总人数
				Integer totalsPeopleNum = this.getTotalsPeopleNum(ruleWelfare, currentDate);

				// 奖金池或订单总业绩*百分比/达标人数
				record.setMoney(calcMoney.multiply(ruleWelfare.getPercent()).divide(new BigDecimal(totalsPeopleNum), 4,
						RoundingMode.HALF_UP));

				recordList.add(record);
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
					walletChange.setRemark(ChangeType.TEAM_LEADERSHIP_AWARD_KEY.getName());
					walletService.addWallet(walletChange, ChangeType.TEAM_LEADERSHIP_AWARD_KEY);

				}

			}

		} catch (Exception e) {
			logger.error("特别贡献奖定时任务--------------------------异常");
			throw new RRException("特别贡献奖定时任务异常", e);
		}

		logger.info("特别贡献奖定时任务--------------------------结束");
	}

	/**
	 * 获取团队总人数和团队总业绩
	 * 
	 * @param userId
	 * @param groupUserIds
	 * @return
	 */
	private Recommend getTeamNumAndAchievement(Long userId, String groupUserIds) {
		RecommendExample example = new RecommendExample();
		RecommendExample.Criteria cr = example.createCriteria();
		cr.andParentIdNotEqualTo(userId);
		cr.andGroupUserIdsLike(groupUserIds + "%");
		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.countField(Recommend.FD_RECOMMENDNUMBER));
		showField.add(SqlTools.sumField(Recommend.FD_TEAMACHIEVEMENT));
		return recommendMapper.selectOneByExampleShowField(showField, example);
	}

	/**
	 * 获取累计的直推人数
	 * 
	 * @param userId
	 * @return
	 */
	private Integer getTotalsCumulativeNum(Long userId) {
		RecommendExample example = new RecommendExample();
		example.or().andParentIdEqualTo(userId);
		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.sumField(Recommend.FD_RECOMMENDNUMBER));
		Recommend recommend = recommendMapper.selectOneByExampleShowField(showField, example);
		return recommend == null ? 0 : recommend.getRecommendNumber();
	}

	/**
	 * 获取昨日满足推荐的总人数
	 * 
	 * @param ruleWelfare
	 * @param currentDate
	 * @return
	 */
	private Integer getTotalsPeopleNum(Welfare ruleWelfare, Date currentDate) {
		RecommendExample example = new RecommendExample();
		String[] rule = ruleWelfare.getWelfareValue().split(",");
		RecommendExample.Criteria cr = example.createCriteria();
		cr.andCreateTimeEqualTo(DateUtils.addDateDays(currentDate, -1));
		cr.andRecommendNumberGreaterThanOrEqualTo(Integer.parseInt(rule[0]));
		cr.andTeamAchievementGreaterThanOrEqualTo(new BigDecimal(rule[2]));
		return recommendMapper.countByExample(example);
	}

	/**
	 * 获取匹配规则
	 * 
	 * @param ruleList
	 * @param recommendNum直推总人数
	 * @param teamNum团队总人数
	 * @param teamAchievement团队总业绩
	 * @return
	 */
	private Welfare getRuleWelfare(List<Welfare> ruleList, Integer recommendNum, Integer teamNum,
			BigDecimal teamAchievement) {
		Welfare ruleWelfare = null;
		// 根据规则 计算返利
		for (Welfare welfare : ruleList) {
			// 解析出规则
			String[] rule = welfare.getWelfareValue().split(",");
			if (recommendNum >= Integer.parseInt(rule[0]) && teamNum >= Integer.parseInt(rule[1])
					&& teamAchievement.compareTo(new BigDecimal(rule[2])) >= 0) {
				ruleWelfare = welfare;
			}
		}

		return ruleWelfare;
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
	 * 获取用户
	 * 
	 * @return
	 */
	private List<User> getUserList() {
		UserExample example = new UserExample();
		example.setOrderByClause(SqlTools.orderByAscField(User.FD_USERLEVEL));
		List<String> showField = Lists.newArrayList();
		showField.add(User.FD_USERID);
		showField.add(User.FD_USERNAME);
		showField.add(User.FD_USERMOBILE);
		showField.add(User.FD_USERLEVEL);
		showField.add(User.FD_GROUPUSERIDS);
		showField.add(User.FD_PARENTID);
		return userMapper.selectByExampleShowField(showField, example);
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

	/**
	 * 获取昨日推荐表中的信息
	 * 
	 * @return
	 */
	private List<Recommend> getRecommendList(Date currentDate) {
		RecommendExample example = new RecommendExample();
		RecommendExample.Criteria cr = example.createCriteria();
		cr.andCreateTimeEqualTo(DateUtils.addDateDays(currentDate, -1));
		List<String> showField = Lists.newArrayList();
		showField.add(Recommend.FD_USERID);
		showField.add(Recommend.FD_RECOMMENDNUMBER);
		showField.add(Recommend.FD_TEAMACHIEVEMENT);
		return recommendMapper.selectByExampleShowField(showField, example);
	}

}
