package com.boxamazing.service.pay.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.util.FormatUtil;
import com.boxamazing.service.util.SqlUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 充值订单表（预订单）
 */
public class RechargeDetailPre extends Model<RechargeDetailPre>  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -12853309298351045L;
	public static final RechargeDetailPre dao = new RechargeDetailPre();
	private static final Log log = LogFactory.getLog(RechargeDetailPre.class);


	/**
	 * 根据充值预订单号查询充值预订单
	 * @param order_num
	 * @version liyanlong
	 */
	public RechargeDetailPre findByOrderNum(String order_num) {
		log.info("[order_num:" + order_num + "]");
		String sql = "select * from recharge_detail_pre where order_num=? ";
		try {
			List<RechargeDetailPre> list = dao.find(sql, order_num);
			if(list != null && list.size() > 0){
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过order_num获取充值预订单订单异常", e);
		}
		return null;
	}
	
	public Page<RechargeDetailPre> paginateByParams(Map<String, Object> params){
		String usernameORuid = (String)params.get("usernameORuid");
		Float amount = params.get("amount") == null ? null : (Float)params.get("amount");
		String regTimeS = (String)params.get("regTimeS");
		String regTimeE = (String)params.get("regTimeE");
		Integer payType = params.get("payType") == null ? null : (Integer)params.get("payType");
		Integer payStatus = params.get("payStatus") == null ? null : (Integer)params.get("payStatus");
		int pageNumber = ((Long)params.get("page")).intValue();
		int pageSize = (Integer)params.get("size");

		String selectSQL = "select rdp.*,u.id as u_id,u.phone as u_phone, u.nickname as u_nickname";

		StringBuffer condition = new StringBuffer(" from recharge_detail_pre rdp left join user u on (rdp.user_id=u.id) where rdp.isdelete <> 1 ");

		List<Object> sqlParams = new ArrayList<Object>();

		if (!StringUtil.isNullOrEmpty(usernameORuid)) {
			condition.append(" and (u.phone = ? or u.id = ?)");
			sqlParams.add(usernameORuid);
			sqlParams.add(usernameORuid);
		}
		
		if (amount != null) {
			condition.append(" and rdp.amount = ? ");
			sqlParams.add(amount);
		}
		
		
		if (!StringUtil.isNullOrEmpty(regTimeS) && !StringUtil.isNull(regTimeE)) {
			condition.append(" and rdp.create_at between ? and ? ");
			sqlParams.add(FormatUtil.strDateToLong(regTimeS + " 00:00:00"));
			sqlParams.add(FormatUtil.strDateToLong(regTimeE + " 59:59:59"));
		}
		
		if (payType != null) {
			condition.append(" and rdp.type = ? ");
			sqlParams.add(payType);
		}
		
		if (payStatus != null) {
			condition.append(" and rdp.status = ? ");
			sqlParams.add(payStatus);
		}

		condition.append(" order by rdp.create_at desc ");

		log.info(" 查询充值订单列表 condition{} : " + condition.toString());
		try {
			Page<RechargeDetailPre> list = dao.paginate(pageNumber, pageSize, selectSQL, condition.toString(), SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;  
	}

	public RechargeDetailPre findByIdWithUser(long id) {
		String sql = "select rdp.*,u.id as u_id,u.phone as u_phone, u.nickname as u_nickname from recharge_detail_pre rdp left join user u on (rdp.user_id=u.id) ";
		List<RechargeDetailPre> result = dao.find(sql);
		return result.size() > 0 ? result.get(0) : null;
	}
	
}
