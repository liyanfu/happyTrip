
package io.frame.service;

import java.util.List;

import io.frame.dao.entity.Config;

/**
 * 配置项接口
 * 
 * @author fury
 *
 */
public interface ConfigService {

	/**
	 * 根据配置Key值查询Value
	 * 
	 * @param configKey
	 * @return
	 */
	String getConfigByKey(String configKey);

	/**
	 * 根据key，获取value的Object对象
	 * 
	 * @param key   key
	 * @param clazz Object对象
	 */
	public <T> T getConfigObject(String key, Class<T> clazz);

	/**
	 * 根据配置Type查询配置项集合
	 * 
	 * @param configType
	 * @return
	 */
	List<Config> getConfigListByType(String configType);
}
