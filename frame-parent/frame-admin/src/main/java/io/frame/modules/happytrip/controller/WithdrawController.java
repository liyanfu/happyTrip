
package io.frame.modules.happytrip.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.frame.common.utils.PageUtils;
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
}
