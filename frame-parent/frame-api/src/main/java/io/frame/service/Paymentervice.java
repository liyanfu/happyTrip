
package io.frame.service;

import java.util.List;

import io.frame.dao.entity.Payment;

/**
 * 支付方式接口
 * 
 * @author fury
 *
 */
public interface Paymentervice {

	/**
	 * 获取充值方式接口
	 * 
	 * @return
	 */
	List<Payment> getPaymentList();

}
