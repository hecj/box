package com.boxamazing.service.system_message.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.comment.model.Comment;
import com.boxamazing.service.private_message.model.PrivateMessage;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 系统消息
 * 
 * @author XuXD
 * @version 1.0
 * @date 2015-11-14
 */
public class SystemMessage extends Model<SystemMessage> {
	public static final SystemMessage dao = new SystemMessage();
	private static final Log log = LogFactory.getLog(SystemMessage.class);

	/**
	 * 获取未读系统消息总数
	 * 
	 * @author XuXD
	 * @date 2015-11-13
	 * @return
	 */
	public long getCount(long userId) {
		String sql = "SELECT count(1) FROM system_message WHERE user_id = ? AND is_read = 0 AND is_delete = 0;";
		try {
			return dao.findFirst(sql,userId).getLong("count(1)");
		} catch (Exception e) {
			log.error("获取未读系统消息总数异常", e);
		}
		return 0;
	}

	/**
	 * 获取系统消息(分页显示)
	 * 
	 * @author XuXD
	 * @date 2015-11-13
	 * @param userId
	 * @param model
	 *            0:全部,1:已读
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<SystemMessage> findMessageByPage(long userId, int model, int pageNum, int pageSize) {
		log.info("[userId:" + userId + ",model:" + model + " pageNum:" + pageNum + ",pageSize:" + pageSize + "]");
		String sql1 = "select * ";
		String sql2 = "";
		if (model == 0) {
			sql2 = "from system_message where user_id = ? and is_delete = 0 order by send_at desc";
		} else if (model == 1) {
			sql2 = "from system_message where user_id = ? and is_read = 0 and is_delete = 0 order by send_at desc";
		} else {
			log.info("model invalid :" + model);
			sql2 = "from system_message where 1=2";
		}
		try {
			return dao.paginate(pageNum, pageSize, sql1, sql2, userId);
		} catch (Exception e) {
			log.error("获取系统消息(分页显示)异常", e);
		}
		return null;
	}

}
