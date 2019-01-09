
package io.frame.modules.sys.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.frame.common.utils.RedisKeys;
import io.frame.common.utils.RedisUtils;
import io.frame.dao.entity.Config;

/**
 * 系统配置Redis
 * 
 * @author fury
 *
 */
@Component
public class SysConfigRedis {
	@Autowired
	private RedisUtils redisUtils;

	public void saveOrUpdate(Config config) {
		if (config == null) {
			return;
		}
		String key = RedisKeys.getSysConfigKey(config.getConfigKey());
		redisUtils.set(key, config);
	}

	public void delete(String configKey) {
		String key = RedisKeys.getSysConfigKey(configKey);
		redisUtils.delete(key);
	}

	public Config get(String configKey) {
		String key = RedisKeys.getSysConfigKey(configKey);
		return redisUtils.get(key, Config.class);
	}
}
