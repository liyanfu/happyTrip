
package io.frame.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import io.frame.entity.WithdrawVo;

/**
 * 提现接口
 * 
 * @author fury
 *
 */
public interface WithdrawService {

	/**
	 * 提现之前查询账户信息
	 * 
	 * @param userId
	 * @param money
	 * @return
	 */
	Map<String, Object> getWithdrawInfo(Long userId);

	/**
	 * 创建提现订单
	 * 
	 * @param userId
	 * @param money
	 * @return
	 */
	void withdrawSubmit(Long userId, BigDecimal money);

	/**
	 * 查询提现信息
	 * 
	 * @param userId
	 * @param money
	 * @return
	 */
	List<WithdrawVo> getWithdrawList(Long userId);

}
