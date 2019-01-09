
package io.frame.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 注册表单
 * 
 * @author fury
 *
 */
@ApiModel(value = "注册表单")
public class RegisterForm {

	@ApiModelProperty(value = "用户名", required = true)
	@NotBlank(message = "用户名不能为空")
	private String userName;

	@ApiModelProperty(value = "密码1", required = true)
	@NotBlank(message = "密码不能为空")
	@Length(min = 6, max = 20, message = "密码长度不正确")
	private String userPass1;

	@ApiModelProperty(value = "密码2", required = true)
	@NotBlank(message = "密码不能为空")
	@Length(min = 6, max = 20, message = "密码长度不正确")
	private String userPass2;

	@ApiModelProperty(value = "手机号", position = 4, required = true)
	@Pattern(regexp = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$", message = "手机号格式错误")
	@NotBlank(message = "手机号不能为空")
	private String mobile;

	@ApiModelProperty(value = "验证码", required = true)
	@NotBlank(message = "验证码不能为空")
	private String verificationCode;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass1() {
		return userPass1;
	}

	public void setUserPass1(String userPass1) {
		this.userPass1 = userPass1;
	}

	public String getUserPass2() {
		return userPass2;
	}

	public void setUserPass2(String userPass2) {
		this.userPass2 = userPass2;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

}
