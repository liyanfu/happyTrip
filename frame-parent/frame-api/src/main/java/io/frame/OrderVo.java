package io.frame;

import java.io.Serializable;

/**
 * 专属车位实体类
 * 
 * @author Fury
 *
 */
public class OrderVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 购买的商品名称
	 */
	private String productName;

	/**
	 * 购买的商品数量
	 */
	private Integer quantity;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
