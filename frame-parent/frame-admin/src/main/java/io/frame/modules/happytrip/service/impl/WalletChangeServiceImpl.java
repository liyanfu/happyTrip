
package io.frame.modules.happytrip.service.impl;

import java.math.BigDecimal;
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
import io.frame.common.enums.Constant.ChangeType;
import io.frame.common.enums.Constant.Sort;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.DateUtils;
import io.frame.common.utils.PageUtils;
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
	public List<WalletChange> getWalletChangeTypelist() {
		List<WalletChange> resultList = Lists.newArrayList();
		ChangeType[] list = Constant.ChangeType.values();
		for (ChangeType bean : list) {
			WalletChange w = new WalletChange();
			w.setOperatorType(bean.getValue());
			w.setOperatorName(bean.getName());
			resultList.add(w);
		}
		return resultList;
	}

	@Transactional(readOnly = true)
	@Override
	public PageUtils<WalletChange> queryPage(WalletChange walletChange) {

		WalletChangeExample example = new WalletChangeExample();
		WalletChangeExample.Criteria cr = example.createCriteria();

		String userName = null;
		if (!StringUtils.isEmpty(walletChange.getUserName())) {
			userName = walletChange.getUserName() + "%";
		}

		Date beginDate = walletChange.getBeginTime();
		Date endDate = walletChange.getEndTime();
		// 当天0点
		if (beginDate == null) {
			beginDate = DateUtils.parse(new Date(), "yyyy-MM-dd");
		}

		// 明天0点
		if (endDate == null) {
			endDate = DateUtils.addDateDays(DateUtils.parse(new Date(), "yyyy-MM-dd"), 1);
		}

		if (beginDate == endDate) {
			endDate = DateUtils.addDateDays(DateUtils.parse(new Date(), "yyyy-MM-dd"), 1);
		}

		cr.andUserNameLikeIgnoreNull(userName);
		cr.andOperatorTypeEqualToIgnoreNull(walletChange.getOperatorType());
		cr.andCreateTimeGreaterThanOrEqualToIgnoreNull(beginDate);
		cr.andCreateTimeLessThanIgnoreNull(endDate);
		PageHelper.startPage(walletChange.getPageNumber(), walletChange.getPageSize());
		example.setOrderByClause(WalletChange.FD_CREATETIME + Sort.DESC.getValue());

		try {
			Page<WalletChange> page = (Page<WalletChange>) walletChangeMapper.selectByExample(example);
			return new PageUtils<WalletChange>(page);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Override
	public void createWalletChange(Long userId, BigDecimal changeMoney, WalletChange walletChange,
			ChangeType changeType) {
		SysUser sysUser = null;
		try {
			sysUser = ShiroUtils.getUserEntity();
		} catch (Exception e) {
			// 这一部是定时任务时,会取不到当前登录用户,报异常
		}

		Wallet wallet = walletService.getInfoByUserId(userId);
		User user = userService.getInfoById(userId);
		WalletChange newWalletChange = new WalletChange();
		newWalletChange.setUserId(userId);
		newWalletChange.setUserName(user.getUserName());
		newWalletChange.setBalance(wallet.getBalance());
		newWalletChange.setOperatorMoney(changeMoney);
		newWalletChange.setOperatorType(changeType.getValue());
		newWalletChange.setOperatorName(changeType.getName());
		newWalletChange.setCreateTime(new Date());
		newWalletChange.setCreateUser(sysUser == null ? "系统" : sysUser.getUserName());
		newWalletChange.setRemark(walletChange.getRemark());
		newWalletChange.setRelationId(walletChange.getRelationId());
		try {
			walletChangeMapper.insertSelective(newWalletChange);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

}
