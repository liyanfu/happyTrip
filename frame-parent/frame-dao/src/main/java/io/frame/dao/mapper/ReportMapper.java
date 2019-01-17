package io.frame.dao.mapper;

import io.frame.dao.entity.Report;
import io.frame.dao.entity.ReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReportMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    int countByExample(ReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    int deleteByExample(ReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long reportId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    int insert(Report record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    int insertSelective(Report record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    List<Report> selectByExample(ReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    Report selectByPrimaryKey(Long reportId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Report record, @Param("example") ReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Report record, @Param("example") ReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Report record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Report record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    int insertBatch(List<Report> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    int insertBatchSelective(@Param("showField") List<String> showField, @Param("list") List<Report> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    Report selectOneByExample(ReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    Report selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("reportId") Long reportId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    List<Report> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") ReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    Report selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") ReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    int updateByExampleSelectiveSync(@Param("record") Report record, @Param("example") ReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelectiveSync(Report record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    int upsert(Report record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_report
     *
     * @mbggenerated
     */
    int upsertSelective(Report record);
}