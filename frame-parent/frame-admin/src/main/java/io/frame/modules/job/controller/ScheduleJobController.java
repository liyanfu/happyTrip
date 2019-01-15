
package io.frame.modules.job.controller;

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
import io.frame.common.validator.ValidatorUtils;
import io.frame.dao.entity.ScheduleJob;
import io.frame.modules.job.service.ScheduleJobService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 定时任务
 *
 * @author Fury
 *
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController extends AbstractController {
	@Autowired
	private ScheduleJobService scheduleJobService;

	/**
	 * 定时任务列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:schedule:list")
	public PageUtils<ScheduleJob> list(ScheduleJob scheduleJob) {
		return scheduleJobService.queryPage(scheduleJob);
	}

	/**
	 * 定时任务信息
	 */
	@RequestMapping("/info/{jobId}")
	@RequiresPermissions("sys:schedule:info")
	public R info(@PathVariable("jobId") Long jobId) {
		ScheduleJob schedule = scheduleJobService.selectById(jobId);
		return R.ok().put("schedule", schedule);
	}

	/**
	 * 保存定时任务
	 */
	@SysLog("保存定时任务")
	@RequestMapping("/save")
	@RequiresPermissions("sys:schedule:save")
	public R save(@RequestBody ScheduleJob scheduleJob) {
		ValidatorUtils.validateEntity(scheduleJob);
		scheduleJobService.save(scheduleJob);
		return R.ok();
	}

	/**
	 * 修改定时任务
	 */
	@SysLog("修改定时任务")
	@RequestMapping("/update")
	@RequiresPermissions("sys:schedule:update")
	public R update(@RequestBody ScheduleJob scheduleJob) {
		ValidatorUtils.validateEntity(scheduleJob);
		scheduleJobService.update(scheduleJob);
		return R.ok();
	}

	/**
	 * 删除定时任务
	 */
	@SysLog("删除定时任务")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:schedule:delete")
	public R delete(@RequestBody Long[] jobIds) {
		scheduleJobService.deleteBatch(jobIds);
		return R.ok();
	}

	/**
	 * 立即执行任务
	 */
	@SysLog("立即执行任务")
	@RequestMapping("/run")
	@RequiresPermissions("sys:schedule:run")
	public R run(@RequestBody Long[] jobIds) {
		scheduleJobService.run(jobIds);
		return R.ok();
	}

	/**
	 * 暂停定时任务
	 */
	@SysLog("暂停定时任务")
	@RequestMapping("/pause")
	@RequiresPermissions("sys:schedule:pause")
	public R pause(@RequestBody Long[] jobIds) {
		scheduleJobService.pause(jobIds);
		return R.ok();
	}

	/**
	 * 恢复定时任务
	 */
	@SysLog("恢复定时任务")
	@RequestMapping("/resume")
	@RequiresPermissions("sys:schedule:resume")
	public R resume(@RequestBody Long[] jobIds) {
		scheduleJobService.resume(jobIds);
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@SysLog("修改状态")
	@RequestMapping("/status")
	@RequiresPermissions("sys:schedule:update")
	public R status(Long jobId, Integer status) {
		if (jobId == null || status == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		ScheduleJob scheduleJob = new ScheduleJob();
		scheduleJob.setJobId(jobId);
		scheduleJob.setStatus(status);
		scheduleJobService.update(scheduleJob);
		return R.ok();
	}

}
