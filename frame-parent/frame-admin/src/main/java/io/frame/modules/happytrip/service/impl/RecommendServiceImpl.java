
package io.frame.modules.happytrip.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import io.frame.common.annotation.SysLog;
import io.frame.common.enums.Constant.Sort;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.DateUtils;
import io.frame.common.utils.PageUtils;
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
	public void upsert(Long userId, Integer num, BigDecimal money) {
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
			recommend.setUserName(user.getUserName());
			recommend.setParentId(user.getParentId());
			recommend.setGroupUserIds(user.getGroupUserIds());
			recommend.setCreateTime(date);
			recommend.setCreateUser(sysUser.getUserName());
			recommend.setRecommendNumber(num);
			recommend.setTeamAchievement(money);
			recommendMapper.insertSelective(recommend);
		} else {
			// 修改
			// 重置信息
			recommend.setRecommendNumber(num);
			recommend.setTeamAchievement(money);
			recommendMapper.updateByPrimaryKeySelectiveSync(recommend);

			Recommend updateRecommend = new Recommend();
			updateRecommend.setRecommendId(recommend.getRecommendId());
			updateRecommend.setUpdateTime(date);
			updateRecommend.setUpdateUser(sysUser.getUserName());
			// 更新操作者和时间
			recommendMapper.updateByPrimaryKeySelective(updateRecommend);

		}

	}

	@Override
	public PageUtils<Recommend> queryPage(Recommend recommend) {
		RecommendExample example = new RecommendExample();
		RecommendExample.Criteria cr = example.createCriteria();

		Date beginDate = recommend.getBeginTime();
		Date endDate = recommend.getEndTime();
		// 当天0点
		if (beginDate == null) {
			beginDate = DateUtils.parse(new Date(), "yyyy-MM-dd");
		}

		// 明天0点
		if (endDate == null) {
			endDate = DateUtils.addDateDays(DateUtils.parse(new Date(), "yyyy-MM-dd"), 1);
		}

		if (beginDate == endDate) {
			endDate = DateUtils.addDateDays(DateUtils.parse(new Date(), "yyyy-MM-dd"), 1);
		}

		if (!StringUtils.isEmpty(recommend.getUserName())) {
			cr.andUserNameLikeIgnoreNull(recommend.getUserName() + "%");
		}
		cr.andCreateTimeGreaterThanOrEqualToIgnoreNull(beginDate);
		cr.andCreateTimeLessThanIgnoreNull(endDate);

		PageHelper.startPage(recommend.getPageNumber(), recommend.getPageSize());
		example.setOrderByClause(Recommend.FD_CREATETIME + Sort.DESC.getValue());

		try {
			Page<Recommend> page = (Page<Recommend>) recommendMapper.selectByExample(example);
			return new PageUtils<Recommend>(page);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}
	}

	@Override
	public Recommend getInfoById(Long recommendId) {

		return recommendMapper.selectByPrimaryKey(recommendId);
	}

	@Override
	public void save(Recommend recommend) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		recommend.setCreateUser(sysUser.getUserName());
		recommend.setCreateTime(new Date());
		try {
			recommendMapper.insertSelective(recommend);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}

	}

	@Override
	public void update(Recommend recommend) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		recommend.setUpdateUser(sysUser.getUserName());
		recommend.setUpdateTime(new Date());
		try {
			recommendMapper.updateByPrimaryKeySelective(recommend);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

	@Override
	public void delete(Long recommendId) {
		recommendMapper.deleteByPrimaryKey(recommendId);

	}
}
