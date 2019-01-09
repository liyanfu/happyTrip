
package io.frame.modules.sys.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;

import io.frame.common.enums.Constant.Sort;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Config;
import io.frame.dao.entity.ConfigExample;
import io.frame.dao.mapper.ConfigMapper;
import io.frame.modules.sys.redis.SysConfigRedis;
import io.frame.modules.sys.service.SysConfigService;

@Transactional
@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {

	Logger logger = LoggerFactory.getLogger(SysConfigServiceImpl.class);
	@Autowired
	private SysConfigRedis sysConfigRedis;

	@Autowired
	private ConfigMapper configMapper;

	@Transactional(readOnly = true)
	@Override
	public PageUtils<Config> queryPage(Config config) {
		ConfigExample example = new ConfigExample();
		example.or().andConfigKeyEqualToIgnoreNull(config.getConfigKey())
				.andConfigStatusEqualToIgnoreNull(config.getConfigStatus());
		example.setOrderByClause(Config.FD_CREATETIME + Sort.DESC.getValue());
		PageHelper.startPage(config.getPageNumber(), config.getPageSize());
		Page<Config> page = (Page<Config>) configMapper.selectByExample(example);
		return new PageUtils<Config>(page);
	}

	@Override
	public void save(Config config) {
		try {
			configMapper.insertSelective(config);
			sysConfigRedis.saveOrUpdate(config);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(Config config) {
		try {
			configMapper.updateByPrimaryKeySelective(config);
			sysConfigRedis.saveOrUpdate(config);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateValueByKey(String key, String value) {

		Config config = new Config();
		config.setConfigVal(value);
		ConfigExample example = new ConfigExample();
		example.or().andConfigKeyEqualTo(key);
		try {
			configMapper.updateByExampleSelective(config, example);
			sysConfigRedis.delete(key);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] ids) {

		try {
			for (Long id : ids) {
				Config config = configMapper.selectByPrimaryKey(id);
				sysConfigRedis.delete(config.getConfigKey());
				configMapper.deleteByPrimaryKey(id);
			}
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public String getValue(String key) {
		Config config = sysConfigRedis.get(key);
		if (config == null) {
			ConfigExample example = new ConfigExample();
			example.or().andConfigKeyEqualTo(key);
			config = configMapper.selectOneByExample(example);
			sysConfigRedis.saveOrUpdate(config);
		}

		return config == null ? null : config.getConfigVal();
	}

	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key);
		if (StringUtils.isNotBlank(value)) {
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RRException("获取参数失败");
		}
	}

	@Override
	public Config getConfigById(Long id) {
		ConfigExample example = new ConfigExample();
		example.or().andConfigIdEqualTo(id);
		return configMapper.selectOneByExample(example);

	}
}
