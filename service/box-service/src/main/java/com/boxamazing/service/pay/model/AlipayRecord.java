package com.boxamazing.service.pay.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.util.SqlUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 即时到帐接口通讯记录
 */
public class AlipayRecord extends Model<AlipayRecord>  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final AlipayRecord dao = new AlipayRecord();
	
	private static final Log log = LogFactory.getLog(AlipayRecord.class);
	
	/**
	 * 查询通讯记录
	 */
	public Page<AlipayRecord> findByAlipayRecordParams(Map<String, Object> params) {

		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		String out_trade_no = (String) params.get("out_trade_no");
		String trade_no = (String) params.get("trade_no");
		String buyer_email = (String) params.get("buyer_email");
		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());

		String selectSQL = "select ar.* ";

		StringBuffer condition = new StringBuffer(" from alipay_record ar where 1=1");

		List<Object> sqlParams = new ArrayList<Object>();

		if (!StringUtil.isNullOrEmpty(out_trade_no)) {
			condition.append(" and ar.out_trade_no = ? ");
			sqlParams.add(out_trade_no);
		}
		if (!StringUtil.isNullOrEmpty(trade_no)) {
			condition.append(" and ar.trade_no = ? ");
			sqlParams.add(trade_no);
		}
		if (!StringUtil.isNullOrEmpty(buyer_email)) {
			condition.append(" and ar.buyer_email = ? ");
			sqlParams.add(buyer_email);
		}

		condition.append(" order by ar.id desc ");

		log.info(" 查询订单列表 condition{} : " + condition.toString());
		try {
			Page<AlipayRecord> list = dao.paginate(pageNumber, pageSize, selectSQL, condition.toString(), SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error("status{},pageObj{},sizeObj{}:" + pageObj + "," + sizeObj);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据交易号查询
	 */
	public AlipayRecord findAlipayRecordByTradeNo(String trade_no){
		
		String querySQL = "select * from alipay_record where trade_no = ?";
		try {
			
			List<AlipayRecord> list = dao.find(querySQL, new Object[]{trade_no});
			if(list != null && list.size() > 0){
				return list.get(0);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据订单号查询支付宝交易记录
	 */
	public AlipayRecord findAlipayRecordByOrderNum(String order_num){
		
		String querySQL = "select * from alipay_record where out_trade_no = ?";
		try {
			
			List<AlipayRecord> list = dao.find(querySQL, new Object[]{order_num});
			if(list != null && list.size() > 0){
				return list.get(0);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据notify_id查询支付宝交易记录
	 */
	public AlipayRecord findAlipayRecordByNotifyId(String notify_id){
		
		String querySQL = "select * from alipay_record where notify_id = ?";
		try {
			
			List<AlipayRecord> list = dao.find(querySQL, new Object[]{notify_id});
			if(list != null && list.size() > 0){
				return list.get(0);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
