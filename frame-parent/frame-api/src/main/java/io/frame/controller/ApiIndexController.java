package io.frame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.frame.annotation.Login;
import io.frame.dao.entity.ProductType;
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
@Api(tags = "顶部tab页签接口")
public class ApiIndexController {

	@Autowired
	ProductTypeService productTypeService;

	@Login
	@GetMapping("getTopTabList")
	@ApiOperation("获取顶部Tab标签页")
	public List<ProductType> getTopTabList() {
		return productTypeService.getProductTypeList();
	}

}
