package com.boxamazing.webfront.task;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.pay.OrderService;
/**
 * 过期订单处理
 */
public class OrderInvalidProceTask implements Job{
	
	public static Log log = LogFactory.getLog(OrderInvalidProceTask.class);
	
	/**
	 * 此处最好限制循环处理次数，避免出现死循环
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		OrderService orderService = new OrderService();
		
		log.info("TASK 正在处理过期的订单开始");
		/*
		 *  本次任务最大处理次数
		 */
		int batch_count = 50;
		/*
		 * 每次处理100条数据
		 */
		int batch_limit = 100;
		Set<Long> errorOrderIds = new HashSet<Long>();
		try {
			for(int i = 0 ; i< batch_count ; i ++){
				log.info(" 本次任务正在执行第（"+(i+1)+"）批次处理");
				List<Long> orderIds = Orders.dao.findOrderByInvalidAt(batch_limit);
				log.info(" 本批次过期订单Ids : "+orderIds);
				if (orderIds != null && orderIds.size() > 0) {
					for(long order_id : orderIds){
						if(!errorOrderIds.contains(order_id)){
							Map<String,String> result = orderService.cancelOrder(order_id);
							if(!"200".equals(result.get("code"))){
								errorOrderIds.add(order_id);
							}
						} else{
							log.error(" 此失效订单处理异常，本次任务不再做处理，orderId:"+order_id);
						}
					}
				} else{
					log.info(" 本次任务，过期订单处理完毕。");
					break;
				}
			}
			if(errorOrderIds.size() > 0){
				log.error("本次任务处理失败订单errorOrderIds ："+errorOrderIds);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		log.info("TASK 正在处理过期的订单结束");
	}
}