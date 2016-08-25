package com.boxamazing.service.common;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * PasswordUtil.
 * Created by jhl on 2015/7/31.
 */
public class PasswordUtil {
    //key
    private static final String key = "boxamazing.com";

    //通过key+密码生成加密密码
    public static String encryptPassword(String src) {
    	if(src == null){
    		throw new RuntimeException("加密密码不能为空");
    	}
        return DigestUtils.md5Hex(key + src);
    }
}