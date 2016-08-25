package com.boxamazing.service.dbs.model;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;


/**
 * 数据源管理
 */
@SuppressWarnings("serial")
public class Dbs extends Model<Dbs> {
	public static final Dbs dao = new Dbs();

	/**
	 * 基本分页方法
	 */
	public Page<Dbs> _page(int pn, int ps) {
		return dao.paginate(pn, ps, "select *", "from dbs order by id desc");
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
		return Db.update("delete from dbs where id in (" + str + ")", ids);
	}
}