package com.boxamazing.webfront.controller.pay;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.PasswordUtil;
import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.pay.OrderService;
import com.boxamazing.service.pay.PaymentService;
import com.boxamazing.service.pay.model.Fundrecord;
import com.boxamazing.service.pay.model.RechargeDetailPre;
import com.boxamazing.service.pay.model.Withdrawals;
import com.boxamazing.service.payPwd.model.UserPayPwd;
import com.boxamazing.service.product.model.Product;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.sms.SmsService;
import com.boxamazing.service.sms.model.NoticeTemplate;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.Constant;
import com.boxamazing.service.util.Template;
import com.boxamazing.webfront.common.BaseController;
import com.boxamazing.webfront.interceptor.LoginInterceptor;
import com.boxamazing.webfront.util.CheckCode;
import com.boxamazing.webfront.util.CheckNum;
import com.boxamazing.webfront.util.Code;
import com.boxamazing.webfront.util.OrdersUtils;
import com.boxamazing.webfront.util.ResultJson;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;

/**
 * 支付相关业务
 * 
 * @author HECJ
 */
@Before(LoginInterceptor.class)
public class PaymentController extends BaseController {

	public static Log log = LogFactory.getLog(PaymentController.class);

	public void index() {

	}

	/**
	 * 我的账户-充值金额填写页面 充值页面一
	 */
	public void recharge() {
		//获取充值当前页数
		Integer recharge_pageNumber = 1;
		if (this.isParaExists(0)) {
			try{recharge_pageNumber = getParaToInt(0);}catch(Exception e){recharge_pageNumber = 1;};
			recharge_pageNumber = recharge_pageNumber == null ? 1 : recharge_pageNumber;
		}
		
		//获取提现当前页数
		Integer withdrawals_pageNumber = 1;
		if(this.isParaExists(1)){
			try{withdrawals_pageNumber = getParaToInt(1);}catch(Exception e){withdrawals_pageNumber = 1;};
			withdrawals_pageNumber = withdrawals_pageNumber == null ? 1 : withdrawals_pageNumber;
		}
		
		//获取当前页面为充值还是提现
		String currPage = "l";
		if(this.isParaExists(2)){
			currPage = getPara(2);
			if("r".equals(currPage)){
				setAttr("tab", 1);
			}else{
				setAttr("tab", 0);
			}
		}
		final int pageSize = 10;

		User user = UserUtil.getUser(getSession());
		user = User.dao.findById(user.getLong("id"));
		UserUtil.setUser(user, getSession());
		setAttr("user", user);

		// 余额记录，分页条数为10
		Page<Fundrecord> fundrecordPage = Fundrecord.dao.findByPage(recharge_pageNumber, pageSize, user.getLong("id"));
		//如果获取的页数大于实际的总页数，则显示第一页的数据
		if(fundrecordPage.getTotalPage() < recharge_pageNumber){
			fundrecordPage = Fundrecord.dao.findByPage(1, pageSize, user.getLong("id"));
		}
		if (fundrecordPage.getList().size() > 0) {
			setAttr("fundrecordPage", fundrecordPage);
		}
		
		//提现记录，分页条数为10
		Page<Withdrawals> withdrawalsPage = Withdrawals.dao.findByPage(withdrawals_pageNumber, pageSize, user.getLong("id"));
		if(withdrawalsPage.getTotalPage() < withdrawals_pageNumber){
			withdrawalsPage = Withdrawals.dao.findByFirstPage(pageSize, user.getLong("id"));
		}
		if (withdrawalsPage.getList().size() > 0) {
			setAttr("withdrawalsPage", withdrawalsPage);
		}
		
		render("/page/payment/account_1.ftl");
	}

