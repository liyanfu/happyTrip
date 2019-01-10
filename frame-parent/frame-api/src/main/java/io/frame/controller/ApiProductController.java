package io.frame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.frame.annotation.Login;
import io.frame.dao.entity.Product;
import io.frame.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 商品列表
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "商品")
public class ApiProductController {

	@Autowired
	ProductService productService;

	@Login
	@GetMapping("getProductList")
	@ApiOperation("我要租车,typeId")
	public List<Product> getProductList(@RequestParam("typeId") Integer typeId) {
		return productService.getProductList(typeId);
	}

}
