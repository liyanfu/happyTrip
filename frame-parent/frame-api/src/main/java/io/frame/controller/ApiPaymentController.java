package io.frame.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import io.frame.annotation.Login;
import io.frame.common.utils.R;
import io.frame.service.Paymentervice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 支付方式
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "支付方式接口")
public class ApiPaymentController {

	@Autowired
	Paymentervice paymentervice;

	@Login
	@GetMapping("getPaymentList")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,paymentList:[{paymentId:支付方式ID,paymentKey:支付方式Key,paymentName:支付方式名称}]}", value = "支付方式")
	public R getPaymentList() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("paymentList", paymentervice.getPaymentList());
		return R.ok(map);
	}

}
