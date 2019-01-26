
package io.frame.form;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 充值表单
 * 
 * @author fury
 *
 */
@ApiModel(value = "充值表单")
public class RechargeForm {

	@ApiModelProperty(value = "充值金额", required = true)
	@Min(value = 1, message = "充值金额不能小于0")
	@NotNull(message = "充值金额不能为空")
	private BigDecimal money;

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

}
