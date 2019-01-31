
package io.frame.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
	List<Order> getMyOrderList(Long userId);

	/**
	 * 购买记录(订单记录)
	 * 
	 * @param userId
	 * @return
	 */
	List<OrderVo> getMyBuyOrderList(Long userId, Long typeId);

	/**
	 * 购买记录(订单记录)
	 * 
	 * @param userId
	 * @return
	 */
	int getMyBuyOrderListCount(Long userId);

	/**
	 * 查询收益中的所有订单总价格
	 * 
	 * 
	 * @return
	 */
	BigDecimal getProfitOrderMoney();

	/**
	 * 创建订单,购买
	 * 
	 * @param userId
	 * @param productId
	 * @param paymentKey
	 * @return
	 */
	Map<String, Object> payOrder(Long userId, Long orderId, Long productId, String paymentKey);

	/**
	 * 上传订单转账付款凭证
	 * 
	 * @param orderId
	 * @param url
	 */
	void update(Long orderId, String url);
}
