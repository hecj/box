package com.boxamazing.admin.controller.pay;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;

import com.boxamazing.common.BaseController;
import com.boxamazing.common.StringUtil;
import com.boxamazing.common.interceptor.UserUtil;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.orders.model.OrdersRefund;
import com.boxamazing.service.pay.PaymentService;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.u.model.PUser;
import com.boxamazing.service.util.OrdersUtils;
import com.boxamazing.util.ResultJson;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 订单退单
 */
public class OrdersRefundController extends BaseController{
	
	public static Log log = LogFactory.getLog(OrdersRefundController.class);
	
	/**
	 * 退单管理列表
	 */
	public void query(){
		PUser puser = UserUtil.getU(getSession());
		try {
			long page  = 1;
			if(this.isParaExists(0)){
				page = getParaToLong(0);
			}
			
			String order_num = getPara("order_num");
			String trade_no = getPara("trade_no");
			Integer status = getParaToInt("status");
			String project_name = getPara("project_name");
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("page", page);
			params.put("size", 10);
			if(!StringUtil.isNullOrEmpty(order_num)){
				params.put("order_num", order_num);
			}
			if(!StringUtil.isNullOrEmpty(trade_no)){
				params.put("trade_no", trade_no);
			}
			if(!StringUtil.isNullOrEmpty(project_name)){
				params.put("project_name", project_name);
			}
			if(status != null){
				params.put("status", status);
			}
			Page<Record> ordersRefundPage = OrdersRefund.dao.findByRecordParams(params);
			
			setAttr("ordersRefundPage", ordersRefundPage);
			setAttr("order_num", order_num);
			setAttr("trade_no", trade_no);
			setAttr("status", status);
			setAttr("project_name", project_name);
			
			render("/page/pay/ordersRefund/index.ftl");
		} catch (Exception e) {
			log.error(e);
			renderError(403);
		}
	}
	
	
	/**
	 * 支持列表-添加批量退款
	 */
	@Before(POST.class)
	public void addRefundOrder(){
		
		PUser puser = UserUtil.getU(getSession());
		final long pid = puser.getLong("id");
		
		String[] order_ids = getParaValues("order_id");
		try {
			
			if(order_ids == null || order_ids.length == 0){
				renderJson(new ResultJson(-1l, "需要退款的订单为空"));
				return ;
			}
			
			List<Long> idList = new ArrayList<Long>();
			for(String id : order_ids){
				idList.add(Long.parseLong(id));
			}
			
			List<Orders> ordersList = Orders.dao.findOrdersByIds(idList);
			
			List<Map<String,String>> errorOrderNumList = new ArrayList<Map<String,String>>();
			// 添加订单退款表
			for (Orders o : ordersList) {
				
				final String order_num = o.getStr("order_num");
				final long order_id = o.getLong("id");
				Map<String,String> result = validOrderRefund(o.getLong("id"));
				if ("200".equals(result.get("code"))) {
					
					Db.tx(new IAtom() {
						@Override
						public boolean run() throws SQLException {
							
							int rows = Db.update("update orders set status = 6 where id = ? ",new Object[] { order_id });
							if(rows == 0){
								log.info("添加订单退款记录失败【回滚】");
								return false;
							}
							// 添加订单退款表
							OrdersRefund ordersRefund = new OrdersRefund();
							ordersRefund.put("order_num", order_num);
							ordersRefund.put("status", 1);
							ordersRefund.put("type", 2);
							ordersRefund.put("desc", "项目众筹失败退款");
							ordersRefund.put("operator", pid);
							ordersRefund.put("create_at",System.currentTimeMillis());
							boolean b = ordersRefund.save();
							if(!b){
								log.info("添加订单退款记录失败【回滚】");
								return false;
							}
							
							return true;
						}
					});
					
				} else {
					// 不符合退款列表
					errorOrderNumList.add(result);
				}
			}
			renderJson(new ResultJson(200l, "已成功个数（"+(ordersList.size()-errorOrderNumList.size()+"），失败个数（"+errorOrderNumList.size()+"）")));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderJson(new ResultJson(-100000l, "处理异常："+e.getMessage()));
		}
	}
	
