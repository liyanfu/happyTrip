package io.frame.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 奖励福利实体类
 * 
 * @author Fury
 *
 */
@JsonInclude(Include.NON_NULL)
public class WelfareVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 奖项名称
	 */
	private String welfareName;

	/**
	 * 奖励规则
	 */
	private String remark;

	/**
	 * 达标人数
	 */
	private Integer qualifiedCount;

	/**
	 * 奖金池
	 */
	private BigDecimal bounsPool;

	/**
	 * 平均分配额
	 */
	private BigDecimal averageAllot;

	public String getWelfareName() {
		return welfareName;
	}

	public void setWelfareName(String welfareName) {
		this.welfareName = welfareName;
	}

	public Integer getQualifiedCount() {
		return qualifiedCount;
	}

	public void setQualifiedCount(Integer qualifiedCount) {
		this.qualifiedCount = qualifiedCount;
	}

	public BigDecimal getBounsPool() {
		return bounsPool;
	}

	public void setBounsPool(BigDecimal bounsPool) {
		this.bounsPool = bounsPool;
	}

	public BigDecimal getAverageAllot() {
		return averageAllot;
	}

	public void setAverageAllot(BigDecimal averageAllot) {
		this.averageAllot = averageAllot;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
