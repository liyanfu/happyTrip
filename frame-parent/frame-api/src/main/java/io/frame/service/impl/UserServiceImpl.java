package io.frame.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.frame.annotation.SysLog;
import io.frame.common.enums.Constant.Numbers;
import io.frame.common.enums.Constant.Status;
import io.frame.common.exception.RRException;
import io.frame.common.utils.HttpContextUtils;
import io.frame.common.utils.IPUtils;
import io.frame.common.utils.RedisUtils;
import io.frame.common.validator.Assert;
import io.frame.dao.entity.Token;
import io.frame.dao.entity.User;
import io.frame.dao.entity.UserExample;
import io.frame.dao.entity.Wallet;
import io.frame.dao.mapper.UserMapper;
import io.frame.entity.MyInfoVo;
import io.frame.entity.MyTeamsVo;
import io.frame.exception.ErrorCode;
import io.frame.service.OrderService;
import io.frame.service.RecommendService;
import io.frame.service.TokenService;
import io.frame.service.UserService;
import io.frame.service.WalletService;
import io.frame.utils.SessionUtils;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private TokenService tokenService;

	@Autowired
	UserMapper userMapper;

	@Autowired
	WalletService walletService;

	@Autowired
	RecommendService recommendService;

	@Autowired
	OrderService orderService;
	@Autowired
	RedisUtils redisUtils;

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

	@Transactional(readOnly = true)
	public User queryByMobile(String userMobile) {
		UserExample example = new UserExample();
		example.createCriteria().andUserMobileEqualTo(userMobile);
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
		User userBean = queryByMobile(user.getUserMobile());
		Assert.isNull(user, ErrorCode.USERNAME_OR_USERPASS_ERROR);

		// 用户名不存在
		if (userBean == null) {
			throw new RRException(ErrorCode.USERNAME_NOT_EXIST);
		}

		// 账号被禁用
		if (userBean.getStatus() == Status.ZERO.getValue()) {
			throw new RRException(ErrorCode.USERNAME_DISABLE);
		}

		// 密码错误
		if (!userBean.getUserPass().equals(user.getUserPass())) {
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
		example.createCriteria().andUserMobileEqualTo(user.getUserMobile());
		// 校验是否存在相同账号
		int count = userMapper.countByExample(example);
		if (count > Status.ZERO.getValue()) {
			throw new RRException(ErrorCode.USER_MOBILE_EXIST);
		}

		UserExample parentExample = new UserExample();
		parentExample.createCriteria().andUserMobileEqualTo(user.getRecommendMobile());

		// 查询父级ID
		User parentUser = userMapper.selectOneByExample(parentExample);
		if (parentUser == null) {
			throw new RRException(ErrorCode.RECOMMEND_MOBILE_NOT_EXIST);
		}
		user.setUserLevel(parentUser.getUserLevel() + 1);
		user.setParentId(parentUser.getUserId());
		user.setStatus(Status.ONE.getValue());
		user.setCreateUser(user.getUserName());
		user.setRegisterType(Numbers.ZERO.getValue());
		user.setCreateTime(new Date());

		// Token token = null;
		try {
			// 新增用户
			userMapper.insertSelective(user);
			// 修改用户组ID
			user.setGroupUserIds(parentUser.getGroupUserIds() + user.getUserId() + ",");
			userMapper.updateByPrimaryKey(user);
			// 放session里,日志SysLog那用
			SessionUtils.setCurrentUser(HttpContextUtils.getHttpServletRequest(), user);
			// 新增用户钱包
			walletService.createWallet(user.getUserId(), user.getUserName());
			// 更新父级推荐表中记录
			recommendService.upsert(parentUser.getUserId(), 1, null);
			// 创建token 并标记为登录
			// token = tokenService.createToken(user.getUserId());
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

		Map<String, Object> map = new HashMap<>(2);
		// map.put("token", token.getToken());
		// map.put("expire", token.getExpireTime().getTime() -
		// System.currentTimeMillis());
		return map;
	}

	@SysLog("修改登录密码")
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

	@SysLog("忘记密码")
	@Override
	public void updateUserPass(Long mobile, String newUserPass) {

		User user = this.queryByMobile(String.valueOf(mobile));
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

	@Transactional(readOnly = true)
	@Override
	public MyInfoVo getMyInfo(Long userId) {
		MyInfoVo info = new MyInfoVo();
		User user = SessionUtils.getCurrentUser();
		info.setUserName(user.getUserName());
		// 我的余额,我的总收益
		Wallet wallet = walletService.getWallet(userId);
		info.setWalletBalance(wallet.getBalance());
		info.setTotalProfit(wallet.getProfitMoney());
		// 我的团队人数
		info.setTeamNum(this.getMyTeamNumByGroupIds(user.getGroupUserIds()));
		// 我的直推人数
		info.setRecommendNum(recommendService.getRecommendNumByParentId(userId, Boolean.FALSE));
		// 今日直推人数
		info.setTodayRecomendNum(recommendService.getRecommendNumByParentId(userId, Boolean.TRUE));
		// 团队总业绩
		info.setTeamTotalMoney(recommendService.getTeamAchievementByGroupIds(user.getGroupUserIds(), Boolean.FALSE));
		// 今日团队业绩
		info.setTodayTeamMoney(recommendService.getTeamAchievementByGroupIds(user.getGroupUserIds(), Boolean.TRUE));

		return info;
	}

	/**
	 * 我的团队人数,包括自己
	 * 
	 * @param userId
	 * @param groupUserIds
	 * @return
	 */
	private Integer getMyTeamNumByGroupIds(String groupUserIds) {
		UserExample example = new UserExample();
		example.createCriteria().andGroupUserIdsLike(groupUserIds + "%");
		return userMapper.countByExample(example);
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> getMyTeams(Long userId) {
		Map<String, Object> map = Maps.newHashMap();
		User currentUser = SessionUtils.getCurrentUser();
		// 团队人数
		map.put("teamNum", this.getMyTeamNumByGroupIds(currentUser.getGroupUserIds()));
		// 团队累计金额
		map.put("teamTotalMoney",
				recommendService.getTeamAchievementByGroupIds(currentUser.getGroupUserIds(), Boolean.FALSE));

		// 查询出我名下的团队会员
		List<MyTeamsVo> list = Lists.newArrayList();
		List<String> showField = Lists.newArrayList();
		showField.add(User.FD_USERID);
		showField.add(User.FD_USERNAME);
		showField.add(User.FD_USERLEVEL);
		showField.add(User.FD_GROUPUSERIDS);
		UserExample example = new UserExample();
		example.createCriteria().andGroupUserIdsLike(currentUser.getGroupUserIds() + "%");
		List<User> userList = userMapper.selectByExampleShowField(showField, example);
		if (!CollectionUtils.isEmpty(userList)) {
			for (User user : userList) {
				MyTeamsVo myTeamsVo = new MyTeamsVo();
				myTeamsVo.setCreateTime(user.getCreateTime());
				myTeamsVo.setUserName(user.getUserName());
				myTeamsVo.setUserLevel(user.getUserLevel());
				myTeamsVo.setIsConsume(orderService.getMyBuyOrderListCount(user.getUserId()) != 0 ? "是" : "否");
				myTeamsVo.setTeamsMoney(recommendService.getTeamAchievementByParentId(user.getUserId()));// 直属下级消费
				list.add(myTeamsVo);
			}
		}
		// 团队集合信息
		map.put("list", list);
		return map;
	}

}
