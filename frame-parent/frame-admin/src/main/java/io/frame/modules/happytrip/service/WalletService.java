package io.frame.modules.happytrip.service;

import io.frame.common.enums.Constant.ChangeType;
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
	Wallet getInfoByUserId(Long userId);

	/**
	 * 获取钱包
	 * 
	 * @param userId
	 * @return
	 */
	Wallet getInfoById(Long walletId);

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

	/**
	 * 购买订单扣款
	 * 
	 * @param walletChange
	 */
	void orderSubtract(WalletChange walletChange);

	/**
	 * 充值入账
	 * 
	 * @param walletChange
	 */
	void recharge(WalletChange walletChange, ChangeType changeType);

	/**
	 * 提现回退
	 * 
	 * @param walletChange
	 * @param withdrawOutBackKey
	 */
	void subtractBack(WalletChange walletChange, ChangeType changeType);

	/**
	 * 各种福利收益入账
	 * 
	 * @param walletChange
	 */
	void addWallet(WalletChange walletChange, ChangeType changeType);

}
