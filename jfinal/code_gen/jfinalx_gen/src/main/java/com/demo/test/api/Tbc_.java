package com.demo.test.api;

import java.util.Map;

import cn.peon.jfinal.core.TMapUtil;

/**
	tbc<br>
	模型字段<br>
	
	将表结构放在此，消除记忆负担
	以“_”结尾为了防止和实体混淆
 */
public class Tbc_ {
	/**
	 * 表名称
	 */
	public final static String _table_name="tbc";
	
	/** 主键:*/
	public final static String id="id"; 
	/** 模型id */
	public final static String tbid="tbid";
	/** 属性名称 */
	public final static String cn="cn";
	/** 数据类型 */
	public final static String ty="ty";
	/** 默认值 */
	public final static String de="de";
	/** 标记(是否是主|空|增) */
	public final static String fl="fl";
	/** 注释 */
	public final static String cm="cm";
	/** 示例数据 */
	public final static String egg="egg";



	/** tbc表对应的数据过滤器*/
	public static Map<String, Class<?>> getBase() {
		Map<String, Class<?>> base = TMapUtil.getEmpty();
		
		base.put(id, java.lang.Integer.class);
		base.put(tbid, java.lang.Integer.class);
		base.put(cn, java.lang.String.class);
		base.put(ty, java.lang.String.class);
		base.put(de, java.lang.String.class);
		base.put(fl, java.lang.String.class);
		base.put(cm, java.lang.String.class);
		base.put(egg, java.lang.String.class);
		
		return base;
	}
}
