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
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.Product;
import io.frame.dao.entity.ProductExample;
import io.frame.dao.mapper.ProductMapper;
import io.frame.exception.ErrorCode;
import io.frame.service.ProductService;

/**
 * 商品列表
 * 
 * @author fury
 *
 */
@Transactional
@Service("productService")
public class ProductServiceImpl implements ProductService {

	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	ProductMapper productMapper;

	@Transactional(readOnly = true)
	@Override
	public List<Product> getProductList(Integer typeId) {
		List<String> showField = Lists.newArrayList();
		showField.add(Product.FD_PRODUCTID);
		showField.add(Product.FD_PRODUCTNAME);
		showField.add(Product.FD_SALEMONEY);
		showField.add(Product.FD_SALEQUANTITY);
		showField.add(Product.FD_REBATEMONEY);
		showField.add(Product.FD_REBATEPERIODS);
		showField.add(Product.FD_REBATETOTALS);
		showField.add(Product.FD_STARTREBATETIME);
		showField.add(Product.FD_PRODUCTIMGURL);
		showField.add(Product.FD_STARTREBATETIME);
		ProductExample example = new ProductExample();
		example.createCriteria().andProductTypeIdEqualTo(typeId).andStatusEqualTo(Constant.Status.ONE.getValue());
		example.setOrderByClause(SqlTools.orderByAscField(Product.FD_SORT));
		try {
			return productMapper.selectByExampleShowField(showField, example);
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

}
