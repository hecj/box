package com.boxamazing.webfront.controller.orders;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.code.Provinces;
import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.fans.model.Fans;
import com.boxamazing.service.orders.model.OrderAddress;
import com.boxamazing.service.orders.model.OrderStatus;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.pay.OrderService;
import com.boxamazing.service.product.model.Product;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.project.model.ProjectStatus;
import com.boxamazing.service.user.model.ReceiveAddress;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.Constant;
import com.boxamazing.service.util.FormatUtil;
import com.boxamazing.webfront.common.BaseController;
import com.boxamazing.webfront.interceptor.LoginInterceptor;
import com.boxamazing.webfront.util.Code;
import com.boxamazing.webfront.util.ResultJson;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

/**
 * 订单《bur》
 */
@Before(LoginInterceptor.class)
public class OrdersController extends BaseController {

	public static Log log = LogFactory.getLog(OrdersController.class);
	
	private static final int SUPPORT_PAGE_SIZE = PropKit.use("page_size.properties").getInt("personSupportPageSize");
	
	public void index() {
		// 获取登录用户ID
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderJson(new ResultJson(Code.F_1, "请登录"));
			return;
		}
		Long userId = user.getLong("id");
		long fans = Fans.dao.getFansCountByPromoder(userId);
		setAttr("fans", fans);

		// 获取分页页数
		int page = getParam(0, 1);

