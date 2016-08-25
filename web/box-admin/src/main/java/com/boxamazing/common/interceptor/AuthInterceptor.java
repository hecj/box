package com.boxamazing.common.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.boxamazing.service.m.model.M;
import com.boxamazing.service.r.model.R;
import com.boxamazing.service.u.model.PUser;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class AuthInterceptor implements Interceptor{

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
				PUser u = UserUtil.getU(ai.getController().getSession());
				R r = R.findFirstRByRid(u.get("role_id"));
				ai.getController().setAttr("__r", r);
				ai.getController().setAttr("__u", u);
				
				List<M> ms = M.dao.find("select * from m");
				ai.getController().setAttr("__ms", ms);
				String actionKey = ai.getActionKey();
				
				M m = InMenu(actionKey, ms);
				if(m!=null){//拦截
					String ums = r.getStr("ms");
					String[] us = ums.split("[\\,]");
					boolean fl = true;
					for (String string : us) {
						if (string.equals(String.valueOf(m.getLong("id")))) {
							ai.invoke();fl=false;
						}
					}
					if (fl) {
						ai.getController().getResponse().sendRedirect("/noauth");
					}
				}else {
					ai.invoke();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private M InMenu(String actionKey, List<M> ms) {
		
		for (M m : ms) {
			String aurl = m.getStr("aurl");
			if(actionKey.equals(aurl)){
				return m;
			}
		}
		return null;
	}

}
