package com.boxamazing.service.pay.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.util.SqlUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 充值成功订单表
 */
public class RechargeDetailRecord extends Model<RechargeDetailRecord>{
	
	/**
	 * 
	 */
	private static final Log log = LogFactory.getLog(WithdrawalsDetail.class);
	
	private static final long serialVersionUID = 7858630616447325175L;
	public static final RechargeDetailRecord dao = new RechargeDetailRecord();

	public RechargeDetailRecord findByOrdernum(String param) {
		String sql = " select * from recharge_detail_record where order_num = ?";
		List<RechargeDetailRecord> list = dao.find(sql, new Object[]{param});
		if(list.size() == 0){
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 根据充值时间，用户id查询此时间之前的充值成功记录
	 * by hecj
	 */
	public Page<RechargeDetailRecord> findRechargeDetailRecordByUserIdAndCreateAt(Map<String, Object> params){
		
		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());

		Long user_id = (Long)params.get("user_id");
		Long create_at = (Long)params.get("create_at");
		
		String selectSQL = "select rdr.* ";

		StringBuffer condition = new StringBuffer(" from recharge_detail_record rdr left join recharge_detail_pre rdp on (rdr.order_num=rdp.order_num) where 1=1 ");
		condition.append(" and rdp.user_id = "+user_id);
		condition.append(" and rdr.create_at < "+create_at);
		
		List<Object> sqlParams = new ArrayList<Object>();

		condition.append(" order by rdr.id desc ");

		log.info(" 查询充值成功列表 condition{} : " + condition.toString());
		try {
			Page<RechargeDetailRecord> list = dao.paginate(pageNumber, pageSize, selectSQL, condition.toString(), SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error("status{},pageObj{},sizeObj{}:" + pageObj + "," + sizeObj);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
