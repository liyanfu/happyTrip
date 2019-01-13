
package io.frame.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.frame.annotation.SysLog;
import io.frame.common.enums.Constant.ChangeType;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.User;
import io.frame.dao.entity.Wallet;
import io.frame.dao.entity.WalletChange;
import io.frame.dao.entity.WalletChangeExample;
import io.frame.dao.mapper.WalletChangeMapper;
import io.frame.service.WalletChangeService;
import io.frame.service.WalletService;
import io.frame.utils.SessionUtils;

/**
 * 钱包
 * 
 * @author fury
 *
 */
@Transactional
@Service("walletChangeService")
public class WalletChangeServiceImpl implements WalletChangeService {

	static Logger logger = LoggerFactory.getLogger(WalletChangeServiceImpl.class);

	@Autowired
	WalletChangeMapper walletChangeMapper;

	@Autowired
	WalletService walletService;

	/**
	 * 查询帐变信息
	 * 
	 * @param userId
	 * @param changeType
	 * @return
	 */
	@Transactional(readOnly = true)
	@Override
	public List<WalletChange> getWalletChangeList(Long userId, ChangeType changeType) {
		List<String> showField = Lists.newArrayList();
		showField.add(WalletChange.FD_OPERATORNAME);
		showField.add(WalletChange.FD_OPERATORMONEY);
		showField.add(WalletChange.FD_CREATETIME);
		WalletChangeExample example = new WalletChangeExample();
		example.createCriteria().andUserIdEqualTo(userId).andOperatorTypeEqualTo(changeType.getValue());
		example.setOrderByClause(SqlTools.orderByDescField(WalletChange.FD_CREATETIME));
		return walletChangeMapper.selectByExampleShowField(showField, example);
	}

	@SysLog("更新帐变")
	@Override
	public void createWalletChange(Long userId, BigDecimal money, Long orderId, ChangeType changeType) {

		// 查询一遍最新的钱包余额
		Wallet wallet = walletService.getWallet(userId);
		User user = SessionUtils.getCurrentUser();
		WalletChange walletChange = new WalletChange();
		walletChange.setBalance(wallet.getBalance());// 剩余余额
		walletChange.setOperatorMoney(money);
		walletChange.setCreateUser(user.getUserName());
		walletChange.setCreateTime(new Date());
		walletChange.setOperatorName(changeType.getName());
		walletChange.setOperatorType(changeType.getValue());
		walletChange.setRelation_id(orderId);
		walletChange.setUserId(userId);
		walletChange.setUserName(user.getUserName());
		walletChangeMapper.insertSelective(walletChange);
	}

}
