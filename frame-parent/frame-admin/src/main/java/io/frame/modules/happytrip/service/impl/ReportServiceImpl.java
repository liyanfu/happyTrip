
package io.frame.modules.happytrip.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.frame.common.annotation.SysLog;
import io.frame.common.enums.Constant.ChangeType;
import io.frame.common.utils.DateUtils;
import io.frame.dao.entity.Report;
import io.frame.dao.entity.ReportExample;
import io.frame.dao.entity.SysUser;
import io.frame.dao.entity.User;
import io.frame.dao.mapper.ReportMapper;
import io.frame.modules.happytrip.service.ReportService;
import io.frame.modules.happytrip.service.UserService;
import io.frame.modules.sys.shiro.ShiroUtils;

/**
 * 报表统计
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class ReportServiceImpl implements ReportService {
	Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

	@Autowired
	ReportMapper reportMapper;

	@Autowired
	UserService userService;

	@SysLog("更新报表")
	@Override
	public void upsert(Long userId, BigDecimal money, BigDecimal moneyFee, ChangeType changeType) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		User user = userService.getInfoById(userId);
		// 查询今日报表中是否已经存在数据
		ReportExample example = new ReportExample();
		ReportExample.Criteria cr = example.createCriteria();
		cr.andUserIdEqualTo(userId);
		Date date = new Date();
		cr.andCreateTimeGreaterThanOrEqualTo(DateUtils.getStartTime(date));
		cr.andCreateTimeLessThan(DateUtils.getEndTime(date));
		Report report = reportMapper.selectOneByExample(example);
		boolean flag = false;
		if (report == null) {
			flag = true;
			// 新建
			report = new Report();
			report.setUserId(userId);
			report.setGroupUserIds(user.getGroupUserIds());
			report.setParentId(user.getParentId());
			report.setUserLevel(user.getUserLevel());
			report.setUserMobile(user.getUserMobile());
			report.setUserName(user.getUserName());
			report.setCreateTime(date);
			report.setCreateUser(sysUser == null ? "系统" : sysUser.getUserName());
		} else {
			flag = false;
			Long reportId = report.getReportId();
			report = new Report();
			report.setReportId(reportId);
		}
		switch (changeType.getValue()) {
		case "RECHARGE_IN_KEY":
			/** 充值入账 */
			report.setRechargeFee(moneyFee);
			report.setRechargeMoney(money);
			break;
		case "CAR_PROFIT_KEY":
			/** 汽车收益 */
			report.setCarProfitMoney(money);
			break;
		case "ALL_PEOPLE_WELFARE_KEY":
			/** 全民福利 */
			report.setPeopleWelfareMoney(money);
			break;
		case "GLOBAL_BONUS_KEY":
			/** 全球分红 */
			report.setGlobalBonusMoney(money);
			break;
		case "TEAM_LEADERSHIP_AWARD_KEY":
			/** 领导团队奖 */
			report.setTeamLeaderMoney(money);
			break;
		case "SPECIAL_CONTRIBUTION_AWARD_KEY":
			/** 特别贡献奖 */
			report.setSpecialContributionMoney(money);
			break;
		case "WITHDRAW_OUT_KEY":
			/** 提现出款 */
			report.setWithdrawFee(moneyFee);
			report.setWithdrawMoney(money);
			break;
		case "PURCHASE_CAR_SPACE_KEY":
			/** 购买车位 下单 */
			report.setOrderMoney(money);
			break;
		case "ARTIFICIAL_RECHARGE_KEY":
			/** 人工充值 */
			report.setRechargeMoney(money);
			break;
		case "MANUAL_DEDUCTION_KEY":
			/** 人工扣款 */
			report.setWithdrawMoney(money);
			break;
		default:
			break;
		}

		if (flag) {
			// 新增
			reportMapper.insertSelective(report);
		} else {
			// 更新
			reportMapper.updateByPrimaryKeySelectiveSync(report);
			Report updateReport = new Report();
			updateReport.setReportId(report.getReportId());
			updateReport.setUpdateTime(date);
			updateReport.setUpdateUser(sysUser == null ? "系统" : sysUser.getUserName());
			// 更新操作者和时间
			reportMapper.updateByPrimaryKey(updateReport);
		}

	}

}
