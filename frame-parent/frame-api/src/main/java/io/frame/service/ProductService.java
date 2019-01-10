
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
	public List<Product> getProductList(Integer typeId);
}
