package com.boxamazing.service.project.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.util.CacheName;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 项目推荐
 * by HECJ
 */
public class ProjectRecomm extends Model<ProjectRecomm>{

	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(ProjectRecomm.class);
	
	public static final ProjectRecomm dao = new ProjectRecomm();
	
	/**
	 * 获取推荐项目id列表
	 * @author XuXD
	 * @param position
	 * @param size
	 * @return
	 */
	public List<Long> getProjectRecomm(Integer position,Integer size){
		List<ProjectRecomm> projectRecommList = dao.find("select project_id from project_recomm where position=? order by update_at desc limit ?",position,size);
		if (projectRecommList==null) {
			log.info("[projectRecommList is null ]");
			return null;
		}
		List<Long> idList = new ArrayList<Long>(size);
		for (int i = 0; i < projectRecommList.size(); i++) {
			idList.add(projectRecommList.get(i).getLong("project_id"));
		}
		return idList;
	}
	
	/**
	 * 查询推荐的项目id list
	 * by HECJ
	 */
	public List<Long> getProjectRecommIdsByPosition(int position) throws Exception{
		
		String querySQL = "select project_id from project_recomm pr where pr.position = ?";
		List<Long> ids = null ;
		try {
			ids = Db.query(querySQL, new Object[]{position});
			
		} catch (Exception ex) {
			log.error("position{} ："+position+" , " + ex.getMessage());
			log.error("execute sql{} : "+querySQL);
			ex.printStackTrace();
			throw ex;
		}
		return ids;
	}
	
	public ProjectRecomm findByProjectId(Long project_id){
		try {
			
			List<ProjectRecomm> list = dao.find("select * from project_recomm pr where pr.project_id = ? ", new Object[]{project_id});
			if(list != null && list.size() > 0){
				return list.get(0);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询推荐的项目id list
	 * by HECJ
	 */
	public List<Long> getProjectIds() throws Exception{
		
		String querySQL = "select project_id from project_recomm pr";
		List<Long> ids = null ;
		try {
			ids = Db.query(querySQL);
			
		} catch (Exception ex) {
			log.error( ex.getMessage());
			log.error("execute sql{} : "+querySQL);
			ex.printStackTrace();
			throw ex;
		}
		return ids;
	}
	
	/**
	 * 获取推荐项目列表(带有缓存)
	 * by HECJ
	 * @return
	 */
	public List<ProjectRecomm> getProjectRecommByCacheAndPostion(Integer position,Integer size){
		log.info("position{},size{}:"+position+","+size);
		try {
			List<ProjectRecomm> projectRecommList = dao.findByCache(CacheName.CONSTANT_CACHE, "getProjectRecommByCacheAndPostion" ,"select project_id from project_recomm where position=? order by update_at desc limit ?",position,size);
			return projectRecommList;
		} catch (Exception e) {
			log.error("position{},size{}:"+position+","+size+","+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取推荐项目列表()
	 * by YJ
	 * @return
	 */
	public List<ProjectRecomm> getProjectRecommByPosition(Integer position,Integer size){
		log.info("position{},size{}:"+position+","+size);
		try {
			List<ProjectRecomm> projectRecommList = dao.find("select pr.project_id from project_recomm pr left join project p on (p.id=pr.project_id) where pr.position=? and p.status in (7,8,10) order by pr.update_at desc limit ?",position,size);
			return projectRecommList;
		} catch (Exception e) {
			log.error("position{},size{}:"+position+","+size+","+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
}
