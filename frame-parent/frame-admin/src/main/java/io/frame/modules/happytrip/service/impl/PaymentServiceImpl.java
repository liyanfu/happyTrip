
package io.frame.modules.happytrip.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import io.frame.common.enums.Constant.Numbers;
import io.frame.common.enums.Constant.Sort;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Payment;
import io.frame.dao.entity.PaymentExample;
import io.frame.dao.entity.SysUser;
import io.frame.dao.mapper.PaymentMapper;
import io.frame.modules.happytrip.service.PaymentService;
import io.frame.modules.happytrip.service.ProductService;
import io.frame.modules.sys.shiro.ShiroUtils;

/**
 * 支付
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class PaymentServiceImpl implements PaymentService {
	Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Autowired
	PaymentMapper paymentMapper;

	@Autowired
	ProductService productService;

	@Transactional(readOnly = true)
	@Override
	public PageUtils<Payment> queryPage(Payment payment) {

		String paymentName = null;
		if (!StringUtils.isEmpty(payment.getPaymentName())) {
			paymentName = payment.getPaymentName() + "%";
		}
		PaymentExample example = new PaymentExample();
		example.or().andPaymentNameLikeIgnoreNull(paymentName).andStatusEqualToIgnoreNull(payment.getStatus());
		PageHelper.startPage(payment.getPageNumber(), payment.getPageSize());
		example.setOrderByClause(Payment.FD_CREATETIME + Sort.DESC.getValue());

		try {
			Page<Payment> page = (Page<Payment>) paymentMapper.selectByExample(example);
			return new PageUtils<Payment>(page);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Transactional(readOnly = true)
	@Override
	public Payment getInfoById(Long paymentId) {
		try {
			return paymentMapper.selectByPrimaryKey(paymentId);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Override
	public void save(Payment payment) {
		PaymentExample example = new PaymentExample();
		example.or().andPaymentNameEqualTo(payment.getPaymentName());
		// 校验是否存在相同支付
		int count = paymentMapper.countByExample(example);
		if (count > Numbers.ZERO.getValue()) {
			throw new RRException(ErrorCode.PRODUCT_TYPE_ANDM_NAME_EXIST);
		}
		SysUser sysUser = ShiroUtils.getUserEntity();
		try {
			payment.setCreateUser(sysUser.getUserName());
			payment.setCreateTime(new Date());
			paymentMapper.insertSelective(payment);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

	@Override
	public void update(Payment payment) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		payment.setUpdateUser(sysUser.getUserName());
		payment.setUpdateTime(new Date());
		try {
			paymentMapper.updateByPrimaryKeySelective(payment);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

	@Override
	public void delete(Long paymentId) {
		try {
			paymentMapper.deleteByPrimaryKey(paymentId);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

}
