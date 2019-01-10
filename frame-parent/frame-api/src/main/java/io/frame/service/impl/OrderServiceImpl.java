package io.frame.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.frame.common.enums.Constant;
import io.frame.common.exception.RRException;
import io.frame.dao.entity.Order;
import io.frame.dao.entity.OrderExample;
import io.frame.dao.mapper.OrderMapper;
import io.frame.exception.ErrorCode;
import io.frame.service.OrderService;

/**
 * 订单列表
 * 
 * @author fury
 *
 */
@Transactional
@Service("orderService")
public class OrderServiceImpl implements OrderService {

	Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	OrderMapper orderMapper;

	@Transactional(readOnly = true)
	@Override
	public List<Order> getMyOrderList(Long userId) {
		List<String> showField = Lists.newArrayList();
		showField.add(Order.FD_PRODUCTID);
		showField.add(Order.FD_PRODUCTNAME);
		OrderExample example = new OrderExample();
		example.createCriteria().andStatusEqualTo(Constant.Status.ONE.getValue());// 收益中的

		List<Order> list = Lists.newArrayList();
		try {
			List<Order> orderList = orderMapper.selectByExampleShowField(showField, example);
			if (!CollectionUtils.isEmpty(orderList)) {
				Map<String, Integer> map = Maps.newHashMap();
				for (Order order : orderList) {
					if (!map.containsKey(order.getProductName())) {
						map.put(order.getProductName(), order.getBuyQuantity());
					} else {
						Integer num = map.get(order.getProductName());
						map.put(order.getProductName(), num + order.getBuyQuantity());
					}
				}

				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					Order order = new Order();
					order.setProductName(entry.getKey());
					order.setBuyQuantity(entry.getValue());
					list.add(order);
				}
			}
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

		return list;
	}

}
