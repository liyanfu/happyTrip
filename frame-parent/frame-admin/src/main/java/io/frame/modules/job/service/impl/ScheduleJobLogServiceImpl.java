
package io.frame.modules.job.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import io.frame.common.utils.DateUtils;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.ScheduleJobLog;
import io.frame.dao.entity.ScheduleJobLogExample;
import io.frame.dao.mapper.ScheduleJobLogMapper;
import io.frame.modules.job.service.ScheduleJobLogService;

@Transactional
@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {

	@Autowired
	ScheduleJobLogMapper scheduleJobLogMapper;

	@Transactional(readOnly = true)
	@Override
	public PageUtils<ScheduleJobLog> queryPage(ScheduleJobLog scheduleJobLog) {
		ScheduleJobLogExample example = new ScheduleJobLogExample();
		ScheduleJobLogExample.Criteria cr = example.createCriteria();

		Date beginDate = scheduleJobLog.getBeginTime();
		Date endDate = scheduleJobLog.getEndTime();
		// 当天0点
		if (beginDate == null) {
			beginDate = DateUtils.parse(new Date(), "yyyy-MM-dd");
		}
		// 明天0点
		if (endDate == null) {
			endDate = DateUtils.addDateDays(DateUtils.parse(new Date(), "yyyy-MM-dd"), 1);
		}

		if (beginDate == endDate) {
			endDate = DateUtils.addDateDays(DateUtils.parse(new Date(), "yyyy-MM-dd"), 1);
		}

		if (scheduleJobLog.getJobId() != null) {
			cr.andJobIdEqualToIgnoreNull(scheduleJobLog.getJobId());
		}

		if (!StringUtils.isEmpty(scheduleJobLog.getBeanName())) {
			cr.andBeanNameLikeIgnoreNull(scheduleJobLog.getBeanName() + "%");
		}

		if (!StringUtils.isEmpty(scheduleJobLog.getMethodName())) {
			cr.andMethodNameLikeIgnoreNull(scheduleJobLog.getMethodName() + "%");
		}

		cr.andStatusEqualToIgnoreNull(scheduleJobLog.getStatus());
		example.setOrderByClause(SqlTools.orderByDescField(scheduleJobLog.FD_CREATETIME));
		PageHelper.startPage(scheduleJobLog.getPageNumber(), scheduleJobLog.getPageSize());
		Page<ScheduleJobLog> page = (Page<ScheduleJobLog>) scheduleJobLogMapper.selectByExample(example);
		return new PageUtils<ScheduleJobLog>(page);
	}

	@Transactional(readOnly = true)
	@Override
	public ScheduleJobLog selectById(Long logId) {
		return scheduleJobLogMapper.selectByPrimaryKey(logId);
	}

	@Override
	public int insert(ScheduleJobLog scheduleJobLog) {
		return scheduleJobLogMapper.insertSelective(scheduleJobLog);
	}

}