	/**
	 * 校验是否符合订单退款逻辑
	 * 订单退款条件：
	 * 1.订单状态为：已付款
	 * 2.判断已经有退款记录
	 * 3.项目失败 才可以退款
	 */
	private Map<String,String> validOrderRefund(long order_id){
		
		Map<String,String> result = new HashMap<String,String>();
		
		Orders orders = Orders.dao.findById(order_id);
		String order_num = orders.getStr("order_num");
		// 1.判断退款状态
		int status = orders.getInt("status");
		if(status != 1){
			log.info("订单状态不是已付款，不可退款。");
			result.put("code", "-1");
			result.put("message",order_num+":订单状态不是已付款，不可退款。");
			return result;
		}
		
		// 2.判断订单退款记录
		OrdersRefund ordersRefund = OrdersRefund.dao.findOrdersRefundByOrderNum(order_num);
		if(ordersRefund != null){
			log.info("该订单号已有退款记录order_num{}："+order_num);
			result.put("code", "-2");
			result.put("message",order_num+":订单号已有退款记录");
			return result;
		}
		
		Project project = Project.dao.findById(orders.getLong("project_id"));
		if(project.getInt("status") != 9){
			log.info("该项目非众筹失败，不可退款 order_num{}："+order_num);
			result.put("code", "-3");
			result.put("message",order_num+":该项目非众筹失败，不可退款");
			return result;
		}
		
		result.put("code", "200");
		result.put("message","success");
		return result;
	}
	
	
	
	/**
	 *1.余额退款->退款到余额
	 */
	public void doBalanceRefund(){
		
		
		String[] refundIds = getPara("order_refund_ids").split(",");

		// 支付宝支付订单集合
		List<Long> alipayOrders = new ArrayList<Long>();

		List<Long> list = new ArrayList<Long>();
		for(String id : refundIds){
			if(!StringUtil.isNullOrEmpty(id)){
				list.add(Long.parseLong(id));
			}
		}
		log.info("批量退款Id集合："+list);
		try {
			List<OrdersRefund> ordersRefunds =OrdersRefund.dao.findOrdersRefundByIds(list);
			
			PaymentService paymentService = new PaymentService();
			
			// 1.过滤余额支付订单
			for (OrdersRefund or : ordersRefunds) {
				if(or.getInt("pay_type") == 1){
					// 余额支付订单退款
					Map<String,String> result = paymentService.procesBalanceOrdersRefund(or.getStr("order_num"));
					log.info("订单退款处理结果："+result);
				} else if(or.getInt("pay_type") == 2){
					// 支付宝支付
					alipayOrders.add(or.getLong("id"));
				}
			}
			
			renderJson(new ResultJson(200l, alipayOrders,"success"));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderJson(new ResultJson(-100000l, "处理异常："+e.getMessage()));
		}
		
	}
	
	/**
	 * 退款提交
	 * 订单退款方式：
	 *            a.支付宝退款->原路径退款
	 * 2.拼接支付宝订单data_detail集合，链接支付宝退款接口
	 */
	public void doOrdersRefund(){
		
		String refund_message = "订单退款";
		
		String[] refundIds = getPara("order_refund_ids").split(",");

		List<Long> list = new ArrayList<Long>();
		for(String id : refundIds){
			if(!StringUtil.isNullOrEmpty(id)){
				list.add(Long.parseLong(id));
			}
		}
		log.info("支付宝订单退款Id："+list);
		try {
			List<OrdersRefund> ordersRefunds =OrdersRefund.dao.findOrdersRefundByIds(list);
			
			PaymentService paymentService = new PaymentService();
			
			// 3.支付宝退款业务
			if(ordersRefunds.size() > 0){
				String detail_data = "";
				int batch_num = 0;
				for(OrdersRefund or : ordersRefunds){
					
					// 校验 1.等待退款状态
					if(paymentService.validDoOrderRefund(or.getStr("order_num"))){
						BigDecimal money = or.getBigDecimal("money");
						BigDecimal postage = or.getBigDecimal("postage");
						String refundMoney = money.add(postage).toString();
						
						String trade_no = or.getStr("trade_no");
						if(!StringUtil.isNullOrEmpty(trade_no)){
							detail_data += or.getStr("trade_no")+"^"+refundMoney+"^"+refund_message+"#";
							batch_num ++;
						}
					}
				}
				
				if(detail_data.endsWith("#")){
					detail_data = detail_data.substring(0, detail_data.length() - 1);
				}
				
				// 退款当天日期
				String refund_date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
				log.info("refund_date:"+refund_date);
				// 批次号
				String batch_no = OrdersUtils.refundBatchNo();
				log.info("退款批次号："+batch_no);
				log.info("detail_data : "+detail_data);
				String sHtmlText = paymentService.createRefundOrderAlipayForm(refund_date, batch_no, detail_data, batch_num);
				renderHtml(sHtmlText);
				return;
			} 
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
}
