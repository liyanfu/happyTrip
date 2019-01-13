package io.frame.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.frame.common.exception.RRException;
import io.frame.common.utils.R;
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

	// private static final String VERIFICATIONCODE = "verificationCode";

	@Autowired
	private UserService userService;

	@PostMapping("register")
	@ApiOperation(notes = "{msg:,code}", value = "注册")
	public R register(@RequestBody RegisterForm form) {
		// 表单校验
		ValidatorUtils.validateEntity(form);
		// 密码一致校验
		if (!form.getUserPass1().equals(form.getUserPass2())) {
			throw new RRException(ErrorCode.NEW_USERPASS_DIFFERENCE);
		}

		User user = new User();
		BeanUtils.copyProperties(form, user);
		user.setUserPass(DigestUtils.sha256Hex(form.getUserPass1().trim()));// 先加密

		return R.ok(userService.register(user));
	}

//	@PostMapping("sendVerificationCode")
//	@ApiOperation("向手机发送验证码")
//	public R sendVerificationCode(@RequestParam("mobile") Long mobile) {
//
//		if (mobile == null) {
//			throw new RRException(ErrorCode.PLEASE_INPUT_MOBILE);
//		}
//		int code = RandomUtils.nextInt(1000, 9999);
//		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//		request.getSession().setAttribute(VERIFICATIONCODE, code);
//		request.getSession().setMaxInactiveInterval(180);// 验证码180秒有效期
//		Map<String, Object> map = Maps.newHashMap();
//		map.put("verificationCode", code);
//		map.put("expireTime", 180);
//		return R.ok(map);
//	}

}
