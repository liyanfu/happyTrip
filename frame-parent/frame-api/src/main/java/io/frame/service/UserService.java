
package io.frame.service;

import java.util.Map;

import io.frame.dao.entity.User;
import io.frame.entity.MyInfoVo;

/**
 * 用户
 * 
 * @author fury
 *
 */
public interface UserService {

	/**
	 * 用户注册
	 * 
	 * @return
	 */
	Map<String, Object> register(User user);

	/**
	 * 根据用户Id查询用户信息
	 * 
	 * @param userName
	 * @return
	 */
	User queryByUserId(Long userId);

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	Map<String, Object> login(User user);

	/**
	 * 修改用户密码
	 * 
	 * @param userId
	 * @param oldUserPass
	 * @param newUserPass
	 */
	void updateUserPass(Long userId, String oldUserPass, String newUserPass);

	/**
	 * 根据Id查询出用户名
	 * 
	 * @param userId
	 * @return
	 */
	String getUserNameById(Long userId);

	/**
	 * 忘记密码
	 * 
	 * @param mobile
	 * @param newUserPass
	 */
	void updateUserPass(Long mobile, String newUserPass);

	/**
	 * 获取我的信息(包括团队基本信息)
	 * 
	 * @param userId
	 * @return
	 */
	MyInfoVo getMyInfo(Long userId);

}
