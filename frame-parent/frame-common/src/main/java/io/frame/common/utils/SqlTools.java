package io.frame.common.utils;

/**
 * sql工具类
 * 
 * @author Fury
 *
 */
public class SqlTools {

	public static String countField(String field) {
		return String.format("COUNT(%s) as %s", field, field);
	}

	public static String sumField(String field) {
		return String.format("SUM(%s) as %s", field, field);
	}

	public static String orderByAscField(String field) {
		return String.format(" %s ASC", field);
	}

	public static String orderByDescField(String field) {
		return String.format(" %s DESC", field);
	}

}
