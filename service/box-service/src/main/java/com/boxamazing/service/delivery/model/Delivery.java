package com.boxamazing.service.delivery.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.util.SqlUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class Delivery extends Model<Delivery>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(Delivery.class);
	
	public static final Delivery dao = new Delivery();
	
	/**
	 * 根据发货单id查询出对应的订单列表
	 * @return
	 */
	public List<Orders> getOrdersByDeliveryId(){
		Long deliveryId = getLong("id");
		String sql = "select * from Orders where id in (select order_id from delivery_b where delivery_id = ?)";
		try{
			List<Orders> orderList = Orders.dao.find(sql, deliveryId);
			return orderList;
		}catch(Exception e){
			log.error(e);
		}
		return new ArrayList<Orders>();
	}
	
	/**
	 * 根据发货单号查询出对应的订单列表
	 * @return
	 */
	public List<Orders> getOrdersByDeliveryNo(){
		String deliveryNo = getStr("delivery_no");
		String sql = "select * from Orders where id in (select order_id from delivery_b where delivery_id = (select id  from delivery where delivery_no = ?))";
		try{
			List<Orders> orderList = Orders.dao.find(sql, deliveryNo);
			return orderList;
		}catch(Exception e){
			log.error(e);
		}
		return new ArrayList<Orders>();
	}
	
	/**
	 * 根据发货单流水号查询出对应的订单列表
	 * @return
	 */
	public List<Orders> getOrdersByDeliveryFlowNo(){
		String deliveryFlowNo = getStr("delivery_flow_no");
		String sql = "select * from Orders where id in (select order_id from delivery_b where delivery_id =(select id  from delivery where delivery_flow_no = ?))";
		try{
			List<Orders> orderList = Orders.dao.find(sql, deliveryFlowNo);
			return orderList;
		}catch(Exception e){
			log.error(e);
		}
		return new ArrayList<Orders>();
	}

	/**
	 * 根据发货单好查询发货单
	 * @param d_delivery_no
	 * @return
	 */
	public Delivery findByDeliveryNo(String d_delivery_no) {
		return Delivery.dao.findFirst("select * from delivery where delivery_no = ?", d_delivery_no);
	}
	
	/**
	 * 根据发货单流水号查询发货单
	 * 
	 */
	public Delivery findByDeliveryFlowNo(String delivery_flow_no) {
		return Delivery.dao.findFirst("select * from delivery where delivery_flow_no = ?", delivery_flow_no);
	}
	
	/**
	 * 通过条件筛选订单
	 * By liyanlong
	 */
	public Page<Delivery> findByDeliverysParams(Map<String, ?> params) {

		String delivery_flow_no = (String) params.get("delivery_flow_no");
		String order_no = (String)params.get("order_no");
		String project_name = (String)params.get("project_name");
		String delivery_name = (String)params.get("delivery_name");
		String status = (String)params.get("status");
		int pageNumber = Integer.parseInt(params.get("page").toString());
		int pageSize = Integer.parseInt(params.get("size").toString());

		String selectBuilder = " select d.*, o.user_id as uid, d.delivery_no as '发货单号', sm.name as '物流名称', o.order_num as '订单号', d.delivery_flow_no as '流水号', p.name as '支持项目', o.pay_at as '支持时间', oa.name as '收货人', d.creat_at as '发货时间', d.status as '发货单状态' ";
		
		StringBuffer fromAndWhere = new StringBuffer();
		fromAndWhere.append(" from delivery_b db ");
		fromAndWhere.append(" left join delivery d on db.delivery_id = d.id ");
		fromAndWhere.append(" left join  order_address oa on db.order_address_id = oa.id ");
		fromAndWhere.append(" left join  ship_mode sm on oa.ship_mode = sm.id ");
		fromAndWhere.append(" left join  orders o on db.order_id = o.id  ");
		fromAndWhere.append(" left join project p on o.project_id= p.id ");

		
		StringBuffer condition = new StringBuffer(" where 1=1 ");
		List<Object> sqlParams = new ArrayList<Object>();
		if (!StringUtil.isNullOrEmpty(delivery_flow_no)) {
			condition.append(" and d.delivery_flow_no = ? ");
			sqlParams.add(delivery_flow_no);
		}
		
		if (!StringUtil.isNullOrEmpty(order_no)) {
			condition.append(" and o.order_num = ? ");
			sqlParams.add(order_no);
		}
		
		if(!StringUtil.isNullOrEmpty(project_name)){
			condition.append(" and p.name like ? ");
			sqlParams.add("%" + project_name + "%");
		}
		
		if (!StringUtil.isNullOrEmpty(delivery_name)) {
			condition.append(" and oa.name = ? ");
			sqlParams.add(delivery_name);
		}

		if (!StringUtil.isNull(status)) {
			condition.append(" and d.status = ? ");
			sqlParams.add(status);
		}

		condition.append(" order by d.creat_at desc ");
		String sqlExceptSelect = fromAndWhere.append(condition).toString();
		log.info(" 查询发货单列表 condition{} : " + (selectBuilder + sqlExceptSelect));
		try {
			Page<Delivery> list = dao.paginate(pageNumber, pageSize, selectBuilder, sqlExceptSelect, SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public Delivery findByOrderId(Long orderId){
		String sql = "select * from delivery where id in (select  delivery_id from delivery_b where order_id =?)";
		List<Delivery> deliverys = Delivery.dao.find(sql, orderId);
		if(deliverys.size() > 0){
			return deliverys.get(0);
		}
		return new Delivery();
	}
	
}
