package io.frame.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;

import io.frame.annotation.SysLog;
import io.frame.common.enums.Constant;
import io.frame.common.enums.Constant.ConfigType;
import io.frame.common.enums.Constant.RechargeKey;
import io.frame.common.utils.DateUtils;
import io.frame.common.utils.R;
import io.frame.dao.entity.Config;
import io.frame.dao.entity.Recharge;
import io.frame.dao.entity.User;
import io.frame.dao.mapper.RechargeMapper;
import io.frame.service.ConfigService;
import io.frame.service.RechargeService;
import io.frame.utils.SessionUtils;

/**
 * 充值接口实现
 * 
 * @author fury
 *
 */
@Transactional
@Service("rechargeService")
public class RechargeServiceImpl implements RechargeService {

	Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);

	/**
	 * 充值配置map
	 */
	private static Map<String, Object> rechagerConfigMap = Maps.newHashMap();

	@Autowired
	RechargeMapper rechargeMapper;

	@Autowired
	ConfigService configService;

	@SysLog("充值申请")
	@Override
	public Map<String, Object> rechargeSubmit(Long userId, BigDecimal money) {
		// 校验充值配置项
		String msg = validRecharge(money);
		if (!StringUtils.isEmpty(msg)) {
			return R.error(msg);
		}
		User currentUser = SessionUtils.getCurrentUser();
		Recharge rechage = new Recharge();
		rechage.setUserId(userId);
		rechage.setUserMobile(currentUser.getUserMobile());
		rechage.setUserName(currentUser.getUserName());
		rechage.setParentId(currentUser.getParentId());
		rechage.setGroupUserIds(currentUser.getGroupUserIds());
		rechage.setAlipayMobile(currentUser.getAlipayMobile());
		rechage.setAlipayName(currentUser.getAlipayName());
		rechage.setRechargeFee(BigDecimal.ZERO);
		rechage.setRechargeMoney(money);
		rechage.setCreateTime(new Date());
		rechage.setCreateUser(currentUser.getUserName());
		String rechargeCode = UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 6);
		rechage.setRechargeCode(rechargeCode);// 随机码
		rechage.setStatus(Constant.Status.ZERO.getValue());
		// 保存
		rechargeMapper.insertSelective(rechage);

		Map<String, Object> map = Maps.newHashMap();
		// 返回随机码，给用户转账时填写备注用。
		map.put("rechargeCode", rechargeCode);
		// 返回支付宝收款二维码图片URL
		map.put("qrCode", rechagerConfigMap.get(RechargeKey.RECHARGE_QRCODE_KEY.getValue()));
		return R.ok(map);
	}

	/**
	 * 校验充值配置项
	 * 
	 * @return
	 */
	public String validRecharge(BigDecimal money) {
		String msg = null;

		List<Config> configList = configService.getConfigListByType(ConfigType.RECHARGE.getValue());
		if (!CollectionUtils.isEmpty(configList)) {
			this.initMap(configList);
			for (Config config : configList) {
				String value = config.getConfigVal();
				if (config.getConfigKey().equals(Constant.RechargeKey.RECHARGE_TIME_RANGE_KEY.getValue())) {
					// 充值时间段
					if (!value.equals("-1")) {
						String[] values = value.split(",");
						// 校验当前时间是不是在时间范围内
						if (!validTime(values[0], values[1])) {
							msg = RechargeKey.getName(config.getConfigKey());
							break;
						}
					}
				}

				if (config.getConfigKey().equals(Constant.RechargeKey.RECHARGE_MIN_KEY.getValue())) {
					// 小于最小金额
					if (!value.equals("-1")) {
						if (money.compareTo(new BigDecimal(value)) == -1) {
							msg = RechargeKey.getName(config.getConfigKey());
							break;
						}
					}
				}

				if (config.getConfigKey().equals(Constant.RechargeKey.RECHARGE_MAX_KEY.getValue())) {
					// 超过最大金额
					if (!value.equals("-1")) {
						if (money.compareTo(new BigDecimal(value)) == 1) {
							msg = RechargeKey.getName(config.getConfigKey());
							break;
						}
					}
				}

				if (config.getConfigKey().equals(Constant.RechargeKey.RECHARGE_SWITCH_KEY.getValue())) {
					// 开关
					if (value.equals("0")) {
						msg = RechargeKey.getName(config.getConfigKey());
						break;
					}
				}

			}
		}
		return msg;
	}

	/**
	 * 初始化静态Map
	 */
	public void initMap(List<Config> configList) {
		if (rechagerConfigMap.size() == 0) {
			for (Config config : configList) {
				rechagerConfigMap.put(config.getConfigKey(), config.getConfigVal());
			}
		}
	}

	public boolean validTime(String start, String end) {
		Date date = new Date();
		Long startTime = DateUtils.parse(start, "HH:mm").getTime();
		Long endTime = DateUtils.parse(end, "HH:mm").getTime();
		Long time = DateUtils.parse(DateUtils.format(date, "HH:mm"), "HH:mm").getTime();
		if (time >= startTime && time <= endTime) {
			return true;
		} else {
			return false;
		}
	}
}
