package io.frame.modules.happytrip.service;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Payment;

/**
 * 支付接口
 * 
 * @author fury
 *
 */
public interface PaymentService {

	/**
	 * 获取支付
	 * 
	 * @param payment
	 * @return
	 */
	PageUtils<Payment> queryPage(Payment payment);

	/**
	 * 获取支付
	 * 
	 * @param paymentId
	 * @return
	 */
	Payment getInfoById(Long paymentId);

	/**
	 * 新增支付
	 * 
	 * @param payment
	 */
	void save(Payment payment);

	/**
	 * 修改
	 * 
	 * @param payment
	 */
	void update(Payment payment);

	/**
	 * 删除
	 * 
	 * @param paymentId
	 */
	void delete(Long paymentId);

}
