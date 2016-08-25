package cn.codegen.jfinal.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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

public class DUBBO_MYSQL_JFINALX {
	private static String mainUrl="/dubbo_gen_config/main.properties";
	private static String pn=Ukit1.getPjPath();//"jfinal_demo";//"el"
	private static String webapp="WebRoot\\page\\";//"WebRoot";//WebRoot\\
	private static String packPath="main\\java\\com\\demo";
	private static String module_name="test";
	public static void main(String[] args) throws Exception {
		//全局环境变量
		run();
	}
	public static void run() throws Exception, IOException,
			FileNotFoundException, SQLException {
		Map<String, Object> content=new HashMap<String, Object>();
		
		InitDBInfo in = new InitDBInfo(mainUrl);
		BaseMe bm = in.initbaseMe();
		Configration configration = in.getConfigration();
		Properties var = new ConfigReader(configration.getVar()).getProperties();
		Properties db2jdbctypemap = new ConfigReader(configration.getDb2jdbcMapping()).getProperties();
		Properties db2javatypemap = new ConfigReader(configration.getDb2javaMapping()).getProperties();
		Properties db2testdataMapping = new ConfigReader(configration.getDb2testdataMapping()).getProperties();
		BaseMe.var=var;
		BaseMe.db2jdbctypemap=db2jdbctypemap;
		BaseMe.db2javatypemap=db2javatypemap;
		BaseMe.db2testdataMapping=db2testdataMapping;
//		Yaml.dump(bm, new File("D://dump.yml"));
//		BaseMe bm=  Yaml.loadType(new File("D://dump.yml"), BaseMe.class);
		
		Configration conf=bm.getConf();
		String templateencode = conf.getTemplateencoding();
//		File target = new File(conf.getOutputbaseurl());
		File pojomodel =  new File(conf.getTemplateurl());
//		if (!target.exists()) {
//			target.mkdir();
//		}
		//添加自定义工具
		addTool(content, var);
		//路径改变
		{
			packPath=var.getProperty("base_pack_url").replace(".", "/");
			module_name=var.getProperty("base_module_name");
		}
		
		//生成
		gen(content, in, bm, configration, templateencode, pojomodel);
		//关闭连接
		in.getConnection().close();
		LOG.log("done!!!!!!!!!");
//		WinRun.open(target.getAbsolutePath());
	}
	/**
	 * @param content
	 * @param in
	 * @param bm
	 * @param configration
	 * @param templateencode
	 * @param pojomodel
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static void gen(Map<String, Object> content, InitDBInfo in,
			BaseMe bm, Configration configration, String templateencode,
			File pojomodel) throws IOException, FileNotFoundException {
		//创建文件夹
//		File dir_model = FileUtil.createDir(target,"model");
//		File dir_page = FileUtil.createDir(target,"page");
//		File dir_service = FileUtil.createDir(target,"service");
//		File dir_serviceimpl = FileUtil.createDir(target,"serviceimpl");
//		File dir_servicetest = FileUtil.createDir(target,"servicetest");
//		File dir_dao = FileUtil.createDir(target,"dao");
//		File dir_daoimpl = FileUtil.createDir(target,"daoimpl");
//		File dir_common = FileUtil.createDir(target,"common");
//		File dir_test = FileUtil.createDir(target,"test");
//		File dir_other = FileUtil.createDir(target,"other");
			
		HashMap<String, TableMe> tablemap = bm.getTablesMap();
		
		//以下部分渲染模板
		for (String key : tablemap.keySet()) {
			
			String names = key.replaceAll(configration.getPrefix(), "");
			String fname=StringUtil.noUnderLineAndUpFirstChar(names);
			Object root = tablemap.get(key);
			content.put("root", root);
			/*{//model
				String templateName="Auth.ftl";
				File fi = FileUtil.createDir(pn+"\\src\\cn\\peon"+"\\"+names, false);
				File targetFile=FileUtil.createFile(fi,fname+".java");
				genearchFile(new File(pojomodel,"model"),templateName, targetFile, content,templateencode);
			}
			{//controller
				String templateName="AuthController.ftl";
				File fi = FileUtil.createDir(pn+"\\src\\cn\\peon"+"\\"+names, false);
				File targetFile=FileUtil.createFile(fi,fname+"Controller.java");
				genearchFile(new File(pojomodel,"model"),templateName, targetFile, content,templateencode);
			}
			{//Validator
				String templateName="AuthValidator.ftl";
				File fi = FileUtil.createDir(pn+"\\src\\cn\\peon"+"\\"+names, false);
				File targetFile=FileUtil.createFile(fi,fname+"Validator.java");
				genearchFile(new File(pojomodel,"model"),templateName, targetFile, content,templateencode);
			}
			*/
			{//model
				String templateName="Blog_.ftl";
				File fi = FileUtil.createDir(pn+"src\\main\\java\\"+packPath+"\\"+module_name+"\\api", false);
				File targetFile=FileUtil.createFile(fi,fname+"_.java");
				genearchFile(new File(pojomodel,"model"),templateName, targetFile, content,templateencode);
			}
			{//service
				String templateName="BlogService.ftl";
				File fi = FileUtil.createDir(pn+"src\\main\\java\\"+packPath+"\\"+module_name+"\\api", false);
				File targetFile=FileUtil.createFile(fi,fname+"Service.java");
				genearchFile(new File(pojomodel,"model"),templateName, targetFile, content,templateencode);
			}
			{//serviceimpl
				String templateName="BlogServiceImpl.ftl";
				File fi = FileUtil.createDir(pn+"src\\main\\java\\"+packPath+"\\"+module_name+"\\service\\impl", false);
				File targetFile=FileUtil.createFile(fi,fname+"ServiceImpl.java");
				genearchFile(new File(pojomodel,"model"),templateName, targetFile, content,templateencode);
			}
			{//controller
				String templateName="BlogController.ftl";
				File fi = FileUtil.createDir(pn+"src\\main\\java\\"+packPath+"\\web\\"+module_name+"", false);
				File targetFile=FileUtil.createFile(fi,fname+"Controller.java");
				genearchFile(new File(pojomodel,"model"),templateName, targetFile, content,templateencode);
			}
			
			
			
			
			{//_form_blog.ftl
				String templateName="_form_blog.ftl";
				File fi = FileUtil.createDir(pn+"src\\main\\webapp\\WEB-INF\\views\\"+names, false);
				File targetFile=FileUtil.createFile(fi,"_form_"+names+".ftl");
				genearchFile(new File(pojomodel,"page"),templateName, targetFile, content,templateencode);
			}
			{//add_blog.ftl
				String templateName="add_blog.ftl";
				File fi = FileUtil.createDir(pn+"src\\main\\webapp\\WEB-INF\\views\\"+names, false);
				File targetFile=FileUtil.createFile(fi,"add_"+names+".ftl");
				genearchFile(new File(pojomodel,"page"),templateName, targetFile, content,templateencode);
			}
			{//ajax_blog_list_page.ftl
				String templateName="ajax_blog_list_page.ftl";
				File fi = FileUtil.createDir(pn+"src\\main\\webapp\\WEB-INF\\views\\"+names, false);
				File targetFile=FileUtil.createFile(fi,"ajax_"+names+"_list_page.ftl");
				genearchFile(new File(pojomodel,"page"),templateName, targetFile, content,templateencode);
			}
			{//ajax_blog_list.ftl
				String templateName="ajax_blog_list.ftl";
				File fi = FileUtil.createDir(pn+"src\\main\\webapp\\WEB-INF\\views\\"+names, false);
				File targetFile=FileUtil.createFile(fi,"ajax_"+names+"_list.ftl");
				genearchFile(new File(pojomodel,"page"),templateName, targetFile, content,templateencode);
			}
			{//edit_blog.ftl
				String templateName="edit_blog.ftl";
				File fi = FileUtil.createDir(pn+"src\\main\\webapp\\WEB-INF\\views\\"+names, false);
				File targetFile=FileUtil.createFile(fi,"edit_"+names+".ftl");
				genearchFile(new File(pojomodel,"page"),templateName, targetFile, content,templateencode);
			}
			{//list_blog.ftl
				String templateName="list_blog.ftl";
				File fi = FileUtil.createDir(pn+"src\\main\\webapp\\WEB-INF\\views\\"+names, false);
				File targetFile=FileUtil.createFile(fi,"list_"+names+".ftl");
				genearchFile(new File(pojomodel,"page"),templateName, targetFile, content,templateencode);
			}
			
		
			
			
		 
		}
		//对页面配置等生成
		gen2(content, in, bm, templateencode, pojomodel);
	}
	private static void gen2(Map<String, Object> content, InitDBInfo in,
			BaseMe bm, String templateencode, File pojomodel)
			throws IOException, FileNotFoundException {
		content.put("root", bm);
		
		{//DemoConfig_web.ftl
			String templateName="WebDemoConfig.ftl";
			File targetFile=FileUtil.createFile(new File(pn+"src\\main\\java\\"+packPath+"\\web"+"\\common\\WebDemoConfig.java"));
			genearchFile(new File(pojomodel,"common"),templateName, targetFile, content,templateencode);
		}
		{//commonconfig
			String templateName="DemoConfig.ftl";
			File targetFile=FileUtil.createFile(new File(pn+"src\\main\\java\\"+packPath+"\\web"+"\\common\\DemoConfig.java"));
			genearchFile(new File(pojomodel,"common"),templateName, targetFile, content,templateencode);
		}
		{//Provider.ftl
			String templateName="Provider.ftl";
			File targetFile=FileUtil.createFile(new File(pn+"src\\main\\java\\"+packPath+"\\"+module_name+"\\service\\Provider.java"));
			genearchFile(new File(pojomodel,"common"),templateName, targetFile, content,templateencode);
		}
		
		{//index.ftl
			String templateName="index.ftl";
			File targetFile = FileUtil.createFile(new File(pn+"src\\main\\webapp\\WEB-INF\\views\\index\\"+"index.ftl"));
			genearchFile(new File(pojomodel,"common"),templateName, targetFile, content,templateencode);
		}
		{//config
			String templateName="config.ftl";
			File targetFile=FileUtil.createFile(new File(pn+"src\\main\\resources\\config.txt"));
			Configration db = in.getConfigration();
			content.put("db", db);
			genearchFile(pojomodel,templateName, targetFile, content,templateencode);
		}
		 
	 
	}
	private static void addTool(Map<String, Object> content, Properties var) {
		{
			content.put("var", var);//用户自定义变量
			content.put("string_kit", new StringUtil());
			content.put("log_kit", new LOG());
			content.put("freemarker_kit", new FreemarkerTempleteUtil());
			content.put("ukit1", new Ukit1());
		}
	}
	/**执行策略 支持freemarker和velocity模板
	 * @param pojomodel
	 * @param templateName
	 * @param targetFile
	 * @param content
	 * @param templateencode
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void genearchFile(File pojomodel, String templateName,
			File targetFile, Map<String, Object> content, String templateencode) throws FileNotFoundException, IOException {
		File seachFile = FileUtil.seachFile( pojomodel.getPath()+"\\"+templateName);
		if (seachFile.exists()) {
			pojomodel=seachFile.getParentFile();
			String ext=FileUtil.getExt(templateName);
			if (ext.equals("ftl")) {
				FreemarkerTempleteUtil.genearchFile(pojomodel, templateName, targetFile, content, templateencode);
			}
			if (ext.equals("vm")) {
				VelocityTempleteUtil.genearchFile(pojomodel, templateName, targetFile, content, templateencode);
			}
			FreemarkerTempleteUtil.genearchFile(pojomodel, templateName, targetFile, content, templateencode);
		}else{
			LOG.error(pojomodel+"下的"+templateName+"模板未找到!");
		}
		
	}
	
}


