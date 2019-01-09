
package io.frame.service;

import io.frame.dao.entity.Token;

/**
 * 用户Token
 * 
 * @author fury
 *
 */
public interface TokenService {

	/**
	 * 查询token
	 * 
	 * @param Token
	 * @return
	 */
	Token queryByToken(String Token);

	/**
	 * 生成Token
	 * 
	 * @param userId
	 *            用户ID
	 * @return 返回Token信息
	 */
	Token createToken(Long userId);

	/**
	 * 刷新Token
	 * 
	 * @param userId
	 *            用户ID
	 * @return 返回Token信息
	 */
	Token refreshToken(Long userId);

	/**
	 * 设置Token过期
	 * 
	 * @param userId
	 *            用户ID
	 */
	void expireToken(Long userId);

}
