package com.boxamazing.webfront.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 订单号生成规则
 * @author hechaojie
 */
public class OrdersUtils {

	private static final SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	/**
	 * 充值订单号生成规则
	 * 时间戳+6位随机数+R+user_id
	 */
	public static String orderRecharge(Long user_id){
		String dateStr = format.format(new Date());
		String random = getRandom(5);
		return dateStr+random+"R"+user_id;
	}
	
	/**
	 * 支持订单号生成规则
	 * 时间戳+6位随机数+P+user_id
	 */
	public static String orderPayNum(Long user_id){
		String dateStr = format.format(new Date());
		String random = getRandom(5);
		return dateStr+random+"P"+user_id;
	}
	
	/**
	 * 随机生成几位数
	 */
	public static String getRandom(int n) {
		int baseNum = 1;
		for (int i = 0; i < n; i++) {
			baseNum *= 10;
		}
		Random rad = new Random();
		String orders = String.valueOf(rad.nextInt(baseNum));
		int len = orders.length();
		for (int i = 0; i < n - len; i++) {
			orders = "0"+orders;
		}
		return orders;
	}
	
}
