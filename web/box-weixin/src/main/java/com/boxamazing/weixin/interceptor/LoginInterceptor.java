package com.boxamazing.weixin.interceptor;

import com.boxamazing.service.openiduid.model.Openiduid;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.kit.CookieKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.CodeApi;
import com.jfinal.weixin.sdk.kit.StringKit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * box-wexin拦截器.
 * 拦截未有openid,uid的请求.
 * 1. 未有openid, uid, 跳转至weixin授权页..
 * 2. 有openid, 未有uid, 跳转至Login页面.
 */
public class LoginInterceptor implements Interceptor {

    private static Logger log = Logger.getLogger(LoginInterceptor.class.getName());
    private static final String OPEN_ID = "openId";
    private static final String UID = "uid";
    private static final String CODE = "code";

    @Override
    public void intercept(ActionInvocation actionInvocation) {

        /** 从request及cookie中获取 openid, uid, code **/
        HttpServletRequest request = actionInvocation.getController().getRequest();

        String code = (String) request.getParameter(CODE);
        String openId = getValueFromSessionAndCookie(OPEN_ID, request);
        String uid = getValueFromSessionAndCookie(UID, request);

        if(StringKit.isNotEmpty(openId, uid)) {
            //已登录,请求放行
            actionInvocation.invoke();
        } else if (StringKit.isEmpty(openId) && StringKit.isEmpty(code)) {
            //没有任何记录,转入用户授权页
            String oauthUrl = CodeApi.getCode(PropKit.get("appId"), "http://wc.duomeidai.com/bind/code");
            actionInvocation.getController().redirect(oauthUrl);
            return;
        }
        actionInvocation.invoke();
    }

    /**
     * 从request及cookie中获取值
     * @param key
     * @param request
     * @return
     */
    public String getValueFromSessionAndCookie(String key, HttpServletRequest request) {
        String value = (String) request.getSession().getAttribute(key);
        if (StringKit.isEmpty(value)) {
            value = CookieKit.getCookie(OPEN_ID, request);
        }
        return value;
    }

}
