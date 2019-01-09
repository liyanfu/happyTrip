
package io.frame.modules.happytrip.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.frame.common.annotation.SysLog;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.R;
import io.frame.dao.entity.User;
import io.frame.modules.happytrip.service.UserService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 注册用户
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/vsball/user")
public class UserController extends AbstractController {
	@Autowired
	private UserService userService;

	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("vsball:user:list")
	public PageUtils<User> list(User user) {
		return userService.queryPage(user);
	}

	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("vsball:user:info")
	public R info(@PathVariable("userId") Long userId) {
		User user = userService.getUserById(userId);
		return R.ok().put("user", user);
	}

	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("vsball:user:save")
	public R save(@RequestBody User user) {
		userService.save(user);
		return R.ok();
	}

	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("vsball:user:update")
	public R update(@RequestBody User user) {
		userService.update(user);
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@SysLog("修改状态")
	@RequestMapping("/status")
	@RequiresPermissions("vsball:user:update")
	public R status(Long userId, Integer status) {
		if (userId == null) {
			throw new RRException("用户ID不能为空");
		}
		if (status == null) {
			throw new RRException("状态不能为空");
		}
		User user = new User();
		user.setUserId(userId);
		user.setStatus(status);
		userService.update(user);
		return R.ok();
	}

}
