package io.frame.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import io.frame.annotation.Login;
import io.frame.common.exception.RRException;
import io.frame.common.utils.R;
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
@Api(tags = "订单接口")
public class ApiOrderController {

	@Autowired
	OrderService orderService;

	@Login
	@GetMapping("getMyOrderList")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,orderList:[{productName:商品名称,buyquantity:投资数量}]}", value = "专属车位")
	public R getMyOrderList(@ApiIgnore @RequestAttribute("userId") Long userId) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("orderList", orderService.getMyOrderList(userId));
		return R.ok(map);
	}

	@Login
	@GetMapping("getMyBuyOrderList")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,orderList:[{productName:商品名称,buyMoney:投资金额,status:订单状态,createTime:创建时间}]}", value = "购买记录")
	public R getMyBuyOrderList(@ApiIgnore @RequestAttribute("userId") Long userId,
			@ApiParam(name = "typeId", value = "商品类型ID", required = true) @RequestParam("typeId") Long typeId) {
		if (typeId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("orderList", orderService.getMyBuyOrderList(userId, typeId));
		return R.ok(map);
	}

	@Login
	@PostMapping("payOrder")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,qrCode:余额支付时为空,其余线下支付返回收款二维码,orderId:订单ID,randomCode:随机码（只有不是余额支付时才让用户线下转账填写）}", value = "下单购买")
	public R payOrder(@ApiIgnore @RequestAttribute("userId") Long userId,
			@ApiParam(name = "productId", value = "商品Id") @RequestParam("productId") Long productId,
			@ApiParam(name = "paymentId", value = "支付类型Id") @RequestParam("paymentId") Long paymentId) {
		if (productId == null || paymentId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		return R.ok(orderService.payOrder(userId, productId, paymentId));
	}

}
