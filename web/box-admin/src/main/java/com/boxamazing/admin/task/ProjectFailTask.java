package com.boxamazing.admin.task;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boxamazing.service.project.ProjectService;
import com.boxamazing.service.project.model.Project;
/**
 * 处理众筹中的项目失败任务
 */
public class ProjectFailTask implements Job{
	
	public static Log log = LogFactory.getLog(ProjectFailTask.class);
	
	/**
	 * 此处最好限制循环处理次数，避免出现死循环
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		log.info("=================TASK 正在处理众筹中的项目失败任务开始");

		ProjectService projectService = new ProjectService();
		
		try {
			
			List<Long> projectList = Project.dao.findProjectByStatusAndExpirestime();
			
			if(projectList != null && projectList.size() > 0){
				for(long project_id : projectList){
					Map<String,String> result = projectService.procesProjectFail(project_id);
					log.info("处理结果:"+result);
				}
			} else{
				log.info("本次任务未查询到数据，结束！");
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		log.info("=====================TASK 正在处理众筹中的项目失败任务结束");
	}
}