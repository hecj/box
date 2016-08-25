package com.boxamazing.service.orders.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.util.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
/**
 * 订单退款表
 */
public class OrdersRefund extends Model<OrdersRefund>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(OrdersRefund.class);
	public static final OrdersRefund dao = new OrdersRefund();
	
	/**
	 * 查询订单退款记录
	 */
	public Page<Record> findByRecordParams(Map<String, Object> params) {

		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		String order_num = (String) params.get("order_num");
		String trade_no = (String) params.get("trade_no");
		String project_name = (String) params.get("project_name");
		Integer status = (Integer) params.get("status");
		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());

		String selectSQL = "select DISTINCT(ore.order_num),ore.id,ore.refund_at,ore.status,u.nickname,p.name as project_name,os.number as order_number,ore.type,ore.operator,ore.desc,os.pay_at,os.pay_type,os.money,os.postage,os.project_id,os.product_id,u.id as user_id,ar.trade_no,ar.buyer_email ";

		StringBuffer condition = new StringBuffer(" from orders_refund ore left join orders os on (ore.order_num=os.order_num) left join user u on (u.id=os.user_id) left join alipay_record ar on (ar.out_trade_no=os.order_num) left join project p on (p.id=os.project_id)  where 1=1");

		List<Object> sqlParams = new ArrayList<Object>();

		if (!StringUtil.isNullOrEmpty(order_num)) {
			condition.append(" and ore.order_num = ? ");
			sqlParams.add(order_num);
		}
		if (!StringUtil.isNullOrEmpty(trade_no)) {
			condition.append(" and ar.trade_no = ? ");
			sqlParams.add(trade_no);
		}
		if (!StringUtil.isNullOrEmpty(project_name)) {
			condition.append(" and p.name like ? ");
			sqlParams.add("%"+project_name+"%");
		}
		if(status != null){
			condition.append(" and ore.status = ? ");
			sqlParams.add(status);
		}

		condition.append(" order by ore.id desc ");
		
		log.info(" 查询订单退款列表 condition{} : " + condition.toString());
		selectSQL += condition.toString();
		try {
			Page<Record> list = Db.paginate(pageNumber, pageSize, "select *", "from ( "+selectSQL+" ) as temp", SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error("status{},pageObj{},sizeObj{}:" + pageObj + "," + sizeObj);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public List<OrdersRefund> findOrdersRefundByIds(List<Long> ids){
		
		String querySQL = "select DISTINCT(ord.order_num),ord.id,ord.type,ord.desc,ord.operator,ord.create_at,o.pay_type,o.money,o.postage,o.number,o.user_id,ar.trade_no from orders_refund ord left join orders o on (o.order_num = ord.order_num) left join alipay_record ar on (ar.out_trade_no=o.order_num) where ord.id in ("+SqlUtil.withSplit2(ids, ",")+")";
		try {
			List<OrdersRefund> list = dao.find(querySQL, new Object[]{});
			return list;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据订单号查询退款记录
	 */
	public OrdersRefund findOrdersRefundByOrderNum(String order_num){
		
		String querySQL = "select * from orders_refund where order_num = ? ";
		try {
			
			List<OrdersRefund> list = dao.find(querySQL, new Object[]{order_num});
			if(list != null && list.size() > 0){
				return list.get(0);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
}
