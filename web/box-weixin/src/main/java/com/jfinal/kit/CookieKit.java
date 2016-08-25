package com.jfinal.kit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CookieKit
 * Created by pchome on 2015/8/5.
 */
public class CookieKit {
    /**
     * 在Cookie中获取参数
     * @return
     */
    public static String getCookie(String cookieName, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 销毁cookie
     * @param request
     * @param response
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies =  request.getCookies();
        for(Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
}
