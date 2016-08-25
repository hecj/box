package com.boxamazing.webfront.controller.open_user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.open_user.model.OpenUser;
import com.boxamazing.service.open_user.model.OpenUserRelation;
import com.boxamazing.service.user.model.User;
import com.boxamazing.webfront.common.BaseController;
import com.boxamazing.webfront.util.NicknameUtil;
import com.boxamazing.webfront.util.OpenUserUtil;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

/**
 * 第三方用户登录QQ
 * 
 * @author XuXD
 * 
 */
public class OpenUserForQQ extends BaseController {
	private static final Log log = LogFactory.getLog(OpenUserForQQ.class);

	/**
	 * 默认方法
	 * 
	 * @author XuXD
	 */
	public void index() {
		redirect("/");
	}

	/**
	 * 获取用户授权
	 * 
	 * @author XuXD
	 */
	public void login() {
		HttpServletRequest request = getRequest();
		try {
			redirect(new Oauth().getAuthorizeURL(request));
			return;
		} catch (Exception e) {
			log.error("获取用户授权异常", e);
		}
		redirect("/");
	}

	/**
	 * 获取用户信息
	 * 
	 * @author XuXD
	 */
	public void afterLogin() {
		User user = null;
		OpenUser openUser = null;
		try {
			AccessToken token = new Oauth().getAccessTokenByRequest(getRequest());
			String accessToken = token.getAccessToken();
			log.info("accessToken:" + accessToken);
			if (accessToken.equals("")) {
				// 我们的网站被CSRF攻击了或者用户取消了授权,做一些数据统计工作
				log.info("没有获取到响应参数");
				redirect("/");
				return;
			}
			// 获取openid
			final String openID = new OpenID(accessToken).getUserOpenID();
			log.info("openID:" + openID);
			// 判断是否新用户
			openUser = OpenUser.dao.findByOpenId(openID, 0);
			if (null == openUser) {
				// 第一次登录，获取用户基本信息
				UserInfoBean userInfoBean = new UserInfo(accessToken, openID).getUserInfo();
				// 判断是否获取到了正确的用户信息
				if (userInfoBean.getRet() != 0) {
					log.info("获取用户信息失败[ret:" + userInfoBean.getRet() + ",msg" + userInfoBean.getMsg() + "]");
					redirect("/");
					return;
				}
				// 获取个人信息
				final String nickname = userInfoBean.getNickname();
				final int sex = "男".equals(userInfoBean.getGender()) ? 1 : "女".equals(userInfoBean.getGender()) ? 2 : 3;
				final String img = userInfoBean.getAvatar().getAvatarURL30();
				final long currentTime = System.currentTimeMillis();

				// 保存
				Db.tx(new IAtom() {
					@Override
					public boolean run() throws SQLException {
						// 创建第三方账号
						OpenUser newOpenUser = new OpenUser().set("openid", openID).set("type", 0).set("nickname", nickname)
								.set("sex", sex).set("img", img).set("create_at", currentTime).set("login_at", currentTime)
								.set("update_at", currentTime);
						if (!newOpenUser.save()) {
							log.info("openUser保存失败");
							return false;
						}
						// 创建默认本地账号
						String finalNick = nickname == null ? "hz":nickname;
						int length = finalNick.length();
						//先判断是否在4-12位之间
						if(length<4){
							finalNick = NicknameUtil.createRandString(4,1,finalNick);
						}else if(length>12){
							finalNick = finalNick.substring(0, 12);
						}
						
						//判断是否有重复昵称
						List<User> userList = User.dao.findSameNickname(finalNick);
						while(userList.size() != 0){
							//TODO  //昵称
							finalNick = NicknameUtil.createRandString(length++, 1, finalNick);
							userList = User.dao.findSameNickname(finalNick);
							if(length>12){
								break;
							}
						}
						User newUser = new User().set("nickname", finalNick).set("sex", sex).set("picture", img).set("status", 1)
								.set("create_at", currentTime);
						if (!newUser.save()) {
							log.info("user保存失败");
							return false;
						}
						// 关联账号
						OpenUserRelation newRelation = new OpenUserRelation().set("open_id", newOpenUser.getLong("id"))
								.set("user_id", newUser.getLong("id")).set("default_user_id", newUser.getLong("id")).set("type", 0)
								.set("create_at", currentTime).set("update_at", currentTime);
						if (!newRelation.save()) {
							log.info("relation保存失败");
							return false;
						}
						// 存入session
						OpenUserUtil.setOpenUser(newOpenUser, getSession());
						UserUtil.setUser(newUser, getSession());
						return true;
					}
				});
			} else {
				// 非新用户,直接获取信息
				user = OpenUser.dao.findUser(openID, 0);
				// 更新时间
				openUser.set("update_at", System.currentTimeMillis());
				if (!openUser.update())
					log.info("OpenUser更新时间失败");
				// 存入session
				OpenUserUtil.setOpenUser(openUser, getSession());
				UserUtil.setUser(user, getSession());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取用户信息异常", e);
			redirect("/");
			return;
		}
		redirect("/");
	}
}
