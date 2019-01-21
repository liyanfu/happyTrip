
package io.frame.modules.happytrip.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.frame.common.annotation.SysLog;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.R;
import io.frame.dao.entity.Withdraw;
import io.frame.modules.happytrip.service.WithdrawService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 提现
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/ht/withdraw")
public class WithdrawController extends AbstractController {

	@Autowired
	private WithdrawService withdrawService;

	/**
	 * 提现列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ht:withdraw:list")
	public PageUtils<Withdraw> list(Withdraw withdraw) {
		return withdrawService.queryPage(withdraw);
	}

	/**
	 * 提现信息
	 */
	@RequestMapping("/info/{withdrawId}")
	@RequiresPermissions("ht:withdraw:info")
	public R info(@PathVariable("withdrawId") Long withdrawId) {
		Withdraw withdraw = withdrawService.getInfoById(withdrawId);
		return R.ok().put("withdraw", withdraw);
	}

	/**
	 * 修改提现
	 */
	@SysLog("修改提现")
	@RequestMapping("/update")
	@RequiresPermissions("ht:withdraw:update")
	public R update(@RequestBody Withdraw withdraw) {
		withdrawService.update(withdraw);
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@SysLog("修改提现状态")
	@RequestMapping("/status")
	@RequiresPermissions("ht:withdraw:update")
	public R status(Long withdrawId, Integer status) {
		if (status == null || withdrawId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		Withdraw withdraw = new Withdraw();
		withdraw.setWithdrawId(withdrawId);
		withdraw.setStatus(status);
		withdrawService.update(withdraw);
		return R.ok();
	}

	/**
	 * 删除 提现
	 */
	@SysLog("删除提现")
	@RequestMapping("/delete")
	@RequiresPermissions("ht:withdraw:delete")
	public R delete(Long withdrawId) {
		if (withdrawId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		withdrawService.delete(withdrawId);
		return R.ok();
	}
}
