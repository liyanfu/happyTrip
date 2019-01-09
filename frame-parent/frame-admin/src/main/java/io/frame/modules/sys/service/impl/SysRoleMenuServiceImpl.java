
package io.frame.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.dao.entity.SysRoleMenu;
import io.frame.dao.entity.SysRoleMenuExample;
import io.frame.dao.mapper.SysRoleMenuMapper;
import io.frame.modules.sys.service.SysRoleMenuService;

/**
 * 角色与菜单对应关系
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
	Logger logger = LoggerFactory.getLogger(SysRoleMenuServiceImpl.class);

	@Autowired
	SysRoleMenuMapper sysRoleMenuMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		// 先删除角色与菜单关系
		deleteBatch(new Long[] { roleId });

		if (menuIdList.size() == 0) {
			return;
		}

		// 保存角色与菜单关系
		List<SysRoleMenu> list = new ArrayList<>(menuIdList.size());
		for (Long menuId : menuIdList) {
			SysRoleMenu SysRoleMenu = new SysRoleMenu();
			SysRoleMenu.setMenuId(menuId);
			SysRoleMenu.setRoleId(roleId);

			list.add(SysRoleMenu);
		}
		try {
			sysRoleMenuMapper.insertBatch(list);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

	@Override
	public List<Long> queryMenuIdList(Long roleId) {

		SysRoleMenuExample example = new SysRoleMenuExample();
		example.or().andRoleIdEqualTo(roleId);
		List<SysRoleMenu> list = sysRoleMenuMapper.selectByExample(example);
		List<Long> ids = Lists.newArrayList();
		for (SysRoleMenu sysRoleMenu : list) {
			ids.add(sysRoleMenu.getMenuId());
		}
		return ids;

	}

	@Override
	public void deleteBatch(Long[] roleIds) {

		try {
			SysRoleMenuExample example = new SysRoleMenuExample();
			example.or().andRoleIdIn(Arrays.asList(roleIds));
			sysRoleMenuMapper.deleteByExample(example);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

}
