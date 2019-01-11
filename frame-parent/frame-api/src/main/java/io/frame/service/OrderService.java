
package io.frame.service;

import java.util.List;

import io.frame.dao.entity.Order;
import io.frame.entity.OrderVo;

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

	/**
	 * 购买记录(订单记录)
	 * 
	 * @param userId
	 * @return
	 */
	public List<OrderVo> getMyBuyOrderList(Long userId, Long typeId);

	/**
	 * 购买记录(订单记录)
	 * 
	 * @param userId
	 * @return
	 */
	public int getMyBuyOrderListCount(Long userId);
}
