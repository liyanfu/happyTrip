package io.frame.dao.mapper;

import io.frame.dao.entity.SysDept;
import io.frame.dao.entity.SysDeptExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysDeptMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    int countByExample(SysDeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    int deleteByExample(SysDeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long deptId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    int insert(SysDept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    int insertSelective(SysDept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    List<SysDept> selectByExample(SysDeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    SysDept selectByPrimaryKey(Long deptId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SysDept record, @Param("example") SysDeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SysDept record, @Param("example") SysDeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysDept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SysDept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    int insertBatch(List<SysDept> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    int insertBatchSelective(@Param("showField") List<String> showField, @Param("list") List<SysDept> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    SysDept selectOneByExample(SysDeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    SysDept selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("deptId") Long deptId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    List<SysDept> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") SysDeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    SysDept selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") SysDeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    int updateByExampleSelectiveSync(@Param("record") SysDept record, @Param("example") SysDeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelectiveSync(SysDept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    int upsert(SysDept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_sys_dept
     *
     * @mbggenerated
     */
    int upsertSelective(SysDept record);
}