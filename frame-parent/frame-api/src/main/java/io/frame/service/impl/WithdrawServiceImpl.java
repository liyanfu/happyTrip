package io.frame.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.frame.annotation.SysLog;
import io.frame.common.enums.Constant;
import io.frame.common.enums.Constant.ChangeType;
import io.frame.common.enums.Constant.ConfigType;
import io.frame.common.enums.Constant.WithdrawKey;
import io.frame.common.exception.RRException;
import io.frame.common.utils.DateUtils;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.Config;
import io.frame.dao.entity.User;
import io.frame.dao.entity.Wallet;
import io.frame.dao.entity.Withdraw;
import io.frame.dao.entity.WithdrawExample;
import io.frame.dao.mapper.WithdrawMapper;
import io.frame.entity.WithdrawVo;
import io.frame.exception.ErrorCode;
import io.frame.service.ConfigService;
import io.frame.service.WalletChangeService;
import io.frame.service.WalletService;
import io.frame.service.WithdrawService;
import io.frame.utils.SessionUtils;

/**
 * 提现接口实现
 * 
 * @author fury
 *
 */
@Transactional
@Service("withdrawService")
public class WithdrawServiceImpl implements WithdrawService {

	Logger logger = LoggerFactory.getLogger(WithdrawServiceImpl.class);

	/**
	 * 提现配置map
	 */
	private static Map<String, Object> withdrawConfigMap = Maps.newHashMap();

	@Autowired
	WithdrawMapper withdrawMapper;

	@Autowired
	ConfigService configService;

	@Autowired
	WalletService walletService;

	@Autowired
	WalletChangeService walletChangeService;

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> getWithdrawInfo(Long userId) {
		User user = SessionUtils.getCurrentUser();
		Map<String, Object> map = Maps.newHashMap();
		map.put("userMobile", user.getUserMobile());
		map.put("userName", user.getUserName());
		map.put("alipayName", user.getAlipayName());
		map.put("alipayMobile", user.getAlipayMobile());
		// 钱包余额
		Wallet wallet = walletService.getWallet(userId);
		map.put("balance", wallet.getBalance());
		// 提现手续费
		String value = configService.getConfigByKey(WithdrawKey.WITHDRAW_FEE_KEY.getValue());
		map.put("withdrawFee", "-1".equals(value) ? BigDecimal.ZERO : value);
		return map;
	}

	@SysLog("提现申请")
	@Override
	public void withdrawSubmit(Long userId, BigDecimal money) {
		// 校验提现配置项
		String msg = this.validWithdraw(userId, money);
		if (!StringUtils.isEmpty(msg)) {
			throw new RRException(msg);
		}

		// 校验余额
		Wallet wallet = walletService.getWallet(userId);
		if (wallet.getBalance().compareTo(money) == -1) {
			throw new RRException(ErrorCode.GOLDCOIN_IS_NOT_ENOUGH);
		}
		User currentUser = SessionUtils.getCurrentUser();
		Withdraw withdraw = new Withdraw();
		withdraw.setUserId(userId);
		withdraw.setUserName(currentUser.getUserName());
		withdraw.setUserMobile(currentUser.getUserMobile());
		withdraw.setParentId(currentUser.getParentId());
		withdraw.setGroupUserIds(currentUser.getGroupUserIds());
		withdraw.setAlipayMobile(currentUser.getAlipayMobile());
		withdraw.setAlipayName(currentUser.getAlipayName());
		withdraw.setWithdrawMoney(money);
		// 提现手续费
		String value = (String) withdrawConfigMap.get(WithdrawKey.WITHDRAW_FEE_KEY.getValue());
		BigDecimal fee = "-1".equals(value) ? BigDecimal.ZERO : new BigDecimal(value);
		BigDecimal feeMoney = money.multiply(fee);
		withdraw.setWithdrawFee(feeMoney);
		withdraw.setWithdrawRealMoney(money.subtract(fee)); // 实际到账金额
		withdraw.setCreateTime(new Date());
		withdraw.setCreateUser(currentUser.getUserName());
		withdraw.setStatus(Constant.Status.ZERO.getValue());
		try {
			// 保存
			withdrawMapper.insertSelective(withdraw);
			// 先扣钱
			walletService.deduction(userId, money);
			// 记录账变手续费
			walletChangeService.createWalletChange(userId, feeMoney.negate(), withdraw.getWithdrawId(),
					ChangeType.WITHDRAW_OUT_FEE_KEY);
			// 记录账变
			walletChangeService.createWalletChange(userId, withdraw.getWithdrawRealMoney().negate(),
					withdraw.getWithdrawId(), ChangeType.WITHDRAW_OUT_KEY);
		} catch (Exception e) {
			logger.error(ErrorCode.SUBMIT_FAILED, e);
			throw new RRException(ErrorCode.SUBMIT_FAILED);
		}

	}

