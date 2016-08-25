package com.boxamazing.webfront.controller.balance_reg;

import java.util.Date;
import java.util.List;
import com.boxamazing.service.balance_reg.model.Balance_reg;
import com.boxamazing.service.user.model.User;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.core.JfinalxController;

public class Balance_regController extends JfinalxController{
	
	public void index(){
		//获取session对象
		User user = UserUtil.getUser(getSession());
		String username = user.get("username");
		
		//获取消费记录信息

		List<Balance_reg> balList = Balance_reg.dao.findAll(username,"");
		setAttr("balList", balList);
		
		//获取当前余额
		setAttr("balance",user.get("balance"));
		render("index.ftl");
		
	}
	
	/*
	 * 提现
	 */
	public void drawMoney(){
		//获取当前账户余额
		User user = User.dao.findByUsername(UserUtil.getUser(getSession()).get("username").toString());
		Balance_reg bal = Balance_reg.dao.findUser(UserUtil.getUser(getSession()).get("username").toString());
		setAttr("user",user);
		
	
	}
	
	//获取提现记录
	public void drawRecord(){
		
		List<Balance_reg> list = Balance_reg.dao.findAll(UserUtil.getUser(getSession()).get("username").toString(),"提现");
		setAttr("record",list);
		int money = UserUtil.getUser(getSession()).getInt("balance");
		setAttr("money",money);
	}
	
	//充值
	public void recharge(){
		
		
		//登录时
		User user = UserUtil.getUser(getSession());
	
		//拼参数
		Balance_reg bal = new Balance_reg();
		Date date = new Date();
		bal.set("regtime", date);
		bal.set("username",user.get("username").toString());
		bal.set("contype",1);
		bal.set("change", getParaToInt("recharge"));		
		//bal.set("balance", user.get("balance"));
		bal.set("content", "充值");
		bal.set("sta",0);
		String bid = Balance_reg.dao.newBid("00",date,getParaToInt("recharge"),getPara("username"));
		bal.set("bid", bid);
		
//		int a = Integer.parseInt(getPara("recharge"));
//		int b = user.get("balance");
//		int balance = a+b;
		
//		user.set("balance", user.get("balance"));
		bal.set("balance", user.get("balance"));
		bal.save();
		
		/*
		 * 
		 * 构建数据，发送给支付宝
		 * 
		 */
		
		
		setAttr("bal",bal);
		
		
		render("recharge.ftl");
	}
	
	//提现确认并记录
	public void enDrawMoney(){

		Balance_reg bal = new Balance_reg();
		Date date = new Date();
		bal.set("regtime", date);
		bal.set("username", getPara("username"));
		bal.set("change", getParaToInt("money"));
		User user = User.dao.findByUsername(getPara("username"));
	
		int b = getParaToInt("money");		
		bal.set("balance",user.get("balance"));
		bal.set("content", "提现");
		bal.set("status",0);
		bal.set("contype",0);
		String bid = Balance_reg.dao.newBid("01",date,b,getPara("username"));
		bal.set("bid",bid);
		bal.save();
		
		
		/*
		 * 
		 * 后台审核
		 */
		
		
		setAttr("bal",bal);
	}
	
	//查看详情
	public void findDetail(){
		System.out.println(getPara("id"));
		Balance_reg bal = Balance_reg.dao.findById(getPara("id"));
		setAttr("bal",bal);
		render("enDrawMoney.ftl");
	}
	

	
}
