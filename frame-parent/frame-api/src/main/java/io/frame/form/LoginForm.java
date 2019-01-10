
package io.frame.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登录表单
 * 
 * @author fury
 *
 */
@ApiModel(value = "登录表单")
public class LoginForm {

	@ApiModelProperty(value = "登录账号", required = true)
	@NotBlank(message = "登录账号不能为空")
	private String userMobile;

	@ApiModelProperty(value = "密码", required = true)
	@NotBlank(message = "密码不能为空")
	private String userPass;

	@ApiModelProperty(value = "验证码", required = true)
	@NotBlank(message = "验证码不能为空")
	private String captcha;

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}
