
package io.frame.modules.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.frame.dao.entity.SysUser;

/**
 * Controller公共组件
 * 
 * @author fury
 *
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SysUser getUser() {
		return (SysUser) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}

	protected int getSuperUser() {
		return getUser().getSuperFlag();
	}

	protected Long getDeptId() {
		return getUser().getDeptId();
	}
}
