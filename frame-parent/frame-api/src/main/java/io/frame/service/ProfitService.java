
package io.frame.service;

import java.util.Map;

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
	public Map<String, Object> getMyCarProfit(Long userId);

	/**
	 * 汽车收益
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getAllWelfare(Long userId);

	/**
	 * 全球分红奖
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getGlobalBonus(Long userId);

	/**
	 * 团队领导奖
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getLeaderBonus(Long userId);

	/**
	 * 特别贡献奖
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getEspeciallyBonus(Long userId);
}
