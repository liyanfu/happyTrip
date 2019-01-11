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

import io.frame.common.utils.DateUtils;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.Recommend;
import io.frame.dao.entity.RecommendExample;
import io.frame.dao.mapper.RecommendMapper;
import io.frame.service.RecommendService;

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

}
