package io.frame.controller;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;

import io.frame.annotation.Login;
import io.frame.common.exception.RRException;
import io.frame.common.utils.FileUtils;
import io.frame.common.utils.R;
import io.frame.common.validator.ValidatorUtils;
import io.frame.exception.ErrorCode;
import io.frame.form.OrderForm;
import io.frame.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 商品列表
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "订单接口")
public class ApiOrderController {

	@Autowired
	OrderService orderService;

	@Login
	@GetMapping("getMyOrderList")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,orderList:[{productName:商品名称,buyQuantity:投资数量}]}", value = "专属车位")
	public R getMyOrderList(@ApiIgnore @RequestAttribute("userId") Long userId) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("orderList", orderService.getMyOrderList(userId));
		return R.ok(map);
	}

	@Login
	@GetMapping("getMyBuyOrderList")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,orderList:[{productName:商品名称,buyMoney:投资金额,status:订单状态[0:待支付,1:收益中,2:已完成,3:已取消],createTime:创建时间,submitStatus上传凭证状态[0:未上传1:已上传]:}]}", value = "购买记录")
	public R getMyBuyOrderList(@ApiIgnore @RequestAttribute("userId") Long userId,
			@ApiParam(name = "typeId", value = "商品类型ID", required = true) @RequestParam("typeId") Long typeId) {
		if (typeId == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("orderList", orderService.getMyBuyOrderList(userId, typeId));
		return R.ok(map);
	}

	@Login
	@PostMapping("payOrder")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,qrCode:余额支付时为空,其余线下支付返回收款二维码,orderId:订单ID,randomCode:随机码（只有不是余额支付时才让用户线下转账填写）}", value = "下单购买")
	public R payOrder(@ApiIgnore @RequestAttribute("userId") Long userId, @RequestBody OrderForm form) {
		// 表单校验
		ValidatorUtils.validateEntity(form);
		if (form.getProductId() == null || form.getPaymentKey() == null) {
			throw new RRException(ErrorCode.PARAMS_IS_NOT_EMPTY);
		}
		return R.ok(orderService.payOrder(userId, form.getProductId(), form.getPaymentKey()));
	}

	@Login
	@PostMapping("/submitOrderCredential")
	@ApiOperation(notes = "{msg:消息提示,code:请求状态", value = "上传凭证")
	public R submitRechargeCredential(
			@ApiParam(name = "orderId", value = "订单ID", required = true) @RequestParam("orderId") Long orderId,
			@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RRException(ErrorCode.UPLOAD_NOT_EMPTY);
		}

		// 校验文件是否是图片格式
		String fileName = file.getOriginalFilename();
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.png|.PNG|.gif|.GIF|.bmp|.BMP)$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(fileName);
		if (!matcher.find()) {
			throw new RRException(ErrorCode.UPLOAD_FORMAT_EEEOR);
		}

		String path = "/home/credential/order/images/" + orderId + "/";
		if (file.getSize() != 0) {
			// 上传文件到本地
			String url = FileUtils.uploadFileToLocal(file, path);

			orderService.update(orderId, url);

		}
		return R.ok();
	}

}
