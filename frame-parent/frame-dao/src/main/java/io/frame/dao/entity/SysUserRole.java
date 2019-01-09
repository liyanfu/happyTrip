package io.frame.dao.entity;

import io.frame.dao.base.BaseEntity;
import java.io.Serializable;

public class SysUserRole extends BaseEntity implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_user_role.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_user_role.userId
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_user_role.roleId
     *
     * @mbggenerated
     */
    private Long roleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table s_sys_user_role
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_user_role.id
     *
     * @mbggenerated
     */
    public static final String FD_ID = "id";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_user_role.userId
     *
     * @mbggenerated
     */
    public static final String FD_USERID = "userId";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_user_role.roleId
     *
     * @mbggenerated
     */
    public static final String FD_ROLEID = "roleId";

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_user_role.id
     *
     * @return the value of s_sys_user_role.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_user_role.id
     *
     * @param id the value for s_sys_user_role.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_user_role.userId
     *
     * @return the value of s_sys_user_role.userId
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_user_role.userId
     *
     * @param userId the value for s_sys_user_role.userId
     *
     * @mbggenerated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_user_role.roleId
     *
     * @return the value of s_sys_user_role.roleId
     *
     * @mbggenerated
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_user_role.roleId
     *
     * @param roleId the value for s_sys_user_role.roleId
     *
     * @mbggenerated
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_user_role
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
        sb.append(", userId=").append(userId);
        sb.append(", roleId=").append(roleId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", FD_ID=").append(FD_ID);
        sb.append(", FD_USERID=").append(FD_USERID);
        sb.append(", FD_ROLEID=").append(FD_ROLEID);
        sb.append("]");
        return sb.toString();
    }
}