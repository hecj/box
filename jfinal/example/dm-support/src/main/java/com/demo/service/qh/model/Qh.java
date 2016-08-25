package com.demo.service.qh.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import cn.peon.core.kit.StringUtil;
import com.demo.util.ArrayUtil;

 /**
 *查询历史
 */
@SuppressWarnings("serial")
public class Qh extends Model<Qh> {
	public static final Qh dao = new Qh();
	 /**
	 *基本分页方法
	 */
	public Page<Qh> _page(int pn,int ps) {
		return dao.paginate(pn, ps, "select *", "from qh order by id desc");
	}
	
	/**根据id删除多个对象
	 * @param ids
	 * @return
	 */
	public int deleteByIds(String... ids) {
		String[] ay = ArrayUtil.getPrePareArray(ids.length);
		String str = StringUtil.join(ay,",");
		return Db.update("delete from qh where id in ("+str+")", ids);
	}
}