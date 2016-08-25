package com.boxamazing.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.common.BaseController;
import com.boxamazing.common.StringUtil;
import com.boxamazing.common.interceptor.UserUtil;
import com.boxamazing.service.sms.model.SendSmsRecord;
import com.boxamazing.service.u.model.PUser;
import com.jfinal.plugin.activerecord.Page;

/**
 * 短息发送记录
 */
public class SendSmsRecordController extends BaseController {
 
	public static Log log = LogFactory.getLog(SendSmsRecordController.class);
	
	/**
	 * 
	 */
	public void index(){
		PUser puser = UserUtil.getU(getSession());
		log.info(puser.get("username")+"["+puser.toString()+"]:SendSmsRecordController--index(), 短信发送记录");
		try {
			Long page  = 1L;
			if(this.isParaExists(0)){
				page = getParaToLong(0);
			}
			
			String phone = getPara("phone");
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("page", page);
			params.put("size", 10);
			if(!StringUtil.isNullOrEmpty(phone)){
				params.put("phone", phone);
			}

			Page<SendSmsRecord> sendSmsRecordPage = SendSmsRecord.dao.findBySendSmsRecordParams(params);
			setAttr("sendSmsRecordPage", sendSmsRecordPage);
			setAttr("phone", phone);
			
			render("/page/message/sendSmsRecord/index.ftl");
		} catch (Exception e) {
			log.error(e);
			renderError(403);
		}
		
	}
	
}
