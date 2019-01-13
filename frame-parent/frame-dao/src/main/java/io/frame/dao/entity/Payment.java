package io.frame.dao.entity;

import io.frame.dao.base.BaseEntity;
import java.io.Serializable;
import java.util.Date;

public class Payment extends BaseEntity implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.paymentId
     *
     * @mbggenerated
     */
    private Long paymentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.paymentKey
     *
     * @mbggenerated
     */
    private String paymentKey;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.paymentName
     *
     * @mbggenerated
     */
    private String paymentName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.sort
     *
     * @mbggenerated
     */
    private Integer sort;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.createTime
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.createUser
     *
     * @mbggenerated
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.updateTime
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.updateUser
     *
     * @mbggenerated
     */
    private String updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table s_payment
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.paymentId
     *
     * @mbggenerated
     */
    public static final String FD_PAYMENTID = "paymentId";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.paymentKey
     *
     * @mbggenerated
     */
    public static final String FD_PAYMENTKEY = "paymentKey";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.paymentName
     *
     * @mbggenerated
     */
    public static final String FD_PAYMENTNAME = "paymentName";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.status
     *
     * @mbggenerated
     */
    public static final String FD_STATUS = "status";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.sort
     *
     * @mbggenerated
     */
    public static final String FD_SORT = "sort";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.createTime
     *
     * @mbggenerated
     */
    public static final String FD_CREATETIME = "createTime";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.createUser
     *
     * @mbggenerated
     */
    public static final String FD_CREATEUSER = "createUser";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.updateTime
     *
     * @mbggenerated
     */
    public static final String FD_UPDATETIME = "updateTime";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_payment.updateUser
     *
     * @mbggenerated
     */
    public static final String FD_UPDATEUSER = "updateUser";

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_payment.paymentId
     *
     * @return the value of s_payment.paymentId
     *
     * @mbggenerated
     */
    public Long getPaymentId() {
        return paymentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_payment.paymentId
     *
     * @param paymentId the value for s_payment.paymentId
     *
     * @mbggenerated
     */
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_payment.paymentKey
     *
     * @return the value of s_payment.paymentKey
     *
     * @mbggenerated
     */
    public String getPaymentKey() {
        return paymentKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_payment.paymentKey
     *
     * @param paymentKey the value for s_payment.paymentKey
     *
     * @mbggenerated
     */
    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey == null ? null : paymentKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_payment.paymentName
     *
     * @return the value of s_payment.paymentName
     *
     * @mbggenerated
     */
    public String getPaymentName() {
        return paymentName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_payment.paymentName
     *
     * @param paymentName the value for s_payment.paymentName
     *
     * @mbggenerated
     */
    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName == null ? null : paymentName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_payment.status
     *
     * @return the value of s_payment.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_payment.status
     *
     * @param status the value for s_payment.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_payment.sort
     *
     * @return the value of s_payment.sort
     *
     * @mbggenerated
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_payment.sort
     *
     * @param sort the value for s_payment.sort
     *
     * @mbggenerated
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_payment.createTime
     *
     * @return the value of s_payment.createTime
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_payment.createTime
     *
     * @param createTime the value for s_payment.createTime
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_payment.createUser
     *
     * @return the value of s_payment.createUser
     *
     * @mbggenerated
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_payment.createUser
     *
     * @param createUser the value for s_payment.createUser
     *
     * @mbggenerated
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_payment.updateTime
     *
     * @return the value of s_payment.updateTime
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_payment.updateTime
     *
     * @param updateTime the value for s_payment.updateTime
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_payment.updateUser
     *
     * @return the value of s_payment.updateUser
     *
     * @mbggenerated
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_payment.updateUser
     *
     * @param updateUser the value for s_payment.updateUser
     *
     * @mbggenerated
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_payment
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", paymentId=").append(paymentId);
        sb.append(", paymentKey=").append(paymentKey);
        sb.append(", paymentName=").append(paymentName);
        sb.append(", status=").append(status);
        sb.append(", sort=").append(sort);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", FD_PAYMENTID=").append(FD_PAYMENTID);
        sb.append(", FD_PAYMENTKEY=").append(FD_PAYMENTKEY);
        sb.append(", FD_PAYMENTNAME=").append(FD_PAYMENTNAME);
        sb.append(", FD_STATUS=").append(FD_STATUS);
        sb.append(", FD_SORT=").append(FD_SORT);
        sb.append(", FD_CREATETIME=").append(FD_CREATETIME);
        sb.append(", FD_CREATEUSER=").append(FD_CREATEUSER);
        sb.append(", FD_UPDATETIME=").append(FD_UPDATETIME);
        sb.append(", FD_UPDATEUSER=").append(FD_UPDATEUSER);
        sb.append("]");
        return sb.toString();
    }
}