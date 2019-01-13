package io.frame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.frame.annotation.Login;
import io.frame.common.utils.R;
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
@Api(tags = "收益,分红,奖励,福利接口")
public class ApiProfitController {

	@Autowired
	ProfitService profitService;

	@Login
	@GetMapping("getMyCarProfit")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,totalsProfitMoney:累计收益,bonusList:[{operatorName:福利名称,operatorMoney:收益金额,createTime:收益时间}", value = "汽车收益")
	public R getMyCarProfit(@ApiIgnore @RequestAttribute("userId") Long userId) {
		return R.ok(profitService.getMyCarProfit(userId));
	}

	@Login
	@GetMapping("getAllWelfare")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,totalsProfitMoney:累计收益,bonusList:[{operatorName:福利名称,operatorMoney:收益金额,createTime:收益时间}", value = "全民福利")
	public R getAllWelfare(@ApiIgnore @RequestAttribute("userId") Long userId) {
		return R.ok(profitService.getAllWelfare(userId));
	}

	@Login
	@GetMapping("getGlobalBonus")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,totalsProfitMoney:累计收益,welfareVoList:[{welfareName:奖励名称,remark:奖励规则,qualifiedCount:达标人数,bounsPool:奖金池,averageAllot:平均分配额}]bonusList:[{operatorName:福利名称,operatorMoney:收益金额,createTime:收益时间}", value = "全球分红")
	public R getGlobalBonus(@ApiIgnore @RequestAttribute("userId") Long userId) {
		return R.ok(profitService.getGlobalBonus(userId));
	}

	@Login
	@GetMapping("getLeaderBonus")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,totalsProfitMoney:累计收益,welfareVoList:[{welfareName:奖励名称,remark:奖励规则,qualifiedCount:达标人数,bounsPool:奖金池,averageAllot:平均分配额}]bonusList:[{operatorName:福利名称,operatorMoney:收益金额,createTime:收益时间}", value = "团队领导奖")
	public R getLeaderBonus(@ApiIgnore @RequestAttribute("userId") Long userId) {
		return R.ok(profitService.getLeaderBonus(userId));
	}

	@Login
	@GetMapping("getEspeciallyBonus")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,totalsProfitMoney:累计收益,welfareVoList:[{welfareName:奖励名称,remark:奖励规则,qualifiedCount:达标人数,bounsPool:奖金池,averageAllot:平均分配额}]bonusList:[{operatorName:福利名称,operatorMoney:收益金额,createTime:收益时间}", value = "特别贡献奖")
	public R getEspeciallyBonus(@ApiIgnore @RequestAttribute("userId") Long userId) {
		return R.ok(profitService.getEspeciallyBonus(userId));
	}

}
