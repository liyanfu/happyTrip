
package io.frame.modules.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import io.frame.common.cloud.CloudStorageConfig;
import io.frame.common.enums.Constant;
import io.frame.common.enums.Constant.CloudEnum;
import io.frame.common.enums.Constant.ConfigEnum;
import io.frame.common.exception.ErrorCode;
import io.frame.common.exception.RRException;
import io.frame.common.utils.FileUtils;
import io.frame.common.utils.PageUtils;
import io.frame.common.utils.R;
import io.frame.common.validator.ValidatorUtils;
import io.frame.common.validator.group.AliyunGroup;
import io.frame.common.validator.group.QcloudGroup;
import io.frame.common.validator.group.QiniuGroup;
import io.frame.dao.entity.SysOss;
import io.frame.modules.sys.service.SysConfigService;
import io.frame.modules.sys.service.SysOssService;

/**
 * 文件上传
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController {
	@Autowired
	private SysOssService sysOssService;
	@Autowired
	private SysConfigService sysConfigService;

	private final static String KEY = ConfigEnum.CLOUD_STORAGE_CONFIG_KEY.getValue();

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:oss:all")
	public PageUtils<SysOss> list(SysOss sysOss) {
		return sysOssService.queryPage(sysOss);
	}

	/**
	 * 云存储配置信息
	 */
	@RequestMapping("/config")
	@RequiresPermissions("sys:oss:all")
	public R config() {
		CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);

		return R.ok().put("config", config);
	}

	/**
	 * 保存云存储配置信息
	 */
	@RequestMapping("/saveConfig")
	@RequiresPermissions("sys:oss:all")
	public R saveConfig(@RequestBody CloudStorageConfig config) {
		// 校验类型
		ValidatorUtils.validateEntity(config);

		if (config.getType() == CloudEnum.QINIU.getValue()) {
			// 校验七牛数据
			ValidatorUtils.validateEntity(config, QiniuGroup.class);
		} else if (config.getType() == CloudEnum.ALIYUN.getValue()) {
			// 校验阿里云数据
			ValidatorUtils.validateEntity(config, AliyunGroup.class);
		} else if (config.getType() == CloudEnum.QCLOUD.getValue()) {
			// 校验腾讯云数据
			ValidatorUtils.validateEntity(config, QcloudGroup.class);
		}

		sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));

		return R.ok();
	}

	/**
	 * 上传文件
	 */
	@RequestMapping("/upload")
	@RequiresPermissions("sys:oss:all")
	public R upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}

		Map<String, Object> map = new HashMap<>();
		// 上传文件
		// String suffix =
		// file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		// String url = UploadUtils.build().uploadSuffix(file.getBytes(), suffix);
		String path = "/home/images/";
		if (file.getSize() != 0) {
			// 上传文件到本地
			String url = FileUtils.uploadFileToLocal(file, path);
			// 保存文件信息
			SysOss sysOss = new SysOss();
			sysOss.setUrl(url);
			sysOss.setCreateTime(new Date());
			sysOssService.insert(sysOss);

			String value = sysConfigService.getValue(Constant.SystemKey.SYSTEM_MANAGE_DOMAIN_KEY.getValue());
			map.put("showPath", value + Constant.readImg + url);// 显示路径
			map.put("src", url);// 存库如今
			return R.ok().put("data", map);
		}
		return R.error(ErrorCode.UPLOAD_ERROR);
	}

	/**
	 * 富文本上传文件
	 */
	@RequestMapping("/uploadFull")
	@RequiresPermissions("sys:oss:all")
	public R uploadFull(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}

		Map<String, Object> map = new HashMap<>();
		String path = "/home/images/";
		if (file.getSize() != 0) {
			// 上传文件到本地
			String url = FileUtils.uploadFileToLocal(file, path);
			// 保存文件信息
			SysOss sysOss = new SysOss();
			sysOss.setUrl(url);
			sysOss.setCreateTime(new Date());
			sysOssService.insert(sysOss);

			String value = sysConfigService.getValue(Constant.SystemKey.SYSTEM_MANAGE_DOMAIN_KEY.getValue());
			map.put("src", value + Constant.readImg + url);// 显示路径
			map.put("alt", url);// 存库如今
			map.put("showPath", value + Constant.readImg + url);// 存库如今
			map.put("title", url);// 存库如今
			return R.ok().put("data", map);
		}
		return R.error(ErrorCode.UPLOAD_ERROR);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:oss:all")
	public R delete(@RequestBody Long[] ids) {
		sysOssService.deleteBatchIds(Arrays.asList(ids));

		return R.ok();
	}

}
