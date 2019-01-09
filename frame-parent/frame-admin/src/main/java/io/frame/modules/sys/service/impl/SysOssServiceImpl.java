
package io.frame.modules.sys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.SysOss;
import io.frame.dao.entity.SysOssExample;
import io.frame.dao.mapper.SysOssMapper;
import io.frame.modules.sys.service.SysOssService;

@Transactional
@Service
public class SysOssServiceImpl implements SysOssService {

	Logger logger = LoggerFactory.getLogger(SysOssServiceImpl.class);

	@Autowired
	SysOssMapper sysOssMapper;

	@Transactional(readOnly = true)
	@Override
	public PageUtils<SysOss> queryPage(SysOss sysOss) {

		SysOssExample example = new SysOssExample();

		PageHelper.startPage(sysOss.getPageNumber(), sysOss.getPageSize());
		Page<SysOss> page = (Page<SysOss>) sysOssMapper.selectByExample(example);
		return new PageUtils<SysOss>(page);
	}

	@Override
	public void insert(SysOss sysOss) {
		try {
			sysOssMapper.insertSelective(sysOss);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public void deleteBatchIds(List<Long> asList) {
		SysOssExample example = new SysOssExample();
		example.or().andIdIn(asList);
		try {
			sysOssMapper.deleteByExample(example);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

}
