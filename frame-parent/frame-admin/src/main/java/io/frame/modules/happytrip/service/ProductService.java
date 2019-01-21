package io.frame.modules.happytrip.service;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Product;

/**
 * 商品
 * 
 * @author fury
 *
 */
public interface ProductService {

	/**
	 * 查询商品集合
	 * 
	 * @param sysProduct
	 * @return
	 */
	PageUtils<Product> queryPage(Product Product);

	/**
	 * 根据ID获取商品信息
	 * 
	 * @param ProductId
	 * @return
	 */
	Product getInfoById(Long ProductId);

	/**
	 * 新增商品
	 * 
	 * @param product
	 */
	void save(Product product);

	/**
	 * 修改商品
	 * 
	 * @param Product
	 */
	void update(Product product);

	/**
	 * 删除商品
	 * 
	 * @param productId
	 */
	void delete(Long productId);

}
