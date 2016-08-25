package com.boxamazing.service.sms;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.system_message.model.SystemMessage;
import com.boxamazing.service.user.model.User;

/**
 * 发送通知消息业务
 */
public class MessageService {

	private static final Log log = LogFactory.getLog(MessageService.class);
	
	/**
	 * 发送通知消息
	 * system(系统消息)
	 * email(邮箱)
	 * phone(手机短信)
	 */
	public void sendNoticeMessage(long user_id, String title, Map<String,String> contentMap){
		
		try {
			
			User user = User.dao.findById(user_id);
			
			for (Map.Entry<String, String> entry : contentMap.entrySet()) {
				if ("system".equals(entry.getKey())) {
					String content = entry.getValue();
					if (!StringUtil.isNullOrEmpty(content)) {
						// 发送系统消息
						sendSystemMessage(user_id, content);
					}
				}
				if ("email".equals(entry.getKey())) {
					String content = entry.getValue();
					if (!StringUtil.isNullOrEmpty(content)) {
						//TODO
						log.info("发送邮件");
					}
				}
				if ("phone".equals(entry.getKey())) {
					String content = entry.getValue();
					if (!StringUtil.isNullOrEmpty(content)) {
						String phone = user.getStr("phone");
						// 发送短信
						if(!StringUtil.isNullOrEmpty(phone)){
							SmsService.sendSms(content, phone);
						}
					}
				}
			}
			
		} catch (Exception e) {
			log.error( "发送通知消息异常：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 发送系统消息
	 * @author XuXD
	 * @date 2015-11-13
	 * @param userId
	 * @param content
	 * @return
	 */
	public boolean sendSystemMessage(long userId, String content) {
		log.info("[userId:" + userId + ",content:" + content + "]");
		
		try {
			new SystemMessage().set("user_id", userId).
			set("message", content).
			set("send_at", System.currentTimeMillis()).
			set("create_at", System.currentTimeMillis()).save();
		} catch (Exception e) {
			log.error("发送系统消息异常", e);
		}
		return false;
	}
	
}
