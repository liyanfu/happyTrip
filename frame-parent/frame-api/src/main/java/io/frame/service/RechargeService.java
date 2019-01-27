
package io.frame.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import io.frame.dao.entity.Recharge;

/**
 * 充值接口
 * 
 * @author fury
 *
 */
public interface RechargeService {

	/**
	 * 创建充值订单
	 * 
	 * @param userId
	 * @param money
	 * @return
	 */
	Map<String, Object> rechargeSubmit(Long userId, BigDecimal money);

	/**
	 * 充值列表
	 * 
	 * @param userId
	 * @return
	 */
	List<Recharge> getRechargeList(Long userId);

	/**
	 * 上传凭证之后修改充值订单凭证状态
	 * 
	 * @param userId
	 * @param rechargeId
	 * @param url
	 */
	void update(Long rechargeId, String url);

}
