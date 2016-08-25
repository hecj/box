package com.boxamazing.service.open_user.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.plugin.activerecord.Model;

/**
 * 第三方与本地账号关系
 * 
 * @author XuXD
 * 
 */
public class OpenUserRelation extends Model<OpenUserRelation> {
	private static final Log log = LogFactory.getLog(OpenUserRelation.class);
	public static final OpenUserRelation dao = new OpenUserRelation();

	/**
	 * 通过openId,type获取用户Id
	 * 
	 * @author XuXD
	 * @param openId
	 * @param type
	 * @return
	 */
	public Long findUserId(String openId, int type) {
		log.info("[openId:" + openId + ",type:" + type + "]");
		try {
			OpenUser openUser = OpenUser.dao.findFirst("select id from open_user where openid=? and type=? ", openId, type);
			Long open_id = openUser.getLong("id");
			OpenUserRelation openUserRelation = dao.findFirst("select user_id from open_user_relation where open_id=? and type=?", open_id, type);
			return openUserRelation.getLong("user_id");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过openId,type获取用户Id异常", e);
		}
		return null;
	}

	/**
	 * 通过openId,type获取关联表
	 * 
	 * @author XuXD
	 * @date 2015-10-19
	 * @param openId
	 * @param type
	 * @return
	 */
	public OpenUserRelation findByOpenId(long openId, int type) {
		log.info("[openId:" + openId + ",type:" + type + "]");
		try {
			return dao.findFirst("select * from open_user_relation where open_id=? and type=?", openId, type);
		} catch (Exception e) {
			log.error("通过openId,type获取关联表异常", e);
		}
		return null;
	}
	
	/**
	 * 通过userId,type获取关联表
	 * @author XuXD
	 * @date 2015-11-12
	 * @param userId
	 * @param type
	 * @return
	 */
	public OpenUserRelation findByUserId(long userId, int type){
		log.info("[userId:" + userId + ",type:" + type + "]");
		try {
			return dao.findFirst("select id from open_user_relation where user_id=? and type=?", userId, type);
		} catch (Exception e) {
			log.error("通过openId,type获取关联表异常", e);
		}
		return null;
	}
}
