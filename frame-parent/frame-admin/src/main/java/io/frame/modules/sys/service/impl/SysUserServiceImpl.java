
package io.frame.modules.sys.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
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
import io.frame.dao.entity.SysRoleMenu;
import io.frame.dao.entity.SysRoleMenuExample;
import io.frame.dao.entity.SysUser;
import io.frame.dao.entity.SysUserExample;
import io.frame.dao.mapper.SysDeptMapper;
import io.frame.dao.mapper.SysRoleMenuMapper;
import io.frame.dao.mapper.SysUserMapper;
import io.frame.modules.sys.service.SysUserRoleService;
import io.frame.modules.sys.service.SysUserService;
import io.frame.modules.sys.shiro.ShiroUtils;

/**
 * 系统用户
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class SysUserServiceImpl implements SysUserService {
	Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysDeptMapper sysDeptMapper;

	@Transactional(readOnly = true)
	@Override
	public List<Long> queryAllMenuId(Long userId) {

		List<Long> roleList = sysUserRoleService.queryRoleIdList(userId);
		SysRoleMenuExample example = new SysRoleMenuExample();
		example.or().andRoleIdInIgnoreNull(roleList);
		List<SysRoleMenu> menuList = sysRoleMenuMapper.selectByExample(example);
		List<Long> ids = Lists.newArrayList();

		for (SysRoleMenu sysRoleMenu : menuList) {
			ids.add(sysRoleMenu.getMenuId());
		}
		return ids;
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public PageUtils<SysUser> queryPage(SysUser sysUser) {

		SysUserExample example = new SysUserExample();
		example.or().andUserNameLikeIgnoreNull(sysUser.getUserName());
		PageHelper.startPage(sysUser.getPageNumber(), sysUser.getPageSize());
		Page<SysUser> page = (Page<SysUser>) sysUserMapper.selectByExample(example);
		for (SysUser sysUserEntity : page) {
			SysDept sysDept = sysDeptMapper.selectByPrimaryKey(sysUserEntity.getDeptId());
			Map<String, Object> map = Maps.newHashMap();
			map.put("deptName", sysDept.getName());
			sysUserEntity.setMap(map);
		}

		return new PageUtils<SysUser>(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(SysUser user) {
		user.setCreateTime(new Date());
		// sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setUserPass(ShiroUtils.sha256(user.getUserPass(), user.getSalt()));

		try {
			sysUserMapper.insertSelective(user);

			// 保存用户与角色关系
			sysUserRoleService.saveOrUpdate(user.getUserId(), this.getIds(user.getMap().get("roleIdList")));
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
	public void update(SysUser user) {
		if (StringUtils.isBlank(user.getUserPass())) {
			user.setUserPass(null);
		} else {
			user.setUserPass(ShiroUtils.sha256(user.getUserPass(), user.getSalt()));
		}

		try {
			sysUserMapper.updateByPrimaryKeySelective(user);
			sysUserRoleService.saveOrUpdate(user.getUserId(), this.getIds(user.getMap().get("roleIdList")));
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public void updatePassword(Long userId, String password, String newPassword) {
		SysUser userEntity = new SysUser();
		userEntity.setUserPass(newPassword);
		SysUserExample example = new SysUserExample();
		example.or().andUserIdEqualTo(userId).andUserPassEqualTo(password);

		SysUser user = sysUserMapper.selectOneByExample(example);
		if (user == null) {
			throw new RRException(ErrorCode.USERNAME_OR_USERPASS_ERROR);
		}
		try {
			sysUserMapper.updateByExampleSelective(userEntity, example);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public SysUser getSysUserById(Long userId) {
		SysUserExample example = new SysUserExample();
		example.or().andUserIdEqualTo(userId);
		return sysUserMapper.selectOneByExample(example);
	}

	@Override
	public void updateSysUserById(SysUser user) {
		try {
			sysUserMapper.updateByPrimaryKeySelective(user);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public void deleteBatchIds(List<Long> asList) {
		SysUserExample example = new SysUserExample();
		example.or().andUserIdIn(asList);
		try {
			sysUserMapper.deleteByExample(example);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

}
