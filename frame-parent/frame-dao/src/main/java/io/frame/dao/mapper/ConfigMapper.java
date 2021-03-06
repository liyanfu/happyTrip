package io.frame.dao.mapper;

import io.frame.dao.entity.Config;
import io.frame.dao.entity.ConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    int countByExample(ConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    int deleteByExample(ConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long configId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    int insert(Config record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    int insertSelective(Config record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    List<Config> selectByExample(ConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    Config selectByPrimaryKey(Long configId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Config record, @Param("example") ConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Config record, @Param("example") ConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Config record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Config record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    int insertBatch(List<Config> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    int insertBatchSelective(@Param("showField") List<String> showField, @Param("list") List<Config> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    Config selectOneByExample(ConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    Config selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("configId") Long configId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    List<Config> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") ConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    Config selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") ConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    int updateByExampleSelectiveSync(@Param("record") Config record, @Param("example") ConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelectiveSync(Config record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    int upsert(Config record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_config
     *
     * @mbggenerated
     */
    int upsertSelective(Config record);
}