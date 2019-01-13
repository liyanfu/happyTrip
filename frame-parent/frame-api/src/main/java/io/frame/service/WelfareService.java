
package io.frame.service;

import java.util.List;

import io.frame.common.enums.Constant.ChangeType;
import io.frame.dao.entity.Welfare;

/**
 * 福利奖项配置接口
 * 
 * @author fury
 *
 */
public interface WelfareService {

	/**
	 * 根据Key查询福利奖项配置集合
	 * 
	 * @param key
	 * @return
	 */
	public List<Welfare> getWelfareByKey(ChangeType key);
}
