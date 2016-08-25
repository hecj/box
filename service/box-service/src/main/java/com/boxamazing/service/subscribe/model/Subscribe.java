package com.boxamazing.service.subscribe.model;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 订阅(关注表) Created by jhl on 15/8/13.
 */
public class Subscribe extends Model<Subscribe> {
	public static final Subscribe dao = new Subscribe();

	private static final Log log = LogFactory.getLog(Subscribe.class);

	/**
	 * 通过userId获取关注项目(分页显示)
	 * 
	 * @author XuXD
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Subscribe> findSubscribeByPage(long userId, int pageNum, int pageSize) {
		log.info("[userId:" + userId + "pageNum:" + pageNum + ",pageSize:" + pageSize + "]");
		String sql1 = "select s.*, p.name, p.cover_image ";
		String sql2 = "from subscribe s, project p where s.user_id=? and s.project_id=p.id order by s.create_at desc ";
		try {
			return dao.paginate(pageNum, pageSize, sql1, sql2, userId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过userId获取关注项目(分页显示)异常", e);
		}
		return null;
	}

	/**
	 * 基本分页方法
	 */
	public Page<Subscribe> _page(int pn, int ps) {
		return dao.paginate(pn, ps, "select *", "from subscribe order by project_id desc");
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
		return Db.update("delete from subscribe where project_id in (" + str + ")", ids);
	}

	/**
	 * 通过pid和uid查询用户订阅.
	 * 
	 * @param pid
	 * @param uid
	 * @return
	 */
	public List<Subscribe> findByProjectIdAndUserId(Long project_id, Long user_id) {
		return Subscribe.dao.find("select * from subscribe where project_id=? and user_id=?", project_id, user_id);
	}

	/**
	 * 添加用户订阅
	 * 
	 * @param pid
	 * @param uid
	 * @return
	 */
	public boolean sub(Long project_id, Long user_id) {
		if (isSub(project_id, user_id)) {
			return false;
		}
		return new Subscribe().set("project_id", project_id).set("user_id", user_id).set("create_at", System.currentTimeMillis()).save();
	}

	/**
	 * 查看用户是否订阅pid的产品
	 * 
	 * @param pid
	 * @param uid
	 * @return true已订阅, false未订阅
	 */
	public boolean isSub(Long project_id, Long user_id) {
		List<Subscribe> findResult = Subscribe.dao.findByProjectIdAndUserId(project_id, user_id);
		if (null != findResult && findResult.size() > 0) {
			return true; // 已订阅(关注)
		}
		return false; // 未订阅(关注)
	}

	/**
	 * 通过pid获得订阅总数
	 * 
	 * @param pid
	 * @return
	 */
	public Long getSubCountByPid(Long project_id) {
		List<Record> result = Db.find("select count(*) as count from subscribe where project_id = ?", project_id);
		Long count = result.get(0).getLong("count");
		return count;
	}

	/**
	 * 通过uid查找我订阅的产品
	 * 
	 * @param uid
	 * @return
	 */
	public List<Record> findMySubByUid(String uid) {
		return Db.find("select " + " s.uid as uid, s.datetime as datetime, p.status as pstatus, p.name as pname, p.pic0 as pic0 "
				+ " from subscribe s, project p " + " where s.uid = ? and s.project_id = p.id", uid);
	}

	/**
	 * 通过uid查找我订阅的产品
	 * 
	 * @param uid
	 * @return
	 */
	public Page<Record> findMySubByUidWithPage(String userId, int pn, int ps) {
		return Db.paginate(pn, ps, "select * from subscribe s, project p where s.user_id = ? and s.project_id = p.project_id", userId);
	}

	/**
	 * 删除关注的项目
	 * 
	 * @param pid
	 * @param uid
	 * @return
	 */
	public int removeByPidUid(Long pid, String uid) {
		return Db.update("delete from subscribe where project_id = ? and user_id = ?", pid, uid);
	}

}
