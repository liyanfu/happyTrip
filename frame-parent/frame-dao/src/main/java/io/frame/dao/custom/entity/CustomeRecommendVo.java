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

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 福利Key
	 */
	private String welfareKey;

	/**
	 * 百分比
	 */
	private BigDecimal percent;

	/**
	 * 奖金池
	 */
	private BigDecimal bonusPool;

	/**
	 * 达标值
	 */
	private String welfareValue;

	/**
	 * 用户组ID
	 */
	private String groupUserIds;

	/**
	 * 直推人数
	 */
	private Integer recommendNum;

	/**
	 * 团队人数
	 */
	private Integer teamNum;
	/**
	 * 团队业绩
	 */

	private BigDecimal teamAchievement;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getWelfareKey() {
		return welfareKey;
	}

	public void setWelfareKey(String welfareKey) {
		this.welfareKey = welfareKey;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public BigDecimal getBonusPool() {
		return bonusPool;
	}

	public void setBonusPool(BigDecimal bonusPool) {
		this.bonusPool = bonusPool;
	}

	public String getWelfareValue() {
		return welfareValue;
	}

	public void setWelfareValue(String welfareValue) {
		this.welfareValue = welfareValue;
	}

	public String getGroupUserIds() {
		return groupUserIds;
	}

	public void setGroupUserIds(String groupUserIds) {
		this.groupUserIds = groupUserIds;
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