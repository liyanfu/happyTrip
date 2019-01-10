
package io.frame.service;

import java.util.List;

import io.frame.dao.entity.Order;

/**
 * 订单列表
 * 
 * @author fury
 *
 */
public interface OrderService {

	/**
	 * 专属车位列表(收益中的商品)
	 * 
	 * @param userId
	 * @return
	 */
	public List<Order> getMyOrderList(Long userId);
}
