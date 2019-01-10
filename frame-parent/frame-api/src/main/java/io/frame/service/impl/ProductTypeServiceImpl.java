package io.frame.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.frame.common.enums.Constant;
import io.frame.common.exception.RRException;
import io.frame.dao.entity.ProductType;
import io.frame.dao.entity.ProductTypeExample;
import io.frame.dao.mapper.ProductTypeMapper;
import io.frame.exception.ErrorCode;
import io.frame.service.ProductTypeService;

/**
 * 商品列表
 * 
 * @author fury
 *
 */
@Transactional
@Service("productTypeService")
public class ProductTypeServiceImpl implements ProductTypeService {

	Logger logger = LoggerFactory.getLogger(ProductTypeServiceImpl.class);

	@Autowired
	ProductTypeMapper productTypeMapper;

	@Override
	@Transactional(readOnly = true)
	public List<ProductType> getProductTypeList() {
		List<String> showField = Lists.newArrayList();
		showField.add(ProductType.FD_PRODUCTTYPEID);
		showField.add(ProductType.FD_PRODUCTTYPENAME);
		ProductTypeExample example = new ProductTypeExample();
		example.createCriteria().andStatusEqualTo(Constant.Status.ONE.getValue());
		try {
			return productTypeMapper.selectByExampleShowField(showField, example);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

}
