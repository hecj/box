package com.boxamazing.service.pay.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 用户资金流水
 */
public class Fundrecord extends Model<Fundrecord> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2075150239747586540L;

	public static final Fundrecord dao = new Fundrecord();

	private static final Log log = LogFactory.getLog(Fundrecord.class);

	// 根据页数及每页大小显示资金流水数量
	public Page<Fundrecord> findByPage(int pageNumber, int pageSize,
			Long user_id) {

		String selectSQL = "select  *  ";
		String condtion = " from fundrecord where operate_type = 1 and user_id = "
				+ user_id + " order by trad_at desc ";

		log.info(" 查询资金流水（充值记录） condition{} : " + condtion);
		try {
			Page<Fundrecord> list = dao.paginate(pageNumber, pageSize,
					selectSQL, condtion);
			return list;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 查询某用户的资金流水
	 * 
	 * @author YJ
	 */
	public Page<Fundrecord> findByUserPage(int pageNumber, int pageSize,
			Long userid) {

		String selectSQL = "select  *  ";
		String condtion = " from fundrecord where user_id = " + userid
				+ " order by trad_at desc , id desc";

		log.info(" 查询资金流水 condition{} : " + condtion);
		try {
			Page<Fundrecord> list = Fundrecord.dao.paginate(pageNumber,
					pageSize, selectSQL, condtion);
			return list;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

}
