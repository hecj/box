package com.boxamazing.service.project;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.project.model.Project;
import com.jfinal.plugin.activerecord.Db;

/**
 * 项目相关处理
 */
public class ProjectService {

	public static Log log = LogFactory.getLog(ProjectService.class);
	
	/**
	 * 处理项目成功/失败业务
	 * 1.众筹中的项目
	 * 2.众筹时间到期
	 * 3.判断已众筹金额
	 */
	public Map<String,String> procesProjectFail(long project_id){
		
		log.info("正在处理项目成功/失败 project_id："+project_id);
		Map<String,String> result = new HashMap<String,String>();
		
		try {
			
			Project project = Project.dao.findById(project_id);
			if(project == null){
				log.info("项目不存在 project_id ："+project_id);
				result.put("code", "-1");
				result.put("message", "项目不存在 project_id ："+project_id);
				return result;
			}
			
			// 1.众筹中的项目
			if(project.getInt("status") != 8){
				log.info("项目非众筹中 project_id ："+project_id+",status:"+project.getInt("status"));
				result.put("code", "-2");
				result.put("message", "项目非众筹中 project_id ："+project_id+",status:"+project.getInt("status"));
				return result;
			}
			
			// 2.众筹时间到期
			long expirestime = project.getLong("expirestime");
			long curr_time = System.currentTimeMillis();
			if(expirestime > curr_time){
				log.info("expirestime:"+expirestime+",curr_time:"+curr_time);
				result.put("code", "-3");
				result.put("message", "项目时间未到期 project_id ："+project_id);
				return result;
			}
			
			// 3.众筹金额
			BigDecimal fundgoal = project.getBigDecimal("fundgoal");
			BigDecimal fundnow = project.getBigDecimal("fundnow");
			log.info("众筹金额："+fundgoal+",已众筹金额："+fundnow);
			if(fundnow.compareTo(fundgoal) < 0){
				// 众筹失败
				log.info("【众筹失败】");
				Db.update("update project set status = 9 where id = ? and status = 8 ",new Object[]{project_id});
				result.put("code", "200");
				result.put("message", "【众筹失败】");
			}else{
				// 众筹成功
				log.info("【众筹成功】");
				Db.update("update project set status = 10 where id = ? and status = 8 ",new Object[]{project_id});
				result.put("code", "200");
				result.put("message", "【众筹成功】");
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			result.put("code", "-100000");
			result.put("message", "处理异常："+e.getMessage());
		}
		
		return result;
	}
	
}
