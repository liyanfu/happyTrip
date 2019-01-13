
package io.frame.service;

import java.math.BigDecimal;

import io.frame.dao.entity.Wallet;

/**
 * 用户钱包接口
 * 
 * @author fury
 *
 */
public interface WalletService {

	/**
	 * 创建用户钱包
	 * 
	 * @param userId
	 * @param userName
	 */
	void createWallet(Long userId, String userName);

	/**
	 * 获取钱包余额
	 * 
	 * @param userId
	 * @return
	 */
	Wallet getWallet(Long userId);

	/**
	 * 扣款
	 * 
	 * @param userId
	 * @param buyMoney
	 */
	void deduction(Long walletId, BigDecimal buyMoney);

}
