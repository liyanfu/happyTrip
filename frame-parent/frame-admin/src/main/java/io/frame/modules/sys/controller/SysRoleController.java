
package io.frame.modules.sys.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import io.frame.common.annotation.SysLog;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.R;
import io.frame.common.validator.ValidatorUtils;
import io.frame.dao.entity.SysRole;
import io.frame.modules.sys.service.SysRoleDeptService;
import io.frame.modules.sys.service.SysRoleMenuService;
import io.frame.modules.sys.service.SysRoleService;

/**
 * 角色管理
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;

	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	public PageUtils<SysRole> list(SysRole sysRole) {
		return sysRoleService.queryPage(sysRole);
	}

	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	public R select(SysRole sysRole) {
		List<SysRole> list = sysRoleService.getSysRoleList(sysRole);

		return R.ok().put("list", list);
	}

	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public R info(@PathVariable("roleId") Long roleId) {
		SysRole role = sysRoleService.getSysRoleById(roleId);
		Map<String, Object> map = Maps.newHashMap();
		map.put("deptName", null);

		// 查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		map.put("menuIdList", menuIdList);

		// 查询角色对应的部门
		List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(new Long[] { roleId });
		map.put("deptIdList", deptIdList);
		role.setMap(map);

		return R.ok().put("role", role);
	}

	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@RequestMapping("/save")
	@RequiresPermissions("sys:role:save")
	public R save(@RequestBody SysRole role) {

		sysRoleService.save(role);

		return R.ok();
	}

	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@RequestMapping("/update")
	@RequiresPermissions("sys:role:update")
	public R update(@RequestBody SysRole role) {
		ValidatorUtils.validateEntity(role);

		sysRoleService.update(role);

		return R.ok();
	}

	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public R delete(@RequestBody Long[] roleIds) {
		sysRoleService.deleteBatch(roleIds);

		return R.ok();
	}
}
