
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

	@ApiModelProperty(value = "登录手机号", position = 1, required = true)
	@Pattern(regexp = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$", message = "手机号格式错误")
	@NotBlank(message = "登录手机号不能为空")
	private String userMobile;

	@ApiModelProperty(value = "用户名", required = true)
	@NotBlank(message = "用户昵称")
	private String userName;

	@ApiModelProperty(value = "密码1", required = true)
	@NotBlank(message = "密码不能为空")
	@Length(min = 6, max = 20, message = "密码长度不正确")
	private String userPass1;

	@ApiModelProperty(value = "密码2", required = true)
	@NotBlank(message = "密码不能为空")
	@Length(min = 6, max = 20, message = "密码长度不正确")
	private String userPass2;

	@ApiModelProperty(value = "支付宝名称", required = true)
	@NotBlank(message = "支付宝名称不能为空")
	private String alipayName;

	@ApiModelProperty(value = "支付宝账号", required = true)
	@NotBlank(message = "支付宝账号不能为空")
	private String alipayMobile;

	@ApiModelProperty(value = "推荐人账号", required = true)
	@NotBlank(message = "推荐人账号不能为空")
	private String recommendMobile;

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

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

	public String getAlipayName() {
		return alipayName;
	}

	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}

	public String getAlipayMobile() {
		return alipayMobile;
	}

	public void setAlipayMobile(String alipayMobile) {
		this.alipayMobile = alipayMobile;
	}

	public String getRecommendMobile() {
		return recommendMobile;
	}

	public void setRecommendMobile(String recommendMobile) {
		this.recommendMobile = recommendMobile;
	}

}
