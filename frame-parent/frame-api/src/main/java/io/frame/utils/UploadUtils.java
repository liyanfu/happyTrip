
package io.frame.utils;

import io.frame.common.cloud.AliyunCloudStorageService;
import io.frame.common.cloud.CloudStorageConfig;
import io.frame.common.cloud.CloudStorageService;
import io.frame.common.cloud.QcloudCloudStorageService;
import io.frame.common.cloud.QiniuCloudStorageService;
import io.frame.common.enums.Constant;
import io.frame.common.utils.SpringContextUtils;
import io.frame.service.ConfigService;

/**
 * 文件上传Factory
 * 
 * @author fury
 *
 */
public final class UploadUtils {

	private static ConfigService configService;

	public static CloudStorageService build() {

		UploadUtils.configService = (ConfigService) SpringContextUtils.getBean("configService");
		// 获取云存储配置信息
		CloudStorageConfig config = configService
				.getConfigObject(Constant.ConfigEnum.CLOUD_STORAGE_CONFIG_KEY.getValue(), CloudStorageConfig.class);

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
