package io.frame.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.frame.common.enums.Constant;
import io.frame.common.enums.Constant.ChangeType;
import io.frame.dao.entity.WalletChange;
import io.frame.dao.entity.Welfare;
import io.frame.dao.mapper.WalletChangeMapper;
import io.frame.entity.WelfareVo;
import io.frame.service.OrderService;
import io.frame.service.ProfitService;
import io.frame.service.RecommendService;
import io.frame.service.WalletChangeService;
import io.frame.service.WelfareService;

/**
 * 收益信息
 * 
 * @author fury
 *
 */
@Transactional
@Service("profitService")
public class ProfitServiceImpl implements ProfitService {

	Logger logger = LoggerFactory.getLogger(ProfitServiceImpl.class);

	@Autowired
	WalletChangeMapper walletChangeMapper;

	@Autowired
	WelfareService welfareService;

	@Autowired
	OrderService orderService;

	@Autowired
	RecommendService recommendService;

	@Autowired
	WalletChangeService walletChangeService;

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> getMyCarProfit(Long userId) {
		Map<String, Object> map = Maps.newHashMap();
		// 分红奖励
		List<WalletChange> bonusList = walletChangeService.getWalletChangeList(userId, ChangeType.CAR_PROFIT_KEY);
		map.put("bonusList", bonusList);
		// 累计收益
		map.put("totalsProfitMoney", this.getCumulativeProfit(bonusList));
		return map;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> getAllWelfare(Long userId) {
		Map<String, Object> map = Maps.newHashMap();
		// 分红奖励
		List<WalletChange> bonusList = walletChangeService.getWalletChangeList(userId,
				Constant.ChangeType.ALL_PEOPLE_WELFARE_KEY);
		map.put("bonusList", bonusList);
		// 累计收益
		map.put("totalsProfitMoney", this.getCumulativeProfit(bonusList));
		return map;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> getGlobalBonus(Long userId) {
		Map<String, Object> map = Maps.newHashMap();
		// 分红奖励
		List<WalletChange> bonusList = walletChangeService.getWalletChangeList(userId,
				Constant.ChangeType.GLOBAL_BONUS_KEY);
		map.put("bonusList", bonusList);
		// 累计收益
		map.put("totalsProfitMoney", this.getCumulativeProfit(bonusList));
		// 查询全球分红奖规则
		map.put("welfareVoList",
				this.getWelfareVo(welfareService.getWelfareByKey(Constant.ChangeType.GLOBAL_BONUS_KEY)));
		return map;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> getLeaderBonus(Long userId) {
		Map<String, Object> map = Maps.newHashMap();
		// 团队领导奖励
		List<WalletChange> bonusList = walletChangeService.getWalletChangeList(userId,
				Constant.ChangeType.TEAM_LEADERSHIP_AWARD_KEY);
		map.put("bonusList", bonusList);
		// 累计收益
		map.put("totalsProfitMoney", this.getCumulativeProfit(bonusList));
		// 查询团队领导奖规则
		map.put("welfareVoList",
				this.getWelfareVo(welfareService.getWelfareByKey(Constant.ChangeType.TEAM_LEADERSHIP_AWARD_KEY)));
		return map;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> getEspeciallyBonus(Long userId) {
		Map<String, Object> map = Maps.newHashMap();
		// 特别奖励
		List<WalletChange> bonusList = walletChangeService.getWalletChangeList(userId,
				Constant.ChangeType.SPECIAL_CONTRIBUTION_AWARD_KEY);
		map.put("bonusList", bonusList);
		// 累计收益
		map.put("totalsProfitMoney", this.getCumulativeProfit(bonusList));
		// 查询特别贡献奖规则
		map.put("welfareVoList",
				this.getWelfareVo(welfareService.getWelfareByKey(Constant.ChangeType.SPECIAL_CONTRIBUTION_AWARD_KEY)));
		return map;
	}

	private List<WelfareVo> getWelfareVo(List<Welfare> wefarelist) {
		List<WelfareVo> welfareVoList = Lists.newArrayList();
		if (!CollectionUtils.isEmpty(wefarelist)) {
			// 判断奖金池是否为空,为空取所有收益中订单的价格
			// 查询所有收益中的订单的总金额
			BigDecimal bounsPool = orderService.getProfitOrderMoney();
			for (Welfare welfare : wefarelist) {
				WelfareVo vo = new WelfareVo();
				vo.setWelfareName(welfare.getWelfareName());
				vo.setRemark(welfare.getRemark());
				if (welfare.getBonusPool() != null) {
					bounsPool = welfare.getBonusPool();
				}
				vo.setBounsPool(bounsPool);
				// 计算达标人数
				int qualifiedCount = this.getQualifiedCount(welfare.getWelfareKey(), welfare.getWelfareValue());
				vo.setQualifiedCount(qualifiedCount);
				if (qualifiedCount != 0) {
					// 计算平均分红 bounsPool/qualifiedCount
					vo.setAverageAllot(bounsPool.divide(new BigDecimal(qualifiedCount), 2, RoundingMode.HALF_UP));
				} else {
					vo.setAverageAllot(BigDecimal.ZERO);
				}
				welfareVoList.add(vo);
			}
		}
		return welfareVoList;
	}

	/**
	 * 计算累计奖励
	 * 
	 * @param bonusList
	 * @return
	 */
	private BigDecimal getCumulativeProfit(List<WalletChange> bonusList) {
		BigDecimal totalsProfitMoney = BigDecimal.ZERO;
		for (WalletChange walletChange : bonusList) {
			totalsProfitMoney = totalsProfitMoney.add(walletChange.getOperatorMoney());
		}
		return totalsProfitMoney;

	}

	/**
	 * 计算福利的达标人数
	 * 
	 * @return
	 */
	private int getQualifiedCount(String key, String value) {
		int qualifiedCount = 0;
		if (key.equals(Constant.ChangeType.GLOBAL_BONUS_KEY.getValue())) {
			// 全球分红
			qualifiedCount = recommendService.getSatisfyGlobalBonusNum(Integer.valueOf(value));
		} else if (key.equals(Constant.ChangeType.TEAM_LEADERSHIP_AWARD_KEY.getValue())) {
			// 团队领导奖励
			String[] values = value.split(",");
			qualifiedCount = recommendService.getSatisfyTeamLeadershipAwardNum(Integer.valueOf(values[0]),
					new BigDecimal(values[1]));
		} else if (key.equals(Constant.ChangeType.SPECIAL_CONTRIBUTION_AWARD_KEY.getValue())) {
			// 特别贡献奖
			String[] values = value.split(",");
			qualifiedCount = recommendService.getSatisfySpecialContributionAwardNum(Integer.valueOf(values[0]),
					Integer.valueOf(values[1]), new BigDecimal(values[2]));
		}
		return qualifiedCount;
	}

}