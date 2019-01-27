
package io.frame.modules.happytrip.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;

import io.frame.common.enums.Constant.ChangeType;
import io.frame.common.enums.Constant.Sort;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.DateUtils;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.Order;
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

	@Override
	public void upsert(Long userId, BigDecimal money, BigDecimal moneyFee, ChangeType changeType) {
		SysUser sysUser = null;
		try {
			sysUser = ShiroUtils.getUserEntity();
		} catch (Exception e) {
			// 这一部是定时任务时,会取不到当前登录用户,报异常
		}
		User user = userService.getInfoById(userId);
		// 查询今日报表中是否已经存在数据
		ReportExample example = new ReportExample();
		ReportExample.Criteria cr = example.createCriteria();
		cr.andUserIdEqualTo(userId);
		Date date = new Date();
		cr.andCreateTimeEqualTo(date);
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
			report.setArtificialRechargeMoney(money);
			break;
		case "MANUAL_DEDUCTION_KEY":
			/** 人工扣款 */
			report.setArtificialWithdrawMoney(money);
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
			reportMapper.updateByPrimaryKeySelective(updateReport);
		}

	}

	@Transactional(readOnly = true)
	@Override
	public PageUtils<Report> queryPage(Report report) {
		ReportExample example = this.getReportExample(report, 1);
		try {
			Page<Report> page = (Page<Report>) reportMapper.selectByExample(example);

			// 插入一条统计全部的金额
			page.add(this.totals(report));

			return new PageUtils<Report>(page);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Report getInfoById(Long reportId) {
		return reportMapper.selectByPrimaryKey(reportId);
	}

	@Transactional(readOnly = true)
	@Override
	public Report totals(Report report) {
		ReportExample example = getReportExample(report, 2);
		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.sumField(Report.FD_ORDERMONEY));
		showField.add(SqlTools.sumField(Report.FD_RECHARGEMONEY));
		showField.add(SqlTools.sumField(Report.FD_RECHARGEFEE));
		showField.add(SqlTools.sumField(Report.FD_WITHDRAWMONEY));
		showField.add(SqlTools.sumField(Report.FD_WITHDRAWFEE));
		showField.add(SqlTools.sumField(Report.FD_CARPROFITMONEY));
		showField.add(SqlTools.sumField(Report.FD_PEOPLEWELFAREMONEY));
		showField.add(SqlTools.sumField(Report.FD_GLOBALBONUSMONEY));
		showField.add(SqlTools.sumField(Report.FD_TEAMLEADERMONEY));
		showField.add(SqlTools.sumField(Report.FD_SPECIALCONTRIBUTIONMONEY));
		showField.add(SqlTools.sumField(Report.FD_ARTIFICIALRECHARGEMONEY));
		showField.add(SqlTools.sumField(Report.FD_ARTIFICIALWITHDRAWMONEY));
		Report newRetort = reportMapper.selectOneByExampleShowField(showField, example);
		if (newRetort != null) {
			newRetort.setReportId(0L);
			newRetort.setUserName("合计:");
		}
		return newRetort;
	}

	@Override
	public void delete(Long reportId) {
		try {
			reportMapper.deleteByPrimaryKey(reportId);
		} catch (Exception e) {

		}

	}

	/**
	 * 拼接条件
	 * 
	 * @return
	 */
	private ReportExample getReportExample(Report report, Integer type) {
		ReportExample example = new ReportExample();
		ReportExample.Criteria cr = example.createCriteria();
		Date beginDate = report.getBeginTime();
		Date endDate = report.getEndTime();
		// 当天0点
		if (beginDate == null) {
			beginDate = DateUtils.parse(new Date(), "yyyy-MM-dd");
		}

		// 明天0点
		if (endDate == null) {
			endDate = DateUtils.addDateDays(DateUtils.parse(new Date(), "yyyy-MM-dd"), 1);
		}

		if (beginDate == endDate) {
			endDate = DateUtils.addDateDays(DateUtils.parse(new Date(), "yyyy-MM-dd"), 1);
		}

		if (!StringUtils.isEmpty(report.getUserName())) {
			cr.andUserNameLikeIgnoreNull(report.getUserName() + "%");
		}

		if (!StringUtils.isEmpty(report.getUserMobile())) {
			cr.andUserMobileLikeIgnoreNull(report.getUserMobile() + "%");
		}

		// 查询我的直属下级时使用
		if (report.getUserId() != null) {
			cr.andParentIdEqualTo(report.getUserId());
			cr.andUserIdNotEqualTo(report.getUserId());
		}
		// 查询我的团队时使用
		if (!StringUtils.isEmpty(report.getGroupUserIds())) {
			cr.andGroupUserIdsLikeIgnoreNull(report.getGroupUserIds() + "%");
		}

		cr.andCreateTimeGreaterThanOrEqualToIgnoreNull(beginDate);
		cr.andCreateTimeLessThanIgnoreNull(endDate);

		if (type == 1) {// 普通查询才需要分页，和排序
			PageHelper.startPage(report.getPageNumber(), report.getPageSize());
			example.setOrderByClause(Order.FD_CREATETIME + Sort.DESC.getValue());
		}

		return example;
	}

	@Override
	public PageUtils<Report> lookMyTeam(Report report) {

		ReportExample example = this.getReportExample(report, 1);
		try {
			Page<Report> page = new Page<Report>();
			List<Report> list = reportMapper.selectByExample(example);
			// 插入一条统计全部的金额
			list.add(this.totals(report));
			page.addAll(list);
			return new PageUtils<Report>(page);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Override
	public PageUtils<Report> lookUnder(Report report) {

		ReportExample example = this.getReportExample(report, 1);
		try {
			Page<Report> page = new Page<Report>();
			List<Report> list = reportMapper.selectByExample(example);
			// 插入一条统计全部的金额
			list.add(this.totals(report));
			page.addAll(list);
			return new PageUtils<Report>(page);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Report getMoneyByUserId(Long userId) {
		ReportExample example = new ReportExample();
		example.or().andUserIdEqualTo(userId);
		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.sumField(Report.FD_RECHARGEMONEY));
		showField.add(SqlTools.sumField(Report.FD_WITHDRAWFEE));
		try {
			return reportMapper.selectOneByExampleShowField(showField, example);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}
	}

}
