package com.boxamazing.service.olu.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
/*
 * 
 * 用户日志
 */
public class Olu extends Model<Olu> {
	public static final Olu dao = new Olu();
	
	/**
	 * 基本分页方法
	 */
	public Page<Olu> _page(int pn, int ps) {
		return dao.paginate(pn, ps, "select *", "from olu order by id desc");
	}

	public Page<Olu> selectInfoUser(int num ,int length, String uInfo, Date firstTime, Date lastTime)  {
		//Date first = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(firstTime);
		//Date last = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(lastTime);
		//System.out.println(firstTime);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//System.out.println(uInfo.equals(null));
		//System.out.println(uInfo.equals(""));
		if(lastTime==null){
			lastTime = new Date();
		}
		if(uInfo.equals("")){
			return dao.paginate(num, length, "select * ", "from olu where tm > ? and endtm < ? order by id desc",df.format(firstTime),df.format(lastTime));
		}else{
			return dao.paginate(num, length, "select * ", "from olu where oids = ? and tm > ? and endtm < ? order by tm desc",uInfo,df.format(firstTime),df.format(lastTime));
		}
	}
	
	
}