		// 获取数据
		Page<Orders> ordersPage = null;
		try {
			ordersPage = Orders.dao.findOrderListByPage(userId,	page, SUPPORT_PAGE_SIZE);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("get orderPage exception", e);
			return;
		}
		if (ordersPage == null) {
			log.info("orderPage is null [userId:" + userId + ", page:" + page + ", size:" + SUPPORT_PAGE_SIZE + "]");
			return;
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		for (Orders order : ordersPage.getList()) {
			//计算预计回报日期：项目截止日期(project.expirestime) + product.send_days
			Long expirestime = order.getLong("pj_expirestime");
			Integer send_day = order.getInt("pd_days");
			int amount = send_day == null ? 0 : send_day;
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(expirestime);
			c.roll(Calendar.DAY_OF_YEAR, amount);
			order.put("pj_expirestime", FormatUtil.MyDateFormat("yyyy-MM-dd", c.getTimeInMillis()));
			
			//显示项目的众筹状态
			order.put("projectStatus", ProjectStatus.getStatus(order.getInt("pj_status")));
			
			//显示订单的状态和可操作按钮
			int project_status = order.getInt("pj_status");    //8."众筹中"； 9. "众筹失败"；10. "众筹成功"
			int pay_status = order.getInt("status");      //0.未付款  1.已付款，2: 交易关闭，3：交易失效，4：已发货，5：已收货, 6:等待退款，7：已退款）
			switch(pay_status){
			case 0:
				//未付款
				order.put("payStatus", OrderStatus.getStatus(pay_status));
				if(project_status == ProjectStatus.CROWDFUNDING){			
					//先要判断订单是否已经失效（点击了支付，但是实际没有付款成功， 则该交易订单失效，只能进行删除操作）
					Long invalid_at = order.getLong("invalid_at");
					if(invalid_at != null && invalid_at <= System.currentTimeMillis()){
						order.put("payStatus", "已失效");
						order.put("showDelete", 1);
						order.put("immediate_pay", 0);
						order.put("cancel_pay", 0);
					}else{
						order.put("immediate_pay", 1);
						order.put("cancel_pay", 1);
					}
				}
				
				//已失效
				else if(project_status == ProjectStatus.CROWDFUNDED_FAILURE || project_status == ProjectStatus.CROWDFUNDED_SUCCESS){
					order.put("payStatus", "已失效");
					order.put("immediate_pay", 0);
					order.put("cancel_pay", 0);
					order.put("showDelete", 1);
				}
				break;
			case 1:
				//已付款(待发回报)  --- 确认收取回报 --- 项目评论
				if(project_status == ProjectStatus.CROWDFUNDING){//众筹中
					order.put("payStatus", OrderStatus.getStatus(pay_status));
					order.put("orderStatus", "待发回报"); 
					order.put("confirm_product", 0);
					order.put("common", 1);
				}else if(project_status == ProjectStatus.CROWDFUNDED_FAILURE){//众筹失败
					order.put("payStatus", "等待退款");
					order.put("immediate_pay", 0);
					order.put("cancel_pay", 0);
					order.put("showDelete", 1);
				}else if(project_status == ProjectStatus.CROWDFUNDED_SUCCESS){//众筹成功
					//已付款(待发回报)  --- 确认收取回报 --- 项目评论
					if(pay_status == 1){//已付款状态
						order.put("payStatus", OrderStatus.getStatus(pay_status));
						order.put("orderStatus", "待发回报"); 
						order.put("confirm_product", 0);
						order.put("common", 1);
					}else if(pay_status == 4){//已发货状态
						order.put("payStatus", "已付款");
						order.put("orderStatus", "已发回报");
						order.put("confirm_product", 1);
						order.put("common", 1);
					}else if(pay_status == 5){
						order.put("payStatus", "已付款");
						order.put("orderStatus", "回报已签收");
						order.put("confirm_product", 0);
						order.put("common", 1);
					}
				}
				break;
			case 2:
				//交易关闭  ---  删除
				order.put("payStatus", OrderStatus.getStatus(pay_status));
				order.put("immediate_pay", 0);
				order.put("cancel_pay", 0);
				order.put("showDelete", 1);
				break;
			case 3:
				//已失效 --- 删除
				order.put("payStatus", "已失效");
				order.put("immediate_pay", 0);
				order.put("cancel_pay", 0);
				order.put("showDelete", 1);
				break;
			case 4:
				//已付款(已发回报)  --- 确认收取回报 --- 项目评论
				order.put("payStatus", "已付款");
				order.put("orderStatus", "已发回报");
				order.put("confirm_product", 1);
				order.put("common", 1);
				break;
			case 5:
				//已付款(回报已签收)  --- 确认收取回报 --- 项目评论
				order.put("payStatus", "已付款");
				order.put("orderStatus", "回报已签收");
				order.put("confirm_product", 0);
				order.put("common", 1);
				break;
			case 6:
				order.put("payStatus", OrderStatus.getStatus(pay_status));
				order.put("immediate_pay", 0);
				order.put("cancel_pay", 0);
				break;
			case 7:
				order.put("payStatus", "已退款");
				if(project_status == ProjectStatus.CROWDFUNDING){//众筹中
					order.put("immediate_pay", 0);
					order.put("cancel_pay", 0);
				}else if(project_status == ProjectStatus.CROWDFUNDED_FAILURE){//众筹失败
					order.put("immediate_pay", 0);
					order.put("cancel_pay", 0);
				}else if(project_status == ProjectStatus.CROWDFUNDED_SUCCESS){//众筹成功
					order.put("immediate_pay", 0);
					order.put("cancel_pay", 0);
				}
				order.put("showDelete", 1);
			}
			// 日期格式化
			try{order.set("pay_at", df.format(new Date( order.getLong("pay_at"))));}catch(Exception e){}
			try{order.set("invalid_at", df.format(new Date( order.getLong("invalid_at"))));}catch(Exception e){}
			try{order.set("create_at", df.format(new Date( order.getLong("create_at"))));}catch(Exception e){}
			try{order.set("update_at", df.format(new Date( order.getLong("update_at"))));}catch(Exception e){}
		}
		
		log.info("page:[pageNumber:" + ordersPage.getPageNumber()
				+ ",pageSize:" + ordersPage.getPageSize() + ",totalPage:"
				+ ordersPage.getTotalPage() + ",totalRow:"
				+ ordersPage.getTotalRow() + "]");
		setAttr("orderList", ordersPage.getList());
		setAttr("pageNumber", ordersPage.getPageNumber());
		setAttr("pageSize", ordersPage.getPageSize());
		setAttr("totalPage", ordersPage.getTotalPage());
		setAttr("totalRow", ordersPage.getTotalRow());
		
		render("index.ftl");
	}

	// 查询订单详情
	public void findDetail() {
		log.info("id{}:" + getPara("id"));
		Orders order = Orders.dao.findById(getPara("id"));
		setAttr("order", order);
		render("detail.ftl");
	}

	public String orderId(int id, Date date, String pid) {
		int random = (int) (Math.random() * 99999999 + 9999999);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		char[] abc = df.format(date).toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char s : abc) {
			if (s >= '0' && s <= '9') {
				sb.append(s);
			}
		}

