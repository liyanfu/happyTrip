
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
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.R;
import io.frame.dao.entity.Welfare;
import io.frame.modules.happytrip.service.WelfareService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 福利
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/ht/welfare")
public class WelfareController extends AbstractController {
	@Autowired
	private WelfareService welfareService;

	/**
	 * 福利类型列表
	 */
	@RequestMapping("/getWelfareList")
	@RequiresPermissions("ht:welfare:list")
	public R getWelfareList(Welfare welfare) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("list", welfareService.getWelfareList());
		return R.ok(map);
	}

	/**
	 * 所有福利列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ht:welfare:list")
	public PageUtils<Welfare> list(Welfare welfare) {
		return welfareService.queryPage(welfare);
	}

	/**
	 * 福利福利信息
	 */
	@RequestMapping("/info/{welfareId}")
	@RequiresPermissions("ht:welfare:info")
	public R info(@PathVariable("welfareId") Long welfareId) {
		Welfare welfare = welfareService.getInfoById(welfareId);
		return R.ok().put("welfare", welfare);
	}

	/**
	 * 修改福利
	 */
	@SysLog("新增福利")
	@RequestMapping("/save")
	@RequiresPermissions("ht:welfare:save")
	public R save(@RequestBody Welfare welfare) {
		welfareService.save(welfare);
		return R.ok();
	}

	/**
	 * 修改福利
	 */
	@SysLog("修改福利信息")
	@RequestMapping("/update")
	@RequiresPermissions("ht:welfare:update")
	public R update(@RequestBody Welfare welfare) {
		welfareService.update(welfare);
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@SysLog("修改福利状态")
	@RequestMapping("/status")
	@RequiresPermissions("ht:welfare:update")
	public R status(Long welfareId, Integer status) {
		if (status == null || welfareId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		Welfare welfare = new Welfare();
		welfare.setWelfareId(welfareId);
		welfare.setStatus(status);
		welfareService.status(welfare);
		return R.ok();
	}

	/**
	 * 删除福利
	 */
	@SysLog("删除福利")
	@RequestMapping("/delete")
	@RequiresPermissions("ht:welfare:delete")
	public R delete(Long welfareId) {
		if (welfareId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		welfareService.delete(welfareId);
		return R.ok();
	}

}
