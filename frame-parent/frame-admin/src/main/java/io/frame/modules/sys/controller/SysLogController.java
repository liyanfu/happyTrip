
package io.frame.modules.sys.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.frame.common.utils.ExcelUtils;
import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Log;
import io.frame.modules.sys.excel.SysLogBean;
import io.frame.modules.sys.service.SysLogService;

/**
 * 系统日志
 * 
 * @author fury
 *
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("sys:log:list")
	public PageUtils<Log> list(Log log) {
		return sysLogService.queryPage(log);
	}

	/**
	 * 导出
	 */
	@RequestMapping("/export")
	@RequiresPermissions("sys:log:list")
	public void export(Log log, HttpServletResponse response) throws Exception {
		PageUtils<Log> page = sysLogService.queryPage(log);

		ExcelUtils.exportExcelToTarget(response, "系统日志", page.getList(), SysLogBean.class);
	}
}
