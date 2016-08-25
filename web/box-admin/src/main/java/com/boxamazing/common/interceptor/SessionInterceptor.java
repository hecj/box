package com.boxamazing.common.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.boxamazing.service.util.Constant;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
/**
 * Session拦截器
 * by HECJ
 */
public class SessionInterceptor implements Interceptor{
	
	public static Log log = LogFactory.getLog(SessionInterceptor.class);
	
	@Override
	public void intercept(ActionInvocation ai) {
		
		String req_url = "";
		try {
			
			ai.getController().setAttr("STATIC_URL", Constant.STATIC_URL);
			ai.getController().setAttr("WEBROOT_URL", Constant.WEBROOT_URL);
			
		} catch (Exception e) {
			log.error(" request url :" +req_url);
			e.printStackTrace();
		}
		
		ai.invoke();
	}

}
