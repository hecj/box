package com.demo.test.api;

import java.util.Map;

import cn.peon.jfinal.core.TMapUtil;

/**

将表结构放在此，消除记忆负担
以“_”结尾为了防止和实体混淆
 */
public class Blog_  /*extends TReg*/{
	/**
	 * 表名称
	 */
	public final static String _table_name="blog";
	
	/**
	 * 提供给外部的唯一key,可用来做缓存主key
	 */
	public final static String _uni_key="com.demo.test.api."+_table_name;
	
	
/*	static{//主动注册,如不注册入内 讲视为不允许controller感知获取参数
//		TReg._all_tables.put(_table_name, getBase());
		TReg.reg(_table_name, getBase());
	}*/
	
	/**
	 * 主键
	 */
	public final static String id="id"; 
	/**
	 * 标题
	 */
	public final static String title="title";
	/**
	 * 内容
	 */
	public final static String content="content";
	
	public static Map<String, Class<?>> getBase() {
		Map<String, Class<?>> base = TMapUtil.getEmpty();
		base.put(id, Integer.class);
		base.put(title, String.class);
		base.put(content, String.class);
		return base;
	}
}
