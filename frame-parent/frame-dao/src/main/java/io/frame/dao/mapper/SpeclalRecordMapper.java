package io.frame.dao.mapper;

import io.frame.dao.entity.SpeclalRecord;
import io.frame.dao.entity.SpeclalRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SpeclalRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    int countByExample(SpeclalRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    int deleteByExample(SpeclalRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    int insert(SpeclalRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    int insertSelective(SpeclalRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    List<SpeclalRecord> selectByExample(SpeclalRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    SpeclalRecord selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SpeclalRecord record, @Param("example") SpeclalRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SpeclalRecord record, @Param("example") SpeclalRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SpeclalRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SpeclalRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    int insertBatch(List<SpeclalRecord> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    int insertBatchSelective(@Param("showField") List<String> showField, @Param("list") List<SpeclalRecord> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    SpeclalRecord selectOneByExample(SpeclalRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    SpeclalRecord selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("id") Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    List<SpeclalRecord> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") SpeclalRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    SpeclalRecord selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") SpeclalRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    int updateByExampleSelectiveSync(@Param("record") SpeclalRecord record, @Param("example") SpeclalRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelectiveSync(SpeclalRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    int upsert(SpeclalRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_speclal_record
     *
     * @mbggenerated
     */
    int upsertSelective(SpeclalRecord record);
}