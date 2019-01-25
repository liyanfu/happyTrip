package io.frame.dao.custom.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import io.frame.dao.custom.entity.CustomeRecommendVo;

public interface CustomeRecommendMapper {

	/**
	 * 自定义查询条数方法
	 *
	 * @mbggenerated
	 */
	int customCountByExample(@Param("recommendNum") Integer recommendNum, @Param("teamsNum") Integer teamsNum,
			@Param("teamsAchievement") BigDecimal teamsAchievement);

	/**
	 * 自定义查询特别贡献奖方法
	 *
	 * @mbggenerated
	 */
	CustomeRecommendVo customSelectOneByExample(@Param("userId") Long userId,
			@Param("groupUserIds") String groupUserIds, @Param("recommendNum") Integer recommendNum,
			@Param("teamNum") Integer teamNum, @Param("teamAchievement") BigDecimal teamAchievement);

	/**
	 * 获取累计直推人数达标的用户信息
	 * 
	 * @param minRecommendNum 最小要求>=
	 * @param maxRecommendNum 最大要求<
	 * @return
	 */
	List<CustomeRecommendVo> customSelectRecommendNumList(@Param("minRecommendNum") Integer minRecommendNum,
			@Param("maxRecommendNum") Integer maxRecommendNum);

	/**
	 * 获取当前用户团队业绩，团队人数
	 * 
	 * @param groupUserIds
	 * @return
	 */
	CustomeRecommendVo customSelectTeamInfo(@Param("groupUserIds") String groupUserIds);

}