package io.frame.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import io.frame.annotation.Login;
import io.frame.common.exception.RRException;
import io.frame.common.utils.R;
import io.frame.common.validator.ValidatorUtils;
import io.frame.dao.entity.User;
import io.frame.entity.MyInfoVo;
import io.frame.exception.ErrorCode;
import io.frame.form.ForgetPassForm;
import io.frame.form.UserPassForm;
import io.frame.service.UserService;
import io.frame.utils.QRCodeUtils;
import io.frame.utils.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 登录接口
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "用户信息接口")
public class ApiUserController {

	private static final String FORGETVERIFICATIONCODE = "forgetVerificationCode";

	@Autowired
	private UserService userService;

	@Login
	@PostMapping("myPromote")
	@ApiOperation("我要推广-生成二维码")
	public R myPromote(HttpServletRequest request, HttpServletResponse response,
			@ApiIgnore @RequestAttribute("userId") Long userId) {

		if (userId == null) {
			throw new RRException(ErrorCode.TOKEN_NOT_NULL);
		}

		User user = SessionUtils.getCurrentUser(request);
		// 把手机号生成二维码返回给前端
		try {
			QRCodeUtils.generateQRCode(user.getUserMobile(), 200, 200, "png", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.ok();
	}

	/**
	 * 修改登录密码
	 * 
	 * @param userId
	 * @param form
	 * @return
	 */
	@Login
	@PostMapping("updateLoginUserPass")
	@ApiOperation("修改登录密码")
	public R updateUserPass(@ApiIgnore @RequestAttribute("userId") Long userId, @RequestBody UserPassForm form) {
		// 表单校验
		ValidatorUtils.validateEntity(form);

		if (!form.getNewUserPass1().equals(form.getNewUserPass2())) {
			throw new RRException(ErrorCode.NEW_USERPASS_DIFFERENCE);
		}
		String oldUserPass = DigestUtils.sha256Hex(form.getOldUserPass().trim());
		String newUserPass = DigestUtils.sha256Hex(form.getNewUserPass1().trim());
		userService.updateUserPass(userId, oldUserPass, newUserPass);
		return R.ok();
	}

	/**
	 * 修改支付密码
	 * 
	 * @param userId
	 * @param form
	 * @return
	 */
	@Login
	@PostMapping("updatePayPassWord")
	@ApiOperation("修改支付密码")
	public R updatePayPassWord(@ApiIgnore @RequestAttribute("userId") Long userId, @RequestBody UserPassForm form) {
		// 表单校验
		ValidatorUtils.validateEntity(form);

		if (!form.getNewUserPass1().equals(form.getNewUserPass2())) {
			throw new RRException(ErrorCode.NEW_USERPASS_DIFFERENCE);
		}
		String oldUserPass = DigestUtils.sha256Hex(form.getOldUserPass().trim());
		String newUserPass = DigestUtils.sha256Hex(form.getNewUserPass1().trim());
		userService.updateUserPass(userId, oldUserPass, newUserPass);
		return R.ok();
	}

	/**
	 * 用户忘记登录密码
	 * 
	 * @param userId
	 * @param form
	 * @return
	 */
	@Login
	@PostMapping("forgetUserPass")
	@ApiOperation("用户忘记登录密码(修改密码)")
	@ApiIgnore
	public R forgetUserPass(@ApiIgnore @RequestAttribute("userId") Long userId, @RequestBody ForgetPassForm form) {
		// 表单校验
		ValidatorUtils.validateEntity(form);

		if (!form.getNewUserPass1().equals(form.getNewUserPass2())) {
			throw new RRException(ErrorCode.NEW_USERPASS_DIFFERENCE);
		}
		String newUserPass = DigestUtils.sha256Hex(form.getNewUserPass1().trim());
		userService.updateUserPass(form.getMobile(), newUserPass);
		return R.ok();
	}

	/**
	 * 我的 信息展示
	 * 
	 * @param userId
	 * @param form
	 * @return
	 */
	@Login
	@PostMapping("getMyInfo")
	@ApiOperation("我的 信息展示")
	@ApiIgnore
	public R getMyInfo(@ApiIgnore @RequestAttribute("userId") Long userId) {

		MyInfoVo infoVo = userService.getMyInfo(userId);
		Map<String, Object> map = Maps.newHashMap();
		map.put("userInfo", infoVo);
		return R.ok(map);
	}

	/**
	 * 我的资料
	 * 
	 * @param userId
	 * @param form
	 * @return
	 */
	@Login
	@GetMapping("getMydata")
	@ApiOperation("我的 信息展示")
	@ApiIgnore
	public R getMydata(HttpServletRequest request, @ApiIgnore @RequestAttribute("userId") Long userId) {
		User user = SessionUtils.getCurrentUser(request);
		User u = new User();
		u.setAlipayMobile(user.getAlipayMobile());
		u.setAlipayName(user.getAlipayName());
		u.setUserMobile(user.getUserMobile());
		Map<String, Object> map = Maps.newHashMap();
		map.put("user", u);
		return R.ok(map);
	}
}
