package com.boxamazing.service.q.model;

import java.util.List;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 通用查询
 */
@SuppressWarnings("serial")
public class Q extends Model<Q> {
	public static final Q dao = new Q();

	/**
	 * 基本分页方法
	 */
	public Page<Q> _page(int pn, int ps) {
		return dao.paginate(pn, ps, "select *", "from q order by id desc");
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
		return Db.update("delete from q where id in (" + str + ")", ids);
	}

	/**
	 * 
	 * 通过 type, stat, qs 查询 List<Q>
	 * 
	 * @param type
	 * @param stat
	 * @param qs
	 * @param orderby
	 * @param sort
	 * @return
	 */
	public static List<Q> findQListByTypeStatIds(int type, int stat, String qs,
			String orderby, String sort) {
		return Q.dao
				.find("select * from q where type=? and stat=? and id in(?) order by ? ?",
						type, stat, qs, orderby, sort);
	}

	/**
	 * 通过type, stat 查询List<Q>
	 * 
	 * @param type
	 * @param stat
	 * @return
	 */
	public static List<Q> findQListByTypeStat(int type, int stat) {
		return Q.dao
				.find("select * from q where type=? and stat=?", type, stat);
	}
	
	/**
	 * 查找所有的QList
	 * @return
	 */
	public static List<Q> findAllQList(){
		return Q.dao.find("select * from q");
	}

}