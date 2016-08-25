package com.boxamazing.admin.controller.delivery;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.common.BaseController;
import com.boxamazing.common.StringUtil;
import com.boxamazing.common.interceptor.UserUtil;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.u.model.PUser;
import com.jfinal.plugin.activerecord.Page;

public class DeliveryController  extends BaseController {
	public static Log log = LogFactory.getLog(DeliveryController.class);
	/**
	 * 订单管理入口
	 */
	public void index(){
		PUser puser = UserUtil.getU(getSession());
		log.info(puser.get("username")+"["+puser.toString()+"]:DeliveryController--index(), 发货单列表");
		try {
			Long orderid  = -1L;
			if(this.isParaExists(0)){
				orderid = getParaToLong(0);
			}
			
			
			
			render("/page/order/index.ftl");
		} catch (Exception e) {
			log.error(e);
			renderError(403);
		}
	}
	
	/**
	 * 订单详情
	 */
	public void detail(){
		PUser puser = UserUtil.getU(getSession());
		log.info(puser.get("username")+"["+puser.toString()+"]:OrderController--detail(), 查看订单详情");
		Long order_id = getParaToLong(0);
//		orderdetail(order_id);
		render("/page/order/order_detail.ftl");
	}
}