
package io.frame.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.frame.annotation.Login;
import io.frame.common.exception.RRException;
import io.frame.dao.entity.Token;
import io.frame.exception.ErrorCode;
import io.frame.service.TokenService;
import io.frame.service.UserService;
import io.frame.utils.SessionUtils;

/**
 * 权限(Token)验证
 * 
 * @author fury
 *
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserService userService;

	public static final String USER_KEY = "userId";

	public static final String TOKEN = "token";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Login annotation;
		if (handler instanceof HandlerMethod) {
			annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
		} else {
			return true;
		}

		if (annotation == null) {
			return true;
		}

		// 从header中获取token
		String token = request.getHeader(TOKEN);
		// 如果header中不存在token，则从参数中获取token
		if (StringUtils.isBlank(token)) {
			token = request.getParameter(TOKEN);
		}

		// token为空
		if (StringUtils.isBlank(token)) {
			throw new RRException(ErrorCode.TOKEN_NOT_NULL, 530);
		}

		// 从缓存中查询token信息
		Token tokenEntity = tokenService.queryByToken(token);
		if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
			throw new RRException(ErrorCode.TOKEN_IS_EXPIRE, 530);
		}

		// 设置userId到request里，后续根据userId，获取用户信息
		request.setAttribute(USER_KEY, tokenEntity.getUserId());
		if (!SessionUtils.getSession()) {// 解决由于系统更新,导致的登录超时
			request.getSession().setAttribute(SessionUtils.USERINFO_KEY,
					userService.getUserById(tokenEntity.getUserId()));
		}

		return true;
	}
}
