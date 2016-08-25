package com.boxamazing.webfront.util;

import java.io.Serializable;

/**
 * AJAX请求时返回JSON对象
 */
public class ResultJson implements Serializable{
	
	private static final long serialVersionUID = -2040819162899511964L;

	public Long code;
	
	public Object data;
	
	public String message;
	
	
	
	public ResultJson() {
	}

	public ResultJson(Long code) {
		this.code = code;
	}

	public ResultJson(Long code, Object data) {
		this.code = code;
		this.data = data;
	}
	
	public ResultJson(Long code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResultJson(Long code, Object data, String message) {
		this.code = code;
		this.data = data;
		this.message = message;
	}



	public Long getCode() {
		return code;
	}


	public void setCode(Long code) {
		this.code = code;
	}
	
	

	public Object getData() {
		return data;
	}


	public void setData(Object data) {
		this.data = data;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
}
