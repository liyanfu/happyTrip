package io.frame.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import io.frame.common.exception.RRException;
import io.frame.dao.entity.Config;
import io.frame.dao.entity.ConfigExample;
import io.frame.dao.mapper.ConfigMapper;
import io.frame.exception.ErrorCode;
import io.frame.service.ConfigService;

/**
 * 配置项接口实现
 * 
 * @author fury
 *
 */
@Transactional
@Service("configService")
public class ConfigServiceImpl implements ConfigService {

	Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

	@Autowired
	ConfigMapper configMapper;

	@Override
	public String getConfigByKey(String configKey) {
		List<String> showField = Lists.newArrayList();
		showField.add(Config.FD_CONFIGVAL);
		ConfigExample example = new ConfigExample();
		example.or().andConfigKeyEqualTo(configKey);
		try {
			Config config = configMapper.selectOneByExampleShowField(showField, example);
			return config == null ? null : config.getConfigVal();
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getConfigByKey(key);
		if (StringUtils.isNotBlank(value)) {
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RRException(ErrorCode.GET_PARAMS_ERROR);
		}
	}
}
