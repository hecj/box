package com.boxamazing.service.util;

import java.io.Serializable;

/**
 * 简单封装支付结果
 */
public class PayResult implements Serializable {

	private static final long serialVersionUID = 1L;

	public long code;
	public String massage;

	public PayResult() {
		super();
	}

	public PayResult(long code, String massage) {
		super();
		this.code = code;
		this.massage = massage;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

}
