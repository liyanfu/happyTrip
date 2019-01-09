
package io.frame.modules.sys.service;

import java.util.List;

import io.frame.dao.entity.SysMenu;
import io.frame.modules.sys.entity.NavMenuEntity;

/**
 * 菜单管理
 * 
 * @author fury
 *
 */
public interface SysMenuService {

	/**
	 * 根据父菜单，查询子菜单
	 * 
	 * @param parentId
	 *            父菜单ID
	 * @param menuIdList
	 *            用户菜单ID
	 */
	List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList);

	/**
	 * 根据父菜单，查询子菜单
	 * 
	 * @param parentId
	 *            父菜单ID
	 */
	List<SysMenu> queryListParentId(Long parentId);

	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenu> queryNotButtonList();

	/**
	 * 获取导航菜单列表
	 */
	List<NavMenuEntity> getNavMenuList(Long userId, int superFlag);

	/**
	 * 删除
	 * 
	 * @param menuId
	 */
	void delete(Long menuId);

	/**
	 * 获取所有菜单列表
	 * 
	 * @param object
	 * @return
	 */
	List<SysMenu> getSysMenuList(SysMenu sysMenu);

	/**
	 * 根据Id获取菜单信息
	 * 
	 * @param parentId
	 * @return
	 */
	SysMenu getSysMenuById(Long id);

	/**
	 * 新增
	 * 
	 * @param menu
	 */
	void insert(SysMenu menu);

	/**
	 * 修改
	 * 
	 * @param menu
	 */
	void updateSysMenuById(SysMenu menu);
}
