
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
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.DateUtils;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.Recharge;
import io.frame.dao.entity.RechargeExample;
import io.frame.dao.entity.SysUser;
import io.frame.dao.entity.WalletChange;
import io.frame.dao.mapper.RechargeMapper;
import io.frame.modules.happytrip.service.RechargeService;
import io.frame.modules.happytrip.service.WalletService;
import io.frame.modules.sys.service.SysConfigService;
import io.frame.modules.sys.shiro.ShiroUtils;

/**
 * 充值
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class RechargeServiceImpl implements RechargeService {
	Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);

	@Autowired
	RechargeMapper rechargeMapper;

	@Autowired
	WalletService walletService;

	@Autowired
	SysConfigService sysConfigService;

	@Transactional(readOnly = true)
	@Override
	public PageUtils<Recharge> queryPage(Recharge recharge) {
		try {

			RechargeExample example = new RechargeExample();
			RechargeExample.Criteria cr = example.createCriteria();

			Date beginDate = recharge.getBeginTime();
			Date endDate = recharge.getEndTime();
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

			if (recharge.getRechargeId() != null) {
				cr.andRechargeIdEqualToIgnoreNull(recharge.getRechargeId());
			}
			if (!StringUtils.isEmpty(recharge.getUserName())) {
				cr.andUserNameLikeIgnoreNull(recharge.getUserName() + "%");
			}

			if (!StringUtils.isEmpty(recharge.getAlipayMobile())) {
				cr.andAlipayMobileLikeIgnoreNull(recharge.getAlipayMobile() + "%");
			}

			if (!StringUtils.isEmpty(recharge.getUserMobile())) {
				cr.andUserMobileLikeIgnoreNull(recharge.getUserMobile() + "%");
			}

			if (!StringUtils.isEmpty(recharge.getRechargeCode())) {
				cr.andRechargeCodeLikeIgnoreNull(recharge.getRechargeCode() + "%");
			}

			cr.andStatusEqualToIgnoreNull(recharge.getStatus());
			cr.andSubmitStatusEqualToIgnoreNull(recharge.getSubmitStatus());

			cr.andCreateTimeGreaterThanOrEqualToIgnoreNull(beginDate);
			cr.andCreateTimeLessThanIgnoreNull(endDate);

			PageHelper.startPage(recharge.getPageNumber(), recharge.getPageSize());
			example.setOrderByClause(
					StringUtils.isEmpty(recharge.getSortName()) ? SqlTools.orderByDescField(Recharge.FD_CREATETIME)
							: SqlTools.orderByDescField(recharge.getSortName()));
			Page<Recharge> page = (Page<Recharge>) rechargeMapper.selectByExample(example);
			return new PageUtils<Recharge>(page);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public BigDecimal getRechargeTotalMoneyById(Long userId) {
		try {
			List<String> showField = Lists.newArrayList();
			showField.add(SqlTools.sumField(Recharge.FD_RECHARGEMONEY));
			RechargeExample example = new RechargeExample();
			example.or().andUserIdEqualTo(userId).andStatusEqualTo(Constant.Status.ONE.getValue());
			Recharge recharge = rechargeMapper.selectOneByExampleShowField(showField, example);
			return recharge == null ? BigDecimal.ZERO : recharge.getRechargeMoney();
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Recharge getInfoById(Long rechargeId) {
		Recharge recharge = rechargeMapper.selectByPrimaryKey(rechargeId);
		if (recharge != null && !StringUtils.isEmpty(recharge.getSubmitCredentialImg())) {
			// 获取推广域名 链接图片显示
			String value = sysConfigService.getValue(Constant.SystemKey.SYSTEM_SPREAD_DOMAIN_KEY.getValue());
			recharge.setSubmitCredentialImg(value + Constant.readImg + recharge.getSubmitCredentialImg());
		}
		return recharge;
	}

	@Override
	public void update(Recharge recharge) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		recharge.setUpdateUser(sysUser.getUserName());
		recharge.setUpdateTime(new Date());
		recharge.setSubmitCredentialImg(null);// 置空,不需要修改

		Recharge newRecharge = rechargeMapper.selectByPrimaryKey(recharge.getRechargeId());
		// 判断订单状态，
		if (newRecharge.getStatus() == Constant.Status.ONE.getValue()) {
			throw new RRException(ErrorCode.STATUS_IS_COMPLETE);
		}

		if (recharge.getStatus() == Constant.Status.ZERO.getValue()
				&& newRecharge.getStatus() == Constant.Status.ZERO.getValue()) {
			throw new RRException(ErrorCode.RECHARGE_STATUS_IS_WAIT);
		}

		try {
			rechargeMapper.updateByPrimaryKeySelective(recharge);

			// 如果是把状态改为已完成,则给用户加钱,更新账变,刷新报表.
			if (recharge.getStatus() == Constant.Status.ONE.getValue()) {
				WalletChange walletChange = new WalletChange();
				walletChange.setUserId(newRecharge.getUserId());
				walletChange.setOperatorMoney(newRecharge.getRechargeMoney());
				walletChange.setRelationId(newRecharge.getRechargeId());
				walletChange.setRemark(recharge.getFrontRemark());
				walletService.recharge(walletChange, ChangeType.RECHARGE_IN_KEY);
			}

		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public void delete(Long rechargeId) {
		Recharge newRecharge = rechargeMapper.selectByPrimaryKey(rechargeId);
		// 判断状态，
		if (newRecharge.getStatus() == Constant.Status.ONE.getValue()) {
			throw new RRException(ErrorCode.THIS_DATA_IS_COMPLETE);
		}
		try {
			rechargeMapper.deleteByPrimaryKey(rechargeId);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

}
