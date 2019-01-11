package io.frame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.frame.annotation.Login;
import io.frame.common.utils.R;
import io.frame.dao.entity.WalletChange;
import io.frame.service.ProfitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 收益信息
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "收益,分红,奖励")
public class ApiProfitController {

	@Autowired
	ProfitService profitService;

	@Login
	@GetMapping("getMyCarProfit")
	@ApiOperation("汽车收益")
	public List<WalletChange> getMyCarProfit(@ApiIgnore @RequestAttribute("userId") Long userId) {
		return profitService.getMyCarProfit(userId);
	}

	@Login
	@GetMapping("getGlobalBonus")
	@ApiOperation("全球分红")
	public R getGlobalBonus(@ApiIgnore @RequestAttribute("userId") Long userId) {
		return R.ok(profitService.getGlobalBonus(userId));
	}

}
