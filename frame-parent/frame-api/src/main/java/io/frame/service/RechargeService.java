
package io.frame.service;

import java.math.BigDecimal;
import java.util.Map;

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

}
