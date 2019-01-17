package io.frame.modules.happytrip.service;

import java.math.BigDecimal;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Recharge;

/**
 * 充值接口
 * 
 * @author fury
 *
 */
public interface RechargeService {

	/**
	 * 充值列表
	 * 
	 * @param recharge
	 * @return
	 */
	PageUtils<Recharge> queryPage(Recharge recharge);

	/**
	 * 获取用户充值成功的总金额数
	 * 
	 * @param userId
	 * @return
	 */
	BigDecimal getRechargeTotalMoneyById(Long userId);

}
