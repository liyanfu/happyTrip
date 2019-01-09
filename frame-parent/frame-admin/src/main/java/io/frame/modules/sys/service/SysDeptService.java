
package io.frame.modules.sys.service;

import java.util.List;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.SysDept;

/**
 * 部门管理
 * 
 * @author fury
 *
 */
public interface SysDeptService {

	PageUtils<SysDept> queryList(SysDept sysDept);

	List<SysDept> querySysDeptList(SysDept sysDept);

	/**
	 * 查询子部门ID列表
	 * 
	 * @param parentId
	 *            上级部门ID
	 */
	List<Long> queryDetpIdList(Long parentId);

	/**
	 * 获取子部门ID，用于数据过滤
	 */
	List<Long> getSubDeptIdList(Long deptId);

	/**
	 * 获取部门信息
	 * 
	 * @param deptId
	 * @return
	 */
	SysDept getDeptById(Long deptId);

	/**
	 * 新增部门
	 * 
	 * @param dept
	 */
	void insert(SysDept dept);

	/**
	 * 修改
	 * 
	 * @param dept
	 */
	void updateById(SysDept dept);

	/**
	 * 删除
	 * 
	 * @param deptId
	 */
	void deleteById(long deptId);

}
