package com.boxamazing.service.payPwd.model;

import java.util.List;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.user.model.User;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;


 /**
 *支付密码
 */
@SuppressWarnings("serial")
public class UserPayPwd extends Model<UserPayPwd> {
	public static final UserPayPwd dao = new UserPayPwd();
	 /**
	 *基本分页方法
	 */
	public Page<UserPayPwd> _page(int pn,int ps) {
		return dao.paginate(pn, ps, "select *", "from user_pay_pwd order by id desc");
	}
	
	/**根据id删除多个对象
	 * @param ids
	 * @return
	 */
	public int deleteByIds(String... ids) {
		String[] ay = ArrayUtil.getPrePareArray(ids.length);
		String str = StringUtil.join(ay, ",");
		return Db.update("delete from user_pay_pwd where id in ("+str+")", ids);
	}
	
	/**
	根据用户手机号查找支付密码
	 */
	public UserPayPwd findUserPayPwdByPhone(String phone){
		List<UserPayPwd> list = UserPayPwd.dao.find("select * from user_pay_pwd where phone = ?", phone);
		if(list.size() == 0){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	//判断是否设置支付密吗
	public boolean isSet(User user){
		Long user_id = user.getLong("id");
		if(UserPayPwd.dao.findUserPayPwdByUserId(user_id) == null){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 *根据user_id查询
	 */
	public UserPayPwd findUserPayPwdByUserId(Long user_id){
		List<UserPayPwd> list = UserPayPwd.dao.find("select * from user_pay_pwd where user_id = ? ", user_id);
		if(list.size() == 0){
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 根据用户id查找支付密码
	 */
	public UserPayPwd findByUserId(Long user_id) {
		List<UserPayPwd> list = UserPayPwd.dao.find("select * from user_pay_pwd where user_id=?", user_id);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
}