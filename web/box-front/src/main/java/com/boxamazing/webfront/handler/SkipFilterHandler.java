package com.boxamazing.webfront.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.handler.Handler;
/**
 * 禁止直接访问.ftl,.jsp文件
 * by HECJ
 */
public class SkipFilterHandler extends Handler {
	
	private static final Log log = LogFactory.getLog(SkipFilterHandler.class);
	
	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		boolean kipError = target.endsWith(".ftl") || target.endsWith(".jsp");
		if(kipError){
			log.info("用户直接访问了禁止文件，跳转到404页面，target："+target);
			nextHandler.handle("/page_404", request, response, isHandled);
		}else{
			nextHandler.handle(target, request, response, isHandled);
		}
	}

}
