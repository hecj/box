package com.boxamazing.service.comment_support.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.plugin.activerecord.Model;

/**
 * 评论赞表
 * 
 * @author XuXD
 * 
 */
public class Comment_support extends Model<Comment_support> {
	private static final Log log = LogFactory.getLog(Comment_support.class);
	public static final Comment_support dao = new Comment_support();

	/**
	 * 获取赞数
	 * 
	 * @param commentId
	 * @return
	 */
	public long getCount(long commentId) {
		String sql = "SELECT count(1) FROM comment_support WHERE comment_id = ?;";
		try {
			return dao.findFirst(sql, commentId).getLong("count(1)");
		} catch (Exception e) {
			log.error("获取赞数异常", e);
		}
		return 0;
	}

	/**
	 * 获取赞
	 * 
	 * @param commentId
	 * @param userId
	 * @return
	 */
	public Comment_support getSupport(long commentId, long userId) {
		log.info("[commentId:" + commentId + ",userId:" + userId + "]");
		String sql = "select id from comment_support where comment_id = ? and user_id = ?";
		try {
			return dao.findFirst(sql, commentId, userId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取赞异常", e);
		}
		return null;
	}

	/**
	 * 通过commentId,userId判断是否赞
	 * 
	 * @author XuXD
	 * @param commentId
	 * @param userId
	 * @return
	 */
	public Boolean isSupport(long commentId, long userId) {
		log.info("[commentId:" + commentId + ",userId:" + userId + "]");
		String sql = "select id from comment_support where comment_id = ? and user_id = ?";
		try {
			return dao.find(sql, commentId, userId).size() != 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过commentId,userId判断是否赞异常", e);
		}
		return false;
	}
}
