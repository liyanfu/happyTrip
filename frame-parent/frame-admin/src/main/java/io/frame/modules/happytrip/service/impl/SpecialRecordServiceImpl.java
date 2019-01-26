
package io.frame.modules.happytrip.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import io.frame.common.enums.Constant.ChangeType;
import io.frame.common.enums.Constant.Sort;
import io.frame.common.enums.Constant.Status;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.DateUtils;
import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.SpecialRecord;
import io.frame.dao.entity.SpecialRecordExample;
import io.frame.dao.entity.SysUser;
import io.frame.dao.entity.WalletChange;
import io.frame.dao.mapper.SpecialRecordMapper;
import io.frame.modules.happytrip.service.RecommendService;
import io.frame.modules.happytrip.service.ReportService;
import io.frame.modules.happytrip.service.SpecialRecordService;
import io.frame.modules.happytrip.service.WalletService;
import io.frame.modules.sys.service.SysConfigService;
import io.frame.modules.sys.shiro.ShiroUtils;

/**
 * 特别贡献奖派发记录
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class SpecialRecordServiceImpl implements SpecialRecordService {
	Logger logger = LoggerFactory.getLogger(SpecialRecordServiceImpl.class);

	@Autowired
	SpecialRecordMapper specialRecordMapper;

	@Autowired
	SysConfigService sysConfigService;

	@Autowired
	ReportService reportService;

	@Autowired
	RecommendService recommendService;

	@Autowired
	WalletService walletService;

	@Transactional(readOnly = true)
	@Override
	public PageUtils<SpecialRecord> queryPage(SpecialRecord record) {

		SpecialRecordExample example = new SpecialRecordExample();
		SpecialRecordExample.Criteria cr = example.createCriteria();

		Date beginDate = record.getBeginTime();
		Date endDate = record.getEndTime();
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

		if (!StringUtils.isEmpty(record.getUserName())) {
			cr.andUserNameLikeIgnoreNull(record.getUserName() + "%");
		}

		if (!StringUtils.isEmpty(record.getUserMobile())) {
			cr.andUserMobileLikeIgnoreNull(record.getUserMobile() + "%");
		}

		if (!StringUtils.isEmpty(record.getGroupUserIds())) {
			cr.andUserMobileLikeIgnoreNull(record.getGroupUserIds() + "%");
		}

		cr.andIsGrantEqualToIgnoreNull(record.getIsGrant());

		cr.andCreateTimeGreaterThanOrEqualToIgnoreNull(beginDate);
		cr.andCreateTimeLessThanIgnoreNull(endDate);

		PageHelper.startPage(record.getPageNumber(), record.getPageSize());
		example.setOrderByClause(SpecialRecord.FD_CREATETIME + Sort.DESC.getValue());

		try {
			Page<SpecialRecord> page = (Page<SpecialRecord>) specialRecordMapper.selectByExample(example);
			return new PageUtils<SpecialRecord>(page);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Transactional(readOnly = true)
	@Override
	public SpecialRecord getInfoById(Long id) {
		try {
			SpecialRecord order = specialRecordMapper.selectByPrimaryKey(id);
			return order;
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Override
	public void grant(Long[] ids) {
		SysUser sysUser = ShiroUtils.getUserEntity();

		Date date = new Date();
		for (Long id : ids) {

			// 先获取该记录
			SpecialRecord record = specialRecordMapper.selectByPrimaryKey(id);
			if (record.getIsGrant() == Status.ONE.getValue()) {
				continue;// 已经发放过,不需要再次发放
			}

			SpecialRecord updateRecord = new SpecialRecord();
			updateRecord.setId(id);
			updateRecord.setUpdateUser(sysUser.getUserName());
			updateRecord.setUpdateTime(date);
			updateRecord.setGrantTime(date);
			updateRecord.setIsGrant(Status.ONE.getValue());
			specialRecordMapper.updateByPrimaryKeySelective(updateRecord);

			WalletChange walletChange = new WalletChange();
			walletChange.setUserId(record.getUserId());
			walletChange.setOperatorMoney(record.getMoney());
			walletChange.setRelationId(record.getId());
			walletChange.setRemark(ChangeType.SPECIAL_CONTRIBUTION_AWARD_KEY.getName());
			// 加钱,帐变,刷新报表
			walletService.addWallet(walletChange, ChangeType.SPECIAL_CONTRIBUTION_AWARD_KEY);

		}

	}

	@Override
	public void delete(Long id) {
		try {
			specialRecordMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

}
