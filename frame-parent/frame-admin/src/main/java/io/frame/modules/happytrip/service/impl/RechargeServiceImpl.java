
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
import io.frame.dao.entity.Recharge;
import io.frame.dao.entity.RechargeExample;
import io.frame.dao.mapper.RechargeMapper;
import io.frame.modules.happytrip.service.RechargeService;

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

	@Transactional(readOnly = true)
	@Override
	public PageUtils<Recharge> queryPage(Recharge recharge) {
		try {

			RechargeExample example = new RechargeExample();
			RechargeExample.Criteria cr = example.createCriteria();
			cr.andUserNameEqualToIgnoreNull(recharge.getUserName());
			cr.andStatusEqualToIgnoreNull(recharge.getStatus());
			example.or().andStatusEqualTo(Constant.Status.ONE.getValue());
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

}
