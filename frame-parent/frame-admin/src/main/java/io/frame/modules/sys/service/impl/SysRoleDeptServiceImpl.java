
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
import io.frame.dao.entity.SysRoleDept;
import io.frame.dao.entity.SysRoleDeptExample;
import io.frame.dao.mapper.SysRoleDeptMapper;
import io.frame.modules.sys.service.SysRoleDeptService;

/**
 * 角色与部门对应关系
 * 
 * @author fury
 *
 */
@Service
public class SysRoleDeptServiceImpl implements SysRoleDeptService {
	Logger logger = LoggerFactory.getLogger(SysRoleDeptServiceImpl.class);
	@Autowired
	SysRoleDeptMapper sysRoleDeptMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
		// 先删除角色与部门关系
		deleteBatch(new Long[] { roleId });

		if (deptIdList.size() == 0) {
			return;
		}

		// 保存角色与菜单关系
		List<SysRoleDept> list = new ArrayList<>(deptIdList.size());
		for (Long deptId : deptIdList) {
			SysRoleDept SysRoleDept = new SysRoleDept();
			SysRoleDept.setDeptId(deptId);
			SysRoleDept.setRoleId(roleId);

			list.add(SysRoleDept);
		}
		try {
			sysRoleDeptMapper.insertBatch(list);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public List<Long> queryDeptIdList(Long[] roleIds) {

		SysRoleDeptExample example = new SysRoleDeptExample();
		example.or().andRoleIdIn(Arrays.asList(roleIds));
		List<SysRoleDept> list = sysRoleDeptMapper.selectByExample(example);
		List<Long> ids = Lists.newArrayList();
		for (SysRoleDept sysRoleDept : list) {
			ids.add(sysRoleDept.getDeptId());
		}
		return ids;
	}

	@Override
	public void deleteBatch(Long[] roleIds) {
		try {
			SysRoleDeptExample example = new SysRoleDeptExample();
			example.or().andRoleIdIn(Arrays.asList(roleIds));
			sysRoleDeptMapper.deleteByExample(example);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}
}
