package io.frame.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

import io.frame.annotation.SysLog;
import io.frame.common.enums.Constant;
import io.frame.common.exception.RRException;
import io.frame.common.utils.SqlTools;
import io.frame.dao.entity.Product;
import io.frame.dao.entity.ProductExample;
import io.frame.dao.mapper.ProductMapper;
import io.frame.exception.ErrorCode;
import io.frame.service.ConfigService;
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

	@Autowired
	ConfigService configService;

	@Transactional(readOnly = true)
	@Override
	public List<Product> getProductList(Long typeId) {
		List<String> showField = Lists.newArrayList();
		showField.add(Product.FD_PRODUCTID);
		showField.add(Product.FD_PRODUCTNAME);
		showField.add(Product.FD_SALEMONEY);
		showField.add(Product.FD_SALEQUANTITY);
		showField.add(Product.FD_SALEVOLUMES);
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
			List<Product> list = productMapper.selectByExampleShowField(showField, example);
			if (!CollectionUtils.isEmpty(list)) {
				// 获取推广域名参数,给前端显示完整的图片路径
				String value = configService.getConfigByKey(Constant.SystemKey.SYSTEM_SPREAD_DOMAIN_KEY.getValue());
				for (Product product : list) {
					if (StringUtils.isNotEmpty(product.getProductImgurl())) {
						product.setProductImgurl(value + Constant.readImg + product.getProductImgurl());
					}
				}
			}
			return list;
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}

	}

	@Transactional(readOnly = true)
	@Override
	public Product getProductById(Long productId) {
		try {
			Product product = productMapper.selectByPrimaryKey(productId);
			// 获取推广域名参数,给前端显示完整的图片路径
			String value = configService.getConfigByKey(Constant.SystemKey.SYSTEM_SPREAD_DOMAIN_KEY.getValue());
			if (product != null && StringUtils.isNotEmpty(product.getProductImgurl())) {
				product.setProductImgurl(value + Constant.readImg + product.getProductImgurl());
			}
			return product;
		} catch (Exception e) {
			logger.error(ErrorCode.GET_INFO_FAILED, e);
			throw new RRException(ErrorCode.GET_INFO_FAILED);
		}
	}

	@SysLog("删减库存")
	@Override
	public void reduceStock(Long productId, Integer quantityNum) {
		Product product = new Product();
		product.setProductId(productId);
		product.setSaleVolumes(1); // 已卖出 +1
		productMapper.updateByPrimaryKeySelectiveSync(product);

	}

}
