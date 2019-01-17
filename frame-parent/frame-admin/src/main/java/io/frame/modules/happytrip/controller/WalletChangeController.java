
package io.frame.modules.happytrip.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.WalletChange;
import io.frame.modules.happytrip.service.WalletChangeService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 钱包
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/ht/walletChange")
public class WalletChangeController extends AbstractController {
	@Autowired
	private WalletChangeService walletChangeService;

	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ht:walletChange:list")
	public PageUtils<WalletChange> list(WalletChange walletChange) {
		return walletChangeService.queryPage(walletChange);
	}

}
