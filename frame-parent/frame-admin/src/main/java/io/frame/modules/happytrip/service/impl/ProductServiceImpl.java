
package io.frame.modules.happytrip.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;

import io.frame.common.enums.Constant;
import io.frame.common.enums.Constant.Numbers;
import io.frame.common.enums.Constant.Sort;
import io.frame.common.enums.Constant.Status;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Product;
import io.frame.dao.entity.ProductExample;
import io.frame.dao.entity.SysUser;
import io.frame.dao.mapper.ProductMapper;
import io.frame.modules.happytrip.service.ProductService;
import io.frame.modules.happytrip.service.ProductTypeService;
import io.frame.modules.sys.service.SysConfigService;
import io.frame.modules.sys.shiro.ShiroUtils;

/**
 * 商品
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class ProductServiceImpl implements ProductService {
	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	ProductMapper productMapper;

	@Autowired
	ProductTypeService productTypeService;

	@Autowired
	SysConfigService sysConfigService;

	@Transactional(readOnly = true)
	@Override
	public PageUtils<Product> queryPage(Product product) {

		String productName = null;
		if (!StringUtils.isEmpty(product.getProductName())) {
			productName = product.getProductName() + "%";
		}
		ProductExample example = new ProductExample();
		example.or().andProductNameLikeIgnoreNull(productName).andStatusEqualToIgnoreNull(product.getStatus())
				.andProductTypeIdEqualToIgnoreNull(product.getProductTypeId());
		PageHelper.startPage(product.getPageNumber(), product.getPageSize());
		example.setOrderByClause(Product.FD_CREATETIME + Sort.DESC.getValue());

		try {
			Page<Product> page = (Page<Product>) productMapper.selectByExample(example);

			if (!CollectionUtils.isEmpty(page)) {
				// 获取推广域名 链接图片显示
				String value = sysConfigService.getValue(Constant.SystemKey.SYSTEM_SPREAD_DOMAIN_KEY.getValue());
				for (Product bean : page.getResult()) {
					bean.setProductImgurl(value + Constant.readImg + bean.getProductImgurl());
					Map<String, Object> map = Maps.newHashMap();
					map.put("productTypeName", productTypeService.getProductTypeName(bean.getProductTypeId()));
					// 查询类型
					bean.setMap(map);
				}
			}

			return new PageUtils<Product>(page);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Transactional(readOnly = true)
	@Override
	public Product getInfoById(Long productId) {
		try {
			Product product = productMapper.selectByPrimaryKey(productId);
			// 获取推广域名 链接图片显示
			String value = sysConfigService.getValue(Constant.SystemKey.SYSTEM_SPREAD_DOMAIN_KEY.getValue());
			product.setProductImgurl(value + Constant.readImg + product.getProductImgurl());
			Map<String, Object> map = Maps.newHashMap();
			map.put("productTypeName", productTypeService.getProductTypeName(product.getProductTypeId()));
			// 查询类型
			product.setMap(map);
			return product;
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Override
	public void save(Product product) {
		ProductExample example = new ProductExample();
		example.or().andProductNameEqualTo(product.getProductName())
				.andProductTypeIdEqualTo(product.getProductTypeId());
		// 校验是否存在相同商品
		int count = productMapper.countByExample(example);
		if (count > Numbers.ZERO.getValue()) {
			throw new RRException(ErrorCode.PRODUCT_TYPE_ANDM_NAME_EXIST);
		}
		SysUser sysUser = ShiroUtils.getUserEntity();
		try {
			// 重新计算返利总额
			product.setRebateTotals(product.getRebateMoney().multiply(new BigDecimal(product.getRebatePeriods())));
			product.setCreateUser(sysUser.getUserName());
			product.setCreateTime(new Date());
			productMapper.insertSelective(product);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

	@Override
	public void update(Product product) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		product.setUpdateUser(sysUser.getUserName());
		product.setUpdateTime(new Date());
		try {
			productMapper.updateByPrimaryKeySelective(product);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

	@Override
	public void delete(Long productId) {

		// 校验该商品是否下架
		Product product = productMapper.selectByPrimaryKey(productId);
		if (product.getStatus() == Status.ONE.getValue()) {
			throw new RRException(ErrorCode.PLEASE_DOWN_PRODUCT);
		}
		try {
			productMapper.deleteByPrimaryKey(productId);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

}
