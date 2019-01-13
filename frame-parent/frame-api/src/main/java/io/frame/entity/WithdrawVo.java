package io.frame.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 提现明细实体类
 * 
 * @author Fury
 *
 */
@JsonInclude(Include.NON_NULL)
public class WithdrawVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 提现金额
	 */
	private BigDecimal withdrawMoney;

	/**
	 * 提现手续费
	 */
	private BigDecimal withdrawFee;

	/**
	 * 提现状态
	 */
	private String status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	public BigDecimal getWithdrawMoney() {
		return withdrawMoney;
	}

	public void setWithdrawMoney(BigDecimal withdrawMoney) {
		this.withdrawMoney = withdrawMoney;
	}

	public BigDecimal getWithdrawFee() {
		return withdrawFee;
	}

	public void setWithdrawFee(BigDecimal withdrawFee) {
		this.withdrawFee = withdrawFee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
