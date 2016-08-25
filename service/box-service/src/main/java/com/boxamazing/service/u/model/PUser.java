package com.boxamazing.service.u.model;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.PasswordUtil;
import com.boxamazing.service.common.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * 后台用户
 * by HECJ
 */
@SuppressWarnings("serial")
public class PUser extends Model<PUser> {
	
	public static final PUser dao = new PUser();

	/**
	 * 基本分页方法
	 */
	public Page<PUser> _page(int pn, int ps) {
		return dao.paginate(pn, ps, "select *", "from puser order by id desc");
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
		return Db.update("delete from puser where id in (" + str + ")", ids);
	}

	/**
	 * 验证账号密码
	 * @param uid
	 * @param pwd
	 * @return
	 */
	public List<PUser> findUByUserNameAndPassword(String username, String password) {
		return PUser.dao.find("select * from puser where username=? and password=? " ,username, PasswordUtil.encryptPassword(password));
	}
}