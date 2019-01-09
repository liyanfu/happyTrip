package io.frame.dao.entity;

import io.frame.dao.base.BaseEntity;
import java.io.Serializable;
import java.util.Date;

public class Welfare extends BaseEntity implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_welfare.welfareId
     *
     * @mbggenerated
     */
    private Long welfareId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_welfare.welfareName
     *
     * @mbggenerated
     */
    private String welfareName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_welfare.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_welfare.createTime
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_welfare.createUser
     *
     * @mbggenerated
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_welfare.updateTime
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_welfare.updateUser
     *
     * @mbggenerated
     */
    private String updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table b_welfare
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_welfare.welfareId
     *
     * @mbggenerated
     */
    public static final String FD_WELFAREID = "welfareId";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_welfare.welfareName
     *
     * @mbggenerated
     */
    public static final String FD_WELFARENAME = "welfareName";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_welfare.status
     *
     * @mbggenerated
     */
    public static final String FD_STATUS = "status";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_welfare.createTime
     *
     * @mbggenerated
     */
    public static final String FD_CREATETIME = "createTime";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_welfare.createUser
     *
     * @mbggenerated
     */
    public static final String FD_CREATEUSER = "createUser";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_welfare.updateTime
     *
     * @mbggenerated
     */
    public static final String FD_UPDATETIME = "updateTime";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_welfare.updateUser
     *
     * @mbggenerated
     */
    public static final String FD_UPDATEUSER = "updateUser";

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_welfare.welfareId
     *
     * @return the value of b_welfare.welfareId
     *
     * @mbggenerated
     */
    public Long getWelfareId() {
        return welfareId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_welfare.welfareId
     *
     * @param welfareId the value for b_welfare.welfareId
     *
     * @mbggenerated
     */
    public void setWelfareId(Long welfareId) {
        this.welfareId = welfareId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_welfare.welfareName
     *
     * @return the value of b_welfare.welfareName
     *
     * @mbggenerated
     */
    public String getWelfareName() {
        return welfareName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_welfare.welfareName
     *
     * @param welfareName the value for b_welfare.welfareName
     *
     * @mbggenerated
     */
    public void setWelfareName(String welfareName) {
        this.welfareName = welfareName == null ? null : welfareName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_welfare.status
     *
     * @return the value of b_welfare.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_welfare.status
     *
     * @param status the value for b_welfare.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_welfare.createTime
     *
     * @return the value of b_welfare.createTime
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_welfare.createTime
     *
     * @param createTime the value for b_welfare.createTime
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_welfare.createUser
     *
     * @return the value of b_welfare.createUser
     *
     * @mbggenerated
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_welfare.createUser
     *
     * @param createUser the value for b_welfare.createUser
     *
     * @mbggenerated
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_welfare.updateTime
     *
     * @return the value of b_welfare.updateTime
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_welfare.updateTime
     *
     * @param updateTime the value for b_welfare.updateTime
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_welfare.updateUser
     *
     * @return the value of b_welfare.updateUser
     *
     * @mbggenerated
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_welfare.updateUser
     *
     * @param updateUser the value for b_welfare.updateUser
     *
     * @mbggenerated
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_welfare
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", welfareId=").append(welfareId);
        sb.append(", welfareName=").append(welfareName);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", FD_WELFAREID=").append(FD_WELFAREID);
        sb.append(", FD_WELFARENAME=").append(FD_WELFARENAME);
        sb.append(", FD_STATUS=").append(FD_STATUS);
        sb.append(", FD_CREATETIME=").append(FD_CREATETIME);
        sb.append(", FD_CREATEUSER=").append(FD_CREATEUSER);
        sb.append(", FD_UPDATETIME=").append(FD_UPDATETIME);
        sb.append(", FD_UPDATEUSER=").append(FD_UPDATEUSER);
        sb.append("]");
        return sb.toString();
    }
}