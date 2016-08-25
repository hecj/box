package com.boxamazing.service.r.model;

import java.util.List;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 角色
 */
@SuppressWarnings("serial")
public class R extends Model<R> {
	public static final R dao = new R();

	/**
	 * 基本分页方法
	 */
	public Page<R> _page(int pn, int ps) {
		return dao.paginate(pn, ps, "select *", "from r order by id desc");
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
		return Db.update("delete from r where id in (" + str + ")", ids);
	}

	/**
	 * 通过rid查找Rid对象(只取第一个)
	 * 
	 * @param rid
	 * @return
	 */
	public static R findFirstRByRid(Object rid) {
		return R.dao.findFirst("select * from r where id=?", rid);
	}
	
	/**
	 * 查找所有的R
	 * @param orderby
	 * @param sort
	 * @return
	 */
	public static List<R> findAllR(String orderby, String sort) {
		return R.dao.find("select * from r order by ? ?", orderby, sort);

	}

}