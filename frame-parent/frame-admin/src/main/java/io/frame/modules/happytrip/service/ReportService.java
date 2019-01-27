package io.frame.modules.happytrip.service;

import java.math.BigDecimal;

import io.frame.common.enums.Constant.ChangeType;
import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Report;

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

	/**
	 * 普通查询报表列表
	 * 
	 * @param report
	 * @return
	 */
	PageUtils<Report> queryPage(Report report);

	/**
	 * 报表信息
	 * 
	 * @param reportId
	 * @return
	 */
	Report getInfoById(Long reportId);

	/**
	 * 统计合计
	 * 
	 * @param report
	 */
	Report totals(Report report);

	/**
	 * 删除报表
	 * 
	 * @param reportId
	 */
	void delete(Long reportId);

	/**
	 * 我的团队
	 * 
	 * @param report
	 * @return
	 */
	PageUtils<Report> lookMyTeam(Report report);

	/**
	 * 我的直属下级
	 * 
	 * @param report
	 * @return
	 */
	PageUtils<Report> lookUnder(Report report);

	/**
	 * 获取用户充值总金额和提现总金额
	 * 
	 * @param userId
	 * @return
	 */
	Report getMoneyByUserId(Long userId);

}
