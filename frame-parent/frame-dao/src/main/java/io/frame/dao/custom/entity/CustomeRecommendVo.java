package io.frame.dao.custom.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import io.frame.dao.base.BaseEntity;

/**
 * 自定义推荐表
 * 
 * @author Fury
 *
 */
public class CustomeRecommendVo extends BaseEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;

	private Integer recommendNum;

	private Integer teamNum;

	private BigDecimal teamAchievement;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getRecommendNum() {
		return recommendNum;
	}

	public void setRecommendNum(Integer recommendNum) {
		this.recommendNum = recommendNum;
	}

	public Integer getTeamNum() {
		return teamNum;
	}

	public void setTeamNum(Integer teamNum) {
		this.teamNum = teamNum;
	}

	public BigDecimal getTeamAchievement() {
		return teamAchievement;
	}

	public void setTeamAchievement(BigDecimal teamAchievement) {
		this.teamAchievement = teamAchievement;
	}

}