	/**
	 * 校验提现配置项
	 * 
	 * @return
	 */
	public String validWithdraw(Long userId, BigDecimal money) {
		String msg = null;

		List<Config> configList = configService.getConfigListByType(ConfigType.WITHDRAW.getValue());
		if (!CollectionUtils.isEmpty(configList)) {
			this.initMap(configList);
			for (Config config : configList) {
				String value = config.getConfigVal();
				if (config.getConfigKey().equals(Constant.WithdrawKey.WITHDRAW_TIME_RANGE_KEY.getValue())) {
					// 提现时间段
					if (!"-1".equals(value)) {
						String[] values = value.split(",");
						// 校验当前时间是不是在时间范围内
						if (!validTime(values[0], values[1])) {
							msg = WithdrawKey.getName(config.getConfigKey());
							break;
						}
					}
				}

				if (config.getConfigKey().equals(Constant.WithdrawKey.WITHDRAW_MIN_KEY.getValue())) {
					// 小于最小金额
					if (!"-1".equals(value)) {
						if (money.compareTo(new BigDecimal(value)) == -1) {
							msg = WithdrawKey.getName(config.getConfigKey());
							break;
						}
					}
				}

				if (config.getConfigKey().equals(Constant.WithdrawKey.WITHDRAW_MAX_KEY.getValue())) {
					// 超过最大金额
					if (!"-1".equals(value)) {
						if (money.compareTo(new BigDecimal(value)) == 1) {
							msg = WithdrawKey.getName(config.getConfigKey());
							break;
						}
					}
				}

				if (config.getConfigKey().equals(Constant.WithdrawKey.WITHDRAW_SWITCH_KEY.getValue())) {
					// 开关
					if ("0".equals(value)) {
						msg = WithdrawKey.getName(config.getConfigKey());
						break;
					}
				}

				if (config.getConfigKey().equals(Constant.WithdrawKey.WITHDRAW_COUNT_KEY.getValue())) {
					// 提现次数
					if (!"-1".equals(value)) {
						// 查询今日提现订单数
						int count = this.getWithDrawCount(userId);
						if (count >= Integer.parseInt(value)) {
							msg = WithdrawKey.getName(config.getConfigKey());
							break;
						}
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
		if (withdrawConfigMap.size() == 0) {
			for (Config config : configList) {
				withdrawConfigMap.put(config.getConfigKey(), config.getConfigVal());
			}
		}
	}

	/**
	 * 查询今日提现订单数,待审核，已支付
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = true)
	public int getWithDrawCount(Long userId) {
		WithdrawExample example = new WithdrawExample();
		WithdrawExample.Criteria cr = example.createCriteria();
		cr.andUserIdEqualTo(userId);
		Integer[] status = { Constant.Status.ZERO.getValue(), Constant.Status.ONE.getValue() };
		cr.andStatusIn(Arrays.asList(status));
		Date date = new Date();
		cr.andCreateTimeGreaterThanOrEqualTo(DateUtils.getStartTime(date));
		cr.andCreateTimeLessThan(DateUtils.getEndTime(date));
		return withdrawMapper.countByExample(example);
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

	@Transactional(readOnly = true)
	public List<WithdrawVo> getWithdrawList(Long userId) {
		List<String> showField = Lists.newArrayList();
		showField.add(Withdraw.FD_WITHDRAWMONEY);
		showField.add(Withdraw.FD_WITHDRAWFEE);
		showField.add(Withdraw.FD_CREATETIME);
		showField.add(Withdraw.FD_STATUS);
		WithdrawExample example = new WithdrawExample();
		WithdrawExample.Criteria cr = example.createCriteria();
		cr.andUserIdEqualTo(userId);
		example.setOrderByClause(SqlTools.orderByDescField(Withdraw.FD_CREATETIME));
		List<Withdraw> list = withdrawMapper.selectByExampleShowField(showField, example);
		List<WithdrawVo> resultList = Lists.newArrayList();
		if (!CollectionUtils.isEmpty(list)) {
			for (Withdraw withdraw : list) {
				WithdrawVo vo = new WithdrawVo();
				BeanUtils.copyProperties(withdraw, vo);
				vo.setStatus(Constant.WithdrawStatus.getName(withdraw.getStatus()));
				resultList.add(vo);
			}
		}
		return resultList;
	}

}
