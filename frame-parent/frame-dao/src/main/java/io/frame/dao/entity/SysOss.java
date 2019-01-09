package io.frame.dao.entity;

import io.frame.dao.base.BaseEntity;
import java.io.Serializable;
import java.util.Date;

public class SysOss extends BaseEntity implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_oss.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_oss.url
     *
     * @mbggenerated
     */
    private String url;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_oss.createTime
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table s_sys_oss
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_oss.id
     *
     * @mbggenerated
     */
    public static final String FD_ID = "id";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_oss.url
     *
     * @mbggenerated
     */
    public static final String FD_URL = "url";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_oss.createTime
     *
     * @mbggenerated
     */
    public static final String FD_CREATETIME = "createTime";

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_oss.id
     *
     * @return the value of s_sys_oss.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_oss.id
     *
     * @param id the value for s_sys_oss.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_oss.url
     *
     * @return the value of s_sys_oss.url
     *
     * @mbggenerated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_oss.url
     *
     * @param url the value for s_sys_oss.url
     *
     * @mbggenerated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_oss.createTime
     *
     * @return the value of s_sys_oss.createTime
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_oss.createTime
     *
     * @param createTime the value for s_sys_oss.createTime
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_oss
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
        sb.append(", url=").append(url);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", FD_ID=").append(FD_ID);
        sb.append(", FD_URL=").append(FD_URL);
        sb.append(", FD_CREATETIME=").append(FD_CREATETIME);
        sb.append("]");
        return sb.toString();
    }
}