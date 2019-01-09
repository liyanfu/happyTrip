package io.frame.modules.sys.excel;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 系统用户Excel Bean
 * 
 * @author fury
 *
 */
public class SysUserBean {
	@Excel(name = "用户ID")
	private Long userId;

	@Excel(name = "用户名")
	private String userName;

	@Excel(name = "邮箱")
	private String email;

	@Excel(name = "手机号")
	private String mobile;

	@Excel(name = "部门ID")
	private Long deptId;

	@Excel(name = "部门名称")
	private String deptName;

	@Excel(name = "状态", replace = { "禁用_0", "正常_1" })
	private Integer status;

	@Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
