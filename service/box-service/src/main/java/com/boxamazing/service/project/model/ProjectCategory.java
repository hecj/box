package com.boxamazing.service.project.model;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.util.CacheName;
import com.jfinal.plugin.activerecord.Model;

/**
 * 产品分类
 * @author HECJ
 */
public class ProjectCategory extends Model<ProjectCategory>{
	
	private static final long serialVersionUID = 1L;
	
	public static final ProjectCategory dao = new ProjectCategory();
	
	private static final Log log = LogFactory.getLog(ProjectCategory.class);
	
	public List<ProjectCategory> findAll(){
		try {
			List<ProjectCategory> list = dao.findByCache(CacheName.CONSTANT_CACHE, "project_category_list", "select * from project_category t order by t.sort asc");
			return list;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
}
