package io.frame.controller;

import java.math.BigDecimal;
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

import io.frame.annotation.Login;
import io.frame.common.exception.RRException;
import io.frame.common.utils.FileUtils;
import io.frame.common.utils.R;
import io.frame.common.validator.ValidatorUtils;
import io.frame.exception.ErrorCode;
import io.frame.form.RechargeForm;
import io.frame.service.RechargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 充值
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "充值接口")
public class ApiRechargeController {

	@Autowired
	RechargeService rechargeService;

	@Login
	@PostMapping("rechargeSubmit")
	@ApiOperation(notes = "{msg:消息提示,code:请求状态,rechargeCode:用户转账时填写的随机码rechargeCode,qrCode:收款二维码图片}", value = "充值申请")
	public R rechargeSubmit(@ApiIgnore @RequestAttribute("userId") Long userId, @RequestBody RechargeForm form) {
		// 表单校验
		ValidatorUtils.validateEntity(form);
		if (form.getMoney() == null || form.getMoney().compareTo(BigDecimal.ZERO) == -1) {
			return R.error(ErrorCode.RECHAGER_MONEY_IS_ERROR);
		}
		return R.ok(rechargeService.rechargeSubmit(userId, form.getMoney()));
	}

	@Login
	@GetMapping("getRechargeList")
	@ApiOperation(notes = "{msg:消息提示,code:请求状态,rechargeList:{rechargeId:充值订单ID,rechargeMoney:充值金额,rechargeCode:随机码,status:状态[0:待支付,1:已完成,2:异常],createTime:创建时间,submitStatus:上传转账凭证状态:[0:未上传，1：已上传}}", value = "充值列表")
	public R getRechargeList(@ApiIgnore @RequestAttribute("userId") Long userId) {
		return R.ok().put("rechargeList", rechargeService.getRechargeList(userId));
	}

	@Login
	@PostMapping("/submitRechargeCredential")
	@ApiOperation(notes = "{msg:消息提示,code:请求状态", value = "上传凭证")
	public R submitRechargeCredential(
			@ApiParam(name = "rechargeId", value = "充值订单ID", required = true) @RequestParam("rechargeId") Long rechargeId,
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

		String path = "/home/credential/recharge/images/" + rechargeId + "/";
		if (file.getSize() != 0) {
			// 上传文件到本地
			String url = FileUtils.uploadFileToLocal(file, path);

			rechargeService.update(rechargeId, url);

		}
		return R.ok();
	}

}
