package com.boxamazing.service.ol.model;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;


/**
 * 操作日志
 */
@SuppressWarnings("serial")
public class Ol extends Model<Ol> {
	public static final Ol dao = new Ol();

	/**
	 * 基本分页方法
	 */
	public Page<Ol> _page(int pn, int ps) {
		return dao.paginate(pn, ps, "select *", "from ol order by id desc");
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
		return Db.update("delete from ol where id in (" + str + ")", ids);
	}
}