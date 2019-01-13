package io.frame.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.frame.annotation.SysLog;
import io.frame.common.enums.Constant;
import io.frame.common.enums.Constant.ChangeType;
import io.frame.common.enums.Constant.OrderStatus;
import io.frame.common.enums.Constant.RechargeKey;
import io.frame.common.exception.RRException;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.Order;
import io.frame.dao.entity.OrderExample;
import io.frame.dao.entity.Product;
import io.frame.dao.entity.ProductType;
import io.frame.dao.entity.User;
import io.frame.dao.entity.Wallet;
import io.frame.dao.mapper.OrderMapper;
import io.frame.entity.OrderVo;
import io.frame.exception.ErrorCode;
import io.frame.service.ConfigService;
import io.frame.service.OrderService;
import io.frame.service.ProductService;
import io.frame.service.ProductTypeService;
import io.frame.service.RecommendService;
import io.frame.service.WalletChangeService;
import io.frame.service.WalletService;
import io.frame.utils.SessionUtils;

/**
 * 订单
 * 
 * @author fury
 *
 */
@Transactional
@Service("orderService")
public class OrderServiceImpl implements OrderService {

	Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	OrderMapper orderMapper;

	@Autowired
	WalletService walletService;

	@Autowired
	WalletChangeService walletChangeService;

	@Autowired
	RecommendService recommendService;

	@Autowired
	ProductService productService;

	@Autowired
	ConfigService configService;

	@Autowired
	ProductTypeService productTypeService;

