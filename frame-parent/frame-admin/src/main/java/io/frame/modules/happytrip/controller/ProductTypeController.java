
package io.frame.modules.happytrip.controller;

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
import io.frame.dao.entity.ProductType;
import io.frame.modules.happytrip.service.ProductTypeService;
import io.frame.modules.sys.controller.AbstractController;

/**
 * 商品类型
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/ht/productType")
public class ProductTypeController extends AbstractController {
	@Autowired
	private ProductTypeService productTypeService;

	/**
	 * 所有商品类型列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ht:productType:list")
	public PageUtils<ProductType> list(ProductType productType) {
		return productTypeService.queryPage(productType);
	}

	/**
	 * 商品类型信息
	 */
	@RequestMapping("/info/{productTypeId}")
	@RequiresPermissions("ht:productType:info")
	public R info(@PathVariable("productTypeId") Long productTypeId) {
		return R.ok().put("productType", productTypeService.getInfoById(productTypeId));
	}

	/**
	 * 新增商品类型
	 */
	@SysLog("新增商品类型")
	@RequestMapping("/save")
	@RequiresPermissions("ht:productType:save")
	public R save(@RequestBody ProductType productType) {
		productTypeService.save(productType);
		return R.ok();
	}

	/**
	 * 修改商品类型
	 */
	@SysLog("修改商品类型")
	@RequestMapping("/update")
	@RequiresPermissions("ht:productType:update")
	public R update(@RequestBody ProductType productType) {
		productTypeService.update(productType);
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@SysLog("修改商品类型状态")
	@RequestMapping("/status")
	@RequiresPermissions("ht:productType:update")
	public R status(Long productTypeId, Integer status) {
		if (status == null || productTypeId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		ProductType productType = new ProductType();
		productType.setProductTypeId(productTypeId);
		productType.setStatus(status);
		productTypeService.update(productType);
		return R.ok();
	}

	/**
	 * 删除商品类型
	 */
	@SysLog("删除商品类型")
	@RequestMapping("/delete")
	@RequiresPermissions("ht:productType:delete")
	public R delete(Long productTypeId) {
		if (productTypeId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		productTypeService.delete(productTypeId);
		return R.ok();
	}

}
