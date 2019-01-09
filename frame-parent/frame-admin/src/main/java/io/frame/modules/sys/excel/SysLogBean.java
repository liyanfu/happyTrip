package io.frame.modules.sys.excel;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 系统日志Excel Bean
 * 
 * @author fury
 *
 */
public class SysLogBean {
	@Excel(name = "ID")
	private Long logId;

	@Excel(name = "用户名")
	private String userName;

	@Excel(name = "用户操作")
	private String operation;

	@Excel(name = "请求方法")
	private String method;

	@Excel(name = "请求参数")
	private String params;

	@Excel(name = "执行时长(毫秒)")
	private Long time;

	@Excel(name = "IP地址")
	private String ip;

	@Excel(name = "来源", replace = { "前端_0", "后台_1" })
	private Integer sources;

	@Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSources() {
		return sources;
	}

	public void setSources(Integer sources) {
		this.sources = sources;
	}

}
