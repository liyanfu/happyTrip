package io.frame.dao.entity;

import io.frame.dao.base.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SpeclalRecord extends BaseEntity implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.userName
     *
     * @mbggenerated
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.userMobile
     *
     * @mbggenerated
     */
    private String userMobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.userLevel
     *
     * @mbggenerated
     */
    private Integer userLevel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.parentId
     *
     * @mbggenerated
     */
    private Long parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.groupUserIds
     *
     * @mbggenerated
     */
    private String groupUserIds;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.money
     *
     * @mbggenerated
     */
    private BigDecimal money;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.recommendNum
     *
     * @mbggenerated
     */
    private Integer recommendNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.teamNum
     *
     * @mbggenerated
     */
    private Integer teamNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.teamAchievement
     *
     * @mbggenerated
     */
    private BigDecimal teamAchievement;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.isGrant
     *
     * @mbggenerated
     */
    private Integer isGrant;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.createTime
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.grantTime
     *
     * @mbggenerated
     */
    private Date grantTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.updateTime
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.updateUser
     *
     * @mbggenerated
     */
    private String updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.id
     *
     * @mbggenerated
     */
    public static final String FD_ID = "id";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.userName
     *
     * @mbggenerated
     */
    public static final String FD_USERNAME = "userName";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.userMobile
     *
     * @mbggenerated
     */
    public static final String FD_USERMOBILE = "userMobile";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.userLevel
     *
     * @mbggenerated
     */
    public static final String FD_USERLEVEL = "userLevel";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.parentId
     *
     * @mbggenerated
     */
    public static final String FD_PARENTID = "parentId";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.groupUserIds
     *
     * @mbggenerated
     */
    public static final String FD_GROUPUSERIDS = "groupUserIds";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.money
     *
     * @mbggenerated
     */
    public static final String FD_MONEY = "money";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.recommendNum
     *
     * @mbggenerated
     */
    public static final String FD_RECOMMENDNUM = "recommendNum";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.teamNum
     *
     * @mbggenerated
     */
    public static final String FD_TEAMNUM = "teamNum";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.teamAchievement
     *
     * @mbggenerated
     */
    public static final String FD_TEAMACHIEVEMENT = "teamAchievement";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.isGrant
     *
     * @mbggenerated
     */
    public static final String FD_ISGRANT = "isGrant";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.createTime
     *
     * @mbggenerated
     */
    public static final String FD_CREATETIME = "createTime";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.grantTime
     *
     * @mbggenerated
     */
    public static final String FD_GRANTTIME = "grantTime";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.updateTime
     *
     * @mbggenerated
     */
    public static final String FD_UPDATETIME = "updateTime";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_speclal_record.updateUser
     *
     * @mbggenerated
     */
    public static final String FD_UPDATEUSER = "updateUser";

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_speclal_record.id
     *
     * @return the value of b_speclal_record.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_speclal_record.id
     *
     * @param id the value for b_speclal_record.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_speclal_record.userName
     *
     * @return the value of b_speclal_record.userName
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_speclal_record.userName
     *
     * @param userName the value for b_speclal_record.userName
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_speclal_record.userMobile
     *
     * @return the value of b_speclal_record.userMobile
     *
     * @mbggenerated
     */
    public String getUserMobile() {
        return userMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_speclal_record.userMobile
     *
     * @param userMobile the value for b_speclal_record.userMobile
     *
     * @mbggenerated
     */
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile == null ? null : userMobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_speclal_record.userLevel
     *
     * @return the value of b_speclal_record.userLevel
     *
     * @mbggenerated
     */
    public Integer getUserLevel() {
        return userLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_speclal_record.userLevel
     *
     * @param userLevel the value for b_speclal_record.userLevel
     *
     * @mbggenerated
     */
    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_speclal_record.parentId
     *
     * @return the value of b_speclal_record.parentId
     *
     * @mbggenerated
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_speclal_record.parentId
     *
     * @param parentId the value for b_speclal_record.parentId
     *
     * @mbggenerated
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_speclal_record.groupUserIds
     *
     * @return the value of b_speclal_record.groupUserIds
     *
     * @mbggenerated
     */
    public String getGroupUserIds() {
        return groupUserIds;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_speclal_record.groupUserIds
     *
     * @param groupUserIds the value for b_speclal_record.groupUserIds
     *
     * @mbggenerated
     */
    public void setGroupUserIds(String groupUserIds) {
        this.groupUserIds = groupUserIds == null ? null : groupUserIds.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_speclal_record.money
     *
     * @return the value of b_speclal_record.money
     *
     * @mbggenerated
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_speclal_record.money
     *
     * @param money the value for b_speclal_record.money
     *
     * @mbggenerated
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_speclal_record.recommendNum
     *
     * @return the value of b_speclal_record.recommendNum
     *
     * @mbggenerated
     */
    public Integer getRecommendNum() {
        return recommendNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_speclal_record.recommendNum
     *
     * @param recommendNum the value for b_speclal_record.recommendNum
     *
     * @mbggenerated
     */
    public void setRecommendNum(Integer recommendNum) {
        this.recommendNum = recommendNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_speclal_record.teamNum
     *
     * @return the value of b_speclal_record.teamNum
     *
     * @mbggenerated
     */
    public Integer getTeamNum() {
        return teamNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_speclal_record.teamNum
     *
     * @param teamNum the value for b_speclal_record.teamNum
     *
     * @mbggenerated
     */
    public void setTeamNum(Integer teamNum) {
        this.teamNum = teamNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_speclal_record.teamAchievement
     *
     * @return the value of b_speclal_record.teamAchievement
     *
     * @mbggenerated
     */
    public BigDecimal getTeamAchievement() {
        return teamAchievement;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_speclal_record.teamAchievement
     *
     * @param teamAchievement the value for b_speclal_record.teamAchievement
     *
     * @mbggenerated
     */
    public void setTeamAchievement(BigDecimal teamAchievement) {
        this.teamAchievement = teamAchievement;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_speclal_record.isGrant
     *
     * @return the value of b_speclal_record.isGrant
     *
     * @mbggenerated
     */
    public Integer getIsGrant() {
        return isGrant;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_speclal_record.isGrant
     *
     * @param isGrant the value for b_speclal_record.isGrant
     *
     * @mbggenerated
     */
    public void setIsGrant(Integer isGrant) {
        this.isGrant = isGrant;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_speclal_record.createTime
     *
     * @return the value of b_speclal_record.createTime
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_speclal_record.createTime
     *
     * @param createTime the value for b_speclal_record.createTime
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_speclal_record.grantTime
     *
     * @return the value of b_speclal_record.grantTime
     *
     * @mbggenerated
     */
    public Date getGrantTime() {
        return grantTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_speclal_record.grantTime
     *
     * @param grantTime the value for b_speclal_record.grantTime
     *
     * @mbggenerated
     */
    public void setGrantTime(Date grantTime) {
        this.grantTime = grantTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_speclal_record.updateTime
     *
     * @return the value of b_speclal_record.updateTime
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_speclal_record.updateTime
     *
     * @param updateTime the value for b_speclal_record.updateTime
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_speclal_record.updateUser
     *
     * @return the value of b_speclal_record.updateUser
     *
     * @mbggenerated
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_speclal_record.updateUser
     *
     * @param updateUser the value for b_speclal_record.updateUser
     *
     * @mbggenerated
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
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
        sb.append(", userName=").append(userName);
        sb.append(", userMobile=").append(userMobile);
        sb.append(", userLevel=").append(userLevel);
        sb.append(", parentId=").append(parentId);
        sb.append(", groupUserIds=").append(groupUserIds);
        sb.append(", money=").append(money);
        sb.append(", recommendNum=").append(recommendNum);
        sb.append(", teamNum=").append(teamNum);
        sb.append(", teamAchievement=").append(teamAchievement);
        sb.append(", isGrant=").append(isGrant);
        sb.append(", createTime=").append(createTime);
        sb.append(", grantTime=").append(grantTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", FD_ID=").append(FD_ID);
        sb.append(", FD_USERNAME=").append(FD_USERNAME);
        sb.append(", FD_USERMOBILE=").append(FD_USERMOBILE);
        sb.append(", FD_USERLEVEL=").append(FD_USERLEVEL);
        sb.append(", FD_PARENTID=").append(FD_PARENTID);
        sb.append(", FD_GROUPUSERIDS=").append(FD_GROUPUSERIDS);
        sb.append(", FD_MONEY=").append(FD_MONEY);
        sb.append(", FD_RECOMMENDNUM=").append(FD_RECOMMENDNUM);
        sb.append(", FD_TEAMNUM=").append(FD_TEAMNUM);
        sb.append(", FD_TEAMACHIEVEMENT=").append(FD_TEAMACHIEVEMENT);
        sb.append(", FD_ISGRANT=").append(FD_ISGRANT);
        sb.append(", FD_CREATETIME=").append(FD_CREATETIME);
        sb.append(", FD_GRANTTIME=").append(FD_GRANTTIME);
        sb.append(", FD_UPDATETIME=").append(FD_UPDATETIME);
        sb.append(", FD_UPDATEUSER=").append(FD_UPDATEUSER);
        sb.append("]");
        return sb.toString();
    }
}