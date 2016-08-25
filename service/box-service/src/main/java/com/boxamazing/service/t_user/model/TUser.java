package com.boxamazing.service.t_user.model;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 *
 */
@SuppressWarnings("serial")
public class TUser extends Model<TUser> {
	public static final TUser dao = new TUser();

	/**
	 * 基本分页方法
	 */
	public Page<TUser> _page(int pn, int ps) {
		return dao.paginate(pn, ps, "select *", "from t_user order by id desc");
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
		return Db.update("delete from t_user where id in (" + str + ")", ids);
	}
}