package cn.peon.common_core.core;

import java.util.ArrayList;
import java.util.Map;

import cn.peon.jfinal.core.SqlKit;
import cn.peon.jfinal.core.TMapUtil;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;


public abstract class BaseMapServiceImpl implements BaseMapService {
	/**表名称预置
	 * @return
	 */
	public abstract String getTname();
	/**基础写
	 * @param map
	 * @return
	 */
	public <T> T save(Map<String, Object> map) {
		Record r = new Record();
		r.setColumns(map);
		boolean has_save = Db.save(getTname(), r);
		if (has_save) {
			return r.get("id");
		}else {
			return r.get("id");
		}
	}

	/**基础读取根据id
	 * @param id
	 * @return
	 */
	public Map<String, Object> findById(Object id) {
		Record rs = Db.findById(getTname(), id);
		if (rs!=null) {
			return rs.getColumns();
		}else {
			return TMapUtil.getEmpty();
		}
	}
	
	/**基础更新实体
	 * @param map
	 * @return
	 */
	public boolean update(Map<String, Object> map) {
		Record r=new Record();
		r.setColumns(map);
		return Db.update(getTname(), r);
	}
	
	/**基础的删除方法
	 * @param id
	 * @return
	 */
	public boolean deleteById(Object id) {
		return Db.deleteById(getTname(), id);
	}
	
	/**基础的分页方法
	 * 
	 * @param pageNumber 当前页，从1开始  pageSize 页长度
	 * @return
	 */
	 public Page<Map<String, Object>> findPage(int pageNumber,int pageSize,Map<String, Object> map,String orderbystr){
		 
		 String from=" from "+getTname() +" where 1=1 ";
		 
		 String sqlWheres =  SqlKit.getSqlWheres(map);//sql条件拼接
		 ArrayList<Object> sqlWhereParams = SqlKit.getSqlWheresParams(map);//参数拼接
		 
		 Page<Record> p = Db.paginate(pageNumber, pageSize,"select *", from + sqlWheres+" order by "+orderbystr,sqlWhereParams.toArray());
		 
		 return TMapUtil.recordPage2MapPage(p);
	 }
	
	
}
