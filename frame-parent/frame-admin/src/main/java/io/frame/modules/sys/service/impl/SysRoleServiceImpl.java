
package io.frame.modules.sys.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.frame.common.annotation.DataFilter;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.SysDept;
import io.frame.dao.entity.SysRole;
import io.frame.dao.entity.SysRoleExample;
import io.frame.dao.mapper.SysDeptMapper;
import io.frame.dao.mapper.SysRoleMapper;
import io.frame.modules.sys.service.SysRoleDeptService;
import io.frame.modules.sys.service.SysRoleMenuService;
import io.frame.modules.sys.service.SysRoleService;
import io.frame.modules.sys.service.SysUserRoleService;

/**
 * 角色
 * 
 * @author fury
 *
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
	Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysDeptMapper sysDeptMapper;

	@Override
	@DataFilter(subDept = true, user = false)
	public PageUtils<SysRole> queryPage(SysRole sysRole) {

		SysRoleExample example = new SysRoleExample();
		example.or().andRoleNameEqualToIgnoreNull(sysRole.getRoleName());
		PageHelper.startPage(sysRole.getPageNumber(), sysRole.getPageSize());
		Page<SysRole> page = (Page<SysRole>) sysRoleMapper.selectByExample(example);

		for (SysRole sysRole2 : page) {
			SysDept sysDept = sysDeptMapper.selectByPrimaryKey(sysRole2.getDeptId());
			Map<String, Object> map = Maps.newHashMap();
			map.put("deptName", sysDept == null ? "" : sysDept.getName());
			sysRole2.setMap(map);
		}

		return new PageUtils<SysRole>(page);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(SysRole role) {
		role.setCreateTime(new Date());

		try {
			sysRoleMapper.insertSelective(role);
			// 保存角色与菜单关系
			sysRoleMenuService.saveOrUpdate(role.getRoleId(), this.getIds(role.getMap().get("menuIdList")));
			// 保存角色与部门关系
			sysRoleDeptService.saveOrUpdate(role.getRoleId(), this.getIds(role.getMap().get("deptIdList")));
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@SuppressWarnings("unchecked")
	public List<Long> getIds(Object object) {
		List<Long> idList = Lists.newArrayList();
		if (object == null) {
			return idList;
		}
		List<String> list = (List<String>) object;
		for (String id : list) {
			idList.add(Long.parseLong(id));
		}
		return idList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysRole role) {

		try {
			sysRoleMapper.updateByPrimaryKeySelective(role);
			// 更新角色与菜单关系
			sysRoleMenuService.saveOrUpdate(role.getRoleId(), this.getIds(role.getMap().get("menuIdList")));
			// 保存角色与部门关系
			sysRoleDeptService.saveOrUpdate(role.getRoleId(), this.getIds(role.getMap().get("deptIdList")));
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] roleIds) {

		try {
			// 删除角色
			SysRoleExample example = new SysRoleExample();
			example.or().andRoleIdIn(Arrays.asList(roleIds));
			sysRoleMapper.deleteByExample(example);

			// 删除角色与菜单关联
			sysRoleMenuService.deleteBatch(roleIds);

			// 删除角色与部门关联
			sysRoleDeptService.deleteBatch(roleIds);

			// 删除角色与用户关联
			sysUserRoleService.deleteBatch(roleIds);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public List<SysRole> getSysRoleList(SysRole sysRole) {
		SysRoleExample example = new SysRoleExample();
		return sysRoleMapper.selectByExample(example);
	}

	@Override
	public SysRole getSysRoleById(Long roleId) {
		SysRoleExample example = new SysRoleExample();
		example.or().andRoleIdEqualTo(roleId);
		return sysRoleMapper.selectOneByExample(example);
	}

}
