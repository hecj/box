package com.boxamazing.webfront.util.constant;

import com.boxamazing.webfront.util.Code;
import com.boxamazing.webfront.util.ResultJson;

/**
 * 各种校验所以使用到的可配置参数
 * @author liyanlong
 * @version 2015/10/15 17:43
 */
public class ValidParamEnum {
	/**
	 * 验证超时
	 */
	public static ResultJson TIMEOUT = new ResultJson(Code.F_00, "验证码超时");
	
	/**
	 * 验证码错误
	 */
	public static final ResultJson IDENTIFY_CODE_ERROR = new ResultJson(Code.F_01, "验证码错误");
	
	/**
	 * 验证码验证正确
	 */
	public static final ResultJson IDENTIFY_CODE_SUCCESS = new ResultJson(Code.S_11, "验证码正确");
}
