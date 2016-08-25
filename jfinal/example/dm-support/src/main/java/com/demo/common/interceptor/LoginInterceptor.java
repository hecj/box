package com.demo.common.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.demo.service.r.model.R;
import com.demo.service.u.model.U;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class LoginInterceptor implements Interceptor{

	@Override
	public void intercept(ActionInvocation ai) {
		HttpServletRequest req = ai.getController().getRequest();
		boolean b=UserUtil.isLogin(req.getSession());
		if (!b) {
			//未登录
//			try {
//				HttpServletResponse response = ai.getController().getResponse();
//				response.sendRedirect("/login");
				ai.getController().forwardAction("/login");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			return;
		}else {
			try {
				U u = UserUtil.getU(ai.getController().getSession());
				R r = R.dao.findFirst("select * from r where id=?",u.get("rid"));
				ai.getController().setAttr("__r", r);
				ai.getController().setAttr("__u", u);
				ai.invoke();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
