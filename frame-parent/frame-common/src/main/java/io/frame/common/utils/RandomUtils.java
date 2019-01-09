package io.frame.common.utils;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.Validate;

public class RandomUtils {

	public static int nextInt(int startNum, int endNum) {
		Validate.isTrue(endNum >= startNum, "开始数值必须小于或等于结束数值");
		Validate.isTrue(startNum >= 0, "开始数值必须大于等于0");
		if (startNum == endNum) {
			return startNum;
		}
		return startNum + new Random().nextInt(endNum - startNum);

	}

	public static String nextDate() {
		return DateUtils.format(new Date(), "yyyyMMddHHmmss") + nextInt(1000, 9999);
	}
}
