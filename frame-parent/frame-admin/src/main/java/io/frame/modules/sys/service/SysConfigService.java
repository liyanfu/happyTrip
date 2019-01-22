
package io.frame.modules.sys.service;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Config;

/**
 * 系统配置信息
 * 
 * @author fury
 *
 */
public interface SysConfigService {

	PageUtils<Config> queryPage(Config config);

	/**
	 * 保存配置信息
	 */
	public void save(Config config);

	/**
	 * 更新配置信息
	 */
	public void update(Config config);

	/**
	 * 根据key，更新value
	 */
	public void updateValueByKey(String key, String value);

	/**
	 * 删除配置信息
	 */
	public void deleteBatch(Long[] ids);

	/**
	 * 根据key，获取配置的value值
	 * 
	 * @param key key
	 */
	public String getValue(String key);

	/**
	 * 根据key，获取value的Object对象
	 * 
	 * @param key   key
	 * @param clazz Object对象
	 */
	public <T> T getConfigObject(String key, Class<T> clazz);

	/**
	 * 根据Id获取配置信息
	 * 
	 * @param id
	 * @return
	 */
	Config getConfigById(Long id);

	/**
	 * 更新配置信息
	 */
	void status(Config config);

}
