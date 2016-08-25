package com.boxamazing.service.sms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.util.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 系统通知任务
 */
public class TaskMessageQueue extends Model<TaskMessageQueue> {
	private static final Log log = LogFactory.getLog(TaskMessageQueue.class);
	private static final long serialVersionUID = 1L;
	
	public static final TaskMessageQueue dao = new TaskMessageQueue();
	
	/**
	 * 查询未执行的任务且未失效的任务
	 */
	public List<Long> findTaskMessageQueueByStatus(int status,int batch_limit){
		log.info("status{},batch_limit{}:" + status+","+batch_limit);
		String querySQL = "select id from task_message_queue where status = ? and send_at < UNIX_TIMESTAMP(now())*1000 " +
				"and UNIX_TIMESTAMP(now())*1000 < invalid_at order by id asc limit ? ";
		try {
			List<Long> list = Db.query(querySQL, new Object[] { status,
					batch_limit });
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 短信记录
	 */
	public Page<TaskMessageQueue> findByTaskMessageQueueParams(Map<String, Object> params) {

		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());

		String selectSQL = "select tmq.* ";

		StringBuffer condition = new StringBuffer(" from task_message_queue tmq where 1=1");

		List<Object> sqlParams = new ArrayList<Object>();

		condition.append(" order by tmq.id desc ");

		log.info(" 查询系统发送通知列表 condition{} : " + condition.toString());
		try {
			Page<TaskMessageQueue> list = dao.paginate(pageNumber, pageSize, selectSQL, condition.toString(), SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error("status{},pageObj{},sizeObj{}:" + pageObj + "," + sizeObj);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
