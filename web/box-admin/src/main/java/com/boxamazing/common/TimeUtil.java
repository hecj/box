package com.boxamazing.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeUtil <br />
 * 由easy_to_code移植而来
 * 
 */
public class TimeUtil {
	public static SimpleDateFormat def_date_format = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");
	public static SimpleDateFormat cn_date_format = new SimpleDateFormat(
			"yyyy年MM月dd日hh时mm分ss秒");
	public static SimpleDateFormat only_date_format = new SimpleDateFormat(
			"yyyyMMddkkmmss");

	/**
	 * 最常用日期格式化
	 * 
	 * @param date
	 * @return
	 */
	public static String defFormat(Date date) {
		return def_date_format.format(date);
	}

	/**
	 * 中文格式化
	 * 
	 * @param date
	 * @return
	 */
	public static String cnFormat(Date date) {
		return cn_date_format.format(date);
	}

	/**
	 * 只获取格式化时间 去掉其他字符
	 * 
	 * @param date
	 * @return
	 */
	public static String onlyFormat(Date date) {
		return only_date_format.format(date);
	}

}