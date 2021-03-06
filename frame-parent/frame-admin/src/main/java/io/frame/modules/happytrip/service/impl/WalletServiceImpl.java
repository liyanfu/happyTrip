
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
import io.frame.modules.happytrip.service.ReportService;
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

	@Autowired
	ReportService reportService;

	@Transactional(readOnly = true)
	@Override
	public Wallet getInfoById(Long walletId) {
		try {
			return walletMapper.selectByPrimaryKey(walletId);
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
		Wallet wallet = this.getInfoByUserId(userId);
		Wallet updateWallet = new Wallet();
		updateWallet.setWalletId(wallet.getWalletId());
		updateWallet.setUserId(userId);
		updateWallet.setBalance(changeMoney);

		try {
			// 账户加钱
			walletMapper.updateByPrimaryKeySelectiveSync(updateWallet);
			// 更新修改者
			Wallet updateWallet2 = new Wallet();
			Date date = new Date();
			updateWallet2.setWalletId(wallet.getWalletId());
			updateWallet2.setUpdateUser(sysUser.getUserName());
			updateWallet2.setUpdateTime(date);
			walletMapper.updateByPrimaryKeySelective(updateWallet2);
			// 记录帐变
			walletChangeService.createWalletChange(userId, changeMoney, walletChange,
					ChangeType.ARTIFICIAL_RECHARGE_KEY);
			// 刷新报表
			reportService.upsert(userId, changeMoney, BigDecimal.ZERO, ChangeType.ARTIFICIAL_RECHARGE_KEY);
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
		Wallet wallet = this.getInfoByUserId(userId);
		Wallet updateWallet = new Wallet();
		updateWallet.setWalletId(wallet.getWalletId());
		updateWallet.setUserId(userId);
		updateWallet.setBalance(changeMoney.negate()); // 扣款取负数

		// 判断账户上的钱够不够扣除
		if (wallet.getBalance().compareTo(changeMoney) == -1) {
			throw new RRException(ErrorCode.INSUFFICIENT_BALANCE_OF_GOLDCOINS);
		}
		try {
			// 账户扣钱
			walletMapper.updateByPrimaryKeySelectiveSync(updateWallet);
			// 更新修改者
			Wallet updateWallet2 = new Wallet();
			Date date = new Date();
			updateWallet2.setWalletId(wallet.getWalletId());
			updateWallet2.setUpdateUser(sysUser.getUserName());
			updateWallet2.setUpdateTime(date);
			walletMapper.updateByPrimaryKeySelective(updateWallet2);
			// 记录帐变
			walletChangeService.createWalletChange(userId, changeMoney.negate(), walletChange,
					ChangeType.MANUAL_DEDUCTION_KEY);
			// 刷新报表
			reportService.upsert(userId, changeMoney, BigDecimal.ZERO, ChangeType.MANUAL_DEDUCTION_KEY);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public Wallet getInfoByUserId(Long userId) {
		try {
			WalletExample example = new WalletExample();
			example.or().andUserIdEqualTo(userId);
			return walletMapper.selectOneByExample(example);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	/**
	 * 订单扣款
	 */
	@Override
	public void orderSubtract(WalletChange walletChange) {

		SysUser sysUser = ShiroUtils.getUserEntity();
		Long userId = walletChange.getUserId();
		BigDecimal changeMoney = walletChange.getOperatorMoney();

		if (changeMoney.compareTo(BigDecimal.ZERO) < 0) {
			throw new RRException(ErrorCode.THE_AMOUNT_CANNOT_BE_NEGATIVE);
		}

		// 获取用户账户信息
		Wallet wallet = this.getInfoByUserId(userId);
		Wallet updateWallet = new Wallet();
		updateWallet.setWalletId(wallet.getWalletId());
		updateWallet.setUserId(userId);
		updateWallet.setBalance(changeMoney.negate()); // 扣款取负数

		// 判断账户上的钱够不够扣除
		if (wallet.getBalance().compareTo(changeMoney) == -1) {
			throw new RRException(ErrorCode.INSUFFICIENT_BALANCE_OF_GOLDCOINS);
		}
		try {
			// 账户扣钱
			walletMapper.updateByPrimaryKeySelectiveSync(updateWallet);
			// 更新修改者
			Wallet updateWallet2 = new Wallet();
			Date date = new Date();
			updateWallet2.setWalletId(wallet.getWalletId());
			updateWallet2.setUpdateUser(sysUser.getUserName());
			updateWallet2.setUpdateTime(date);
			walletMapper.updateByPrimaryKeySelective(updateWallet2);
			// 记录帐变
			walletChangeService.createWalletChange(userId, changeMoney.negate(), walletChange,
					ChangeType.PURCHASE_CAR_SPACE_KEY);
			// 刷新报表
			reportService.upsert(userId, changeMoney, BigDecimal.ZERO, ChangeType.PURCHASE_CAR_SPACE_KEY);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public void recharge(WalletChange walletChange, ChangeType changeType) {

		SysUser sysUser = ShiroUtils.getUserEntity();
		Long userId = walletChange.getUserId();
		BigDecimal changeMoney = walletChange.getOperatorMoney();
		if (changeMoney.compareTo(BigDecimal.ZERO) < 0) {
			throw new RRException(ErrorCode.THE_AMOUNT_CANNOT_BE_NEGATIVE);
		}

		// 获取用户账户信息
		Wallet wallet = this.getInfoByUserId(userId);
		Wallet updateWallet = new Wallet();
		updateWallet.setWalletId(wallet.getWalletId());
		updateWallet.setUserId(userId);
		updateWallet.setBalance(changeMoney);

		try {
			// 账户加钱
			walletMapper.updateByPrimaryKeySelectiveSync(updateWallet);
			// 更新修改者
			Wallet updateWallet2 = new Wallet();
			Date date = new Date();
			updateWallet2.setWalletId(wallet.getWalletId());
			updateWallet2.setUpdateUser(sysUser.getUserName());
			updateWallet2.setUpdateTime(date);
			walletMapper.updateByPrimaryKeySelective(updateWallet2);
			// 记录帐变
			walletChangeService.createWalletChange(userId, changeMoney, walletChange, changeType);
			// 刷新报表
			reportService.upsert(userId, changeMoney, BigDecimal.ZERO, changeType);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@SysLog("提现回退,加钱")
	@Override
	public void subtractBack(WalletChange walletChange, ChangeType changeType) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		Long userId = walletChange.getUserId();
		BigDecimal changeMoney = walletChange.getOperatorMoney();

		if (changeMoney.compareTo(BigDecimal.ZERO) < 0) {
			throw new RRException(ErrorCode.THE_AMOUNT_CANNOT_BE_NEGATIVE);
		}

		// 获取用户账户信息
		Wallet wallet = this.getInfoByUserId(userId);
		Wallet updateWallet = new Wallet();
		updateWallet.setWalletId(wallet.getWalletId());
		updateWallet.setUserId(userId);
		updateWallet.setBalance(changeMoney);

		try {
			// 账户加钱
			walletMapper.updateByPrimaryKeySelectiveSync(updateWallet);
			// 更新修改者
			Wallet updateWallet2 = new Wallet();
			Date date = new Date();
			updateWallet2.setWalletId(wallet.getWalletId());
			updateWallet2.setUpdateUser(sysUser.getUserName());
			updateWallet2.setUpdateTime(date);
			walletMapper.updateByPrimaryKeySelective(updateWallet2);
			// 记录帐变
			walletChangeService.createWalletChange(userId, changeMoney, walletChange, changeType);

		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public void addWallet(WalletChange walletChange, ChangeType changeType) {
		SysUser sysUser = null;
		try {
			sysUser = ShiroUtils.getUserEntity();
		} catch (Exception e) {
			logger.debug("取不到登录账号信息");
		}

		Long userId = walletChange.getUserId();
		BigDecimal changeMoney = walletChange.getOperatorMoney();
		if (changeMoney.compareTo(BigDecimal.ZERO) < 0) {
			throw new RRException(ErrorCode.THE_AMOUNT_CANNOT_BE_NEGATIVE);
		}

		// 获取用户账户信息
		Wallet wallet = this.getInfoByUserId(userId);
		Wallet updateWallet = new Wallet();
		updateWallet.setWalletId(wallet.getWalletId());
		updateWallet.setUserId(userId);
		updateWallet.setBalance(changeMoney);
		updateWallet.setProfitMoney(changeMoney);// 收益金额

		try {
			// 账户加钱
			walletMapper.updateByPrimaryKeySelectiveSync(updateWallet);
			// 更新修改者
			Wallet updateWallet2 = new Wallet();
			Date date = new Date();
			updateWallet2.setWalletId(wallet.getWalletId());
			updateWallet2.setUpdateUser(sysUser == null ? "系统" : sysUser.getUserName());
			updateWallet2.setUpdateTime(date);
			walletMapper.updateByPrimaryKeySelective(updateWallet2);
			// 记录帐变
			walletChangeService.createWalletChange(userId, changeMoney, walletChange, changeType);
			// 刷新报表
			reportService.upsert(userId, changeMoney, BigDecimal.ZERO, changeType);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

}
