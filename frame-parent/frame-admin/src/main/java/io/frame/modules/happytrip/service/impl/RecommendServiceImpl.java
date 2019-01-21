
package io.frame.modules.happytrip.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.frame.common.annotation.SysLog;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.DateUtils;
import io.frame.dao.entity.Recommend;
import io.frame.dao.entity.RecommendExample;
import io.frame.dao.entity.SysUser;
import io.frame.dao.entity.User;
import io.frame.dao.mapper.RecommendMapper;
import io.frame.modules.happytrip.service.RecommendService;
import io.frame.modules.happytrip.service.UserService;
import io.frame.modules.sys.shiro.ShiroUtils;

/**
 * 推荐表信息
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class RecommendServiceImpl implements RecommendService {
	Logger logger = LoggerFactory.getLogger(RecommendServiceImpl.class);

	@Autowired
	RecommendMapper recommendMapper;

	@Autowired
	UserService userService;

	@Transactional(readOnly = true)
	@Override
	public Recommend getRecommendById(Long userId) {
		try {
			RecommendExample example = new RecommendExample();
			example.or().andUserIdEqualTo(userId);
			return recommendMapper.selectOneByExample(example);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@SysLog("更新推荐")
	@Override
	public void upsert(Long userId, BigDecimal money) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		RecommendExample example = new RecommendExample();
		RecommendExample.Criteria cr = example.createCriteria();
		cr.andUserIdEqualTo(userId);
		Date date = new Date();
		cr.andCreateTimeGreaterThanOrEqualTo(DateUtils.getStartTime(date));
		cr.andCreateTimeLessThan(DateUtils.getEndTime(date));
		Recommend recommend = recommendMapper.selectOneByExample(example);
		if (recommend == null) {
			// 新增
			User user = userService.getInfoById(userId);
			recommend = new Recommend();
			recommend.setUserId(userId);
			recommend.setParentId(user.getParentId());
			recommend.setGroupUserIds(user.getGroupUserIds());
			recommend.setCreateTime(date);
			recommend.setCreateUser(sysUser.getUserName());
			recommend.setRecommendNumber(1);
			recommend.setTeamAchievement(money);
			recommendMapper.insertSelective(recommend);
		} else {
			// 修改
			// 重置信息
			recommend.setRecommendNumber(1);
			recommend.setTeamAchievement(money);
			recommendMapper.updateByPrimaryKeySelectiveSync(recommend);

			Recommend updateRecommend = new Recommend();
			updateRecommend.setRecommendId(recommend.getRecommendId());
			updateRecommend.setUpdateTime(date);
			updateRecommend.setUpdateUser(sysUser.getUserName());
			// 更新操作者和时间
			recommendMapper.updateByPrimaryKey(updateRecommend);

		}

	}
}
