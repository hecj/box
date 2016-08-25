package cn.peon.jfinal.core;

import java.util.ArrayList;
import java.util.Map;

import com.jfinal.kit.StrKit;

public class SqlKit {
	/**根据map获取一个sql条件拼接
	 * @param map
	 * @return
	 */
	public static String getSqlWheres(Map<String, Object> map) {
		return getSqlWheres(map, "","");
	}
	/**根据map获取一个sql条件拼接
	 * @param map
	 * @return
	 */
	public static String getSqlWheres(Map<String, Object> map,String mapfix,String sqlfix) {
		
		if (StrKit.isBlank(mapfix)) {mapfix=""; }
		if (StrKit.isBlank(sqlfix)) {sqlfix=""; }
		
		StringBuffer sqlWheres = new StringBuffer();
		for (String key : map.keySet()) {
			if (!StrKit.isBlank(key)) {
				if (StrKit.isBlank(mapfix)) {//无map前缀
					sqlWheres.append(" and ").append(sqlfix).append(key).append(" =? ");
				}else {
					if (key.startsWith(mapfix)) {//有
						sqlWheres.append(" and ").append(sqlfix).append(key).append(" =? ");
					}
				}
			}
		}
		return sqlWheres.toString();
	}
	/**根据map获取一个sql条件的参数列表
	 * @param map
	 * @return
	 */
	public static ArrayList<Object> getSqlWheresParams(Map<String, Object> map, String mapfix) {
		ArrayList<Object> sqlWhereParams = new ArrayList<Object>();//参数拼接
		for (String key : map.keySet()) {
			if (!StrKit.isBlank(key)) {
				if (StrKit.isBlank(mapfix)) {//无map前缀
					sqlWhereParams.add(map.get(key));
				}else {
					if (key.startsWith(mapfix)) {//有
						sqlWhereParams.add(map.get(key));
					}
				}
			}
		}
		return sqlWhereParams;
	}
	/**根据map获取一个sql条件的参数列表
	 * @param map
	 * @return
	 */
	public static ArrayList<Object> getSqlWheresParams(Map<String, Object> map ) {
		 ArrayList<Object> sqlWhereParams = new ArrayList<Object>();//参数拼接
		for (String key : map.keySet()) {
			if (!StrKit.isBlank(key)) {
				sqlWhereParams.add(map.get(key));
			}
		 }
		return sqlWhereParams;
	}
}
