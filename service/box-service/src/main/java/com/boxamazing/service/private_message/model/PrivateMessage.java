package com.boxamazing.service.private_message.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 私信
 * 
 * @author XuXD
 * 
 */
public class PrivateMessage extends Model<PrivateMessage> {
	public static final PrivateMessage dao = new PrivateMessage();
	private static final Log log = LogFactory.getLog(PrivateMessage.class);

	/**
	 * 获取未读私信总数
	 * 
	 * @author XuXD
	 * @date 2015-11-10
	 * @return
	 */
	public long getCount(long userId) {
		String sql = "SELECT count(1) FROM private_message WHERE receive_user_id = ? AND is_read = 0 AND is_delete = 0;";
		try {
			return dao.findFirst(sql,userId).getLong("count(1)");
		} catch (Exception e) {
			log.error("获取获取未读私信异常", e);
		}
		return 0;
	}

	/**
	 * 获取私信(分页显示)
	 * 
	 * @author XuXD
	 * @date 2015-11-13
	 * @param userId
	 * @param model
	 *            0:查看接收 1:查看发送
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<PrivateMessage> findMessageByPage(long userId, int model, int pageNum, int pageSize) {
		log.info("[userId:" + userId + ",model:" + model + " pageNum:" + pageNum + ",pageSize:" + pageSize + "]");
		String sql1 = "select p.*, u.nickname, u.picture ";
		String sql2 = "";
		if (model == 0) {
			sql2 = "from private_message p,user u where p.receive_user_id = ? AND is_delete = 0 and p.send_user_id = u.id order by send_at desc";
		} else if (model == 1) {
			sql2 = "from private_message p,user u where p.send_user_id = ? AND is_delete = 0 and p.receive_user_id = u.id order by send_at desc";
		} else {
			log.info("model invalid :" + model);
			sql2 = "from private_message p,user u where 1=2";
		}
		try {
			return dao.paginate(pageNum, pageSize, sql1, sql2, userId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取私信(分页显示)异常", e);
		}
		return null;
	}

	/**
	 * 标记已读
	 * 
	 * @author XuXD
	 * @date 2015-11-12
	 * @param userId
	 * @return
	 */
	public int setRead(long userId) {
		log.info("[userId:" + userId + "]");
		try {
			return Db.update("update private_message set is_read = 1 where receive_user_id = ?", userId);
		} catch (Exception e) {
			log.error("标记已读异常", e);
		}
		return -1;
	}
}
