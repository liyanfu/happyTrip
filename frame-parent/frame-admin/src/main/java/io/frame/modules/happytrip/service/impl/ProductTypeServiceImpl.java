
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

import io.frame.common.enums.Constant.Numbers;
import io.frame.common.enums.Constant.Sort;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.Product;
import io.frame.dao.entity.ProductType;
import io.frame.dao.entity.ProductTypeExample;
import io.frame.dao.entity.SysUser;
import io.frame.dao.mapper.ProductTypeMapper;
import io.frame.modules.happytrip.service.ProductService;
import io.frame.modules.happytrip.service.ProductTypeService;
import io.frame.modules.sys.shiro.ShiroUtils;

/**
 * 商品
 * 
 * @author fury
 *
 */
@Transactional
@Service
public class ProductTypeServiceImpl implements ProductTypeService {
	Logger logger = LoggerFactory.getLogger(ProductTypeServiceImpl.class);

	@Autowired
	ProductTypeMapper productTypeMapper;

	@Autowired
	ProductService productService;

	@Transactional(readOnly = true)
	@Override
	public PageUtils<ProductType> queryPage(ProductType productType) {

		String productTypeName = null;
		if (!StringUtils.isEmpty(productType.getProductTypeName())) {
			productTypeName = productType.getProductTypeName() + "%";
		}
		ProductTypeExample example = new ProductTypeExample();
		example.or().andProductTypeNameLikeIgnoreNull(productTypeName)
				.andStatusEqualToIgnoreNull(productType.getStatus());
		PageHelper.startPage(productType.getPageNumber(), productType.getPageSize());
		example.setOrderByClause(ProductType.FD_CREATETIME + Sort.DESC.getValue());

		try {
			Page<ProductType> page = (Page<ProductType>) productTypeMapper.selectByExample(example);
			return new PageUtils<ProductType>(page);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Transactional(readOnly = true)
	@Override
	public ProductType getInfoById(Long productTypeId) {
		try {
			return productTypeMapper.selectByPrimaryKey(productTypeId);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Transactional(readOnly = true)
	@Override
	public String getProductTypeName(Long productTypeId) {
		try {
			return productTypeMapper.selectByPrimaryKey(productTypeId).getProductTypeName();
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Override
	public void save(ProductType productType) {
		ProductTypeExample example = new ProductTypeExample();
		example.or().andProductTypeNameEqualTo(productType.getProductTypeName());
		// 校验是否存在相同商品
		int count = productTypeMapper.countByExample(example);
		if (count > Numbers.ZERO.getValue()) {
			throw new RRException(ErrorCode.PRODUCT_TYPE_ANDM_NAME_EXIST);
		}
		SysUser sysUser = ShiroUtils.getUserEntity();
		try {
			productType.setCreateUser(sysUser.getUserName());
			productType.setCreateTime(new Date());
			productTypeMapper.insertSelective(productType);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

	@Override
	public void update(ProductType productType) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		productType.setUpdateUser(sysUser.getUserName());
		productType.setUpdateTime(new Date());
		try {
			productTypeMapper.updateByPrimaryKeySelective(productType);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

	@Override
	public void delete(Long productTypeId) {

		Product product = new Product();
		product.setProductTypeId(productTypeId);
		// 校验是否存在该类型的商品
		PageUtils<Product> list = productService.queryPage(product);
		if (list.getList().size() != 0) {
			throw new RRException(ErrorCode.EXIST_THIS_PRODUCT);
		}
		try {
			productTypeMapper.deleteByPrimaryKey(productTypeId);
		} catch (Exception e) {
			logger.error(ErrorCode.OPERATE_FAILED, e);
			throw new RRException(ErrorCode.OPERATE_FAILED);
		}
	}

}
