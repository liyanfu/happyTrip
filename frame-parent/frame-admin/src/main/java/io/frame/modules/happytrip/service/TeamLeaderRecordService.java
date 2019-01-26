package io.frame.modules.happytrip.service;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.TeamleaderRecord;

/**
 * 团队领导奖派发记录
 * 
 * @author fury
 *
 */
public interface TeamLeaderRecordService {

	/**
	 * 列表集合
	 * 
	 * @param sysTeamleaderRecord
	 * @return
	 */
	PageUtils<TeamleaderRecord> queryPage(TeamleaderRecord order);

	/**
	 * 根据信息
	 * 
	 * @param orderId
	 * @return
	 */
	TeamleaderRecord getInfoById(Long orderId);

	/**
	 * 删除
	 * 
	 * @param orderId
	 */
	void delete(Long orderId);

	/**
	 * 发放分红
	 * 
	 * @param ids
	 */
	void grant(Long[] ids);

}
