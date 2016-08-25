package com.boxamazing.webfront.util;

import javax.servlet.http.HttpSession;

import com.boxamazing.service.open_user.model.OpenUser;


/**
 * 第三方用户工具类
 * 
 * @author XuXD
 * 
 */
public class OpenUserUtil {
	// SessionKey
	private static final String OPEN_USER_SESSION_KEY = "OPEN_USER_SESSION_KEY";

	/**
	 * 将第三方用户存入session中
	 * @author XuXD
	 * @param openUser
	 * @param httpSession
	 */
	public static void setOpenUser(OpenUser openUser, HttpSession httpSession) {
		httpSession.setAttribute(OPEN_USER_SESSION_KEY, openUser);
	}

	/**
	 * 从session中获取第三方用户
	 * @author XuXD
	 * @param httpSession
	 * @return
	 */
	public static OpenUser getOpenUser(HttpSession httpSession) {
        return (OpenUser) httpSession.getAttribute(OPEN_USER_SESSION_KEY);
   }
	
	/**
	 * 退出登录
	 * yj
	 * 
	 */
	public static void removeOpenUser(HttpSession httpSession){
		httpSession.removeAttribute(OPEN_USER_SESSION_KEY);
	    httpSession.invalidate();
	}
	
	 /**
     * 检测是否登录
     * yj
     * @param httpSession
     * @return
     */
    public static boolean isLogin(HttpSession httpSession) {
        OpenUser openUser = getOpenUser(httpSession);
        if (null == openUser) {
            return false;
        }
        return true;
    }
}
