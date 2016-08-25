package com.boxamazing.weixin.controller;

import com.boxamazing.service.openiduid.model.Openiduid;
import com.boxamazing.service.user.model.User;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.JfinalxController;
import com.jfinal.kit.CookieKit;
import com.jfinal.weixin.sdk.kit.StringKit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Logger;

/**
 * Weixin Web页-首页
 * Created by pchome on 2015/8/4.
 */
public class RootController extends JfinalxController {

    private static Logger log = Logger.getLogger(BindController.class.getName());
    private static final String OPEN_ID = "openId";
    private static final String UID = "uid";
    private static final String CODE = "code";

    /**
     * 微信首页
     */
    public void index() {
        render("index.ftl");
    }

    /**
     * 登录功能
     */
    @ClearInterceptor(ClearLayer.ALL)
    public void login() {
        String openId = getValueFromSessionAndCookie("wxa0ae3fb796c22120", getRequest());
        /** 检测用户登录账号密码 */
        String uid = getPara("uid");
        String password = getPara("pwd");
        if (StringKit.isEmpty(uid, password)) {
            render("login.ftl");
            return;
        }

        List<User> users = User.dao.findUser(uid, password,0);
        if (null != users && users.size() > 0) {
            getSession().setAttribute("uid", uid);
            setCookie("uid", uid, 720);
            Openiduid.dao.createOpeniduid(openId, uid);
            setAttr("msg", "已绑定:" + openId + " <-> " + uid);
            render("index.ftl");
        } else {
            render("login.ftl");
        }
    }

    /**
     * 登出
     */
    @ClearInterceptor(ClearLayer.ALL)
    public void logout() {
        CookieKit.deleteCookie(getRequest(), getResponse());
        getSession().removeAttribute("openId");
        getSession().removeAttribute("uid");
        render("logout.ftl");
        return;
    }

    /**
     * ======================================================
     * 在session及cookie中查找是否有uid
     * 在openiduid表中查找是否有绑定关系
     * 1. 有绑定关系, 将uid取出并自动登录
     * 2. 无绑定关系, 转到登录页.
     * <p/>
     * ======================================================
     */
    @ClearInterceptor(ClearLayer.ALL)
    public void autoLogin() {

        /** 获取变量 */
        String openId = getValueFromSessionAndCookie(OPEN_ID, getRequest());
        String uid = getValueFromSessionAndCookie(UID, getRequest());

        /** 检测uid和openId */
        if (StringKit.isEmpty(openId)) {
            redirect("/");
        }

        /** 有openid没有uid通过表查询uid */
        if (StringKit.isEmpty(uid)) {
            Openiduid openiduid = Openiduid.dao.findFirst("select * from openiduid where openid=?", openId);
            if (null != openiduid) {
                uid = openiduid.get("uid");
                getSession().setAttribute(UID, uid);
                setCookie("uid", uid, 7200);
                redirect("/");
            } else {
                redirect("/login");
            }
        }
    }

    /**
     * 通过session及cookie获取变量信息
     *
     * @param key
     * @param request
     * @return
     */
    public String getValueFromSessionAndCookie(String key, HttpServletRequest request) {
        String value = (String) request.getSession().getAttribute(key);
        if (null == value) {
            value = CookieKit.getCookie(key, request);
        }
        return value;
    }

}
