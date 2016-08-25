package com.boxamazing.webfront.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.user.model.User;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.JfinalxController;

/**
 * 账号没有绑定手机时拦截
 * 
 * @author XuXD
 * 
 */
public class BindInterceptor implements Interceptor {
	
	public static Log log = LogFactory.getLog(BindInterceptor.class);

	@Override
	public void intercept(ActionInvocation ai) {
		HttpServletRequest req = ai.getController().getRequest();
		HttpServletResponse resp = ai.getController().getResponse();
		JfinalxController controller = ai.getController();
		User user = UserUtil.getUser(controller.getSession());

		try {
			// 判断是否绑定手机号
			String queryString = req.getQueryString();
			String uri = req.getRequestURI();
			String back_url = uri;
			if(queryString != null){
				back_url = uri+"?"+queryString;
			}
			boolean b = user.isBind();
			if (!b) {
				resp.sendRedirect("/personal/bind?back_url="+URLEncoder.encode(back_url,"UTF-8"));
			} else {
				ai.invoke();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("绑定拦截器异常", e);
			controller.redirect("/");
		}
	}

}
