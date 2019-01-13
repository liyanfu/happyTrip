package io.frame.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import io.frame.annotation.Login;
import io.frame.common.enums.Constant;
import io.frame.common.utils.R;
import io.frame.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 客服,公司介绍
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "客服,公司介绍接口")
public class ApiCustomerController {

	@Autowired
	ConfigService configService;

	@Login
	@GetMapping("getCustomerInfo")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,customerInfo:客服二维码}", value = "客服信息")
	public R getCustomerInfo() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("customerInfo",
				configService.getConfigByKey(Constant.SystemKey.SYSTEM_CUSTOMER_SERVICE_IMG_KEY.getValue()));
		return R.ok(map);
	}

	@Login
	@GetMapping("getCompanyInfo")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,companyInfo:公司介绍}", value = "公司介绍")
	public R getCompanyInfo() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("companyInfo",
				configService.getConfigByKey(Constant.SystemKey.SYSTEM_COMPANY_INTRODUCE_KEY.getValue()));
		return R.ok(map);
	}

}
