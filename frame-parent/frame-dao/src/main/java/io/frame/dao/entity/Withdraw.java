package io.frame.dao.entity;

import io.frame.dao.base.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Withdraw extends BaseEntity implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.withdrawId
     *
     * @mbggenerated
     */
    private Long withdrawId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.userId
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.userMobile
     *
     * @mbggenerated
     */
    private String userMobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.userName
     *
     * @mbggenerated
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.parentId
     *
     * @mbggenerated
     */
    private Long parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.groupUserIds
     *
     * @mbggenerated
     */
    private String groupUserIds;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.withdrawMoney
     *
     * @mbggenerated
     */
    private BigDecimal withdrawMoney;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.withdrawFee
     *
     * @mbggenerated
     */
    private BigDecimal withdrawFee;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.withdrawRealMoney
     *
     * @mbggenerated
     */
    private BigDecimal withdrawRealMoney;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.alipayName
     *
     * @mbggenerated
     */
    private String alipayName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.alipayMobile
     *
     * @mbggenerated
     */
    private String alipayMobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.transferAccounts
     *
     * @mbggenerated
     */
    private String transferAccounts;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.frontRemark
     *
     * @mbggenerated
     */
    private String frontRemark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.backRemark
     *
     * @mbggenerated
     */
    private String backRemark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.createTime
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.createUser
     *
     * @mbggenerated
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.updateTime
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.updateUser
     *
     * @mbggenerated
     */
    private String updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.withdrawId
     *
     * @mbggenerated
     */
    public static final String FD_WITHDRAWID = "withdrawId";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.userId
     *
     * @mbggenerated
     */
    public static final String FD_USERID = "userId";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.userMobile
     *
     * @mbggenerated
     */
    public static final String FD_USERMOBILE = "userMobile";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.userName
     *
     * @mbggenerated
     */
    public static final String FD_USERNAME = "userName";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.parentId
     *
     * @mbggenerated
     */
    public static final String FD_PARENTID = "parentId";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.groupUserIds
     *
     * @mbggenerated
     */
    public static final String FD_GROUPUSERIDS = "groupUserIds";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.withdrawMoney
     *
     * @mbggenerated
     */
    public static final String FD_WITHDRAWMONEY = "withdrawMoney";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.withdrawFee
     *
     * @mbggenerated
     */
    public static final String FD_WITHDRAWFEE = "withdrawFee";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.withdrawRealMoney
     *
     * @mbggenerated
     */
    public static final String FD_WITHDRAWREALMONEY = "withdrawRealMoney";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.alipayName
     *
     * @mbggenerated
     */
    public static final String FD_ALIPAYNAME = "alipayName";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.alipayMobile
     *
     * @mbggenerated
     */
    public static final String FD_ALIPAYMOBILE = "alipayMobile";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.transferAccounts
     *
     * @mbggenerated
     */
    public static final String FD_TRANSFERACCOUNTS = "transferAccounts";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.status
     *
     * @mbggenerated
     */
    public static final String FD_STATUS = "status";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.frontRemark
     *
     * @mbggenerated
     */
    public static final String FD_FRONTREMARK = "frontRemark";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.backRemark
     *
     * @mbggenerated
     */
    public static final String FD_BACKREMARK = "backRemark";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.createTime
     *
     * @mbggenerated
     */
    public static final String FD_CREATETIME = "createTime";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.createUser
     *
     * @mbggenerated
     */
    public static final String FD_CREATEUSER = "createUser";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.updateTime
     *
     * @mbggenerated
     */
    public static final String FD_UPDATETIME = "updateTime";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_withdraw.updateUser
     *
     * @mbggenerated
     */
    public static final String FD_UPDATEUSER = "updateUser";

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.withdrawId
     *
     * @return the value of u_withdraw.withdrawId
     *
     * @mbggenerated
     */
    public Long getWithdrawId() {
        return withdrawId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.withdrawId
     *
     * @param withdrawId the value for u_withdraw.withdrawId
     *
     * @mbggenerated
     */
    public void setWithdrawId(Long withdrawId) {
        this.withdrawId = withdrawId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.userId
     *
     * @return the value of u_withdraw.userId
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.userId
     *
     * @param userId the value for u_withdraw.userId
     *
     * @mbggenerated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.userMobile
     *
     * @return the value of u_withdraw.userMobile
     *
     * @mbggenerated
     */
    public String getUserMobile() {
        return userMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.userMobile
     *
     * @param userMobile the value for u_withdraw.userMobile
     *
     * @mbggenerated
     */
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile == null ? null : userMobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.userName
     *
     * @return the value of u_withdraw.userName
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.userName
     *
     * @param userName the value for u_withdraw.userName
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.parentId
     *
     * @return the value of u_withdraw.parentId
     *
     * @mbggenerated
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.parentId
     *
     * @param parentId the value for u_withdraw.parentId
     *
     * @mbggenerated
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.groupUserIds
     *
     * @return the value of u_withdraw.groupUserIds
     *
     * @mbggenerated
     */
    public String getGroupUserIds() {
        return groupUserIds;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.groupUserIds
     *
     * @param groupUserIds the value for u_withdraw.groupUserIds
     *
     * @mbggenerated
     */
    public void setGroupUserIds(String groupUserIds) {
        this.groupUserIds = groupUserIds == null ? null : groupUserIds.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.withdrawMoney
     *
     * @return the value of u_withdraw.withdrawMoney
     *
     * @mbggenerated
     */
    public BigDecimal getWithdrawMoney() {
        return withdrawMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.withdrawMoney
     *
     * @param withdrawMoney the value for u_withdraw.withdrawMoney
     *
     * @mbggenerated
     */
    public void setWithdrawMoney(BigDecimal withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.withdrawFee
     *
     * @return the value of u_withdraw.withdrawFee
     *
     * @mbggenerated
     */
    public BigDecimal getWithdrawFee() {
        return withdrawFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.withdrawFee
     *
     * @param withdrawFee the value for u_withdraw.withdrawFee
     *
     * @mbggenerated
     */
    public void setWithdrawFee(BigDecimal withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.withdrawRealMoney
     *
     * @return the value of u_withdraw.withdrawRealMoney
     *
     * @mbggenerated
     */
    public BigDecimal getWithdrawRealMoney() {
        return withdrawRealMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.withdrawRealMoney
     *
     * @param withdrawRealMoney the value for u_withdraw.withdrawRealMoney
     *
     * @mbggenerated
     */
    public void setWithdrawRealMoney(BigDecimal withdrawRealMoney) {
        this.withdrawRealMoney = withdrawRealMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.alipayName
     *
     * @return the value of u_withdraw.alipayName
     *
     * @mbggenerated
     */
    public String getAlipayName() {
        return alipayName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.alipayName
     *
     * @param alipayName the value for u_withdraw.alipayName
     *
     * @mbggenerated
     */
    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName == null ? null : alipayName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.alipayMobile
     *
     * @return the value of u_withdraw.alipayMobile
     *
     * @mbggenerated
     */
    public String getAlipayMobile() {
        return alipayMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.alipayMobile
     *
     * @param alipayMobile the value for u_withdraw.alipayMobile
     *
     * @mbggenerated
     */
    public void setAlipayMobile(String alipayMobile) {
        this.alipayMobile = alipayMobile == null ? null : alipayMobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.transferAccounts
     *
     * @return the value of u_withdraw.transferAccounts
     *
     * @mbggenerated
     */
    public String getTransferAccounts() {
        return transferAccounts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.transferAccounts
     *
     * @param transferAccounts the value for u_withdraw.transferAccounts
     *
     * @mbggenerated
     */
    public void setTransferAccounts(String transferAccounts) {
        this.transferAccounts = transferAccounts == null ? null : transferAccounts.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.status
     *
     * @return the value of u_withdraw.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.status
     *
     * @param status the value for u_withdraw.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.frontRemark
     *
     * @return the value of u_withdraw.frontRemark
     *
     * @mbggenerated
     */
    public String getFrontRemark() {
        return frontRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.frontRemark
     *
     * @param frontRemark the value for u_withdraw.frontRemark
     *
     * @mbggenerated
     */
    public void setFrontRemark(String frontRemark) {
        this.frontRemark = frontRemark == null ? null : frontRemark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.backRemark
     *
     * @return the value of u_withdraw.backRemark
     *
     * @mbggenerated
     */
    public String getBackRemark() {
        return backRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.backRemark
     *
     * @param backRemark the value for u_withdraw.backRemark
     *
     * @mbggenerated
     */
    public void setBackRemark(String backRemark) {
        this.backRemark = backRemark == null ? null : backRemark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.createTime
     *
     * @return the value of u_withdraw.createTime
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.createTime
     *
     * @param createTime the value for u_withdraw.createTime
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.createUser
     *
     * @return the value of u_withdraw.createUser
     *
     * @mbggenerated
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.createUser
     *
     * @param createUser the value for u_withdraw.createUser
     *
     * @mbggenerated
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.updateTime
     *
     * @return the value of u_withdraw.updateTime
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.updateTime
     *
     * @param updateTime the value for u_withdraw.updateTime
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_withdraw.updateUser
     *
     * @return the value of u_withdraw.updateUser
     *
     * @mbggenerated
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_withdraw.updateUser
     *
     * @param updateUser the value for u_withdraw.updateUser
     *
     * @mbggenerated
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", withdrawId=").append(withdrawId);
        sb.append(", userId=").append(userId);
        sb.append(", userMobile=").append(userMobile);
        sb.append(", userName=").append(userName);
        sb.append(", parentId=").append(parentId);
        sb.append(", groupUserIds=").append(groupUserIds);
        sb.append(", withdrawMoney=").append(withdrawMoney);
        sb.append(", withdrawFee=").append(withdrawFee);
        sb.append(", withdrawRealMoney=").append(withdrawRealMoney);
        sb.append(", alipayName=").append(alipayName);
        sb.append(", alipayMobile=").append(alipayMobile);
        sb.append(", transferAccounts=").append(transferAccounts);
        sb.append(", status=").append(status);
        sb.append(", frontRemark=").append(frontRemark);
        sb.append(", backRemark=").append(backRemark);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", FD_WITHDRAWID=").append(FD_WITHDRAWID);
        sb.append(", FD_USERID=").append(FD_USERID);
        sb.append(", FD_USERMOBILE=").append(FD_USERMOBILE);
        sb.append(", FD_USERNAME=").append(FD_USERNAME);
        sb.append(", FD_PARENTID=").append(FD_PARENTID);
        sb.append(", FD_GROUPUSERIDS=").append(FD_GROUPUSERIDS);
        sb.append(", FD_WITHDRAWMONEY=").append(FD_WITHDRAWMONEY);
        sb.append(", FD_WITHDRAWFEE=").append(FD_WITHDRAWFEE);
        sb.append(", FD_WITHDRAWREALMONEY=").append(FD_WITHDRAWREALMONEY);
        sb.append(", FD_ALIPAYNAME=").append(FD_ALIPAYNAME);
        sb.append(", FD_ALIPAYMOBILE=").append(FD_ALIPAYMOBILE);
        sb.append(", FD_TRANSFERACCOUNTS=").append(FD_TRANSFERACCOUNTS);
        sb.append(", FD_STATUS=").append(FD_STATUS);
        sb.append(", FD_FRONTREMARK=").append(FD_FRONTREMARK);
        sb.append(", FD_BACKREMARK=").append(FD_BACKREMARK);
        sb.append(", FD_CREATETIME=").append(FD_CREATETIME);
        sb.append(", FD_CREATEUSER=").append(FD_CREATEUSER);
        sb.append(", FD_UPDATETIME=").append(FD_UPDATETIME);
        sb.append(", FD_UPDATEUSER=").append(FD_UPDATEUSER);
        sb.append("]");
        return sb.toString();
    }
}