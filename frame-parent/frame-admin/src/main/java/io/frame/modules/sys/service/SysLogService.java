
package io.frame.modules.sys.service;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Log;

/**
 * 系统日志
 * 
 * @author fury
 *
 */
public interface SysLogService {

	/**
	 * 查询日志
	 * 
	 * @param log
	 * @return
	 */
	PageUtils<Log> queryPage(Log log);

	/**
	 * 新增操作日志
	 * 
	 * @param sysLog
	 */
	void insert(Log log);

}
