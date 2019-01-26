package io.frame.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import io.frame.common.enums.Constant.Status;
import io.frame.common.utils.DateUtils;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.Recommend;
import io.frame.dao.entity.RecommendExample;
import io.frame.dao.entity.Report;
import io.frame.dao.entity.ReportExample;
import io.frame.dao.entity.User;
import io.frame.dao.entity.UserExample;
import io.frame.dao.entity.WalletChange;
import io.frame.dao.entity.Welfare;
import io.frame.dao.entity.WelfareExample;
import io.frame.dao.mapper.RecommendMapper;
import io.frame.dao.mapper.ReportMapper;
import io.frame.dao.mapper.UserMapper;
import io.frame.dao.mapper.WalletChangeMapper;
import io.frame.dao.mapper.WelfareMapper;
import io.frame.entity.WelfareVo;
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
	ReportMapper reportMapper;

	@Autowired
	RecommendService recommendService;

	@Autowired
	WalletChangeService walletChangeService;
	@Autowired
	UserMapper userMapper;
	@Autowired
	WelfareMapper welfareMapper;
	@Autowired
	RecommendMapper recommendMapper;

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
			// 判断奖金池是否为空,为空取当日订单总额
			// 查询当日所有订单总额
			BigDecimal bounsPool = this.getOrderMoney();
			for (Welfare welfare : wefarelist) {
				// 每次循环重新赋值
				BigDecimal newBounsPool = bounsPool;
				WelfareVo vo = new WelfareVo();
				vo.setWelfareName(welfare.getWelfareName());
				vo.setRemark(welfare.getRemark());
				if (welfare.getBonusPool() != null && welfare.getBonusPool().compareTo(BigDecimal.ZERO) == 1) {
					newBounsPool = welfare.getBonusPool();
				}
				vo.setBounsPool(newBounsPool);
				// 计算达标人数
				int qualifiedCount = this.getQualifiedCount(welfare.getWelfareKey(), welfare.getWelfareValue());
				vo.setQualifiedCount(qualifiedCount);
				if (qualifiedCount != 0 && !newBounsPool.equals("0")) {
					// 计算平均分红 总业绩*百分比/总人数 bounsPool*percent/qualifiedCount
					vo.setAverageAllot(newBounsPool.multiply(welfare.getPercent())
							.divide(new BigDecimal(qualifiedCount), 4, RoundingMode.HALF_UP));
				} else {
					vo.setAverageAllot(BigDecimal.ZERO);
				}
				welfareVoList.add(vo);
			}
		}
		return welfareVoList;
	}

	/**
	 * 获取当日报表中订单的总业绩
	 * 
	 * @return
	 */
	public BigDecimal getOrderMoney() {
		ReportExample example = new ReportExample();
		ReportExample.Criteria cr = example.createCriteria();
		Date date = new Date();
		cr.andCreateTimeEqualTo(date);
		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.sumField(Report.FD_ORDERMONEY));
		Report peport = reportMapper.selectOneByExampleShowField(showField, example);
		return peport == null ? BigDecimal.ZERO : peport.getOrderMoney();
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
			qualifiedCount = this.getSatisfyGlobalBonusNum(key, value);
		} else if (key.equals(Constant.ChangeType.TEAM_LEADERSHIP_AWARD_KEY.getValue())) {
			// 团队领导奖励
			qualifiedCount = this.getSatisfyTeamLeadershipAwardNum(key, value);
		} else if (key.equals(Constant.ChangeType.SPECIAL_CONTRIBUTION_AWARD_KEY.getValue())) {
			// 特别贡献奖
			qualifiedCount = this.getSatisfySpecialContributionAwardNum(key, value);
		}
		return qualifiedCount;
	}

	/**
	 * 获取特别贡献奖达标人数
	 * 
	 * @param welfareKey
	 * @param value
	 * @return
	 */
	@Transactional(readOnly = true)
	private int getSatisfySpecialContributionAwardNum(String welfareKey, String value) {

		Date date = new Date();
		// 保存父级ID,与直推有效会员人数
		Map<Long, Integer> map = this.getUserList(date);
		if (CollectionUtils.isEmpty(map)) {
			return 0;
		}
		// 获取全球分红奖励规则
		List<Welfare> ruleList = this.getRuleList(welfareKey);

		Map<String, Integer> rulesMap = Maps.newHashMap();
		// 统计每个规则各占多少人
		for (Entry<Long, Integer> m : map.entrySet()) {
			Long userId = m.getKey();
			Integer recommendNum = m.getValue();
			// 获取团队人数及团队业绩
			User user = userMapper.selectByPrimaryKey(userId);
			Recommend vo = this.recommendInfo(user.getGroupUserIds());
			if (vo == null || vo.getRecommendNumber() == null || vo.getTeamAchievement() == null
					|| vo.getTeamAchievement().compareTo(BigDecimal.ZERO) == 0) {
				continue;// 没有团队人数和业绩,下一个
			}

			Welfare welfareObj = null; // 最终匹配到的条件
			for (Welfare welfare : ruleList) {
				String[] rules = welfare.getWelfareValue().split(",");
				Integer nums = Integer.parseInt(rules[0]);
				Integer teamNum = Integer.parseInt(rules[1]);
				BigDecimal teamMoeny = new BigDecimal(rules[2]);
				if (recommendNum >= nums && vo.getRecommendNumber() >= teamNum
						&& vo.getTeamAchievement().compareTo(teamMoeny) >= 0) {
					welfareObj = welfare;// 取得最后一个匹配的结果
				}
			}

			if (welfareObj == null) {
				continue;// 没匹配到 下一个.
			}
			// 保存当前各项规则达标的总人数
			String welfareValue = welfareObj.getWelfareValue();
			if (!rulesMap.containsKey(welfareValue)) {
				rulesMap.put(welfareValue, 1);
			} else {
				Integer countNum = rulesMap.get(welfareValue);
				rulesMap.put(welfareValue, countNum + 1);
			}

		}

		return rulesMap.get(value) == null ? 0 : rulesMap.get(value);
	}

	/**
	 * 获取团队领导奖规则达标人数
	 * 
	 * @param welfareKey
	 * @param value
	 * @return
	 */
	@Transactional(readOnly = true)
	private int getSatisfyTeamLeadershipAwardNum(String welfareKey, String value) {

		Date date = new Date();
		// 保存父级ID,与直推有效会员人数
		Map<Long, Integer> map = this.getUserList(date);
		if (CollectionUtils.isEmpty(map)) {
			return 0;
		}
		// 获取全球分红奖励规则
		List<Welfare> ruleList = this.getRuleList(welfareKey);

		Map<String, Integer> rulesMap = Maps.newHashMap();
		// 统计每个规则各占多少人
		for (Entry<Long, Integer> m : map.entrySet()) {
			Long userId = m.getKey();
			Integer recommendNum = m.getValue();
			// 获取当前用户的昨日团队业绩
			User user = userMapper.selectByPrimaryKey(userId);
			BigDecimal teamAchievement = this.getTeamAchievement(user.getGroupUserIds(), date);
			Welfare welfareObj = null;
			for (Welfare welfare : ruleList) {
				String[] rules = welfare.getWelfareValue().split(",");
				Integer nums = Integer.parseInt(rules[0]);
				BigDecimal moeny = new BigDecimal(rules[1]);
				if (recommendNum >= nums && teamAchievement.compareTo(moeny) >= 0) {
					welfareObj = welfare;// 取得最后一个匹配的结果
				}
			}

			if (welfareObj == null) {
				continue;// 没匹配到 下一个.
			}
			// 保存当前各项规则达标的总人数
			String welfareValue = welfareObj.getWelfareValue();
			if (!rulesMap.containsKey(welfareValue)) {
				rulesMap.put(welfareValue, 1);
			} else {
				Integer countNum = rulesMap.get(welfareValue);
				rulesMap.put(welfareValue, countNum + 1);
			}

		}

		return rulesMap.get(value) == null ? 0 : rulesMap.get(value);
	}

	/**
	 * 获取全球分红规则达标人数
	 * 
	 * @param welfareKey
	 * @param value
	 * @return
	 */
	@Transactional(readOnly = true)
	private int getSatisfyGlobalBonusNum(String welfareKey, String value) {

		Date date = new Date();
		// 保存父级ID,与直推有效会员人数
		Map<Long, Integer> map = this.getUserList(date);
		if (CollectionUtils.isEmpty(map)) {
			return 0;
		}

		// 获取全球分红奖励规则
		List<Welfare> ruleList = this.getRuleList(welfareKey);

		Map<String, Integer> rulesMap = Maps.newHashMap();
		// 统计每个规则各占多少人
		for (Entry<Long, Integer> m : map.entrySet()) {
			Integer recommendNum = m.getValue();
			Welfare welfareObj = null;
			for (Welfare welfare : ruleList) {
				Integer nums = Integer.parseInt(welfare.getWelfareValue());// 3个
				if (recommendNum >= nums) {
					welfareObj = welfare;// 取得最后一个匹配的结果
				}
			}

			if (welfareObj == null) {
				continue;// 没匹配到 下一个.
			}
			// 保存当前各项规则达标的总人数
			String welfareValue = welfareObj.getWelfareValue();
			if (!rulesMap.containsKey(welfareValue)) {
				rulesMap.put(welfareValue, 1);
			} else {
				Integer countNum = rulesMap.get(welfareValue);
				rulesMap.put(welfareValue, countNum + 1);
			}

		}

		return rulesMap.get(value) == null ? 0 : rulesMap.get(value);
	}

	/**
	 * 获取用户团队业绩
	 * 
	 * @param groupUserIds
	 * @param currentDate
	 * @return
	 */
	private BigDecimal getTeamAchievement(String groupUserIds, Date currentDate) {
		RecommendExample example = new RecommendExample();
		RecommendExample.Criteria cr = example.createCriteria();
		cr.andGroupUserIdsLike(groupUserIds + "%");
		if (currentDate != null) {
			cr.andCreateTimeEqualTo(currentDate);
		}
		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.sumField(Recommend.FD_TEAMACHIEVEMENT));
		Recommend recommend = recommendMapper.selectOneByExampleShowField(showField, example);
		return recommend == null ? BigDecimal.ZERO : recommend.getTeamAchievement();
	}

	/**
	 * 获取用户
	 * 
	 * @param currentDate
	 * @return
	 */
	@Transactional(readOnly = true)
	private Map<Long, Integer> getUserList(Date currentDate) {
		UserExample example = new UserExample();
		UserExample.Criteria cr = example.createCriteria();
		if (currentDate != null) { // 等于空时 是特别贡献奖获取所有用户数据,否则只获取今日新注册用户
			cr.andCreateTimeGreaterThanOrEqualTo(DateUtils.parse(currentDate, "yyyy-MM-dd")); // >=今日0点
			cr.andCreateTimeLessThan(DateUtils.addDateDays(DateUtils.parse(currentDate, "yyyy-MM-dd"), 1)); // 小于明日0点
		}
		List<String> showField = Lists.newArrayList();
		showField.add(User.FD_USERID);
		showField.add(User.FD_USERNAME);
		showField.add(User.FD_PARENTID);
		showField.add(User.FD_GROUPUSERIDS);
		showField.add(User.FD_USERLEVEL);
		showField.add(User.FD_USERMOBILE);
		List<User> userList = userMapper.selectByExampleShowField(showField, example);

		Map<Long, Integer> map = Maps.newHashMap();
		if (!CollectionUtils.isEmpty(userList)) {
			for (User user : userList) {
				// 查看当前用户今日是否有消费
				if (!this.getCustomReport(user.getUserId(), currentDate)) {
					continue;
				}
				if (!map.containsKey(user.getParentId())) {
					map.put(user.getParentId(), 1);
				} else {
					Integer num = map.get(user.getParentId());
					map.put(user.getParentId(), num + 1);
				}
			}
		}

		return map;
	}

	/**
	 * 获取奖励派发规则
	 * 
	 * @return
	 */
	private List<Welfare> getRuleList(String welfareKey) {
		WelfareExample example = new WelfareExample();
		example.or().andStatusEqualTo(Status.ONE.getValue()).andWelfareKeyEqualTo(welfareKey);
		example.setOrderByClause(SqlTools.orderByAscField(Welfare.FD_CREATETIME));
		List<String> showField = Lists.newArrayList();
		showField.add(Welfare.FD_WELFAREKEY);
		showField.add(Welfare.FD_WELFARENAME);
		showField.add(Welfare.FD_WELFAREVALUE);
		showField.add(Welfare.FD_BONUSPOOL);
		showField.add(Welfare.FD_PERCENT);
		return welfareMapper.selectByExampleShowField(showField, example);
	}

	/**
	 * 判断昨日注册用户昨日是否有消费
	 * 
	 * @param userId
	 * @param currentDate
	 * @return
	 */
	@Transactional(readOnly = true)
	private boolean getCustomReport(Long userId, Date currentDate) {
		ReportExample example = new ReportExample();
		example.or().andUserIdEqualTo(userId).andCreateTimeEqualTo(currentDate);
		List<String> showField = Lists.newArrayList();
		showField.add(Report.FD_ORDERMONEY);
		Report report = reportMapper.selectOneByExampleShowField(showField, example);
		if (report == null || report.getOrderMoney() == null
				|| report.getOrderMoney().compareTo(BigDecimal.ZERO) == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 获取团队人数和团队业绩
	 * 
	 * @param groupUserIds
	 * @return
	 */
	private Recommend recommendInfo(String groupUserIds) {
		RecommendExample example = new RecommendExample();
		example.or().andGroupUserIdsLike(groupUserIds + "%");
		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.sumField(Recommend.FD_RECOMMENDNUMBER));
		showField.add(SqlTools.sumField(Recommend.FD_TEAMACHIEVEMENT));
		return recommendMapper.selectOneByExampleShowField(showField, example);
	}

}
