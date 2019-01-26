
package io.frame.service;

import java.math.BigDecimal;

/**
 * 每日推荐接口
 * 
 * @author fury
 *
 */
public interface RecommendService {

	/**
	 * 获取直推人数,
	 * 
	 * @param userId
	 * @param isTodayFlag
	 * @return
	 */
	Integer getRecommendNumByParentId(Long userId, boolean isTodayFlag);

	/**
	 * 获取团队业绩,包括自己
	 * 
	 * @param groupUserIds
	 * @param isTodayFlag
	 * @return
	 */
	BigDecimal getTeamAchievementByGroupIds(String groupUserIds, Boolean isTodayFlag);

	/**
	 * 获取直属下级业绩
	 * 
	 * @param userId
	 * @return
	 */
	BigDecimal getTeamAchievementByParentId(Long userId);

	/**
	 * 更新用户的推荐人数，和团队业绩
	 * 
	 * @param userId
	 * @param object
	 */
	void upsert(Long userId, Integer num, BigDecimal money);

}
