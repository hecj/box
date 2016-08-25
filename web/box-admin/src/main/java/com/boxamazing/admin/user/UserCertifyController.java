package com.boxamazing.admin.user;

import java.util.HashMap;
import java.util.Map;

import com.boxamazing.common.StringUtil;
import com.boxamazing.service.user.model.User;
import com.boxamazing.util.ResultJson;
import com.jfinal.aop.Before;
import com.jfinal.core.JfinalxController;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;

/**
 * 用户实名认证controller
 * Created by jhl on 15/8/25.
 */
public class UserCertifyController extends JfinalxController {

    /**
     * 审核列表
     * by HECJ
     */
    public void index() {
    	
    	int pageNumber = getParaToInt(0, 1);
		
    	String uPhone = getPara("uPhone");
    	
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", pageNumber);
		params.put("size", 10);
		params.put("certify", 1);
		if(!StringUtil.isNullOrEmpty(uPhone)){
			params.put("uPhone", uPhone);
		}
    	
        Page<User> userList = User.dao.findByCertify(params);
        setAttr("page", userList);
        setAttr("uPhone", uPhone);
        renderFreeMarker("index.ftl");
    }
    
    /**
     * 去实名审核页面
     * by HECJ
     */
    public void toCertifyApply(){
    	
    	long user_id = getParaToLong(0);
    	User user = User.dao.findById(user_id);
    	setAttr("user", user);
    	render("/page/usercertify/certify-apply.ftl");
    }

    /**
     * 审核动作
     */
    @Before(POST.class)
    public void check() {
        long user_id = getParaToLong(0);
        String audit_type = getPara("audit_type");
        
        User user = User.dao.findById(user_id); 
        if(user.getInt("certify") == 2){
        	renderJson(new ResultJson(-1l, "已认证通过，无需重复认证"));
        	return;
        }
        
        if (audit_type.equals("pass")) {
            //审核通过
            user.set("certify", 2).update();
            //发送消息
            renderJson(new ResultJson(200l, "认证通过"));
        	return;
        } else {
        	// 审核不通过
        	// TODO
            user.set("certify", 3).update();
        	renderJson(new ResultJson(-2l, "认证不通过"));
        	return;
        }
    }

}
