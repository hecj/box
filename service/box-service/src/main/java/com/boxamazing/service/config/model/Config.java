package com.boxamazing.service.config.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class Config extends Model<Config> {

	public static final Config dao = new Config();
	
	 /**
     * 基本分页方法
     */
    public Page<Config> _page(int pn, int ps) {
        return dao.paginate(pn, ps, "select *", "from config order by id desc");
    }
    
    public List<Config> findAll(){
    	return Config.dao.find("select * from config");
    }
    
    


}
