
package io.frame.modules.happytrip.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;

import io.frame.common.enums.Constant;
import io.frame.common.enums.Constant.Sort;
import io.frame.common.enums.Constant.WelfareKey;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.SysUser;
import io.frame.dao.entity.Welfare;
import io.frame.dao.entity.WelfareExample;
import io.frame.dao.mapper.WelfareMapper;
import io.frame.modules.happytrip.service.WelfareService;
import io.frame.modules.sys.service.SysConfigService;
import io.frame.modules.sys.shiro.ShiroUtils;

/**
 * 福利
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class WelfareServiceImpl implements WelfareService {
	Logger logger = LoggerFactory.getLogger(WelfareServiceImpl.class);

	@Autowired
	SysConfigService sysConfigService;

	@Autowired
	WelfareMapper welfareMapper;

	@Transactional(readOnly = true)
	@Override
	public List<Welfare> getWelfareList() {
		List<Welfare> resultList = Lists.newArrayList();
		WelfareKey[] list = Constant.WelfareKey.values();
		for (WelfareKey welfareKey : list) {
			Welfare w = new Welfare();
			w.setWelfareKey(welfareKey.getValue());
			w.setWelfareName(welfareKey.getName());
			resultList.add(w);
		}
		return resultList;
	}

	@Transactional(readOnly = true)
	@Override
	public PageUtils<Welfare> queryPage(Welfare welfare) {

		WelfareExample example = new WelfareExample();
		WelfareExample.Criteria cr = example.createCriteria();

		if (welfare.getWelfareKey() != null) {
			cr.andWelfareKeyEqualToIgnoreNull(welfare.getWelfareKey());
		}

		if (!StringUtils.isEmpty(welfare.getWelfareName())) {
			cr.andWelfareNameLikeIgnoreNull(welfare.getWelfareName() + "%");
		}

		cr.andStatusEqualToIgnoreNull(welfare.getStatus());

		PageHelper.startPage(welfare.getPageNumber(), welfare.getPageSize());
		example.setOrderByClause(Welfare.FD_CREATETIME + Sort.DESC.getValue());

		try {
			Page<Welfare> page = (Page<Welfare>) welfareMapper.selectByExample(example);
			return new PageUtils<Welfare>(page);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Transactional(readOnly = true)
	@Override
	public Welfare getInfoById(Long welfareId) {
		try {
			return welfareMapper.selectByPrimaryKey(welfareId);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	private void valid(String welfareKey, String welfareValue, String remark) {

		// 校验参数
		if (StringUtils.isEmpty(welfareKey)) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}

		// 校验参数
		if (StringUtils.isEmpty(welfareValue)) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}

		// 校验参数
		if (StringUtils.isEmpty(remark)) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}

		if (remark.contains("X") || remark.contains("x")) {
			throw new RRException(ErrorCode.PLEASE_RELPACE_STR_X);
		}

		String[] str = welfareValue.split(",");
		// 校验参数值格式是否正确
		if (welfareKey.equals(Constant.WelfareKey.GLOBAL_BONUS_KEY.getValue())) {
			if (str.length != 1) {
				throw new RRException(ErrorCode.PARAMS_IS_FAILD);
			}
		}
		if (welfareKey.equals(Constant.WelfareKey.TEAM_LEADERSHIP_AWARD_KEY.getValue())) {
			if (str.length != 2) {
				throw new RRException(ErrorCode.PARAMS_IS_FAILD);
			}
		}

		if (welfareKey.equals(Constant.WelfareKey.TEAM_LEADERSHIP_AWARD_KEY.getValue())) {
			if (str.length != 3) {
				throw new RRException(ErrorCode.PARAMS_IS_FAILD);
			}
		}

		for (int i = 0; i < str.length; i++) {
			try {
				Integer.parseInt(str[i]);
			} catch (Exception e) {
				throw new RRException(ErrorCode.PARAMS_IS_FAILD);
			}
		}
	}

	@Override
	public void save(Welfare welfare) {

		SysUser sysUser = ShiroUtils.getUserEntity();
		welfare.setCreateUser(sysUser.getUserName());
		welfare.setCreateTime(new Date());
		welfare.setWelfareName(Constant.WelfareKey.getName(welfare.getWelfareKey()));
		this.valid(welfare.getWelfareKey(), welfare.getWelfareValue(), welfare.getRemark());
		try {
			welfareMapper.insertSelective(welfare);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public void update(Welfare welfare) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		welfare.setUpdateUser(sysUser.getUserName());
		welfare.setUpdateTime(new Date());
		this.valid(welfare.getWelfareKey(), welfare.getWelfareValue(), welfare.getRemark());
		try {
			welfareMapper.updateByPrimaryKeySelective(welfare);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public void delete(Long welfareId) {
		try {
			welfareMapper.deleteByPrimaryKey(welfareId);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

	@Override
	public void status(Welfare welfare) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		welfare.setUpdateUser(sysUser.getUserName());
		welfare.setUpdateTime(new Date());
		try {
			welfareMapper.updateByPrimaryKeySelective(welfare);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

}
