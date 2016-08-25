package com.boxamazing.admin.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.admin.project.ProjectController;
import com.boxamazing.admin.user.UserValidator;
import com.boxamazing.common.BaseController;
import com.boxamazing.common.StringUtil;
import com.boxamazing.common.interceptor.UserUtil;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.r.model.R;
import com.boxamazing.service.user.model.User;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class UserController extends BaseController{
	
	private static final Log log = LogFactory.getLog(UserController.class);
	
	public void index() {
		
		try {
    		int pageNumber = getParaToInt(0, 1);
    		String uPhone = getPara("uPhone");
    		String email = getPara("email");
    		String certify = getPara("certify");
    		
    		log.info("pageNumber{}uPhone{}statusObj{}email{},:"+pageNumber+","+uPhone+","+certify+","+email);
    		
    		Map<String,Object> params = new HashMap<String,Object>();
    		params.put("page", pageNumber);
    		params.put("size", 10);
    		params.put("uPhone", uPhone);
    		params.put("email", email);
    		params.put("certify", certify);
    		
        	Page<Record> recordList = User.dao.findRecordByCondition(params);
            
        	setAttr("recordList", recordList);
            setAttr("uPhone", uPhone);
            setAttr("certify", certify);
            setAttr("email", email);
            
            renderFreeMarker("index.ftl");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			forwardAction("/main");
		}
	}
	
	/**
	 * 用户管理-查看
	 */
	public void detail(){
		long user_id = getParaToLong(0);
		log.info("查看用户信息："+user_id);
		User user = User.dao.findById(user_id);
		setAttr("user", user);
		render("/page/user/detail.ftl");
	}

	@Override
	public String getMn() {
		return "用户管理";
	}
	
	
	
}
