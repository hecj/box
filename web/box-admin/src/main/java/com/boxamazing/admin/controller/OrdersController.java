package com.boxamazing.admin.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.common.BaseController;
import com.boxamazing.common.StringUtil;
import com.boxamazing.common.interceptor.UserUtil;
import com.boxamazing.service.delivery.model.Delivery;
import com.boxamazing.service.delivery.model.Delivery_b;
import com.boxamazing.service.orders.model.OrderAddress;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.orders.model.ShipMode;
import com.boxamazing.service.product.model.Product;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.u.model.PUser;
import com.boxamazing.service.user.model.User;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

/**
 * 订单控制层
 */
public class OrdersController extends BaseController {
 
	public static Log log = LogFactory.getLog(OrdersController.class);
	
	/**
	 * 订单管理入口
	 */
	public void index(){
		PUser puser = UserUtil.getU(getSession());
		log.info(puser.get("username")+"["+puser.toString()+"]:OrderController--index(), 后台订单管理列表页");
		try {
			Long page  = 1L;
			if(this.isParaExists(0)){
				page = getParaToLong(0);
			}
			
			String order_num = getPara("order_num");
			Long project_id = getParaToLong("project_id");
			Long status = getParaToLong("status");
			String project_name = getPara("project_name");
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("page", page);
			params.put("size", 10);
			if(!StringUtil.isNullOrEmpty(order_num)){
				params.put("order_num", order_num);
			}
			if(!StringUtil.isNullOrEmpty(project_name)){
				params.put("project_name", project_name);
			}
			if(project_id != null){
				params.put("project_id", project_id);
			}
			if(status != null){
				params.put("status", status);
			}
			Page<Orders> ordersPage = Orders.dao.findByOrdersParams(params);
			setAttr("ordersPage", ordersPage);
			setAttr("order_num", order_num);
			setAttr("project_id", project_id);
			setAttr("status", status);
			setAttr("project_name", project_name);
			
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
		orderdetail(order_id);
		render("/page/order/order_detail.ftl");
	}
	
	/**
	 * 选择配送方式
	 */
	public void shipmode(){
		PUser puser = UserUtil.getU(getSession());
		log.info(puser.get("username")+"["+puser.toString()+"]:OrderController--shipmode()，选择配送方式");
		setAttr("orderid", getParaToLong(0));
		setAttr("orderAddId", getParaToLong(1));
		Integer shipmode = getParaToInt(2, -1);
		try {
			if(shipmode == -1){
				//如果是-1，表示该订单没有默认的配送方式，需要获取系统默认的配送方式
				ShipMode defaultShipMode = ShipMode.dao.queryDefaultShipMode();
				//如果无默认配送方式，则配送方式为-1
				shipmode = defaultShipMode.get("id", -1);
			}
			//当前页面默认的
			setAttr("shipmode", shipmode);
			//所有的配送模式列表
			List<ShipMode> shipModeList = ShipMode.dao.queryAllShipMode();
			setAttr("shipModeList", shipModeList);
			
			render("/page/order/ship_mode.ftl");
		} catch (Exception e) {
			log.error(e);
			renderError(403);
		}
	}
	
	/**
	 * 配送方式返回结果
	 */
	public void shipmodeResult(){
		int shipmode = getParaToInt(0);
  		Long orderid = getParaToLong(1);
		String orderAddId = getPara(2);
		boolean flag = getParaToBoolean(3);
		//如果点击确定，则获取对应的配送方式对象，更新到数据库中
		PUser puser = UserUtil.getU(getSession());
		try{			
			if(flag){
				log.info(puser.get("username")+"[" + puser.toString()+"]:OrderController--shipmode()，选择配送方式为: " + shipmode);
				OrderAddress oa = OrderAddress.dao.findById(orderAddId);
				if(oa != null){					
					oa.set("ship_mode", shipmode).update();
				}
			}else{
				log.info(puser.get("username") + "["+puser.toString()+"]:OrderController--shipmode()，取消选择配送方式");
			}
		}catch(Exception e){
			log.error(e);
			renderError(403);
		}
		orderdetail(orderid);
		render("/page/order/order_detail.ftl");
	}

	/**
	 * 后台预发货单(显示在页面上，不保存在数据库)
	 * @param 
	 */
	public void pre_delivery(){
		PUser puser = UserUtil.getU(getSession());
		log.info(puser.get("username")+"["+puser.toString()+"]:OrderController--shipmode()，生成发货单");
		Long orderid = getParaToLong("orderid");
		log.info("orderid: " + orderid);
		try{
			gotoDelivery(gerneralDeliveryFlowNo());
			renderJson("true");
			log.info("renderJson(true);");
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			renderError(403);
		}
	}
	
	public void lookDelivery(){
		Long deliveryId = getParaToLong(0);
		log.info("deliveryId: " + deliveryId);
		//根据deliveryId 查询出多个 订单（订单和发货单是多对一的）
		//显示发货单详情
		List<Orders> orders = Orders.dao.findByDeliveryId(deliveryId);
		Orders order = orders.get(0);
		setAttr("orders", order);
		Delivery delivery = Delivery.dao.findById(deliveryId);
		setAttr("delivery", delivery);
		Long user_id = order.getLong("user_id");
		User user = User.dao.findById(user_id);
		setAttr("user", user);
		OrderAddress orderAddress = OrderAddress.dao.findOrderAddressByOrderNum(order.getStr("order_num"));
		setAttr("orderAddress", orderAddress);
		Long sendAt = orderAddress.getLong("send_at");
		if(sendAt != null){
			setAttr("send_at", sendAt);
		}else{
			setAttr("send_at", System.currentTimeMillis());
		}
		
		Project project = Project.dao.findById(order.getLong("project_id"));
		setAttr("project", project);
		
		Product product = Product.dao.findById(order.getLong("product_id"));
		setAttr("product", product);
		render("/page/order/deliveryDetail.ftl");
	}
	
	public void renderPreDeliver(){
		Long orderid = getParaToLong(0);
		log.info("orderid: " + orderid);
		//显示订单详情
		orderdetail(orderid);
		render("/page/order/pre_delivery.ftl");
	}
	
	private String gerneralDeliveryFlowNo() {
		final String prefix = "BOX";
		//流水号位数：BOX + YYYY + MM + dd + 流水号
		final int digit = 5;
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String mid = df.format(new Date());
		
		String sql = "select right(delivery_flow_no, " + digit + ") as flow  from delivery where delivery_flow_no like '%" + mid+ "%' order by delivery_flow_no desc limit 0,1";
		Delivery delivery = Delivery.dao.findFirst(sql);
 
		BigDecimal decimal = null;
		String pattern = "";
		for(int i = 0; i < digit; i++){
			pattern += "0";
		}
		DecimalFormat format = new DecimalFormat(pattern);
		if(delivery != null){
			decimal = new BigDecimal(delivery.getStr(("flow")));
		}else{
			decimal = new BigDecimal(0);
		}
		String subfix = format.format(decimal.add(new BigDecimal("1")));
		
		return new StringBuffer().append(prefix).append(mid).append(subfix).toString();
	}
	
	private void orderdetail(Long order_id){
		try {
			Orders orders = Orders.dao.findById(order_id);
			setAttr("orders", orders);
			Long user_id = orders.getLong("user_id");
			User user = User.dao.findById(user_id);
			setAttr("user", user);
			OrderAddress orderAddress = OrderAddress.dao.findOrderAddressByOrderNum(orders.getStr("order_num"));
			setAttr("orderAddress", orderAddress);
			Long sendAt = orderAddress.getLong("send_at");
			if(sendAt != null){
				setAttr("send_at", sendAt);
			}else{
				setAttr("send_at", System.currentTimeMillis());
			}
			
			Project project = Project.dao.findById(orders.getLong("project_id"));
			setAttr("project", project);
			
			Product product = Product.dao.findById(orders.getLong("product_id"));
			setAttr("product", product);
			
			Delivery delivery = Delivery.dao.findByOrderId(order_id);
			setAttr("delivery", delivery);
		} catch (Exception e) {
			log.error(e);
			renderError(403);
		}
	}
	
	/**
	 * 生成发货单
	 */
	private void gotoDelivery(String delivery_flow_no){
			//流水号，发货单号，发货时间，发货单备注, 订单id, 发货地址id
			final String d_flow_no = delivery_flow_no;
			log.info("生成发货单的流水号为：" + delivery_flow_no);
			final String remark = getPara("remark");
			final Long order_id = getParaToLong("orderid");
			final Long address_id = getParaToLong("orderAddressId");
			final Long puser = UserUtil.getU(getSession()).get("id");
			
			
			boolean save = Db.tx(new IAtom(){
				@Override
				public boolean run() throws SQLException {
					//保存发货单主表
					Delivery delivery_n = new Delivery();
					delivery_n.set("delivery_flow_no", d_flow_no);
					delivery_n.set("creator_id", puser);
					delivery_n.set("creat_at", System.currentTimeMillis());
					delivery_n.set("status", 0); //0表示创建发货单,即 未发货，1表示已发货，2.表示已经确认收货， 3表示取消发货, 4.货物丢失
					if(remark != null && remark.trim().length() > 0){
						delivery_n.set("remark", remark);
					}
					boolean s1 = delivery_n.save();
					log.info("创建发货单主表成功？"+ s1 +", 对应的订单号为：order_id{" + order_id.toString() +"}" );
					
					//保存发货单子表
					delivery_n =  Delivery.dao.findByDeliveryFlowNo(d_flow_no);
					Delivery_b delivery_b  = new Delivery_b();
					delivery_b.set("delivery_id", delivery_n.get("id"));
					delivery_b.set("order_id", order_id);
					delivery_b.set("order_address_id", address_id);
					boolean s2 = delivery_b.save();
					log.info("创建发货单子表成功？"+ s2 +", 对应的订单号为：order_id{" + order_id.toString() +"}" );
					return true;
				}
			});
			
			if(save){
				renderText("success");
			}else{
				renderText("failure");
			}
	}
	
	public void deliveryList(){
		PUser puser = UserUtil.getU(getSession());
		log.info(puser.get("username")+"["+puser.toString()+"]:OrderController--index(), 后台发货单管理列表页");
		try {
			Long page  = 1L;
			if(this.isParaExists(0)){
				page = getParaToLong(0);
			}
			
			String delivery_flow_no = getPara("delivery_flow_no");
			String order_no = getPara("delivery_no");
			String project_name = getPara("project_name");
			String delivery_name = getPara("delivery_name");
			String status = getPara("status");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("page", page);
			params.put("size", 10);
			if(!StringUtil.isNullOrEmpty(delivery_flow_no)){
				params.put("delivery_flow_no", delivery_flow_no);
			}
			if(!StringUtil.isNullOrEmpty(order_no)){
				params.put("order_no", order_no);
			}
			if(!StringUtil.isNullOrEmpty(project_name)){
				params.put("project_name", project_name);
			}
			if(!StringUtil.isNullOrEmpty(delivery_name)){
				params.put("delivery_name", delivery_name);
			}
			if(status != null){
				params.put("status", status);
			}
			Page<Delivery> deliverysPage = Delivery.dao.findByDeliverysParams(params);
			setAttr("deliverysPage", deliverysPage);
			setAttr("delivery_flow_no", delivery_flow_no);
			setAttr("delivery_no", order_no);
			setAttr("project_name", project_name);
			setAttr("delivery_name", delivery_name);
			setAttr("status", status);
			
			render("/page/order/delivery_list.ftl");
		} catch (Exception e) {
			log.error(e);
			renderError(403);
		}
	}
	
	/**
	 * 发货操作
	 */
	public void delivery(){
		final String d_delivery_no = getPara("delivery_num");
		final Long sendAt = getParaToLong("send_at");
		final Long order_id = getParaToLong("orderid");
		final Long address_id = getParaToLong("orderAddressId");
		
		Delivery delivery_e = Delivery.dao.findByDeliveryNo(d_delivery_no);
		if(delivery_e != null){
			//render("该发货单号已经存在！");
			renderText("exist");
		}
		
		boolean save = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				//保存发货单号
				Delivery delivery_n = Delivery.dao.findByOrderId(order_id);
				delivery_n.set("delivery_no", d_delivery_no).update();
				
				//保存订单对应的收货地址的发货时间
				OrderAddress orderAddress = OrderAddress.dao.findById(address_id);
				orderAddress.set("send_at", sendAt);
				return true;
			}
		});
		
		if(save){
			renderText("success");
		}else{
			renderText("failure");
		}
	}
	
	public void updateOrderStatus(){
		final Long orderid = getParaToLong(0);
		if(orderid == null){
			return;
		}else{
			Db.tx(new IAtom() {
				@Override
				public boolean run() throws SQLException {
					Delivery delivery = Delivery.dao.findByOrderId(orderid);
					delivery.set("status", 1).update();
					Orders order = Orders.dao.findById(orderid);
					order.set("status", 4).update();
					return true;
				}
			});
			
		}
		setAttr("orderid", Delivery.dao.findByOrderId(orderid).getLong("id"));
		render("/page/order/finish.ftl");
	}
}
