
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
import io.frame.dao.entity.SysUserRole;
import io.frame.dao.entity.SysUserRoleExample;
import io.frame.dao.mapper.SysUserRoleMapper;
import io.frame.modules.sys.service.SysUserRoleService;

/**
 * 用户与角色对应关系
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {
	Logger logger = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);
	@Autowired
	SysUserRoleMapper sysUserRoleMapper;

	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {

		SysUserRoleExample example = new SysUserRoleExample();
		example.or().andUserIdEqualTo(userId);

		try {
			sysUserRoleMapper.deleteByExample(example);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

		if (roleIdList == null || roleIdList.size() == 0) {
			return;
		}

		// 保存用户与角色关系
		List<SysUserRole> list = new ArrayList<>(roleIdList.size());
		for (Long roleId : roleIdList) {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setUserId(userId);
			sysUserRole.setRoleId(roleId);
			list.add(sysUserRole);
		}
		try {
			sysUserRoleMapper.insertBatch(list);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		SysUserRoleExample example = new SysUserRoleExample();
		example.or().andUserIdEqualTo(userId);
		List<SysUserRole> list = sysUserRoleMapper.selectByExample(example);
		List<Long> ids = Lists.newArrayList();
		for (SysUserRole sysUserRole : list) {
			ids.add(sysUserRole.getRoleId());
		}
		return ids;
	}

	@Override
	public void deleteBatch(Long[] roleIds) {

		try {
			SysUserRoleExample example = new SysUserRoleExample();
			example.or().andRoleIdIn(Arrays.asList(roleIds));
			sysUserRoleMapper.deleteByExample(example);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}
}
