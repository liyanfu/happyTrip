package io.frame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.frame.annotation.Login;
import io.frame.common.exception.RRException;
import io.frame.dao.entity.Order;
import io.frame.entity.OrderVo;
import io.frame.exception.ErrorCode;
import io.frame.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

	@Login
	@GetMapping("getMyBuyOrderList")
	@ApiOperation("购买记录")
	public List<OrderVo> getMyBuyOrderList(@ApiIgnore @RequestAttribute("userId") Long userId,
			@ApiParam(name = "类型", value = "topTabId") @RequestParam("typeId") Long typeId) {
		if (typeId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		return orderService.getMyBuyOrderList(userId, typeId);
	}

}