	/**
	 * 我的账户-充值-充值页面跳转到第二
	 * 个页面，该页面判断支付密码是否存在，
	 * 如果不存在，则要显示支付密码的窗口
	 */
	public void exist_pay_pwd(){
		User user = UserUtil.getUser(getSession());
		UserPayPwd pay_pwd = UserPayPwd.dao.findByUserId(user.getLong("id"));
		// 判断是否存在支付密码
		if (pay_pwd == null) {
			setAttr("exist_pay_pwd", 0);
		} else {
			setAttr("exist_pay_pwd", 1);
		}
		
		// 判断是否存在手机号
		String phone = user.get("phone");
		if (phone == null) {
			setAttr("exist_phone", 0);
		} else {
			setAttr("phone", phone);
			setAttr("exist_phone", 1);
		}
		render("/page/payment/account_3.ftl");
	}
	
	/**
	 *充值支付方式选择 三
	 */
	public void choosePayType(){
		String recharge = getPara("amount");
		try{			
			float amount = Float.valueOf(recharge);
			setAttr("money", amount);
			setAttr("order_num", OrdersUtils.orderRecharge(UserUtil.getUser(getSession()).getLong("id")));
		}catch(NumberFormatException e){
			log.error("充值金额输入有误，输入的值无法转化为float类型, Float.valueOf("+recharge+")出现异常！");
			throw new RuntimeException("充值金额输入有误，输入的值无法转化为float类型");
		}
		render("/page/payment/account_4.ftl");
	}
	
	/**
	 * 我的账户-充值-支付确认页面 充值页面四
	 */
	public void rechargesub() {
		User user = UserUtil.getUser(getSession());
		Long user_id = -1l;
		try {
			user_id = user.getLong("id");
			log.info("user_id{} : " + user_id);

			String amount = getPara("amount");
			log.info("充值金额amount{}:" + amount);
			String order_num = getPara("ordernum");
			// 生成预充值订单recharge_detail_pre
			RechargeDetailPre rdp = new RechargeDetailPre();
			long currentTime = System.currentTimeMillis();
			rdp.set("user_id", user_id).set("order_num", order_num).set("amount", amount).set("counter_fee", 0)
					.set("counter_fee", 0).set("type", 1).set("status", 1).set("ip_address", getRemortIp(getRequest()))
					.put("desc", "支付宝充值").set("create_at", currentTime).set("update_at", currentTime).set("isdelete", 0).save();

			// 订单失效时间
			int valid_min = 30;
			// 同步回调地址
			String return_url = Constant.WEBROOT_URL + "alipay/pay_recharge_return_url"; // 同步通知处理页面
			String notify_url = Constant.WEBROOT_URL + "alipay/pay_recharge_notify_url";  // 异步通知处理页面
			// 支付宝支付
			PaymentService paymentService = new PaymentService();
			String sHtmlText = paymentService.createOrderAlipayForm(order_num, "充值", amount, "", "", notify_url,
					return_url, valid_min);
			renderHtml(sHtmlText);
		} catch (Exception e) {
			log.error("user_id{} : " + user_id + " - " + e.getMessage());
			log.info("充值非法");
			renderNull();
		}
	}

	/**
	 * 充值成功后判断是否成功
	 */
	public void rechargeFinish(){
		String order_num = getPara("ordernum");
		RechargeDetailPre rdp = RechargeDetailPre.dao.findByOrderNum(order_num);
		if(rdp == null)renderText("false");
		if(rdp.getInt("status").intValue() == 2){
			renderText("true");
		}else{
			renderText("false");
		}
	}
	
	/**
	 * 充值成功
	 */
	public void rechargeOK() {
		String order_num = getPara(0);
		RechargeDetailPre rdp = RechargeDetailPre.dao.findByOrderNum(order_num);
		setAttr("amount", rdp.getBigDecimal("amount"));
		render("/page/payment/account_5.ftl");
	}
	
