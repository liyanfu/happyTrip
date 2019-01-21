
package io.frame.modules.happytrip.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import io.frame.common.annotation.SysLog;
import io.frame.common.utils.R;
import io.frame.dao.entity.WalletChange;
import io.frame.modules.happytrip.service.RechargeService;
import io.frame.modules.happytrip.service.WalletService;
import io.frame.modules.happytrip.service.WithdrawService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 钱包
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/ht/wallet")
public class WalletController extends AbstractController {
	@Autowired
	private WalletService walletService;

	@Autowired
	private RechargeService rechargeService;

	@Autowired
	private WithdrawService withdrawService;

	/**
	 * 钱包信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("ht:wallet:info")
	public R info(@PathVariable("userId") Long userId) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("wallet", walletService.getInfoById(userId));
		// 充值总金额
		map.put("rechargeTotalMoney", rechargeService.getRechargeTotalMoneyById(userId));
		// 提现总金额
		map.put("withdrawTotalMoney", withdrawService.getWithdrawTotalMoneyById(userId));
		return R.ok(map);
	}

	/**
	 * 人工充值
	 */
	@SysLog("人工充值")
	@RequestMapping("/recharge")
	@RequiresPermissions("ht:wallet:recharge")
	public R recharge(@RequestBody WalletChange walletChange) {
		walletService.recharge(walletChange);
		return R.ok();
	}

	@SysLog("人工扣款")
	@RequestMapping("/subtract")
	@RequiresPermissions("ht:wallet:subtract")
	public R subtract(@RequestBody WalletChange walletChange) {
		walletService.subtract(walletChange);
		return R.ok();
	}

}
