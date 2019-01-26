
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
import io.frame.dao.entity.GlobalRecord;
import io.frame.modules.happytrip.service.GlobalRecordService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 全球分红派发记录
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/ht/globalrecord")
public class GlobalRecordController extends AbstractController {
	@Autowired
	private GlobalRecordService globalRecordService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ht:globalrecord:list")
	public PageUtils<GlobalRecord> list(GlobalRecord globalrecord) {
		return globalRecordService.queryPage(globalrecord);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ht:globalrecord:info")
	public R info(@PathVariable("id") Long id) {
		GlobalRecord record = globalRecordService.getInfoById(id);
		return R.ok().put("record", record);
	}

	/**
	 * 修改
	 */
	@SysLog("发放分红")
	@RequestMapping("/grant")
	@RequiresPermissions("ht:globalrecord:grant")
	public R update(@RequestBody Long[] ids) {
		if (ids == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		globalRecordService.grant(ids);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("删除分红记录")
	@RequestMapping("/delete")
	@RequiresPermissions("ht:globalrecord:delete")
	public R delete(Long id) {
		if (id == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		globalRecordService.delete(id);
		return R.ok();
	}

}
