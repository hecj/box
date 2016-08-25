package com.boxamazing.service.code;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.util.CacheName;
import com.jfinal.plugin.activerecord.Model;

/**
 * 市区代码表
 * @author HECJ
 */
public class Areas extends Model<Areas> {

	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(Areas.class);
    
	public static final Areas dao = new Areas();
   
	public List<Areas> findAreasByCtiy(String cityid){
		try {
			List<Areas> areasList = dao.findByCache(CacheName.CONSTANT_CACHE, "findAreasByCtiy_"+cityid, "select * from areas a where a.cityid = ?",new Object[]{cityid});
			return areasList;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取区域名称
	 * by YJ
	 */
	public Areas findAreasByCityid(String areaid){
		try {
    		List<Areas> areasList = dao.findByCache(CacheName.CONSTANT_CACHE, "findAreasByCityid"+areaid, "select * from areas a where a.areaid = ?",new Object[]{areaid});
    		if(areasList != null && areasList.size() > 0){
    			return areasList.get(0);
    		}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
    	return null;
	}
	
	
    
}
