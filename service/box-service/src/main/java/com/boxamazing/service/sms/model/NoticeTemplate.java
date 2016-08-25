package com.boxamazing.service.sms.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * 提醒模版表
 * @author HECJ
 */
public class NoticeTemplate extends Model<NoticeTemplate> {
	
	private static final long serialVersionUID = 1L;
	
	public static final NoticeTemplate dao = new NoticeTemplate();
	
	public NoticeTemplate findByTempName(String temp_name) {
		List<NoticeTemplate> list = NoticeTemplate.dao.find("select * from notice_template where temp_name = ?", temp_name);
		if(list == null || list.size() == 0 ){
			return null;
		}
        return list.get(0);
    }

}
