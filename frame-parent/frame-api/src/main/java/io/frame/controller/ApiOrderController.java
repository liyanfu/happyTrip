package io.frame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.frame.annotation.Login;
import io.frame.dao.entity.Order;
import io.frame.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 商品列表
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "订单")
public class ApiOrderController {

	@Autowired
	OrderService orderService;

	@Login
	@GetMapping("getMyOrderList")
	@ApiOperation("专属车位")
	public List<Order> getMyOrderList(@ApiIgnore @RequestAttribute("userId") Long userId) {
		return orderService.getMyOrderList(userId);
	}

}
