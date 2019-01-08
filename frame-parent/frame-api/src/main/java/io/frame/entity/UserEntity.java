package io.frame.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 用户表
 * 
 * @author fury
 * @email ${email}
 * @date ${datetime}
 */
@TableName("tb_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户Id
	 */
	@TableId
	private Long userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 手机号登录号
	 */
	private String userMobile;
	/**
	 * 登录密码
	 */
	private String password;
	/**
	 * 支付宝名称
	 */
	private String alipayName;
	/**
	 * 支付宝账号
	 */
	private String alipayMobile;
	/**
	 * 会员级别
	 */
	private Integer userLevel;
	/**
	 * 父级ID
	 */
	private Long parentId;
	/**
	 * 用户组ID,逗号隔开
	 */
	private String groupUserIds;
	/**
	 * 状态[0:禁用,1:正常]
	 */
	private Integer status;
	/**
	 * 推荐人手机号
	 */
	private String recommend;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 修改人
	 */
	private String updateUser;
	/**
	 * 注册类型[0:前端注册,1:后台添加]
	 */
	private Integer registerType;
	/**
	 * 推广码
	 */
	private Integer spreadCode;

	/**
	 * 设置：用户Id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取：用户Id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置：用户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取：用户名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置：手机号登录号
	 */
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	/**
	 * 获取：手机号登录号
	 */
	public String getUserMobile() {
		return userMobile;
	}

	/**
	 * 设置：登录密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取：登录密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置：支付宝名称
	 */
	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}

	/**
	 * 获取：支付宝名称
	 */
	public String getAlipayName() {
		return alipayName;
	}

	/**
	 * 设置：支付宝账号
	 */
	public void setAlipayMobile(String alipayMobile) {
		this.alipayMobile = alipayMobile;
	}

	/**
	 * 获取：支付宝账号
	 */
	public String getAlipayMobile() {
		return alipayMobile;
	}

	/**
	 * 设置：会员级别
	 */
	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	/**
	 * 获取：会员级别
	 */
	public Integer getUserLevel() {
		return userLevel;
	}

	/**
	 * 设置：父级ID
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取：父级ID
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * 设置：用户组ID,逗号隔开
	 */
	public void setGroupUserIds(String groupUserIds) {
		this.groupUserIds = groupUserIds;
	}

	/**
	 * 获取：用户组ID,逗号隔开
	 */
	public String getGroupUserIds() {
		return groupUserIds;
	}

	/**
	 * 设置：状态[0:禁用,1:正常]
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态[0:禁用,1:正常]
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置：推荐人手机号
	 */
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	/**
	 * 获取：推荐人手机号
	 */
	public String getRecommend() {
		return recommend;
	}

	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建人
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 获取：创建人
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取：修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置：修改人
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * 获取：修改人
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * 设置：注册类型[0:前端注册,1:后台添加]
	 */
	public void setRegisterType(Integer registerType) {
		this.registerType = registerType;
	}

	/**
	 * 获取：注册类型[0:前端注册,1:后台添加]
	 */
	public Integer getRegisterType() {
		return registerType;
	}

	/**
	 * 设置：推广码
	 */
	public void setSpreadCode(Integer spreadCode) {
		this.spreadCode = spreadCode;
	}

	/**
	 * 获取：推广码
	 */
	public Integer getSpreadCode() {
		return spreadCode;
	}
}
