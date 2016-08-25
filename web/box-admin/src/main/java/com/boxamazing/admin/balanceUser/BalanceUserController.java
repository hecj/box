package com.boxamazing.admin.balanceUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.common.StringUtil;
import com.boxamazing.common.interceptor.UserUtil;
import com.boxamazing.service.balance_reg.model.Balance_reg;
import com.boxamazing.service.pay.model.RechargeDetailPre;
import com.boxamazing.service.u.model.PUser;
import com.boxamazing.service.user.model.User;
import com.jfinal.core.JfinalxController;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

public class BalanceUserController extends JfinalxController{
	public static Log log = LogFactory.getLog(BalanceUserController.class);	
	
		/**
		 * 后台用户余额管理
		 * @version liyanlong
		 */
		public void index() {
			PUser puser = UserUtil.getU(getSession());
			log.info(puser.get("username")+"["+puser.toString()+"]:BalanceUserController--index(), 用户余额管理列表页");
			Long page  = 1L;
			if(this.isParaExists(0)){
				page = getParaToLong(0);
			}
			index(page);
		} 
			
		private void index(Long page) {
			String usernameORuid = getPara("usernameORuid");
			Float amount = getParaToFloat("amount");
			String regTimeS = getPara("regTimeS");
			String regTimeE = getPara("regTimeE");
			Integer payType = getParaToInt("payType");
			Integer payStatus = getParaToInt("payStatus");
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("page", page);
			params.put("size", 10);
			if(!StringUtil.isNullOrEmpty(usernameORuid)){
				params.put("usernameORuid", usernameORuid);
			}
			if(amount != null){
				params.put("amount", amount);
			}
			if(regTimeS != null){
				params.put("regTimeS", regTimeS);
			}
			if(regTimeE != null){
				params.put("regTimeE", regTimeE);
			}
			if(payType != null){
				params.put("payType", payType);
			}
			if(payStatus != null){
				params.put("payStatus", payStatus);
			}
			
			Page<RechargeDetailPre> rechargePage = RechargeDetailPre.dao.paginateByParams(params);
			setAttr("rechargePage", rechargePage);
			setAttr("usernameORuid", usernameORuid);
			setAttr("amount", amount);
			setAttr("regTimeS", regTimeS);
			setAttr("regTimeE", regTimeE);
			setAttr("payType", payType);
			setAttr("payStatus", payStatus);
			render("index.ftl");
		}

		private Float getParaToFloat(String attr) {
			return getParaToFloat(attr, null);
		}
		
		private Float getParaToFloat(String attr, Float defaultValue) {
			String value = getPara(attr);
			if (value == null || "".equals(value.trim()))
				return defaultValue;
			if (value.startsWith("N") || value.startsWith("n"))
				return -Float.parseFloat(value.substring(1));
			return Float.parseFloat(value);
		}
		
		public void look(){
			long id = getParaToLong(0);
			RechargeDetailPre rdp = RechargeDetailPre.dao.findByIdWithUser(id);
			setAttr("rdp", rdp);
			setAttr("currentpage", getParaToLong(1));
			render("rechargeList.ftl");
		}
		
		public void delete(){
			long id = getParaToLong(0);
			long currentPage = getParaToLong(1);
			
			RechargeDetailPre rdp = RechargeDetailPre.dao.findById(id);
			rdp.set("isdelete", 1);
			rdp.update();
			index(currentPage);
		}
		
		public void deleteByIds(){
			String ids = getPara(0);
			if(ids == null)return;
			if((ids = ids.trim()).endsWith(",")){
				ids = ids.substring(0, ids.length() - 1);
			}
			Db.update("update recharge_detail_pre set isdelete = 1 where id in ("+ ids +")");
			
			long currentPage = getParaToLong(1);
			index(currentPage);
		}
		//获取提现记录
		public void findCharge(){
//			String way = "提现";
//			setAttr("pg", Balance_reg.dao._page(getParaToInt(0, 1), 10 ,way,1));
			render("recharge.ftl");
		}
		
		//查找目标充值记录
		public void find(){
			getPara("search");
//			setAttr("pg", Balance_reg.dao._page(getParaToInt(0, 1), 10,getPara("search"),0));
			render("index.ftl");
		}
		
		//查找目标提现记录
		public void findRecharge(){
//			setAttr("pg", Balance_reg.dao._page(getParaToInt(0, 1), 10 ,getParaToInt("type")));
			render("recharge.ftl");
		}
		
		//编辑目标提现记录
		public void edit(){
			getPara(0);
			setAttr("bal",Balance_reg.dao.findById(getPara(0)));
		}
		
		//审核结果提交
		public void onsub(){
			getPara("id");
			getPara("shenhe");
			
			Balance_reg bal = Balance_reg.dao.findById(getPara("id"));
			bal.set("status", getParaToInt("shenhe"));
			bal.set("endtime", new Date());
			
			
			//审核成功才会扣钱，不成功则不会
			if(getParaToInt("shenhe")==2){
				User user = User.dao.findByUsername(getPara("username"));
				int balance = (Integer)user.get("balance")-getParaToInt("change");
				user.set("balance",balance);
				bal.set("balance", balance);
				user.update();
				
				/*
				 * 给支付宝发信息
				 */
			}
			bal.update();
//			String way = "提现";
//			setAttr("pg", Balance_reg.dao._page(getParaToInt(0, 1), 10 ,way,1));
			renderFreeMarker("/page/balanceUser/recharge.ftl");
			
		}
		
		/*
		 * 
		 * 支付宝处理成功才会给客户充钱
		 * 
		 */
		public void addCharge(){
			
			/*
			 * 接收支付宝返回数据
			 */
			
			Balance_reg bal= Balance_reg.dao.findById(48);
			User user = User.dao.findByUsername(bal.get("username").toString());
			user.set("balance",(Integer)bal.get("change")+(Integer)user.get("balance"));
			
			bal.set("balance",user.get("balance"));
			bal.set("sta", 1);
			bal.set("endtime", new Date());
			bal.update();
			user.update();
			renderText("处理成功");
		}
}