	@Transactional(readOnly = true)
	@Override
	public List<Order> getMyOrderList(Long userId) {
		List<String> showField = Lists.newArrayList();
		showField.add(Order.FD_PRODUCTID);
		showField.add(Order.FD_PRODUCTNAME);
		showField.add(Order.FD_BUYQUANTITY);
		OrderExample example = new OrderExample();
		example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(Constant.Status.ONE.getValue());// 收益中的

		List<Order> list = Lists.newArrayList();
		try {
			List<Order> orderList = orderMapper.selectByExampleShowField(showField, example);
			if (!CollectionUtils.isEmpty(orderList)) {
				Map<String, Integer> map = Maps.newHashMap();
				for (Order order : orderList) {
					if (!map.containsKey(order.getProductName())) {
						map.put(order.getProductName(), order.getBuyQuantity());
					} else {
						Integer num = map.get(order.getProductName());
						map.put(order.getProductName(), num + order.getBuyQuantity());
					}
				}

				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					Order order = new Order();
					order.setProductName(entry.getKey());
					order.setBuyQuantity(entry.getValue());
					list.add(order);
				}
			}
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public List<OrderVo> getMyBuyOrderList(Long userId, Long typeId) {
		List<String> showField = Lists.newArrayList();
		showField.add(Order.FD_PRODUCTNAME);
		showField.add(Order.FD_CREATETIME);
		showField.add(Order.FD_STATUS);
		showField.add(Order.FD_BUYMONEY);
		OrderExample example = new OrderExample();
		example.createCriteria().andUserIdEqualTo(userId).andProductTypeIdEqualTo(typeId);
		example.setOrderByClause(SqlTools.orderByDescField(Order.FD_CREATETIME));

		List<OrderVo> resultList = Lists.newArrayList();
		List<Order> list = orderMapper.selectByExampleShowField(showField, example);
		if (!CollectionUtils.isEmpty(list)) {
			for (Order order : list) {
				OrderVo orderVo = new OrderVo();
				BeanUtils.copyProperties(order, orderVo);
				orderVo.setStatus(Constant.OrderStatus.getName(order.getStatus()));
				resultList.add(orderVo);
			}
		}
		return resultList;
	}

	@Transactional(readOnly = true)
	@Override
	public int getMyBuyOrderListCount(Long userId) {
		List<Integer> status = Lists.newArrayList();
		status.add(OrderStatus.ONE.getValue());
		status.add(OrderStatus.TWO.getValue());
		OrderExample example = new OrderExample();
		OrderExample.Criteria cr = example.createCriteria();
		cr.andUserIdEqualTo(userId);
		cr.andStatusIn(status);
		return orderMapper.countByExample(example);

	}

	@Transactional(readOnly = true)
	@Override
	public BigDecimal getProfitOrderMoney() {
		List<String> showField = Lists.newArrayList();
		showField.add(SqlTools.sumField(Order.FD_BUYMONEY));
		OrderExample example = new OrderExample();
		example.createCriteria().andStatusEqualTo(Constant.Status.ONE.getValue());
		Order order = orderMapper.selectOneByExampleShowField(showField, example);
		return order == null ? BigDecimal.ZERO : order.getBuyMoney();
	}

	@SysLog("购买车位")
	@Override
	public Map<String, Object> payOrder(Long userId, Long productId, Long paymentId) {
		Map<String, Object> map = Maps.newHashMap();
		User user = SessionUtils.getCurrentUser();
		// 查询出商品
		Product product = productService.getProductById(productId);
		// 校验余额
		Wallet wallet = walletService.getWallet(userId);
		if (wallet.getBalance().compareTo(product.getSaleMoney()) == -1) {
			throw new RRException(ErrorCode.GOLDCOIN_IS_NOT_ENOUGH);
		}
		// 校验库存剩余数量
		if (product.getSaleQuantity() <= 0) {
			throw new RRException(ErrorCode.INSUFFICIENT_STOCK);
		}

		// 校验状态
		if (product.getStatus() == Constant.Status.ZERO.getValue()) {
			throw new RRException(ErrorCode.CAR_LOWER_SHEIF);
		}

		// 校验是否超过购买数量
		if (product.getPurchaseRestriction() > 0) {
			int count = this.getOrderCount(userId, productId);
			if (count >= product.getPurchaseRestriction()) {
				throw new RRException(ErrorCode.EXCEEDING_THE_PURCHASE_LIMIT);
			}
		}

		Date date = new Date();
		// 生成订单
		Order order = new Order();
		order.setUserId(userId);
		order.setUserMobile(user.getUserMobile());
		order.setBuyQuantity(1);// 默认1
		order.setBuyMoney(product.getSaleMoney());
		order.setProductTypeId(product.getProductTypeId());
		// 查询商品类型名称
		ProductType productType = productTypeService.getProductTypeById(product.getProductTypeId());
		order.setProductTypeName(productType.getProductTypeName());
		order.setProductName(product.getProductName());
		order.setProductImgurl(product.getProductImgurl());
		order.setProductId(productId);
		order.setRebateMoney(product.getRebateMoney());
		order.setTotalRebatePeriods(product.getRebatePeriods());
		order.setProfitMoney(product.getRebateTotals());
		order.setStartRebateTime(product.getStartRebateTime());
		order.setRebatePeriods(0);
		order.setCreateTime(date);
		order.setCreateUser(user.getUserName());
		order.setPaymentId(paymentId);
		String randomCode = UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 6);
		order.setRandomCode(randomCode);// 随机码
		// 余额支付
		String qrCode = null;
		Integer status = 0;

		// 余额支付
		if (paymentId == Constant.PaymentKey.PAYMENT_WALLET_KEY.getValue()) {
			status = Constant.Status.ONE.getValue();
		}
		order.setStatus(status);
		// 保存
		orderMapper.insertSelective(order);

		if (paymentId == Constant.PaymentKey.PAYMENT_WALLET_KEY.getValue()) {
			// 扣款
			walletService.deduction(wallet.getWalletId(), order.getBuyMoney());
			// 记录帐变
			walletChangeService.createWalletChange(userId, order.getBuyMoney(), order.getOrderId(),
					ChangeType.PURCHASE_CAR_SPACE_KEY);
			// 写入推荐表信息 ,算入父级的团队业绩中..
			recommendService.upsert(user.getParentId(), order.getBuyMoney());
		} else {
			// 其他线下支付获取收款二维码
			qrCode = configService.getConfigByKey(RechargeKey.RECHARGE_QRCODE_KEY.getValue());
		}

		// 减库存
		productService.reduceStock(productId, order.getBuyQuantity());
		// 返回收款二维码图片，和随机码
		map.put("qrCode", qrCode);
		map.put("randomCode", order.getRandomCode());
		map.put("orderId", order.getOrderId());
		return map;

	}

	public int getOrderCount(Long userId, Long productId) {
		OrderExample example = new OrderExample();
		OrderExample.Criteria cr = example.createCriteria();
		cr.andUserIdEqualTo(userId);
		cr.andProductIdEqualTo(productId);
		Integer[] status = { Constant.Status.ZERO.getValue(), Constant.Status.ONE.getValue() };
		cr.andStatusIn(Arrays.asList(status));
		return orderMapper.countByExample(example);

	}

}
