package io.frame.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TokenExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table u_token
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table u_token
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table u_token
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_token
     *
     * @mbggenerated
     */
    public TokenExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_token
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_token
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_token
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_token
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_token
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_token
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_token
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_token
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_token
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_token
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_token
     *
     * @mbggenerated
     */
    public void setOrderByClauseIgnoreNull(String orderByClause) {
        String tmp = orderByClause.toLowerCase().replace("null", "").replace("asc", "").replace("desc", "").trim();
        if (orderByClause != null && tmp.length() > 0) {
            this.orderByClause = orderByClause;}
        }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table u_token
     *
     * @mbggenerated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andUserIdIsNull() {
            addCriterion("userId is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("userId is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("userId =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("userId <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("userId >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("userId >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("userId <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("userId <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("userId in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("userId not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("userId between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("userId not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andTokenIsNull() {
            addCriterion("token is null");
            return (Criteria) this;
        }

        public Criteria andTokenIsNotNull() {
            addCriterion("token is not null");
            return (Criteria) this;
        }

        public Criteria andTokenEqualTo(String value) {
            addCriterion("token =", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotEqualTo(String value) {
            addCriterion("token <>", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenGreaterThan(String value) {
            addCriterion("token >", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenGreaterThanOrEqualTo(String value) {
            addCriterion("token >=", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLessThan(String value) {
            addCriterion("token <", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLessThanOrEqualTo(String value) {
            addCriterion("token <=", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLike(String value) {
            addCriterion("token like", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotLike(String value) {
            addCriterion("token not like", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenIn(List<String> values) {
            addCriterion("token in", values, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotIn(List<String> values) {
            addCriterion("token not in", values, "token");
            return (Criteria) this;
        }

        public Criteria andTokenBetween(String value1, String value2) {
            addCriterion("token between", value1, value2, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotBetween(String value1, String value2) {
            addCriterion("token not between", value1, value2, "token");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIsNull() {
            addCriterion("expireTime is null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIsNotNull() {
            addCriterion("expireTime is not null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeEqualTo(Date value) {
            addCriterion("expireTime =", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotEqualTo(Date value) {
            addCriterion("expireTime <>", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThan(Date value) {
            addCriterion("expireTime >", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("expireTime >=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThan(Date value) {
            addCriterion("expireTime <", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThanOrEqualTo(Date value) {
            addCriterion("expireTime <=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIn(List<Date> values) {
            addCriterion("expireTime in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotIn(List<Date> values) {
            addCriterion("expireTime not in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeBetween(Date value1, Date value2) {
            addCriterion("expireTime between", value1, value2, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotBetween(Date value1, Date value2) {
            addCriterion("expireTime not between", value1, value2, "expireTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("updateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("updateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("updateTime =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("updateTime <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("updateTime >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updateTime >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("updateTime <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("updateTime <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("updateTime in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("updateTime not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("updateTime between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("updateTime not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andTokenLikeInsensitive(String value) {
            addCriterion("upper(token) like", value.toUpperCase(), "token");
            return (Criteria) this;
        }

        protected void addCriterionIgnoreNull(String condition, Object value, String property) {
            if (value != null) {
                if (value instanceof String) {
                    if (condition.toLowerCase().endsWith("like")) {
                        if (((String) value).replaceAll("%", "").length() > 0) {
                            criteria.add(new Criterion(condition, value));
                        }
                    } else if (((String) value).length() > 0) {
                        criteria.add(new Criterion(condition, value));
                    }
                } else if (value instanceof List<?>) {
                    if (((List<?>) value).size() > 0) {
                        criteria.add(new Criterion(condition, value));
                    }
                } else {
                    criteria.add(new Criterion(condition, value));
                }
            }
        }

        public Criteria andUserIdEqualToIgnoreNull(Long value) {
            addCriterionIgnoreNull("userId =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualToIgnoreNull(Long value) {
            addCriterionIgnoreNull("userId <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanIgnoreNull(Long value) {
            addCriterionIgnoreNull("userId >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualToIgnoreNull(Long value) {
            addCriterionIgnoreNull("userId >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanIgnoreNull(Long value) {
            addCriterionIgnoreNull("userId <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualToIgnoreNull(Long value) {
            addCriterionIgnoreNull("userId <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdInIgnoreNull(List<Long> value) {
            addCriterionIgnoreNull("userId in", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotInIgnoreNull(List<Long> value) {
            addCriterionIgnoreNull("userId not in", value, "userId");
            return (Criteria) this;
        }

        public Criteria andTokenEqualToIgnoreNull(String value) {
            addCriterionIgnoreNull("token =", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotEqualToIgnoreNull(String value) {
            addCriterionIgnoreNull("token <>", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenGreaterThanIgnoreNull(String value) {
            addCriterionIgnoreNull("token >", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenGreaterThanOrEqualToIgnoreNull(String value) {
            addCriterionIgnoreNull("token >=", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLessThanIgnoreNull(String value) {
            addCriterionIgnoreNull("token <", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLessThanOrEqualToIgnoreNull(String value) {
            addCriterionIgnoreNull("token <=", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLikeIgnoreNull(String value) {
            addCriterionIgnoreNull("token like", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotLikeIgnoreNull(String value) {
            addCriterionIgnoreNull("token not like", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenInIgnoreNull(List<String> value) {
            addCriterionIgnoreNull("token in", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotInIgnoreNull(List<String> value) {
            addCriterionIgnoreNull("token not in", value, "token");
            return (Criteria) this;
        }

        public Criteria andExpireTimeEqualToIgnoreNull(Date value) {
            addCriterionIgnoreNull("expireTime =", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotEqualToIgnoreNull(Date value) {
            addCriterionIgnoreNull("expireTime <>", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThanIgnoreNull(Date value) {
            addCriterionIgnoreNull("expireTime >", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThanOrEqualToIgnoreNull(Date value) {
            addCriterionIgnoreNull("expireTime >=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThanIgnoreNull(Date value) {
            addCriterionIgnoreNull("expireTime <", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThanOrEqualToIgnoreNull(Date value) {
            addCriterionIgnoreNull("expireTime <=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeInIgnoreNull(List<Date> value) {
            addCriterionIgnoreNull("expireTime in", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotInIgnoreNull(List<Date> value) {
            addCriterionIgnoreNull("expireTime not in", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualToIgnoreNull(Date value) {
            addCriterionIgnoreNull("updateTime =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualToIgnoreNull(Date value) {
            addCriterionIgnoreNull("updateTime <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanIgnoreNull(Date value) {
            addCriterionIgnoreNull("updateTime >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualToIgnoreNull(Date value) {
            addCriterionIgnoreNull("updateTime >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanIgnoreNull(Date value) {
            addCriterionIgnoreNull("updateTime <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualToIgnoreNull(Date value) {
            addCriterionIgnoreNull("updateTime <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeInIgnoreNull(List<Date> value) {
            addCriterionIgnoreNull("updateTime in", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotInIgnoreNull(List<Date> value) {
            addCriterionIgnoreNull("updateTime not in", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andTokenLikeInsensitiveIgnoreNull(String value) {
            addCriterionIgnoreNull("upper(token) like", value.toUpperCase(), "token");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table u_token
     *
     * @mbggenerated
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria andUserIdLikeInsensitive(String value) {
            addCriterion("upper(userId) like", value.toUpperCase(), "userId");
            return this;
        }

        public Criteria andTokenLikeInsensitive(String value) {
            addCriterion("upper(token) like", value.toUpperCase(), "token");
            return this;
        }

        public Criteria andExpireTimeLikeInsensitive(String value) {
            addCriterion("upper(expireTime) like", value.toUpperCase(), "expireTime");
            return this;
        }

        public Criteria andUpdateTimeLikeInsensitive(String value) {
            addCriterion("upper(updateTime) like", value.toUpperCase(), "updateTime");
            return this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table u_token
     *
     * @mbggenerated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}