package com.boxamazing.service.user.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 用户私信内容表
 * 
 * @author Xu
 * 
 */
public class UserLettleContent extends Model<UserLettleContent> {
	public static final UserLettleContent dao = new UserLettleContent();
	private static final Log log = LogFactory.getLog(UserLettleContent.class);

	/**
	 * 获取最后一条内容
	 * @param fromUsesrId
	 * @param toUserId
	 * @return
	 */
	public UserLettleContent getLastContent(long fromUsesrId, long toUserId) {
		log.info("[fromUsesrId:" + fromUsesrId + ",toUserId:" + toUserId + "]");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM user_lettle_content ");
		sql.append("WHERE ( receive_user_id = ? AND send_user_id = ? ) ");
		sql.append("OR ( receive_user_id = ? AND send_user_id = ? ) ");
		sql.append("ORDER BY send_at DESC; ");
		try {
			return dao.findFirst(sql.toString(), fromUsesrId, toUserId, toUserId, fromUsesrId);
		} catch (Exception e) {
			log.error("获取最后一条内容异常",e);
		}
		return null;
	}

	
	public Map<String, Object> getLettleContents(long fromUsesrId, long toUserId, long time){
		log.info("[fromUsesrId:" + fromUsesrId + ",toUserId:" + toUserId + "]");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM user_lettle_content ");
		sql.append("WHERE (( receive_user_id = ? AND send_user_id = ? ) ");
		sql.append("OR ( receive_user_id = ? AND send_user_id = ? )) ");
		sql.append("AND ( send_at > ? ) ");
		sql.append("ORDER BY send_at DESC; ");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("contents", dao.find(sql.toString(), fromUsesrId, toUserId, toUserId, fromUsesrId, time));
			map.put("from", User.dao.findById(fromUsesrId));
			map.put("to", User.dao.findById(toUserId));
			return map;
		} catch (Exception e) {
			log.error("获取最后一条内容异常",e);
		}
		return new HashMap<String, Object>();
	}
	
	/**
	 * 获取获取未读私信
	 * 
	 * @author XuXD
	 * @date 2015-11-25
	 * @param userId
	 * @return
	 */
	public long getCount(long userId) {
		String sql = "SELECT count(1) FROM user_lettle_content WHERE receive_user_id = ? AND is_read = 0";
		try {
			return dao.findFirst(sql, userId).getLong("count(1)");
		} catch (Exception e) {
			log.error("获取获取未读私信异常", e);
		}
		return 0;
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
			return Db.update("update user_lettle_content set is_read = 1 where receive_user_id = ?", userId);
		} catch (Exception e) {
			log.error("标记已读异常", e);
		}
		return -1;
	}
	
}
