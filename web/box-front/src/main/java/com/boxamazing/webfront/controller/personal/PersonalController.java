package com.boxamazing.webfront.controller.personal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.code.Areas;
import com.boxamazing.service.code.Cities;
import com.boxamazing.service.code.Provinces;
import com.boxamazing.service.common.PasswordUtil;
import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.fans.model.Fans;
import com.boxamazing.service.open_user.model.OpenUser;
import com.boxamazing.service.open_user.model.OpenUserRelation;
import com.boxamazing.service.payPwd.model.UserPayPwd;
import com.boxamazing.service.private_message.model.PrivateMessage;
import com.boxamazing.service.system_message.model.SystemMessage;
import com.boxamazing.service.user.model.ReceiveAddress;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.user.model.UserLettleContent;
import com.boxamazing.service.user.model.UserLettleDialog;
import com.boxamazing.service.util.FormatUtil;
import com.boxamazing.webfront.common.BaseController;
import com.boxamazing.webfront.interceptor.LoginInterceptor;
import com.boxamazing.webfront.util.CheckNum;
import com.boxamazing.webfront.util.Code;
import com.boxamazing.webfront.util.OpenUserUtil;
import com.boxamazing.webfront.util.ResultJson;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * 个人中心
 * 
 * @author XuXD
 * 
 */
public class PersonalController extends BaseController {
	private static final Log log = LogFactory.getLog(PersonalController.class);

	private static final int MESSAGE_PAGE_SIZE = PropKit.use("page_size.properties").getInt("messagePageSize");

	/**
	 * 个人中心首页
	 * 
	 * @author XuXD
	 */
	@Before(LoginInterceptor.class)
	public void index() {
		// 获取登录用户
		User user = UserUtil.getUser(getSession());
		setAttr("user", user);
		render("index.ftl");
	}

	/**
	 * 获取个人基本资料
	 * 
	 * @author XuXD
	 */
	@Before(LoginInterceptor.class)
	public void getPersonalDetail() {
		try {
			// 获取登录用户
			User user = UserUtil.getUser(getSession());
			if (null == user) {
				renderJson(new ResultJson(Code.F_1, "请登录"));
				return;
			}
			
			user = User.dao.findById(user.getLong("id"));
			UserUtil.setUser(user, getSession());
			

			// 获取粉丝数量
			Long fans_count = Fans.dao.getFansCountByPromoder(user.getLong("id"));
			setAttr("fans", fans_count);

			// 获取省信息
			List<Provinces> provincesList = Provinces.dao.findAll();
			setAttr("provincesList", provincesList);

			// 获取市/区信息
			String userProvince = user.getStr("province") == null ? "110000" : user.getStr("province");
			List<Cities> citiesList = Cities.dao.findCitiesByProvinceId(userProvince);

			// 直辖市
			Provinces provinces = Provinces.dao.findCacheByProvinceId(userProvince);
			if (provinces.getInt("type") == 2) {
				List<Areas> areasList = new ArrayList<>();
				for (Cities cities : citiesList) {
					areasList.addAll(Areas.dao.findAreasByCtiy(cities.getStr("cityid")));
				}
				setAttr("citiesList", areasList);
			} else {
				setAttr("citiesList", citiesList);
			}

			// 将生日时间戳转换成date类型
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Long date = new Long(System.currentTimeMillis());
			if (user.getLong("birthday") != null) {
				date = user.getLong("birthday");
			}
			String strBirth = format.format(date);
			String[] str = strBirth.split("-");
			setAttr("birthYear", str[0]);
			setAttr("birthMonth", str[1]);
			setAttr("birthDay", str[2]);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取个人基本资料异常", e);
		}
		render("personalDetail.ftl");
	}

