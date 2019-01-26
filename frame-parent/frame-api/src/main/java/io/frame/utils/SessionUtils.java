package io.frame.utils;

import javax.servlet.http.HttpServletRequest;

import io.frame.common.exception.RRException;
import io.frame.common.utils.HttpContextUtils;
import io.frame.dao.entity.User;
import io.frame.exception.ErrorCode;

/**
 * 当前用户session管理
 * 
 * @author fury
 *
 */
public class SessionUtils {

	public static String USERINFO_KEY = "userInfo_key";

	public static void setCurrentUser(HttpServletRequest request, User user) {
		request.getSession().setAttribute(USERINFO_KEY, user);
	}

	public static User getCurrentUser(HttpServletRequest request) {
		if (request.getSession().getAttribute(USERINFO_KEY) != null) {

			User bean = (User) request.getSession().getAttribute(USERINFO_KEY);
			return bean;
		} else {
			throw new RRException(ErrorCode.TOKEN_IS_EXPIRE, 530);
		}
	}

	public static User getCurrentUser() {
		// 获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		if (request.getSession().getAttribute(USERINFO_KEY) != null) {
			User bean = (User) request.getSession().getAttribute(USERINFO_KEY);
			return bean;
		} else {
			throw new RRException(ErrorCode.TOKEN_IS_EXPIRE, 530);
		}
	}

	public static void removeCurrentUser(HttpServletRequest request) {
		request.getSession().removeAttribute(USERINFO_KEY);
	}

	public static boolean getSession() {
		// 获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		if (request.getSession().getAttribute(USERINFO_KEY) != null) {
			return true;
		}
		return false;
	}

}
