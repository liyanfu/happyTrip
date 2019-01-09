
package io.frame.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.frame.common.annotation.SysLog;
import io.frame.common.exception.RRException;
import io.frame.common.utils.ExcelUtils;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.R;
import io.frame.common.validator.Assert;
import io.frame.common.validator.ValidatorUtils;
import io.frame.common.validator.group.UpdateGroup;
import io.frame.dao.entity.SysUser;
import io.frame.modules.sys.excel.SysUserBean;
import io.frame.modules.sys.service.SysUserRoleService;
import io.frame.modules.sys.service.SysUserService;
import io.frame.modules.sys.shiro.ShiroUtils;

/**
 * 系统用户
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public PageUtils<SysUser> list(SysUser sysUser) {
		return sysUserService.queryPage(sysUser);
	}

	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info() {
		return R.ok().put("user", getUser());
	}

	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/password")
	public R password(String password, String newPassword) {
		Assert.isBlank(newPassword, "新密码不为能空");
		// 原密码
		password = ShiroUtils.sha256(password, getUser().getSalt());
		// 新密码
		newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());
		// 更新密码
		sysUserService.updatePassword(getUserId(), password, newPassword);
		return R.ok();
	}

	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId) {
		SysUser user = sysUserService.getSysUserById(userId);

		// 获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		Map<String, Object> map = Maps.newHashMap();
		map.put("roleIdList", roleIdList);
		map.put("deptName", null);
		user.setMap(map);

		return R.ok().put("user", user);
	}

	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUser user) {
		sysUserService.save(user);
		return R.ok();
	}

	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUser user) {
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		sysUserService.update(user);

		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@SysLog("修改状态")
	@RequestMapping("/status")
	@RequiresPermissions("sys:user:update")
	public R status(Long userId, Integer status) {
		if (userId == null) {
			throw new RRException("用户ID不能为空");
		}
		if (status == null) {
			throw new RRException("状态不能为空");
		}

		SysUser user = new SysUser();
		user.setUserId(userId);
		user.setStatus(status);
		sysUserService.updateSysUserById(user);

		return R.ok();
	}

	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds) {
		if (ArrayUtils.contains(userIds, 1L)) {
			return R.error("系统管理员不能删除");
		}

		if (ArrayUtils.contains(userIds, getUserId())) {
			return R.error("当前用户不能删除");
		}

		sysUserService.deleteBatchIds(Arrays.asList(userIds));

		return R.ok();
	}

	/**
	 * 导出
	 */
	@RequestMapping("/export")
	@RequiresPermissions("sys:user:export")
	public void export(SysUser sysUser, HttpServletResponse response) throws Exception {
		PageUtils<SysUser> page = sysUserService.queryPage(sysUser);

		List<SysUserBean> list = Lists.newArrayList();
		for (Object user : page.getList()) {
			SysUserBean userBean = new SysUserBean();
			SysUser newUser = (SysUser) user;
			BeanUtils.copyProperties(newUser, userBean);
			userBean.setDeptName(String.valueOf(newUser.getMap().get("deptName")));
			list.add(userBean);
		}

		ExcelUtils.exportExcelToTarget(response, "用户管理", list, SysUserBean.class);
	}
}