	/**
	 * 判断用户是否设置支付密码
	 */
	public void checkSetPayPwd() {
		User user = UserUtil.getUser(getSession());
		Long user_id = -1l;
		try {
			user_id = user.getLong("id");
			log.info("user_id{} : " + user_id);

			UserPayPwd payPwd = UserPayPwd.dao.findUserPayPwdByUserId(user_id);

			if (payPwd == null) {
				log.info("user_id{} : " + user_id + " - 用户未设置支付密码");
				renderJson(new ResultJson(Code.F_22, "用户未设置支付密码"));
			} else {
				renderJson(new ResultJson(Code.S_200, "用已设置支付密码"));
			}
		} catch (Exception e) {
			log.error("user_id{} : " + user_id + " - " + e.getMessage());
			renderJson(new ResultJson(Code.F_100000, "服务器异常"));
			e.printStackTrace();
		}
	}

	/**
	 * 获取短信验证码
	 */
	public void sendCheckCode() {

		User user = UserUtil.getUser(getSession());
		Long user_id = -1l;
		try {
			user_id = user.getLong("id");
			log.info("user_id{} : " + user_id);
			String phone = getPara("phone");
			log.info("phone{} : " + phone);
			if (!CheckNum.isPhone(phone)) {
				renderJson(new ResultJson(Code.F_01, "手机号输入有误"));
				return;
			}

			String randomCode = OrdersUtils.getRandom(6);
			log.info("randomCode{} : " + randomCode);
			NoticeTemplate noticeTemplate = NoticeTemplate.dao.findByTempName(Template.SEND_CHECK_CODE);
			String content = noticeTemplate.getStr("content");
			content = content.replace("{CHECK_CODE}", randomCode);

			CheckCode checkCode = new CheckCode();
			checkCode.setUser_id(user_id);
			checkCode.setPhone(phone);
			checkCode.setRandomCode(randomCode);
			checkCode.setSendTime(System.currentTimeMillis());

			getSession().setAttribute(phone, checkCode);

			// 发送验证码
			SmsService.sendSms(content, phone);

			renderJson(new ResultJson(Code.S_200, "已发送"));

		} catch (Exception e) {
			log.error("user_id{} : " + user_id + " - " + e.getMessage());
			e.printStackTrace();
			renderJson(new ResultJson(Code.F_100000, "服务器异常"));
		}
	}

