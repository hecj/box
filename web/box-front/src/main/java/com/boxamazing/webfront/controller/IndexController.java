package com.boxamazing.webfront.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.PasswordUtil;
import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.msg.model.MessageConfig;
import com.boxamazing.service.open_user.model.OpenUser;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.project.model.ProjectCategory;
import com.boxamazing.service.project.model.ProjectRecomm;
import com.boxamazing.service.sms.EmailService;
import com.boxamazing.service.sms.SmsService;
import com.boxamazing.service.sms.model.AuthToken;
import com.boxamazing.service.sms.model.NoticeTemplate;
import com.boxamazing.service.sms.model.SendEmailRecord;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.Constant;
import com.boxamazing.service.util.TokenUtil;
import com.boxamazing.webfront.common.BaseController;
import com.boxamazing.webfront.util.CheckNum;
import com.boxamazing.webfront.util.Code;
import com.boxamazing.webfront.util.CookieUtil;
import com.boxamazing.webfront.util.MD5;
import com.boxamazing.webfront.util.NicknameUtil;
import com.boxamazing.webfront.util.OpenUserUtil;
import com.boxamazing.webfront.util.ResultJson;
import com.boxamazing.webfront.util.UserUtil;
import com.boxamazing.webfront.util.constant.EmailType;
import com.boxamazing.webfront.util.constant.UserNameType;
import com.boxamazing.webfront.util.constant.UserStatus;
import com.boxamazing.webfront.util.constant.ValidParamEnum;
import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;

/**
 * IndexController&LoginController Created by pchome on 2015/7/28.
 */

public class IndexController extends BaseController {
	private static final Log log = LogFactory.getLog(IndexController.class);

