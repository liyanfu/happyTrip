
package io.frame.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 忘记密码表单
 * 
 * @author fury
 *
 */
@ApiModel(value = "忘记密码表单")
public class ForgetPassForm {

	@ApiModelProperty(value = "手机号", position = 1, required = true)
	@NotNull(message = "手机号不能为空")
	private Long mobile;

	@ApiModelProperty(value = "新密码", required = true)
	@NotBlank(message = "新密码不能为空")
	private String newUserPass1;

	@ApiModelProperty(value = "新密码", required = true)
	@NotBlank(message = "新密码不能为空")
	private String newUserPass2;

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public String getNewUserPass1() {
		return newUserPass1;
	}

	public void setNewUserPass1(String newUserPass1) {
		this.newUserPass1 = newUserPass1;
	}

	public String getNewUserPass2() {
		return newUserPass2;
	}

	public void setNewUserPass2(String newUserPass2) {
		this.newUserPass2 = newUserPass2;
	}

}
