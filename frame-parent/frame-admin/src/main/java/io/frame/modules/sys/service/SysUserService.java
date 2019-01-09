
package io.frame.modules.sys.service;

import java.util.List;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.SysUser;

/**
 * 系统用户
 * 
 * @author fury
 *
 */
public interface SysUserService {

	PageUtils<SysUser> queryPage(SysUser sysUser);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 保存用户
	 */
	void save(SysUser user);

	/**
	 * 修改用户
	 */
	void update(SysUser user);

	/**
	 * 修改密码
	 * 
	 * @param userId
	 *            用户ID
	 * @param password
	 *            原密码
	 * @param newPassword
	 *            新密码
	 */
	void updatePassword(Long userId, String password, String newPassword);

	SysUser getSysUserById(Long userId);

	void updateSysUserById(SysUser user);

	void deleteBatchIds(List<Long> asList);
}
