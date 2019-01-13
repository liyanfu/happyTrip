package io.frame.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.frame.annotation.SysLog;
import io.frame.common.utils.DateUtils;
import io.frame.common.utils.SqlTools;
import io.frame.dao.custom.mapper.CustomeRecommendMapper;
import io.frame.dao.entity.Recommend;
import io.frame.dao.entity.RecommendExample;
import io.frame.dao.entity.User;
import io.frame.dao.mapper.RecommendMapper;
import io.frame.service.RecommendService;
import io.frame.service.UserService;

/**
 * 每日推荐
 * 
 * @author fury
 *
 */
@Transactional
@Service("recommendService")
public class RecommendServiceImpl implements RecommendService {

	Logger logger = LoggerFactory.getLogger(RecommendServiceImpl.class);

	@Autowired
	RecommendMapper recommendMapper;

	@Autowired
	CustomeRecommendMapper customeRecommendMapper;
	@Autowired
	UserService userService;

	@Transactional(readOnly = true)
	@Override
	public Integer getRecommendNumByParentId(Long userId, boolean isTodayFlag) {

		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.sumField(Recommend.FD_RECOMMENDNUMBER));

		RecommendExample example = new RecommendExample();
		RecommendExample.Criteria cr = example.createCriteria();
		cr.andParentIdEqualTo(userId);
		// 为真查询今日，为假查询所有
		if (isTodayFlag) {
			Date date = new Date();
			cr.andCreateTimeGreaterThanOrEqualTo(DateUtils.getStartTime(date));
			cr.andCreateTimeLessThan(DateUtils.getEndTime(date));
		}

		Recommend recomend = recommendMapper.selectOneByExampleShowField(showField, example);
		return recomend == null ? 0 : recomend.getRecommendNumber();
	}

	@Transactional(readOnly = true)
	@Override
	public BigDecimal getTeamAchievementByGroupIds(String groupUserIds, Boolean isTodayFlag) {
		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.sumField(Recommend.FD_TEAMACHIEVEMENT));

		RecommendExample example = new RecommendExample();
		RecommendExample.Criteria cr = example.createCriteria();
		cr.andGroupUserIdsLike(groupUserIds + "%");
		// 为真查询今日,为假查询所有
		if (isTodayFlag) {
			Date date = new Date();
			cr.andCreateTimeGreaterThanOrEqualTo(DateUtils.getStartTime(date));
			cr.andCreateTimeLessThan(DateUtils.getEndTime(date));
		}

		Recommend recomend = recommendMapper.selectOneByExampleShowField(showField, example);
		return recomend == null ? BigDecimal.ZERO : recomend.getTeamAchievement();
	}

	@Transactional(readOnly = true)
	@Override
	public BigDecimal getTeamAchievementByParentId(Long userId) {
		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.sumField(Recommend.FD_TEAMACHIEVEMENT));
		RecommendExample example = new RecommendExample();
		RecommendExample.Criteria cr = example.createCriteria();
		cr.andParentIdEqualTo(userId);
		Recommend recomend = recommendMapper.selectOneByExampleShowField(showField, example);
		return recomend == null ? BigDecimal.ZERO : recomend.getTeamAchievement();
	}

	@Transactional(readOnly = true)
	@Override
	public int getSatisfyGlobalBonusNum(Integer recommendNum) {
		RecommendExample example = new RecommendExample();
		RecommendExample.Criteria cr = example.createCriteria();
		cr.andRecommendNumberGreaterThan(recommendNum);
		Date date = new Date();
		cr.andCreateTimeGreaterThanOrEqualTo(DateUtils.getStartTime(date));
		cr.andCreateTimeLessThan(DateUtils.getEndTime(date));
		return recommendMapper.countByExample(example);
	}

	@Transactional(readOnly = true)
	@Override
	public int getSatisfyTeamLeadershipAwardNum(Integer recommendNum, BigDecimal achievement) {
		RecommendExample example = new RecommendExample();
		RecommendExample.Criteria cr = example.createCriteria();
		cr.andRecommendNumberGreaterThan(recommendNum);
		cr.andTeamAchievementGreaterThan(achievement);
		Date date = new Date();
		cr.andCreateTimeGreaterThanOrEqualTo(DateUtils.getStartTime(date));
		cr.andCreateTimeLessThan(DateUtils.getEndTime(date));
		return recommendMapper.countByExample(example);
	}

	@Transactional(readOnly = true)
	@Override
	public int getSatisfySpecialContributionAwardNum(Integer recommendNum, Integer teamsNum,
			BigDecimal teamsAchievement) {
		return customeRecommendMapper.customCountByExample(recommendNum, teamsNum, teamsAchievement);
	}

	@SysLog("更新推荐")
	@Override
	public void upsert(Long userId, BigDecimal money) {
		RecommendExample example = new RecommendExample();
		RecommendExample.Criteria cr = example.createCriteria();
		cr.andUserIdEqualTo(userId);
		Date date = new Date();
		cr.andCreateTimeGreaterThanOrEqualTo(DateUtils.getStartTime(date));
		cr.andCreateTimeLessThan(DateUtils.getEndTime(date));
		Recommend recommend = recommendMapper.selectOneByExample(example);
		if (recommend == null) {
			// 新增
			User user = userService.getUserById(userId);
			recommend = new Recommend();
			recommend.setUserId(userId);
			recommend.setParentId(user.getParentId());
			recommend.setGroupUserIds(user.getGroupUserIds());
			recommend.setCreateTime(date);
			recommend.setCreateUser(user.getUserName());
			recommend.setRecommendNumber(1);
			recommend.setTeamAchievement(money);
			recommendMapper.insertSelective(recommend);
		} else {
			// 修改
			// 重置信息
			recommend.setRecommendNumber(1);
			recommend.setTeamAchievement(money);
			recommendMapper.updateByPrimaryKeySelectiveSync(recommend);
		}

	}

}