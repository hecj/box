package com.boxamazing.webfront.interceptor;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.user.model.User;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.kit.StrKit;
/**
 * 登陆拦截器
 * by HECJ
 */
public class LoginInterceptor implements Interceptor{
	
	public static Log log = LogFactory.getLog(LoginInterceptor.class);
	
	public void intercept(ActionInvocation ai) {
		HttpServletRequest req = ai.getController().getRequest();
		HttpServletResponse resp = ai.getController().getResponse();
		User user = UserUtil.getUser(req.getSession());
		try {
			String queryString = req.getQueryString();
			String uri = req.getRequestURI();
			String back_url = uri;
			String retUrl = req.getHeader("Referer");
			if (StrKit.isBlank(retUrl)) {
				retUrl = "/";
			}
			if(queryString != null){
				back_url = uri+"?"+queryString;
			}
		
			if (user == null) {
				log.info(" user no login , back url : " + back_url);
				// AJAX请求
				if (req.getHeader("x-requested-with") != null && req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
					// 给个状态码
					log.info("ajax访问超时，跳转到登录页面。");
					resp.setStatus(999);
					ai.getController().renderNull();
					return;
				} else {
					// 过滤递归情况
					if ( queryString!=null && queryString.indexOf("login=nologin") != -1) {
						back_url = URLDecoder.decode(back_url, "UTF-8").substring(back_url.lastIndexOf("=")+1);
						resp.sendRedirect("/?login=nologin&back_url=" + URLEncoder.encode(back_url, "UTF-8"));
						ai.getController().renderNull();
					} else {
						if(retUrl.indexOf("login=nologin") == -1){
							resp.sendRedirect(retUrl + "?login=nologin&back_url=" + URLEncoder.encode(back_url, "UTF-8"));
							ai.getController().renderNull();
						} else{
							resp.sendRedirect(retUrl);
							ai.getController().renderNull();
						}
					}
				}
			} else {
				ai.invoke();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				resp.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
