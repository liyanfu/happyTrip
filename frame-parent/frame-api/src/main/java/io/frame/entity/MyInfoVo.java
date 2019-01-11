package io.frame.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 我的信息展示
 * 
 * @author Fury
 *
 */
@JsonInclude(Include.NON_NULL)
public class MyInfoVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户昵称
	 */
	private String userName;

	/**
	 * 钱包余额
	 */
	private BigDecimal walletBalance;

	/**
	 * 总收益
	 */
	private BigDecimal totalProfit;

	/**
	 * 直推人数
	 */
	private Integer recommendNum;

	/**
	 * 团队人数
	 */
	private Integer teamNum;

	/**
	 * 团队总业绩
	 */
	private BigDecimal teamTotalMoney;

	/**
	 * 今日直推人数
	 */
	private Integer todayRecomendNum;

	/**
	 * 今日团队业绩
	 */
	private BigDecimal todayTeamMoney;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(BigDecimal walletBalance) {
		this.walletBalance = walletBalance;
	}

	public BigDecimal getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(BigDecimal totalProfit) {
		this.totalProfit = totalProfit;
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

	public BigDecimal getTeamTotalMoney() {
		return teamTotalMoney;
	}

	public void setTeamTotalMoney(BigDecimal teamTotalMoney) {
		this.teamTotalMoney = teamTotalMoney;
	}

	public Integer getTodayRecomendNum() {
		return todayRecomendNum;
	}

	public void setTodayRecomendNum(Integer todayRecomendNum) {
		this.todayRecomendNum = todayRecomendNum;
	}

	public BigDecimal getTodayTeamMoney() {
		return todayTeamMoney;
	}

	public void setTodayTeamMoney(BigDecimal todayTeamMoney) {
		this.todayTeamMoney = todayTeamMoney;
	}

}