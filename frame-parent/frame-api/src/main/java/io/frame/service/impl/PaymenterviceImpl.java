package io.frame.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.frame.common.enums.Constant;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.Payment;
import io.frame.dao.entity.PaymentExample;
import io.frame.dao.mapper.PaymentMapper;
import io.frame.service.Paymentervice;

/**
 * 支付方式接口实现
 * 
 * @author fury
 *
 */
@Transactional
@Service("paymentervice")
public class PaymenterviceImpl implements Paymentervice {

	Logger logger = LoggerFactory.getLogger(PaymenterviceImpl.class);

	@Autowired
	PaymentMapper paymentMapper;

	@Transactional(readOnly = true)
	@Override
	public List<Payment> getPaymentList() {
		List<String> showField = Lists.newArrayList();
		showField.add(Payment.FD_PAYMENTID);
		showField.add(Payment.FD_PAYMENTKEY);
		showField.add(Payment.FD_PAYMENTNAME);
		PaymentExample example = new PaymentExample();
		example.or().andStatusEqualTo(Constant.OrderStatus.ONE.getValue());
		example.setOrderByClause(SqlTools.orderByAscField(Payment.FD_SORT));
		return paymentMapper.selectByExampleShowField(showField, example);
	}

}
