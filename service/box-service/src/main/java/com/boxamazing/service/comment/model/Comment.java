package com.boxamazing.service.comment.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.sms.model.SendEmailRecord;
import com.boxamazing.service.util.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 产品评论表
 * 
 * @author XuXD
 * 
 */
public class Comment extends Model<Comment> {
	public static final Comment dao = new Comment();
	private static final Log log = LogFactory.getLog(Comment.class);

	/**
	 * 获取项目评论动态
	 * 
	 * @author XuXD
	 * @date 2015-10-29
	 * @param projectId
	 * @return
	 */
	public long getDynamic(long projectId) {
		log.info("[projectId:" + projectId + "]");
		String sql = "select count(1) from comment where project_id = ? and is_delete = 0 ";
		try {
			return dao.findFirst(sql, projectId).getLong("count(1)");
		} catch (Exception e) {
			log.error("获取项目动态异常", e);
		}
		return 0;
	}

	/**
	 * 通过projectId获取评论(分页显示)
	 * 
	 * @author XuXD
	 * @param projectId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Comment> findCommentByPage(long projectId, int pageNum, int pageSize) {
		log.info("[projectId:" + projectId + ", pageNum:" + pageNum + ",pageSize:" + pageSize + "]");
		String sql1 = "select c.*, u.nickname, u.picture";
		String sql2 = "from comment c, user u where c.project_id = ? and c.parent_id = 0  and c.is_delete = 0 and c.user_id = u.id order by create_at desc";
		try {
			return dao.paginate(pageNum, pageSize, sql1, sql2, projectId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过projectId获取评论(分页显示)异常", e);
		}
		return null;
	}

	/**
	 * 通过projectId,userId获取评论(分页显示)
	 * 
	 * @author XuXD
	 * @param projectId
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Comment> findCommentByPage(long projectId, long userId, int pageNum, int pageSize) {
		log.info("[projectId:" + projectId + ",userId:" + userId + " pageNum:" + pageNum + ",pageSize:" + pageSize + "]");
		String sql1 = "select c.*,u.nickname,u.picture";
		String sql2 = "from comment c, user u where c.project_id = ? and u.id = ? and c.parent_id = 0  and c.is_delete = 0 and c.user_id = u.id order by create_at desc";
		try {
			return dao.paginate(pageNum, pageSize, sql1, sql2, projectId, userId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过projectId,userId获取评论(分页显示)异常", e);
		}
		return null;
	}

	/**
	 * 通过parentId获取回复(分页显示)
	 * 
	 * @author XuXD
	 * @param parentId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Comment> findReplyByPage(long parentId, int pageNum, int pageSize) {
		log.info("[parentId:" + parentId + " pageNum:" + pageNum + ",pageSize:" + pageSize + "]");
		String sql1 = "SELECT c.*, u.nickname, s.nickname  s_nickname ";
		String sql2 = "FROM `comment` c "
					+ "LEFT JOIN `user` u ON c.user_id = u.id "
					+ "LEFT JOIN `user` s ON c.second_user_id = s.id "
					+ "WHERE c.parent_id = ? AND c.is_delete = 0 "
					+ "ORDER BY create_at DESC";
		try {
			return dao.paginate(pageNum, pageSize, sql1, sql2, parentId);
		} catch (Exception e) {
			log.error("通过parentId获取回复(分页显示)异常", e);
		}
		return null;
	}
	
	/**
	 * 删除子评论
	 * @author XuXD
	 * @date 2015-11-5
	 * @param parentId
	 * @return
	 */
	public int delByParentId(long parentId) {
		log.info("[parentId:" + parentId + "]");
		String sql = "UPDATE comment SET is_delete = 1 WHERE parent_id = ? ";
		return Db.update(sql, parentId);
	}

