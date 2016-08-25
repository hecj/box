package com.boxamazing.service.util;

import java.math.BigDecimal;

/**
 * 常量配置类
 * @author HECJ
 */
public class Constant {
	
	/**
	 * 静态域名
	 */
	public static String STATIC_URL = "";
	
	/**
	 * 服务器域名
	 */
	public static String WEBROOT_URL = "";
	
	/**
	 * 最低筹款金额
	 */
	public static BigDecimal fundGoalBegin = new BigDecimal(500);
	
	/**
	 * 最高筹款金额
	 */
	public static BigDecimal fundGoalEnd = new BigDecimal(100000000);
	
	/**
	 * 支付宝-支付订单服务器异步通知页面路径
	 */
	public static String pay_order_notify_url = "";
	
	/**
	 * 支付宝-支付订单页面跳转同步通知页面路径
	 */
	public static String pay_order_return_url = "";
	
	/**
	 * 支付宝-订单退款异步通知接口
	 */
	public static String refund_order_notify_url = "";
	/**
	 * 支付宝-提现异步通知接口
	 */
	public static String withdrawals_order_notify_url = "";
	
	/**
	 * 订单失效时间
	 * 订单创建后120分钟失效
	 */
	public static long order_invalid_time = 2 * 60 * 60 * 1000;
	
	
	/**
	 * 第三方应用登录配置
	 */
	public static String qq_lable = "";
	public static String weibo_lable = "";
	public static String wechat_AppID = "";
	public static String wechat_redirect_URI = "";
}