	/**
	 * 修改个人基本资料
	 * 
	 * @author XuXD
	 * @throws ParseException
	 */
	@Before(LoginInterceptor.class)
	public void updatePersonalDetail() {
		// 获取登录用户
		User user = UserUtil.getUser(getSession());
	
		if (null == user) {
			renderJson(new ResultJson(Code.F_1, "请登录"));
			return;
		}
		
		user = User.dao.findById(user.getLong("id"));
		UserUtil.setUser(user, getSession());

		try {
			// 获取参数
			String picture = getParam("picture", "");
			String nickname = getParam("nickname", "");
			List<User> userList = User.dao.findSameNickname(nickname);
			// 昵称列表不为空并且与当前不一致
			if (userList.size() != 0 && !nickname.equals(user.getStr("nickname"))) {
				renderJson(new ResultJson(Code.F_1, "该昵称已被使用,请重新填写"));
				return;
			}

			int sex = getParam("sex", 3);
			Integer birthType = getParam("birthType", -1);
			if (birthType == -1) {
				String birthYear = getParam("birthYear", "1970");
				String birthMonth = getParam("birthMonth", "1");
				String birthDay = getParam("birthDay", "1");

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = format.parse(birthYear + "-" + birthMonth + "-" + birthDay + " 00:00:00");

				Long birthday = date.getTime();
				String province = getParam("province", "-1");
				String city = getParam("city", "-1");
				String note = getParam("note", "");

				// 判断省市值是否有效
				province = province.equals("-1") ? null : province;
				city = city.equals("-1") ? null : city;

				// 更新个人基本资料
				user.set("picture", picture).set("nickname", nickname).set("sex", sex).set("province", province).set("birthday", birthday)
						.set("city", city).set("note", note).update();
				renderJson(new ResultJson(Code.S_200, "保存成功"));
				return;
			} else {
				renderJson(new ResultJson(Code.F_100000, "农历生日暂不支持"));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("update user exception", e);
			renderJson(new ResultJson(Code.F_100000, "保存失败"));
			return;
		}

	}

	/**
	 * 私信页面
	 * 
	 * @author XuXD
	 * @date 2015-11-25
	 */
	@Before(LoginInterceptor.class)
	public void myPrivateMessage() {
		long userId = UserUtil.getUser(getSession()).getLong("id");
		try {
			// 获取会话
			setAttr("dialogs", UserLettleDialog.dao.getUserDialog(userId));
			// 标记已读
			UserLettleContent.dao.setRead(userId);
			// 获取未读消息数量
			setAttr("privateMessageCount",UserLettleContent.dao.getCount(userId));
			setAttr("systemMessageCount", SystemMessage.dao.getCount(userId));
		} catch (Exception e) {
			log.error("跳转私信页面异常", e);
			setAttr("privateMessageCount", 0);
			setAttr("systemMessageCount", 0);
		}
		render("privateMessage.ftl");
	}
	
	/**
	 * 发私信页面
	 * @author XuXD
	 * @date 2015-11-25
	 */
	@Before(LoginInterceptor.class)
	public void letterWrite(){
		long userId = UserUtil.getUser(getSession()).getLong("id");
		try {
			// 获取未读消息数量
			setAttr("privateMessageCount", UserLettleContent.dao.getCount(userId));
			setAttr("systemMessageCount", SystemMessage.dao.getCount(userId));
		} catch (Exception e) {
			log.error("跳转发私信页面异常", e);
			setAttr("privateMessageCount", 0);
			setAttr("systemMessageCount", 0);
		}
		render("LetterWrite.ftl");
	}

	/***
	 * 查看更多页面
	 * 
	 * @author XuXD
	 * @date 2015-11-25
	 */
	@Before(LoginInterceptor.class)
	public void letterChatRecord() {
		long userId = UserUtil.getUser(getSession()).getLong("id");
		
		// 获取参数
		long fromUsesrId = getParam(0, -1L);
		long toUserId = getParam(1, -1L);
		setAttr("toUserId", toUserId);
		long dialogId = getParam(2, -1L);
		
		try {
			// 标记已读
			UserLettleContent.dao.setRead(userId);
			// 获取删除时间
			long time = UserLettleDialog.dao.findById(dialogId).getLong("delete_at");
			// 获取聊天记录
			Map<String, Object> map = UserLettleContent.dao.getLettleContents(fromUsesrId, toUserId,time);
			setAttr("fromUsesr", map.get("from"));
			setAttr("toUsesr", map.get("to"));
			setAttr("contents", map.get("contents"));
			// 获取未读消息数量
			setAttr("privateMessageCount", UserLettleContent.dao.getCount(userId));
			setAttr("systemMessageCount", SystemMessage.dao.getCount(userId));
		} catch (Exception e) {
			log.error("跳转查看更多页面异常", e);
			setAttr("privateMessageCount", 0);
			setAttr("systemMessageCount", 0);
		}
		render("LetterChatRecord.ftl");
	}

	/**
	 * 发私信
	 * 
	 * @author XuXD
	 */
	@Before(LoginInterceptor.class)
	public void sendPrivateMessage() {
		// 获取登录用户ID
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderJson(new ResultJson(Code.F_1, "请登录"));
			return;
		}
		long userId = user.getLong("id");

		// 获取接收人id
		long receiveUserId = getParam(0, -1L);
		
		// 获取接收人昵称
		String receiveUserName = getParam("receiveUserName", null);
		if (receiveUserName != null) {
			List<User> list = User.dao.findSameNickname(receiveUserName);
			if (list.size() < 1) {
				renderJson(new ResultJson(Code.F_02, "用户不存在"));
				return;
			}else{
				receiveUserId = list.get(0).getLong("id");
			}
		}

		// 校验id
		if (userId == receiveUserId) {
			renderJson(new ResultJson(Code.F_02, "不能私信自己~"));
			return;
		}
		
		// 获取私信内容
		String content = getParam("content", "");
		if (null == content || content.isEmpty()) {
			renderJson(new ResultJson(Code.F_02, "请输入内容"));
			return;
		}
		if (content.length() > 300) {
			renderJson(new ResultJson(Code.F_02, "不能超过300个字符"));
			return;
		}

		try {
			// 判断主会话是否存在
			UserLettleDialog from = UserLettleDialog.dao.getDialog(userId, receiveUserId);
			if (from == null) {
				new UserLettleDialog().set("from_usesr_id", userId)
									  .set("to_user_id", receiveUserId)
									  .set("create_at", System.currentTimeMillis())
									  .save();
			} else if (from.getInt("is_delete") == 1) {
				from.set("is_delete", 0).set("create_at", System.currentTimeMillis()).update();
			} else {
				from.set("create_at", System.currentTimeMillis()).update();
			}
			// 判断次会话是否存在
			UserLettleDialog to = UserLettleDialog.dao.getDialog(receiveUserId, userId);
			if (to == null) {
				new UserLettleDialog().set("from_usesr_id", receiveUserId)
									  .set("to_user_id", userId)
									  .set("create_at", System.currentTimeMillis())
									  .save();
			} else if (to.getInt("is_delete") == 1) {
				to.set("is_delete", 0).set("create_at", System.currentTimeMillis()).update();
			} else {
				to.set("create_at", System.currentTimeMillis()).update();
			}
			
			// 插入内容
			new UserLettleContent().set("receive_user_id", receiveUserId)
								   .set("send_user_id", userId)
								   .set("message", content)
								   .set("send_at", System.currentTimeMillis())
								   .set("create_at", System.currentTimeMillis())
								   .save();
			
			renderJson(new ResultJson(Code.S_200, "发送成功"));
		} catch (Exception e) {
			log.error("发私信异常", e);
			renderJson(new ResultJson(Code.F_100000, "发送失败"));
		}
	}

