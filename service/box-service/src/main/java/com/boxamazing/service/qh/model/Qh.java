package com.boxamazing.service.qh.model;

import java.util.List;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;


/**
 * 查询历史
 */
@SuppressWarnings("serial")
public class Qh extends Model<Qh> {
	public static final Qh dao = new Qh();

	/**
	 * 基本分页方法
	 */
	public Page<Qh> _page(int pn, int ps) {
		return dao.paginate(pn, ps, "select *", "from qh order by id desc");
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
		return Db.update("delete from qh where id in (" + str + ")", ids);
	}

	/**
	 * 通过qid, pms, dt 查询在dt时间内的QhList
	 * @param id
	 * @param pms
	 * @param dt
	 * @return
	 */
	public static List<Qh> findQhByQidPmsDt(Long id, String pms, Long dt){
		return Qh.dao.find(
				"select * from qh where qid=? and pms=? and dt > FROM_UNIXTIME(?) "
				, id, pms, dt);
	}
}