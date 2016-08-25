package com.boxamazing.webfront.util;

import java.io.Serializable;
import java.util.regex.Pattern;



/**
 * 校验工具类
 * 
 * @author yj
 * @version 2015/10/16
 * 
 */
public class CheckNum implements Serializable {

	/*
	 * 判断String字符串中是否为整数
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/*
	 * 判断String字符串中是否为Long
	 */
	public static boolean isLong(String value) {
		try {
			Long.parseLong(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// 判断是否为空以及""
	public static boolean isEmpty(String value) {
		if (value == null || "".equals(value.trim())) {
			return true;
		} else {
			return false;
		}
	}
	
	// 判断是否是邮箱
	public static boolean isEmail(String value) {
		String reg = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		return Pattern.compile(reg).matcher(value).matches();
	}

	// 判断是否是手机
	public static boolean isPhone(String value) {
		String reg = "^(((13[0-9])|(15([0-9]))|(17[0-9])|(18[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";
		return Pattern.compile(reg).matcher(value).matches();
	}

}
