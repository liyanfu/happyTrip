
package io.frame.modules.happytrip.service.impl;

import java.math.BigDecimal;
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
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.Withdraw;
import io.frame.dao.entity.WithdrawExample;
import io.frame.dao.mapper.WithdrawMapper;
import io.frame.modules.happytrip.service.WithdrawService;

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

	@Transactional(readOnly = true)
	@Override
	public PageUtils<Withdraw> queryPage(Withdraw withdraw) {
		try {

			WithdrawExample example = new WithdrawExample();
			WithdrawExample.Criteria cr = example.createCriteria();
			cr.andUserNameEqualToIgnoreNull(withdraw.getUserName());
			cr.andStatusEqualToIgnoreNull(withdraw.getStatus());
			example.or().andStatusEqualTo(Constant.Status.ONE.getValue());
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

}