		return id + "" + sb.toString() + pid + random;
	}

	/**
	 * 进入生成订单页面两种途径：
	 * 1.从项目详情点“立即支付”进入
	 * 2.在订单确认页面点“修改”进入
	 */
	public void generate() {

		long product_id = getParaToLong(0);
		User user = UserUtil.getUser(getSession());
		long user_id = user.getLong("id");
		log.info("user_id{},product_id{}:" +user_id+","+ product_id);
		try {
			
			Product product = Product.dao.findById(product_id);
			if (product == null) {
				log.info("进入订单页面产品不存在product_id：" + product_id);
				redirect("/page_404");
				return;
			}
			
			// 第三方登录跳到完善信息页面
			String phone = user.getStr("phone");
			if(StringUtil.isNullOrEmpty(phone) || phone.trim().length() == 0){
				redirect("/personal/bind");
				return ;
			}
			
			// 若订单号存在，表示从修改页面进入
			String order_num = getPara("order_num");
			if (!StringUtil.isNullOrEmpty(order_num)) {
				log.info("订单页面存在订单号order_num:" + order_num);
				Orders orders = Orders.dao.findOrdersByOrderNum(order_num);
				if (orders != null) {
					if (orders.getLong("user_id").compareTo(user_id) != 0) {
						log.info("该笔订单不属于此用户，订单非法。");
						redirect("/page_404");
						return;
					}
					setAttr("order_num", order_num);
				}
			}
			
			Project project = Project.dao.findById(product.getLong("project_id"));
			// 默认收货地址
			List<ReceiveAddress> defaultAddressList = ReceiveAddress.dao.findListByUserIdAndDefault(user.getLong("id"), 1);
			// 其余收货地址
			List<ReceiveAddress> receiveAddressList = ReceiveAddress.dao.findListByUserIdAndDefault(user.getLong("id"), 0);

			List<Provinces> provincesList = Provinces.dao.findAll();

			setAttr("product", product);
			setAttr("project", project);
			setAttr("STATIC_URL", Constant.STATIC_URL);
			setAttr("defaultAddressList", defaultAddressList);
			setAttr("receiveAddressList", receiveAddressList);
			setAttr("provincesList", provincesList);

			render("/page/orders/cf-pay-submit.ftl");

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderError(403);
		}
	}

	/**
	 * 生成订单
	 */
	@Before(POST.class)
	public void checkout() {

		final User user = UserUtil.getUser(getSession());
		long product_id = getParaToLong(0);
		final long user_id = user.getLong("id");
		log.info("user_id{}:" + user_id);
		log.info("订单预提交product_id:" + product_id);
		try {
			
			Product product = Product.dao.findById(product_id);
			// 收货地址
			Long receiveAddressId = getParaToLong("receive_address_id");
			
			// 生成订单
			OrderService orderService = new OrderService();
			Map<String,String> result = orderService.generateOrder(user_id, product_id, receiveAddressId);
			log.info("result:"+result);
			if(!("200").equals(result.get("code"))){
				setAttr("order_message", result.get("message"));
				setAttr("product", product);
				redirect("/page_404");
				return ;
			}
			
			// 订单号
			String order_num = result.get("order_num");
			redirect("/orders/confirm/" + order_num);

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			setAttr("order_message", "订单处理失败");
			renderError(403);
		}

	}
	
	/**
	 * 从订单确认页面返回-修改订单提交
	 */
	@Before(POST.class)
	public void checkout_up() {

		final User user = UserUtil.getUser(getSession());
		long product_id = getParaToLong(0);
		final long user_id = user.getLong("id");
		log.info("user_id{}:" + user_id);
		log.info("订单修改提交product_id:" + product_id);
		try {
			
			String order_num = getPara("order_num");
			log.info("订单号："+order_num);
			
			final Product product = Product.dao.findById(product_id);
//			final Project project = Project.dao.findById(product.getLong("project_id"));
			
			Orders orders = Orders.dao.findOrdersByOrderNum(order_num);
			if(orders.getLong("user_id").compareTo(user_id) != 0){
				setAttr("order_message", "订单处理失败，原因：订单不属于此用户，订单非法");
				setAttr("product", product);
				redirect("/page_404");
				return;
			}
			
			//收货地址
			final Long receiveAddressId = getParaToLong("receive_address_id");

			if (product.getInt("type") == 0) {
			
				// 修改订单收货地址
				OrderAddress orderAddress = OrderAddress.dao.findOrderAddressByOrderNum(order_num);
				
				ReceiveAddress receiveAddress = ReceiveAddress.dao.findById(receiveAddressId);
				orderAddress.set("receive_address_id", receiveAddressId);
				orderAddress.set("name", receiveAddress.get("name"));
				orderAddress.set("phone", receiveAddress.get("phone"));
				orderAddress.set("province", receiveAddress.get("province"));
				orderAddress.set("city", receiveAddress.get("city"));
				orderAddress.set("area", receiveAddress.get("area"));
				orderAddress.set("zipcode", receiveAddress.get("zipcode"));
				orderAddress.set("detail_address", receiveAddress.get("detail_address"));
				// 配送方式：未知
				orderAddress.set("ship_mode", -1);
				orderAddress.update();
				log.info("修改订单成功");
				
			}
			
			redirect("/orders/confirm/" + order_num);

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderError(403);
		}

	}

	/**
	 * 订单确认页面 3 by HECJ
	 */
	public void confirm() {

		User user = UserUtil.getUser(getSession());
		// 订单号
		String order_num = getPara(0);
		long user_id = user.getLong("id");
		log.info("user_id{}:" + user_id);
		log.info("进入订单确认页order_num:" + order_num);
		try {

			Orders orders = Orders.dao.findOrdersByOrderNum(order_num);
			
			if(orders.getLong("user_id").compareTo(user_id) != 0){
				setAttr("order_message", "订单处理失败，原因：订单不属于此用户，订单非法");
				redirect("/page_404");
				return;
			}
			
			Long product_id = orders.getLong("product_id");
			Product product = Product.dao.findById(product_id);
			if (product == null) {
				log.info("进入订单确认页面产品不存在product_id：" + product_id);
				renderError(404);
				return;
			}

			if (product.getInt("type") == 0) {
				Long receive_address_id = OrderAddress.dao.findOrderAddressByOrderNum(order_num).getLong("receive_address_id");
				ReceiveAddress receiveAddress = ReceiveAddress.dao.findById(receive_address_id);
				setAttr("receiveAddress", receiveAddress);
				setAttr("default_address_id",receive_address_id);
			}

			// TODO 优惠卷

			// 用户余额
			setAttr("user", User.dao.findById(user_id));

			setAttr("product", product);
			
			setAttr("orders", orders);

			render("/page/orders/cf-pay-way.ftl");

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderError(403);
		}
	}
	
	/**
	 * 查询订单详情JSON
	 */
	public void detailJSON(){
		User user = UserUtil.getUser(getSession());
		// 订单号
		long user_id = user.getLong("id");
		log.info("user_id:"+user_id);
		String order_num = getPara(0);
		try {
			log.info("order_num:"+order_num);
			Orders orders = Orders.dao.findOrdersByOrderNum(order_num);
			if(orders.getLong("user_id").compareTo(user_id) != 0){
				renderJson(new ResultJson(-1l, "订单查询失败"));
				return;
			}
			renderJson(new ResultJson(200l, orders, "success"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new ResultJson(-100000l, e.getMessage()));
		}
	} 
	
	/**
	 * 根据订单id删除订单
	 */
	public void delete(){
		Orders o = Orders.dao.findById(getPara("id"));
		o.set("shower", 0);
		o.update();
		index();
		render("index.ftl");
	}
	
	/**
	 * 取消订单付款
	 * 1.修改订单关闭状态
	 * 2.增加库存
	 */
	public void cancelPay(){
		final Orders o = Orders.dao.findById(getPara("orderid"));
		//只有未付款的订单才可以变为“订单已失效”
		if(o.getInt("status") == 0){
			try{				
				boolean bool = Db.tx(new IAtom() {
					@Override
					public boolean run() throws SQLException {
						boolean b = o.set("status", 2).update();
						if(!b){
							return false;
						}
						int rows = Db.update("update product set existnum = existnum - 1 where id = ?",new Object[]{o.getLong("product_id")});
						if(rows == 0){
							return false;
						}
						return true;
					}
				});
				if(bool){
					renderText("success");
				}else{
					renderText("false");
				}
			}catch(Exception e){
				log.error("取消订单付款时更新状态不成功");
				renderText("error");
				return;
			}			
		}
		//如果后台扫描程序已经将订单置为失效状态，则不用管
		else if(o.getInt("status")  == 2)	{
			index();	
			renderText("success");
			return;
		}
		//如果订单状态已经变成了其它状态，则提示页面，发生了并发操作，取消失败，请刷新页面的提示
		else{
			renderText("failure");
			return;
		}
		renderNull();
	}
	
	/**
	 * 确认收取回报
	 */
	public void confirmProduct(){
		Orders o = Orders.dao.findById(getPara("orderid"));
		//只有已发货的订单才可以变为“已收货”
		if(o.getInt("status") == 4){
			try{				
				o.set("status", 5).update();
			}catch(Exception e){
				log.error("确认收取回报时更新状态不成功");
				renderText("error");
				return;
			}
		}
		//如果订单状态已经变成了其它状态(不是状态4)，则提示页面，发生了并发操作，取消失败，请刷新页面的提示
		else{
			renderText("failure");
			return;
		}
		renderNull();
	}
}
