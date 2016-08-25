package com.boxamazing.service.orders.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.plugin.activerecord.Model;

/**
 * 订单配送方式
 * by liyanlong
 */
public class ShipMode extends Model<ShipMode> {
	
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(ShipMode.class);
	
	public static final ShipMode dao = new ShipMode();
	
	/**
	 * 获取订单配送方式列表  by liyanlong
	 * @return List
	 * @version 2015/10/30
	 */
	public List<ShipMode> queryAllShipMode(){
		String sql =  "select * from ship_mode where iswork = 1";
		try{
			List<ShipMode> shipModeList = dao.find(sql);
			return shipModeList;
		}catch(Exception e){
			log.equals(e);
		}
		return new ArrayList<ShipMode>();
	}

	/**
	 * 获取默认配送方式 by liyanlong
	 * @return 默认配送方式ShipMode
	 */
	public ShipMode queryDefaultShipMode() {
		String  sql = "select * from ship_mode where iswork = 1 and isdefault = 1";
		try{
			List<ShipMode> shipModeList = dao.find(sql);
			if(shipModeList.size() > 0){
				return shipModeList.get(0);
			}
		}catch(Exception e){
			log.equals(e);
		}
		return new ShipMode();
	}
}
