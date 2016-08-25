package com.boxamazing.admin.task;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boxamazing.service.sms.TaskMessageQueueService;
import com.boxamazing.service.sms.model.TaskMessageQueue;
/**
 * 系统消息任务处理
 */
public class SystemMessageTask implements Job{
	
	public static Log log = LogFactory.getLog(SystemMessageTask.class);
	
	/**
	 * 此处最好限制循环处理次数，避免出现死循环
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		log.info("=================TASK 正在处理系统发送通知开始");
		/*
		 *  本次任务最大处理次数
		 */
		int batch_count = 5;
		/*
		 * 每次处理10条数据
		 */
		int batch_limit = 10;
		
		TaskMessageQueueService taskMessageQueueService = new TaskMessageQueueService();
		
		try {
			for(int i = 0 ; i< batch_count ; i ++){
				log.info(" 本次任务正在执行第（"+(i+1)+"）批次处理");
				List<Long> taskIds = TaskMessageQueue.dao.findTaskMessageQueueByStatus(0, batch_limit);
				log.info(" 本批次任务Ids : "+taskIds);
				if (taskIds != null && taskIds.size() > 0) {
					for(long taskId : taskIds){
						log.info("当前任务Id:"+taskId);
						try {
							Map<String,String> result = taskMessageQueueService.dealSystemMessage(taskId);
							log.info("result : " + result);
							// 标记执行完毕
						} catch (Exception e) {
							log.error(e.getMessage());
							e.printStackTrace();
						} finally{
							
						}
					}
				} else{
					log.info(" 本次任务，系统发送通知处理完毕。");
					break;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		log.info("=====================TASK 正在处理系统发送通知结束");
	}
}