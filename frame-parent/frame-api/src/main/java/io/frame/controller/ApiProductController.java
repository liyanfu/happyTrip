package io.frame.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import io.frame.annotation.Login;
import io.frame.common.utils.R;
import io.frame.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 商品列表
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "商品接口")
public class ApiProductController {

	@Autowired
	ProductService productService;

	@Login
	@GetMapping("getProductList")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,productList:[{productId:商品Id,productName:商品名称,saleMoney:售卖价格,saleQuantity:售卖数量,saleVolumes:已卖数量,rebateMoney:每期返利金额,rebatePeriods:返利期数,rebateTotals:返利总金额,startRebateTime:购买几天后开始返利(-1为不限制),productImgurl:商品图片}]}", value = "我要租车")
	public R getProductList(
			@ApiParam(name = "typeId", value = "商品类别ID", required = true) @RequestParam("typeId") Long typeId) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("productList", productService.getProductList(typeId));
		return R.ok(map);
	}

}
