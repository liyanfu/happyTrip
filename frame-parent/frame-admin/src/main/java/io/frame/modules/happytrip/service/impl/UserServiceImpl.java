
package io.frame.modules.happytrip.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.frame.common.enums.Constant.Sort;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.SysUser;
import io.frame.dao.entity.User;
import io.frame.dao.entity.UserExample;
import io.frame.dao.entity.Wallet;
import io.frame.dao.mapper.UserMapper;
import io.frame.dao.mapper.WalletMapper;
import io.frame.modules.happytrip.service.UserService;
import io.frame.modules.sys.shiro.ShiroUtils;

/**
 * 注册用户
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	UserMapper userMapper;

	@Autowired
	WalletMapper walletMapper;

	@Transactional(readOnly = true)
	@Override
	public PageUtils<User> queryPage(User user) {

		String userName = null;
		if (!StringUtils.isEmpty(user.getUserName())) {
			userName = user.getUserName() + "%";
		}
		UserExample example = new UserExample();
		example.or().andUserNameLikeIgnoreNull(userName)//
				.andStatusEqualToIgnoreNull(user.getStatus());
		PageHelper.startPage(user.getPageNumber(), user.getPageSize());
		example.setOrderByClause(User.FD_CREATETIME + Sort.DESC.getValue());

		try {
			Page<User> page = (Page<User>) userMapper.selectByExample(example);
			// 查询用户账户信息
			for (User bean : page.getResult()) {
				Wallet wallet = walletMapper.selectByPrimaryKey(bean.getUserId());
				Map<String, Object> map = Maps.newHashMap();
				// map.put("goldCoin", wallet.getGoldCoin());
				bean.setMap(map);
			}
			return new PageUtils<User>(page);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Transactional(readOnly = true)
	@Override
	public User getUserById(Long userId) {
		try {
			return userMapper.selectByPrimaryKey(userId);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Override
	public void save(User user) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		user.setUpdateUser(sysUser.getUserName());
		user.setUpdateTime(new Date());
		try {
			userMapper.insertSelective(user);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public void update(User user) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		user.setUpdateUser(sysUser.getUserName());
		user.setUpdateTime(new Date());
		try {
			userMapper.updateByPrimaryKeySelective(user);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public List<Long> getUserIdsByLikeName(String userName) {
		if (!StringUtils.isEmpty(userName)) {
			userName = userName + "%";
		}
		List<String> showField = Lists.newArrayList();
		showField.add(User.FD_USERID);
		UserExample example = new UserExample();
		example.or().andUserNameLikeIgnoreNull(userName);
		List<User> userList = userMapper.selectByExampleShowField(showField, example);
		List<Long> userIds = Lists.newArrayList();
		if (!CollectionUtils.isEmpty(userList)) {
			for (User user : userList) {
				userIds.add(user.getUserId());
			}
		}
		return userIds;
	}

	@Override
	public String getUserNameById(Long userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		return user == null ? null : user.getUserName();
	}

}
