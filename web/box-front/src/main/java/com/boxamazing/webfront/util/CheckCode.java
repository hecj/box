package com.boxamazing.webfront.util;

import java.io.Serializable;

/**
 * 存验证码的VO对象
 */
public class CheckCode implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 手机验证码session key
	 */
	public static final String PHONE_CODE = "PHONE_CODE";
	
	/**
	 * 邮箱验证码session key
	 */
	public static final String EMAIL_CODE = "EMAIL_CODE";
	
	/**
	 * 用户id
	 */
	private Long user_id;
	
	/**
	 * 验证码
	 */
	private String randomCode ;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 发送时间
	 */
	private Long sendTime;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getRandomCode() {
		return randomCode;
	}

	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}
	
}
