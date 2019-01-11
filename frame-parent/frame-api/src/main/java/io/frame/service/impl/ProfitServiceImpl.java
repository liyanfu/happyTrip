package io.frame.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.frame.common.enums.Constant.ChangeType;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.WalletChange;
import io.frame.dao.entity.WalletChangeExample;
import io.frame.dao.mapper.WalletChangeMapper;
import io.frame.service.ProfitService;

/**
 * 收益信息
 * 
 * @author fury
 *
 */
@Transactional
@Service("profitService")
public class ProfitServiceImpl implements ProfitService {

	Logger logger = LoggerFactory.getLogger(ProfitServiceImpl.class);

	@Autowired
	WalletChangeMapper walletChangeMapper;

	@Transactional(readOnly = true)
	@Override
	public List<WalletChange> getMyCarProfit(Long userId) {
		return this.getWalletChange(userId, ChangeType.CAR_PROFIT_KEY);
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> getGlobalBonus(Long userId) {
		Map<String, Object> map = Maps.newHashMap();
		//分红奖励
		map.put("list", this.getWalletChange(userId, ChangeType.GLOBAL_BONUS_KEY));
		//查询全球分红规则
		return map;
	}
	SPECIAL CONTRIBUTION AWARD
	private List<WalletChange> getWalletChange(Long userId, ChangeType changeType) {
		List<String> showField = Lists.newArrayList();
		showField.add(WalletChange.FD_OPERATORNAME);
		showField.add(WalletChange.FD_OPERATORMONEY);
		showField.add(WalletChange.FD_CREATETIME);
		WalletChangeExample example = new WalletChangeExample();
		example.createCriteria().andUserIdEqualTo(userId).andOperatorTypeEqualTo(changeType.getValue());
		example.setOrderByClause(SqlTools.orderByDescField(WalletChange.FD_CREATETIME));
		return walletChangeMapper.selectByExampleShowField(showField, example);

	}

}
