package io.frame.modules.happytrip.service;

import java.util.List;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Welfare;

/**
 * 福利
 * 
 * @author fury
 *
 */
public interface WelfareService {

	/**
	 * 福利规则类型列表
	 * 
	 * @param welfare
	 * @return
	 */
	List<Welfare> getWelfareList();

	/**
	 * 福利规则列表
	 * 
	 * @param welfare
	 * @return
	 */
	PageUtils<Welfare> queryPage(Welfare welfare);

	/**
	 * 根据ID获取福利规则信息
	 * 
	 * @param WelfareId
	 * @return
	 */
	Welfare getInfoById(Long welfareId);

	/**
	 * 新增福利规则
	 * 
	 * @param Welfare
	 */
	void save(Welfare welfare);

	/**
	 * 修改福利规则
	 * 
	 * @param Welfare
	 */
	void update(Welfare welfare);

	/**
	 * 删除商品
	 * 
	 * @param WelfareId
	 */
	void delete(Long welfareId);

	/**
	 * 修改状态
	 * 
	 * @param welfare
	 */
	void status(Welfare welfare);

}
