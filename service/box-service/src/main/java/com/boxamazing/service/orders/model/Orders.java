package com.boxamazing.service.orders.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.delivery.model.Delivery;
import com.boxamazing.service.pay.model.AlipayRecord;
import com.boxamazing.service.product.model.Product;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.util.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 订单
 * 
 * @author XuXD
 * 
 */
public class Orders extends Model<Orders> {

	private static final long serialVersionUID = -3184541956091337453L;
	private static final Log log = LogFactory.getLog(Orders.class);
	public static final Orders dao = new Orders();

	/**
	 * 获取项目支持动态
	 * 
	 * @author XuXD
	 * @date 2015-10-29
	 * @param projectId
	 * @return
	 */
	public long getDynamic(long projectId) {
		log.info("[projectId:" + projectId + "]");
		String sql = "select count(1) from orders where project_id = ? and status in (1,4,5,6,7) ";
		try {
			return dao.findFirst(sql, projectId).getLong("count(1)");
		} catch (Exception e) {
			log.error("获取项目动态异常", e);
		}
		return 0;
	}
	
	/**
	 * 通过userId获取订单
	 * 
	 * @author XuXD
	 * @param userId
	 * @return
	 */
	public List<Orders> findOrdersByUser(long userId) {
		log.info("[userId:" + userId + "]");
		String sql = "select * from orders where user_id=? and shower=1 order by id desc";
		try {
			return dao.find(sql, userId);
		} catch (Exception e) {
			log.error("通过userId获取订单异常", e);
		}
		return null;
	}

	/**
	 * 通过projectId获取订单
	 * 
	 * @author XuXD
	 * @param projectId
	 * @return
	 */
	public List<Orders> findOrdersByProject(long projectId) {
		log.info("findOrders:[projectId:" + projectId + "]");
		String sql = "select * from orders where project_id=? and shower=1 order by id desc";
		try {
			return dao.find(sql, projectId);
		} catch (Exception e) {
			log.error("通过projectId获取订单异常", e);
		}
		return null;
	}

	/**
	 * 通过projectId获取订单(分页)
	 * 
	 * @author XuXD
	 * @param projectId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Orders> findByPage(long projectId, int pageNum, int pageSize) {
		log.info("[projectId:" + projectId + ",pageNum:" + pageNum + ",pageSize:" + pageSize + "]");
		String sql1 = "select o.* ,p.desc ,u.nickname,u.picture ";
		String sql2 = "from orders o ,product p ,user u where o.project_id= ? and o.product_id = p.id and o.user_id = u.id and o.status in (1,4,5,6,7) order by o.pay_at desc";
		return dao.paginate(pageNum, pageSize, sql1, sql2, projectId);
	}
	
	public Project getProject(){
		Project project = Project.dao.findById(getLong("project_id")); 
		return project;
	}
	
	/**
	 * 根据product_id获取回报
	 * 
	 * @return 回报对象
	 */
	public Product getProduct() {
		return Product.dao.findById(getLong("product_id"));
	}

	/**
	 * 根据order_num获取订单收货地址
	 * 
	 * @return 订单收货地址
	 */
	public OrderAddress getOrderAddress() {
		return OrderAddress.dao
				.findOrderAddressByOrderNum(getStr("order_num"));
	}
	
