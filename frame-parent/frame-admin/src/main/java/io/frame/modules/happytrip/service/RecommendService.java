package io.frame.modules.happytrip.service;

import java.math.BigDecimal;

import io.frame.common.utils.PageUtils;
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
	void upsert(Long userId, Integer num, BigDecimal money);

	/**
	 * 推荐列表
	 * 
	 * @param recommend
	 * @return
	 */
	PageUtils<Recommend> queryPage(Recommend recommend);

	/**
	 * 获取推荐信息
	 * 
	 * @param recommendId
	 * @return
	 */
	Recommend getInfoById(Long recommendId);

	/**
	 * 保存
	 * 
	 * @param recommend
	 */
	void save(Recommend recommend);

	/**
	 * 修改推荐
	 * 
	 * @param recommend
	 */
	void update(Recommend recommend);

	/**
	 * 删除推荐
	 * 
	 * @param recommendId
	 */
	void delete(Long recommendId);

}
