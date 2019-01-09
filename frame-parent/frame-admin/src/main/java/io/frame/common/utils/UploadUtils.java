
package io.frame.common.utils;

import io.frame.common.cloud.AliyunCloudStorageService;
import io.frame.common.cloud.CloudStorageConfig;
import io.frame.common.cloud.CloudStorageService;
import io.frame.common.cloud.QcloudCloudStorageService;
import io.frame.common.cloud.QiniuCloudStorageService;
import io.frame.common.enums.Constant;
import io.frame.modules.sys.service.SysConfigService;

/**
 * 文件上传Factory
 * 
 * @author fury
 *
 */
public final class UploadUtils {

	private static SysConfigService sysConfigService;

	public static CloudStorageService build() {

		UploadUtils.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
		// 获取云存储配置信息
		CloudStorageConfig config = sysConfigService.getConfigObject(Constant.ConfigEnum.CLOUD_STORAGE_CONFIG_KEY.getValue(), CloudStorageConfig.class);

		if (config.getType() == Constant.CloudEnum.QINIU.getValue()) {
			return new QiniuCloudStorageService(config);
		} else if (config.getType() == Constant.CloudEnum.ALIYUN.getValue()) {
			return new AliyunCloudStorageService(config);
		} else if (config.getType() == Constant.CloudEnum.QCLOUD.getValue()) {
			return new QcloudCloudStorageService(config);
		}

		return null;
	}

}
