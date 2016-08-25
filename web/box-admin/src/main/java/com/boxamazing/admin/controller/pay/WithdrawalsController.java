package com.boxamazing.admin.controller.pay;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.common.BaseController;
import com.boxamazing.common.StringUtil;
import com.boxamazing.common.interceptor.UserUtil;
import com.boxamazing.service.open_user.model.OpenUser;
import com.boxamazing.service.open_user.model.OpenUserRelation;
import com.boxamazing.service.pay.PaymentService;
import com.boxamazing.service.pay.model.Fundrecord;
import com.boxamazing.service.pay.model.Withdrawals;
import com.boxamazing.service.pay.model.WithdrawalsDetail;
import com.boxamazing.service.u.model.PUser;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.OrdersUtils;
import com.boxamazing.util.ResultJson;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

/**
 * 提现业务
 */
public class WithdrawalsController extends BaseController {

	public static Log log = LogFactory.getLog(WithdrawalsController.class);
	
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 后台-提现列表
	 */
	public void list(){
		PUser puser = UserUtil.getU(getSession());
		try {
			long page  = 1;
			if(this.isParaExists(0)){
				page = getParaToLong(0);
			}
			
			String phone = getPara("phone");
			String nickname = getPara("nickname");
			String amount = getPara("amount");
			String start_time = getPara("start_time");
			String end_time = getPara("end_time");
			Integer status = getParaToInt("status");
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("page", page);
			params.put("size", 10);
			if(!StringUtil.isNullOrEmpty(phone)){
				params.put("phone", phone);
			}
			if(!StringUtil.isNullOrEmpty(nickname)){
				params.put("nickname", nickname);
			}
			if(!StringUtil.isNullOrEmpty(amount)){
				params.put("amount", new BigDecimal(amount));
			}
			if(!StringUtil.isNullOrEmpty(start_time)){
				params.put("start_time", format.parse(start_time).getTime());
			}
			if(!StringUtil.isNullOrEmpty(end_time)){
				params.put("end_time", format.parse(end_time).getTime());
			}
			if(status != null){
				params.put("status", status);
			}
			Page<Withdrawals> withdrawalsPage = Withdrawals.dao.findWithdrawalsByParams(params);
			
			setAttr("withdrawalsPage", withdrawalsPage);
			setAttr("phone", phone);
			setAttr("status", status);
			setAttr("nickname", nickname);
			setAttr("amount", amount);
			setAttr("start_time", start_time);
			setAttr("end_time", end_time);
			
			render("/page/pay/withdrawals/list.ftl");
		} catch (Exception e) {
			log.error(e);
			renderError(403);
		}
	}	
	
	/**
	 * 后台-提现管理
	 */
	public void manager(){
		PUser puser = UserUtil.getU(getSession());
		try {
			long page  = 1;
			if(this.isParaExists(0)){
				page = getParaToLong(0);
			}
			
			String phone = getPara("phone");
			String nickname = getPara("nickname");
			String amount = getPara("amount");
			String start_time = getPara("start_time");
			String end_time = getPara("end_time");
			Integer status = getParaToInt("status");
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("page", page);
			params.put("size", 10);
			if(!StringUtil.isNullOrEmpty(phone)){
				params.put("phone", phone);
			}
			if(!StringUtil.isNullOrEmpty(nickname)){
				params.put("nickname", nickname);
			}
			if(!StringUtil.isNullOrEmpty(amount)){
				params.put("amount", new BigDecimal(amount));
			}
			if(!StringUtil.isNullOrEmpty(start_time)){
				params.put("start_time", format.parse(start_time).getTime());
			}
			if(!StringUtil.isNullOrEmpty(end_time)){
				params.put("end_time", format.parse(end_time).getTime());
			}
			if(status != null){
				params.put("status", status);
			}
			Page<Withdrawals> withdrawalsPage = Withdrawals.dao.findWithdrawalsByParams(params);
			
			setAttr("withdrawalsPage", withdrawalsPage);
			setAttr("phone", phone);
			setAttr("status", status);
			setAttr("nickname", nickname);
			setAttr("amount", amount);
			setAttr("start_time", start_time);
			setAttr("end_time", end_time);
			
			render("/page/pay/withdrawals/manager.ftl");
		} catch (Exception e) {
			log.error(e);
			renderError(403);
		}
	}	
	
