package com.boxamazing.webfront.util.constant;

/**
 * 给手机或者邮箱发送动态码时的信息模板类型
 * @author liyanlong
 * @version 2015/10/15 17:10
 */
public class NoticeTempleteEnum {
	/**
	 * 默认消息模板
	 */
	public static final Integer SEND_DEFAULT_CODE = 0;
	
	/**
	 * 找回密码时的消息模板
	 */
	public static final Integer SEND_FINDPWD_CODE = 1;
	
	/**
	 * 用户注册时的消息模板
	 */
	public static final Integer SEND_REG_CODE = 2;
	
	/**
	 * 用户支付时的消息模板
	 */
	public static final Integer SEND_PAYPWD_CODE = 3;
}
