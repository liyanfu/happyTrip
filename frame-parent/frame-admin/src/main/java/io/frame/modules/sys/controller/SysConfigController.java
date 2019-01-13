
package io.frame.modules.sys.controller;

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
import io.frame.dao.entity.Config;
import io.frame.modules.sys.service.SysConfigService;

/**
 * 系统配置信息
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:config:list")
	public PageUtils<Config> list(Config config) {
		return sysConfigService.queryPage(config);
	}

	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public R info(@PathVariable("id") Long id) {
		Config config = sysConfigService.getConfigById(id);
		return R.ok().put("config", config);
	}

	/**
	 * 保存配置
	 */
	@SysLog("保存配置")
	@RequestMapping("/save")
	@RequiresPermissions("sys:config:save")
	public R save(@RequestBody Config config) {
		sysConfigService.save(config);
		return R.ok();
	}

	/**
	 * 修改配置
	 */
	@SysLog("修改配置")
	@RequestMapping("/update")
	@RequiresPermissions("sys:config:update")
	public R update(@RequestBody Config config) {
		sysConfigService.update(config);
		return R.ok();
	}

	/**
	 * 删除配置
	 */
	@SysLog("删除配置")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public R delete(@RequestBody Long[] ids) {
		sysConfigService.deleteBatch(ids);
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@SysLog("修改状态")
	@RequestMapping("/status")
	@RequiresPermissions("sys:config:update")
	public R status(Long configId, Integer status) {
		if (configId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		if (status == null) {
			throw new RRException("状态不能为空");
		}

		Config config = new Config();
		config.setConfigId(configId);
		// config.setConfigStatus(status);
		sysConfigService.update(config);

		return R.ok();
	}

}
