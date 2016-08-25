package com.boxamazing.service.user.model;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.code.Areas;
import com.boxamazing.service.code.Cities;
import com.boxamazing.service.code.Provinces;
import com.jfinal.plugin.activerecord.Model;

/**
 * 收货地址
 * by HECJ
 */
public class ReceiveAddress extends Model<ReceiveAddress> {
	
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(ReceiveAddress.class);
	
	public static final ReceiveAddress dao = new ReceiveAddress();

	/**
	 * 查询用户有效的收货地址
	 * by HECJ
	 */
	public List<ReceiveAddress> findListByUserId(long user_id){
		log.info("user_Id{}:"+user_id);
		try {
			
			String sqlQuery = "select * from receive_address ra where ra.is_delete = 0 and ra.user_id = ?";
			
			List<ReceiveAddress> list = dao.find(sqlQuery, new Object[]{user_id});
			return list ;
		} catch (Exception e) {
			log.error(e.getMessage()+","+user_id);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询用户有效的收货地址
	 * by HECJ
	 */
	public List<ReceiveAddress> findListByUserIdAndDefault(long user_id,int default_status){
		log.info("user_Id{}:"+user_id);
		try {
			
			String sqlQuery = "select * from receive_address ra where ra.is_delete = 0 and ra.user_id = ? and ra.default = ?";
			
			List<ReceiveAddress> list = dao.find(sqlQuery, new Object[]{user_id,default_status});
			return list ;
		} catch (Exception e) {
			log.error(e.getMessage()+","+user_id);
			e.printStackTrace();
		}
		return null;
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
}
