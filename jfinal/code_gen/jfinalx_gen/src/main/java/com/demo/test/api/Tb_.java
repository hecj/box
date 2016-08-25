package com.demo.test.api;

import java.util.Map;

import cn.peon.jfinal.core.TMapUtil;

/**
	tb<br>
	模型<br>
	
	将表结构放在此，消除记忆负担
	以“_”结尾为了防止和实体混淆
 */
public class Tb_ {
	/**
	 * 表名称
	 */
	public final static String _table_name="tb";
	
	/** 主键:*/
	public final static String id="id"; 
	/** 模型名 */
	public final static String n="n";
	/** 注释 */
	public final static String cm="cm";
	/**  */
	public final static String eg="eg";



	/** tb表对应的数据过滤器*/
	public static Map<String, Class<?>> getBase() {
		Map<String, Class<?>> base = TMapUtil.getEmpty();
		
		base.put(id, java.lang.Integer.class);
		base.put(n, java.lang.String.class);
		base.put(cm, java.lang.String.class);
		base.put(eg, java.lang.String.class);
		
		return base;
	}
}
