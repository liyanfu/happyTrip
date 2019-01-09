package io.frame.dao.entity;

import io.frame.dao.base.BaseEntity;
import java.io.Serializable;
import java.util.Date;

public class SysRole extends BaseEntity implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role.roleId
     *
     * @mbggenerated
     */
    private Long roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role.roleName
     *
     * @mbggenerated
     */
    private String roleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role.deptId
     *
     * @mbggenerated
     */
    private Long deptId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role.createTime
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table s_sys_role
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role.roleId
     *
     * @mbggenerated
     */
    public static final String FD_ROLEID = "roleId";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role.roleName
     *
     * @mbggenerated
     */
    public static final String FD_ROLENAME = "roleName";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role.remark
     *
     * @mbggenerated
     */
    public static final String FD_REMARK = "remark";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role.deptId
     *
     * @mbggenerated
     */
    public static final String FD_DEPTID = "deptId";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role.createTime
     *
     * @mbggenerated
     */
    public static final String FD_CREATETIME = "createTime";

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_role.roleId
     *
     * @return the value of s_sys_role.roleId
     *
     * @mbggenerated
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_role.roleId
     *
     * @param roleId the value for s_sys_role.roleId
     *
     * @mbggenerated
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_role.roleName
     *
     * @return the value of s_sys_role.roleName
     *
     * @mbggenerated
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_role.roleName
     *
     * @param roleName the value for s_sys_role.roleName
     *
     * @mbggenerated
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_role.remark
     *
     * @return the value of s_sys_role.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_role.remark
     *
     * @param remark the value for s_sys_role.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_role.deptId
     *
     * @return the value of s_sys_role.deptId
     *
     * @mbggenerated
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_role.deptId
     *
     * @param deptId the value for s_sys_role.deptId
     *
     * @mbggenerated
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_role.createTime
     *
     * @return the value of s_sys_role.createTime
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_role.createTime
     *
     * @param createTime the value for s_sys_role.createTime
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_role
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roleId=").append(roleId);
        sb.append(", roleName=").append(roleName);
        sb.append(", remark=").append(remark);
        sb.append(", deptId=").append(deptId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", FD_ROLEID=").append(FD_ROLEID);
        sb.append(", FD_ROLENAME=").append(FD_ROLENAME);
        sb.append(", FD_REMARK=").append(FD_REMARK);
        sb.append(", FD_DEPTID=").append(FD_DEPTID);
        sb.append(", FD_CREATETIME=").append(FD_CREATETIME);
        sb.append("]");
        return sb.toString();
    }
}