package io.frame.modules.happytrip.service;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.GlobalRecord;

/**
 * 全球分红派发记录
 * 
 * @author fury
 *
 */
public interface GlobalRecordService {

	/**
	 * 列表集合
	 * 
	 * @param sysGlobalRecord
	 * @return
	 */
	PageUtils<GlobalRecord> queryPage(GlobalRecord order);

	/**
	 * 根据信息
	 * 
	 * @param orderId
	 * @return
	 */
	GlobalRecord getInfoById(Long orderId);

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
