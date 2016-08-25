package com.boxamazing.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.common.BaseController;
import com.boxamazing.common.StringUtil;
import com.boxamazing.common.interceptor.UserUtil;
import com.boxamazing.service.sms.model.SendEmailRecord;
import com.boxamazing.service.u.model.PUser;
import com.jfinal.plugin.activerecord.Page;

/**
 * 邮件发送记录
 */
public class SendEmailRecordController extends BaseController {
 
	public static Log log = LogFactory.getLog(SendEmailRecordController.class);
	
	/**
	 * 
	 */
	public void index(){
		PUser puser = UserUtil.getU(getSession());
		log.info(puser.get("username")+"["+puser.toString()+"]:SendEmailRecordController--index(), 短信邮件记录");
		try {
			Long page  = 1L;
			if(this.isParaExists(0)){
				page = getParaToLong(0);
			}
			
			String reciver_email = getPara("reciver_email");
			Long reciver = getParaToLong("reciver");
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("page", page);
			params.put("size", 10);
			if(!StringUtil.isNullOrEmpty(reciver_email)){
				params.put("reciver_email", reciver_email);
			}
			if(reciver != null){
				params.put("reciver", reciver);
			}

			Page<SendEmailRecord> sendEmailRecordPage = SendEmailRecord.dao.findBySendEmailRecordParams(params);
			setAttr("sendEmailRecordPage", sendEmailRecordPage);
			setAttr("reciver_email", reciver_email);
			setAttr("reciver", reciver);
			
			render("/page/message/sendEmailRecord/index.ftl");
		} catch (Exception e) {
			log.error(e);
			renderError(403);
		}
		
	}
	
}
