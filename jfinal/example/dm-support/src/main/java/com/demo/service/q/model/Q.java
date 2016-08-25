package com.demo.service.q.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import cn.peon.core.kit.StringUtil;
import com.demo.util.ArrayUtil;

 /**
 *通用查询
 */
@SuppressWarnings("serial")
public class Q extends Model<Q> {
	public static final Q dao = new Q();
	 /**
	 *基本分页方法
	 */
	public Page<Q> _page(int pn,int ps) {
		return dao.paginate(pn, ps, "select *", "from q order by id desc");
	}
	
	/**根据id删除多个对象
	 * @param ids
	 * @return
	 */
	public int deleteByIds(String... ids) {
		String[] ay = ArrayUtil.getPrePareArray(ids.length);
		String str = StringUtil.join(ay,",");
		return Db.update("delete from q where id in ("+str+")", ids);
	}
}