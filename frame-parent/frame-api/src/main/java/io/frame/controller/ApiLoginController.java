package io.frame.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

import io.frame.annotation.Login;
import io.frame.common.exception.RRException;
import io.frame.common.utils.R;
import io.frame.common.validator.ValidatorUtils;
import io.frame.dao.entity.User;
import io.frame.exception.ErrorCode;
import io.frame.form.LoginForm;
import io.frame.service.TokenService;
import io.frame.service.UserService;
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
@Api(tags = "登录接口")
public class ApiLoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;

	@Autowired
	private Producer producer;

	@PostMapping("login")
	@ApiOperation("登录")
	public R login(HttpServletRequest request, @RequestBody LoginForm form) {
		// 表单校验
		ValidatorUtils.validateEntity(form);

		// 校验验证码
		Object verificationCode = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (StringUtils.isEmpty(verificationCode) || !form.getCaptcha().equals(verificationCode)) {
			throw new RRException(ErrorCode.VERIFICATIONCODE_IS_ERROR);
		}

		User user = new User();
		BeanUtils.copyProperties(form, user);
		// 用户登录
		Map<String, Object> map = userService.login(user);
		// 清空
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, null);
		return R.ok(map);
	}

	@Login
	@PostMapping("logout")
	@ApiOperation("退出")
	public R logout(@ApiIgnore @RequestAttribute("userId") Long userId) {
		tokenService.expireToken(userId);
		return R.ok();
	}

	@GetMapping("getCaptcha")
	@ApiOperation("验证码")
	public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg"); // 生成文字验证码
		String text = producer.createText(); // 生成图片验证码
		BufferedImage image = producer.createImage(text);
		HttpSession session = request.getSession();
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
		request.getSession().setMaxInactiveInterval(180);// 验证码180秒有效期
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
	}

}
