package com.boxamazing.webfront.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.boxamazing.service.payPwd.model.UserPayPwd;
import com.boxamazing.service.user.model.User;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.JfinalxController;

public class PayPwdInterceptor implements Interceptor{

	@Override
	public void intercept(ActionInvocation arg0) {
		JfinalxController controller = arg0.getController();
		HttpServletRequest req = controller.getRequest();
		//User user = (User) req.getSession();
		User user = UserUtil.getUser(arg0.getController().getSession());
		boolean b = UserPayPwd.dao.isSet(user);
		if(!b){
			//未设置支付密码
			arg0.getController().redirect("/setPayPwd");			
		}
	}

}
