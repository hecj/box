package com.boxamazing.admin.controller.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.common.BaseController;
import com.boxamazing.service.code.Areas;
import com.boxamazing.service.code.Cities;
import com.boxamazing.service.code.Provinces;
import com.boxamazing.service.common.StringUtil;
import com.boxamazing.util.ResultJson;

/**
 * 省市区编码处理类
 * by HCJ
 */
public class ZipCodeController extends BaseController{
	
	public static Log log = LogFactory.getLog(ZipCodeController.class);
	
	/**
	 * 根据省份获取下属城市list
	 * by HCJ
	 */
	public void findCitys(){
		String province_id = getPara("province_id");
		try {
			if(StringUtil.isNullOrEmpty(province_id)){
				renderJson(new ResultJson(-1l, "省份id为空"));
				return ;
			}
			
			List<Map<String,String>> data = new ArrayList<Map<String,String>>();
			Provinces provinces = Provinces.dao.findCacheByProvinceId(province_id);
			if(provinces.getInt("type") == 2){
				// 直辖市
				List<Cities> citiesList = Cities.dao.findCitiesByProvinceId(province_id);
				for(Cities cities : citiesList){
					List<Areas> areasList = Areas.dao.findAreasByCtiy(cities.getStr("cityid"));
					for(Areas areas : areasList){
						Map<String,String> row = new HashMap<String,String>();
						row.put("key", areas.getStr("areaid"));
						row.put("value", areas.getStr("area"));
						data.add(row);
					}
				}
			}else{
				List<Cities> citiesList = Cities.dao.findCitiesByProvinceId(province_id);
				for(Cities cities : citiesList){
					Map<String,String> row = new HashMap<String,String>();
					row.put("key", cities.getStr("cityid"));
					row.put("value", cities.getStr("city"));
					data.add(row);
				}
			}
			ResultJson result = new ResultJson(200L,data,"success");
			renderJson(result);
		} catch (Exception e) {
			log.error(e.getMessage()+",province_id{}:"+province_id);
			e.printStackTrace();
			renderJson(new ResultJson(-100000l, e.getMessage()));
		}
	}
}
