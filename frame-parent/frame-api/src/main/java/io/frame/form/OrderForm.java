
package io.frame.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 下单购买表单
 * 
 * @author fury
 *
 */
@ApiModel(value = "下单表单")
public class OrderForm {

	@ApiModelProperty(value = "订单ID,当选择支付宝点击付款之后返回,此时再选择余额支付或者再次点击去支付带上该值")
	private Long orderId;

	@ApiModelProperty(value = "商品ID", required = true)
	@NotNull(message = "商品ID不能为空")
	private Long productId;

	@ApiModelProperty(value = "支付类型Key", required = true)
	@NotBlank(message = "支付类型不能为空")
	private String paymentKey;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getPaymentKey() {
		return paymentKey;
	}

	public void setPaymentKey(String paymentKey) {
		this.paymentKey = paymentKey;
	}

}
