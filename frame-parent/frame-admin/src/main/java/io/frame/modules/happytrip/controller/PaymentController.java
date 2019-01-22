
package io.frame.modules.happytrip.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.frame.common.annotation.SysLog;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.R;
import io.frame.dao.entity.Payment;
import io.frame.modules.happytrip.service.PaymentService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 支付
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/ht/payment")
public class PaymentController extends AbstractController {
	@Autowired
	private PaymentService paymentService;

	/**
	 * 所有支付列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ht:payment:list")
	public PageUtils<Payment> list(Payment payment) {
		return paymentService.queryPage(payment);
	}

	/**
	 * 支付信息
	 */
	@RequestMapping("/info/{paymentId}")
	@RequiresPermissions("ht:payment:info")
	public R info(@PathVariable("paymentId") Long paymentId) {
		return R.ok().put("payment", paymentService.getInfoById(paymentId));
	}

	/**
	 * 新增支付
	 */
	@SysLog("新增支付")
	@RequestMapping("/save")
	@RequiresPermissions("ht:payment:save")
	public R save(@RequestBody Payment payment) {
		paymentService.save(payment);
		return R.ok();
	}

	/**
	 * 修改支付
	 */
	@SysLog("修改支付")
	@RequestMapping("/update")
	@RequiresPermissions("ht:payment:update")
	public R update(@RequestBody Payment payment) {
		paymentService.update(payment);
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@SysLog("修改支付状态")
	@RequestMapping("/status")
	@RequiresPermissions("ht:payment:update")
	public R status(Long paymentId, Integer status) {
		if (status == null || paymentId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setStatus(status);
		paymentService.update(payment);
		return R.ok();
	}

	/**
	 * 删除支付
	 */
	@SysLog("删除支付")
	@RequestMapping("/delete")
	@RequiresPermissions("ht:payment:delete")
	public R delete(Long paymentId) {
		if (paymentId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		paymentService.delete(paymentId);
		return R.ok();
	}

}
