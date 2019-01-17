package io.frame.modules.happytrip.service;

import java.math.BigDecimal;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Withdraw;

/**
 * 提现接口
 * 
 * @author fury
 *
 */
public interface WithdrawService {

	/**
	 * 提现列表
	 * 
	 * @param withdraw
	 * @return
	 */
	PageUtils<Withdraw> queryPage(Withdraw withdraw);

	/**
	 * 获取用户提现成功的总金额数
	 * 
	 * @param userId
	 * @return
	 */
	BigDecimal getWithdrawTotalMoneyById(Long userId);

}