	/**
	 * 众筹首页入口
	 */
	public void index() {
		try {
			/**
			 * 查询banner图片集合
			 */
			List<MessageConfig> messageConfigList = MessageConfig.dao.findMessageConfigByType(1, 4);
			setAttr("messageConfigList", messageConfigList);
			log.info("messageConfigList:"+messageConfigList.size());
			// 获取本周推荐项目
			// 推荐的项目ids
			List<Long> projectRecommIds = new ArrayList<Long>();
			List<ProjectRecomm> projectRecommList = ProjectRecomm.dao.getProjectRecommByPosition(1, 1);
			if (projectRecommList != null && projectRecommList.size() > 0) {
				log.info("projectRecommList size:"+projectRecommList.size());
				Project recommProject = Project.dao.findById(projectRecommList.get(0).getLong("project_id"));
				setAttr("recommProject", recommProject);
				
				projectRecommIds.add(recommProject.getLong("id"));
			}

			// 获取最新上线的4个项目
			// TODO 参数配置化
			List<Project> newProjectList = Project.dao.getNewProject(4,projectRecommIds);
			log.info("newProjectList:"+newProjectList.size());
			setAttr("newProjectList", newProjectList);
			// 获取登录用户
			OpenUser openUser = OpenUserUtil.getOpenUser(getSession());
			User user = UserUtil.getUser(getSession());
			if(user==null){
				user = CookieUtil.readCookieAndLogin(getRequest(), getResponse());
				if(user!=null){
					UserUtil.setUser(user, getSession());
				}
			}
			setAttr("openUser", openUser);
			setAttr("user", user);

			// 项目类型
			List<ProjectCategory> projectTypeList = ProjectCategory.dao.findAll();
			setAttr("projectTypeList", projectTypeList);

			// 静态URL
			setAttr("STATIC_URL", Constant.STATIC_URL);
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		render("index.ftl");
	}

	/**
	 * 退出登录
	 * 
	 * @author XuXD
	 */
	@ClearInterceptor(ClearLayer.ALL)
	public void logOut() {
		UserUtil.removeUser(getSession());
		OpenUserUtil.removeOpenUser(getSession());
		removeCookie("hezi");
		redirect("/");
	}

	/**
	 * 用户登录页
	public void login() {
		String back_url = getRequest().getParameter("back_url");
		if (!StringUtil.isNullOrEmpty(back_url)) {
			log.info("to登录页时，URL参数中带有回调地址back_url：" + back_url);
			setAttr("back_url", back_url);
		}
		render("login.ftl");
	}
	 */
	
	/**
	 * 微信二维码登陆
	 * @author XuXD
	 * @date 2015-10-21
	 */
	public void wechatLogin(){
		String back_url = getRequest().getParameter("back_url");
		if (!StringUtil.isNullOrEmpty(back_url)) {
			log.info("to登录页时，URL参数中带有回调地址back_url：" + back_url);
			setAttr("back_url", back_url);
		}
		render("wechatLogin.ftl");
	}
	
	/**
	 * 用户注册页
	
	public void register() {
		render("reg.ftl");
	} 
	*/

	/**
	 * 用户登录Action
	 * 
	 * @author XuXD
	 * @throws IOException
	 */
	public void doLogin() throws IOException {
		ResultJson resultJson = new ResultJson();
		String username = getPara("username");
		String password = getPara("password");
		String back_url = getRequest().getParameter("back_url");
		boolean isAutoLogin = getParaToBoolean("isAutoLogin",false);

		// 1.校验用户名并返回用户名类型
		int userNameType = checkUserName(username);
		if (userNameType == -1) {
			resultJson.setCode(Code.F_14);
			renderJson(resultJson);
			return;
		}

		// 2.校验密码是否为空
		if (CheckNum.isEmpty(password)) {
			resultJson.setCode(Code.F_12);
			renderJson(resultJson);
			return;
		}

		// 3.登录
		User user = User.dao.finduser(username);
		if(null == user){
			resultJson.setCode(Code.F_14);
			renderJson(resultJson);
			return;
		}
		List<User> userList = User.dao.findUser(username, password, userNameType);
		if (null == userList || userList.size() == 0) {
			//用户名存在   密码错误
			resultJson.setCode(Code.F_12);
			renderJson(resultJson);
			return;
		}
		
		user = userList.get(0);
		if (user.getInt("status").intValue() == UserStatus.locked) {
			resultJson.setCode(Code.F_13);
			renderJson(resultJson);
			return;
		}

		// 4.返回登录成功页面
		if(isAutoLogin){
			List<String> list = CookieUtil.saveCookie(getRequest(), getResponse(), user);
			setCookie(list.get(0), list.get(1), Integer.valueOf(list.get(2)), "/");
		}
		UserUtil.setUser(user, getSession());
		setSessionAttr("user", user);
		resultJson.setCode(Code.S_200);
		if (!StringUtil.isNullOrEmpty(back_url)) {
			log.info("登录提交时，URL参数中带有回调地址，跳转到回调页面。back_url：" + back_url);
		}else{
			back_url = "/";
		}
		resultJson.setData(user);
		resultJson.setMessage(back_url);
		renderJson(resultJson);
		
	}

	private int checkUserName(String username) {
		int usernameType = -1;
		if (CheckNum.isPhone(username)) {
			usernameType = UserNameType.PHOEN;
		} else if (CheckNum.isEmail(username)) {
			usernameType = UserNameType.EMAIL;
		}
		return usernameType;
	}

	/**
	 *  用户注册
	 */
	public void save() {
		ResultJson resultJson = new ResultJson();
		log.info("手机号为：" + getPara("phone") + "的用户注册");
		String phone = getPara("phone");
		String password = getPara("password");
		String passwordE = getPara("passwordE");
		if (phone == null || !CheckNum.isLong(phone)) {
			redirect("/page_404");
		}else if(!password.equals(passwordE)){
			resultJson.setCode(Code.F_100000);
		}else {
			User user = User.dao.findUser(phone);
			// 此用户已被注册
			if (user != null) {
				resultJson.setCode(Code.F_10000);
			} else {
				// 注册新用户
//				String nickname = phone;
//				StringBuffer sb = new StringBuffer(nickname);
//				sb.replace(3, 7, "****");
				// 创建默认本地账号
				String nickname = "hz";
				int length = nickname.length();
				nickname = NicknameUtil.createRandString(4,1,nickname);
					
				//判断是否有重复昵称
				List<User> userList = User.dao.findSameNickname(nickname);
				while(userList.size() != 0){
					//昵称
					nickname = NicknameUtil.createRandString(length++, 1, nickname);
					userList = User.dao.findSameNickname(nickname);
					if(length>12){
						break;
					}
				}
				//sb.append("_"+(int)(Math.random()*1000000)+1);
				user = new User();
				user.put("username", phone);
				user.put("phone", phone);
				user.put("password", PasswordUtil.encryptPassword(password));
				user.put("status", 1);
				user.put("balance", 0);
				user.put("nickname",nickname);
				//user.put("idphoto", "/static/upload/temps/20151113105907505-8c29e0de-d87c-4318.png");
				user.put("sex",3);
				user.put("picture",Constant.WEBROOT_URL+"static/images/top-footer/userphoto.png");
				user.put("create_at", System.currentTimeMillis());
				boolean flag = user.save();
				if (!flag) {
					resultJson.setCode(00L);
				} else {
					//注册成功存session
					UserUtil.setUser(user, getSession());
					resultJson.setCode(11L);
				}
			}
		}
		renderJson(resultJson);
	}

	/**
	 * 
	 *  密码找回
	 */
	public void findPassword() {
		render("findPassword.ftl");
	}

	public void resetPassword() {
		String phoneAndEmail = getPara("phoneAndEmail");
		Long type = getParaToLong("type", 0L);
		log.info("重置用户信息为：" + phoneAndEmail + "的用户密码,方式为：" + type);
		if (phoneAndEmail == null) {
			log.info("用户输入有误");
			redirect("/page_404");
		} else {
			ResultJson resultJson = new ResultJson();
			User user = new User();
			// 根据类型查询用户
			if (type == 0) {
				user = User.dao.findUser(phoneAndEmail);
			} else {
				user = User.dao.findByEmail(phoneAndEmail);
			}
			if (user != null) {
				// 判断两个参数（手机和邮箱）是否都存在
				String phone = user.get("phone");
				String email = user.get("email");
				if (!CheckNum.isEmpty(phone) && !CheckNum.isEmpty(email)) {
					// 两个都存在
					resultJson.setCode(2L);
				} else if ((!CheckNum.isEmpty(phone)) && (CheckNum.isEmpty(email)) || (CheckNum.isEmpty(phone))
						&& (!CheckNum.isEmpty(email))) {
					// 只存在一个
					resultJson.setCode(type);
				} else {
					// 一个也不存在
					log.info("用户输入有误");
					redirect("/page_404");
				}
				resultJson.setData(user);
			} else {
				log.info("用户不存在");
				redirect("/page_404");
				return;
			}
			setAttr("type",type);
			setAttr("resultJson", resultJson);
			render("resetPassword.ftl");
		}

	}

	// 设置支付密码页
	public void setPayPwd() {
		render("setPayPwd.ftl");
	}

	/**
	 * 发送手机短信验证码
	 * 
	 * @author XuXD
	 * @date 2015-10-16
	 * @throws UnsupportedEncodingException
	 */
	public void sendCode() throws UnsupportedEncodingException {
		// 判断发送间隔
		String interval = getCookie("box_amazing_message_interval");
		if (interval != null) {
			renderJson(new ResultJson(Code.F_1, "请60秒后重试"));
			return;
		}
		setCookie("box_amazing_message_interval", "60", 60);

		// 获取参数
		String phone = getParam("phone", "").trim();
		int type = getParam("type", 2);

		// 校验手机号码格式
		if (phone == null || !CheckNum.isPhone(phone)) {
			log.info("手机号格式不正确" + phone);
			renderJson(new ResultJson(-2L, "手机号格式不正确"));
			return;
		}

		// 生成验证码并生成模板
		int num = (int) ((Math.random() * 9 + 1) * 100000);
		NoticeTemplate noticeTemplate = NoticeTemplate.dao.findById(type);
		String content = noticeTemplate.getStr("content");
		content = content.replace("{CHECK_CODE}", String.valueOf(num));
		log.info("短信内容:" + content);
		setSessionAttr(phone, num);
		setSessionAttr("phoneAndEmail",phone);
		setSessionAttr(phone+"time", System.currentTimeMillis());
		if (SmsService.sendSms(content, phone)) {
			renderJson(new ResultJson(Code.S_200, "发送成功"));
		} else {
			renderJson(new ResultJson(Code.F_100000, "发送失败"));
		}
	}

	/**
	 * 验证手机验证码
	 * 
	 * @author XuXD
	 * @date 2015-10-19
	 */
	
	public void verifyCode() {
		// 获取参数
		int code = getParam("code", -1);
		String phone = getParam("phone", "");
		long time = System.currentTimeMillis();
		Integer check_num = (Integer) getSessionAttr(phone);
		Long check_num_time = getSessionAttr(phone + "time");
		log.info("[code:" + code + ",check_num:" + check_num + ",check_num_time:" + check_num_time + "]");
		
		if (check_num == null || check_num_time == null || time - check_num_time > 600000) {
			renderJson(ValidParamEnum.TIMEOUT);
			return;
		}
		if (check_num == code) {
			renderJson(ValidParamEnum.IDENTIFY_CODE_SUCCESS);
		} else {
			renderJson(ValidParamEnum.IDENTIFY_CODE_ERROR);
		}
	}

	/**
	 * 修改密码
	 */
	public void resetPwd() {
		ResultJson resultJson = new ResultJson();
		String phoneOrEmail = getPara("id");
		String password = getPara("password");
		
		//先判断是手机还是邮箱 
		if(CheckNum.isEmail(phoneOrEmail)){
			//获取token
			String token = getPara("token");
			String sessionToken = getSessionAttr("token");
			if(sessionToken==null||"".equals(sessionToken)){
				log.info("session中查不到该记录token："+token);
				redirect("/page_404");
				return;
			}else{
				if(token==null||"".equals(token)){
					log.info("无效的token:"+token);
					redirect("/page_404");
					return;
				}
				
				if(token.equals(sessionToken)){
					log.info("页面传参数token为："+token+"------"+"session中的token为："+token);
					//根据token获取发送记录
					AuthToken authToken = AuthToken.dao.findByToken(token);
					if(authToken == null){
						log.info("该链接不存在或已验证,请重新发送验证");
						//setAttr("v_info", "该链接不存在或已验证,请重新发送验证");
						redirect("/page_404");
						return;
					}
					if(authToken.getInt("is_verify") == 1){
						log.info("该链接已通过验证");
						//setAttr("v_info", "该链接已通过验证");
						redirect("/");
						return;
					}
					
					//是否超时
					if(authToken.getLong("valid_at") < System.currentTimeMillis()){
						log.info("链接超时");
						//setAttr("v_info", "该链接已失效,请重新发送验证");
						redirect("/page_404");
						return;
					}
					
					//获取session中的值
					String userid = getSessionAttr("phoneAndEmail");
					//为空
					if(null==userid||"".equals(userid)){
						log.info("此时userid存在异常:"+userid);
						redirect("/page_404");
						return;
					}else if(userid.equals(phoneOrEmail)){
						if ((phoneOrEmail != null) && (password != null)) {
							User user = new User();
							if (CheckNum.isPhone(phoneOrEmail)) {
								user = User.dao.findUser(phoneOrEmail);
							} else if (CheckNum.isEmail(phoneOrEmail)) {
								user = User.dao.findByEmail(phoneOrEmail);
							} else {
								log.info("参数错误" + phoneOrEmail);
								redirect("/page_404");
							}
							
							if (user != null) {
								//修改密码
								user.set("password", PasswordUtil.encryptPassword(password));
								authToken.set("is_verify", 1).set("verify_at", System.currentTimeMillis());
								
								try {
									user.update();
									authToken.update();
									resultJson.setCode(Code.S_200);
									removeSessionAttr("token");
									removeSessionAttr("phoneAndEmail");
									//TODO
									//发送设置密码成功邮件(先判断是否有邮箱  没有邮箱 不会发送)
									String email = User.dao.findById(user.getLong("id")).getStr("email");
									String nickname = User.dao.findById(user.getLong("id")).getStr("nickname");
									if(email!=null&&!"".equals(email)){
										EmailService.sendNoticeEmail(email, "", "setPwd_succ", nickname);
									}
								} catch (Exception e) {
									log.error("系统异常");
									e.printStackTrace();
									resultJson.setCode(Code.F_100000);
								}
							} else {
								log.info("用户不存在");
								redirect("/");
							}
						} else {
							log.info("参数错误" + phoneOrEmail + password);
							redirect("/page_404");
						}
					}else{
						log.info("参数错误" + phoneOrEmail + password);
						redirect("/page_404");
					}
					
				}
			}
		}else{
			//获取session中的值
			String userid = getSessionAttr("phoneAndEmail");
			//为空
			if(null==userid||"".equals(userid)){
				log.info("此时userid存在异常:"+userid);
				redirect("/page_404");
				return;
			}else if(userid.equals(phoneOrEmail)){
				if ((phoneOrEmail != null) && (password != null)) {
					User user = new User();
					if (CheckNum.isPhone(phoneOrEmail)) {
						user = User.dao.findUser(phoneOrEmail);
					} else if (CheckNum.isEmail(phoneOrEmail)) {
						user = User.dao.findByEmail(phoneOrEmail);
					} else {
						log.info("参数错误" + phoneOrEmail);
						redirect("/page_404");
					}
					
					if (user != null) {
						//修改密码
						user.set("password", PasswordUtil.encryptPassword(password));
						
						try {
							user.update();
							resultJson.setCode(Code.S_200);
							removeSessionAttr("phoneAndEmail");
							//TODO
							//发送设置密码成功邮件(先判断是否有邮箱  没有邮箱 不会发送)
							String email = User.dao.findById(user.getLong("id")).getStr("email");
							String nickname = User.dao.findById(user.getLong("id")).getStr("nickname");
							if(email!=null&&!"".equals(email)){
								EmailService.sendNoticeEmail(email, "", "setPwd_succ", nickname);
							}
						} catch (Exception e) {
							log.error("系统异常");
							e.printStackTrace();
							resultJson.setCode(Code.F_100000);
						}
					}else {
						log.info("用户不存在");
						redirect("/");
					}
				}else {
					log.info("参数错误" + phoneOrEmail + password);
					redirect("/page_404");
				}
			}
		}
		renderJson(resultJson);
	}

	/**
	 * 验证邮箱
	 * 
	 */
	public void verifyEmail() {
		ResultJson resultJson = new ResultJson();
		String email = getPara("email");
		log.info("验证邮箱：" + email);
		if (email == null || !CheckNum.isEmail(email)) {
			log.info(email + "邮箱格式错误");
			redirect("/page_404");
		} else {
			User user = User.dao.findByEmail(email);
			if (user == null) {
				resultJson.setCode(00L);
				resultJson.setMessage("该邮箱尚未绑定");
			} else {
				resultJson.setCode(200L);
			}
		}
		renderJson(resultJson);

	}

	/**
	 * 
	 * 找回密码发送邮件
	 */
	public void sendEmail() {
		String email = getPara("email");
		int type = getParaToInt("type", EmailType.findPwd_message);
		if (email != null && CheckNum.isEmail(email)) {
			User user = User.dao.findByEmail(email);
			if (user != null) {
				String uuid = TokenUtil.generalToken();
				String urlMd5 = MD5.sign(uuid.replace("-", ""), email, "UTF-8");
				urlMd5 = urlMd5.concat(MD5.sign("findPwd", "hezi", "UTF-8"));	
				setSessionAttr("phoneAndEmail",email);
				log.info("当前发送邮件的用户是："+email+"---urlMd5:"+urlMd5);
				boolean isTrue = EmailService.sendEmail(urlMd5, email, type, "findPwd");
				if (isTrue) {
					renderText("true");
				} else {
					renderText("false");
				}
			} else {
				log.info("不存在该用户:" + email);
				renderText("false");
			}
		} else {
			log.info("邮件格式不正确" + email);
			redirect("/page_404");
		}
	}
	
	/**
	 * 用户绑定邮箱
	 */
	public void sendBindEmail() {
		String email = getPara("email");
		int type = getParaToInt("type", EmailType.bind_message);
		if (email != null && CheckNum.isEmail(email)) {
			User user = User.dao.findByEmail(email);
			if (user == null) {
				String uuid = TokenUtil.generalToken();
				String urlMd5 = MD5.sign(uuid, email, "UTF-8");
				urlMd5 = urlMd5.concat(MD5.sign("bind", "hezi", "UTF-8"));
				boolean isTrue = EmailService.sendEmail(urlMd5, email, type, "bind");
				if (isTrue) {
					log.info("发送验证邮箱成功:" + email);
					renderText("true");
				} else {
					log.info("发送验证邮箱失败:" + email);
					renderText("false");
				}
			} else {
				log.info("该邮箱已注册:" + email);
				renderText("registed");
			}
		} else {
			renderText("errorType");
			log.info("邮件格式不正确" + email);
		}
	}

	/**
	 * 验证邮件是否失效
	 * by yj
	 */
	public void verifyEmailTrue() {
		String uuid = getPara("uuid");
		if(CheckNum.isEmpty(uuid)){
			log.info("输入参数uuid不存在");
			//setAttr("v_info", "链接错误");
			redirect("/page_404");
			return;
		}
		
		//是否为空
		final AuthToken authToken = AuthToken.dao.findByToken(uuid);
		if(authToken == null){
			log.info("该链接不存在或已验证,请重新发送验证");
			//setAttr("v_info", "该链接不存在或已验证,请重新发送验证");
			redirect("/page_404");
			return;
		}
		if(authToken.getInt("is_verify") == 1){
			log.info("该链接已通过验证");
			//setAttr("v_info", "该链接已通过验证");
			redirect("/");
			return;
		}
		
		//是否超时
		if(authToken.getLong("valid_at") < System.currentTimeMillis()){
			log.info("链接超时");
			//setAttr("v_info", "该链接已失效,请重新发送验证");
			redirect("/page_404");
			return;
		}
		
		String action = uuid.substring(32);
		//邮箱绑定
		if(MD5.sign("bind", "hezi", "UTF-8").equals(action)){
			Long user_id = authToken.getLong("user_id");
			final User user = User.dao.findById(user_id);
			String valid_email = user.getStr("valid_email");
			
			User user_email = User.dao.findByEmail(valid_email);
			if(user_email != null){
				if(user_email.get("id").equals(user_id)){
					log.info("该链接已失效");
					//setAttr("v_info", "该链接已失效");
					redirect("/page_404");
					return;
				}else{
					log.info("该邮箱已被占用");
					//setAttr("v_info", "该邮箱已被绑定");
					redirect("/page_404");
					return;
				}
			}
			
			final SendEmailRecord sendRecord = SendEmailRecord.dao.findByEmail(valid_email);
			if(sendRecord == null){
				log.info("没有该用户的发送记录");
				//setAttr("v_info", "没有该用户的发送记录");
				redirect("/page_404");
				return;
			}
			
			boolean isBind = Db.tx(new IAtom(){
				@Override
				public boolean run()
						throws SQLException {
					//更新数据库（AuthToken表）
					authToken.set("is_verify", 1)
					.set("verify_at", System.currentTimeMillis());
					if(!authToken.update()){
						log.info("authToken更新失败");
						//setAttr("v_info", "服务器异常");
						redirect("/page_404");
						return false;
					}
					//更新 数据（SendEmailRecord）
					sendRecord.set("id_delete", 1);
					if(!sendRecord.update()){
						log.info("sendRecord更新失败");
						//setAttr("v_info", "服务器异常");
						redirect("/page_404");
						return false;
					}
					//更新User表中email数据
					user.set("email", sendRecord.getStr("reciver_email"));
					if(!user.update()){
						log.info("用户更新失败");
						//setAttr("v_info", "服务器异常");
						redirect("/page_404");
						return false;
					}
					return true;
				}
			});
			if(isBind){
				log.info("绑定成功");
				//setAttr("v_info", "绑定成功");
				redirect("/");
				return;
			}else{
				log.info("服务器异常");
				//setAttr("v_info", "服务器异常");
				redirect("/page_404");
				return;
			}
		}
		//找回密码邮箱验证
		else if(MD5.sign("findPwd", "hezi", "UTF-8").equals(action)){
			final String email = User.dao.findById(authToken.getLong("user_id")).getStr("email");
			if(email == null){
				log.info("邮箱参数错误");
				//setAttr("v_info", "邮箱参数错误");
				redirect("/page_404");
				return;
			}
			final SendEmailRecord sendRecord = SendEmailRecord.dao.findByEmail(email);
			if(sendRecord == null){
				log.info("没有该用户的发送记录");
				//setAttr("v_info", "没有该用户的发送记录");
				redirect("/page_404");
				return;
			}
			
			if(!uuid.equals(authToken.getStr("token"))){
				log.info("链接错误");
				redirect("/page_404");
				return;
			}
			final boolean isUpdate  = Db.tx(new IAtom(){
				@Override
				public boolean run() throws SQLException {
					
					//更新 数据（SendEmailRecord）
					sendRecord.set("id_delete", 1);
					if(!sendRecord.update()){
						log.info("sendRecord更新失败");
						redirect("/page_404");
						return false;
					}
					return true;
				}
			});
			
				//转移到新密码逻辑
				setSessionAttr("phoneAndEmail",email);
				setSessionAttr("token",uuid);
				setAttr("id",email);
				setAttr("token",uuid);
				render("newPwd.ftl");
				return;
			}else{
				log.info("服务器异常");
				redirect("/page_404");
				return;
			}
		}
	
	// 绑定手机
	public void bindPhone() {
		User user = UserUtil.getUser(getSession());
		String phone = StringEscapeUtils.escapeSql(getPara("phone"));
		List<User> list = User.dao.findByphone(phone);
		if(list != null && list.size() > 0){
			renderText("exist");
			return;
		}
		user.set("phone", phone);
		try {
			user.update();
			log.info("用户" + user.get("username") + "绑定手机号：" + getPara("phone"));
			renderText("true");
			return;
		} catch (Exception e) {
			log.fatal(e);
			renderText("false");
			return;
		}

	}

	/**
	 * 404页面 by HECJ
	 */
	public void page_404() {
		renderFreeMarker("/page/common/404.html");
	}

	// 验证手机号是否存在
	public void verifyPhone() {
		String phone = getPara("phone");
		User user = User.dao.findUser(phone);
		ResultJson resultJson = new ResultJson();
		if (user == null) {
			resultJson.setCode(00L);
		} else {
			resultJson.setCode(01L);
		}
		renderJson(resultJson);
	}

	// 验证账户是否存在
	public void verifyPhoneOrEmail() {
		ResultJson resultJson = new ResultJson();
		String phoneOrEmail = getPara("phoneOrEmail");
		int type = getParaToInt("type",0);
		if(CheckNum.isEmail(phoneOrEmail)){
			type=1;
		}else if(CheckNum.isPhone(phoneOrEmail)){
			type=0;
		}
		
		log.info("用户：" + phoneOrEmail + "正在验证" + "方式为：" + type);
		if (phoneOrEmail == null||phoneOrEmail=="") {
			log.info("用户输入有误");
			redirect("/page_404");
		} else {
			User user = new User();
			// 验证手机
			if (type == 0) {
				user = User.dao.findUser(phoneOrEmail);
			} else if (type == 1) {
				// 验证邮箱
				user = User.dao.findByEmail(phoneOrEmail);
			} else {
				log.info("用户输入有误");
				redirect("/page_404");
			}
			if (user == null) {
				resultJson.setCode(00L);
			} else {
				resultJson.setCode(200L);
				/*
				 * redirect("/resetPassword?phoneAndEmail="+phoneOrEmail+"&type="
				 * +type;);
				 */
			}
		}

		renderJson(resultJson);
	}

	/**
	 * 验证成功之后跳转到重置密码
	 */
	public void newPwd() {
		String id = getPara("id");
		log.info("用户id为：" + id + "的重置密码");
		String userid = getSessionAttr("phoneAndEmail");
		if(userid==null){
			redirect("/page_404");
			return;
		}
		log.info("找回密码的用户是 session :"+userid);
		if(userid.equals(id)){
			if (id != null || !CheckNum.isEmpty(id)) {
				setAttr("id", id);
				render("newPwd.ftl");
			} else {
				redirect("/page_404");
			}
		}else{
			redirect("/page_404");
		}
		
	}
	
	
	/**
	 * 
	 * 验证成功之后跳转到验证成功页面
	 */
	public void resetSucc(){
		render("resetSucc.ftl");
	}
	
	
	/**
	 * 判断session是否有用户信息
	 */
	public void isSession(){
		User user = UserUtil.getUser(getSession());
		ResultJson resultJson = new ResultJson();
		if(user==null){
			resultJson.setCode(00L);
		}else{
			resultJson.setCode(11L);
		}
		renderJson(resultJson);
	}
}
