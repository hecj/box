package com.boxamazing.service.qo.model;

import java.util.List;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 查询关联的字段选项
 */
@SuppressWarnings("serial")
public class Qo extends Model<Qo> {
	public static final Qo dao = new Qo();

	/**
	 * 基本分页方法
	 */
	public Page<Qo> _page(int pn, int ps) {
		return dao.paginate(pn, ps, "select *", "from qo order by id desc");
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
		return Db.update("delete from qo where id in (" + str + ")", ids);
	}

	/**
	 * 通过qid列表查询QoList
	 * 
	 * @param qs
	 */
	public static List<Qo> findQoListByQids(String qs) {
		return Qo.dao.find("select * from qo where qid in (" + qs + ")");
	}

	/**
	 * 通过qid查询QoList
	 * 
	 * @param qid
	 * @return
	 */
	public static List<Qo> findQoListByQid(Long qid) {
		return Qo.dao.find("select * from qo where qid=?", qid);
	}

	/**
	 * 通过qid,f查找Qo List
	 */
	public static List<Qo> findQoListByQidF(Long qid, String f) {
		return Qo.dao.find("select * from qo where qid=? and f=?", qid, f);
	}

}