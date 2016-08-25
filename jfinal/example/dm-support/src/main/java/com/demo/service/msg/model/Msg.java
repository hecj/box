package com.demo.service.msg.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import cn.peon.core.kit.StringUtil;
import com.demo.util.ArrayUtil;

 /**
 *消息
 */
@SuppressWarnings("serial")
public class Msg extends Model<Msg> {
	public static final Msg dao = new Msg();
	 /**
	 *基本分页方法
	 */
	public Page<Msg> _page(int pn,int ps) {
		return dao.paginate(pn, ps, "select *", "from msg order by id desc");
	}
	
	/**根据id删除多个对象
	 * @param ids
	 * @return
	 */
	public int deleteByIds(String... ids) {
		String[] ay = ArrayUtil.getPrePareArray(ids.length);
		String str = StringUtil.join(ay,",");
		return Db.update("delete from msg where id in ("+str+")", ids);
	}
}