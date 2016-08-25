package cn.peon.jfinal.core;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.JfinalxController;

public class XssInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {
		JfinalxController c = ai.getController();
		if (c!=null) {
			HttpServletRequest request=c.getRequest();
			c.setRequest(new XssHttpServletRequestWrapper(request));
		}
		ai.invoke();
	}
}
