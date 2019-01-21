package io.frame.modules.happytrip.service;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Order;

/**
 * 订单
 * 
 * @author fury
 *
 */
public interface OrderService {

	/**
	 * 查询商品集合
	 * 
	 * @param sysOrder
	 * @return
	 */
	PageUtils<Order> queryPage(Order order);

	/**
	 * 根据ID获取商品信息
	 * 
	 * @param orderId
	 * @return
	 */
	Order getInfoById(Long orderId);

	/**
	 * 修改商品
	 * 
	 * @param order
	 */
	void update(Order order);

	/**
	 * 删除商品
	 * 
	 * @param orderId
	 */
	void delete(Long orderId);

}
