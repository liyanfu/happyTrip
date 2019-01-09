package io.frame.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import io.frame.common.exception.RRException;
import io.frame.common.utils.HttpContextUtils;
import io.frame.common.utils.R;
import io.frame.common.utils.RandomUtils;
import io.frame.common.validator.ValidatorUtils;
import io.frame.dao.entity.User;
import io.frame.exception.ErrorCode;
import io.frame.form.RegisterForm;
import io.frame.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 注册接口
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "注册接口")
public class ApiRegisterController {

	private static final String VERIFICATIONCODE = "verificationCode";

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

//	@PostMapping("thirdRegister")
//	@ApiOperation("(微信,QQ,微博)直接注册")
//	public R thirdRegister(@RequestBody ThirdRegisterForm form) {
//		// 表单校验
//		ValidatorUtils.validateEntity(form);
//		User user = new User();
//		BeanUtils.copyProperties(form, user);
//		user.setUserPass(DigestUtils.sha256Hex(user.getUserPass().trim()));// 先加密
//		return R.ok(userService.register(user));
//	}

	@PostMapping("sendVerificationCode")
	@ApiOperation("向手机发送验证码")
	public R sendVerificationCode(@RequestParam("mobile") Long mobile) {

		if (mobile == null) {
			throw new RRException(ErrorCode.PLEASE_INPUT_MOBILE);
		}
		int code = RandomUtils.nextInt(1000, 9999);
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		request.getSession().setAttribute(VERIFICATIONCODE, code);
		request.getSession().setMaxInactiveInterval(180);// 验证码180秒有效期
		Map<String, Object> map = Maps.newHashMap();
		map.put("verificationCode", code);
		map.put("expireTime", 180);
		return R.ok(map);
	}

}
