
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
	 * 获取满足全球分红条件的人数
	 * 
	 * @param recommendNum 当日直推人数
	 * @return
	 */
	int getSatisfyGlobalBonusNum(Integer recommendNum);

	/**
	 * 获取满足领导团队奖条件的人数
	 * 
	 * @param recommendNum 当日直推人数
	 * @param achievement  当日有效业绩
	 * @return
	 */
	int getSatisfyTeamLeadershipAwardNum(Integer recommendNum, BigDecimal achievement);

	/**
	 * 获取满足特别贡献奖条件的人数
	 * 
	 * @param recommendNum     直推会员人数
	 * @param teamsNum         团队人数
	 * @param teamsAchievement 累计团队业绩
	 * @return
	 */
	int getSatisfySpecialContributionAwardNum(Integer recommendNum, Integer teamsNum, BigDecimal teamsAchievement);

	/**
	 * 更新用户的推荐人数，和团队业绩
	 * 
	 * @param userId
	 * @param object
	 */
	void upsert(Long userId, BigDecimal money);

}
