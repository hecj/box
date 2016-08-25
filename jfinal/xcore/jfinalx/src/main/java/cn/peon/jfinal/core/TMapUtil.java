package cn.peon.jfinal.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 *  
 map处理通用类
 */
public abstract class TMapUtil  {

	public static  Object _reflect_(Class<?> c) throws NoSuchMethodException,
		IllegalAccessException, InvocationTargetException {
		Method m = c.getMethod("getBase"); 		
		Object obj = m.invoke(c);
		return obj;
	}

 
	/**获取一个空的映射
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String, T> getEmpty(){
		return new  HashMap<String, T>();
	}
	
	/**
	 * 获得一个删减字段后的map
	 * @param <T>
	 * @return 
	 */
	public static <T> Map<String, T> sub(Map<String, T> base,String...fileds ) {
		if (fileds!=null) {
			for (String key : fileds) {
				if (key!=null&&(!key.equals(""))) {
					base.remove(key);
				}
			}
		}
		return base;
	}
	
	/**
	 * 获得一个扩展字段后的map
	 * @param <T>
	 * @return 
	 */
	public static <T> Map<String, T> add(Map<String, T> base,String...fileds) {
		if (fileds!=null) {
			for (String key : fileds) {
				if (key!=null&&(!key.equals(""))) {
					base.put(key,(T) String.class);
				}
			}
		}
		return base;
	}
	
	/**
	 * 获得一个扩展字段后的map
	 * @param <T>
	 * @return 
	 */
	public static <T> Map<String, T> merger(Map<String, T> base,Map<String, T> add,boolean iscover) {
		if (add==null) {
			add=new HashMap<String, T>();
		}
		if (iscover) {
			base.putAll(add);
		}else {
			add.putAll(base);
		}
		
		return base;
	}
	
	/**
	 * 简单复制一个map
	 * @param <T>
	 * @return 
	 */
	public static <T> Map<String, T> easyCopy(Map<String, T> base) {
		 HashMap<String, T> o = new HashMap<String, T>();
		for (String key : base.keySet()) {
			o.put(key, base.get(key));
		}
		return o;
	}
	
	/**
	 * 获得一个过滤字段后的map
	 * @param <T>
	 * @return 
	 */
	public static <T> Map<String, T> filter(Map<String, T> base,String...cls) {
		if (cls!=null) {
			for (String key : cls) {
				if (key!=null&&(!key.equals(""))&&base.containsKey(key)) {
//					base.put(key,String.class);
				}else {
					base.remove(key);
				}
			}
		}
		return base;
	}


	/**record分页数据转化为mapPage的数据
	 * @param p
	 * @return
	 */
	public static <E> Page<Map<String, Object>> recordPage2MapPage(Page<Record> p) {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Record> pl = p.getList();
		if (pl!=null&&pl.size()>0) {
			for (Record record : pl) {
				list.add(record.getColumns());
			}
		}
		Page<Map<String, Object>> pg=new Page<Map<String,Object>>(list, p.getPageNumber(), p.getPageSize(), p.getTotalPage(), p.getTotalRow());
		return pg;
	}
	
	
	
	
	/**根据前缀从一个map中获取map
	 * @param from
	 * @param fromfix
	 * @param fix
	 * @return
	 */
	public static Map<String, Object> getMapWithFix(Map<String, Object> from,
			String fromfix) {
		return getMapWithFixAndAppendFix(from, fromfix, "");
	}
	/**根据前缀从一个map中获取map 并给与新的前缀
	 * @param from
	 * @param fromfix
	 * @param fix
	 * @return
	 */
	public static Map<String, Object> getMapWithFixAndAppendFix(Map<String, Object> from,
			String fromfix, String fix) {
		Map<String, Object> m=new HashMap<String, Object>();
		if (from!=null) {
			for (String key : from.keySet()) {
				if (key.startsWith(fromfix)) {
					m.put(fix+(key.substring(fromfix.length())), from.get(key));
				}
			}
		}
		return m;
	}
	
	
}
