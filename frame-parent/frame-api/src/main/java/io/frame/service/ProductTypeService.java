
package io.frame.service;

import java.util.List;

import io.frame.dao.entity.ProductType;

/**
 * 商品列表
 * 
 * @author fury
 *
 */
public interface ProductTypeService {

	/**
	 * 获取Toptab标签
	 * 
	 * @param typeId
	 * @return
	 */
	public List<ProductType> getProductTypeList();

	/**
	 * 获取商品类型
	 * 
	 * @param productTypeId
	 * @return
	 */
	public ProductType getProductTypeById(Long productTypeId);
}
