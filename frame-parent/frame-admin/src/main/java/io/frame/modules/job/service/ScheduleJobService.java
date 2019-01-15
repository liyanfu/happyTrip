
package io.frame.modules.job.service;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.ScheduleJob;

/**
 * 定时任务
 *
 * @author Fury
 *
 */
public interface ScheduleJobService {

	PageUtils<ScheduleJob> queryPage(ScheduleJob scheduleJob);

	/**
	 * 保存定时任务
	 */
	void save(ScheduleJob scheduleJob);

	/**
	 * 更新定时任务
	 */
	void update(ScheduleJob scheduleJob);

	/**
	 * 批量删除定时任务
	 */
	void deleteBatch(Long[] jobIds);

	/**
	 * 批量更新定时任务状态
	 */
	int updateBatch(Long[] jobIds, int status);

	/**
	 * 立即执行
	 */
	void run(Long[] jobIds);

	/**
	 * 暂停运行
	 */
	void pause(Long[] jobIds);

	/**
	 * 恢复运行
	 */
	void resume(Long[] jobIds);

	/**
	 * 根据ID查询定时任务
	 * 
	 * @param jobId
	 * @return
	 */
	ScheduleJob selectById(Long jobId);
}
