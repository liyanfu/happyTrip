
package io.frame.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 修改用户密码表单
 * 
 * @author fury
 *
 */
@ApiModel(value = "修改用户密码表单")
public class UserPassForm {

	@ApiModelProperty(value = "原始密码", position = 1, required = true)
	@NotBlank(message = "原始密码不能为空")
	private String oldUserPass;

	@ApiModelProperty(value = "新密码", required = true)
	@NotBlank(message = "新密码不能为空")
	private String newUserPass1;

	@ApiModelProperty(value = "新密码", required = true)
	@NotBlank(message = "新密码不能为空")
	private String newUserPass2;

	public String getOldUserPass() {
		return oldUserPass;
	}

	public void setOldUserPass(String oldUserPass) {
		this.oldUserPass = oldUserPass;
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
