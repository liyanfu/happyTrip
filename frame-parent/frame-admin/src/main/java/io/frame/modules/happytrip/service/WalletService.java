package io.frame.modules.happytrip.service;

import io.frame.dao.entity.Wallet;

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
	Wallet getWalletById(Long userId);

	/**
	 * 新增用户钱包
	 * 
	 * @param userId
	 * @param userName
	 */
	void createWallet(Long userId, String userName);

}
