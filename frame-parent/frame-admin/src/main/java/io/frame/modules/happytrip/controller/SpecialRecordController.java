
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
import io.frame.dao.entity.SpecialRecord;
import io.frame.modules.happytrip.service.SpecialRecordService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 特别贡献奖派发记录
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/ht/specialrecord")
public class SpecialRecordController extends AbstractController {
	@Autowired
	private SpecialRecordService specialRecordService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ht:specialrecord:list")
	public PageUtils<SpecialRecord> list(SpecialRecord specialrecord) {
		return specialRecordService.queryPage(specialrecord);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ht:specialrecord:info")
	public R info(@PathVariable("id") Long id) {
		SpecialRecord record = specialRecordService.getInfoById(id);
		return R.ok().put("record", record);
	}

	/**
	 * 修改
	 */
	@SysLog("发放特别贡献奖")
	@RequestMapping("/grant")
	@RequiresPermissions("ht:specialrecord:grant")
	public R update(@RequestBody Long[] ids) {
		if (ids == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		specialRecordService.grant(ids);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("删除分红记录")
	@RequestMapping("/delete")
	@RequiresPermissions("ht:specialrecord:delete")
	public R delete(Long id) {
		if (id == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		specialRecordService.delete(id);
		return R.ok();
	}

}
