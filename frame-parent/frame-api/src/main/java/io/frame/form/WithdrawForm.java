
package io.frame.form;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 提现表单
 * 
 * @author fury
 *
 */
@ApiModel(value = "提现表单")
public class WithdrawForm {

	@ApiModelProperty(value = "提现金额", required = true)
	@Min(value = 1, message = "提现金额不能小于1")
	@NotNull(message = "提现金额不能为空")
	private BigDecimal money;

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

}
