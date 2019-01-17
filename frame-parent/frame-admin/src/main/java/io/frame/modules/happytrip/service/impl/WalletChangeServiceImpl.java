
package io.frame.modules.happytrip.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import io.frame.common.annotation.SysLog;
import io.frame.common.enums.Constant.ChangeType;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.SysUser;
import io.frame.dao.entity.User;
import io.frame.dao.entity.Wallet;
import io.frame.dao.entity.WalletChange;
import io.frame.dao.entity.WalletChangeExample;
import io.frame.dao.mapper.WalletChangeMapper;
import io.frame.modules.happytrip.service.UserService;
import io.frame.modules.happytrip.service.WalletChangeService;
import io.frame.modules.happytrip.service.WalletService;
import io.frame.modules.sys.shiro.ShiroUtils;

/**
 * 钱包帐变
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class WalletChangeServiceImpl implements WalletChangeService {
	Logger logger = LoggerFactory.getLogger(WalletChangeServiceImpl.class);

	@Autowired
	WalletChangeMapper walletChangeMapper;

	@Autowired
	WalletService walletService;

	@Autowired
	UserService userService;

	@Transactional(readOnly = true)
	@Override
	public PageUtils<WalletChange> queryPage(WalletChange walletChange) {

		String userName = null;
		if (!StringUtils.isEmpty(walletChange.getUserName())) {
			userName = walletChange.getUserName() + "%";
		}
		WalletChangeExample example = new WalletChangeExample();
		example.or().andUserNameLikeIgnoreNull(userName)//
				.andUserIdEqualToIgnoreNull(walletChange.getUserId())//
				.andOperatorTypeEqualToIgnoreNull(walletChange.getOperatorType());
		PageHelper.startPage(walletChange.getPageNumber(), walletChange.getPageSize());
		example.setOrderByClause(SqlTools.orderByDescField(walletChange.FD_CREATETIME));

		try {
			Page<WalletChange> page = (Page<WalletChange>) walletChangeMapper.selectByExample(example);
			return new PageUtils<WalletChange>(page);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@SysLog("创建帐变")
	@Override
	public void createWalletChange(Long userId, BigDecimal changeMoney, String remark, ChangeType changeType) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		Wallet wallet = walletService.getWalletById(userId);
		User user = userService.getUserById(userId);
		WalletChange walletChange = new WalletChange();
		walletChange.setUserId(userId);
		walletChange.setUserName(user.getUserName());
		walletChange.setBalance(wallet.getBalance());
		walletChange.setOperatorMoney(changeMoney);
		walletChange.setOperatorType(changeType.getValue());
		walletChange.setOperatorName(changeType.getName());
		walletChange.setCreateTime(new Date());
		walletChange.setCreateUser(sysUser.getUserName());
		walletChange.setRemark(remark);
		try {
			walletChangeMapper.insertSelective(walletChange);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

}
