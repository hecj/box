package ${var.base_pack_url}.${var.base_module_name}.api;

import java.util.Map;

import cn.peon.jfinal.core.TMapUtil;

/**
	${root.name}<br>
	${root.comment?if_exists}<br>
	
	将表结构放在此，消除记忆负担
	以“_”结尾为了防止和实体混淆
 */
public class ${root.fname}_ {
	/**
	 * 表名称
	 */
	public final static String _table_name="${root.name}";
	
<#list root.columnlist as col>
	<#if col.ispk>
	/** 主键:${col.comment?if_exists}*/
	public final static String ${col.name}="${col.name}"; 
	<#else> 
	/** ${col.comment?if_exists} */
	public final static String ${col.name}="${col.name}";
	</#if> 
</#list>



	/** ${root.name}表对应的数据过滤器*/
	public static Map<String, Class<?>> getBase() {
		Map<String, Class<?>> base = TMapUtil.getEmpty();
		
	<#list root.columnlist as col>
		base.put(${col.name}, ${col.className}.class);
	</#list>
		
		return base;
	}
}
