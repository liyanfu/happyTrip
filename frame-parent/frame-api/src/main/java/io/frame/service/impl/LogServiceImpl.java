
package io.frame.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.frame.common.exception.RRException;
import io.frame.dao.entity.Log;
import io.frame.dao.mapper.LogMapper;
import io.frame.exception.ErrorCode;
import io.frame.service.LogService;

/**
 * 系统日志
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class LogServiceImpl implements LogService {

	Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

	@Autowired
	LogMapper logMapper;

	@Override
	public void insertLog(Log sysLog) {
		try {
			logMapper.insertSelective(sysLog);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

}
