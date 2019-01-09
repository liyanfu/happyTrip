
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

	@ApiModelProperty(value = "用户名", required = true)
	@NotBlank(message = "用户名不能为空")
	private String userName;

	@ApiModelProperty(value = "密码", required = true)
	@NotBlank(message = "密码不能为空")
	private String userPass;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

}
