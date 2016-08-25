package com.boxamazing.service.open_user.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.user.model.User;
import com.jfinal.plugin.activerecord.Model;

/**
 * 第三方用户类
 * 
 * @author XuXD
 * 
 */
public class OpenUser extends Model<OpenUser> {
	private static final Log log = LogFactory.getLog(OpenUser.class);
	public static final OpenUser dao = new OpenUser();

	/**
	 * 通过openId获取第三方用户
	 * 
	 * @author XuXD
	 * @param openId
	 * @param type
	 * @return
	 */
	public OpenUser findByOpenId(String openId, int type) {
		log.info("[openId:" + openId + ",type:" + type + "]");
		String sql = "select * from open_user where openid=? and type=?";
		try {
			return dao.findFirst(sql, openId, type);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过openId获取第三方用户异常", e);
		}
		return null;
	}

	/**
	 * 通过openId获取本地用户
	 * 
	 * @author XuXD
	 * @param openId
	 * @param type
	 * @return
	 */
	public User findUser(String openId, int type) {
		log.info("[openId:" + openId + ",type:" + type + "]");
		try {
			Long userId = OpenUserRelation.dao.findUserId(openId, type);
			if (userId!=null) 
				return User.dao.findById(userId);
			log.info("userId is null");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过openId获取本地用户异常",e);
		}
		return null;
	}
}
