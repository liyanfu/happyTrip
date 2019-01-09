
package io.frame.service;

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
	 * @param key
	 *            key
	 * @param clazz
	 *            Object对象
	 */
	public <T> T getConfigObject(String key, Class<T> clazz);
}
