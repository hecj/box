package com.boxamazing.weixin.controller;

import com.boxamazing.service.wxuser.model.Wxuser;
import com.boxamazing.weixin.interceptor.WxApiConfigInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.JfinalxController;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.*;
import com.jfinal.weixin.sdk.ext.WxUtils;
import com.jfinal.weixin.sdk.kit.StringKit;

import java.util.logging.Logger;

/**
 * 绑定weixin openid 和系统 Uid 的Controller.
 * Created by pchome on 2015/8/4.
 */
public class BindController extends JfinalxController {

    private static Logger log = Logger.getLogger(BindController.class.getName());
    private static final String OPEN_ID = "openId";
    private static final String UID = "uid";
    private static final String CODE = "code";

    /**
     * 绑定微信用户ID功能页.
     */
    public void index() {
        renderNull();
    }

    /**
     * ================================================
     * 带有code参数的请求是由腾迅授权页返回的请求,
     * 1.通过code获取用户weixin详细信息并插入表
     * 2.通过code获取用户weixin详细信息并更新表
     * ==============================================
     **/
    @ClearInterceptor(ClearLayer.ALL)
    @Before(WxApiConfigInterceptor.class)
    public void code() {
        String code = getPara("code");
        if (null == code) renderError(430);

        //通过code获取openid
        String openId = null;
        SnsAccessToken result = SnsAccessTokenApi.getSnsAccessToken(PropKit.get("appId"), PropKit.get("appSecret"), code);
        openId = result.getOpenid();
        if (StringKit.isEmpty(openId)) {
            log.info("use code to get openid error, code:" + code);
            renderError(430);
        }

     
        ApiResult userInfo = UserApi.getUserInfo(openId);

        //查找openid是否存在wxuser表
        Wxuser wxuser = Wxuser.dao.findFirstByOpenId(openId);
        if (null == wxuser) {
            //创建wxuser
            new Wxuser().createWxuserByUserInfo(
                    userInfo.get("openid"), userInfo.get("nickname"), userInfo.get("sex"),userInfo.get("headimgurl"));
        } else {
            //更新wxuser
            new Wxuser().updateWxuserInfo(
                    userInfo.get("openid"), userInfo.get("nickname"), userInfo.get("sex"), userInfo.get("headimgurl"));
        }

        //OpenId已经得到
        getSession().setAttribute(OPEN_ID, openId);
        setCookie(OPEN_ID, openId, 7200);
        redirect("/autoLogin");
    }

   

}

