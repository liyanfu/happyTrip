package io.frame.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;

import io.frame.annotation.Login;
import io.frame.common.exception.RRException;
import io.frame.common.utils.R;
import io.frame.common.utils.RandomUtils;
import io.frame.common.validator.ValidatorUtils;
import io.frame.exception.ErrorCode;
import io.frame.form.ForgetPassForm;
import io.frame.form.UserPassForm;
import io.frame.service.UserService;
import io.frame.utils.UploadUtils;
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

	/**
	 * 修改用户密码
	 * 
	 * @param userId
	 * @param form
	 * @return
	 */
	@Login
	@PostMapping("updateUserPass")
	@ApiOperation("修改用户密码")
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
	 * 修改用户头像
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@Login
	@PostMapping("updateHeadImg")
	@ApiOperation("修改用户头像")
	public R updateHeadImg(@RequestParam("file") MultipartFile file, @ApiIgnore @RequestAttribute("userId") Long userId)
			throws Exception {
		if (file.isEmpty()) {
			throw new RRException(ErrorCode.PLEASE_SELECT_IMAGE);
		}

		// 上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String imgUrl = UploadUtils.build().uploadSuffix(file.getBytes(), suffix);
		// userService.updateHeadImg(userId, imgUrl);
		Map<String, Object> data = new HashMap<>();
		data.put("imgUrl", imgUrl);
		return R.ok().put("data", data);
	}

	@PostMapping("forgetUserPass1")
	@ApiOperation("忘记密码1(向手机发送短信验证码)")
	public R forgetUserPass1(@RequestParam("mobile") Long mobile, HttpServletRequest request,
			HttpServletResponse response) {

		if (mobile == null) {
			throw new RRException(ErrorCode.PLEASE_INPUT_MOBILE);
		}
		int code = RandomUtils.nextInt(1000, 9999);
		request.getSession().setAttribute(FORGETVERIFICATIONCODE, code);
		request.getSession().setMaxInactiveInterval(180);// 验证码180秒有效期
		Map<String, Object> map = Maps.newHashMap();
		map.put("mobile", mobile);
		map.put("verificationCode", code);
		map.put("expireTime", 180);
		return R.ok(map);
	}

	/**
	 * 用户忘记密码
	 * 
	 * @param userId
	 * @param form
	 * @return
	 */
	@PostMapping("forgetUserPass2")
	@ApiOperation("忘记密码2(校验验证码)")
	public R forgetUserPass2(@RequestParam("forgetVerificationCode") Integer forgetVerificationCode,
			@RequestParam("mobile") Long mobile, HttpServletRequest request) {

		if (forgetVerificationCode == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}

		Object verificationCode = request.getSession().getAttribute(FORGETVERIFICATIONCODE);

		if (verificationCode == null) {
			throw new RRException(ErrorCode.VERIFICATIONCODE_IS_EXPIRE);
		}

		if (forgetVerificationCode != Integer.parseInt(verificationCode.toString())) {
			throw new RRException(ErrorCode.VERIFICATIONCODE_IS_ERROR);
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("mobile", mobile);
		return R.ok(map);
	}

	/**
	 * 用户忘记密码
	 * 
	 * @param userId
	 * @param form
	 * @return
	 */
	@PostMapping("forgetUserPass3")
	@ApiOperation("忘记密码3(修改密码)")
	public R forgetUserPass3(@RequestBody ForgetPassForm form) {
		// 表单校验
		ValidatorUtils.validateEntity(form);

		if (!form.getNewUserPass1().equals(form.getNewUserPass2())) {
			throw new RRException(ErrorCode.NEW_USERPASS_DIFFERENCE);
		}
		String newUserPass = DigestUtils.sha256Hex(form.getNewUserPass1().trim());
		userService.updateUserPass(form.getMobile(), newUserPass);
		return R.ok();
	}

}
