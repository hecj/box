package com.boxamazing.admin.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.jfinal.plugin.IPlugin;

public class QuartzPlugin implements IPlugin {

	public static Log log = LogFactory.getLog(QuartzPlugin.class);
	
	private SchedulerFactory schedulerFactory = new StdSchedulerFactory();
	
	private static String defaultJobFile = "job.properties";
	
	private List<Quartz> quartzList = new ArrayList<Quartz>();

	private List<Scheduler> schedulerList = new ArrayList<Scheduler>();
	
	public QuartzPlugin() {
		this(defaultJobFile);
	}
		
	public QuartzPlugin(String jobFile) {
		Properties props = new Properties();
		try {
			props.load(getClass().getClassLoader().getResourceAsStream(jobFile));
			Set<String> keys = propertiesToStartWith(props);
			toQuartzList(keys,props);
		} catch (IOException e) {
			log.error(" properties file load exception , file name : " + jobFile);
			e.printStackTrace();
		}
	}
	
	private void toQuartzList(Set<String> keys ,Properties p){
		
		for(String key : keys){
			
			String clazz = p.getProperty(key+"."+"class");
			String cron = p.getProperty(key+"."+"cron");
			String enable = p.getProperty(key+"."+"enable");
			try {
				
				Quartz quartz = new Quartz();
				
				if(clazz == null || clazz.length() == 0){
					log.error(key +" , clazz is empty ");
					continue;
				}
				quartz.setClazz(clazz);
				
				if(cron == null || cron.length() == 0){
					log.error(key +" , cron is empty ");
					continue;
				}
				quartz.setCron(cron);
				
				if(enable == null || enable.length() == 0 || enable.trim().equals("true")){
					quartz.setEnable(true);
				} else {
					quartz.setEnable(false);
				}
				
				quartzList.add(quartz);
			} catch (Exception e) {
				log.error(key + " is error ");
			}
		}
	}

	private Set<String> propertiesToStartWith(Properties p) {
		Set<String> keys = new HashSet<String>();
		Set<Object> keySet = p.keySet();
		for (Object o : keySet) {
			String key = (String) o;
			int index = key.indexOf(".");
			if(index != -1){
				keys.add(key.substring(0,index));
			}
		}
		return keys;
	}
	
	public boolean start() {

		log.info(" QuartzPlugin start ...");
		
		for(Quartz quartz : quartzList){
			try {
				
				if(!quartz.isEnable()){
					log.info(quartz.getClazz()+" task is stop");
					continue;
				}
				Scheduler scheduler = schedulerFactory.getScheduler();
				String cronExpression = quartz.getCron();
				Class clazz = Class.forName(quartz.getClazz().trim());
				String jobClassName = clazz.getName();
				
				JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobClassName, jobClassName).build();
				
				CronTrigger cronTrigger=TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
		                .startNow().build();
				
				scheduler.scheduleJob(jobDetail, cronTrigger);
				
				schedulerList.add(scheduler);
				
				scheduler.start();
				
			} catch (Exception e) {
				log.error(" exception : "+ quartz.toString());
			}
		}
		
		return true;
	}

	public boolean stop() {

		log.info(" QuartzPlugin stop ...");
		
		for(Scheduler scheduler : schedulerList){
			try {
				log.info(" close scheduler : "+scheduler.getSchedulerName()+","+scheduler.getSchedulerInstanceId());
				scheduler.shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}

}