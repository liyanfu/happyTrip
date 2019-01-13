
package io.frame.service;

import java.math.BigDecimal;
import java.util.List;

import io.frame.common.enums.Constant.ChangeType;
import io.frame.dao.entity.WalletChange;

/**
 * 用户帐变接口
 * 
 * @author fury
 *
 */
public interface WalletChangeService {

	/**
	 * 查新帐变信息
	 * 
	 * @param userId
	 * @return
	 */
	List<WalletChange> getWalletChangeList(Long userId, ChangeType changeType);

	/**
	 * 记录帐变
	 * 
	 * @param userId
	 * @param money
	 * @param orderId
	 * @param purchaseCarSpaceKey
	 */
	void createWalletChange(Long userId, BigDecimal money, Long orderId, ChangeType changeType);
}
