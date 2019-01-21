package io.frame.modules.happytrip.service;

import java.math.BigDecimal;

import io.frame.common.enums.Constant.ChangeType;
import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.WalletChange;

/**
 * 钱包帐变接口
 * 
 * @author fury
 *
 */
public interface WalletChangeService {
	/**
	 * 用户钱包帐变集合
	 * 
	 * @param user
	 * @return
	 */
	PageUtils<WalletChange> queryPage(WalletChange walletChange);

	/**
	 * 创建帐变
	 * 
	 * @param userId
	 * @param changeMoney 账变金额
	 * @param balance     钱包剩余金额
	 * @param remark
	 * @param changeType
	 */
	void createWalletChange(Long userId, BigDecimal changeMoney, WalletChange walletChange, ChangeType changeType);
}