	/**
	 * 根据id查看相关提现记录
	 * @version  By YJ
	 * 
	 */
	public void finddetail(){
		String withdrawId = getPara(0);
		if(withdrawId==null||withdrawId==""){
			log.info("请求参数错误!");
			return;
		}
		
		Withdrawals withDrawals = Withdrawals.dao.findById(withdrawId);
		setAttr("withDrawals",withDrawals);
		render("finddetail.ftl");
	}
	
	/**
	 * 分页查询资金流水变动
	 * @author YJ
	 */
	
	public void findAllFundRecord(){
		ResultJson resultJson = new ResultJson();
		int pageNumber = 1;
		try{
			pageNumber = getParaToInt("pageNumber");
		}catch(Exception e){
			e.printStackTrace();
			log.info("获取页数异常!");
		}
		int pageSize = 10;
		try{
			pageSize = getParaToInt("pageSize");
		}catch(Exception e){
			e.printStackTrace();
			log.info("获取页面显示数量异常!");
		}
		Long userid = 1L;
		try{
			userid = getParaToLong("userid");
		}catch(Exception e){
			e.printStackTrace();
			log.info("获取当前操作对象异常!");
		}
		
		Page<Fundrecord> pg = Fundrecord.dao.findByUserPage(pageNumber,pageSize,userid);
		if(pg!=null){
			resultJson.setCode(200L);
			resultJson.setData(pg);
		}else{
			resultJson.setCode(404L);
		}
		
		renderJson(resultJson);
	}
	
