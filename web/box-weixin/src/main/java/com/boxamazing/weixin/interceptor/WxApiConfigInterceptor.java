package com.boxamazing.weixin.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.ext.WxUtils;

/**
 * box-wexin拦截器.
 * 拦截未有openid,uid的请求.
 * 1. 未有openid, uid, 跳转至weixin授权页..
 * 2. 有openid, 未有uid, 跳转至Login页面.
 */
public class WxApiConfigInterceptor implements Interceptor {


    @Override
    public void intercept(ActionInvocation actionInvocation) {
		ApiConfigKit.setThreadLocalApiConfig(WxUtils.getApiConfig());
         actionInvocation.invoke();
    }


}