	/**
	 * 根据订单号 查询订单
	 */
	public Orders findOrdersByOrderNum(String order_num) {
		log.info("[order_num:" + order_num + "]");
		String sql = "select * from orders where order_num=? ";
		try {
			List<Orders> list = dao.find(sql, order_num);
			if(list != null && list.size() > 0){
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过userId获取订单异常", e);
		}
		return new Orders();
	}
	
	/**
	 * 根据交易号查询订单
	 */
	public Orders findOrdersByTradeNo(String trade_no) {
		log.info("[trade_no:" + trade_no + "]");
		String sql = "select * from orders where pay_num=? ";
		try {
			List<Orders> list = dao.find(sql, trade_no);
			if(list != null && list.size() > 0){
				return list.get(0);
			} else{
				// 尝试查询通讯表查询订单号
				AlipayRecord ar = AlipayRecord.dao.findAlipayRecordByTradeNo(trade_no);
				if(ar != null){
					return dao.findOrdersByOrderNum(ar.getStr("out_trade_no"));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过条件筛选订单
	 * By HECJ
	 */
	public Page<Orders> findByOrdersParams(Map<String, Object> params) {

		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		String order_num = (String) params.get("order_num");
		String project_name = (String) params.get("project_name");
		Object project_id = params.get("project_id");
		String phone = (String) params.get("phone");
		Object status = params.get("status");
		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());

		String selectSQL = "select o.*,u.id as user_id,u.nickname,p.id as project_id,p.name as project_name,p.status as project_status ";

		StringBuffer condition = new StringBuffer(" from orders o left join user u on (o.user_id=u.id) left join project p on (p.id = o.project_id) where 1=1");

		List<Object> sqlParams = new ArrayList<Object>();

		if (!StringUtil.isNullOrEmpty(project_name)) {
			condition.append(" and p.name like ? ");
			sqlParams.add("%"+project_name+"%");
		}
		
		if (!StringUtil.isNullOrEmpty(order_num)) {
			condition.append(" and o.order_num = ? ");
			sqlParams.add(order_num);
		}
		
		if (!StringUtil.isNullOrEmpty(phone)) {
			condition.append(" and u.phone = ? ");
			sqlParams.add(phone);
		}

		if (!StringUtil.isNull(status)) {
			condition.append(" and o.status = ? ");
			sqlParams.add(status);
		}

		if (!StringUtil.isNull(project_id)) {
			condition.append(" and p.id = ? ");
			sqlParams.add(project_id);
		}

		condition.append(" order by o.create_at desc ");

		log.info(" 查询订单列表 condition{} : " + condition.toString());
		try {
			Page<Orders> list = dao.paginate(pageNumber, pageSize, selectSQL, condition.toString(), SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error("status{},pageObj{},sizeObj{}:" + pageObj + "," + sizeObj);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取状态名称POJO
	 * 
	 * @return
	 */
	public String getStatusName() {
		return OrderStatus.getStatus(this.getInt("status"));
	}
	
	/**
	 * 获取状态名称POJO
	 * 
	 * @return
	 */
	public String getStatuName(Integer status) {
		return OrderStatus.getStatus(status);
	}
	
	/**
	 * 查询失效的订单
	 */
	public List<Long> findOrderByInvalidAt(int limit){
		String querySQL = " select id from orders o where status = 0 and invalid_at < UNIX_TIMESTAMP(now())*1000 order by id desc limit ? ";
		return Db.query(querySQL, new Object[]{limit});
	}

	/**
	 * 分页获取个人中心我的支持
	 * @param user_id 登录用户的id
	 * @param page 获取第page页的数据
	 * @param supportPageSize 每页能显示的数据条数
	 * @return 第page页的所有order记录
	 */
	public Page<Orders> findOrderListByPage(Long user_id, int page, int supportPageSize) {
		log.info("[user_id:" + user_id + ", page:" + page + ",pageSize:" + supportPageSize + "]");
		String sql1 = "select o.*, pj.cover_image as pj_cover_image, pj.name as pj_name, pj.status as pj_status, pj.expirestime as pj_expirestime, pd.send_days as pd_days, pd.desc as pd_desc, oa.name as oa_name, oa.phone as oa_phone, provinces.province as p_pro, cities.city as c_cit, areas.area as a_are, mode.name as md_shipmode, delivery.delivery_no as delivery_delivery_no ";
		
		StringBuffer fromSql = new StringBuffer();
		fromSql.append(" from orders  o ");
		fromSql.append(" left join project pj on pj.id = o.project_id ");
		fromSql.append(" left join product pd on pd.id = o.product_id  ");
		fromSql.append(" left join order_address oa on oa.order_num = o.order_num ");
		fromSql.append(" left join provinces provinces on oa.province = provinces.provinceid ");
		fromSql.append(" left join cities cities on oa.city = cities.cityid ");
		fromSql.append(" left join areas areas on oa.area = areas.areaid ");
		fromSql.append(" left join ship_mode mode on oa.ship_mode = mode.id ");
		fromSql.append(" left join delivery_b db on db.order_id = o.id ");
		fromSql.append(" left join delivery delivery on delivery.id = db.delivery_id ");
		fromSql.append(" where o.user_id = ? and o.shower=1 order by o.id desc ");
		return dao.paginate(page, supportPageSize, sql1, fromSql.toString(), user_id);
	}
	
	/**
	 * 根据订单查询寻对应的物流关系
	 */
	public Delivery getLogistic(){
		return Delivery.dao.findByOrderId(getLong("id"));
	}
	
	
	/**
	 * 根据id查询订单集合
	 */
	public List<Orders> findOrdersByIds(List<Long> ids) {

		String querySQL = "select * from orders where id in ("+SqlUtil.withSplit2(ids, ",")+")";
		try {
			List<Orders> list = dao.find(querySQL,
					new Object[] {});
			return list;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据发货单Id查询出对应的所有订单
	 * @param deliveryId
	 * @return
	 */
	public List<Orders> findByDeliveryId(Long deliveryId) {
		String sql = "select * from orders where id in (select order_id from delivery_b where delivery_id = ?)";
		List<Orders> ordersList = dao.find(sql, deliveryId);
		if(ordersList.size() == 0){
			ordersList.add(new Orders());
		}
		return ordersList;
	}
}
