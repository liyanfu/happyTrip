
package io.frame.modules.happytrip.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.frame.common.enums.Constant.Status;
import io.frame.common.utils.DateUtils;
import io.frame.common.utils.R;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.GlobalRecord;
import io.frame.dao.entity.Recharge;
import io.frame.dao.entity.RechargeExample;
import io.frame.dao.entity.Report;
import io.frame.dao.entity.ReportExample;
import io.frame.dao.entity.TokenExample;
import io.frame.dao.entity.UserExample;
import io.frame.dao.entity.Withdraw;
import io.frame.dao.entity.WithdrawExample;
import io.frame.dao.mapper.RechargeMapper;
import io.frame.dao.mapper.ReportMapper;
import io.frame.dao.mapper.TokenMapper;
import io.frame.dao.mapper.UserMapper;
import io.frame.dao.mapper.WithdrawMapper;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 全球分红派发记录
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/ht/index")
public class IndexController extends AbstractController {
	@Autowired
	private ReportMapper reportMapper;

	@Autowired
	private RechargeMapper rechargeMapper;

	@Autowired
	private WithdrawMapper withdrawMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private TokenMapper tokenMapper;

	/**
	 * 获取首页需要的展示信息
	 * 
	 * @param globalrecord
	 * @return
	 */
	@RequestMapping("/info")
	public R list(GlobalRecord globalrecord) {
		Date date = new Date();
		Map<String, Object> map = Maps.newHashMap();
		// 今日订单业绩
		map.put("todayOrderMoney", this.getOrderMoney(date));
		// 历史订单业绩
		map.put("historyOrderMoney", this.getOrderMoney(null));
		// 今日充值金额
		map.put("todayRechargeMoney", this.getRechargeMoney(date));
		// 历史充值金额
		map.put("historyRechargeMoney", this.getRechargeMoney(null));
		// 待处理充值订单数
		map.put("rechargeCount", this.getPendingRechargeCount());
		// 今日提现金额
		map.put("todayWithdrawMoney", this.getWithdrawMoney(date));
		// 历史提现总金额
		map.put("historyWithdrawMoney", this.getWithdrawMoney(null));
		// 待审核提现订单数
		map.put("withdrawCount", this.getExamineWithdrawCount());
		// 今日注册用户数
		map.put("todayRegisterCount", this.getRegisterCount(date));
		// 历史总注册用户数
		map.put("historyRegisterCount", this.getRegisterCount(null));
		// 今日登录用户数
		map.put("todayLoginCount", this.getLoginCount(date));

		return R.ok(map);
	}

	private Object getLoginCount(Date date) {
		TokenExample example = new TokenExample();
		TokenExample.Criteria cr = example.createCriteria();
		cr.andUpdateTimeGreaterThanOrEqualTo(DateUtils.getStartTime(date));
		cr.andUpdateTimeLessThan(DateUtils.getEndTime(date));
		return tokenMapper.countByExample(example);
	}

	private Object getRegisterCount(Date date) {
		UserExample example = new UserExample();
		UserExample.Criteria cr = example.createCriteria();
		if (date != null) {
			cr.andCreateTimeGreaterThanOrEqualTo(DateUtils.getStartTime(date));
			cr.andCreateTimeLessThan(DateUtils.getEndTime(date));
		}
		return userMapper.countByExample(example);
	}

	private Object getExamineWithdrawCount() {
		WithdrawExample example = new WithdrawExample();
		example.or().andStatusEqualTo(Status.ZERO.getValue());
		return withdrawMapper.countByExample(example);
	}

	private Object getWithdrawMoney(Date date) {
		WithdrawExample example = new WithdrawExample();
		WithdrawExample.Criteria cr = example.createCriteria();
		cr.andStatusEqualTo(Status.ONE.getValue());
		if (date != null) {
			cr.andCreateTimeGreaterThanOrEqualTo(DateUtils.getStartTime(date));
			cr.andCreateTimeLessThan(DateUtils.getEndTime(date));
		}
		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.sumField(Withdraw.FD_WITHDRAWMONEY));
		Withdraw withdraw = withdrawMapper.selectOneByExampleShowField(showField, example);
		return withdraw == null ? BigDecimal.ZERO : withdraw.getWithdrawMoney();
	}

	private Object getPendingRechargeCount() {
		RechargeExample example = new RechargeExample();
		example.or().andStatusEqualTo(Status.ZERO.getValue());
		return rechargeMapper.countByExample(example);
	}

	private Object getRechargeMoney(Date date) {
		RechargeExample example = new RechargeExample();
		RechargeExample.Criteria cr = example.createCriteria();
		cr.andStatusEqualTo(Status.ONE.getValue());
		if (date != null) {
			cr.andCreateTimeGreaterThanOrEqualTo(DateUtils.getStartTime(date));
			cr.andCreateTimeLessThan(DateUtils.getEndTime(date));
		}

		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.sumField(Recharge.FD_RECHARGEMONEY));
		Recharge recharge = rechargeMapper.selectOneByExampleShowField(showField, example);
		return recharge == null ? BigDecimal.ZERO : recharge.getRechargeMoney();
	}

	private Object getOrderMoney(Date date) {
		ReportExample example = new ReportExample();
		example.or().andCreateTimeEqualToIgnoreNull(date);
		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.sumField(Report.FD_ORDERMONEY));
		Report report = reportMapper.selectOneByExampleShowField(showField, example);
		return report == null ? BigDecimal.ZERO : report.getOrderMoney();
	}

}
