
package io.frame.modules.sys.service;

import java.util.List;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.SysRole;

/**
 * 角色
 * 
 * @author fury
 *
 */
public interface SysRoleService {

	PageUtils<SysRole> queryPage(SysRole sysRole);

	void save(SysRole role);

	void update(SysRole role);

	void deleteBatch(Long[] roleIds);

	List<SysRole> getSysRoleList(SysRole sysRole);

	SysRole getSysRoleById(Long roleId);

}
