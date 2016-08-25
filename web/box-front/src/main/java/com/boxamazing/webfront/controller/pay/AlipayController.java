package com.boxamazing.webfront.controller.pay;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alipay.util.AlipayNotify;
import com.boxamazing.service.pay.PaymentService;
import com.boxamazing.service.pay.model.AlipayRecord;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.FormatUtil;
import com.boxamazing.webfront.common.BaseController;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;

/**
 * 支付回调相关业务处理
 * by HECJ
 */
public class AlipayController extends BaseController{
	
	public static Log log = LogFactory.getLog(AlipayController.class);
	
	
	@Before(GET.class)
	public void pay_order_return_url(){
		log.info("支付宝同步回调");
		String out_trade_no = getParam(getRequest(),"out_trade_no");
		log.info("商户订单号 ："+out_trade_no);
		redirect("/payment/ordersuccess/"+out_trade_no);
	}
	
	/**
	 * 项目支持支付宝异步通知回调接口
	 */
	@Before(POST.class)
	public void pay_order_notify_url(){

		log.info("=========支付宝异步通知回调 pay_notify_url===========");
		HttpServletRequest request = getRequest();
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		
		//通知校验ID
		String notify_id = getParam(request,"notify_id");
		log.info("通知校验ID："+notify_id);
		//商户订单号
		String out_trade_no = getParam(request,"out_trade_no");
		log.info("商户订单号 ："+out_trade_no);
		//支付宝交易号
		String trade_no = getParam(request,"trade_no");
		log.info("支付宝交易号 ："+trade_no);
		//交易状态
		String trade_status = getParam(request,"trade_status");
		log.info("交易状态 ："+trade_status);
		// 签名方式
		String sign_type = getParam(request,"sign_type");
		// 支付类型
		String payment_type = getParam(request,"payment_type"); 
		// 卖家支付宝账户
		String seller_email = getParam(request,"seller_email");
		log.info("卖家支付宝账户："+seller_email);
		// 买家支付宝账户
		String seller_id = getParam(request,"seller_id");
		log.info("卖家支付宝账户号："+seller_id);
		// 买家支付宝账户
		String buyer_email = getParam(request,"buyer_email");
		log.info("买家支付宝账户："+buyer_email);
		// 买家支付宝账户号
		String buyer_id = getParam(request,"buyer_id"); 
		log.info("买家支付宝账户号："+buyer_id);
		// 交易金额
		String total_fee = getParam(request,"total_fee");
		log.info("交易金额："+total_fee);
		// 交易付款时间
		String gmt_payment = getParam(request, "gmt_payment");
		log.info("交易付款时间:"+gmt_payment);
		// 交易创建时间
		String gmt_create = getParam(request, "gmt_create");
		log.info("交易创建时间:"+gmt_create);
		
		try {
			//计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);
			log.info("参数校验结果："+verify_result);
			//验证成功
			if(verify_result){
				
				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
					
					log.info("订单处理成功，业务处理开始");
					long pay_at = FormatUtil.strDateToLong(gmt_payment);
					log.info("pay_at:"+pay_at);
					PaymentService paymentService = new PaymentService();
					paymentService.procesAlipayOrder(trade_no,out_trade_no,pay_at);
					
					// 给支付宝反馈此笔订单处理成功，不必继续发送通知
					renderHtml("success");
					return;
				}
				
			}else{
				log.info("参数校验失败："+verify_result);
				renderHtml("fail");
				return;
			}
		}catch(Exception ex){
			log.error(ex.getMessage());
			ex.printStackTrace();
			renderError(403);
		} finally{
			
			// 记录通讯日志 根据 notify_id判断是否重复通知（只记录一条重复通知）
			try {
				AlipayRecord alipayRecord = AlipayRecord.dao.findAlipayRecordByNotifyId(notify_id);
				if (alipayRecord == null) {
					// 记录通讯日志
					AlipayRecord ar = new AlipayRecord();
					ar.put("notify_id", notify_id);
					ar.put("out_trade_no", out_trade_no);
					ar.put("trade_no", trade_no);
					ar.put("trade_status", trade_status);
					ar.put("sign_type", sign_type);
					ar.put("payment_type", payment_type);
					ar.put("seller_email", seller_email);
					ar.put("seller_id", seller_id);
					ar.put("total_fee", total_fee);
					ar.put("buyer_email", buyer_email);
					ar.put("buyer_id", buyer_id);
					ar.put("gmt_payment", gmt_payment);
					// 提交订单支付回调
					ar.put("trade_type", 1);
					ar.put("gmt_create", gmt_create);
					ar.put("create_at", System.currentTimeMillis());
					ar.save();
				} else {
					log.info("支付宝-订单付款重复通知 notify_id :" + notify_id);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		renderHtml("fail");
	}
	
	/**
	 * 编码获取参数
	 */
	private String getParam(HttpServletRequest request,String key) {
		try{
			return new String(request.getParameter(key).getBytes("ISO-8859-1"),"UTF-8");
		}catch(UnsupportedEncodingException ex){
			ex.printStackTrace();
		}
		return "";
	}
	
	
	@Before(GET.class)
	public void pay_recharge_return_url(){
		log.info("支付宝同步回调");
		User user = UserUtil.getUser(getSession());
		user = User.dao.findById(user.get("id"));
		UserUtil.setUser(user, getSession());
		redirect("/payment/recharge/");
	}
	
	/**
	 * 支付宝异步响应，在此处理所有充值后的业务逻辑
	 * @version liyanlong
	 */
	@Before(POST.class)
	public void pay_recharge_notify_url(){
		log.info("=========支付宝异步通知回调 pay_recharge_notify_url===========");
		final HttpServletRequest request = getRequest();
		final Map<String,String> params = getFeedbackFromAliPay(request);
		boolean exeBoo = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				//计算得出通知验证结果
				boolean verify_result = AlipayNotify.verify(params);
				log.info("参数校验结果："+verify_result);
				//验证成功
				if(!verify_result){
					log.info("参数校验失败："+verify_result);
					renderHtml("fail");
					return false;
				}
				String trade_status = getParam(request,"trade_status");
				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
					//逻辑处理
				    try{
						PaymentService paymentService = new PaymentService();
						paymentService.procesAlipayRecharge(request);
						return true;
					}catch(Exception e){
						e.printStackTrace();
						return false;
					}
				}
				return false;
			}
		});
		log.info("exeBoo:"+exeBoo);
		if (exeBoo) {
			// 给支付宝反馈此笔订单处理成功，不必继续发送通知
			renderHtml("success");
		} else{
			renderHtml("fail");
		}
	}

	/**
	 * 获取支付宝GET过来反馈信息
	 * @param request
	 * @return
	 * @version liyanlong
	 */
	private Map<String, String> getFeedbackFromAliPay(HttpServletRequest request) {
		Map<String,String> params = new HashMap<String,String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		return params;
	}
}
