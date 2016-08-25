package com.boxamazing.webfront.controller.user;

import com.boxamazing.service.user.model.User;
import com.boxamazing.service.user.model.UserFeedBack;
import com.boxamazing.webfront.common.BaseController;
import com.boxamazing.webfront.interceptor.LoginInterceptor;
import com.boxamazing.webfront.util.ResultJson;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.aop.Before;

@Before({LoginInterceptor.class}) 
public class UserFeedBackController extends BaseController {
	
	//保存用户反馈信息
	 public void saveFeedBack() {
		 ResultJson resultJson = new ResultJson();
		 //判断是否登录
		 User user  = UserUtil.getUser(getSession());
		 String content = getPara("content");
		 String remark = getPara("remark");
		 UserFeedBack userFeedBack = new UserFeedBack();
		 boolean isSave = userFeedBack.set("user_id", user.getLong("id")).set("content", content).set("is_delete", 0).set("create_at", System.currentTimeMillis()).set("remark",remark).save();
		 if(isSave){
			 resultJson.setCode(200L);
		 }else{
			 resultJson.setCode(404L);
		 }
		 renderJson(resultJson);
     }
	 
	 public void index(){
		 render("/index.ftl");
	 }
}
