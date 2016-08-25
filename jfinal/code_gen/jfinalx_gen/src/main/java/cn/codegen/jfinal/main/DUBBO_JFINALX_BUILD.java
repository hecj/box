package cn.codegen.jfinal.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import cn.codegen.jfinal.main.tool.Ukit1;
import cn.codegen.jfinal.velocityUtil.VelocityTempleteUtil;
import cn.peon.core.db.common.config.ConfigReader;
import cn.peon.core.db.common.config.Configration;
import cn.peon.core.db.common.connect.InitDBInfo;
import cn.peon.core.db.mysql.meta.BaseMe;
import cn.peon.core.db.mysql.meta.TableMe;
import cn.peon.core.file.FileUtil;
import cn.peon.core.kit.StringUtil;
import cn.peon.core.kit.template.FreemarkerTempleteUtil;
import cn.peon.core.log.LOG;

public class DUBBO_JFINALX_BUILD {
	private static String mainUrl="/dubbo/main.properties";
	private static String pn=Ukit1.getPjPath();//"jfinal_demo";//"el"
	private static String packPath="main\\java\\com\\demo";
	private static String module_name="test";
	
	private static File parent=new File(pn).getParentFile();
	private static File api_proj_url=new File(parent,"test_api");
	
	private static File provider_proj_url=new File(parent,"test_provider");
	
	private static File web_proj_url=new File(parent,"jfinal_demo_for_maven");
	
	 
	public static void main(String[] args) throws Exception {
		run();
//		WinRun.open(target.getAbsolutePath());
	}


	public static void run() throws Exception, IOException {
		//路径等加载
		init();
		//copy api
		api_copy();
		//copy provider
		provider_copy();
		//copy web
		web_copy();
		
		LOG.log("done!!!!!!!!!");
	}


	private static void api_copy() throws IOException {
		String targetDir=api_proj_url+"\\src\\main\\java\\"+packPath+"\\"+module_name+"\\api";
		FileUtil.createDir(targetDir, false);
		String from = pn+"src\\main\\java\\"+packPath+"\\"+module_name+"\\api";
		System.out.println("先复制api:"+targetDir+"  <==  "+ from);
		FileUtil.copyDirectiory(new File(from), targetDir);
		System.out.println("复制api完毕:"+api_proj_url);
	}
	private static void provider_copy() throws IOException {
		String targetDir=provider_proj_url+"\\src\\main\\java\\"+packPath+"\\"+module_name+"\\service";
		FileUtil.createDir(targetDir, false);
		String from = pn+"src\\main\\java\\"+packPath+"\\"+module_name+"\\service";
		System.out.println("先复制provider:"+targetDir+"  <==  "+ from);
		FileUtil.copyDirectiory(new File(from), targetDir);
		
		copy_provider_config();
		System.out.println("复制provider完毕:"+provider_proj_url);
	}


	private static void copy_provider_config() throws IOException {
		File config_from_File=(new File(pn+"src\\main\\resources\\config.txt"));
		File config_target_File=(new File(provider_proj_url+"\\src\\main\\resources\\config.txt"));
		FileUtil.createFile(config_target_File);
		FileUtil.copyFile(config_from_File, config_target_File);
		System.out.println(config_from_File+ "==>" +config_target_File);
	}
	private static void web_copy() throws IOException {
		copy_web_page();
		
		copy_controller();
		
		copy_web_config();
		
		copy_commonconfig();
	}


	private static void copy_web_page() throws IOException {
		String targetDir=web_proj_url+"\\src\\main\\webapp\\WEB-INF\\views\\";
		FileUtil.createDir(targetDir, false);
		String from =pn+"src\\main\\webapp\\WEB-INF\\views\\";
		System.out.println("先复制web_page:"+targetDir+"  <==  "+ from);
		FileUtil.copyDirectiory(new File(from), targetDir);
		System.out.println("复制web_page完毕:"+api_proj_url);
	}
	private static void copy_commonconfig() throws IOException {
		String targetDir=web_proj_url+"\\src\\main\\java\\"+packPath+"\\web"+"\\common\\WebDemoConfig.java";
		FileUtil.createFile(new File(targetDir));
		String from =pn+"src\\main\\java\\"+packPath+"\\web"+"\\common\\WebDemoConfig.java";
		System.out.println("先复制web_commonconfig:"+targetDir+"  <==  "+ from);
		FileUtil.copyFile(new File(from), new File(targetDir));
		System.out.println("复制web_commonconfig完毕:"+api_proj_url);
	}
	private static void copy_controller() throws IOException {
		String targetDir=web_proj_url+"\\src\\main\\java\\"+packPath+"\\web\\"+module_name+"";
		FileUtil.createDir(targetDir, false);
		String from =pn+"src\\main\\java\\"+packPath+"\\web\\"+module_name+"";
		System.out.println("先复制web_controller:"+targetDir+"  <==  "+ from);
		FileUtil.copyDirectiory(new File(from), targetDir);
		System.out.println("复制web_controller完毕:"+api_proj_url);
	}
	private static void copy_web_config() throws IOException {
		File config_from_File=(new File(pn+"src\\main\\resources\\config.txt"));
		File config_target_File=(new File(web_proj_url+"\\src\\main\\resources\\config.txt"));
		FileUtil.createFile(config_target_File);
		FileUtil.copyFile(config_from_File, config_target_File);
		System.out.println(config_from_File+ "==>" +config_target_File);
	}

	private static void init() throws Exception {
		InitDBInfo in = new InitDBInfo(mainUrl);
		BaseMe bm = in.initbaseMe();
		Configration configration = in.getConfigration();
		Properties var = new ConfigReader(configration.getVar()).getProperties();
		{
			packPath=var.getProperty("base_pack_url").replace(".", "/");
			module_name=var.getProperty("base_module_name");
		}
	}
 
	
}


