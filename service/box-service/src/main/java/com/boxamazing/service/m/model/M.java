package com.boxamazing.service.m.model;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;


 /**
 *菜单
 */
@SuppressWarnings("serial")
public class M extends Model<M> {
	public static final M dao = new M();
	 /**
	 *基本分页方法
	 */
	public Page<M> _page(int pn,int ps) {
		return dao.paginate(pn, ps, "select *", "from m order by id desc");
	}
	
	/**根据id删除多个对象
	 * @param ids
	 * @return
	 */
	public int deleteByIds(String... ids) {
		String[] ay = ArrayUtil.getPrePareArray(ids.length);
		String str = StringUtil.join(ay, ",");
		return Db.update("delete from m where id in ("+str+")", ids);
	}
}