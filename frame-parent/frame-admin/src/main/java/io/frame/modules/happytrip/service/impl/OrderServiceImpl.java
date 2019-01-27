
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

import io.frame.common.enums.Constant;
import io.frame.common.enums.Constant.OrderStatus;
import io.frame.common.enums.Constant.Sort;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.DateUtils;
import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Order;
import io.frame.dao.entity.OrderExample;
import io.frame.dao.entity.SysUser;
import io.frame.dao.entity.WalletChange;
import io.frame.dao.mapper.OrderMapper;
import io.frame.modules.happytrip.service.OrderService;
import io.frame.modules.happytrip.service.RecommendService;
import io.frame.modules.happytrip.service.ReportService;
import io.frame.modules.happytrip.service.WalletService;
import io.frame.modules.sys.service.SysConfigService;
import io.frame.modules.sys.shiro.ShiroUtils;

/**
 * 订单
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class OrderServiceImpl implements OrderService {
	Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	OrderMapper orderMapper;

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
	public PageUtils<Order> queryPage(Order order) {

		OrderExample example = new OrderExample();
		OrderExample.Criteria cr = example.createCriteria();

		Date beginDate = order.getBeginTime();
		Date endDate = order.getEndTime();
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

		if (order.getOrderId() != null) {
			cr.andOrderIdEqualToIgnoreNull(order.getOrderId());
		}
		if (!StringUtils.isEmpty(order.getUserName())) {
			cr.andUserNameLikeIgnoreNull(order.getUserName() + "%");
		}

		if (!StringUtils.isEmpty(order.getUserMobile())) {
			cr.andUserMobileLikeIgnoreNull(order.getUserMobile() + "%");
		}

		if (!StringUtils.isEmpty(order.getProductName())) {
			cr.andProductNameLikeIgnoreNull(order.getProductName() + "%");
		}
		if (order.getProductTypeId() != null) {
			cr.andProductTypeIdEqualToIgnoreNull(order.getProductTypeId());
		}
		cr.andStatusEqualToIgnoreNull(order.getStatus());
		cr.andSubmitStatusEqualToIgnoreNull(order.getSubmitStatus());

		cr.andCreateTimeGreaterThanOrEqualToIgnoreNull(beginDate);
		cr.andCreateTimeLessThanIgnoreNull(endDate);

		PageHelper.startPage(order.getPageNumber(), order.getPageSize());
		example.setOrderByClause(Order.FD_CREATETIME + Sort.DESC.getValue());

		try {
			Page<Order> page = (Page<Order>) orderMapper.selectByExample(example);
			if (page.size() != 0) {
				// 获取推广域名 链接图片显示
				String value = sysConfigService.getValue(Constant.SystemKey.SYSTEM_SPREAD_DOMAIN_KEY.getValue());
				for (Order bean : page.getResult()) {
					bean.setProductImgurl(value + Constant.readImg + bean.getProductImgurl());
					if (!StringUtils.isEmpty(bean.getSubmitCredentialImg())) {
						bean.setSubmitCredentialImg(value + Constant.readImg + bean.getSubmitCredentialImg());
					}
				}
			}

			return new PageUtils<Order>(page);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Transactional(readOnly = true)
	@Override
	public Order getInfoById(Long orderId) {
		try {
			Order order = orderMapper.selectByPrimaryKey(orderId);
			// 获取推广域名 链接图片显示
			String value = sysConfigService.getValue(Constant.SystemKey.SYSTEM_SPREAD_DOMAIN_KEY.getValue());
			order.setProductImgurl(value + Constant.readImg + order.getProductImgurl());
			if (!StringUtils.isEmpty(order.getSubmitCredentialImg())) {
				order.setSubmitCredentialImg(value + Constant.readImg + order.getSubmitCredentialImg());
			}
			return order;
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Override
	public void update(Order order) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		order.setUpdateUser(sysUser.getUserName());
		order.setUpdateTime(new Date());
		order.setSubmitCredentialImg(null);

		// 判断订单状态，
		if (order.getStatus() != OrderStatus.ONE.getValue() && order.getStatus() != OrderStatus.THREE.getValue()) {
			throw new RRException(ErrorCode.ONLY_UPDATE_ONE_ORDER_STATUS);
		}

		Order newOrder = orderMapper.selectByPrimaryKey(order.getOrderId());
		// 判断订单状态，
		if (newOrder.getStatus() != OrderStatus.ZERO.getValue()
				&& newOrder.getStatus() != OrderStatus.THREE.getValue()) {
			throw new RRException(ErrorCode.ONLY_UPDATE_ZERO_ORDER_STATUS);
		}

		orderMapper.updateByPrimaryKeySelective(order);

		// 如果是把状态改为收益中,则刷新报表.因为待支付的订单并未事先扣款
		if (order.getStatus() == Constant.Status.ONE.getValue()) {
			// 扣款
			WalletChange walletChange = new WalletChange();
			walletChange.setUserId(newOrder.getUserId());
			walletChange.setOperatorMoney(newOrder.getBuyMoney());
			walletChange.setRelationId(newOrder.getOrderId());
			walletService.orderSubtract(walletChange);
			// 刷新推荐父级团队业绩
			recommendService.upsert(newOrder.getParentId(), null, newOrder.getBuyMoney());
		}

	}

	@Override
	public void delete(Long orderId) {

		Order newOrder = orderMapper.selectByPrimaryKey(orderId);
		// 判断订单状态，
		if (newOrder.getStatus() == OrderStatus.ONE.getValue()) {
			throw new RRException(ErrorCode.ORDER_IS_OPEN);
		}

		try {
			orderMapper.deleteByPrimaryKey(orderId);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

}
