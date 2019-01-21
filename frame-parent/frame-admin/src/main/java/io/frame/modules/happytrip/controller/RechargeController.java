
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
import io.frame.dao.entity.Recharge;
import io.frame.modules.happytrip.service.RechargeService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 充值
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/ht/recharge")
public class RechargeController extends AbstractController {

	@Autowired
	private RechargeService rechargeService;

	/**
	 * 充值列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ht:recharge:list")
	public PageUtils<Recharge> list(Recharge recharge) {
		return rechargeService.queryPage(recharge);
	}

	/**
	 * 充值信息
	 */
	@RequestMapping("/info/{rechargeId}")
	@RequiresPermissions("ht:recharge:info")
	public R info(@PathVariable("rechargeId") Long rechargeId) {
		Recharge recharge = rechargeService.getInfoById(rechargeId);
		return R.ok().put("recharge", recharge);
	}

	/**
	 * 修改充值
	 */
	@SysLog("修改充值")
	@RequestMapping("/update")
	@RequiresPermissions("ht:recharge:update")
	public R update(@RequestBody Recharge recharge) {
		rechargeService.update(recharge);
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@SysLog("修改充值状态")
	@RequestMapping("/status")
	@RequiresPermissions("ht:recharge:update")
	public R status(Long rechargeId, Integer status) {
		if (status == null || rechargeId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		Recharge recharge = new Recharge();
		recharge.setRechargeId(rechargeId);
		recharge.setStatus(status);
		rechargeService.update(recharge);
		return R.ok();
	}

	/**
	 * 删除 充值
	 */
	@SysLog("删除充值")
	@RequestMapping("/delete")
	@RequiresPermissions("ht:recharge:delete")
	public R delete(Long rechargeId) {
		if (rechargeId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		rechargeService.delete(rechargeId);
		return R.ok();
	}
}