	/**
	 * 充值-设置支付密码
	 */
	public void setPayPwd() {

		String phone = getPara("phone");
		String pay_pwd = getPara("pay_pwd");
		String re_pay_pwd = getPara("re_pay_pwd");
		String check_code = getPara("check_code");

		User user = UserUtil.getUser(getSession());
		Long user_id = -1l;
		try {
			user_id = user.getLong("id");
			log.info("user_id{},phone{}: " + user_id + "," + phone);

			// validate
			if (!pay_pwd.equals(re_pay_pwd)) {
				log.info("支付密码与确认支付密码不一致");
				renderJson(new ResultJson(Code.F_01, "支付密码与确认支付密码不一致"));
				return;
			}

			if (StringUtil.isNullOrEmpty(pay_pwd)) {
				log.info("支付密码不能为空");
				renderJson(new ResultJson(Code.F_01, "支付密码不能为空"));
				return;
			}

			if (pay_pwd.length() < 6 || pay_pwd.length() > 20) {
				log.info("支付密码的长度必须在6~20位之间");
				renderJson(new ResultJson(Code.F_01, "支付密码的长度必须在6~20位之间"));
				return;
			}

			if (StringUtil.isNullOrEmpty(re_pay_pwd)) {
				log.info("确认支付密码不能为空");
				renderJson(new ResultJson(Code.F_01, "确认支付密码不能为空"));
				return;
			}

			if (re_pay_pwd.length() < 6 || re_pay_pwd.length() > 20) {
				log.info("确认支付密码的长度必须在6~20位之间");
				renderJson(new ResultJson(Code.F_01, "确认支付密码的长度必须在6~20位之间"));
				return;
			}

			if (CheckNum.isPhone(phone)) {
				log.info("手机号输入有误phone{}:" + phone);
				renderJson(new ResultJson(Code.F_01, "手机号输入有误"));
				return;
			}

			// 验证短信验证码
			final long InvalidTime = 10 * 60 * 1000l; // 验证码失效时间10 min
			CheckCode checkCode = (CheckCode) getSession().getAttribute(phone);
			if ((checkCode.getSendTime() + InvalidTime) < System.currentTimeMillis()) {
				log.info("验证码已失效sendtime{},currTime{}:" + checkCode.getSendTime() + "," + System.currentTimeMillis());
				renderJson(new ResultJson(Code.F_00, "验证码已失效"));
				return;
			}

			if (!checkCode.getRandomCode().equals(check_code)) {
				log.info("验证码不正确RandomCode{},check_code{}:" + checkCode.getRandomCode() + "," + check_code);
				renderJson(new ResultJson(Code.F_01, "验证码不正确"));
				return;
			}

			// set pay pwd
			UserPayPwd payPwd = UserPayPwd.dao.findUserPayPwdByUserId(user_id);
			// save & update
			if (payPwd == null) {
				payPwd = new UserPayPwd();
				payPwd.put("user_id", user_id);
				payPwd.put("phone", phone);
				payPwd.put("pay_pwd", PasswordUtil.encryptPassword(pay_pwd));
				payPwd.put("create_at", System.currentTimeMillis());
				payPwd.put("update_at", System.currentTimeMillis());
				payPwd.save();
			} else {
				payPwd.set("pay_pwd", PasswordUtil.encryptPassword(pay_pwd));
				payPwd.set("update_at", System.currentTimeMillis());
				payPwd.update();
			}
			log.info("设置支付密码成功user_id{}:" + user_id);
			renderJson(new ResultJson(Code.S_200, "设置支付密码成功"));
			return;
		} catch (Exception e) {
			log.error("user_id{} : " + user_id + " - " + e.getMessage());
			e.printStackTrace();
			renderJson(new ResultJson(Code.F_100000, "服务器异常"));
		}
		return;
	}

