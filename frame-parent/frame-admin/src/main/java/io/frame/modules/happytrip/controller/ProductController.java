
package io.frame.modules.happytrip.controller;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.frame.common.annotation.SysLog;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.R;
import io.frame.dao.entity.Product;
import io.frame.modules.happytrip.service.ProductService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 商品
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/ht/product")
public class ProductController extends AbstractController {
	@Autowired
	private ProductService productService;

	/**
	 * 所有商品列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ht:product:list")
	public PageUtils<Product> list(Product product) {
		return productService.queryPage(product);
	}

	/**
	 * 商品信息
	 */
	@RequestMapping("/info/{productId}")
	@RequiresPermissions("ht:product:info")
	public R info(@PathVariable("productId") Long productId) {
		Product product = productService.getInfoById(productId);
		return R.ok().put("product", product);
	}

	/**
	 * 新增商品
	 */
	@SysLog("新增商品")
	@RequestMapping("/save")
	@RequiresPermissions("ht:product:save")
	public R save(@RequestBody Product product) {
		productService.save(product);
		return R.ok();
	}

	/**
	 * 修改商品
	 */
	@SysLog("修改商品")
	@RequestMapping("/update")
	@RequiresPermissions("ht:product:update")
	public R update(@RequestBody Product product) {
		// 如果带有http请求的图片，说明没有更新。
		if (!StringUtils.isEmpty(product.getProductImgurl()) && product.getProductImgurl().contains("http")) {
			product.setProductImgurl(null);
		}
		if ("".equals(product.getProductImgurl())) {
			product.setProductImgurl(null);
		}
		// 重新计算返利总额
		product.setRebateTotals(product.getRebateMoney().multiply(new BigDecimal(product.getRebatePeriods())));
		productService.update(product);
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@SysLog("修改状态")
	@RequestMapping("/status")
	@RequiresPermissions("ht:product:update")
	public R status(Long productId, Integer status) {
		if (status == null || productId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		Product product = new Product();
		product.setProductId(productId);
		product.setStatus(status);
		productService.update(product);
		return R.ok();
	}

	/**
	 * 删除商品
	 */
	@SysLog("删除商品")
	@RequestMapping("/delete")
	@RequiresPermissions("ht:product:delete")
	public R delete(Long productId) {
		if (productId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		productService.delete(productId);
		return R.ok();
	}

}
