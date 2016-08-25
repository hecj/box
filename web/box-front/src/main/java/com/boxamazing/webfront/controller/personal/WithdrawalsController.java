package com.boxamazing.webfront.controller.personal;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.pay.model.Fundrecord;
import com.boxamazing.service.pay.model.Withdrawals;
import com.boxamazing.service.user.model.User;
import com.boxamazing.webfront.common.BaseController;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;

public class WithdrawalsController extends BaseController {

	public static Log log = LogFactory.getLog(WithdrawalsController.class);

	//查看详情（用户查看提现记录的详情）
	public void lookDetail(){
		Long id = getParaToLong(0);
		Withdrawals withdrawals = Withdrawals.dao.findById(id);
		setAttr("amount", withdrawals.getBigDecimal("amount"));
		setAttr("withdrawal_at", withdrawals.getLong("withdrawals_at"));
		setAttr("verify_at", withdrawals.getLong("check_at"));
		setAttr("withdrawals_success_at", withdrawals.get("withdrawals_success_at"));
		int  status = withdrawals.getInt("status");
		setAttr("status", status);
		if(status == 4){
			render("/page/personal/personal-withdrawal-two.ftl");
		}else{
			render("/page/personal/personal-withdrawal-one.ftl");
		}		
	}

	// 判断用户输入提现金额是否合理
	public void compareBalance() {
		User user = UserUtil.getUser(getSession());
		user = User.dao.findById(user.get("id"));
		try {
			float withdrawals = Float.valueOf(getPara("withdrawals"));
			BigDecimal balance = user.getBigDecimal("balance");
			if (balance != null && balance.floatValue() > 0 && balance.floatValue() >= withdrawals) {
				renderText("true");
				return;
			}
		} catch (NullPointerException e) {
			renderText("error");
		} catch (NumberFormatException e) {
			renderText("error");
		}
		renderText("false");
	}
	
	// 提现首页，输入提现金额
	public void index() {
		User user = UserUtil.getUser(getSession());
		try {
			flushSessionUser(user.getLong("id"), getRequest());
		} catch (Exception e) {
			log.info("体现时刷新用户session异常！");
		}
		BigDecimal balance = user.getBigDecimal("balance");
		if (balance != null && balance.floatValue() > 0f) {
			setAttr("balance", balance);
		} else {
			setAttr("balance", 0);
		}
		render("/page/personal/withdrawcash.ftl");
	}

	// 用户提现
	public void userWithdrawals() {
		final String amount = getPara("amount");
		if(amount == null)return;
		User u = UserUtil.getUser(getSession());
		final User user = User.dao.findById(u.get("id"));
		final Long current_time = System.currentTimeMillis();
		
		boolean success = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				// 添加提现记录
				Long user_id = user.get("id");
				Withdrawals withdrawals = new Withdrawals();
				withdrawals.set("user_id", user_id).set("amount", amount).set("cost", 0).set("status", 0)
						.set("type", 1).set("desc", "用户提现").set("batch_no", "").set("operator", user_id)
						.set("withdrawals_at", current_time).set("create_at", current_time).save();
				log.info("添加用户提现记录");

				// 添加资金流水
				Fundrecord fundrecord = new Fundrecord();
				fundrecord.put("user_id", user_id);
				
				BigDecimal before_amount = user.getBigDecimal("balance");
				fundrecord.put("before_amount", before_amount);

				BigDecimal handle_amount = new BigDecimal(amount);
				fundrecord.put("handle_amount", handle_amount);
				
				BigDecimal after_amount = before_amount.subtract(handle_amount);
				if(after_amount.compareTo(new BigDecimal(0)) >= 0){
					fundrecord.put("after_amount", after_amount);
				}else{
					throw new SQLException("提现金额不能大于余额");
				}
				
				BigDecimal freeze_amount = user.getBigDecimal("freeze_amount").add(handle_amount);
				fundrecord.put("freeze_amount", freeze_amount);
				fundrecord.put("cost", 0);
				fundrecord.put("trader", user_id);
				fundrecord.put("operate_type", 2);
				fundrecord.put("remark", "用户提现");
				fundrecord.put("trad_at", current_time);
				fundrecord.put("create_at", current_time);
				fundrecord.save();
				log.info("加入用户提现资金流水");

				// 更新用户余额与冻结金额
				user.set("balance", after_amount).set("freeze_amount", freeze_amount).update();
				return true;
			}
		});
		
		if(success){
			renderText("true");
		}else{
			log.error("用户提现申请异常, user_id{" + user.get("id") + "}");
		}
	}
	public void withdrawalProgress(){
		User u = UserUtil.getUser(getSession());
		Withdrawals withdrawals = Withdrawals.dao.findWithdrawalsByBatchNoAndUserId("''", u.getLong("id"));
		setAttr("amount", withdrawals.get("amount"));
		setAttr("withdrawal_at", withdrawals.get("create_at"));
		render("/page/personal/personal-withdrawal-one.ftl");
	}
}
