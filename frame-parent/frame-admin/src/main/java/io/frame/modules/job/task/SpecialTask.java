package io.frame.modules.job.task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
import io.frame.dao.custom.entity.CustomeRecommendVo;
import io.frame.dao.custom.mapper.CustomeRecommendMapper;
import io.frame.dao.entity.Config;
import io.frame.dao.entity.Report;
import io.frame.dao.entity.ReportExample;
import io.frame.dao.entity.SpecialRecord;
import io.frame.dao.entity.User;
import io.frame.dao.entity.UserExample;
import io.frame.dao.entity.WalletChange;
import io.frame.dao.entity.Welfare;
import io.frame.dao.entity.WelfareExample;
import io.frame.dao.mapper.RecommendMapper;
import io.frame.dao.mapper.ReportMapper;
import io.frame.dao.mapper.SpecialRecordMapper;
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
	SpecialRecordMapper specialRecordMapper;

	@Autowired
	WalletService walletService;

	@Autowired
	CustomeRecommendMapper customeRecommendMapper;

	@Transactional(readOnly = false)
	public void run() {
		logger.info("特别贡献奖定时任务--------------------------启动");
		Date currentDate = new Date();
		try {

			// 获取特别贡献奖奖励规则
			List<Welfare> ruleList = this.getRuleList();
			if (CollectionUtils.isEmpty(ruleList)) {
				logger.info("无匹配规则数据...");
				return;
			}

			// 获取所有用户集合
			List<User> userList = this.getUserList();
			if (CollectionUtils.isEmpty(userList)) {
				logger.info("无用户数据...");
				return;
			}

			// 获取昨日报表中的订单总业绩金额
			BigDecimal totalsMoney = this.getOrderTotasMoney(currentDate);
			// 最终入库数据
			List<SpecialRecord> recordList = Lists.newArrayList();
			for (Welfare welfare : ruleList) {
				// 获取达到此规则的用户
				List<CustomeRecommendVo> recomendList = this.getSatisfyUser(welfare.getWelfareValue());
				if (CollectionUtils.isEmpty(recomendList)) {
					logger.info("无此规则会员数据...[{}]", welfare.toString());
					continue;// 下一个规则
				}

				BigDecimal calcMoney = totalsMoney; // 每次重新赋值
				for (CustomeRecommendVo recommend : recomendList) {
					User user = userMapper.selectByPrimaryKey(recommend.getUserId());
					SpecialRecord record = new SpecialRecord();
					record.setUserId(user.getUserId());
					record.setUserName(user.getUserName());
					record.setUserMobile(user.getUserMobile());
					record.setUserLevel(user.getUserLevel());
					record.setParentId(user.getParentId());
					record.setGroupUserIds(user.getGroupUserIds());
					record.setCreateTime(currentDate);
					record.setGenerateTime(DateUtils.addDateDays(currentDate, -1));// 生成时间实际是生成昨天的数据
					record.setRecommendNum(recommend.getRecommendNum());
					record.setTeamNum(recommend.getTeamNum());
					record.setTeamAchievement(recommend.getTeamAchievement());
					// 如果奖金池不为空,则使用奖金池中的金额计算
					if (welfare.getBonusPool() != null) {
						calcMoney = welfare.getBonusPool();
					}

					// 获取达标的总人数
					Integer totalsPeopleNum = recomendList.size();
					// 奖金池或订单总业绩*百分比/达标人数
					record.setMoney(calcMoney.multiply(welfare.getPercent()).divide(new BigDecimal(totalsPeopleNum), 4,
							RoundingMode.HALF_UP));

					recordList.add(record);
				}

			}

			if (CollectionUtils.isEmpty(recordList)) {
				logger.info("无满足条件数据...");
				return;
			}

			// 判断是否开启自动派发开关
			boolean flag = this.getSwitch();
			Date date = new Date();
			for (SpecialRecord record : recordList) {
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
				specialRecordMapper.insertSelective(record);

				if (!flag) {// 开启自动派发，需要加钱，账变
					// 钱包加钱 帐变 刷新报表
					WalletChange walletChange = new WalletChange();
					walletChange.setUserId(record.getUserId());
					walletChange.setOperatorMoney(record.getMoney());
					walletChange.setRemark(ChangeType.SPECIAL_CONTRIBUTION_AWARD_KEY.getName());
					walletService.addWallet(walletChange, ChangeType.SPECIAL_CONTRIBUTION_AWARD_KEY);

				}

			}

		} catch (

		Exception e) {
			logger.error("特别贡献奖定时任务--------------------------异常");
			throw new RRException("特别贡献奖定时任务异常", e);
		}

		logger.info("特别贡献奖定时任务--------------------------结束");
	}

	/**
	 * 获取满足条件的会员
	 * 
	 * @param recommendNum
	 * @param teamNum
	 * @param teamMoney
	 * @return
	 */
	private List<CustomeRecommendVo> getSatisfyUser(String rules) {
		String[] rule = rules.split(",");
		// 直推人数要求
		String[] recommendNumStr = rule[0].split("\\|");
		// 累计直推人数最小要求
		Integer minRecommendNum = Integer.parseInt(recommendNumStr[0]);
		// 累计直推人数最大要求
		Integer maxRecommendNum = Integer.parseInt(recommendNumStr[1]);
		List<CustomeRecommendVo> list = customeRecommendMapper.customSelectRecommendNumList(minRecommendNum,
				maxRecommendNum);
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<>();
		}

		// 团队人数要求
		String[] teamNumStr = rule[1].split("\\|");
		// 团队人数最小要求
		Integer minTeamNumNum = Integer.parseInt(teamNumStr[0]);
		// 团队人数最大要求
		Integer maxTeamNumNum = Integer.parseInt(teamNumStr[1]);

		// 团队业绩要求
		String[] teamAchievementStr = rule[2].split("\\|");
		// 团队业绩最小要求
		BigDecimal minTeamAchievementNum = new BigDecimal(teamAchievementStr[0]);
		// 团队业绩最大要求
		BigDecimal maxTeamAchievementNum = new BigDecimal(teamAchievementStr[1]);

		List<CustomeRecommendVo> resultList = Lists.newArrayList();
		// 获取团队人数要求,团队业绩要求
		for (CustomeRecommendVo customeRecommendVo : list) {
			CustomeRecommendVo vo = customeRecommendMapper.customSelectTeamInfo(customeRecommendVo.getGroupUserIds());
			if (vo == null || vo.getTeamNum() == null || vo.getTeamAchievement() == null) {
				continue;
			}

			// 判断团队人数是否达标 ,团队业绩是否达标
			if ((vo.getTeamNum() >= minTeamNumNum && vo.getTeamNum() < maxTeamNumNum)
					&& (vo.getTeamAchievement().compareTo(minTeamAchievementNum) >= 0
							&& vo.getTeamAchievement().compareTo(maxTeamAchievementNum) < 0)) {
				CustomeRecommendVo newVo = new CustomeRecommendVo();
				newVo.setUserId(customeRecommendVo.getUserId());
				newVo.setRecommendNum(customeRecommendVo.getRecommendNum());
				newVo.setTeamNum(vo.getTeamNum());
				newVo.setTeamAchievement(vo.getTeamAchievement());
				resultList.add(newVo);
			}

		}

		return resultList;
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
				.andWelfareKeyEqualTo(Constant.WelfareKey.SPECIAL_CONTRIBUTION_AWARD_KEY.getValue());
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

}
