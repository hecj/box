package com.boxamazing.service.pay.model;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.plugin.activerecord.Model;

/**
 * 即时到帐接口订单退款通讯记录
 */
public class AlipayOrderRefundRecord extends Model<AlipayOrderRefundRecord>  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final AlipayOrderRefundRecord dao = new AlipayOrderRefundRecord();
	
	private static final Log log = LogFactory.getLog(AlipayOrderRefundRecord.class);
	
	/**
	 * 根据notify_id查询支付宝交易记录
	 */
	public AlipayOrderRefundRecord findAlipayOrderRefundRecordByNotifyId(String notify_id){
		
		String querySQL = "select * from alipay_order_refund_record where notify_id = ?";
		try {
			
			List<AlipayOrderRefundRecord> list = dao.find(querySQL, new Object[]{notify_id});
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