	/**
	 * 通过projectId获取评论
	 * 
	 * @author XuXD
	 * @param projectId
	 * @return
	 */
	public List<Comment> findComment(long projectId) {
		log.info("[projectId:" + projectId + "]");
		String sql = "select c.*, u.nickname from comment c, user u where c.project_id = ? and c.parent_id = 0  and c.is_delete = 0 and c.user_id = u.id order by create_at desc";
		try {
			return dao.find(sql, projectId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过projectId获取评论异常", e);
		}
		return null;
	}

	/**
	 * 通过projectId,userId获取评论
	 * 
	 * @author XuXD
	 * @param projectId
	 * @param userId
	 * @return
	 */
	public List<Comment> findComment(long projectId, long userId) {
		log.info("[projectId:" + projectId + ",userId:" + userId + "]");
		String sql = "select c.*,u.nickname from comment c, user u where c.project_id = ? and c.user_id = ? and c.parent_id = 0  and c.is_delete = 0 and c.user_id = u.id order by create_at desc";
		try {
			return dao.find(sql, projectId, userId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过projectId,userId获取评论异常", e);
		}
		return null;
	}

	/**
	 * 通过parentId获取回复
	 * 
	 * @author XuXD
	 * @param parentId
	 * @return
	 */
	public List<Comment> findReply(long parentId) {
		log.info("[parentId:" + parentId + "]");
		String sql = "select c.*,u.nickname from comment c, user u where c.parent_id = ? and c.user_id = u.id order by create_at desc";
		try {
			return dao.find(sql, parentId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过parentId获取回复异常", e);
		}
		return null;
	}

	/**
	 * 判断用户是否支持了指定项目
	 * 
	 * @author XuXD
	 * @param projectId
	 * @param user_id
	 * @return
	 */
	public Boolean isSupport(long projectId, long userId) {
		log.info("[projectId:" + projectId + ",userId:" + userId + "]");
		try {
			// 判断是否项目发起人
			Long id = Project.dao.getUserId(projectId);
			if (id != null && id == userId)
				return true;
			// 判断是否有订单
			List<Orders> orders = Orders.dao.find("SELECT * FROM orders where `status` not in (0,2,3) and project_id = ? and user_id = ? ", projectId,
					userId);
			if (orders != null && orders.size() > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("判断用户是否支持了指定项目异常", e);
		}
		return false;
	}

	/**
	 * 基本分页方法
	 */
	public Page<Comment> _page(int pn, int ps) {
		return dao.paginate(pn, ps, "select *", "from comment order by id desc");
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
		return Db.update("delete from comment where id in (" + str + ")", ids);
	}
	
	/**
	 * 评论记录
	 */
	public Page<Comment> findByCommentParams(Map<String, Object> params) {

		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		Long project_id = (Long) params.get("project_id");
		Long user_id = (Long) params.get("user_id");
		String project_name = (String) params.get("project_name");
		String phone = (String) params.get("phone");
		String nickname = (String) params.get("nickname");
		String content = (String) params.get("content");
		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());

		String selectSQL = "select c.*,p.name project_name,u.nickname nickname ";

		StringBuffer condition = new StringBuffer(" from comment c left join project p on (p.id = c.project_id) left join user u on (u.id=c.user_id) where 1=1 and c.is_delete in (0,1) ");

		List<Object> sqlParams = new ArrayList<Object>();

		if (project_id != null) {
			condition.append(" and c.project_id = ? ");
			sqlParams.add(project_id);
		}
		if (user_id != null) {
			condition.append(" and c.user_id = ? ");
			sqlParams.add(user_id);
		}
		
		if(!StringUtil.isNullOrEmpty(project_name)){
			condition.append(" and p.name like ? ");
			sqlParams.add("%"+project_name+"%");
		}
		if(!StringUtil.isNullOrEmpty(nickname)){
			condition.append(" and u.nickname = ? ");
			sqlParams.add(nickname);
		}
		if(!StringUtil.isNullOrEmpty(phone)){
			condition.append(" and u.phone = ? ");
			sqlParams.add(phone);
		}
		if(!StringUtil.isNullOrEmpty(content)){
			condition.append(" and c.content like ? ");
			sqlParams.add("%"+content+"%");
		}

		condition.append(" order by c.id desc ");

		log.info(" 评论记录 condition{} : " + condition.toString());
		try {
			Page<Comment> list = dao.paginate(pageNumber, pageSize, selectSQL, condition.toString(), SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error("status{},pageObj{},sizeObj{}:" + pageObj + "," + sizeObj);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
