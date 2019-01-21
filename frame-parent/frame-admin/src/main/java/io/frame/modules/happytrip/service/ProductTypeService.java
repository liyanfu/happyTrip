package io.frame.modules.happytrip.service;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.ProductType;

/**
 * 商品类型接口
 * 
 * @author fury
 *
 */
public interface ProductTypeService {

	/**
	 * 获取商品类型
	 * 
	 * @param productType
	 * @return
	 */
	PageUtils<ProductType> queryPage(ProductType productType);

	/**
	 * 获取商品类型
	 * 
	 * @param productTypeId
	 * @return
	 */
	ProductType getInfoById(Long productTypeId);

	/**
	 * 获取商品类型名称
	 * 
	 * @param productTypeId
	 * @return
	 */
	String getProductTypeName(Long productTypeId);

	/**
	 * 新增商品类型
	 * 
	 * @param productType
	 */
	void save(ProductType productType);

	/**
	 * 修改
	 * 
	 * @param productType
	 */
	void update(ProductType productType);

	/**
	 * 删除
	 * 
	 * @param productTypeId
	 */
	void delete(Long productTypeId);

}
