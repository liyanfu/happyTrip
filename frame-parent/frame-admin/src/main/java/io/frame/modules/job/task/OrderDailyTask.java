package io.frame.modules.job.task;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import io.frame.common.enums.Constant;
import io.frame.common.enums.Constant.ChangeType;
import io.frame.common.exception.RRException;
import io.frame.dao.entity.Config;
import io.frame.dao.entity.Order;
import io.frame.dao.entity.OrderExample;
import io.frame.dao.entity.WalletChange;
import io.frame.dao.mapper.OrderMapper;
import io.frame.modules.happytrip.service.WalletService;
import io.frame.modules.sys.service.SysConfigService;

/**
 * 订单共享汽车返利定时任务 每天凌晨0点跑
 * 
 * @author Fury
 *
 */
@Component("orderDailyTask")
public class OrderDailyTask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	OrderMapper orderMapper;

	@Autowired
	WalletService walletService;

	@Autowired
	SysConfigService sysConfigService;

	public void run() {
		logger.info("共享汽车每天返利定时任务--------------------------启动");
		Date currentDate = new Date();
		try {

			// 判断是否开启每天返利开关
			if (this.getSwitch()) {
				logger.info("开关没开启");
				return;
			}
			// 获取所有收益中的订单
			List<Order> orderList = this.getOrderList();
			if (!CollectionUtils.isEmpty(orderList)) {
				for (Order order : orderList) {
					// 判断当前订单是否已经到了收益时间
					if (!this.compareDate(order.getStartRebateTime(), currentDate, order.getCreateTime())) {
						continue; // 为假 下一个
					}

					// 每期返现金额
					BigDecimal rebateMoney = order.getRebateMoney();

					// 钱包加钱 帐变 刷新报表
					WalletChange walletChange = new WalletChange();
					walletChange.setUserId(order.getUserId());
					walletChange.setOperatorMoney(rebateMoney);
					walletChange.setRemark(ChangeType.CAR_PROFIT_KEY.getName());
					walletChange.setRelationId(order.getOrderId());
					walletService.addWallet(walletChange, ChangeType.CAR_PROFIT_KEY);
					// 更新订单返现期数和已返现金额
					Order updateOrder = new Order();
					updateOrder.setOrderId(order.getOrderId());
					updateOrder.setAlreadyProfitMoney(rebateMoney);
					updateOrder.setRebatePeriods(1);
					orderMapper.updateByPrimaryKeySelectiveSync(updateOrder);
					// 更新修改时间
					Order updateOrder2 = new Order();
					updateOrder2.setOrderId(order.getOrderId());
					updateOrder2.setUpdateTime(currentDate);
					updateOrder2.setUpdateUser("系统");
					// 判断是否已经全部发放完毕,是则改变订单状态
					if (order.getTotalRebatePeriods() - (order.getRebatePeriods() + 1) == 0) {
						// 最后一期已发完，
						updateOrder2.setStatus(Constant.OrderStatus.TWO.getValue());
					}
					orderMapper.updateByPrimaryKeySelective(updateOrder2);
				}
			}
		} catch (Exception e) {
			logger.error("共享汽车每天返利定时任务--------------------------异常");
			throw new RRException("共享汽车每天返利定时任务异常", e);
		}

		logger.info("共享汽车每天返利定时任务--------------------------结束");
	}

	/**
	 * 获取配置开关
	 * 
	 * @return
	 */
	private boolean getSwitch() {
		Config config = new Config();
		config.setConfigKey(Constant.WelfareSwitch.WELFARE_SWITCH_DAILY_KEY.getValue());
		config.setConfigStatus(Constant.Status.ONE.getValue());
		Config newConfig = sysConfigService.getInfo(config);
		if (newConfig == null || "0".equals(newConfig.getConfigVal())) {
			return true;
		}
		return false;
	}

	private boolean compareDate(Integer runDay, Date currentDate, Date createDate) {

		long betweenDays = (long) ((currentDate.getTime() - createDate.getTime()) / (1000 * 60 * 60 * 24));
		// 判断当前订单购买时间是否已经超过一天,至少需要超过一天.
		if (betweenDays <= 0) {
			return false;
		}
		// ==-1或者空 不限制
		if (runDay == -1 || runDay == null) {
			return true;
		}
		// 购买时间大于等于当前时间，计算返利
		return betweenDays >= runDay;
	}

	/**
	 * 获取所有收益中的共享汽车类型的订单
	 * 
	 * @return
	 */
	private List<Order> getOrderList() {
		OrderExample example = new OrderExample();
		example.or().andStatusEqualTo(Constant.OrderStatus.ONE.getValue()).andProductTypeIdEqualTo(1L);
		return orderMapper.selectByExample(example);
	}

}