	/**
	 * 支付订单 4 逻辑： 支付方式：1、余额支付 a) 判断支付密码 b) 判断余额 c) 支付失败 返回支付选择页面 2、第三方支付 c)
	 * 链接到支付宝 d) 我方提供支付回调接口 e) 支付成功回调到个人中心 by HECJ
	 */
	@Before(POST.class)
	public void order() {

		final User user = UserUtil.getUser(getSession());
		final long user_id = user.getLong("id");
		log.info("用户提交订单user_id：" + user_id);
		try {
			String order_num = getPara(0);
			Orders orders = Orders.dao.findOrdersByOrderNum(order_num);
			if (orders == null) {
				setAttr("order_message", "订单不存在，订单号：" + order_num);
				redirect("/page_404");
				return;
			}

			// 校验订单状态
			if (orders.getInt("status") != 0) {
				log.info("订单状态不可支付" + order_num + ",status:" + orders.getInt("status"));
				setAttr("order_message", "订单状态不可支付" + order_num);
				// 订单不可支付
				redirect("/orders");
				return;
			}

			long invalid_at = orders.getLong("invalid_at");
			log.info("订单失效时间invalid_at：" + invalid_at);
			long curr_time = System.currentTimeMillis();
			log.info("当前时间curr_time:" + curr_time);
			// 校验订单超时
			if (invalid_at < curr_time) {
				log.info("订单已失效，订单号：" + order_num);
				setAttr("order_message", "订单已失效，订单号：" + order_num);
				redirect("/page_404");
				return;
			}

			Long product_id = orders.getLong("product_id");
			log.info("product_id:" + product_id);
			final Product product = Product.dao.findById(product_id);
			if (product == null) {
				renderError(404);
				return;
			}

			Project project = Project.dao.findById(product.getLong("project_id"));

			// 前置校验
			OrderService orderService = new OrderService();
			Map<String, String> valid_result = orderService.validGPaymentOrders(user_id, product_id);
			if (!valid_result.get("code").equals("200")) {
				setAttr("order_message", valid_result.get("message"));
				redirect("/page_404");
				return;
			}

			// 支付类型
			int payment_type = getParaToInt("payment_type");
			log.info("payment_type:" + payment_type);
			// 收货地址
			final String receiveAddressId = getPara("receiveAddressId");
			log.info("receiveAddressId:" + receiveAddressId);
			// 支付金额
			BigDecimal pay_amount = orders.getBigDecimal("money");
			pay_amount = pay_amount.add(orders.getBigDecimal("postage"));

			log.info("支付方式payment_type：" + payment_type);
			if (payment_type == 1) {
				// 余额支付
				// 支付密码
				String payment_password = getPara("payment_password");
				PaymentService paymentService = new PaymentService();
				Map<String, String> result = paymentService.procesUserOrder(order_num, payment_password);
				log.info("result:" + result);
				if (!result.get("code").equals("200")) {
					setAttr("order_message", result.get("message"));
					redirect("/page_404");
					return;
				}
				redirect("/payment/ordersuccess/" + order_num);

			} else if (payment_type == 2) {
				// 支付宝支付
				String show_url = Constant.WEBROOT_URL + "project/detail/" + project.getLong("id");
				String subject = project.getStr("name");
				String body = product.getStr("desc");
				PaymentService paymentService = new PaymentService();

				// 同步回调地址
				String return_url = Constant.WEBROOT_URL + "alipay/pay_order_return_url";
				String notify_url = Constant.pay_order_notify_url;
				// 订单失效时间
				int valid_min = getPayValidMin(invalid_at);
				String sHtmlText = paymentService.createOrderAlipayForm(order_num, subject, pay_amount.toString(), body,
						show_url, notify_url, return_url, valid_min);
				renderHtml(sHtmlText);
				return;

			} else {
				log.info("支付方式错误payment_type：" + payment_type);
				setAttr("order_message", "支付方式错误");
				redirect("/page_404");
				return;
			}

		} catch (Exception e) {
			log.error("user_id{} : " + user_id + " - " + e.getMessage());
			e.printStackTrace();
			setAttr("order_message", "订单处理失败");
			renderError(403);
		}
	}

	/**
	 * 订单成功页面
	 */
	public void ordersuccess() {
		User user = UserUtil.getUser(getSession());
		try {

			String order_num = getPara(0);
			Orders orders = Orders.dao.findOrdersByOrderNum(order_num);
			if (orders == null) {
				forwardAction("/page_404");
				return;
			} else {
				if (user.getLong("id").compareTo(orders.getLong("user_id")) != 0) {
					redirect("/page_404");
					return;
				}
			}

			render("/page/payment/cf-pay-success.ftl");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderError(403);
		}
	}

	/**
	 * 计算支付超时时间 invalid_at 订单失效时间
	 */
	private int getPayValidMin(long invalid_at) {

		long min = (invalid_at - System.currentTimeMillis()) / 1000 / 60;
		log.info("订单交易失效时间：" + min);
		return (int) min;
	}

	/**
	 * 充值成功页面
	 */
	public void rechargesuccess() {
		User user = UserUtil.getUser(getSession());
		try {
			String order_num = getPara(0);
			RechargeDetailPre rdp = RechargeDetailPre.dao.findByOrderNum(order_num);
			if (rdp == null) {
				forwardAction("/page_404");
				return;
			} else {
				if (user.getLong("id").compareTo(rdp.getLong("user_id")) != 0) {
					redirect("/page_404");
					return;
				}
			}
			
			//更新用户session(更新了余额)，由于异步通常要比同步快1到2秒钟，所以此处直接刷新session基本能保证为最新session,但并非100%
			flushSessionUser(user.getLong("id"), getRequest());
			
			render("/page/payment/rechargeOK.ftl");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderError(403);
		}
	}
}
