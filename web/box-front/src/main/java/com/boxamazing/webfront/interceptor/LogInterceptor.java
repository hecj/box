package com.boxamazing.webfront.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
/**
 * 日志拦截器
 * by HECJ
 */
public class LogInterceptor implements Interceptor{
	
	public static Log log = LogFactory.getLog(LogInterceptor.class);
	
	public void intercept(ActionInvocation ai) {
		HttpServletRequest request = ai.getController().getRequest();
		
		try {
			String retUrl = request.getHeader("Referer");
			log.info("上一跳地址:" + retUrl);
			String requestUrl = request.getRequestURI();
			log.info("访问URL :" + requestUrl);
			String queryString = request.getQueryString();
			log.info("访问queryString :" + queryString);
			log.info("访问 IP:" + getRemortIP(request));
			log.info("是否AJAX请求:"+isAjaxRequest(request));
			String userAgent = request.getHeader("User-Agent");
			log.info("访问 userAgent:" + userAgent);
		} catch (Exception e) {
			log.error(" 【ERROR】获取访问日志出现异常。");
			e.printStackTrace();
		}
		ai.invoke();
	}
	
	
	private String getRemortIP(HttpServletRequest request) {
		// 取代理ip地址
		String ip = request.getHeader("x-forwarded-for");
		log.info("获取的x-forwarded-for：" + ip);
		log.info("获取的x-real-ip：" + request.getHeader("x-real-ip"));
		log.info("获取的Proxy-Client-IP：" + request.getHeader("Proxy-Client-IP"));
		log.info("获取的request.getRemoteAddr()：" + request.getRemoteAddr());
		// 取nginx代理设置的ip地址
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-real-ip");
			log.info("取代理ip地址:" + ip);
		}
		// 从网上取的
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			log.info("取代理ip地址:" + ip);
		}
		// 取JAVA获得的ip地址
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			log.info("取代理ip地址:" + ip);
		}
		if (ip==null) {
			log.info("取代理ip地址失败" );
			return null;
		}
		// 去除unkonwn
		if ("unknown".startsWith(ip)) {
			ip = ip.substring(ip.indexOf("unknown") + "unknown".length());
			log.info("取代理ip地址:" + ip);
		}
		
		// 去除多多余的信息
		ip = ip.trim();
		log.info("取代理ip地址:" + ip);
		if (ip.startsWith(",")) {
			ip = ip.substring(1);
		}
		if (ip.indexOf(",") > 0) {
			ip = ip.substring(0, ip.indexOf(","));
		}
		return ip;
	}
	
	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		log.info("header :"+header);
		if (header != null && "XMLHttpRequest".equals(header)) {
			return true;
		}
		return false;
	}
}
