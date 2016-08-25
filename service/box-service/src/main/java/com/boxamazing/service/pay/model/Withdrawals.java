package com.boxamazing.service.pay.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 提现信息表
 */
public class Withdrawals extends Model<Withdrawals>{

	private static final Log log = LogFactory.getLog(Withdrawals.class);
	
	public static final Withdrawals dao = new Withdrawals();
	
	public User getUser(){
		return User.dao.findById(get("user_id"));
	}
	
	/**
	 * 后台-提现列表
	 */
	public Page<Withdrawals> findWithdrawalsByParams(Map<String, Object> params) {

		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		String phone = (String) params.get("phone");
		String nickname = (String) params.get("nickname");
		BigDecimal amount = (BigDecimal) params.get("amount");
		Long user_id = (Long) params.get("user_id");
		Long start_time = (Long) params.get("start_time");
		Long end_time = (Long) params.get("end_time");
		Integer status = (Integer) params.get("status");
		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());

		String selectSQL = "select w.*,u.phone,u.nickname";

		StringBuffer condition = new StringBuffer(" from withdrawals w left join user u on (w.user_id = u.id) where 1=1");

		List<Object> sqlParams = new ArrayList<Object>();

		if (!StringUtil.isNullOrEmpty(phone)) {
			condition.append(" and u.phone = ? ");
			sqlParams.add(phone);
		}
		if (!StringUtil.isNullOrEmpty(nickname)) {
			condition.append(" and u.nickname = ? ");
			sqlParams.add(nickname);
		}
		if (user_id != null) {
			condition.append(" and w.user_id = ? ");
			sqlParams.add(user_id);
		}
		if (status != null) {
			condition.append(" and w.status = ? ");
			sqlParams.add(status);
		}
		if(start_time != null){
			condition.append(" and w.create_at >= ? ");
			sqlParams.add(start_time);
		}
		
		if(end_time != null){
			condition.append(" and w.create_at <= ? ");
			sqlParams.add(end_time);
		}
		
		if(amount != null){
			condition.append(" and w.amount = ? ");
			sqlParams.add(amount);
		}

		condition.append(" order by w.id desc ");
		
		log.info(" 后台-提现列表 condition{} : " + condition.toString());
		try {
			Page<Withdrawals> list = dao.paginate(pageNumber, pageSize, selectSQL, condition.toString(), SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error("status{},pageObj{},sizeObj{}:" + pageObj + "," + sizeObj);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据批次号和用户id查询
	 * by hecj
	 */
	public Withdrawals findWithdrawalsByBatchNoAndUserId(String batch_no,long user_id){
		
		StringBuffer querySQL = new StringBuffer("select wd.* from withdrawals wd where 1=1 ");
		querySQL.append(" and wd.batch_no = "+batch_no);
		querySQL.append(" and wd.user_id = "+user_id);
		querySQL.append(" order by wd.id desc ");
		log.info(" 根据批次号和用户id查询提现详细数据 condition{} : " + querySQL.toString());
		try {
			List<Withdrawals> list = dao.find(querySQL.toString());
			if(list.size() > 0){
				return list.get(0);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	//获取第一页
	public Page<Withdrawals> findByFirstPage(int pageSize, Long user_id) {
		return findByPage(1, pageSize, user_id);
	}
	
	/**
	 * 根据页数及每页大小显示提现记录
	 * @param pageSize
	 * @param user_id
	 * @return
	 */
	public Page<Withdrawals> findByPage(int pageNumber, int pageSize, Long user_id) {
		String selectSQL = "select  *  ";
		String condtion = " from withdrawals where user_id = "
				+ user_id + " order by create_at desc ";

		log.info(" 查询提现记录 condition{} : " + condtion);
		try {
			Page<Withdrawals> list = dao.paginate(pageNumber, pageSize,
					selectSQL, condtion);
			return list;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 查询提现超时的记录
	 * by hecj
	 */
	public List<Withdrawals> findWithdrawalsList(){
		
		StringBuffer querySQL = new StringBuffer("select wd.* from withdrawals wd where 1=1 ");
		querySQL.append(" order by wd.id desc ");
		log.info(" 根据批次号和用户id查询提现详细数据 condition{} : " + querySQL.toString());
		try {
			List<Withdrawals> list = dao.find(querySQL.toString());
			return list;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询提现中，提现超时的订单（提现超时时间定为30分钟）
	 * minute 分钟
	 * limit 限制多少条
	 */
	public List<Long> findWithdrawalsByInvalidAt(int minute,int limit){
		String querySQL = " select id from withdrawals w where status = 3 and (withdrawals_at+?*60*100) < UNIX_TIMESTAMP(now())*1000 order by id desc limit ? ";
		return Db.query(querySQL, new Object[]{minute,limit});
	}
}
