package com.boxamazing.service.user.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.code.Areas;
import com.boxamazing.service.code.Cities;
import com.boxamazing.service.code.Provinces;
import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.PasswordUtil;
import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.fans.model.Fans;
import com.boxamazing.service.system_message.model.SystemMessage;
import com.boxamazing.service.util.SqlUtil;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * User Model. Created by pchome on 2015/7/28.
 */
@SuppressWarnings("serial")
public class User extends Model<User> {
	public static final User dao = new User();
	private static final Log log = LogFactory.getLog(User.class);

	/**
	 * 获取昵称
	 * @author XuXD
	 * @date 2015-11-24
	 * @return
	 */
	public String getNickName() {
		try {
			if (StrKit.notBlank(getStr("nickname")))
				return getStr("nickname");
		} catch (Exception e) {
			log.error("获取昵称异常", e);
		}
		return "";
	}
	
	/**
	 * 获取头像
	 * @author XuXD
	 * @date 2015-11-17
	 * @return
	 */
	public String getPicture() {
		try {
			if (StrKit.notBlank(getStr("picture")))
				return getStr("picture");
		} catch (Exception e) {
			log.error("获取头像异常", e);
		}
		return "";
	}
	
	/**
	 * 获取粉丝数
	 * 
	 * @author XuXD
	 * @date 2015-11-14
	 * @return
	 */
	public long getFansNum() {
		try {
			long id = getLong("id");
			return Fans.dao.getFansCountByPromoder(id);
		} catch (Exception e) {
			log.error("获取粉丝数异常", e);
		}
		return 0;
	}

	/**
	 * 获取未读消息总数
	 * 
	 * @author XuXD
	 * @date 2015-11-14
	 * @return
	 */
	public long getMessageCount() {
		try {
			long id = getLong("id");
			long count = UserLettleContent.dao.getCount(id) + SystemMessage.dao.getCount(id);
			return count > 99 ? 99 : count;
		} catch (Exception e) {
			log.error("获取未读消息总数异常", e);
		}
		return 0;
	}

	/**
	 * 判断是否有手机号
	 * 
	 * @author XuXD
	 * @date 2015-11-14
	 * @return
	 */
	public boolean isBind() {
		try {
			String phone = get("phone");
			String password = get("password");
			if (StrKit.notBlank(phone) && StrKit.notBlank(password))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("判断是否有手机号异常", e);
		}
		return false;
	}

	/**
	 * 判断手机号是否注册
	 * 
	 * @author XuXD
	 * @date 2015-10-19
	 * @param phone
	 * @return
	 */
	public boolean isRegister(String phone) {
		log.info("[phone:" + phone + "]");
		try {
			List<User> users = dao.find("select id from user where phone=?", phone);
			return users.size() > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("判断手机号是否注册异常", e);
		}
		return true;
	}

