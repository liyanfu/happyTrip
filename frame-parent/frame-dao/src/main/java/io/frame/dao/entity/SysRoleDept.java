package io.frame.dao.entity;

import io.frame.dao.base.BaseEntity;
import java.io.Serializable;

public class SysRoleDept extends BaseEntity implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role_dept.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role_dept.roleId
     *
     * @mbggenerated
     */
    private Long roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role_dept.deptId
     *
     * @mbggenerated
     */
    private Long deptId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table s_sys_role_dept
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role_dept.id
     *
     * @mbggenerated
     */
    public static final String FD_ID = "id";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role_dept.roleId
     *
     * @mbggenerated
     */
    public static final String FD_ROLEID = "roleId";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_role_dept.deptId
     *
     * @mbggenerated
     */
    public static final String FD_DEPTID = "deptId";

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_role_dept.id
     *
     * @return the value of s_sys_role_dept.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_role_dept.id
     *
     * @param id the value for s_sys_role_dept.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_role_dept.roleId
     *
     * @return the value of s_sys_role_dept.roleId
     *
     * @mbggenerated
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_role_dept.roleId
     *
     * @param roleId the value for s_sys_role_dept.roleId
     *
     * @mbggenerated
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_role_dept.deptId
     *
     * @return the value of s_sys_role_dept.deptId
     *
     * @mbggenerated
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_role_dept.deptId
     *
     * @param deptId the value for s_sys_role_dept.deptId
     *
     * @mbggenerated
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_role_dept
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleId=").append(roleId);
        sb.append(", deptId=").append(deptId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", FD_ID=").append(FD_ID);
        sb.append(", FD_ROLEID=").append(FD_ROLEID);
        sb.append(", FD_DEPTID=").append(FD_DEPTID);
        sb.append("]");
        return sb.toString();
    }
}