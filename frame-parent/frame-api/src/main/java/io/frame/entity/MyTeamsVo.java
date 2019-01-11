package io.frame.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 我的团队
 * 
 * @author Fury
 *
 */
@JsonInclude(Include.NON_NULL)
public class MyTeamsVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 会员昵称
	 */
	private String userName;

	/**
	 * 会员层级
	 */
	private Integer userLevel;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 是否消费
	 */
	private String isConsume;

	/**
	 * 团队业绩
	 */
	private BigDecimal teamsMoney;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIsConsume() {
		return isConsume;
	}

	public void setIsConsume(String isConsume) {
		this.isConsume = isConsume;
	}

	public BigDecimal getTeamsMoney() {
		return teamsMoney;
	}

	public void setTeamsMoney(BigDecimal teamsMoney) {
		this.teamsMoney = teamsMoney;
	}

}
