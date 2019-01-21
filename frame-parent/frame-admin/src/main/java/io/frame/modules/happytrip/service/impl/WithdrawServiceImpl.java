
package io.frame.modules.happytrip.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import io.frame.common.enums.Constant.OrderStatus;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.DateUtils;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.SysUser;
import io.frame.dao.entity.WalletChange;
import io.frame.dao.entity.Withdraw;
import io.frame.dao.entity.WithdrawExample;
import io.frame.dao.mapper.WithdrawMapper;
import io.frame.modules.happytrip.service.ReportService;
import io.frame.modules.happytrip.service.WalletChangeService;
import io.frame.modules.happytrip.service.WalletService;
import io.frame.modules.happytrip.service.WithdrawService;
import io.frame.modules.sys.shiro.ShiroUtils;

/**
 * 提现
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class WithdrawServiceImpl implements WithdrawService {
	Logger logger = LoggerFactory.getLogger(WithdrawServiceImpl.class);

	@Autowired
	WithdrawMapper withdrawMapper;

	@Autowired
	WalletService walletService;

	@Autowired
	ReportService reportService;

	@Autowired
	WalletChangeService walletChangeService;

	@Transactional(readOnly = true)
	@Override
	public PageUtils<Withdraw> queryPage(Withdraw withdraw) {
		try {

			WithdrawExample example = new WithdrawExample();
			WithdrawExample.Criteria cr = example.createCriteria();

			Date beginDate = withdraw.getBeginTime();
			Date endDate = withdraw.getEndTime();
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

			if (withdraw.getWithdrawId() != null) {
				cr.andWithdrawIdEqualToIgnoreNull(withdraw.getWithdrawId());
			}
			if (!StringUtils.isEmpty(withdraw.getUserName())) {
				cr.andUserNameLikeIgnoreNull(withdraw.getUserName() + "%");
			}

			if (!StringUtils.isEmpty(withdraw.getAlipayMobile())) {
				cr.andAlipayMobileLikeIgnoreNull(withdraw.getAlipayMobile() + "%");
			}

			if (!StringUtils.isEmpty(withdraw.getUserMobile())) {
				cr.andUserMobileLikeIgnoreNull(withdraw.getUserMobile() + "%");
			}

			cr.andStatusEqualToIgnoreNull(withdraw.getStatus());

			cr.andCreateTimeGreaterThanOrEqualToIgnoreNull(beginDate);
			cr.andCreateTimeLessThanIgnoreNull(endDate);

			PageHelper.startPage(withdraw.getPageNumber(), withdraw.getPageSize());
			example.setOrderByClause(
					StringUtils.isEmpty(withdraw.getSortName()) ? SqlTools.orderByDescField(Withdraw.FD_CREATETIME)
							: SqlTools.orderByDescField(withdraw.getSortName()));
			Page<Withdraw> page = (Page<Withdraw>) withdrawMapper.selectByExample(example);
			return new PageUtils<Withdraw>(page);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public BigDecimal getWithdrawTotalMoneyById(Long userId) {
		try {
			List<String> showField = Lists.newArrayList();
			showField.add(SqlTools.sumField(Withdraw.FD_WITHDRAWMONEY));
			WithdrawExample example = new WithdrawExample();
			example.or().andUserIdEqualTo(userId).andStatusEqualTo(Constant.Status.ONE.getValue());
			Withdraw withdraw = withdrawMapper.selectOneByExampleShowField(showField, example);
			return withdraw == null ? BigDecimal.ZERO : withdraw.getWithdrawMoney();
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}
	}

	@Override
	public Withdraw getInfoById(Long withdrawId) {
		try {
			return withdrawMapper.selectByPrimaryKey(withdrawId);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}
	}

	@Override
	public void update(Withdraw withdraw) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		withdraw.setUpdateUser(sysUser.getUserName());
		withdraw.setUpdateTime(new Date());

		Withdraw newWithdraw = withdrawMapper.selectByPrimaryKey(withdraw.getWithdrawId());
		// 判断订单状态，
		if (newWithdraw.getStatus() != Constant.Status.ZERO.getValue()) {
			throw new RRException(ErrorCode.ONLY_UPDATE_ZERO_WITHDRAW_STATUS);
		}

		if (withdraw.getStatus() == Constant.Status.ZERO.getValue()
				&& newWithdraw.getStatus() == Constant.Status.ZERO.getValue()) {
			throw new RRException(ErrorCode.WITHDRAW_STATUS_IS_WAIT);
		}

		try {
			withdrawMapper.updateByPrimaryKeySelective(withdraw);

			// 如果是把状态改为已完成,只需刷新报表.
			if (withdraw.getStatus() == Constant.Status.ONE.getValue()) {
				reportService.upsert(newWithdraw.getUserId(), newWithdraw.getWithdrawMoney(),
						newWithdraw.getWithdrawFee(), ChangeType.WITHDRAW_OUT_KEY);
			}

			// 如果是把状态改为异常或者已取消，给用户把钱返回去 ,不需要更新报表
			if (withdraw.getStatus() == Constant.Status.TWO.getValue()
					|| withdraw.getStatus() == Constant.Status.THREE.getValue()) {
				// 金额
				WalletChange walletChange = new WalletChange();
				walletChange.setUserId(newWithdraw.getUserId());
				walletChange.setOperatorMoney(newWithdraw.getWithdrawRealMoney());
				walletChange.setRelationId(newWithdraw.getWithdrawId());
				walletChange.setRemark(withdraw.getFrontRemark());
				walletService.subtractBack(walletChange, ChangeType.WITHDRAW_OUT_BACK_KEY);
				// 手续费
				walletChange = new WalletChange();
				walletChange.setUserId(newWithdraw.getUserId());
				walletChange.setOperatorMoney(newWithdraw.getWithdrawFee());
				walletChange.setRelationId(newWithdraw.getWithdrawId());
				walletService.subtractBack(walletChange, ChangeType.WITHDRAW_OUT_BACK_FEE_KEY);
			}
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public void delete(Long withdrawId) {
		Withdraw withdraw = withdrawMapper.selectByPrimaryKey(withdrawId);
		// 判断订单状态，
		if (withdraw.getStatus() == OrderStatus.ONE.getValue()) {
			throw new RRException(ErrorCode.THIS_DATA_IS_COMPLETE);
		}
		try {
			withdrawMapper.deleteByPrimaryKey(withdrawId);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

}
