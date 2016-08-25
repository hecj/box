package com.boxamazing.service.pay;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.orders.model.OrderAddress;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.product.model.Product;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.user.model.ReceiveAddress;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.Constant;
import com.boxamazing.service.util.OrdersUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;

/**
 * 订单业务处理
 */
public class OrderService {

	public static Log log = LogFactory.getLog(OrderService.class);

	/**
	 * @校验生成订单条件： 1.众筹中的项目 2.众筹时间未到期 3.产品名额未满，4.不可以购买自己发布的项目
	 */
	public Map<String, String> validGenerateOrders(long user_id, long product_id) {

		log.info("生成订单校验 user_id{},product_id{}:" + user_id + "," + product_id);
		Map<String, String> result = new HashMap<String, String>();

		try {

			Product product = Product.dao.findById(product_id);
			Project project = Project.dao.findById(product.getLong("project_id"));

			// 1.众筹中的项目
			if (project.getInt("status") != 8) {
				log.info("该项目不在众筹中，生成订单失败");
				result.put("code", "-1");
				result.put("message", "该项目不在众筹中，生成订单失败");
				return result;
			}

			// 2.众筹时间未到期
			Long expirestime = project.getLong("expirestime");
			if (System.currentTimeMillis() > expirestime) {
				log.info("该项目众筹日期已到，生成订单失败");
				result.put("code", "-2");
				result.put("message", "该项目众筹日期已到，生成订单失败");
				return result;
			}

			// 3.产品名额未满
			int totalnum = product.getInt("totalnum");
			log.info("totalnum{}:" + totalnum);
			if (totalnum > 0) {
				// 参与人数
				int existnum = product.getInt("existnum");
				log.info("该产品参与人数 existnum{}:" + existnum);
				if (existnum >= totalnum) {
					log.info("该产品订单名额已满，生成订单失败");
					result.put("code", "-3");
					result.put("message", "该产品订单名额已满，生成订单失败");
					return result;
				}
			}
			
			// 4.不可以购买自己发布的项目
			if(project.getLong("user_id").compareTo(user_id) == 0){
				log.info("不可以购买自己发布的项目");
				result.put("code", "-4");
				result.put("message", "不可以购买自己发布的项目");
				return result;
			}

			result.put("code", "200");
			result.put("message", "订单校验成功");
			log.info(result);
		} catch (Exception e) {
			log.error("生成订单校验失败" + e.getMessage());
			e.printStackTrace();
			result.put("code", "-100000");
			result.put("message", "订单处理异常");
		}

		return result;
	}

