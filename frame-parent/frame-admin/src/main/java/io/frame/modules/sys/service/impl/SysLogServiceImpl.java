
package io.frame.modules.sys.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import io.frame.common.enums.Constant.Sort;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Log;
import io.frame.dao.entity.LogExample;
import io.frame.dao.mapper.LogMapper;
import io.frame.modules.sys.service.SysLogService;

@Transactional
@Service
public class SysLogServiceImpl implements SysLogService {
	Logger logger = LoggerFactory.getLogger(SysLogServiceImpl.class);
	@Autowired
	LogMapper logMapper;

	@Transactional(readOnly = true)
	@Override
	public PageUtils<Log> queryPage(Log log) {

		LogExample example = new LogExample();
		example.or().andUserNameEqualToIgnoreNull(log.getUserName()).andSourcesEqualTo(log.getSources());
		example.setOrderByClause(Log.FD_CREATETIME + Sort.DESC.getValue());
		PageHelper.startPage(log.getPageNumber(), log.getPageSize());
		Page<Log> page = (Page<Log>) logMapper.selectByExample(example);
		return new PageUtils<Log>(page);
	}

	@Override
	public void insert(Log log) {
		try {
			logMapper.insertSelective(log);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}
}
