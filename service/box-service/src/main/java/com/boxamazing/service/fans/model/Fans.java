package com.boxamazing.service.fans.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class Fans extends Model<Fans>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Fans dao = new Fans();
	
	/**
	 * 
	 * @param promoderid 项目发起人id
	 * @return 项目发起人的粉丝数量
	 */
	public long getFansCountByPromoder(Long promoderid) {
		List<Record> result = Db.find("select count(*) as count from fans where promoderid = ?", promoderid);
		if(result.size() > 0){			
			return result.get(0).getLong("count");
		}else{
			return 0;
		}
	}

	/**
	 * 判断当前登录用户是否为当前浏览项目的发起人的粉丝
	 * @param promoderid 项目发起人
	 * @param userid 当前登陆人
	 * @return
	 */
	public boolean isSub(Long promoderid, Long userid) {
		List<Fans> findResult = Fans.dao.find("select * from fans where promoderid = ? and userid = ?", promoderid, userid);
		if (null != findResult && findResult.size() > 0) {
			return true; // 已关注
		}
		return false; // 未关注
	}

	public List<Fans> findByPromoderIdAndUserId(Long promoderid, Long userid) {
		return Fans.dao.find("select * from fans where promoderid=? and userid=?", promoderid, userid);
	}
}