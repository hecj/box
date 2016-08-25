package com.boxamazing.service.user.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.util.FormatUtil;
import com.jfinal.plugin.activerecord.Model;

/**
 * 用户私信会话表
 * 
 * @author Xu
 * 
 */
public class UserLettleDialog extends Model<UserLettleDialog> {
	public static final UserLettleDialog dao = new UserLettleDialog();
	private static final Log log = LogFactory.getLog(UserLettleDialog.class);

	/**
	 * 查询会话
	 * @param fromUsesrId
	 * @param toUserId
	 * @return
	 */
	public UserLettleDialog getDialog(long fromUsesrId, long toUserId) {
		log.info("[fromUsesrId:" + fromUsesrId + ",toUserId:" + toUserId + "]");
		try {
			return dao.findFirst("select * from user_lettle_dialog where from_usesr_id=? and to_user_id=? ", fromUsesrId, toUserId);
		} catch (Exception e) {
			log.error("查询会话异常",e);
		}
		return null;
	}
	
	/**
	 * 查询用户会话
	 * @param fromUsesrId
	 * @return
	 */
	public List<UserLettleDialog> getUserDialog(long fromUsesrId){
		log.info("[fromUsesrId:" + fromUsesrId + "]");
		try {
			List<UserLettleDialog> dialogs = dao.find("SELECT * FROM user_lettle_dialog WHERE from_usesr_id=? AND is_delete=0 ORDER BY create_at DESC",fromUsesrId);
			for (UserLettleDialog dialog : dialogs) {
				UserLettleContent content = UserLettleContent.dao.getLastContent(dialog.getLong("from_usesr_id"), dialog.getLong("to_user_id"));
				if (content != null) {
					dialog.put("message", content.getStr("message"));
					dialog.put("send_at", FormatUtil.MyDateFormat("yyyy-MM-dd HH:mm:ss",content.getLong("send_at")));
				}
				User user = User.dao.findById(dialog.getLong("to_user_id"));
				if (user != null) {
					dialog.put("nickname", user.getNickName());
					dialog.put("picture", user.getPicture());
				}
			}
			return dialogs;
		} catch (Exception e) {
			log.error("查询用户会话异常", e);
		}
		return new ArrayList<UserLettleDialog>();
	}
	
	
}
