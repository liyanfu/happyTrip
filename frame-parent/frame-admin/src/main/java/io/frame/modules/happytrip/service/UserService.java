package io.frame.modules.happytrip.service;

import java.util.List;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.User;

/**
 * 用户
 * 
 * @author fury
 *
 */
public interface UserService {

	/**
	 * 查询用户集合
	 * 
	 * @param sysUser
	 * @return
	 */
	PageUtils<User> queryPage(User user);

	/**
	 * 根据ID获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	User getInfoById(Long userId);

	void save(User user);

	void update(User user);

	/**
	 * 根据用户名称模糊查询userIds
	 * 
	 * @param userName
	 * @return
	 */
	List<Long> getUserIdsByLikeName(String userName);

	/**
	 * 根据用户ID获取用户名称
	 * 
	 * @param userId
	 * @return
	 */
	String getUserNameById(Long userId);

}
