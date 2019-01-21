package io.frame.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import io.frame.annotation.Login;
import io.frame.common.utils.R;
import io.frame.service.ProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * topTab页标签
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "商品类别接口")
public class ApiIndexController {

	@Autowired
	ProductTypeService productTypeService;

	@Login
	@GetMapping("getTopTabList")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,productTypeList:[{productTypeId:商品类别ID,productTypeName:商品类别名称}]}", value = "获取顶部Tab标签页")
	public R getTopTabList() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("productTypeList", productTypeService.getProductTypeList());
		return R.ok(map);
	}

}
