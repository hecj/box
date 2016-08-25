//package cn.peon.jfinal.core;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**table接口注册
// * @author hero.wang
// *
// */
//public class TReg {
//	public final static String _table_name="_no_table_name_";
//	public static Map<String, Map<String, Class<?>>> _all_tables=new HashMap<String, Map<String, Class<?>>>();
//	public static Map<String, Class<?>> getBase() {
//		Map<String, Class<?>> base = TUtil.getEmpty();
//		return base;
//	}
//	/**讲实体映射注册到注册接口中
//	 * @param tn
//	 * @param mp
//	 */
//	public static void reg(String tn,Map<String, Class<?>> mp){
//		_all_tables.put(tn, mp);
//	}
//}
