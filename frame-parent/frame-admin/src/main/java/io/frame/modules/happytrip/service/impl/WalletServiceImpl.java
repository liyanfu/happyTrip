
package io.frame.modules.happytrip.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.frame.common.annotation.SysLog;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.dao.entity.Wallet;
import io.frame.dao.entity.WalletExample;
import io.frame.dao.mapper.WalletMapper;
import io.frame.modules.happytrip.service.WalletService;

/**
 * 用户钱包
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class WalletServiceImpl implements WalletService {
	Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

	@Autowired
	WalletMapper walletMapper;

	@Transactional(readOnly = true)
	@Override
	public Wallet getWalletById(Long userId) {
		try {
			WalletExample example = new WalletExample();
			example.or().andUserIdEqualTo(userId);
			return walletMapper.selectOneByExample(example);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@SysLog("创建钱包")
	@Override
	public void createWallet(Long userId, String createUser) {
		Date date = new Date();
		Wallet wallet = new Wallet();
		wallet.setUserId(userId);
		wallet.setBalance(BigDecimal.ZERO);
		wallet.setProfitMoney(BigDecimal.ZERO);
		wallet.setCreateTime(date);
		wallet.setCreateUser(createUser);
		walletMapper.insertSelective(wallet);
	}
}
