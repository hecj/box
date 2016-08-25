package com.boxamazing.service.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.sms.model.TaskMessageQueue;
import com.boxamazing.service.user.model.User;
import com.jfinal.plugin.activerecord.Db;

/**
 * 系统通知任务处理类
 */
public class TaskMessageQueueService {

	private static final Log log = LogFactory.getLog(TaskMessageQueueService.class);
	
	private static MessageService messageService = new MessageService();
	/**
	 * 处理系统通知任务
	 * taskId 任务Id
	 */
	public Map<String,String> dealSystemMessage(long taskId){
		
		log.info("taskId : " + taskId);
		// 校验
		Map<String, String> validResult = validSystemMessage(taskId);
		log.info("validResult : "+validResult);
		if (!"200".equals(validResult.get("code"))) {
			return validResult;
		}
		
		Map<String, String> result = new HashMap<String,String>();
		
		// 标记正在执行
		Db.update("update task_message_queue set count = count + 1 , status = 1 where id = ?",new Object[]{taskId});
		
		TaskMessageQueue task = TaskMessageQueue.dao.findById(taskId);
		String title = task.getStr("title");
		String content = task.getStr("content");
		String task_uuid = task.getStr("task_uuid");
		String message_type = task.getStr("message_type");
		log.info("message_type : " + message_type);
		try {
			// 1.根据手机号集合定位发送对象users
			String phones = task.getStr("phones");
			if(!StringUtil.isNullOrEmpty(phones)){
				String[] phoneList = phones.split(",");
				for(String phone : phoneList){
					User user = User.dao.findUser(phone);
					if(user != null){
						// send 
						sendTaskMessage(user.getLong("id"), title, content, message_type,task_uuid);
					}
				}
			}
			
			// 2.根据userId集合定位发送对象users
			String user_ids = task.getStr("user_ids");
			if(!StringUtil.isNullOrEmpty(user_ids)){
				String[] userIdList = user_ids.split(",");
				for(String user_id : userIdList){
					User user = User.dao.findById(user_id);
					if(user != null){
						sendTaskMessage(user.getLong("id"), title, content, message_type,task_uuid);
					}
				}
			}
			
			// 3.根据分组类型定位发送对象users 用户类型（1.全部用户，2.注册用户，3.认证用户，4.第三方游客）
			int user_type = task.getInt("user_type");
			List<Long> userIds = getUserIdsByUserType(user_type);
			for(long user_id : userIds){
				sendTaskMessage(user_id, title, content, message_type,task_uuid);
			}
			
			// 标记执行完成
			Db.update("update task_message_queue set status = 2 where id = ?",new Object[]{taskId});
			
			result.put("code", "200");
			result.put("message", "任务执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "-100000");
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 校验系统通知任务是否符合执行条件
	 *1.校验状态
	 *2.校验有效时间
	 */
	private Map<String,String> validSystemMessage(long taskId){
		
		Map<String, String> result = new HashMap<String,String>();
		
		TaskMessageQueue task = TaskMessageQueue.dao.findById(taskId);
		// 1.校验状态
		int status = task.getInt("status");
		log.info("status : "+status);
		if(status == 1){
			result.put("code", "-1");
			result.put("message", "任务正在执行中，不可重复执行");
			return result;
		}
		if(status == 2){
			result.put("code", "-2");
			result.put("message", "任务已执行完毕，本次执行非法");
			return result;
		}

		// 2.校验有效时间
		long send_at = task.getLong("send_at");
		log.info("send_at :  " + send_at);
		long invalid_at = task.getLong("invalid_at");
		log.info("invalid_at :  " + invalid_at);
		long curr_time = System.currentTimeMillis();
		log.info(" curr_time : " + curr_time);
		if (send_at > curr_time) {
			result.put("code", "-3");
			result.put("message", "任务执行时间未到，不可执行。");
			return result;
		}
		if (invalid_at < curr_time) {
			result.put("code", "-3");
			result.put("message", "任务执行时间已过期，不可执行。");
			return result;
		}
		
		result.put("code", "200");
		result.put("message", "校验通过");
		return result;
	}
	
	/**
	 * 发送消息
	 */
	private void sendTaskMessage(long user_id, String title, String content, String message_type,String task_uuid){
		
		Map<String,String> contentMap = new HashMap<String,String>();
		if(message_type.contains("system")){
			contentMap.put("system", content); 
		} 
		if(message_type.contains("email")){
			contentMap.put("email", content);
		} 
		if(message_type.contains("phone")){
			contentMap.put("phone", content);
		} 
		messageService.sendNoticeMessage(user_id, title, contentMap);
	}
	
	/**
	 * 根据用户类型获取用户集合
	 */
	private List<Long> getUserIdsByUserType(int user_type){
		log.info("user_type {} :" + user_type);
		List<Long> userIds = new ArrayList<Long>();
		try {
			String sqlQuery = "";
			if(user_type == 1){
				// 全部用户
				sqlQuery = "select id from user where status = 1";
			} else if (user_type == 2){
				// 注册用户
				sqlQuery = "select id from user where status = 1 and phone is not null";
			} else if (user_type == 3){
				// 实名用户
				sqlQuery = "select id from user where status = 1 and certify = 2";
			} else if (user_type == 4){
				// 第三方游客
				sqlQuery = "select distinct(our.user_id) from open_user_relation our left join user u on (u.id = our.user_id)" +
						" where u.status = 1 and u.phone is null";
			}
			log.info("sqlQuery:"+sqlQuery);
			userIds = Db.query(sqlQuery);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return userIds;
	}
	
}
