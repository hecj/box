package com.demo.common.interceptor;

import java.sql.Savepoint;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.demo.admin.r.RValidator;
import com.demo.common.BaseController;
import com.demo.service.m.model.M;
import com.demo.service.ol.model.Ol;
import com.demo.service.q.model.Q;
import com.demo.service.r.model.R;
import com.demo.service.u.model.U;
import com.jfinal.aop.Before;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.JfinalxController;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;

public class LogInterceptor implements Interceptor{

	@Override
	public void intercept(ActionInvocation ai) {
		JfinalxController controller = ai.getController();
		HttpServletRequest req = controller.getRequest();
		boolean b=UserUtil.isLogin(req.getSession());
		Long tid =null;
		try {
			if (!b) {
				//未登录
//				try {
//					HttpServletResponse response = ai.getController().getResponse();
//					response.sendRedirect("/login");
					controller.forwardAction("/login");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
					
				String keys = ai.getActionKey();
				Ol ol=null;
				ol = getBaseOl(controller, keys); 
				if(keys.endsWith("/dologin")){//登陆
					String n=controller.getPara("n");
					if (StrKit.isBlank(n)) {
						n=controller.getPara("u");
					}
					ol.put("on", n); 
					ol.save();
				}
				tid = ol.getLong("id");
				
				
			}else {
				U u = UserUtil.getU(controller.getSession());
				String keys = ai.getActionKey();
				
	
				
				Ol ol=null;
				ol = getBaseOl(controller, keys); 
				
				if(keys.endsWith("/save")){
					Model rs = controller.getAttr("_rs");
					Object n = null ;
					if(rs!=null){
						ol.put("oids", rs.get("id"));
						n = rs.get("n");
						if (n==null) {
							n=rs.get("u");
						}
						
						ol.put("on", n); 
					}
					ol.save();
				}
				if(keys.endsWith("/update")){
					String id = controller.getPara("id");
					if (!StrKit.isBlank(id)) {
						ol.put("oids",id);
						String n=controller.getPara("n");
						if (StrKit.isBlank(n)) {
							n=controller.getPara("u");
						}
						ol.put("on", n); 
						ol.save();
					}
				}
				if(keys.endsWith("/delete")){
					Integer id = controller.getParaToInt();
					if (id!=null&&id>0) {
						ol.put("oids",id);
						String n=controller.getPara("n");
						if (StrKit.isBlank(n)) {
							n=controller.getPara("u");
						}
						ol.put("on", n); 
						ol.save();
					}
				}
		 
				if(keys.equals("/deleteByIds")){
					String[] ids = controller.getParaValues("id");
					String str="";
					if (ids.length>0) {
						for (String id : ids) {
							str+=id+",";
						}
						str=str.substring(0,str.length()-1);
					}
					ol.put("oids",str);
					ol.save();
				}
				
				
				//下面为特殊记录
				
				if(keys.endsWith("/logout")){//退出
					u=UserUtil.getU(controller.getSession());
					Long id = u.getLong("id");
					if (id!=null&&id>0) {
						ol.put("oids",id);
						String n=u.getStr("u");
						ol.put("on", n); 
						ol.save();
					}
				}
				if(keys.endsWith("/query/ex")){//普通查询
					String id = controller.getPara("id");
					if (!StrKit.isBlank(id)) {
						ol.put("oids",id);
						String n=controller.getPara("n");
						if (StrKit.isBlank(n)) {
							n=controller.getPara("u");
						}
						ol.put("on", n); 
						ol.save();
					}
				}
				
				if(keys.endsWith("/query/staticex")){//统计查询
					String id = controller.getPara("id");
					if (!StrKit.isBlank(id)) {
						ol.put("oids",id);
						String n=controller.getPara("n");
						if (StrKit.isBlank(n)) {
							n=controller.getPara("u");
						}
						ol.put("on", n); 
						ol.save();
					}
				}
				tid = ol.getLong("id");
					
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ai.invoke();
			
			if(tid!=null&&tid>0){
				Ol t = Ol.dao.findById(tid);
				
				U u = UserUtil.getU(controller.getSession());
				if (u!=null) {
					t.set("uid", u.get("id"));
					t.set("un", u.get("u"));
				}
				t.put("endtm", new Date());
				t.put("msg", controller.getAttr("msg"));
				
				Model rs = controller.getAttr("_rs");
				Object n = null ;
				if(rs!=null){
					t.put("oids", rs.get("id"));
					n = rs.get("n");
					if (n==null) {
						n=rs.get("u");
					}
					
					t.put("on", n); 
				}
				
				
				t.update();
			}
			
		}
		
	}

	 
	
 




//	id	 
//	type	 操作后缀
//	m	 	模块前缀
//	mn	 模块名称
//	oid	 对象id
//	on	 对象名称
//	tm	 执行时间
	private Ol getBaseOl(JfinalxController controller, String keys) {
		Ol ol;
		ol=new Ol();
		String[] sz = keys.split("[\\/]");
		if (sz.length>2) {
			String value = sz[1];
			ol.put("m", value);
			ol.put("type", sz[sz.length-1]);
		}else if(sz.length>1){
			ol.put("m", "");
			ol.put("type", sz[sz.length-1]);
		}
		if (controller instanceof BaseController) {
			ol.put("mn", ((BaseController) controller).getMn());
		}
		ol.put("tm", new Date());
		
		U u = UserUtil.getU(controller.getSession());
		if (u!=null) {
			ol.set("uid", u.get("id"));
			ol.set("un", u.get("u"));
		}
		return ol;
	}

}
