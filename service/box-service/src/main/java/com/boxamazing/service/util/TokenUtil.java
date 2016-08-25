package com.boxamazing.service.util;

import java.util.UUID;

/**
 * Token生成策略
 * by HECJ
 */
public class TokenUtil {
	
	
	/**
	 * token生产规则
	 * 暂时用UUID，以后有好的方式再重构规则
	 */
	public static String generalToken(){
		return  UUID.randomUUID().toString();
	}
	
}
