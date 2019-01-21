
package io.frame.modules.happytrip.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.frame.common.annotation.SysLog;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.R;
import io.frame.dao.entity.Order;
import io.frame.modules.happytrip.service.OrderService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 订单
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/ht/order")
public class OrderController extends AbstractController {
	@Autowired
	private OrderService orderService;

	/**
	 * 所有订单列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ht:order:list")
	public PageUtils<Order> list(Order order) {
		return orderService.queryPage(order);
	}

	/**
	 * 订单商品信息
	 */
	@RequestMapping("/info/{orderId}")
	@RequiresPermissions("ht:order:info")
	public R info(@PathVariable("orderId") Long orderId) {
		Order order = orderService.getInfoById(orderId);
		return R.ok().put("order", order);
	}

	/**
	 * 修改订单
	 */
	@SysLog("修改订单")
	@RequestMapping("/update")
	@RequiresPermissions("ht:order:update")
	public R update(@RequestBody Order order) {
		orderService.update(order);
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@SysLog("修改订单状态")
	@RequestMapping("/status")
	@RequiresPermissions("ht:order:update")
	public R status(Long orderId, Integer status) {
		if (status == null || orderId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		Order order = new Order();
		order.setOrderId(orderId);
		order.setStatus(status);
		orderService.update(order);
		return R.ok();
	}

	/**
	 * 删除商品
	 */
	@SysLog("删除订单")
	@RequestMapping("/delete")
	@RequiresPermissions("ht:order:delete")
	public R delete(Long orderId) {
		if (orderId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		orderService.delete(orderId);
		return R.ok();
	}

}