	/**
	 * @生成订单
	 * @param user_id 用户id
	 * @param product_id产品id
	 * @param receiveAddressId 收货地址id
	 */
	public Map<String, String> generateOrder(long user_id, long product_id,final Long receiveAddressId) {
		
		log.info("正在生成订单user_id："+user_id+",product_id:"+product_id+",receiveAddressId:"+receiveAddressId);
		final Map<String, String> result = new HashMap<String, String>();
		try{
			// 前置校验
			Map<String, String> valid_result = validGenerateOrders(user_id,product_id);
			if(!valid_result.get("code").equals("200")){
				return valid_result;
			}
	
			// 订单号
			final String order_num = OrdersUtils.orderPayNum(user_id);
			
			final User user = User.dao.findById(user_id);
			final Product product = Product.dao.findById(product_id);
			
			// 事务
			Db.tx(new IAtom() {
				@Override
				public boolean run() throws SQLException {
					long curr_time = System.currentTimeMillis();
					// 保存订单
					Orders orders = new Orders();
					orders.put("user_id", user.get("id"));
					orders.put("project_id", product.getLong("project_id"));
					orders.put("product_id", product.getLong("id"));
					// 订单金额+运费
					BigDecimal money = product.getBigDecimal("fund");
					BigDecimal postage = product.getBigDecimal("postage");
					orders.put("money", money);
					orders.put("postage", postage);
					// 未支付
					orders.put("status", 0);
					orders.put("shower", 1);
					orders.put("number", 1);
					orders.put("order_num", order_num); // 订单号
					orders.put("pay_type", -1); // 支付类型：-1未知
					orders.put("invalid_at", curr_time + Constant.order_invalid_time ); // 订单失效时间
					orders.put("create_at", curr_time);
					orders.put("update_at", System.currentTimeMillis());
					boolean exe_result = orders.save();
					if(!exe_result){
						log.info("订单保存失败,【回滚】");
						result.put("code", "-1");
						result.put("message", "保存订单失败");
						return false;
					}
					log.info("保存订单：" + orders.getLong("id"));
					// 实物保存收货地址
					if (product.getInt("type") == 0) {
						// 保存收货地址
						ReceiveAddress receiveAddress = ReceiveAddress.dao.findById(receiveAddressId);
						OrderAddress orderAddress = new OrderAddress();
						orderAddress.put("receive_address_id", receiveAddressId);
						orderAddress.put("order_num", order_num);
						orderAddress.put("name", receiveAddress.get("name"));
						orderAddress.put("phone", receiveAddress.get("phone"));
						orderAddress.put("province", receiveAddress.get("province"));
						orderAddress.put("city", receiveAddress.get("city"));
						orderAddress.put("area", receiveAddress.get("area"));
						orderAddress.put("zipcode", receiveAddress.get("zipcode"));
						orderAddress.put("detail_address",receiveAddress.get("detail_address"));
						orderAddress.put("create_at", System.currentTimeMillis());
						orderAddress.save();
						log.info("保存订单收货地址：" + orderAddress.getLong("id"));
					}
	
					// 判断产品是否限制库存
					int rows = 0;
					if(product.getInt("totalnum") == 0){
						// 不限制库存
						// 更改产品参与人数(减少库存)
						rows = Db.update("update product set existnum = existnum + 1 where id = ?", new Object[]{product.getLong("id")});
					} else{
						// 限制库存
						// 更改产品参与人数(减少库存)
						rows = Db.update("update product set existnum = existnum + 1 where id = ? and existnum < totalnum", new Object[]{product.getLong("id")});
					}
					log.info("更改库存数量："+rows);
					if(rows == 0){
						log.info("更改库存时，减少库存失败,【回滚】");
						result.put("code", "-2");
						result.put("message", "更改库存时，减少库存失败");
						return false;
					}
					
					return true;
				}
			});
			
			result.put("code", "200");
			result.put("order_num", order_num);
			result.put("message", "生成订单成功");
		} catch(Exception ex){
			ex.printStackTrace();
			result.put("code", "-100000");
			result.put("message", "生成订单异常："+ex.getMessage());
		}
		log.info("result :"+result);
		return result;
	}
	
	
	/**
	 * @校验支付订单条件： 1.众筹中的项目 2.众筹时间未到期 3.不可以购买自己发布的项目
	 */
	public Map<String, String> validGPaymentOrders(long user_id, long product_id) {

		log.info("支付订单校验 user_id{},product_id{}:" + user_id + "," + product_id);
		Map<String, String> result = new HashMap<String, String>();

		try {

			Product product = Product.dao.findById(product_id);
			Project project = Project.dao.findById(product.getLong("project_id"));

			// 1.众筹中的项目
			if (project.getInt("status") != 8) {
				log.info("该项目不在众筹中，生成订单失败");
				result.put("code", "-1");
				result.put("message", "该项目不在众筹中，生成订单失败");
				return result;
			}

			// 2.众筹时间未到期
			Long expirestime = project.getLong("expirestime");
			if (System.currentTimeMillis() > expirestime) {
				log.info("该项目众筹日期已到，生成订单失败");
				result.put("code", "-2");
				result.put("message", "该项目众筹日期已到，生成订单失败");
				return result;
			}

			// 3.不可以购买自己发布的项目
			if(project.getLong("user_id").compareTo(user_id) == 0){
				log.info("不可以购买自己发布的项目");
				result.put("code", "-4");
				result.put("message", "不可以购买自己发布的项目");
				return result;
			}

			result.put("code", "200");
			result.put("message", "订单校验成功");
			log.info(result);
		} catch (Exception e) {
			log.error("生成订单校验失败" + e.getMessage());
			e.printStackTrace();
			result.put("code", "-100000");
			result.put("message", "订单处理异常");
		}

		return result;
	}
	
	
	/**
	 * 过期订单处理
	 * 1.订单已过期
	 * 2.未付款订单才可以取消订单
	 * 业务处理：
	 *        a.修改订单状态未订单取消
	 *        b.回收订单增加库存
	 */
	public Map<String,String> cancelOrder(final long order_id){
		
		final Map<String,String> result = new HashMap<String,String>();
		try {
			log.info("=========正在处理过期订单 Id （"+order_id+ "）==============");
			Orders orders = Orders.dao.findById(order_id);
			
			if(orders == null){
				log.info("订单不存在，取消失败，order_id："+order_id);
				result.put("code", "-1");
				result.put("message", "订单不存在，取消失败，order_id："+order_id);
				return result;
			}
			
			// 订单失效时间
			long invalid_at = orders.getLong("invalid_at");
			// 当前时间
			long curr_time = System.currentTimeMillis();
			if(invalid_at > curr_time){
				log.info("订单未到期，不可取消订单 invalid_at ："+invalid_at+" , curr_time :"+curr_time);
				result.put("code", "-2");
				result.put("message", "订单未到期，不可取消订单 invalid_at ："+invalid_at+" , curr_time :"+curr_time);
				return result;
			}
			
			// 订单状态
			int status = orders.getInt("status");
			log.info("订单状态 status ："+status);
			if (status != 0){
				log.info("未付款的订单才可取消订单，该订单状态不可取消");
				result.put("code", "-3");
				result.put("message", "未付款的订单才可取消订单，该订单状态不可取消");
				return result;
			}
			
			final long product_id = orders.getLong("product_id");
			
			// 取消订单事务处理
			Db.tx(new IAtom() {
				
				@Override
				public boolean run() throws SQLException {
					
					// 修改订单状态
					int rows = Db.update(" update orders set status = 3 where id = ? and status = 0 ", new Object[]{order_id});
					log.info(" exe orders rows : "+rows);
					if(rows == 0){
						log.info("修改订单状态处理失败，回滚");
						result.put("code", "-4");
						result.put("message", "修改订单状态处理失败，【回滚】");
						return false;
					}
					
					// 增加库存
					rows = Db.update(" update product set existnum = existnum -1 where id = ? and existnum > 0 ", new Object[]{product_id});
					log.info(" exe product rows : "+rows);
					if(rows == 0){
						log.info("修改产品库存处理失败，回滚");
						result.put("code", "-5");
						result.put("message", "修改产品库存处理失败，【回滚】");
						return false;
					}
					
					result.put("code", "200");
					result.put("message", "取消订单处理成功，【提交】");
					return true;
				}
			});
		} catch (Exception e) {
			log.error("过期订单处理异常，商户订单号："+order_id+" , "+e.getMessage());
			e.printStackTrace();
			result.put("code", "-100000");
			result.put("message", "过期订单处理异常，商户订单号："+order_id+" , "+e.getMessage());
		}
		log.info(" result : "+result);
		return result;
	}

}
