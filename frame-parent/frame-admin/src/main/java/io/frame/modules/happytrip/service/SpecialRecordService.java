package io.frame.modules.happytrip.service;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.SpecialRecord;

/**
 * 特别贡献奖派发记录
 * 
 * @author fury
 *
 */
public interface SpecialRecordService {

	/**
	 * 列表集合
	 * 
	 * @param sysSpecialRecord
	 * @return
	 */
	PageUtils<SpecialRecord> queryPage(SpecialRecord order);

	/**
	 * 根据信息
	 * 
	 * @param orderId
	 * @return
	 */
	SpecialRecord getInfoById(Long orderId);

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
