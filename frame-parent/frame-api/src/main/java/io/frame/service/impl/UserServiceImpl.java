package io.frame.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.frame.annotation.SysLog;
import io.frame.common.enums.Constant.Status;
import io.frame.common.exception.RRException;
import io.frame.common.utils.HttpContextUtils;
import io.frame.common.utils.IPUtils;
import io.frame.common.utils.RedisUtils;
import io.frame.common.validator.Assert;
import io.frame.dao.entity.Token;
import io.frame.dao.entity.User;
import io.frame.dao.entity.UserExample;
import io.frame.dao.mapper.UserMapper;
import io.frame.exception.ErrorCode;
import io.frame.service.TokenService;
import io.frame.service.UserService;
import io.frame.utils.SessionUtils;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private TokenService tokenService;

	@Autowired
	UserMapper userMapper;

	@Autowired
	RedisUtils redisUtils;

	@Transactional(readOnly = true)
	@Override
	public User queryByUserId(Long userId) {
		try {
			return userMapper.selectByPrimaryKey(userId);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Transactional(readOnly = true)
	public User queryByUserName(String userName) {
		UserExample example = new UserExample();
		example.or().andUserNameEqualTo(userName);
		try {
			return userMapper.selectOneByExample(example);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}
	}

	@Transactional(readOnly = true)
	public User queryByNameOrMobile(String userName) {
		UserExample example = new UserExample();
		example.or().andUserNameEqualTo(userName);
		example.or().andUserMobileEqualTo(userName);
		try {
			return userMapper.selectOneByExample(example);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}
	}

	@SysLog("登录接口")
	@Override
	public Map<String, Object> login(User user) {
		User userBean = queryByNameOrMobile(user.getUserName());
		Assert.isNull(user, ErrorCode.USERNAME_OR_USERPASS_ERROR);

		// 用户名不存在
		if (userBean == null) {
			throw new RRException(ErrorCode.USERNAME_NOT_EXIST);
		}

		// 账号被禁用
		if (userBean.getStatus() == 0) {
			throw new RRException(ErrorCode.USERNAME_DISABLE);
		}

		// 密码错误
		if (!userBean.getUserPass().equals(DigestUtils.sha256Hex(user.getUserPass()))) {
			throw new RRException(ErrorCode.USERNAME_OR_USERPASS_ERROR);
		}

		Token tokenEntity = null;
		HttpServletRequest reuqest = HttpContextUtils.getHttpServletRequest();
		try {
			// 获取登录token
			tokenEntity = tokenService.createToken(userBean.getUserId());
			SessionUtils.setCurrentUser(reuqest, userBean);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

		try {
			// 更新上次登录时间和IP
			User updateUser = new User();
			updateUser.setUserId(userBean.getUserId());
			updateUser.setLastLoginIp(IPUtils.getIpAddr(reuqest));
			updateUser.setLastLoginTime(new Date());
			userMapper.updateByPrimaryKeySelective(updateUser);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

		Map<String, Object> map = new HashMap<>(2);
		map.put("token", tokenEntity.getToken());
		map.put("expire", tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());

		return map;
	}

	@SysLog("注册接口")
	@Override
	public Map<String, Object> register(User user) {

		UserExample example = new UserExample();
		example.or().andUserNameEqualTo(user.getUserName());
		example.or().andUserMobileEqualTo(user.getUserMobile());
		// 校验是否存在相同用户名
		int count = userMapper.countByExample(example);
		if (count > Status.ZERO.getValue()) {
			throw new RRException(ErrorCode.USERNAME_EXIST);
		}

		user.setStatus(Status.ONE.getValue());
		user.setCreateUser(user.getUserName());
		user.setUserLevel(Status.ONE.getValue());
		user.setCreateTime(new Date());

		Token token = null;
		try {
			// 新增用户
			userMapper.insertSelective(user);
			// 放session里,日志SysLog那用
			SessionUtils.setCurrentUser(HttpContextUtils.getHttpServletRequest(), user);
			// 新增用户账户
			// Account account = new Account();
			// account.setUserId(user.getUserId());
			// account.setCreateUser(user.getUserName());
			// accountService.createAccount(account);

			// 创建token 并标记为登录
			token = tokenService.createToken(user.getUserId());
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

		Map<String, Object> map = new HashMap<>(2);
		map.put("token", token.getToken());
		map.put("expire", token.getExpireTime().getTime() - System.currentTimeMillis());
		return map;
	}

	@SysLog("修改密码")
	@Override
	public void updateUserPass(Long userId, String oldUserPass, String newUserPass) {
		User user = userMapper.selectByPrimaryKey(userId);

		if (user == null) {
			throw new RRException(ErrorCode.USERNAME_NOT_EXIST);
		}

		if (!user.getUserPass().equals(oldUserPass)) {
			throw new RRException(ErrorCode.ORIGINAL_PASS_ERROR);
		}
		User updateUser = new User();
		updateUser.setUserId(user.getUserId());
		updateUser.setUserPass(newUserPass);
		updateUser.setUpdateUser(user.getUserName());
		updateUser.setUpdateTime(new Date());

		try {
			userMapper.updateByPrimaryKeySelective(updateUser);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Transactional(readOnly = true)
	@Override
	public String getUserNameById(Long userId) {
		List<String> showField = Lists.newArrayList();
		showField.add(User.FD_USERNAME);
		try {
			return userMapper.selectByPrimaryKeyShowField(showField, userId).getUserName();
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Override
	public void updateUserPass(Long mobile, String newUserPass) {

		User user = this.queryByNameOrMobile(String.valueOf(mobile));
		if (user == null) {
			throw new RRException(ErrorCode.USERNAME_NOT_EXIST);
		}
		User updateUser = new User();
		updateUser.setUserId(user.getUserId());
		updateUser.setUserPass(newUserPass);
		updateUser.setUpdateUser(user.getUserName());
		updateUser.setUpdateTime(new Date());
		try {
			userMapper.updateByPrimaryKeySelective(updateUser);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

}
