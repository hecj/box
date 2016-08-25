package com.boxamazing.service.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {
	private static final long ONE_MINUTE = 60000;// 一分钟
	private static final long ONE_HOUR = 3600000;
	private static final long ONE_DAY = 86400000;// 一天/二十四小时

	/**
	 * digits：保留几位小数（不采用四舍五入） <br>
	 * eg: num:3.141 digits:2 -> 3.14<br>
	 * num:3.147 digits:2 -> 3.14<br>
	 */
	public static double truncationDigits(double num, int digits) {
		DecimalFormat formater = new DecimalFormat();
		formater.setMaximumFractionDigits(digits);
		formater.setGroupingSize(0);
		formater.setRoundingMode(RoundingMode.FLOOR);
		return Double.valueOf(formater.format(num));
	}

	/**
	 * 千分位 eg: 100000 -> 100,000
	 */
	public static String thousandPoints(long number) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		return numberFormat.format(number);

	}

	/**
	 * 通过指定格式字符串格式化时间
	 * 
	 * @author XuXD
	 * @param format
	 * @param date
	 * @return
	 */
	public static String MyDateFormat(String format, Long date) {
		return new SimpleDateFormat(format).format(new Date(date));
	}

	/**
	 * 格式化评论时间
	 * 
	 * @author XuXD
	 * @date 2015-11-3
	 * @param date
	 * @return
	 */
	public static String MyCommentDateFormat(Long date) {
		long time = System.currentTimeMillis() - date;
		if (time < ONE_MINUTE) {
			return "刚刚";
		}
		if (time < ONE_HOUR) {
			long min = time / ONE_MINUTE;
			return (min == 0? min + 1 : min) + "分钟以前";
		}
		if (time < ONE_DAY) {
			long hour = time / ONE_HOUR;
			return (hour == 0? hour + 1 : hour) + "小时以前";
		}
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(date));
	}
	
	/**
	 * 字符串日期转long
	 */
	public static Long strDateToLong(String str){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format.parse(str).getTime();
		} catch (ParseException e) {
			throw new RuntimeException("日期转化失败");
		}
	}
}
