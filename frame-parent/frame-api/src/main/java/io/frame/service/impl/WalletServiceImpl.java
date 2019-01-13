
package io.frame.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.frame.annotation.SysLog;
import io.frame.dao.entity.Wallet;
import io.frame.dao.entity.WalletExample;
import io.frame.dao.mapper.WalletMapper;
import io.frame.service.WalletService;

/**
 * 钱包
 * 
 * @author fury
 *
 */
@Transactional
@Service("walletService")
public class WalletServiceImpl implements WalletService {

	static Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

	@Autowired
	WalletMapper walletMapper;

	@SysLog("创建钱包")
	@Override
	public void createWallet(Long userId, String userName) {
		Date date = new Date();
		Wallet wallet = new Wallet();
		wallet.setUserId(userId);
		wallet.setBalance(BigDecimal.ZERO);
		wallet.setProfitMoney(BigDecimal.ZERO);
		wallet.setCreateTime(date);
		wallet.setCreateUser(userName);
		walletMapper.insertSelective(wallet);

	}

	@Transactional(readOnly = true)
	@Override
	public Wallet getWallet(Long userId) {
		WalletExample example = new WalletExample();
		example.createCriteria().andUserIdEqualTo(userId);
		return walletMapper.selectOneByExample(example);
	}

	@SysLog("钱包扣款")
	@Override
	public void deduction(Long walletId, BigDecimal buyMoney) {
		Wallet wallet = new Wallet();
		wallet.setWalletId(walletId);
		wallet.setBalance(buyMoney.negate()); // 扣款取负数
		walletMapper.updateByPrimaryKeySelectiveSync(wallet);

	}

}
