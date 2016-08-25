package com.boxamazing.service.orders.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.code.Areas;
import com.boxamazing.service.code.Cities;
import com.boxamazing.service.code.Provinces;
import com.jfinal.plugin.activerecord.Model;

/**
 * 订单的收货地址
 * by HECJ
 */
public class OrderAddress extends Model<OrderAddress> {
	
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(OrderAddress.class);
	
	public static final OrderAddress dao = new OrderAddress();
	
	/**
	 * 根据订单号查询订单地址
	 */
	public OrderAddress findOrderAddressByOrderNum(String order_num){
		if(order_num == null)return null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select address.*,provinces.province as pro, cities.city as cit, areas.area as are, mode.name as shipmode from order_address address ");
		sql.append(" left join provinces provinces on address.province = provinces.provinceid ");
		sql.append(" left join cities cities on address.city = cities.cityid ");
		sql.append(" left join areas areas on address.area = areas.areaid ");
		sql.append(" left join ship_mode mode on address.ship_mode = mode.id");
		sql.append(" where address.order_num = ? ");
		try {
			OrderAddress address = OrderAddress.dao.findFirst(sql.toString(), order_num);
			if(address != null){
				address.put("full_address", getFullAddress(address));
			}else{
				address = new OrderAddress();
			}
			return address;
		} catch (Exception e) {
			log.error("通过order_num获取订单异常", e);
		}
		return null;
	}
	
	/**
	 * 根据订单ID查询订单地址
	 */
	public OrderAddress findOrderAddressByOrderId(Long orderid){
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select address.*,provinces.province as pro, cities.city as cit, areas.area as are, mode.name as shipmode from order_address address ");
		sql.append(" left join provinces provinces on address.province = provinces.provinceid ");
		sql.append(" left join cities cities on address.city = cities.cityid ");
		sql.append(" left join areas areas on address.area = areas.areaid ");
		sql.append(" left join ship_mode mode on address.ship_mode = mode.id");
		sql.append(" where address.order_num = (select order_num from orders where id = ?) ");
		try {
			OrderAddress address = OrderAddress.dao.findFirst(sql.toString(), orderid);
			if(address != null){
				address.put("full_address", getFullAddress(address));
			}else{
				address = new OrderAddress();
			}
			return address;
		} catch (Exception e) {
			log.error("通过order_num获取订单异常", e);
		}
		return null;
	}
	
	private String getFullAddress(OrderAddress record) {
		String pro = record.get("pro");
		String cit = record.get("cit");
		String are = record.get("are");
		StringBuffer full_address = new StringBuffer();
		full_address.append(pro);
		full_address.append(" ");
		full_address.append(cit);
		full_address.append(" ");
		full_address.append(are);
		full_address.append(" ");
		full_address.append(record.get("detail_address"));
		return full_address.toString();
	}
	
	/**
	 * 省份
	 * by HECJ
	 */
	public String getProvinceName(){
		try {
			
			Provinces provinces = Provinces.dao.findCacheByProvinceId(this.getStr("province"));
			if(provinces != null){
				return provinces.getStr("province");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 城市
	 * by HECJ
	 */
	public String getCityName(){
		try {
			
			Cities city = Cities.dao.findCacheByCityid(this.getStr("city"));
			if(city != null){
				return city.getStr("city");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 县城
	 * by HECJ
	 */
	public String getAreaName(){
		try {
			Areas areas = Areas.dao.findAreasByCityid(this.getStr("area"));
			if(areas != null){
				return areas.getStr("area");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取配送方式
	 * @return 配送方式
	 */
	public ShipMode getShipMode(){
		ShipMode shipmode =  ShipMode.dao.findById(this.getLong("ship_mode"));
		if(shipmode == null){
			return new ShipMode().set("name", "商家合作快递");
		}
		return shipmode;
	}
}
