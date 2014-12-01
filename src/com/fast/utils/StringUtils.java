package com.fast.utils;

public class StringUtils {

	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim()) ? true : false;
	}

	public static boolean isEmpty(Object str) {
		return str == null ? true : false;
	}

	public static String defaultIfEmpty(String value, String defaultValue) {
		if (isBlank(value)) {
			if (isBlank(defaultValue)) {
				return "";
			} else {
				return defaultValue;
			}
		} else {
			return value;
		}
	}

	public static String formatGetMethodName(String name) {
		return "get".concat(name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase()));
	}

	public static String formatSetMethodName(String name) {
		return "set".concat(name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase()));
	}

	public static String substringLastSuffix(String value) {
		return value.substring(0, value.length() - 1);
	}

}
