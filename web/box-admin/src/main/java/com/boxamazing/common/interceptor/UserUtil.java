package com.boxamazing.common.interceptor;

import javax.servlet.http.HttpSession;

import com.boxamazing.service.u.model.PUser;

public class UserUtil {
	/**
	 * session中user的key
	 */
	public static final String U_KEY_SESSION="U_KEY_SESSION";
	/**是否登录
	 * @param httpSession 
	 * @param httpSession 
	 * @return 
	 * @return
	 */
	public static boolean isLogin(HttpSession httpSession) {
       PUser u = getU(httpSession);
       if (u==null) {
    	   return false;
       }else {
    	   return true;
       }
	}
	public static PUser getU(HttpSession httpSession) {
		PUser user = (PUser) httpSession.getAttribute(U_KEY_SESSION);
		return user;
	}
	/**设置user到session中
	 * @param u
	 * @param session
	 */
	public static void setU(PUser u, HttpSession session) {
		session.setAttribute(U_KEY_SESSION, u);
	}
	public static void removeU(HttpSession session) {
		session.removeAttribute(U_KEY_SESSION);
		session.invalidate();
	}
}
