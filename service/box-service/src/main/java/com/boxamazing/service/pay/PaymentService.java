package com.boxamazing.service.pay;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;
import com.boxamazing.service.common.PasswordUtil;
import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.error.DealException;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.orders.model.OrdersRefund;
import com.boxamazing.service.pay.model.AlipayRecord;
import com.boxamazing.service.pay.model.Fundrecord;
import com.boxamazing.service.pay.model.RechargeDetailPre;
import com.boxamazing.service.pay.model.RechargeDetailRecord;
import com.boxamazing.service.pay.model.Withdrawals;
import com.boxamazing.service.pay.model.WithdrawalsDetail;
import com.boxamazing.service.payPwd.model.UserPayPwd;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.sms.MessageService;
import com.boxamazing.service.sms.model.NoticeTemplate;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.Constant;
import com.boxamazing.service.util.FormatUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

/**
 * 支付业务处理
 */
public class PaymentService {

	public static Log log = LogFactory.getLog(PaymentService.class);

	/**
	 * @支付宝支付成功回调处理订单 trade_no : 支付宝交易号 order_num : 订单号 pay_at ：支付时间
	 */
	public boolean procesAlipayOrder(final String trade_no, final String order_num, final Long pay_at) {

		// 业务处理
		final Orders orders = Orders.dao.findOrdersByOrderNum(order_num);

		// 订单状态
		int status = orders.getInt("status");
		log.info("订单状态：" + status + ",pay_at:" + pay_at);
		if (status == 1) {
			log.info("订单已支付，无需重复处理");
			return true;
		} else if (status == 0) {
			// 1.支付成功,订单处理
			boolean result = Db.tx(new IAtom() {

				@Override
				public boolean run() throws SQLException {

					// 本地订单状态变更
					orders.set("status", 1);
					orders.set("pay_type", 2); // 支付宝支付
					orders.set("pay_at", pay_at);
					orders.set("pay_num", trade_no);
					orders.update();
					log.info("更改订单信息成功");

					Long project_id = orders.getLong("project_id");
					Db.update("update project set fundpcount = fundpcount + ?, fundnow = fundnow + ?  where id = ? ",
							new Object[] { 1, orders.getBigDecimal("money"), project_id });
					log.info("更改项目募集信息成功");

					return true;
				}
			});
			log.info("result : " + result);
			if (result) {

				// 支持成功 发送系统消息和短信
				User user = User.dao.findById(orders.getLong("user_id"));
				Project project = Project.dao.findById(orders.getLong("project_id"));
				try {
					// 模版
					String content = NoticeTemplate.dao.findByTempName("template_payment_project_success")
							.getStr("content");
					content = content.replace("%nickname%", user.getStr("nickname"));
					content = content.replace("%project_name%", project.getStr("name"));
					// 复审项目 发送 系统消息、短信
					MessageService messageService = new MessageService();
					Map<String, String> contentParams = new HashMap<String, String>();
					contentParams.put("system", content);
					contentParams.put("phone", content);
					messageService.sendNoticeMessage(user.getLong("id"), "", contentParams);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}

		} else if (status == 2 || status == 3) {
			/**
			 * 2.TODO 针对商户订单取消/关闭，而支付宝回调支付成功，需做订单异常处理
			 */
			log.info("订单非未付款状态，出现支付宝订单支付成功，该笔订单存在异常情况。status:" + status + ",order_num:" + order_num);

			// 2. 订单退款处理
			Db.tx(new IAtom() {
				@Override
				public boolean run() throws SQLException {

					// 退款
					orders.set("status", 6);
					orders.set("pay_type", 2); // 支付宝支付
					orders.set("pay_at", pay_at);
					orders.set("pay_num", trade_no);
					orders.update();
					// 订单退款表
					OrdersRefund ordersRefund = new OrdersRefund();
					ordersRefund.put("order_num", order_num);
					ordersRefund.put("status", 1);
					ordersRefund.put("type", 1);
					ordersRefund.put("desc", "订单关闭，支付宝付款成功");
					ordersRefund.put("operator", -1);
					ordersRefund.put("create_at", System.currentTimeMillis());
					ordersRefund.save();

					return true;
				}
			});

		} else {

		}

		return true;
	}

	/**
	 * 创建订单alipay支付表单
	 */
	public String createOrderAlipayForm(String order_num, String subject, String pay_amount, String body,
			String show_url, String notify_url, String return_url, int valid_min) {

		// 链接到支付宝把,请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("seller_email", AlipayConfig.seller_email);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", "1");
		// 服务器异步通知页面路径
		sParaTemp.put("notify_url", notify_url);
		// 页面跳转同步通知页面路径
		sParaTemp.put("return_url", return_url);
		// 商户订单号
		sParaTemp.put("out_trade_no", order_num);
		// 订单名称
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", pay_amount + "");
		// 根据订单30分钟超时，计算支付超时时间
		sParaTemp.put("it_b_pay", valid_min + "m");
		// 订单描述
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("anti_phishing_key", "");
		sParaTemp.put("exter_invoke_ip", "");
		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");

		return sHtmlText;
	}

	/**
	 * 创建alipay订单退款表单
	 * 
	 * @param refund_date
	 *            退款时间
	 * @param batch_no
	 *            退款批量号
	 * @param detail_data
	 *            退款详细数据
	 * @param batch_num
	 *            退款数量
	 * @return
	 */
	public String createRefundOrderAlipayForm(String refund_date, String batch_no, String detail_data, int batch_num) {
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("notify_url", Constant.refund_order_notify_url);
		sParaTemp.put("seller_email", AlipayConfig.seller_email);
		sParaTemp.put("refund_date", refund_date);
		sParaTemp.put("batch_no", batch_no);
		sParaTemp.put("batch_num", batch_num + "");
		sParaTemp.put("detail_data", detail_data);

		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		return sHtmlText;
	}

	/**
	 * 提现组装支付宝form表单
	 */
	public String createWithdrawalsAlipayForm(String refund_date, String batch_no, String detail_data, int batch_num) {
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("notify_url", Constant.withdrawals_order_notify_url);
		sParaTemp.put("seller_email", AlipayConfig.seller_email);
		sParaTemp.put("refund_date", refund_date);
		sParaTemp.put("batch_no", batch_no);
		sParaTemp.put("batch_num", batch_num + "");
		sParaTemp.put("detail_data", detail_data);

		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		return sHtmlText;
	}

	
	/**
	 * 余额支付订单
	 * 
	 * @param order_num
	 *            : 订单号
	 * @param payment_password
	 *            : 支付密码 1.校验支付密码 2.余额足够
	 */
	public Map<String, String> procesUserOrder(final String order_num, String payment_password) {

		log.info("余额支付订单号：" + order_num);

		Map<String, String> result = new HashMap<String, String>();

		final Orders orders = Orders.dao.findOrdersByOrderNum(order_num);
		final long user_id = orders.getLong("user_id");
		final User user = User.dao.findById(user_id);
		log.info("用户user_id:" + user_id);

		// 1.校验支付密码
		UserPayPwd userPayPwds = UserPayPwd.dao.findByUserId(user_id);
		if (userPayPwds == null) {
			result.put("code", "-1");
			result.put("message", "未设置支付密码，请先设置支付密码");
			return result;
		}
		if (!userPayPwds.getStr("pay_pwd").equals(PasswordUtil.encryptPassword(payment_password))) {
			result.put("code", "-2");
			result.put("message", "支付密码输入错误，请重新输入后支付订单");
			return result;
		}

		// 2.校验余额
		BigDecimal pay_money_temp = orders.getBigDecimal("money");
		pay_money_temp = pay_money_temp.add(orders.getBigDecimal("postage"));
		log.info("订单支付金额：" + pay_money_temp);
		// 可用余额
		final BigDecimal usable = user.getBigDecimal("balance");
		log.info("用户可用余额：" + usable);
		if (usable.compareTo(pay_money_temp) < 0) {
			result.put("code", "-3");
			result.put("message", "可用余额不足，请充值后支付订单");
			return result;
		}

		// 支付金额
		final BigDecimal pay_money = pay_money_temp;

		// 支付订单
		Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {

				// 减少用户可用余额
				Db.update("update user set balance = balance - ? where id = ?", new Object[] { pay_money, user_id });

				long pay_at = System.currentTimeMillis();
				// 本地订单状态变更
				orders.set("status", 1);
				orders.set("pay_type", 1); // 余额支付
				orders.set("pay_at", pay_at);
				orders.update();
				log.info("更改订单信息成功");

				// 更改产品已参与人数
				Long product_id = orders.getLong("product_id");
				Db.update("update product set existnum = existnum + ? where id = ? ", new Object[] { 1, product_id });
				log.info("更改产品参与人数成功");

				Long project_id = orders.getLong("project_id");
				Db.update("update project set fundpcount = fundpcount + ?, fundnow = fundnow + ?  where id = ? ",
						new Object[] { 1, orders.getBigDecimal("money"), project_id });
				log.info("更改项目募集信息成功");

				// 添加用户资金流水
				Fundrecord fundrecord = new Fundrecord();
				fundrecord.put("user_id", user_id);
				fundrecord.put("before_amount", usable);
				fundrecord.put("handle_amount", pay_money);
				fundrecord.put("after_amount", usable.subtract(pay_money));
				fundrecord.put("freeze_amount", user.getBigDecimal("freeze_amount"));
				fundrecord.put("cost", 0);
				fundrecord.put("trader", user_id);
				fundrecord.put("operate_type", 3);
				fundrecord.put("order_num", order_num);
				fundrecord.put("remark", "订单支付");
				fundrecord.put("trad_at", pay_at);
				fundrecord.put("create_at", System.currentTimeMillis());
				fundrecord.save();
				log.info("加入用户资金流水");

				return true;
			}
		});

