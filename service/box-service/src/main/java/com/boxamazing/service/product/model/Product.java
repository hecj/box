package com.boxamazing.service.product.model;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 回报表
 * 
 * @author XuXD
 * 
 */
public class Product extends Model<Product> {
	private static final Log log = LogFactory.getLog(Product.class);
	public static final Product dao = new Product();

	/**
	 * 计算剩余人数POJO
	 * 
	 * @author XuXD
	 * @return
	 */
	public Integer getRemainderNum() {
		try {
			if (null != this.getInt("totalnum") || null != this.getInt("existnum")) {
				return this.getInt("totalnum") - this.getInt("existnum");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("计算剩余人数POJO异常", e);
		}
		log.info("totalnum or existnum is null");
		return 0;
	}

	/**
	 * 通过projectId获取订单
	 * 
	 * @author XuXD
	 * @param projectId
	 * @return
	 */
	public List<Product> findByProjectId(long projectId) {
		log.info("[projectId:" + projectId + "]");
		String sql = "select * from product where project_id = ? and is_delete=0 order by fund asc";
		try {
			return dao.find(sql, projectId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过projectId获取订单异常", e);
		}
		return null;
	}

	/**
	 * 基本分页方法
	 */
	public Page<Product> _page(int pn, int ps) {
		return dao.paginate(pn, ps, "select *", "from product order by id desc");
	}

	/**
	 * 根据id删除多个对象
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteByIds(String... ids) {
		String[] ay = ArrayUtil.getPrePareArray(ids.length);
		String str = StringUtil.join(ay, ",");
		return Db.update("delete from product where id in (" + str + ")", ids);
	}
}
