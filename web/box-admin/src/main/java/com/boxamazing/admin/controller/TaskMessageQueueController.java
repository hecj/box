package com.boxamazing.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.common.BaseController;
import com.boxamazing.common.StringUtil;
import com.boxamazing.common.interceptor.UserUtil;
import com.boxamazing.service.sms.model.SendEmailRecord;
import com.boxamazing.service.sms.model.TaskMessageQueue;
import com.boxamazing.service.u.model.PUser;
import com.boxamazing.service.util.OrdersUtils;
import com.boxamazing.util.ResultJson;
import com.jfinal.plugin.activerecord.Page;

/**
 * 系统发送通知
 */
public class TaskMessageQueueController extends BaseController {
 
	public static Log log = LogFactory.getLog(TaskMessageQueueController.class);
	
	
	/**
	 * 添加通知任务页面
	 */
	public void toAddTask(){
		
		render("/page/message/taskMessageQueue/addTask.ftl");
	}
	
	/**
	 * 添加通知任务提交
	 */
	public void doAddTask(){
		
		String phones = getPara("phones");
		String userIds = getPara("userIds");
		String user_type = getPara("user_type");
		String title = getPara("title");
		String content = getPara("content");
		String message_type = getPara("message_type");
		
		log.info("添加系统发送通知phones{},userIds{},user_type{},title{},content{},message_type{}:"+
				phones+","+userIds+","+user_type+","+title+","+content+","+message_type);
		
		try {

			if(StringUtil.isNullOrEmpty(phones) && StringUtil.isNullOrEmpty(userIds) && StringUtil.isNullOrEmpty(user_type)){
				renderJson(new ResultJson(-1l,"请选择一种发送通知对象"));
				return ;
			}
			
			if(StringUtil.isNullOrEmpty(content)){
				renderJson(new ResultJson(-2l,"请输入通知内容"));
				return ;
			}
			if(StringUtil.isNullOrEmpty(message_type)){
				renderJson(new ResultJson(-3l,"请选择通知类型"));
				return ;
			}
			
			if(message_type.endsWith(",")){
				message_type = message_type.substring(0, message_type.length()-1);
			}
			
			TaskMessageQueue taskMessageQueue = new TaskMessageQueue();
			taskMessageQueue.put("message_type", message_type);
			taskMessageQueue.put("user_ids", userIds);
			taskMessageQueue.put("phones", phones);
			taskMessageQueue.put("user_type", user_type);
			taskMessageQueue.put("status", 0);
			taskMessageQueue.put("count", 0);
			taskMessageQueue.put("title", title);
			taskMessageQueue.put("content", content);
			// 发送时间默认为当前时间
			taskMessageQueue.put("send_at", System.currentTimeMillis());
			// 任务失效时间为1天
			long invalid_at = System.currentTimeMillis()+ 1*24*60*60*1000;
			taskMessageQueue.put("invalid_at", invalid_at);
			taskMessageQueue.put("task_uuid", OrdersUtils.taskOrderNum());
			taskMessageQueue.put("create_at", System.currentTimeMillis());
			
			taskMessageQueue.save();
			
			renderJson(new ResultJson(200l,"success"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new ResultJson(-100000l,"处理异常"));
		}
	}
	
	public void taskList(){
		PUser puser = UserUtil.getU(getSession());
		log.info(puser.get("username")+"["+puser.toString()+"]:TaskMessageQueueController--taskList(), 任务发送记录");
		try {
			Long page  = 1L;
			if(this.isParaExists(0)){
				page = getParaToLong(0);
			}
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("page", page);
			params.put("size", 10);

			Page<TaskMessageQueue> taskMessageQueuePage = TaskMessageQueue.dao.findByTaskMessageQueueParams(params);
			setAttr("taskMessageQueuePage", taskMessageQueuePage);
			
			render("/page/message/taskMessageQueue/taskList.ftl");
		} catch (Exception e) {
			log.error(e);
			renderError(403);
		}
		
	}
	
}
