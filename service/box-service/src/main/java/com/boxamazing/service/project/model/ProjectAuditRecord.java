package com.boxamazing.service.project.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 项目审核记录表
 * by HECJ
 */
public class ProjectAuditRecord extends Model<ProjectAuditRecord> {

	private static final long serialVersionUID = 1L;
	
	public static final ProjectAuditRecord dao = new ProjectAuditRecord();
	
	private static final Log log = LogFactory.getLog(ProjectAuditRecord.class);
	
	public ProjectAuditRecord findByProject(Long id,int status){
		log.info("项目id为"+id+"当前状态为"+status);
		List<ProjectAuditRecord> listPar = dao.find("select * from project_audit_record where project_id =? and status_after = ? order by create_at desc ",id,status);
		if(listPar.size()>0){
			return listPar.get(0);
		}else{
			return null;
		}
	}
	
}
