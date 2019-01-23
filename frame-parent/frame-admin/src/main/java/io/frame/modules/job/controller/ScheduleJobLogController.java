
package io.frame.modules.job.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.frame.common.utils.PageUtils;
import io.frame.common.utils.R;
import io.frame.dao.entity.ScheduleJobLog;
import io.frame.modules.job.service.ScheduleJobLogService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 定时任务日志
 *
 * @author Fury
 *
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController extends AbstractController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;

	/**
	 * 定时任务日志列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:schedulelog:list")
	public PageUtils<ScheduleJobLog> list(ScheduleJobLog scheduleJobLog) {
		return scheduleJobLogService.queryPage(scheduleJobLog);
	}

	/**
	 * 定时任务日志信息
	 */
	@RequestMapping("/info/{logId}")
	@RequiresPermissions("sys:schedulelog:info")
	public R info(@PathVariable("logId") Long logId) {
		return R.ok().put("log", scheduleJobLogService.selectById(logId));
	}
}
