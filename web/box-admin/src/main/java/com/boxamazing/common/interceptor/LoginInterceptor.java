package com.boxamazing.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.r.model.R;
import com.boxamazing.service.u.model.PUser;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

/**
 * 登录拦截器
 * by HECJ
 */
public class LoginInterceptor implements Interceptor{
	
	private static final Log log = LogFactory.getLog(LoginInterceptor.class);
	
	@Override
	public void intercept(ActionInvocation ai) {
		HttpServletRequest req = ai.getController().getRequest();
		HttpServletResponse resp = ai.getController().getResponse();
		boolean b=UserUtil.isLogin(req.getSession());
		if (!b) {
			String uri = req.getRequestURI();
			String queryString = req.getQueryString();
			log.info("用户未登录或登录超时，跳转到登录页面。");
			log.info("uri{},queryString{}:"+uri+","+queryString);

			//AJAX请求
			if(req.getHeader("x-requested-with") !=null && req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
				log.info("ajax访问超时，跳转到登录页面。");
				//给个状态码
				resp.setStatus(999);
				ai.getController().renderNull();
				return;
			}else{
				ai.getController().renderHtml("<script>top.location.href='/login'</script>");
			}
		}else {
			try {
				PUser u = UserUtil.getU(ai.getController().getSession());
				R r = R.findFirstRByRid(u.get("role_id"));
				ai.getController().setAttr("__r", r);
				ai.getController().setAttr("__u", u);
				ai.invoke();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
