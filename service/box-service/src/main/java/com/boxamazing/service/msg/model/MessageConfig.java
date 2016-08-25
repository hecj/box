package com.boxamazing.service.msg.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.util.CacheName;
import com.jfinal.plugin.activerecord.Model;

/**
 * 消息配置表
 * @author HECJ
 */
public class MessageConfig extends Model<MessageConfig> {
	
	private static final long serialVersionUID = 1L;
	
	public static final MessageConfig dao = new MessageConfig();
	
	private static final Log log = LogFactory.getLog(MessageConfig.class);
	
	/**
	 * 根据类型查询消息集合 cache
	 * by HECJ
	 */
	public List<MessageConfig> findMessageConfigByType(int type,int size){
		
		String querySQL = "SELECT * FROM message_config mc where mc.type = ? order by sort asc limit ?";
		List<MessageConfig> list = new ArrayList<MessageConfig>();
		try {
			
			list = dao.findByCache(CacheName.CONSTANT_CACHE, "findMessageConfigByType", querySQL,new Object[]{type,size});
			
		} catch (Exception e) {
			log.error(" type {}:"+type+","+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	
}