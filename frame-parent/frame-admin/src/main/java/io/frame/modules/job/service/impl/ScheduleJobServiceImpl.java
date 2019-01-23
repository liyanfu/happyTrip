
package io.frame.modules.job.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import io.frame.common.enums.Constant;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.ScheduleJob;
import io.frame.dao.entity.ScheduleJobExample;
import io.frame.dao.mapper.ScheduleJobMapper;
import io.frame.modules.job.service.ScheduleJobService;
import io.frame.modules.job.utils.ScheduleUtils;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService {
	@Autowired
	private Scheduler scheduler;

	@Autowired
	private ScheduleJobMapper scheduleJobMapper;

	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init() {
		List<ScheduleJob> scheduleJobList = this.selectList(new ScheduleJob());
		for (ScheduleJob scheduleJob : scheduleJobList) {
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
			// 如果不存在，则创建
			if (cronTrigger == null) {
				ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			} else {
				ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
			}
		}
	}

	public List<ScheduleJob> selectList(ScheduleJob scheduleJob) {
		ScheduleJobExample example = new ScheduleJobExample();
		ScheduleJobExample.Criteria cr = example.createCriteria();
		cr.andStatusEqualToIgnoreNull(scheduleJob.getStatus());
		example.setOrderByClause(SqlTools.orderByDescField(scheduleJob.FD_CREATETIME));
		return scheduleJobMapper.selectByExample(example);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(ScheduleJob scheduleJob) {
		scheduleJob.setCreateTime(new Date());
		scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
		scheduleJobMapper.insertSelective(scheduleJob);
		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(ScheduleJob scheduleJob) {

//		ScheduleJob newScheduleJob = scheduleJobMapper.selectByPrimaryKey(scheduleJob.getJobId());
//		newScheduleJob.setStatus(scheduleJob.getStatus());
//		if (StringUtils.isNotEmpty(scheduleJob.getRemark())) {
//			newScheduleJob.setRemark(scheduleJob.getRemark());
//		}
//		if (StringUtils.isNotEmpty(scheduleJob.getCronExpression())) {
//			newScheduleJob.setCronExpression(scheduleJob.getCronExpression());
//		}

		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
		scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] jobIds) {
		for (Long jobId : jobIds) {
			ScheduleUtils.deleteScheduleJob(scheduler, jobId);
		}
		// 删除数据
		for (Long jobId : jobIds) {
			scheduleJobMapper.deleteByPrimaryKey(jobId);
		}
	}

	@Override
	public int updateBatch(Long[] jobIds, int status) {
		Map<String, Object> map = new HashMap<>();
		map.put("list", jobIds);
		map.put("status", status);
		ScheduleJob record = new ScheduleJob();
		record.setStatus(status);
		ScheduleJobExample example = new ScheduleJobExample();
		example.or().andJobIdIn(Arrays.asList(jobIds));
		return scheduleJobMapper.updateByExampleSelective(record, example);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void run(Long[] jobIds) {
		for (Long jobId : jobIds) {
			ScheduleUtils.run(scheduler, this.selectById(jobId));
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void pause(Long[] jobIds) {
		for (Long jobId : jobIds) {
			ScheduleUtils.pauseJob(scheduler, jobId);
		}

		updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void resume(Long[] jobIds) {
		for (Long jobId : jobIds) {
			ScheduleUtils.resumeJob(scheduler, jobId);
		}

		updateBatch(jobIds, Constant.ScheduleStatus.NORMAL.getValue());
	}

	@Transactional(readOnly = true)
	@Override
	public PageUtils<ScheduleJob> queryPage(ScheduleJob scheduleJob) {
		ScheduleJobExample example = new ScheduleJobExample();
		ScheduleJobExample.Criteria cr = example.createCriteria();
		cr.andStatusEqualToIgnoreNull(scheduleJob.getStatus());
		cr.andBeanNameEqualToIgnoreNull(scheduleJob.getBeanName());
		example.setOrderByClause(SqlTools.orderByDescField(scheduleJob.FD_CREATETIME));
		PageHelper.startPage(scheduleJob.getPageNumber(), scheduleJob.getPageSize());
		Page<ScheduleJob> page = (Page<ScheduleJob>) scheduleJobMapper.selectByExample(example);
		return new PageUtils<ScheduleJob>(page);
	}

	@Transactional(readOnly = true)
	@Override
	public ScheduleJob selectById(Long jobId) {
		return scheduleJobMapper.selectByPrimaryKey(jobId);
	}

}
