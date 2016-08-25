package cn.peon.common_core.core;

import java.util.Map;

import com.jfinal.plugin.activerecord.Page;


public interface BaseMapService  {
	/**表名称预置
	 * @return
	 */
	 String getTname();
	/**基础写
	 * @param map
	 * @return
	 */
	 <T> T save(Map<String, Object> map);

	/**基础读取根据id
	 * @param id
	 * @return
	 */
	 Map<String, Object> findById(Object id);
	
	/**基础更新实体
	 * @param map
	 * @return
	 */
	 boolean update(Map<String, Object> map);
	
	 /**基础的删除方法
	  * @param id
	  * @return
	  */
	 boolean deleteById(Object id);
	 
	/**基础的分页方法
	 * 
	 * @param pageNumber 当前页，从1开始  pageSize 页长度
	 * @return
	 */
	 Page<Map<String, Object>> findPage(int pageNumber,int pageSize,Map<String, Object> map,String orderbystr);

}