	/**
	 * 获取省份名称 by YJ
	 */
	public String getProvincesName() {
		try {
			if (this.getStr("province") == null) {
				return "";
			}
			return Provinces.dao.findCacheByProvinceId(this.getStr("province")).getStr("province");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取市名 by YJ
	 */
	public String getCityNames() {
		try {
			if (this.getStr("province") == null || this.getStr("city") == null) {
				return "";
			}
			Provinces provin = Provinces.dao.findCacheByProvinceId(this.getStr("province"));
			int type = provin.getInt("type");
			if (type == 2) {
				return Areas.dao.findAreasByCityid(this.getStr("city")).getStr("area");
			} else {
				return Cities.dao.findCacheByCityid(this.getStr("city")).getStr("city");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 基本分页方法
	 */
	public Page<User> _page(int pn, int ps) {
		return dao.paginate(pn, ps, "select *", "from user order by id desc");
	}

	/**
	 * 根据id删除多个对象
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteByIds(String... ids) {
		String[] ay = ArrayUtil.getPrePareArray(ids.length);
		String str = StringUtil.join(ay, ",");
		return Db.update("delete from user where username in (" + str + ")", ids);
	}

	/**
	 * 通过username,password查找用户
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public List<User> findUser(String phone, String password, int type) {
		log.info("查询用户信息  " + phone + password + type);
		if (type == 0) {
			return User.dao.find("select * from user where phone = ? and password = ?", phone, PasswordUtil.encryptPassword(password));
		} else {
			return User.dao.find("select * from user where email = ? and password = ?", phone, PasswordUtil.encryptPassword(password));
		}

	}

	/**
	 * 重置密码
	 */
	public void resetUserPwd(String username) {
		// System.out.println(userid);
		User user = findUser(username);
		user.set("password", PasswordUtil.encryptPassword(username));
		// User.dao.update("user",user);
		user.update();
	}

	/**
	 * 根据手机查找用户
	 * 
	 * @param phone
	 * @return
	 */
	public User findUser(String phone) {
		log.info("查找用户手机号为:" + phone + "的用户信息");
		User user = User.dao.findFirst("select * from user where phone = ?", phone);
		return user;
	}

	/**
	 * 根据手机/邮箱查找用户
	 */
	public User finduser(String username) {
		log.info("查找用户手机号/邮箱为:" + username + "的用户信息");
		User user = User.dao.findFirst("select * from user where phone = ? or email = ?", username, username);
		return user;
	}

	/**
	 * 根据username查找用户
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		return findUser(username);
	}

	/**
	 * 通过username检测用户是否完成了实名认证
	 * 
	 * @param username
	 * @return
	 */
	public boolean isCertify(String username) {
		List<User> users = User.dao.find("select certify from user where username = ?", username);
		if (users.size() > 0) {
			int certify = users.get(0).getInt("certify");
			if (certify == 2) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取认证状态码
	 * 
	 * @param username
	 * @return
	 */
	public int getCertifyStatus(String phone) {
		List<User> users = User.dao.find("select certify from user where phone = ?", phone);
		if (users.size() > 0) {
			int certify = users.get(0).getInt("certify");
			return certify;
		}
		return 0;
	}

	/**
	 * 通过certify来查找User
	 * 
	 * @param certifyStatus
	 *            By HECJ
	 */
	public Page<User> findByCertify(Map<String, Object> params) {

		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		Object certifyOjb = params.get("certify");
		String uPhone = (String)params.get("uPhone");
		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());

		String selectSQL = "select * ";

		StringBuffer condition = new StringBuffer(" from user u where 1=1");

		List<Object> sqlParams = new ArrayList<Object>();

		if (!StringUtil.isNullOrEmpty(certifyOjb.toString())) {
			condition.append(" and u.certify = ? ");
			sqlParams.add(Integer.parseInt(certifyOjb.toString()));
		}
		if (!StringUtil.isNullOrEmpty(uPhone)) {
			condition.append(" and u.phone = ? ");
			sqlParams.add(uPhone);
		}

		condition.append(" order by u.id desc ");

		log.info(" 查询实名状态用户列表 condition{} : " + condition.toString());
		try {
			Page<User> list = dao.paginate(pageNumber, pageSize, selectSQL, condition.toString(), SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error("status{},pageObj{},sizeObj{}:" + pageObj + "," + sizeObj);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 验证是否绑定该手机号
	 */
	public boolean verifyPhone(String para) {
		if (para.equals(User.dao.findByUsername(para).get("phone"))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 1。用户管理 2.分页<br>
	 * 后端查询用的<br>
	 * by HECJ
	 */
	public Page<Record> findRecordByCondition(Map<String, Object> params) {

		String status = (String) params.get("status");
		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		String uPhone = (String) params.get("uPhone");
		String email = (String) params.get("email");
		String certifyOjb = (String) params.get("certify");
		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());

		String selectSQL = "select u.id,u.nickname,u.status,u.balance,u.realname,u.sex,u.phone,u.email,u.idno,u.certify,u.create_at ";

		StringBuffer condition = new StringBuffer(" from user u where 1=1");

		List<Object> sqlParams = new ArrayList<Object>();

		if (!StringUtil.isNullOrEmpty(status)) {
			condition.append(" and u.status = ? ");
			sqlParams.add(Integer.parseInt(status));
		}

		if (!StringUtil.isNullOrEmpty(uPhone)) {
			condition.append(" and u.phone = ? ");
			sqlParams.add(uPhone);
		}

		if (!StringUtil.isNullOrEmpty(certifyOjb)) {
			condition.append(" and u.certify = ? ");
			sqlParams.add(Integer.parseInt(certifyOjb));
		}

		if (!StringUtil.isNullOrEmpty(email)) {
			condition.append(" and u.email = ? ");
			sqlParams.add(email);
		}

		condition.append(" order by u.id desc ");

		log.info(" 查询用户列表 condition{} : " + condition.toString());
		try {
			Page<Record> list = Db.paginate(pageNumber, pageSize, selectSQL, condition.toString(), SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error("status{},pageObj{},sizeObj{},uPhone{}:" + status + "," + pageObj + "," + sizeObj + "," + uPhone);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断是否绑定邮箱 by YJ
	 */
	public User findByEmail(String email) {
		log.info("查询手机号或者邮箱为：" + email + "的用户信息");
		User user = new User();
		try {
			user = User.dao.findFirst("select * from user where email = ?", email);
		} catch (Exception e) {
			log.error("email:" + email);
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * 判断昵称是否存在
	 * 
	 * @param nickname
	 * @return
	 */
	public List<User> findSameNickname(String nickname) {
		List<User> userList = new ArrayList<User>();
		log.info("昵称为：" + nickname);
		userList = User.dao.find("select * from user where nickname = ?", nickname);
		return userList;
	}

	/**
	 * 判断是否绑定邮箱 by YJ
	 */
	public User findByValidEmail(String valid_email) {
		log.info("查询手机号或者邮箱为：" + valid_email + "的用户信息");
		User user = new User();
		try {
			user = User.dao.findFirst("select * from user where valid_email = ?", valid_email);
		} catch (Exception e) {
			log.error("email:" + valid_email);
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * 判断身份证号是否重复  by YJ
	 * @param value
	 * @return
	 */
	public List<User> isSameIdno(String value){
		log.info("查询身份证号是否被使用:"+value);
		List<User> list = User.dao.find("select * from user where idno=?",value);
		return list;
		
	}
	
	public List<User> findByphone(String phone){
		List<User> list = dao.find("select * from user where phone=?", phone);
		return list;
	}
}
