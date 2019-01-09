
package io.frame.modules.sys.service.impl;

import java.util.ArrayList;
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
import io.frame.dao.entity.SysDeptExample;
import io.frame.dao.mapper.SysDeptMapper;
import io.frame.modules.sys.service.SysDeptService;

@Transactional
@Service
public class SysDeptServiceImpl implements SysDeptService {
	Logger logger = LoggerFactory.getLogger(SysDeptServiceImpl.class);
	@Autowired
	SysDeptMapper sysDeptMapper;

	@Override
	@DataFilter(subDept = true, user = false)
	public PageUtils<SysDept> queryList(SysDept sysDept) {

		SysDeptExample example = new SysDeptExample();
		PageHelper.startPage(sysDept.getPageNumber(), sysDept.getPageSize());
		Page<SysDept> page = (Page<SysDept>) sysDeptMapper.selectByExample(example);
		for (SysDept sysDeptEntity : page) {
			SysDept parentDeptEntity = this.getDeptById(sysDeptEntity.getParentId());
			if (parentDeptEntity != null) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("parentName", parentDeptEntity.getName());
				sysDeptEntity.setMap(map);
			}
		}
		return new PageUtils<SysDept>(page);
	}

	@Override
	public List<Long> queryDetpIdList(Long parentId) {
		SysDeptExample example = new SysDeptExample();
		example.or().andParentIdEqualTo(parentId);
		List<SysDept> list = sysDeptMapper.selectByExample(example);
		List<Long> ids = Lists.newArrayList();
		for (SysDept sysDept : list) {
			ids.add(sysDept.getDeptId());
		}

		return ids;
	}

	@Override
	public List<Long> getSubDeptIdList(Long deptId) {
		// 部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		// 获取子部门ID
		List<Long> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		return deptIdList;
	}

	/**
	 * 递归
	 */
	private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList) {
		for (Long deptId : subIdList) {
			List<Long> list = queryDetpIdList(deptId);
			if (list.size() > 0) {
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}

	@Override
	public SysDept getDeptById(Long deptId) {
		SysDeptExample example = new SysDeptExample();
		example.or().andDeptIdEqualTo(deptId);
		return sysDeptMapper.selectOneByExample(example);
	}

	@Override
	public void insert(SysDept dept) {
		try {
			sysDeptMapper.insertSelective(dept);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public void updateById(SysDept dept) {
		try {
			sysDeptMapper.updateByPrimaryKey(dept);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

	@Override
	public void deleteById(long deptId) {
		try {
			sysDeptMapper.deleteByPrimaryKey(deptId);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

	@Override
	public List<SysDept> querySysDeptList(SysDept sysDept) {
		SysDeptExample example = new SysDeptExample();
		List<SysDept> list = sysDeptMapper.selectByExample(example);
		for (SysDept sysDeptEntity : list) {
			SysDept parentDeptEntity = this.getDeptById(sysDeptEntity.getParentId());
			Map<String, Object> map = Maps.newHashMap();
			map.put("parentName", parentDeptEntity == null ? null : parentDeptEntity.getName());
			sysDeptEntity.setMap(map);
		}
		return list;
	}
}
