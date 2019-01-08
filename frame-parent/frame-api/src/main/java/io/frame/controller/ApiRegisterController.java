package io.frame.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.wall.violation.ErrorCode;

import io.frame.common.exception.RRException;
import io.frame.common.utils.HttpContextUtils;
import io.frame.common.utils.R;
import io.frame.common.validator.ValidatorUtils;
import io.frame.form.RegisterForm;
import io.frame.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 注册接口
 * 
 * @author Fury
 *
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "注册接口")
public class ApiRegisterController {
	@Autowired
	private UserService userService;

	@PostMapping("register")
	@ApiOperation("注册")
	public R register(@RequestBody RegisterForm form) {
		// 表单校验
		ValidatorUtils.validateEntity(form);
		// 密码一致校验
		if (!form.getUserPass1().equals(form.getUserPass2())) {
			throw new RRException(ErrorCode.NEW_USERPASS_DIFFERENCE);
		}
		// 获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();

		Object verificationCode = request.getSession().getAttribute(VERIFICATIONCODE);
		// 验证码校验
		if (verificationCode == null) {
			throw new RRException(ErrorCode.VERIFICATIONCODE_IS_EXPIRE);
		}
		// 验证码校验
		if (!form.getVerificationCode().equals(String.valueOf(verificationCode))) {
			throw new RRException(ErrorCode.VERIFICATIONCODE_IS_ERROR);
		}

		User user = new User();
		BeanUtils.copyProperties(form, user);
		user.setUserPass(form.getUserPass1());
		user.setUserPass(DigestUtils.sha256Hex(user.getUserPass().trim()));// 先加密

		// 缓存失效
		request.getSession().setAttribute(VERIFICATIONCODE, null);

		return R.ok(userService.register(user));
	}
}
