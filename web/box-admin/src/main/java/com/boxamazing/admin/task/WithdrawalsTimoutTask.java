package com.boxamazing.admin.task;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boxamazing.service.error.DealException;
import com.boxamazing.service.pay.model.Withdrawals;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;

public class WithdrawalsTimoutTask implements Job {
public static Log log = LogFactory.getLog(SystemMessageTask.class);
	
	/**
	 * 此处最好限制循环处理次数，避免出现死循环
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		log.info("=================TASK 正在提现超时任务处理开始");
		/*
		 *  本次任务最大处理次数
		 */
		int batch_count = 5;
		/*
		 * 每次处理10条数据
		 */
		int batch_limit = 10;
		
		try {
			for(int i = 0 ; i< batch_count ; i ++){
				log.info(" 本次任务正在执行第（"+(i+1)+"）批次处理");
				List<Long> withdrawalsIds = Withdrawals.dao.findWithdrawalsByInvalidAt(30, batch_limit);
				log.info(" 本批次任务Ids : "+withdrawalsIds);
				if (withdrawalsIds != null && withdrawalsIds.size() > 0) {
					for(final long withdrawalsId : withdrawalsIds){
						log.info("当前任务Id:"+withdrawalsId);
						try {
							
							final Withdrawals w = Withdrawals.dao.findById(withdrawalsId);
							final String batch_no = w.getStr("batch_no");
							boolean exeBlo = Db.tx(new IAtom() {
								@Override
								public boolean run() throws SQLException {
									
									int rows = Db.update("update withdrawals set status = 1 where status = 3 and id = ?", new Object[]{w.getLong("id")});
									log.info("rows : " + rows);
									if(rows == 0){
										log.error("【回滚】");
										return false;
									}
									rows = Db.update("update withdrawals_detail set status = 5 where status = 3 and withdrawals_id = ? and batch_no = ?",new Object[]{w.getLong("id"),batch_no});
									log.info("rows : " + rows);
									if(rows == 0){
										log.error("【回滚】");
										return false;
									}
									
									DealException dealException = new DealException();
									dealException.put("type", 4);
									dealException.put("event_id", withdrawalsId);
									dealException.put("desc", "定时任务处理提现超时");
									dealException.put("create_at", System.currentTimeMillis());
									dealException.save();
									
									return true;
								}
							});
							
							log.info("【执行结果】exeBlo:"+exeBlo);
							
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
		log.info("=====================TASK 正在提现超时任务处理结束");
	}
}
