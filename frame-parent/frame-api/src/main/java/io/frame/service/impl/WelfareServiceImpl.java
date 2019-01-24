package io.frame.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.frame.common.enums.Constant;
import io.frame.common.enums.Constant.ChangeType;
import io.frame.dao.entity.Welfare;
import io.frame.dao.entity.WelfareExample;
import io.frame.dao.mapper.WelfareMapper;
import io.frame.service.WelfareService;

/**
 * 福利项接口实现
 * 
 * @author fury
 *
 */
@Transactional
@Service("welfareService")
public class WelfareServiceImpl implements WelfareService {

	Logger logger = LoggerFactory.getLogger(WelfareServiceImpl.class);

	@Autowired
	WelfareMapper welfareMapper;

	@Transactional(readOnly = true)
	@Override
	public List<Welfare> getWelfareByKey(ChangeType key) {
		List<String> showField = Lists.newArrayList();
		showField.add(Welfare.FD_WELFAREID);
		showField.add(Welfare.FD_WELFAREKEY);
		showField.add(Welfare.FD_WELFARENAME);
		showField.add(Welfare.FD_WELFAREVALUE);
		showField.add(Welfare.FD_BONUSPOOL);
		showField.add(Welfare.FD_PERCENT);
		showField.add(Welfare.FD_REMARK);
		WelfareExample example = new WelfareExample();
		example.createCriteria().andWelfareKeyEqualTo(key.getValue()).andStatusEqualTo(Constant.Status.ONE.getValue());
		return welfareMapper.selectByExampleShowField(showField, example);
	}

}
