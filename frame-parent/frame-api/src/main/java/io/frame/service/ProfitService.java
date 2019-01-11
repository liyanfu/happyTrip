
package io.frame.service;

import java.util.List;
import java.util.Map;

import io.frame.dao.entity.WalletChange;

/**
 * 收益(账变)信息接口
 * 
 * @author fury
 *
 */
public interface ProfitService {

	/**
	 * 汽车收益
	 * 
	 * @param userId
	 * @return
	 */
	public List<WalletChange> getMyCarProfit(Long userId);

	/**
	 * 全球分红
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getGlobalBonus(Long userId);
}
