package com.demo.service.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import com.demo.service.dbs.model.Dbs;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Config;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class DBSUTIL {
	public static final String COMMON = "COMMON_";//配置数据源config前缀
	/**获取db
	 * @param string
	 * @return 
	 */
	public static Config getDb(Dbs dbs) {
		String configName = COMMON+dbs.getLong("id");
		Config c = DbKit.getConfig(configName);
		if (c==null) {//配置为空 创建
			
			C3p0Plugin c3p0Plugin = new C3p0Plugin(dbs.getStr("url"), dbs.getStr("u"), dbs.getStr("p"));
			// 配置ActiveRecord插件
			ActiveRecordPlugin arp = new ActiveRecordPlugin(configName,c3p0Plugin);
			arp.setShowSql(true);
			c3p0Plugin.start();
			arp.start();
			c=DbKit.getConfig(configName);
		}else {
			return c;
		}
		return c;
	}
	
	public static Object invokeMeth(String name,DbPro dbPro,String sql,ArrayList<String> palist) {
		
		Class<?> clazz = DbPro.class;
		DbPro single = dbPro;
        
		Method method = null;
		try {
//			method = clazz.getDeclaredMethod(name, Array.newInstance(Object.class, 0).getClass());
//			method = clazz.getDeclaredMethod(name,   String.class,Object[].class );
			method = clazz.getDeclaredMethod(name,  new Class[] {String.class, Object[].class });
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (NegativeArraySizeException e1) {
			e1.printStackTrace();
		}
//        /* 2 */
//        method = clazz.getDeclaredMethod("method", (new Object[0]).getClass());
 
 
         
        //初始化参数
        /* 1 */
        
        Object[] objs=new Object[palist.size()+1];
        objs[0]=sql;
        int i=1;
        for (String pa : palist) {
        	objs[i]=pa;
        	i++;
		}
        try {
			return method.invoke(single, objs);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**获取sql中select的字段
	 * @param sql
	 * @return
	 */
	public static String getFields(String sql) {
		return sql.split("(from)|(FROM)")[0].trim().replaceFirst("(select)|(SELECT)", "").replace(" ", "");
	}

}
