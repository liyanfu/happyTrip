package io.frame.modules.happytrip.service;

import java.math.BigDecimal;

import io.frame.common.enums.Constant.ChangeType;

/**
 * 统计报表接口
 * 
 * @author fury
 *
 */
public interface ReportService {

	/**
	 * 更新统计报表金额数据
	 * 
	 * @param userId
	 * @param money
	 * @param moneyFee
	 * @param changeType
	 */
	void upsert(Long userId, BigDecimal money, BigDecimal moneyFee, ChangeType changeType);

}
