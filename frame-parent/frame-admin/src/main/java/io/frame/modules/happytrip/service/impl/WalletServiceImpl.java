
package io.frame.modules.happytrip.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.frame.common.annotation.SysLog;
import io.frame.common.enums.Constant.ChangeType;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.dao.entity.SysUser;
import io.frame.dao.entity.Wallet;
import io.frame.dao.entity.WalletChange;
import io.frame.dao.entity.WalletExample;
import io.frame.dao.mapper.WalletMapper;
import io.frame.modules.happytrip.service.WalletChangeService;
import io.frame.modules.happytrip.service.WalletService;
import io.frame.modules.sys.shiro.ShiroUtils;

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

	@Autowired
	WalletChangeService walletChangeService;

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

	@Override
	public void recharge(WalletChange walletChange) {

		SysUser sysUser = ShiroUtils.getUserEntity();
		Long userId = walletChange.getUserId();
		BigDecimal changeMoney = walletChange.getOperatorMoney();
		if (changeMoney.compareTo(BigDecimal.ZERO) < 0) {
			throw new RRException(ErrorCode.THE_AMOUNT_CANNOT_BE_NEGATIVE);
		}

		// 获取用户账户信息
		Wallet wallet = this.getWalletById(userId);
		Wallet updateWallet = new Wallet();
		updateWallet.setWalletId(wallet.getWalletId());
		updateWallet.setUserId(userId);
		updateWallet.setBalance(changeMoney);
		Date date = new Date();
		updateWallet.setUpdateUser(sysUser.getUserName());
		updateWallet.setUpdateTime(date);

		try {
			// 账户加钱
			walletMapper.updateByPrimaryKeySelectiveSync(updateWallet);
			// 记录帐变
			walletChangeService.createWalletChange(userId, changeMoney, walletChange.getRemark(),
					ChangeType.ARTIFICIAL_RECHARGE_KEY);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public void subtract(WalletChange walletChange) {

		SysUser sysUser = ShiroUtils.getUserEntity();
		Long userId = walletChange.getUserId();
		BigDecimal changeMoney = walletChange.getOperatorMoney();

		if (changeMoney.compareTo(BigDecimal.ZERO) < 0) {
			throw new RRException(ErrorCode.THE_AMOUNT_CANNOT_BE_NEGATIVE);
		}

		// 获取用户账户信息
		Wallet wallet = this.getWalletById(userId);
		Wallet updateWallet = new Wallet();
		updateWallet.setWalletId(wallet.getWalletId());
		updateWallet.setUserId(userId);
		updateWallet.setBalance(changeMoney.negate()); // 扣款取负数
		Date date = new Date();
		updateWallet.setUpdateUser(sysUser.getUserName());
		updateWallet.setUpdateTime(date);

		// 判断账户上的钱够不够扣除
		if (wallet.getBalance().compareTo(changeMoney) == -1) {
			throw new RRException(ErrorCode.INSUFFICIENT_BALANCE_OF_GOLDCOINS);
		}
		try {
			// 账户扣钱
			walletMapper.updateByPrimaryKeySelectiveSync(updateWallet);
			// 记录帐变
			walletChangeService.createWalletChange(userId, changeMoney.negate(), walletChange.getRemark(),
					ChangeType.MANUAL_DEDUCTION_KEY);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

}
