
package io.frame.service.impl;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.frame.annotation.SysLog;
import io.frame.common.exception.RRException;
import io.frame.dao.entity.Token;
import io.frame.dao.entity.TokenExample;
import io.frame.dao.mapper.TokenMapper;
import io.frame.exception.ErrorCode;
import io.frame.service.TokenService;

@Transactional
@Service
public class TokenServiceImpl implements TokenService {

	Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
	/**
	 * 12小时后过期
	 */
	private final static int EXPIRE = 3600 * 12;

	@Autowired
	TokenMapper tokenMapper;

	@Transactional(readOnly = true)
	@Override
	public Token queryByToken(String token) {
		TokenExample example = new TokenExample();
		example.or().andTokenEqualTo(token);
		try {
			return tokenMapper.selectOneByExample(example);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}
	}

	@Override
	public Token createToken(Long userId) {
		// 当前时间
		Date now = new Date();
		// 过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		// 生成token
		String tokenStr = generateToken();

		// 保存或更新用户token
		Token token = new Token();
		token.setUserId(userId);
		token.setToken(tokenStr);
		token.setUpdateTime(now);
		token.setExpireTime(expireTime);

		try {
			tokenMapper.upsert(token);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
		return token;
	}

	@Override
	public Token refreshToken(Long userId) {
		// 当前时间
		Date now = new Date();
		// 过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		// 保存或更新用户token
		Token token = new Token();
		token.setUserId(userId);
		token.setUpdateTime(now);
		token.setExpireTime(expireTime);
		try {
			tokenMapper.updateByPrimaryKeySelective(token);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

		return token;
	}

	@SysLog("登出接口")
	@Override
	public void expireToken(Long userId) {
		Date now = new Date();
		Token token = new Token();
		token.setUserId(userId);
		token.setUpdateTime(now);
		token.setExpireTime(now);
		try {
			tokenMapper.updateByPrimaryKeySelective(token);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

	private String generateToken() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
