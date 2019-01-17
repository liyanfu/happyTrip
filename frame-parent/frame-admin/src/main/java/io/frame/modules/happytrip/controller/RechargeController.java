
package io.frame.modules.happytrip.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.frame.common.utils.PageUtils;
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

}