		result.put("code", "200");
		result.put("message", "订单处理成功");

		return result;
	}

	/**
	 * 支付宝回调-订单退款 1.判断退款状态 2.更改订单状态&更改订单退款记录
	 * 
	 * @param batch_no
	 * @param success_num
	 * @param result_details
	 * @return
	 */
	public Map<String, String> procesAlipayOrdersRefund(String batch_no, String success_num, String result_details,
			String notify_time) {

		log.info("订单退款处理 batch_no{},success_num{},result_details{}:" + batch_no + "," + success_num + ","
				+ result_details);
		Map<String, String> result = new HashMap<String, String>();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			long refund_at = dateFormat.parse(notify_time).getTime();
			// 解析数据集
			String[] rowList = result_details.split("\\#");
			for (String rowStr : rowList) {
				if (StringUtil.isNullOrEmpty(rowStr)) {
					continue;
				}
				try {
					Map<String, String> rs = alipayOrderRefund(batch_no, rowStr, refund_at);
					log.info("row result : " + rs);
				} catch (Exception e) {
					log.error(" 订单退款处理异常 :" + rowStr);
					e.printStackTrace();
				}
			}
			result.put("code", "200");
			result.put("message", "success");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			result.put("code", "-100000");
			result.put("message", "订单退款业务处理异常：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 支付宝回调：一个订单退款处理
	 */
	private Map<String, String> alipayOrderRefund(final String batch_no, String rowStr, final long notify_time) {
		log.info("正在处理一个订单退款 trade_no{},rowStr{}：" + batch_no + "," + rowStr);
		Map<String, String> result = new HashMap<String, String>();

		String[] row = rowStr.split("\\^");

		String trade_no = row[0];
//		String trade_money = row[1];
		String trade_status = row[2];
		log.info("trade_no:" + trade_no);
		Orders orders = Orders.dao.findOrdersByTradeNo(trade_no);
		final String order_num = orders.getStr("order_num");
		log.info("order_num:" + order_num);
		int status = orders.getInt("status");
		log.info("status:" + status);
		if (status != 6) {
			log.info("订单状态非等待退款，不可执行退款操作");
			result.put("code", "-1");
			result.put("message", "订单状态非等待退款，不可执行退款操作");
			return result;
		}

		// 支付宝交易成功或交易关闭
		if ("SUCCESS".equals(trade_status) || "TRADE_HAS_CLOSED".equals(trade_status)) {

			boolean exeBoo = Db.tx(new IAtom() {
				@Override
				public boolean run() throws SQLException {

					// 1.更改订单表状态
					int rows = Db.update("update orders set status = 7 where order_num = ? and status = 6 ",
							new Object[] { order_num });
					log.info("rows:" + rows);
					if (rows == 0) {
						log.info("修改订单表状态失败");
						return false;
					}
					// 2.更改订单退款表状态
					rows = Db.update(
							"update orders_refund set status = 2,batch_no = ?,refund_at = ? where order_num = ? and status = 1 ",
							new Object[] { batch_no, notify_time, order_num });
					log.info("rows:" + rows);
					if (rows == 0) {
						log.info("修改订单退款表状态失败");
						return false;
					}
					return true;
				}
			});

			if (exeBoo) {
				result.put("code", "200");
				result.put("message", "订单退款业务处理成功");
			}
		}
		return result;
	}

	/**
	 * 校验订单退款条件 1.校验退款订单状态等待退款 2.校验订单表状态为等待退款
	 * 
	 * @param order_num
	 *            订单号
	 */
	public boolean validDoOrderRefund(String order_num) {

		// 1.校验订单退款表
		OrdersRefund ordersRefund = OrdersRefund.dao.findOrdersRefundByOrderNum(order_num);
		if (ordersRefund.getInt("status") != 1) {
			log.info("订单退款表不是等待退款状态，不可提交退款。order_num：" + order_num);
			return false;
		}

		// 2.校验订单表
		Orders orders = Orders.dao.findOrdersByOrderNum(order_num);
		if (orders.getInt("status") != 6) {
			log.info("订单表不是等待退款状态，不可提交退款。order_num：" + order_num);
			return false;
		}

		return true;
	}

	/**
	 * 订单退款管理-处理余额支付订单退款 1.更改订单表状态为已退款 2.更改订单退款表状态为已退款 3.增加用户余额 4.添加资金流水
	 * 
	 * @param order_num
	 *            订单号
	 */
	public Map<String, String> procesBalanceOrdersRefund(final String order_num) {

		log.info("=======余额支付订单退款：order_num:" + order_num);
		Map<String, String> result = new HashMap<String, String>();
		try {
			if (validDoOrderRefund(order_num)) {

				Orders orders = Orders.dao.findOrdersByOrderNum(order_num);
				final BigDecimal refund_money = orders.getBigDecimal("money").add(orders.getBigDecimal("postage"));
				final User user = User.dao.findById(orders.getLong("user_id"));
				final long user_id = user.getLong("id");
				log.info("user_id : " + user_id);
				boolean exeBoo = Db.tx(new IAtom() {
					@Override
					public boolean run() throws SQLException {

						// 更改订单表状态
						int rows = Db.update("update orders set status = 7 where status = 6 and order_num = ?",
								new Object[] { order_num });
						log.info("rows ：" + rows);
						if (rows == 0) {
							return false;
						}

						// 更改订单退款表状态
						rows = Db.update("update orders_refund set status = 2 where status = 1 and order_num = ?",
								new Object[] { order_num });
						log.info("rows ：" + rows);
						if (rows == 0) {
							return false;
						}

						// 增加用户余额
						rows = Db.update("update user set balance = balance + ? where id = ?",
								new Object[] { refund_money, user_id });
						log.info("rows ：" + rows);
						if (rows == 0) {
							return false;
						}

						// 添加资金流水
						Fundrecord fundrecord = new Fundrecord();
						fundrecord.put("user_id", user_id);
						fundrecord.put("before_amount", user.getBigDecimal("balance"));
						fundrecord.put("handle_amount", refund_money);
						fundrecord.put("after_amount", user.getBigDecimal("balance").add(refund_money));
						fundrecord.put("freeze_amount", user.getBigDecimal("freeze_amount"));
						fundrecord.put("cost", 0);
						fundrecord.put("trader", user_id);
						fundrecord.put("operate_type", 4);
						fundrecord.put("order_num", order_num);
						fundrecord.put("remark", "订单退款");
						fundrecord.put("trad_at", System.currentTimeMillis());
						fundrecord.put("create_at", System.currentTimeMillis());
						boolean b = fundrecord.save();
						if (!b) {
							return false;
						}

						return true;
					}
				});
				if (exeBoo) {
					result.put("code", "200");
					result.put("message", "余额支付订单退款成功");
				} else {
					result.put("code", "-1");
					result.put("message", "订单处理失败order_num ：" + order_num);
				}
			} else {
				result.put("code", "-2");
				result.put("message", "订单处理失败order_num ：" + order_num);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			result.put("code", "-100000");
			result.put("message", "订单处理异常order_num ：" + order_num);
		}
		return result;
	}

	/**
	 * @param request 
	 * @支付宝充值成功后处理业务逻辑
	 * 1.更新充值预订单状态
	 * 2.新增充值订单记录
	 * 3.新增支付宝通信记录
	 * 4.新增资金流水记录
	 * 5.更新用户表中余额
	 * @version liyanlong
	 * @param user 
	 */
	public boolean procesAlipayRecharge(HttpServletRequest request) {
		//1.更新充值预订单状态
		Long user_id = updateRechargeDetailPre(request);
		//2.新增充值订单记录
		insertRechargeDetailRecord(request);
		//3.新增支付宝通信记录
		insertAlipayRecord(request);
		//4.新增资金流水记录
		insertFundrecord(request, user_id); 
		//5.更新余额
		updateUserBalance(request, user_id);
		
		return true;
	}

	private void updateUserBalance(HttpServletRequest request, Long user_id) {
		BigDecimal  recharge_money = new BigDecimal(getParam(request,"total_fee"));
		log.info("更新用户当前余额：user_id=" + user_id + ", 充值金额：" +  request.getParameter("total_fee"));
		int rows = Db.update("update user set balance = balance + ? where id = ?", new Object[] { recharge_money, user_id });		
		log.info("rows :"+rows);
	}

	private void insertFundrecord(HttpServletRequest request, Long user_id) {
		Fundrecord record = new Fundrecord();
		User user = User.dao.findById(user_id);
		record.set("user_id", user_id);
		
		BigDecimal balance = user.getBigDecimal("balance");
		record.set("before_amount", balance.floatValue());
		
		BigDecimal total_fee = new BigDecimal(getParam(request,"total_fee"));
		record.set("handle_amount", total_fee.floatValue());
		
		BigDecimal after_amount = balance.add(total_fee);
		record.set("after_amount", after_amount);
		
		record.set("freeze_amount", user.getBigDecimal("freeze_amount"));
		record.put("cost", 0);
		record.put("trader", user_id);
		record.put("operate_type", 1);
		record.put("order_num", getParam(request,"out_trade_no"));
		record.put("remark", "充值");
		record.put("trad_at", FormatUtil.strDateToLong(getParam(request, "gmt_payment")));
		record.put("create_at", System.currentTimeMillis());
		boolean exeBlo = record.save();
		log.info("exeBlo:"+exeBlo);
		log.info("加入用户资金流水, before_amount{"+balance+"},handle_amount{"+total_fee+"},after_amount{"+after_amount+"}");
		
	
	}

	private void insertRechargeDetailRecord(HttpServletRequest request) {
		RechargeDetailRecord rdr = RechargeDetailRecord.dao.findByOrdernum(getParam(request,"out_trade_no"));
		if(rdr == null){
			rdr = new RechargeDetailRecord();
			rdr.set("order_num", getParam(request,"out_trade_no"));
			rdr.set("tarde_no", getParam(request,"trade_no"));
			rdr.set("subject", getParam(request,"subject"));
			rdr.set("payment_type", getParam(request,"payment_type"));
//		String trade_status = getParam(request,"trade_status");
//		if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS"))
			rdr.set("trade_status", 2);
			rdr.set("notify_time", FormatUtil.strDateToLong(getParam(request,"notify_time")));
			rdr.set("buyer_email", getParam(request,"buyer_email"));
			rdr.set("buyer_id", getParam(request,"buyer_id"));
			rdr.set("total_fee", getParam(request,"total_fee"));
			rdr.set("create_at", System.currentTimeMillis());
			boolean b = rdr.save();
			log.info("exe blo :" +b);
		} else{
			log.info("预订单已充值 order_num："+getParam(request,"out_trade_no"));
			throw new RuntimeException("预订单已充值 order_num："+getParam(request,"out_trade_no"));
		}
	}

	private Long updateRechargeDetailPre(HttpServletRequest request) {
		RechargeDetailPre rdp = RechargeDetailPre.dao.findByOrderNum(getParam(request,"out_trade_no"));
		//状态机的跳跃判断（暂无）
		Integer status = rdp.getInt("status");
		log.info("status:"+status);
		if(status == null)throw new RuntimeException("充值预订单从null到充值成功的状态跃迁失败");
		
		//如果状态可以跳转，则跳转为2（支付成功）
		Long id = rdp.getLong("id");
		if(id == null)throw new RuntimeException("充值预订单[" + getParam(request,"out_trade_no") + "]未找到");
		int rows = Db.update("update recharge_detail_pre set status = 2 where id = ? and  (status =1 or status = 3)", new Object[]{id});
		log.info("rows:" + rows);
		if(rows == 0){
			log.info("预订单已充值 order_num："+getParam(request,"out_trade_no"));
			throw new RuntimeException("预订单已充值 order_num："+getParam(request,"out_trade_no"));
		}
		
		return rdp.getLong("user_id");
	}


	private void insertAlipayRecord(HttpServletRequest request){
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
		
		// 记录通讯日志 根据 notify_id判断是否重复通知（只记录一条重复通知）
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
			ar.put("trade_type", 2);
			ar.put("gmt_create", gmt_create);
			ar.put("create_at", System.currentTimeMillis());
			ar.save();
		} else {
			log.info("支付宝-订单付款重复通知 notify_id :" + notify_id);
		}
	}
	
	/**
	 * 编码获取参数
	 */
	private String getParam(HttpServletRequest request,String key) {
		try{
			return new String(request.getParameter(key).getBytes("ISO-8859-1"),"UTF-8");
		}catch(Exception ex){
			log.error(ex.getMessage());
			ex.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 根据多个提现Ids计算出提现方案
	 * 1.校验提现状态
	 * 2.提现->拆分提现方案
	 * @author hecj
	 */
	public void dealWithdrawalsDetail(final List<Long> withdrawalsIds, final String batch_no){
		try {
			Db.tx(new IAtom() {
				@Override
				public boolean run() throws SQLException {
					for(final long id : withdrawalsIds){
						log.info("正在计算一个提现方案 withdrawalsId："+id);
						if(validateWithdrawals(id)){
							
							List<WithdrawalsDetail> resultData = withdrawalsConvertWithdrawalsDetail(id);
							
							Withdrawals w = Withdrawals.dao.findById(id);
							// 一个批次提现，每个用户只能出现一次，防止计算提现方案时出现错误
							Withdrawals ws = Withdrawals.dao.findWithdrawalsByBatchNoAndUserId(batch_no, w.getLong("user_id"));
							if(ws != null){
								log.info("本批次用户已经有提现方案，不再重复计算提现方案，留下一批次提现。");
								continue;
							}
							
							for(WithdrawalsDetail wd : resultData){
								wd.put("batch_no", batch_no);
								wd.save();
							}
							if(resultData.size() > 0){
								w.set("status", 3);
								w.set("batch_no", batch_no);
								w.set("type", 1);
								w.set("withdrawals_at", System.currentTimeMillis());
								w.update();
							}
						}
					}
					return true;
				}
			});
		} catch (Exception e) {
			log.error("处理提现方案时出现异常："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 校验提现状态
	 * @author hecj
	 */
	private boolean validateWithdrawals(long withdrawalsId){
		
		Withdrawals w = Withdrawals.dao.findById(withdrawalsId);
		if(w == null){
			log.info("提现为空 withdrawalsId ："+withdrawalsId);
			return false;
		}
		int status = w.getInt("status");
		if(status != 1){
			log.info("提现不是通过状态，不可进行提现处理 status : "+status);
			return false;
		}
		return true;
	}
	
	/**
	 * 一个提现计算提现方案
	 * 1.分页查询提现之前用户的充值记录
	 * 2.计算提现金额是否继续查询
	 * @author hecj
	 */
	public List<WithdrawalsDetail> withdrawalsConvertWithdrawalsDetail(long withdrawalsId){
		
		List<WithdrawalsDetail> result = new ArrayList<WithdrawalsDetail>();
		
		Withdrawals w = Withdrawals.dao.findById(withdrawalsId);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("size", 50);
		params.put("user_id", w.getLong("user_id"));
		params.put("create_at", w.getLong("create_at"));
		// 提现金额
		BigDecimal amount = w.getBigDecimal("amount");
		// 根据提现金额，尝试判断是否继续查询
		for(int i = 1 ;i <= 10 ; i++){
			params.put("page", i);
			Page<RechargeDetailRecord> rechargeDetailRecordList = RechargeDetailRecord.dao.findRechargeDetailRecordByUserIdAndCreateAt(params);
			if(rechargeDetailRecordList.getList().size() == 0){
				log.info("拆分提现方案时，根据提现条件未查询到充值记录 params ：" + params);
				return result;
			}
			for(RechargeDetailRecord r : rechargeDetailRecordList.getList()){
				// 充值金额
				BigDecimal total_fee = r.getBigDecimal("total_fee");
				// 已提现金额
				BigDecimal has_amount = r.getBigDecimal("has_amount");
				// 可提现金额
				BigDecimal uable_amount = total_fee.subtract(has_amount);
				
				if(uable_amount.compareTo(new BigDecimal(0)) > 0){
					// 提现金额需小于等于全局提现金额
					if(uable_amount.compareTo(amount) > 0){
						uable_amount = amount;
					}
					// 满足本次提现业务
					WithdrawalsDetail detail = new WithdrawalsDetail();
					detail.put("withdrawals_id", withdrawalsId);
					detail.put("order_num", r.getStr("order_num"));
					detail.put("trade_no", r.getStr("tarde_no"));
					detail.put("amount", uable_amount);
					detail.put("desc", "提现");
					detail.put("status", 3);
					detail.put("create_at", System.currentTimeMillis());
					result.add(detail);
					amount = amount.subtract(uable_amount);
				}
				
				// 全局提现金额拆分为0时不再继续拆分
				if(amount.compareTo(new BigDecimal(0)) == 0){
					return result;
				}
			}
			// 全局提现金额拆分为0时不再继续拆分
			if(amount.compareTo(new BigDecimal(0)) == 0){
				return result;
			}
		}
		
		return result;
	}
	
	
	/**
	 * 支付宝回调-提现 1.判断提现状态 2.更改提现状态&更改提现明细记录
	 * @param batch_no
	 * @param success_num
	 * @param result_details
	 * @return
	 */
	public Map<String, String> procesAlipayWithdrawals(String batch_no, String success_num, String result_details,
			String notify_time) {

		log.info("提现处理 batch_no{},success_num{},result_details{}:" + batch_no + "," + success_num + ","
				+ result_details);
		Map<String, String> result = new HashMap<String, String>();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			long refund_at = dateFormat.parse(notify_time).getTime();
			// 解析数据集
			String[] rowList = result_details.split("\\#");
			for (String rowStr : rowList) {
				if (StringUtil.isNullOrEmpty(rowStr)) {
					continue;
				}
				try {
					Map<String, String> rs = alipayWithdrawals(batch_no, rowStr, refund_at);
					log.info("row result : " + rs);
				} catch (Exception e) {
					log.error("提现处理异常 :" + rowStr);
					e.printStackTrace();
				}
			}
			
			result.put("code", "200");
			result.put("message", "success");

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			result.put("code", "-100000");
			result.put("message", "提现业务处理异常：" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 支付宝回调：一个提现处理
	 * 1.更改提现详细表数据状态
	 * 2.减去用户冻结金额
	 * 3.更改提现表状态
	 * 4.增加充值表已提现金额
	 */
	private Map<String, String> alipayWithdrawals(final String batch_no, String rowStr, final long notify_time) {
		log.info("正在处理一个提现 trade_no{},rowStr{}：" + batch_no + "," + rowStr);
		Map<String, String> result = new HashMap<String, String>();

		String[] row = rowStr.split("\\^");

		final String trade_no = row[0];
		final String trade_money = row[1];
		String trade_status = row[2];
		log.info("trade_no:" + trade_no);
		final WithdrawalsDetail wd = WithdrawalsDetail.dao.findWithdrawalsDetailByBatchNoAndTradeNo(batch_no, trade_no);
		final String order_num = wd.getStr("order_num");
		log.info("order_num:" + order_num);
		int status = wd.getInt("status");
		log.info("status:" + status);
		if (status == 4) {
			log.info("提现已完成，不可重复执行提现操作");
			result.put("code", "-1");
			result.put("message", "提现已完成，不可执行提现操作");
			return result;
		}

		// 支付宝交易成功或交易关闭
		if ("SUCCESS".equals(trade_status) || "TRADE_HAS_CLOSED".equals(trade_status)) {

			boolean exeBoo = Db.tx(new IAtom() {
				@Override
				public boolean run() throws SQLException {
					
					Withdrawals w = Withdrawals.dao.findById(wd.getLong("withdrawals_id"));
					
					// 1.更改提现详细表状态
					int rows = Db.update("update withdrawals_detail set status = 4,withdrawals_success_at=? where id = ? and status = 3 ",
							new Object[] { notify_time, wd.getLong("id")});
					log.info("修改提现详细表 rows:" + rows);
					if (rows == 0) {
						log.info("更改提现详细表状态失败");
						return false;
					}
					// 2.减去用户冻结金额
					rows = Db.update("update user set freeze_amount = freeze_amount - ? where id = ? ",
							new Object[] { new BigDecimal(trade_money), w.getLong("user_id")});
					log.info("减去冻结金额 rows:" + rows);
					if (rows == 0) {
						log.info("修改订单退款表状态失败");
						return false;
					}
					
					// 4.增加充值表已提现金额
					rows = Db.update("update recharge_detail_record set has_amount = has_amount + ? where tarde_no = ?",new Object[]{trade_money, trade_no});
					if (rows == 0) {
						log.info("增加充值表已提现金额失败");
						return false;
					}
					
					if(w.getInt("status") == 3){
						w.set("status", 4);
						w.set("withdrawals_success_at", notify_time);
						w.update();
						log.info("修改提现表状态");
					} else{
						log.info("提现表状态为已提现，说明改成提现被拆分成多笔提现明细");
					}
					
					return true;
				}
			});

			if (exeBoo) {
				result.put("code", "200");
				result.put("message", "提现业务处理成功");
			} else{
				DealException dealException = new DealException();
				dealException.put("type", 3);
				dealException.put("event_id", wd.getLong("id"));
				dealException.put("desc", "提现支付宝回调错误");
				dealException.put("remark", "batch_no:"+batch_no+",rowStr:"+rowStr+",notify_time:"+notify_time);
				dealException.put("create_at", System.currentTimeMillis());
				dealException.save();
				
			}
		}
		return result;
	}
	
}
