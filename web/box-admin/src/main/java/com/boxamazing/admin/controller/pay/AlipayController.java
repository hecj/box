package com.boxamazing.admin.controller.pay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alipay.util.AlipayNotify;
import com.boxamazing.common.BaseController;
import com.boxamazing.service.pay.PaymentService;
import com.boxamazing.service.pay.model.AlipayOrderRefundRecord;
import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.ext.interceptor.POST;

/**
 * 支付回调相关业务处理
 * by HECJ
 */
@ClearInterceptor(ClearLayer.ALL)
public class AlipayController extends BaseController{
	
	public static Log log = LogFactory.getLog(AlipayController.class);
	
	/**
	 * 订单退款支付宝异步通知回调接口
	 */
	@Before(POST.class)
	public void refund_order_notify_url(){

		log.info("============支付宝订单退款异步通知 refund_order_notify_url==============");
		HttpServletRequest request = getRequest();
		//获取支付宝GET过来反馈信息
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
		
		String notify_id = getParam(request,"notify_id");
		log.info("通知校验ID："+notify_id);
		String notify_time = getParam(request,"notify_time");
		log.info("通知时间："+notify_time);
		String notify_type = getParam(request,"notify_type");
		log.info("通知类型："+notify_type);
		String sign_type = getParam(request,"sign_type");
		log.info("签名方式："+sign_type);
		String sign = getParam(request,"sign");
		log.info("签名："+sign);
		//批次号
		String batch_no = getParam(request, "batch_no");
		log.info("批次号："+batch_no);
		//批量退款数据中转账成功的笔数
		String success_num = getParam(request, "success_num");
		log.info("成功数量："+success_num);
		//批量退款数据中的详细信息
		String result_details = getParam(request, "result_details");
		log.info("详细数据："+result_details);
		try {
			//计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);
			log.info("参数校验结果："+verify_result);
			if(verify_result){
				//验证成功 退款业务处理
				PaymentService paymentService = new PaymentService();
				Map<String,String> result = paymentService.procesAlipayOrdersRefund(batch_no, success_num, result_details, notify_time);
				log.info("===处理结果:"+result);
				if("200".equals(result.get("code"))){
					renderHtml("success");
				}else{
					renderHtml("fail");
				}
				return;
			}else{
				//验证失败
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
				AlipayOrderRefundRecord alipayOrderRefundRecord = AlipayOrderRefundRecord.dao.findAlipayOrderRefundRecordByNotifyId(notify_id);
				if(alipayOrderRefundRecord == null){
					AlipayOrderRefundRecord aord = new AlipayOrderRefundRecord();
					aord.put("notify_id", notify_id);
					aord.put("notify_time", notify_time);
					aord.put("notify_type", notify_type);
					aord.put("sign_type", sign_type);
					aord.put("type", 1);
					aord.put("sign", sign);
					aord.put("batch_no", batch_no);
					aord.put("success_num", success_num);
					aord.put("result_details", result_details);
					aord.put("create_at", System.currentTimeMillis());
					aord.save();
				} else{
					log.info("支付宝-订单退款重复通知 notify_id :"+notify_id);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		renderHtml("fail");
	}
	
	/**
	 * 提现支付宝异步通知回调接口
	 */
	@Before(POST.class)
	public void withdrawals_order_notify_url(){

		log.info("============支付宝提现异步通知 withdrawals_order_notify_url==============");
		HttpServletRequest request = getRequest();
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		
		String notify_id = getParam(request,"notify_id");
		log.info("通知校验ID："+notify_id);
		String notify_time = getParam(request,"notify_time");
		log.info("通知时间："+notify_time);
		String notify_type = getParam(request,"notify_type");
		log.info("通知类型："+notify_type);
		String sign_type = getParam(request,"sign_type");
		log.info("签名方式："+sign_type);
		String sign = getParam(request,"sign");
		log.info("签名："+sign);
		//批次号
		String batch_no = getParam(request, "batch_no");
		log.info("批次号："+batch_no);
		//批量退款数据中转账成功的笔数
		String success_num = getParam(request, "success_num");
		log.info("成功数量："+success_num);
		//批量退款数据中的详细信息
		String result_details = getParam(request, "result_details");
		log.info("详细数据："+result_details);
		try {
			//计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);
			log.info("参数校验结果："+verify_result);
			if(verify_result){
				//验证成功 退款业务处理
				PaymentService paymentService = new PaymentService();
				Map<String,String> result = paymentService.procesAlipayWithdrawals(batch_no, success_num, result_details, notify_time);
				log.info("===处理结果:"+result);
				if("200".equals(result.get("code"))){
					renderHtml("success");
				}else{
					renderHtml("fail");
				}
				return;
			}else{
				//验证失败
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
				AlipayOrderRefundRecord alipayOrderRefundRecord = AlipayOrderRefundRecord.dao.findAlipayOrderRefundRecordByNotifyId(notify_id);
				if(alipayOrderRefundRecord == null){
					AlipayOrderRefundRecord aord = new AlipayOrderRefundRecord();
					aord.put("notify_id", notify_id);
					aord.put("notify_time", notify_time);
					aord.put("notify_type", notify_type);
					aord.put("sign_type", sign_type);
					aord.put("type", 2);
					aord.put("sign", sign);
					aord.put("batch_no", batch_no);
					aord.put("success_num", success_num);
					aord.put("result_details", result_details);
					aord.put("create_at", System.currentTimeMillis());
					aord.save();
				} else{
					log.info("支付宝-订单退款重复通知 notify_id :"+notify_id);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		renderHtml("fail");
	}
}