	/**
	 * 获取私信
	 * 
	 * @author XuXD
	 */
	@Before(LoginInterceptor.class)
	public void getPrivateMessage() {
		// 准备Json数据
		HashMap<String, Object> map = new HashMap<String, Object>();

		// 获取登录用户ID
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderJson(new ResultJson(Code.F_1, "请登录"));
			return;
		}
		long userId = user.getLong("id");

		// 获取查询模式
		int mode = getParam(0, -1);

		// 获取分页页数
		int page = getParam(1, 1);

		// 获取数据
		Page<PrivateMessage> messagePage = null;
		try {
			messagePage = PrivateMessage.dao.findMessageByPage(userId, mode, page, MESSAGE_PAGE_SIZE);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("get messagePage exception", e);
			renderJson(new ResultJson(Code.F_100000, "获取失败"));
			return;
		}
		if (messagePage == null) {
			log.info("messagePage is null [userId:" + userId + ",mode:" + mode + ",page:" + page + "]");
			renderJson(new ResultJson(Code.F_100000, "获取失败"));
			return;
		}

		// 日期格式化
		for (PrivateMessage message : messagePage.getList()) {
			String format = FormatUtil.MyDateFormat("yyyy-MM-dd HH:mm:ss", message.getLong("send_at"));
			message.set("send_at", format);
		}

