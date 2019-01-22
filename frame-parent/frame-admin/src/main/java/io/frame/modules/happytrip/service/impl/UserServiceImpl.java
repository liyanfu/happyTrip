
package io.frame.modules.happytrip.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
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

import io.frame.common.enums.Constant;
import io.frame.common.enums.Constant.Numbers;
import io.frame.common.enums.Constant.Sort;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Recommend;
import io.frame.dao.entity.SysUser;
import io.frame.dao.entity.User;
import io.frame.dao.entity.UserExample;
import io.frame.dao.entity.Wallet;
import io.frame.dao.mapper.UserMapper;
import io.frame.modules.happytrip.service.RecommendService;
import io.frame.modules.happytrip.service.UserService;
import io.frame.modules.happytrip.service.WalletService;
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
	WalletService walletService;

	@Autowired
	RecommendService recommendService;

	@Transactional(readOnly = true)
	@Override
	public PageUtils<User> queryPage(User user) {

		String userName = null;
		if (!StringUtils.isEmpty(user.getUserName())) {
			userName = user.getUserName() + "%";
		}
		UserExample example = new UserExample();
		example.or().andUserMobileLikeIgnoreNull(userName).andStatusEqualToIgnoreNull(user.getStatus());
		example.or().andUserNameLikeIgnoreNull(userName).andStatusEqualToIgnoreNull(user.getStatus());
		PageHelper.startPage(user.getPageNumber(), user.getPageSize());
		example.setOrderByClause(User.FD_CREATETIME + Sort.DESC.getValue());

		try {
			Page<User> page = (Page<User>) userMapper.selectByExample(example);

			for (User bean : page.getResult()) {
				Map<String, Object> map = Maps.newHashMap();
				// 查询用户账户信息
				Wallet wallet = walletService.getInfoById(bean.getUserId());
				map.put("balance", wallet == null ? BigDecimal.ZERO : wallet.getBalance());
				Recommend recommend = recommendService.getRecommendById(bean.getUserId());
				// 今日推荐人数
				map.put("recomendNum", recommend == null ? 0 : recommend.getRecommendNumber());
				// 今日团队业绩
				map.put("teamAchievement", recommend == null ? 0 : recommend.getTeamAchievement());
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
	public User getInfoById(Long userId) {
		try {
			return userMapper.selectByPrimaryKey(userId);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Override
	public void save(User user) {
		UserExample example = new UserExample();
		example.or().andUserMobileEqualTo(user.getUserMobile());
		example.or().andAlipayMobileEqualTo(user.getAlipayMobile());
		// 校验是否存在相同账号
		int count = userMapper.countByExample(example);
		if (count > Numbers.ZERO.getValue()) {
			throw new RRException(ErrorCode.USER_MOBILE_EXIST);
		}
		SysUser sysUser = ShiroUtils.getUserEntity();

		User parentUser = null;
		// 校验是否创建的是下级用户
		if (user.getParentId() != null) {
			// 查询父级用户信息
			UserExample parentExample = new UserExample();
			parentExample.createCriteria().andUserIdEqualTo(user.getParentId())
					.andStatusEqualTo(Constant.Status.ONE.getValue());
			// 查询父级ID
			parentUser = userMapper.selectOneByExample(parentExample);
			if (parentUser == null) {
				throw new RRException(ErrorCode.RECOMMEND_MOBILE_NOT_EXIST);
			}

		}

		try {
			user.setCreateUser(sysUser.getUserName());
			user.setCreateTime(new Date());
			user.setStatus(Constant.Status.ONE.getValue());
			user.setRegisterType(Numbers.ONE.getValue());
			user.setUserPass(DigestUtils.sha256Hex(user.getUserPass().trim()));// 先加密
			// 修改用户组ID
			user.setUserLevel(parentUser == null ? 1 : parentUser.getUserLevel() + 1);
			user.setParentId(parentUser == null ? user.getUserId() : parentUser.getUserId());
			user.setGroupUserIds(parentUser == null ? null : parentUser.getGroupUserIds());
			// 新增用户
			userMapper.insertSelective(user);
			user.setParentId(parentUser == null ? user.getUserId() : parentUser.getUserId());
			user.setGroupUserIds(parentUser == null ? user.getUserId() + ","
					: parentUser.getGroupUserIds() + user.getUserId() + ",");
			userMapper.updateByPrimaryKeySelective(user);
			// 新增用户钱包
			walletService.createWallet(user.getUserId(), sysUser.getUserName());

			if (parentUser != null) {
				// 更新父级推荐表中记录
				recommendService.upsert(parentUser.getUserId(), 1, null);
			}

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

	@Transactional(readOnly = true)
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

	@Transactional(readOnly = true)
	@Override
	public String getUserNameById(Long userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		return user == null ? null : user.getUserName();
	}

}
