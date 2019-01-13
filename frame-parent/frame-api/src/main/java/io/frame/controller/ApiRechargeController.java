package io.frame.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.frame.annotation.Login;
import io.frame.common.utils.R;
import io.frame.exception.ErrorCode;
import io.frame.service.RechargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 充值
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "充值接口")
public class ApiRechargeController {

	@Autowired
	RechargeService rechargeService;

	@Login
	@PostMapping("rechargeSubmit")
	@ApiOperation(notes = "{msg:消息提示,code:请求状态,rechargeCode:用户转账时填写的随机码rechargeCode,qrCode:收款二维码图片}", value = "充值申请")
	public R rechargeSubmit(@ApiIgnore @RequestAttribute("userId") Long userId,
			@ApiParam(name = "money", value = "充值金额", required = true) @RequestParam("money") BigDecimal money) {
		if (money == null || money.compareTo(BigDecimal.ZERO) == -1) {
			return R.error(ErrorCode.RECHAGER_MONEY_IS_ERROR);
		}
		return R.ok(rechargeService.rechargeSubmit(userId, money));
	}

}
