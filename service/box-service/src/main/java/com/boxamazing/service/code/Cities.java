package com.boxamazing.service.code;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.util.CacheName;
import com.jfinal.plugin.activerecord.Model;

/**
 * 行政区
 * @author HECJ
 */
public class Cities extends Model<Cities> {

	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(Cities.class);
    
	public static final Cities dao = new Cities();
   
	/**
	 * 查询所有行政区<br>
	 * by HECJ
	 */
    public List<Cities> findCitiesByProvinceId(String province_id){
    	try {
    		List<Cities> list = dao.findByCache(CacheName.CONSTANT_CACHE, "findCitiesByProvinceId"+province_id, "select * from cities c where c.provinceid = ?",new Object[]{province_id});
    		return list ;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 
     * 从缓存中获取市区名称
     * by YJ
     */
   
    public Cities findCacheByCityid(String cityid){
    	try {
    		List<Cities> cityList = dao.findByCache(CacheName.CONSTANT_CACHE, "findCacheByCityid"+cityid, "select * from cities c where c.cityid = ?",new Object[]{cityid});
    		if(cityList != null && cityList.size() > 0){
    			return cityList.get(0);
    		}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
    	return null;
    }
}
