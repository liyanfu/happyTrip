package io.frame.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import io.frame.annotation.Login;
import io.frame.common.utils.R;
import io.frame.common.validator.ValidatorUtils;
import io.frame.exception.ErrorCode;
import io.frame.form.WithdrawForm;
import io.frame.service.WithdrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 提现
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "提现接口")
public class ApiWithdrawController {

	static Logger log = LoggerFactory.getLogger(ApiWithdrawController.class);

	@Autowired
	WithdrawService withdrawService;

	@Login
	@GetMapping("getWithdrawInfo")
	@ApiOperation(notes = "alipayMobile: 支付宝账号,balance: 钱包余额,userMobile: 登录账号,alipayName:支付宝名称,userName:用户昵称,withdrawFee: 手续费}", value = "提现时获取钱包信息")
	public R getWithdrawInfo(@ApiIgnore @RequestAttribute("userId") Long userId) {
		return R.ok(withdrawService.getWithdrawInfo(userId));
	}

	@Login
	@GetMapping("getWithdrawDetails")
	@ApiOperation(notes = "{msg:提示消息,code=状态码,withdrawDetailList:[{withdrawMoney:提现金额,withdrawFee:提现手续费,createTime:提现时间,status:提现状态}]}", value = "提现明细")
	public R getWithdrawDetails(@ApiIgnore @RequestAttribute("userId") Long userId) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("withdrawDetailList", withdrawService.getWithdrawList(userId));
		return R.ok(map);
	}

	@Login
	@PostMapping("withdrawSubmit")
	@ApiOperation(notes = "{msg:提示消息,code=状态码}", value = "提现申请")
	public R withdrawSubmit(@ApiIgnore @RequestAttribute("userId") Long userId, @RequestBody WithdrawForm form) {
		// 表单校验
		ValidatorUtils.validateEntity(form);
		if (form.getMoney() == null || form.getMoney().compareTo(BigDecimal.ZERO) == -1) {
			return R.error(ErrorCode.WITHDRAW_MONEY_IS_ERROR);
		}
		withdrawService.withdrawSubmit(userId, form.getMoney());
		return R.ok();
	}

}
