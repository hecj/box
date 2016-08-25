package com.demo.service.tbc.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import cn.peon.core.kit.StringUtil;
import com.demo.util.ArrayUtil;

 /**
 *模型字段
 */
@SuppressWarnings("serial")
public class Tbc extends Model<Tbc> {
	public static final Tbc dao = new Tbc();
	 /**
	 *基本分页方法
	 */
	public Page<Tbc> _page(int pn,int ps) {
		return dao.paginate(pn, ps, "select *", "from tbc order by id desc");
	}
	
	/**根据id删除多个对象
	 * @param ids
	 * @return
	 */
	public int deleteByIds(String... ids) {
		String[] ay = ArrayUtil.getPrePareArray(ids.length);
		String str = StringUtil.join(ay,",");
		return Db.update("delete from tbc where id in ("+str+")", ids);
	}
}