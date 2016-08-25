package com.demo.service.qo.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import cn.peon.core.kit.StringUtil;
import com.demo.util.ArrayUtil;

 /**
 *查询关联的字段选项
 */
@SuppressWarnings("serial")
public class Qo extends Model<Qo> {
	public static final Qo dao = new Qo();
	 /**
	 *基本分页方法
	 */
	public Page<Qo> _page(int pn,int ps) {
		return dao.paginate(pn, ps, "select *", "from qo order by id desc");
	}
	
	/**根据id删除多个对象
	 * @param ids
	 * @return
	 */
	public int deleteByIds(String... ids) {
		String[] ay = ArrayUtil.getPrePareArray(ids.length);
		String str = StringUtil.join(ay,",");
		return Db.update("delete from qo where id in ("+str+")", ids);
	}
}