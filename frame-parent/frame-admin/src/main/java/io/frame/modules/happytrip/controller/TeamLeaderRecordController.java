
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
import io.frame.dao.entity.TeamleaderRecord;
import io.frame.modules.happytrip.service.TeamLeaderRecordService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 团队领导奖派发记录
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/ht/teamleaderrecord")
public class TeamLeaderRecordController extends AbstractController {
	@Autowired
	private TeamLeaderRecordService teamLeaderRecordService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ht:teamleaderrecord:list")
	public PageUtils<TeamleaderRecord> list(TeamleaderRecord teamleaderrecord) {
		return teamLeaderRecordService.queryPage(teamleaderrecord);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ht:teamleaderrecord:info")
	public R info(@PathVariable("id") Long id) {
		TeamleaderRecord record = teamLeaderRecordService.getInfoById(id);
		return R.ok().put("record", record);
	}

	/**
	 * 修改
	 */
	@SysLog("发放团队领导奖")
	@RequestMapping("/grant")
	@RequiresPermissions("ht:teamleaderrecord:grant")
	public R update(@RequestBody Long[] ids) {
		if (ids == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		teamLeaderRecordService.grant(ids);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("删除分红记录")
	@RequestMapping("/delete")
	@RequiresPermissions("ht:teamleaderrecord:delete")
	public R delete(Long id) {
		if (id == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		teamLeaderRecordService.delete(id);
		return R.ok();
	}

}
