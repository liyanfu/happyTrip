package io.frame.modules.job.task;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

import io.frame.common.enums.Constant;
import io.frame.common.enums.Constant.Status;
import io.frame.common.utils.DateUtils;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.Config;
import io.frame.dao.entity.Recommend;
import io.frame.dao.entity.RecommendExample;
import io.frame.dao.entity.Report;
import io.frame.dao.entity.ReportExample;
import io.frame.dao.entity.User;
import io.frame.dao.entity.UserExample;
import io.frame.dao.entity.Welfare;
import io.frame.dao.entity.WelfareExample;
import io.frame.dao.mapper.RecommendMapper;
import io.frame.dao.mapper.ReportMapper;
import io.frame.dao.mapper.UserMapper;
import io.frame.dao.mapper.WelfareMapper;
import io.frame.modules.sys.service.SysConfigService;

/**
 * 全球分红定时任务 每天凌晨0点跑
 * 
 * 
 * @author Fury
 *
 */
@Component("globalTask")
public class GlobalTask {
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

	public void run() {
		logger.info("全球分红定时任务--------------------------启动");
		Date currentDate = new Date();
		try {

//			// 判断是否开启每天返利开关
//			if (!this.getSwitch()) {
//				logger.info("开关没开启");
//				return;
//			}
			// 获取昨日推荐表中的用户信息

			List<Recommend> recommendList = this.getRecommendList(currentDate);

			if (!CollectionUtils.isEmpty(recommendList)) {

				// 获取全球分红奖励规则
				List<Welfare> ruleList = this.getRuleList();

				if (CollectionUtils.isEmpty(ruleList)) {
					logger.info("无匹配规则...");
					return;
				}
				// 获取昨日报表中的订单总业绩金额
				BigDecimal totalsMoney = this.getOrderTotasMoney(currentDate);

				for (Recommend recommend : recommendList) {

					Integer recommendNum = recommend.getRecommendNumber();
					Welfare ruleWelfare = this.getRuleWelfare(ruleList, recommendNum);

					if (ruleWelfare == null) {
						// 无匹配规则
						continue;
					}

				}
			}
		} catch (Exception e) {
			logger.error("全球分红定时任务--------------------------异常");
		}

		logger.info("全球分红定时任务--------------------------结束");
	}

	/**
	 * 获取匹配规则
	 * 
	 * @return
	 */
	private Welfare getRuleWelfare(List<Welfare> ruleList, Integer recommendNum) {
		Welfare ruleWelfare = null;
		// 根据规则 计算返利
		for (Welfare welfare : ruleList) {
			Integer ruleNum = Integer.parseInt(welfare.getWelfareValue());
			if (recommendNum >= ruleNum) {
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
				.andWelfareKeyEqualTo(Constant.WelfareKey.GLOBAL_BONUS_KEY.getValue());
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
		if (newConfig != null && "1".equals(newConfig.getConfigVal())) {
			return false;
		}
		return true;
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
