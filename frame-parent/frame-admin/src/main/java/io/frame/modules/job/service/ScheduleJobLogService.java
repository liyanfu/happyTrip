
package io.frame.modules.job.service;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.ScheduleJobLog;

/**
 * 定时任务日志
 *
 * @author Fury
 *
 */
public interface ScheduleJobLogService {
	PageUtils<ScheduleJobLog> queryPage(ScheduleJobLog scheduleJobLog);

	/**
	 * 查询定时任务日志
	 * 
	 * @param logId
	 * @return
	 */
	ScheduleJobLog selectById(Long logId);

	/**
	 * 新增定时任务日志记录
	 * 
	 * @param logId
	 * @return
	 */
	int insert(ScheduleJobLog scheduleJobLog);
}
