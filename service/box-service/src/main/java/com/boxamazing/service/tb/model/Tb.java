package com.boxamazing.service.tb.model;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 模型
 */
@SuppressWarnings("serial")
public class Tb extends Model<Tb> {
	public static final Tb dao = new Tb();

	/**
	 * 基本分页方法
	 */
	public Page<Tb> _page(int pn, int ps) {
		return dao.paginate(pn, ps, "select *", "from tb order by id desc");
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
		return Db.update("delete from tb where id in (" + str + ")", ids);
	}
}