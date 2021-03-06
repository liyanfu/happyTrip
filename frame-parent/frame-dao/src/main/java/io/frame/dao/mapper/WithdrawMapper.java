package io.frame.dao.mapper;

import io.frame.dao.entity.Withdraw;
import io.frame.dao.entity.WithdrawExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WithdrawMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    int countByExample(WithdrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    int deleteByExample(WithdrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long withdrawId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    int insert(Withdraw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    int insertSelective(Withdraw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    List<Withdraw> selectByExample(WithdrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    Withdraw selectByPrimaryKey(Long withdrawId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Withdraw record, @Param("example") WithdrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Withdraw record, @Param("example") WithdrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Withdraw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Withdraw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    int insertBatch(List<Withdraw> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    int insertBatchSelective(@Param("showField") List<String> showField, @Param("list") List<Withdraw> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    Withdraw selectOneByExample(WithdrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    Withdraw selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("withdrawId") Long withdrawId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    List<Withdraw> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") WithdrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    Withdraw selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") WithdrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    int updateByExampleSelectiveSync(@Param("record") Withdraw record, @Param("example") WithdrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelectiveSync(Withdraw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    int upsert(Withdraw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_withdraw
     *
     * @mbggenerated
     */
    int upsertSelective(Withdraw record);
}