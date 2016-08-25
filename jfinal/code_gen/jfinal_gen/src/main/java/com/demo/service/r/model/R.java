package com.demo.service.r.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import cn.peon.core.kit.StringUtil;
import com.demo.util.ArrayUtil;

 /**
 *角色
 */
@SuppressWarnings("serial")
public class R extends Model<R> {
	public static final R dao = new R();
	 /**
	 *基本分页方法
	 */
	public Page<R> _page(int pn,int ps) {
		return dao.paginate(pn, ps, "select *", "from r order by id desc");
	}
	
	/**根据id删除多个对象
	 * @param ids
	 * @return
	 */
	public int deleteByIds(String... ids) {
		String[] ay = ArrayUtil.getPrePareArray(ids.length);
		String str = StringUtil.join(ay,",");
		return Db.update("delete from r where id in ("+str+")", ids);
	}
}