	/**
	 * 查询提现记录
	 * @author YJ
	 */
	public void findWithDrawals(){
		//提現表id
		Long withdrawalsId = 1L;
		try{
			withdrawalsId = getParaToLong("withdrawalsId");
			log.info("提现表id为："+withdrawalsId);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String batch_no = "";
		try{
			batch_no = getPara("batch_no");
			log.info("提现批次号为："+batch_no);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		ResultJson resultJson = new ResultJson();
		List<WithdrawalsDetail> list = new ArrayList<WithdrawalsDetail>();
		list = WithdrawalsDetail.dao.findAllDetail(withdrawalsId,batch_no);
		if(list!=null){
			resultJson.setCode(200L);
			resultJson.setData(list);
		}else{
			resultJson.setCode(404L);
		}
		renderJson(resultJson);
	}
	
	/**
	 * 审核
	 * @author YJ
	 */
	public void verify(){
		String id = getPara("id");
		int pass = getParaToInt("pass");
		log.info("审核提现id为："+id+"的提现记录"+"操作为:"+pass);
		final PUser user = UserUtil.getU(getSession());
		ResultJson resultJson = new ResultJson();
		if(id!=null&&!"".equals(id)){
			final Withdrawals withDreawals = Withdrawals.dao.findById(id);
			if(withDreawals!=null&&withDreawals.getInt("status")==0){
				if(pass==0){
					//通过审核
					withDreawals.set("status", 1).set("operator",user.getLong("id")).set("check_at",System.currentTimeMillis()).update();
					resultJson.setCode(200L);
				}else if(pass==1){
					//未通过审核  //事物控制  减去冻结金额  增加到可用余额中
					Db.tx(new IAtom() {
						@Override
						public boolean run() throws SQLException {
							/* 1  修改提现表状态 */
							int rows = Db.update("update withdrawals set status = 2 ,operator = ? ,check_at = ? where id = ? ",new Object[] { user.getLong("id"), System.currentTimeMillis(), withDreawals.getLong("id")});
							log.info("修改提现表 rows:" + rows);
							if(rows==0){
								log.info("withDreawals更新失败");
								return false;
							}
							/*2 修改用户信息表  减去冻结金额  增加到可用余额中*/
							final User u = User.dao.findById(withDreawals.getLong("user_id"));
							if(u!=null){
								final BigDecimal amount = u.getBigDecimal("freeze_amount").add(withDreawals.getBigDecimal("amount").negate());
								log.info("冻结金额 - 提现金额 = "+amount);
								if(amount.floatValue() >= 0){
									int row1 = Db.update("update user set freeze_amount = freeze_amount - ? ,balance = balance + ? where id = ? ",new Object[] { withDreawals.getBigDecimal("amount"),withDreawals.getBigDecimal("amount"), withDreawals.getLong("user_id")});
									log.info("修改用户表："+row1);
									if(row1==0){
										log.info("用户可用余额更新失败");
										return false;
									}
									
									/*  3  填入资金流水表  */
									final Fundrecord fundrecord = new Fundrecord();
									fundrecord.set("user_id",withDreawals.getLong("user_id")).set("order_num",withDreawals.getLong("id")).set("before_amount",u.getBigDecimal("balance")).set("handle_amount",withDreawals.getBigDecimal("amount")).set("after_amount",u.getBigDecimal("balance").add(withDreawals.getBigDecimal("amount"))).set("freeze_amount",u.getBigDecimal("freeze_amount").add(withDreawals.getBigDecimal("amount").negate())).set("cost",withDreawals.getBigDecimal("cost")).set("trader",user.getLong("id")).set("operate_type",5).set("remark","提现未通过审核").set("trad_at",System.currentTimeMillis()).set("create_at",System.currentTimeMillis());
									if(!fundrecord.save()){
										log.info("资金流水表更新失败");
										return false;
									}		
								}else{
									log.info("当前用户冻结金额不足,提现出现问题");
									return false;
								}
							}else{
								log.info("用户信息出错：更新失败");
								return false;
							}
							return true;
						}
					});
					resultJson.setCode(200L);
				}else{
					log.info("审核状态信息出错："+pass);
					resultJson.setCode(404L);
				}
			}else{
				log.info("提现记录出错！");
				resultJson.setCode(404L);
			}
		}else{
			log.info("提现id信息出错:"+id);
			resultJson.setCode(404L);
		}
		renderJson(resultJson);
	}
	
	/**
	 * 提现处理
	 * @author hecj
	 */
	public void doWithdrawals(){
		
		String refund_message = "提现";
		
		String[] withdrawals_ids = getPara("withdrawals_ids").split(",");

		final List<Long> list = new ArrayList<Long>();
		for(String id : withdrawals_ids){
			if(!StringUtil.isNullOrEmpty(id)){
				list.add(Long.parseLong(id));
			}
		}
		log.info("支付宝提现Id："+list);
		try {
			
			PaymentService paymentService = new PaymentService();
			
			if(list.size() > 0){
				// 拆分后的提现明细
				// 批次号
				String batch_no = OrdersUtils.refundBatchNo();
				log.info("提现批次号："+batch_no);
				paymentService.dealWithdrawalsDetail(list, batch_no);
				List<WithdrawalsDetail> detailList = WithdrawalsDetail.dao.findWithdrawalsDetailByBatchNo(batch_no);
				log.info("本批次提现详细列表数 size ："+detailList.size());
				if(detailList != null && detailList.size() > 0){
					
					// 组装支付宝退款
					String detail_data = "";
					for(WithdrawalsDetail d : detailList){
						String trade_no = d.getStr("trade_no");
						BigDecimal amount = d.getBigDecimal("amount");
						detail_data += trade_no+"^"+amount+"^"+refund_message+"#";
					}
					
					if(detail_data.endsWith("#")){
						detail_data = detail_data.substring(0, detail_data.length() - 1);
					}
					
					// 退款当天日期
					String refund_date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
					log.info("refund_date:"+refund_date);
					log.info("detail_data : "+detail_data);
					
					String sHtmlText = paymentService.createWithdrawalsAlipayForm(refund_date, batch_no, detail_data, detailList.size());
					renderHtml(sHtmlText);
					return;
				}
				
			} else{
				log.info("需要提现的记录不存在，请核实");
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		renderError(403);
	}
}
