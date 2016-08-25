package com.boxamazing.service.code;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.util.CacheName;
import com.jfinal.plugin.activerecord.Model;

/**
 * 省份
 * @author HECJ
 */
public class Provinces extends Model<Provinces> {

	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(Provinces.class);
    
	public static final Provinces dao = new Provinces();
	
	/**
	 * 查询所有省份<br>
	 * by HECJ
	 */
    public List<Provinces> findAll(){
    	
    	try {
    		List<Provinces> list = dao.findByCache(CacheName.CONSTANT_CACHE, "provinces_list", "select * from provinces p ");
    		return list ;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
    	return null;
    }

    /**
     * 从缓存中获取省份
     * by HECJ
     */
    public Provinces findCacheByProvinceId(String provinceid){
    	try {
    		List<Provinces> provincesList = dao.findByCache(CacheName.CONSTANT_CACHE, "findCacheByProvinceId_"+provinceid, "select * from provinces p where p.provinceid = ?",new Object[]{provinceid});
    		if(provincesList != null && provincesList.size() > 0){
    			return provincesList.get(0);
    		}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
    	return null;
    }
    
  
}
