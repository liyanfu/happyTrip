
package io.frame.modules.happytrip.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.frame.common.annotation.SysLog;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.R;
import io.frame.dao.entity.Report;
import io.frame.modules.happytrip.service.ReportService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 报表
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/ht/report")
public class ReportController extends AbstractController {
	@Autowired
	private ReportService reportService;

	/**
	 * 报表列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ht:report:list")
	public PageUtils<Report> list(Report report) {
		return reportService.queryPage(report);
	}

	/**
	 * 我的团队
	 */
	@RequestMapping("/lookMyTeam")
	@RequiresPermissions("ht:report:list")
	public PageUtils<Report> lookMyTeam(Report report) {
		return reportService.lookMyTeam(report);
	}

	/**
	 * 直属下级
	 */
	@RequestMapping("/lookUnder")
	@RequiresPermissions("ht:report:list")
	public PageUtils<Report> lookUnder(Report report) {
		return reportService.lookUnder(report);
	}

	/**
	 * 报表信息
	 */
	@RequestMapping("/info/{reportId}")
	@RequiresPermissions("ht:report:info")
	public R info(@PathVariable("reportId") Long reportId) {
		Report report = reportService.getInfoById(reportId);
		return R.ok().put("report", report);
	}

	/**
	 * 统计合计
	 */
	@RequestMapping("/totals")
	@RequiresPermissions("ht:report:info")
	public R totals(Report report) {
		reportService.totals(report);
		return R.ok().put("report", report);
	}

	/**
	 * 删除报表
	 */
	@SysLog("删除订单")
	@RequestMapping("/delete")
	@RequiresPermissions("ht:report:delete")
	public R delete(Long reportId) {
		if (reportId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		reportService.delete(reportId);
		return R.ok();
	}

}
