package ${var.base_pack_url}.service.${root.name}.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import cn.peon.core.kit.StringUtil;
import com.demo.util.ArrayUtil;

 /**
 *${root.comment?if_exists}
 */
@SuppressWarnings("serial")
public class ${root.fname} extends Model<${root.fname}> {
	public static final ${root.fname} dao = new ${root.fname}();
	 /**
	 *基本分页方法
	 */
	public Page<${root.fname}> _page(int pn,int ps) {
		return dao.paginate(pn, ps, "select *", "from ${root.name} order by id desc");
	}
	
	/**根据id删除多个对象
	 * @param ids
	 * @return
	 */
	public int deleteByIds(String... ids) {
		String[] ay = ArrayUtil.getPrePareArray(ids.length);
		String str = StringUtil.join(ay,",");
		return Db.update("delete from ${root.name} where id in ("+str+")", ids);
	}
}