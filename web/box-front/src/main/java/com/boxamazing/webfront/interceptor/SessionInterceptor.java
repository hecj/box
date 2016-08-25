package com.boxamazing.webfront.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.fans.model.Fans;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.Constant;
import com.boxamazing.webfront.util.UserUtil;
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
			
			HttpSession session = (HttpSession) ai.getController().getSession();
			HttpServletRequest req = ai.getController().getRequest();
			
			String queryString = req.getQueryString();
			String uri = req.getRequestURI();
			req_url = uri;
			if(queryString != null&&!req_url.equals("isSession")){
				req_url = uri+"?"+queryString;
			}else if(req_url.equals("/isSession")&&queryString!=null){
				req_url = "/"+queryString;
			}else{
				req_url = "/";
			}
			
			log.info("rquest url : "+req_url);
			
			ai.getController().setAttr("STATIC_URL", Constant.STATIC_URL);
			ai.getController().setAttr("WEBROOT_URL", Constant.WEBROOT_URL);
			ai.getController().setAttr("qq_lable", Constant.qq_lable);
			ai.getController().setAttr("weibo_lable", Constant.weibo_lable);
			ai.getController().setAttr("wechat_AppID", Constant.wechat_AppID);
			ai.getController().setAttr("wechat_redirect_URI", Constant.wechat_redirect_URI);
			
			User user = UserUtil.getUser(session);
			if(user != null){
				User u = User.dao.findById(user.getLong("id"));
				if(!StringUtil.isNullOrEmpty(u.getStr("phone"))){
					
					if(!StringUtil.isNullOrEmpty(user.getStr("password"))){
						if(!user.getStr("password").equals(u.getStr("password"))){
							log.info("u的密码为："+u.getStr("password")+"user的密码是："+user.getStr("password"));
							ai.getController().forwardAction("/logOut");
							return;
						}
					}
				}
				UserUtil.setUser(user, session);
				ai.getController().setAttr("user", user);
			}
			
		} catch (Exception e) {
			log.error(" request url :" +req_url);
			log.error("Session拦截器出现异常，请查明原因：");
			e.printStackTrace();
		}
		
		ai.invoke();
	}

}
