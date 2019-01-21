package io.frame.modules.happytrip.service;

import io.frame.dao.entity.Wallet;
import io.frame.dao.entity.WalletChange;

/**
 * 钱包接口
 * 
 * @author fury
 *
 */
public interface WalletService {

	/**
	 * 获取用户钱包
	 * 
	 * @param userId
	 * @return
	 */
	Wallet getInfoById(Long userId);

	/**
	 * 新增用户钱包
	 * 
	 * @param userId
	 * @param userName
	 */
	void createWallet(Long userId, String userName);

	/**
	 * 人工充值
	 * 
	 * @param walletChange
	 */
	void recharge(WalletChange walletChange);

	/**
	 * 人工扣款
	 * 
	 * @param walletChange
	 */
	void subtract(WalletChange walletChange);

}
