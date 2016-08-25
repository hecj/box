package com.boxamazing.admin.controller.pay;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.common.BaseController;
import com.boxamazing.common.StringUtil;
import com.boxamazing.common.interceptor.UserUtil;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.pay.model.AlipayRecord;
import com.boxamazing.service.u.model.PUser;
import com.jfinal.plugin.activerecord.Page;

/**
 * 支付宝交易记录
 */
public class AlipayRecordController extends BaseController{
	
	public static Log log = LogFactory.getLog(AlipayRecordController.class);
	
	
	public void query(){
		PUser puser = UserUtil.getU(getSession());
		try {
			long page  = 1;
			if(this.isParaExists(0)){
				page = getParaToLong(0);
			}
			
			String out_trade_no = getPara("out_trade_no");
			String trade_no = getPara("trade_no");
			String buyer_email = getPara("buyer_email");
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("page", page);
			params.put("size", 10);
			if(!StringUtil.isNullOrEmpty(out_trade_no)){
				params.put("out_trade_no", out_trade_no);
			}
			if(!StringUtil.isNullOrEmpty(trade_no)){
				params.put("trade_no", trade_no);
			}
			if(!StringUtil.isNullOrEmpty(buyer_email)){
				params.put("buyer_email", buyer_email);
			}
			Page<AlipayRecord> alipayRecordPage = AlipayRecord.dao.findByAlipayRecordParams(params);
			
			setAttr("alipayRecordPage", alipayRecordPage);
			setAttr("out_trade_no", out_trade_no);
			setAttr("trade_no", trade_no);
			setAttr("buyer_email", buyer_email);
			
			render("/page/pay/alipay_query.ftl");
		} catch (Exception e) {
			log.error(e);
			renderError(403);
		}
	}
	
}
