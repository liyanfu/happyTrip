
package io.frame.service;

import java.util.List;

import io.frame.dao.entity.Product;

/**
 * 商品列表
 * 
 * @author fury
 *
 */
public interface ProductService {

	/**
	 * 获取商品列表
	 * 
	 * @param type
	 * @return
	 */
	List<Product> getProductList(Long typeId);

	/**
	 * 获取商品信息
	 * 
	 * @param productId
	 * @return
	 */
	Product getProductById(Long productId);

	/**
	 * 减库存
	 * 
	 * @param productId
	 * @param quantityNum
	 */
	void reduceStock(Long productId, Integer quantityNum);
}
