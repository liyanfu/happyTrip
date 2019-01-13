package io.frame.dao.entity;

import io.frame.dao.base.BaseEntity;
import java.io.Serializable;
import java.util.Date;

public class Config extends BaseEntity implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.configId
     *
     * @mbggenerated
     */
    private Long configId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.configKey
     *
     * @mbggenerated
     */
    private String configKey;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.configVal
     *
     * @mbggenerated
     */
    private String configVal;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.configName
     *
     * @mbggenerated
     */
    private String configName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.configType
     *
     * @mbggenerated
     */
    private String configType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.configStatus
     *
     * @mbggenerated
     */
    private Integer configStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.createTime
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.createUser
     *
     * @mbggenerated
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.updateTime
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.updateUser
     *
     * @mbggenerated
     */
    private String updateUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table s_config
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.configId
     *
     * @mbggenerated
     */
    public static final String FD_CONFIGID = "configId";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.configKey
     *
     * @mbggenerated
     */
    public static final String FD_CONFIGKEY = "configKey";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.configVal
     *
     * @mbggenerated
     */
    public static final String FD_CONFIGVAL = "configVal";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.configName
     *
     * @mbggenerated
     */
    public static final String FD_CONFIGNAME = "configName";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.configType
     *
     * @mbggenerated
     */
    public static final String FD_CONFIGTYPE = "configType";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.configStatus
     *
     * @mbggenerated
     */
    public static final String FD_CONFIGSTATUS = "configStatus";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.createTime
     *
     * @mbggenerated
     */
    public static final String FD_CREATETIME = "createTime";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.createUser
     *
     * @mbggenerated
     */
    public static final String FD_CREATEUSER = "createUser";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.updateTime
     *
     * @mbggenerated
     */
    public static final String FD_UPDATETIME = "updateTime";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.updateUser
     *
     * @mbggenerated
     */
    public static final String FD_UPDATEUSER = "updateUser";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_config.remark
     *
     * @mbggenerated
     */
    public static final String FD_REMARK = "remark";

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_config.configId
     *
     * @return the value of s_config.configId
     *
     * @mbggenerated
     */
    public Long getConfigId() {
        return configId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_config.configId
     *
     * @param configId the value for s_config.configId
     *
     * @mbggenerated
     */
    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_config.configKey
     *
     * @return the value of s_config.configKey
     *
     * @mbggenerated
     */
    public String getConfigKey() {
        return configKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_config.configKey
     *
     * @param configKey the value for s_config.configKey
     *
     * @mbggenerated
     */
    public void setConfigKey(String configKey) {
        this.configKey = configKey == null ? null : configKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_config.configVal
     *
     * @return the value of s_config.configVal
     *
     * @mbggenerated
     */
    public String getConfigVal() {
        return configVal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_config.configVal
     *
     * @param configVal the value for s_config.configVal
     *
     * @mbggenerated
     */
    public void setConfigVal(String configVal) {
        this.configVal = configVal == null ? null : configVal.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_config.configName
     *
     * @return the value of s_config.configName
     *
     * @mbggenerated
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_config.configName
     *
     * @param configName the value for s_config.configName
     *
     * @mbggenerated
     */
    public void setConfigName(String configName) {
        this.configName = configName == null ? null : configName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_config.configType
     *
     * @return the value of s_config.configType
     *
     * @mbggenerated
     */
    public String getConfigType() {
        return configType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_config.configType
     *
     * @param configType the value for s_config.configType
     *
     * @mbggenerated
     */
    public void setConfigType(String configType) {
        this.configType = configType == null ? null : configType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_config.configStatus
     *
     * @return the value of s_config.configStatus
     *
     * @mbggenerated
     */
    public Integer getConfigStatus() {
        return configStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_config.configStatus
     *
     * @param configStatus the value for s_config.configStatus
     *
     * @mbggenerated
     */
    public void setConfigStatus(Integer configStatus) {
        this.configStatus = configStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_config.createTime
     *
     * @return the value of s_config.createTime
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_config.createTime
     *
     * @param createTime the value for s_config.createTime
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_config.createUser
     *
     * @return the value of s_config.createUser
     *
     * @mbggenerated
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_config.createUser
     *
     * @param createUser the value for s_config.createUser
     *
     * @mbggenerated
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_config.updateTime
     *
     * @return the value of s_config.updateTime
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_config.updateTime
     *
     * @param updateTime the value for s_config.updateTime
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_config.updateUser
     *
     * @return the value of s_config.updateUser
     *
     * @mbggenerated
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_config.updateUser
     *
     * @param updateUser the value for s_config.updateUser
     *
     * @mbggenerated
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_config.remark
     *
     * @return the value of s_config.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_config.remark
     *
     * @param remark the value for s_config.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", configId=").append(configId);
        sb.append(", configKey=").append(configKey);
        sb.append(", configVal=").append(configVal);
        sb.append(", configName=").append(configName);
        sb.append(", configType=").append(configType);
        sb.append(", configStatus=").append(configStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", FD_CONFIGID=").append(FD_CONFIGID);
        sb.append(", FD_CONFIGKEY=").append(FD_CONFIGKEY);
        sb.append(", FD_CONFIGVAL=").append(FD_CONFIGVAL);
        sb.append(", FD_CONFIGNAME=").append(FD_CONFIGNAME);
        sb.append(", FD_CONFIGTYPE=").append(FD_CONFIGTYPE);
        sb.append(", FD_CONFIGSTATUS=").append(FD_CONFIGSTATUS);
        sb.append(", FD_CREATETIME=").append(FD_CREATETIME);
        sb.append(", FD_CREATEUSER=").append(FD_CREATEUSER);
        sb.append(", FD_UPDATETIME=").append(FD_UPDATETIME);
        sb.append(", FD_UPDATEUSER=").append(FD_UPDATEUSER);
        sb.append(", FD_REMARK=").append(FD_REMARK);
        sb.append("]");
        return sb.toString();
    }
}