package com.boxamazing.webfront.util;

/**
 * 状态码
 */
public class Code {

	/**
	 * 200：成功
	 */
	public static final Long S_200 = 200L;
	
	/**
	 * 403：失败
	 */
	public static final Long F_403 = 403L;
	
	/**
	 * -1：请求不存在
	 */
	public static final Long F_1 = -1L;
	
	/**
	 * -100000：系统异常
	 */
	public static final Long F_100000 = -100000L;
	
	/**
	 * 00L: 验证超时
	 */
	public static final Long F_00 = 00L;
	
	/**
	 * 01L: 验证错误
	 */
	public static final Long F_01 = 01L;
	
	/**
	 * -2：校验不通过
	 */
	public static final Long F_02 = -02L;
	
	/**
	 * 11L: 验证正确
	 */
	public static final Long S_11 = 11L;
	
	/**
	 * 12L: 密码为空
	 */
	public static final Long F_12 = 12L;
	
	/**
	 * 13L: 用户锁定
	 */
	public static final Long F_13 = 13L;
	/**
	 * 14L: 用户名不存在
	 */
	public static final Long F_14 = 14L;
	
	/**
	 * 用户未绑定手机号
	 */
	public static final Long F_15 = 15L;
	
	/**
	 * 两次输入密码不一致
	 */
	public static final Long F_10000 = -10000L;
	
	/**
	 * 相同的请求已经处理了
	 */
	public static final Long F_20 = 20L;
	
	/**
	 * 操作有误
	 */
	public static final Long F_21 = 21L;
	
	/**
	 * 用户未设置支付密码
	 */
	public static final Long F_22 = 22L;
	
}
