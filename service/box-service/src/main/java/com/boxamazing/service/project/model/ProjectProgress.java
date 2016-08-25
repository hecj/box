package com.boxamazing.service.project.model;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 公告历史(项目进度) Created by jhl on 15/8/13.
 */
public class ProjectProgress extends Model<ProjectProgress> {
	public static final ProjectProgress dao = new ProjectProgress();
	private static final Log log = LogFactory.getLog(ProjectProgress.class);

	/**
	 * 获取项目进度动态
	 * 
	 * @author XuXD
	 * @date 2015-10-29
	 * @param projectId
	 * @return
	 */
	public long getDynamic(long projectId) {
		log.info("[projectId:" + projectId + "]");
		String sql = "select count(1) from project_progress where project_id = ? and is_delete = 0 ";
		try {
			return dao.findFirst(sql, projectId).getLong("count(1)");
		} catch (Exception e) {
			log.error("获取项目动态异常", e);
		}
		return 0;
	}

	/**
	 * 获取最新的项目进展
	 * 
	 * @author XuXD
	 * @param projectId
	 * @return
	 */
	public ProjectProgress findLatestOne(long projectId) {
		log.info("[projectId:" + projectId + "]");
		String sql = "select * from project_progress where project_id = ? and is_delete = 0 order by progress_at desc";
		try {
			return dao.findFirst(sql, projectId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取最新的项目进展异常", e);
		}
		return null;
	}

	/**
	 * 获取项目进展(分页显示)
	 * 
	 * @author XuXD
	 * @param projectId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<ProjectProgress> findByPage(long projectId, int pageNum, int pageSize) {
		log.info("[projectId:" + projectId + ",pageNum:" + pageNum + ",pageSize:" + pageSize + "]");
		String sql1 = "select * ";
		String sql2 = "from project_progress where project_id = ? order by progress_at desc ";
		try {
			return dao.paginate(pageNum, pageSize, sql1, sql2, projectId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取项目进展(分页显示)异常", e);
		}
		return null;
	}

	/**
	 * 获取项目进展
	 * 
	 * @author XuXD
	 * @param projectId
	 * @return
	 */
	public List<ProjectProgress> findByProjectId(long projectId) {
		log.info("[projectId:" + projectId + "]");
		String sql = "select * from project_progress where is_delete = 0 and project_id = ? order by progress_at desc ";
		try {
			return dao.find(sql, projectId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取项目进展异常", e);
		}
		return null;
	}
}
