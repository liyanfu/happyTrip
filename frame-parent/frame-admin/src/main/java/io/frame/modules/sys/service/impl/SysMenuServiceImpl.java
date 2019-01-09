
package io.frame.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.frame.common.enums.Constant;
import io.frame.common.enums.Constant.Sort;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.dao.entity.SysMenu;
import io.frame.dao.entity.SysMenuExample;
import io.frame.dao.entity.SysRoleMenuExample;
import io.frame.dao.mapper.SysMenuMapper;
import io.frame.dao.mapper.SysRoleMenuMapper;
import io.frame.modules.sys.entity.NavMenuEntity;
import io.frame.modules.sys.service.SysMenuService;
import io.frame.modules.sys.service.SysRoleMenuService;
import io.frame.modules.sys.service.SysUserService;

@Transactional
@Service
public class SysMenuServiceImpl implements SysMenuService {

	Logger logger = LoggerFactory.getLogger(SysMenuServiceImpl.class);
	@Autowired
	SysUserService sysUserService;
	@Autowired
	SysRoleMenuService sysRoleMenuService;

	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Autowired
	SysRoleMenuMapper sysRoleMenuMapper;

	@Override
	public List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenu> menuList = queryListParentId(parentId);
		if (menuIdList == null) {
			return menuList;
		}

		List<SysMenu> userMenuList = new ArrayList<>();
		for (SysMenu menu : menuList) {
			if (menuIdList.contains(menu.getMenuId())) {
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysMenu> queryListParentId(Long parentId) {
		SysMenuExample example = new SysMenuExample();
		example.or().andParentIdEqualTo(parentId);
		example.setOrderByClause(SysMenu.FD_ORDERNUM + Sort.ASC.getValue());
		return sysMenuMapper.selectByExample(example);
	}

	@Override
	public List<SysMenu> queryNotButtonList() {
		SysMenuExample example = new SysMenuExample();
		example.or().andTypeNotEqualTo(2);
		example.setOrderByClause(SysMenu.FD_ORDERNUM + Sort.ASC.getValue());
		return sysMenuMapper.selectByExample(example);
	}

	@Override
	public List<NavMenuEntity> getNavMenuList(Long userId, int superFlag) {
		// 导航菜单ID列表
		List<Long> menuIdList = null;
		// 非系统管理员
		if (superFlag != Constant.SUPER_ADMIN) {
			menuIdList = sysUserService.queryAllMenuId(userId);
		}

		// 查询根菜单列表
		List<NavMenuEntity> navList = queryNavListParentId(0L, menuIdList);
		// 递归获取子菜单
		getMenuTreeList(navList, menuIdList);

		return navList;
	}

	@Override
	public void delete(Long menuId) {
		try {
			// 删除菜单
			sysMenuMapper.deleteByPrimaryKey(menuId);
			// 删除菜单与角色关联
			SysRoleMenuExample example = new SysRoleMenuExample();
			example.or().andMenuIdEqualTo(menuId);
			sysRoleMenuMapper.deleteByExample(example);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	/**
	 * 查询导航菜单列表
	 */
	private List<NavMenuEntity> queryNavListParentId(Long parentId, List<Long> menuIdList) {
		// 查询根菜单列表
		List<SysMenu> menuList = queryListParentId(parentId, menuIdList);

		List<NavMenuEntity> navList = new ArrayList<>(menuList.size());
		for (SysMenu menuEntity : menuList) {
			NavMenuEntity nav = new NavMenuEntity();
			nav.setId(menuEntity.getMenuId());
			nav.setPid(menuEntity.getParentId());
			nav.setIcon(menuEntity.getIcon());
			nav.setTitle(menuEntity.getName());
			nav.setUrl(menuEntity.getUrl());
			nav.setType(menuEntity.getType());
			nav.setSpread(menuEntity.getSpread() == 0 ? false : true);

			navList.add(nav);
		}
		return navList;
	}

	/**
	 * 递归
	 */
	private List<NavMenuEntity> getMenuTreeList(List<NavMenuEntity> navList, List<Long> menuIdList) {
		List<NavMenuEntity> subMenuList = new ArrayList<>();

		for (NavMenuEntity nav : navList) {
			// 目录
			if (nav.getType() == Constant.MenuType.CATALOG.getValue()) {
				nav.setChildren(getMenuTreeList(queryNavListParentId(nav.getId(), menuIdList), menuIdList));
			}
			subMenuList.add(nav);
		}

		return subMenuList;
	}

	@Override
	public List<SysMenu> getSysMenuList(SysMenu sysMenu) {
		SysMenuExample example = new SysMenuExample();
		example.setOrderByClause(SysMenu.FD_ORDERNUM + Sort.ASC.getValue());
		return sysMenuMapper.selectByExample(example);
	}

	@Override
	public SysMenu getSysMenuById(Long id) {
		SysMenuExample example = new SysMenuExample();
		example.or().andMenuIdEqualTo(id);
		return sysMenuMapper.selectOneByExample(example);
	}

	@Override
	public void insert(SysMenu menu) {
		try {
			sysMenuMapper.insertSelective(menu);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public void updateSysMenuById(SysMenu menu) {
		try {
			sysMenuMapper.updateByPrimaryKeySelective(menu);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}
}
