
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
import io.frame.dao.entity.Recommend;
import io.frame.modules.happytrip.service.RecommendService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 推荐
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/ht/recommend")
public class RecommendController extends AbstractController {
	@Autowired
	private RecommendService recommendService;

	/**
	 * 所有推荐列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ht:recommend:list")
	public PageUtils<Recommend> list(Recommend recommend) {
		return recommendService.queryPage(recommend);
	}

	/**
	 * 推荐信息
	 */
	@RequestMapping("/info/{recommendId}")
	@RequiresPermissions("ht:recommend:info")
	public R info(@PathVariable("recommendId") Long recommendId) {
		return R.ok().put("recommend", recommendService.getInfoById(recommendId));
	}

	/**
	 * 新增推荐
	 */
	@SysLog("新增推荐")
	@RequestMapping("/save")
	@RequiresPermissions("ht:recommend:save")
	public R save(@RequestBody Recommend recommend) {
		recommendService.save(recommend);
		return R.ok();
	}

	/**
	 * 修改推荐
	 */
	@SysLog("修改推荐")
	@RequestMapping("/update")
	@RequiresPermissions("ht:recommend:update")
	public R update(@RequestBody Recommend recommend) {
		recommendService.update(recommend);
		return R.ok();
	}

	/**
	 * 删除推荐
	 */
	@SysLog("删除推荐")
	@RequestMapping("/delete")
	@RequiresPermissions("ht:recommend:delete")
	public R delete(Long recommendId) {
		if (recommendId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		recommendService.delete(recommendId);
		return R.ok();
	}

}