		log.info("page:[pageNumber:" + messagePage.getPageNumber() + ",pageSize:" + messagePage.getPageSize() + ",totalPage:"
				+ messagePage.getTotalPage() + ",totalRow:" + messagePage.getTotalRow() + "]");
		map.put("messageList", messagePage.getList());
		map.put("pageNumber", messagePage.getPageNumber());
		map.put("pageSize", messagePage.getPageSize());
		map.put("totalPage", messagePage.getTotalPage());
		map.put("totalRow", messagePage.getTotalRow());

		renderJson(new ResultJson(Code.S_200, map, "获取成功"));
	}

	/**
	 * 删除会话
	 * 
	 * @author XuXD
	 * @date 2015-11-17
	 */
	@Before(LoginInterceptor.class)
	public void delPrivateMessage() {
		// 获取会话id
		long id = getParam(0, -1);
		
		// 校验
		if (id < 1) {
			renderNull();
			return;
		}
		
		try {
			// 标记删除
			UserLettleDialog.dao.findById(id).set("is_delete", 1).set("delete_at", System.currentTimeMillis()).update();
			renderJson(new ResultJson(Code.S_200));
		} catch (Exception e) {
			log.error("删除会话异常", e);
			renderJson(new ResultJson(Code.F_100000, "删除会话失败"));
		}
	}

	/**
	 * 系统消息
	 * 
	 * @author XuXD
	 * @date 2015-11-11
	 */
	@Before(LoginInterceptor.class)
	public void mySystemMessage() {
		long userId = UserUtil.getUser(getSession()).getLong("id");
		try {
			setAttr("privateMessageCount", UserLettleContent.dao.getCount(userId));
			setAttr("systemMessageCount", SystemMessage.dao.getCount(userId));
		} catch (Exception e) {
			log.error("跳转系统消息页面异常", e);
			setAttr("privateMessageCount", 0);
			setAttr("systemMessageCount", 0);
		}
		render("systemMessage.ftl");
	}

	/**
	 * 查看系统消息
	 * 
	 * @author XuXD
	 * @date 2015-11-12
	 */
	@Before(LoginInterceptor.class)
	public void getSystemMessage() {
		// 获取登录用户ID
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderJson(new ResultJson(Code.F_1, "请登录"));
			return;
		}
		long userId = user.getLong("id");

		// 获取查询模式
		int mode = getParam(0, -1);

		// 获取分页页数
		int page = getParam(1, 1);

		try {
			// 获取数据
			Page<SystemMessage> messagePage = SystemMessage.dao.findMessageByPage(userId, mode, page, MESSAGE_PAGE_SIZE);

			// 日期格式化
			for (SystemMessage message : messagePage.getList()) {
				message.set("send_at", FormatUtil.MyCommentDateFormat(message.getLong("send_at")));
			}

			log.info("page:[pageNumber:" + messagePage.getPageNumber() + ",pageSize:" + messagePage.getPageSize() + ",totalPage:"
					+ messagePage.getTotalPage() + ",totalRow:" + messagePage.getTotalRow() + "]");

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("messageList", messagePage.getList());
			map.put("pageNumber", messagePage.getPageNumber());
			map.put("pageSize", messagePage.getPageSize());
			map.put("totalPage", messagePage.getTotalPage());
			map.put("totalRow", messagePage.getTotalRow());

			renderJson(new ResultJson(Code.S_200, map, "加载成功"));
			return;
		} catch (Exception e) {
			log.error("查看系统消息异常", e);
		}
		renderJson(new ResultJson(Code.F_100000, "加载失败"));
	}

	/**
	 * 读取系统消息
	 * 
	 * @author XuXD
	 * @date 2015-11-12
	 */
	@Before(LoginInterceptor.class)
	public void readSystemMessage() {
		// 获取登录用户ID
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderJson(new ResultJson(Code.F_1, "请登录"));
			return;
		}
		long userId = user.getLong("id");

		// 获取消息id
		long id = getParam(0, -1);

		try {
			// 标记已读
			SystemMessage.dao.findById(id).set("is_read", 1).update();

			// 获取未读消息数量
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("privateMessageCount", UserLettleContent.dao.getCount(userId));
			map.put("systemMessageCount", SystemMessage.dao.getCount(userId));

			renderJson(new ResultJson(Code.S_200, map, "success"));
			return;
		} catch (Exception e) {
			log.error("系统消息标记已读异常", e);
		}
		renderJson(new ResultJson(Code.F_100000, "标记已读失败"));
	}

	/**
	 * 删除系统消息
	 * 
	 * @author XuXD
	 * @date 2015-11-12
	 */
	@Before(LoginInterceptor.class)
	public void delSystemMessage() {
		// 获取登录用户ID
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderJson(new ResultJson(Code.F_1, "请登录"));
			return;
		}
		long userId = user.getLong("id");

		// 获取消息id
		long id = getParam(0, -1);

		try {
			// 标记删除
			SystemMessage.dao.findById(id).set("is_delete", 1).update();

			// 获取未读消息数量
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("privateMessageCount", UserLettleContent.dao.getCount(userId));
			map.put("systemMessageCount", SystemMessage.dao.getCount(userId));

			renderJson(new ResultJson(Code.S_200, map, "success"));
			return;
		} catch (Exception e) {
			log.error("删除系统消息异常", e);
		}
		renderJson(new ResultJson(Code.F_100000, "删除系统消息失败"));
	}

	/**
	 * 去绑定
	 * 
	 * @author XuXD
	 */
	@Before(LoginInterceptor.class)
	public void bind() {
		String back_url = getRequest().getParameter("back_url");
		// setSessionAttr("back_url",back_url);
		User user = UserUtil.getUser(getSession());
		if (user == null) {
			redirect("/login");
			return;
		}
		setAttr("back_url", back_url);
		render("bind.ftl");
	}

	/**
	 * 提交绑定
	 * 
	 * @author XuXD
	 * @date 2015-10-19
	 */
	@Before(LoginInterceptor.class)
	public void saveBind() {
		// 获取登录用户ID
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			redirect("/login");
			renderJson(new ResultJson(Code.F_1, "请登录"));
			return;
		}

		// 获取参数
		String phone = getParam("bind_phone", "");
		String password = getParam("bind_password", "");
		String repassword = getParam("bind_repassword", "");
		String back_url = getParam("back_url", "/");
		log.info("[phone:" + phone + ",password:" + password + ",repassword:" + repassword + "]");

		// 数据校验
		if (StrKit.isBlank(phone) && !CheckNum.isPhone(phone)) {
			renderJson(new ResultJson(-2L, "手机号格式不正确"));
			return;
		}
		if (StrKit.isBlank(password)) {
			renderJson(new ResultJson(-3L, "密码不能为空"));
			return;
		}
		if (StrKit.isBlank(repassword)) {
			renderJson(new ResultJson(-4L, "确认密码不能为空"));
			return;
		}
		if (!password.equals(repassword)) {
			renderJson(new ResultJson(-5L, "两次密码不一致"));
			return;
		}
		if (User.dao.isRegister(phone)) {
			renderJson(new ResultJson(-6L, "手机号已注册,请更换手机号"));
			return;
		}

		// 保存数据
		try {
			user.set("phone", StringEscapeUtils.escapeSql(phone));
			user.set("password", PasswordUtil.encryptPassword(password));
			boolean result = user.update();
			if (result) {
				// 更新session
				UserUtil.setUser(user, getSession());
				// renderJson(new ResultJson(Code.S_200, "绑定成功"));
				if (!StringUtil.isNullOrEmpty(back_url)) {
					log.info("登录提交时，URL参数中带有回调地址，跳转到回调页面。back_url：" + back_url);
					renderJson(new ResultJson(Code.S_200, "绑定成功"));
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("提交绑定异常", e);
		}
		renderJson(new ResultJson(Code.F_100000, "绑定失败"));
	}

	/**
	 * 去关联账号
	 * 
	 * @author XuXD
	 * @date 2015-10-19
	 */
	@Before(LoginInterceptor.class)
	public void relate() {
		String back_url = getRequest().getParameter("back_url");
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			redirect("/login");
			return;
		}
		setAttr("back_url", back_url);
		render("relate.ftl");
	}

	/**
	 * 保存关联账号
	 * 
	 * @author XuXD
	 * @date 2015-10-19
	 */
	@Before(LoginInterceptor.class)
	public void saveRelate() {
		// 获取登录用户ID
		OpenUser openUser = OpenUserUtil.getOpenUser(getSession());
		User user = UserUtil.getUser(getSession());
		if (null == openUser || null == user) {
			redirect("/login");
			renderJson(new ResultJson(Code.F_1, "请登录"));
			return;
		}

		// 获取参数
		String phone = getParam("relate_phone", "");
		String password = getParam("relate_password", "");
		log.info("[phone:" + phone + ",password:" + password + "]");

		// 数据校验
		if (StrKit.isBlank(phone) && !CheckNum.isPhone(phone) && !CheckNum.isEmail(phone)) {
			renderJson(new ResultJson(-2L, "账户不正确"));
			return;
		}
		if (StrKit.isBlank(password)) {
			renderJson(new ResultJson(-3L, "密码不能为空"));
			return;
		}
		User newUser = User.dao.finduser(phone);
		if (newUser == null) {
			renderJson(new ResultJson(-4L, "账户不存在"));
			return;
		}
		try {
			// 验证账号
			List<User> userList = User.dao.findUser(phone, password, 0);
			if (userList == null || userList.size() < 1) {
				renderJson(new ResultJson(Code.F_1, "账户密码错误"));
				return;
			}
			user = userList.get(0);

			// 验证账号未绑定
			OpenUserRelation isRelation = OpenUserRelation.dao.findByUserId(user.getLong("id"), openUser.getInt("type"));
			if (isRelation != null) {
				renderJson(new ResultJson(-6L, "此账户已经绑定过了"));
				return;
			}

			// 保存关联
			OpenUserRelation relation = OpenUserRelation.dao.findByOpenId(openUser.getLong("id"), openUser.getInt("type"));
			relation.set("user_id", user.getLong("id"));
			if (relation.update()) {
				UserUtil.setUser(user, getSession());
				renderJson(new ResultJson(Code.S_200, "关联成功"));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("提交关联异常", e);
		}
		renderJson(new ResultJson(Code.F_100000, "关联失败"));
	}

	/**
	 * 收货地址
	 * 
	 * @author XuXD
	 * @date 2015-11-5
	 */
	@Before(LoginInterceptor.class)
	public void receiveAddress() {
		try {
			Long userId = UserUtil.getUser(getSession()).getLong("id");

			// 默认收货地址
			List<ReceiveAddress> defaultAddressList = ReceiveAddress.dao.findListByUserIdAndDefault(userId, 1);
			setAttr("defaultAddressList", defaultAddressList);
			// 其余收货地址
			List<ReceiveAddress> receiveAddressList = ReceiveAddress.dao.findListByUserIdAndDefault(userId, 0);
			setAttr("receiveAddressList", receiveAddressList);
			// 城市
			List<Provinces> provincesList = Provinces.dao.findAll();
			setAttr("provincesList", provincesList);

			render("receiveAddress.ftl");
			return;
		} catch (Exception e) {
			log.error("显示收货地址异常", e);
		}
		renderError(403);
	}

	// 个人中心安全中心
	@Before(LoginInterceptor.class)
	public void security() {
		User user = UserUtil.getUser(getSession());
		user = User.dao.findById(user.get("id"));
		//更新session
		UserUtil.setUser(user, getSession());
		setAttr("user", user);

		UserPayPwd payPwd = UserPayPwd.dao.findUserPayPwdByUserId(user.getLong("id"));
		if(payPwd == null){
			payPwd = new UserPayPwd();
		}
		setAttr("payPwd", payPwd);
		
		//获取安全级别
		int security_level = 0;
		String phone = user.get("phone");
		if(phone != null){
			if(phone.length() == 11){
				user.put("encrypt_phone", new StringBuffer(phone.substring(0, 3)).append("****").append(phone.substring(7)));
			}
			security_level++;
		}
		
		if(user.get("password") != null){
			security_level++;
		}
		
		if(user.get("email") != null){
			security_level++;
		}
		
		if(payPwd.get("pay_pwd") != null){
			security_level++;
		}
		
		if(security_level < 2){
			setAttr("c_security_level", "低");
		}else if(security_level < 4){
			setAttr("c_security_level", "中");
		}else if(security_level == 4){
			setAttr("c_security_level", "高");
		}
		
		//按照百分百进行换算，每设置一个为25%
		setAttr("security_level", security_level * 25);
		
		render("security.ftl");
	}

	// 更换手机验证
	public void changePhone() {
		setAttr("oldPhone", getPara("phone"));
		render("changePhone.ftl");
	}

	// 验证手机是否绑定
	public void verifyPhone() {
		boolean b = User.dao.verifyPhone(getPara("phone"));
		if (b) {
			renderText("true");
		} else {
			renderText("false");
		}
	}

	// 修改手机号
	public void resetPhone() {
		User user = User.dao.findByUsername(getPara("oldphone"));
		user.set("phone", getPara("phone"));
		user.set("username", getPara("phone"));
		user.update();
		renderText("绑定手机号修改成功，请使用新手机号登陆");
	}

	// 设置支付密码
	public void setPayPwd() {
		String phone = getPara("phone");
		String payPwd = getPara("payPwd");
		String surePwd = getPara("surePwd");
		String code = getPara("code");
		
		Integer d_code = getSessionAttr(phone);
		if(!code.equals(d_code == null ? null : String.valueOf(d_code))){
			renderText("codeError");
			return;
		}
		
		if(!payPwd.equals(surePwd)){
			renderText("pwdNotEq");
			return;
		}
		
		User user = UserUtil.getUser(getSession());
		
		//绑定用户手机
		if(user.getStr("phone") == null){
			User n_user = User.dao.findById(user.get("id"));
			n_user.set("phone", phone).update();
			UserUtil.setUser(n_user, getSession());
		}
		
		//绑定支付密码
		Long user_id = user.getLong("id");
		UserPayPwd userPayPwd = UserPayPwd.dao.findUserPayPwdByUserId(user_id);
		if (userPayPwd == null) {
			log.info("用户" + getPara("phone") + "正在设置支付密码");
			userPayPwd = new UserPayPwd();
			userPayPwd.set("user_id", user_id);
			userPayPwd.set("phone", phone);
			userPayPwd.set("pay_pwd", PasswordUtil.encryptPassword(payPwd));
			userPayPwd.set("create_at", System.currentTimeMillis());
			userPayPwd.save();
			log.info("用户" + user_id + "支付密码设置成功");
			renderText("true");
			return;
		} else {
			log.info("用户"+ getPara("phone") + "设置密码失败，该用户支付密码已存在");
			renderText("false");
			return;
		}
	}

	/**
	 * 修改支付密码
	 */
	public void resetPayPwd() {
		User user = UserUtil.getUser(getSession());
		log.info("正在修改用户名为：" + user.get("username") + "的支付密码");
		String currentpayPwd = getPara("currentpayPwd");
		String payPwd = getPara("payPwd");
		String surePwd = getPara("surePwd");
		try {
			Long user_id = user.getLong("id");
			UserPayPwd userPayPwd = UserPayPwd.dao.findUserPayPwdByUserId(user_id);
			if (userPayPwd == null) {
				renderText("no_exist");
				log.info("修改用户名为：" + user.get("username") + "的支付密码失败");
				return;
			} 
			//判断当前支付密码是否正确
			if(!PasswordUtil.encryptPassword(currentpayPwd).equals(userPayPwd.get("pay_pwd"))){
				renderText("codeError");
				return;
			}
			
			//支付密码与确认支付密码是否相等
			if(!payPwd.equals(surePwd)){
				renderText("pwdNotEq");
				return;
			}
			
			userPayPwd.set("pay_pwd", PasswordUtil.encryptPassword(payPwd));
			userPayPwd.set("update_at", System.currentTimeMillis());
			userPayPwd.update();
			log.info("用户" + user_id + "支付密码设置成功");
			renderText("true");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("修改用户名为：" + user.get("username") + "的支付密码失败");
			renderText("false");
		}
	}

	/***
	 * 验证昵称
	 */
	@Before(LoginInterceptor.class)
	public void verifyNickname() {
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderJson(new ResultJson(Code.F_1, "请登录"));
			return;
		}

		String nickname = getPara("nickname");
		if (nickname == null) {
			renderJson(new ResultJson(Code.F_1, "请输入昵称"));
			return;
		}

		List<User> userList = User.dao.findSameNickname(nickname);
		if (userList.size() == 0 || nickname.equals(user.get("nickname"))) {
			renderJson(new ResultJson(Code.S_200, "该昵称可以使用"));
			return;
		} else {
			renderJson(new ResultJson(Code.F_1, "该昵称已被使用,请重新填写"));
			return;
		}
	}
}
