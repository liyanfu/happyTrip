
package io.frame.service;

import io.frame.dao.entity.Log;

/**
 * 系统日志
 * @author fury
 *
 */
public interface LogService {

	/**
	 * 新增操作日志
	 * @param sysLog
	 */
	void insertLog(Log sysLog);

}
