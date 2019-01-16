package io.frame.modules.happytrip.service;

import java.math.BigDecimal;

import io.frame.dao.entity.Recommend;

/**
 * 推荐表接口
 * 
 * @author fury
 *
 */
public interface RecommendService {

	/**
	 * 获取用户今日推荐信息
	 * 
	 * @param userId
	 * @return
	 */
	Recommend getRecommendById(Long userId);

	/**
	 * 更新用户的推荐人数，和团队业绩
	 * 
	 * @param userId
	 * @param object
	 */
	void upsert(Long userId, BigDecimal money);

}
