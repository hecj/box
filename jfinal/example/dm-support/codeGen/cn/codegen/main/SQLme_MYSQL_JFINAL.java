package cn.codegen.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import cn.peon.core.db.common.config.ConfigReader;
import cn.peon.core.db.common.config.Configration;
import cn.peon.core.db.common.connect.InitDBInfo;
import cn.peon.core.db.mysql.meta.BaseMe;
import cn.peon.core.db.mysql.meta.TableMe;
import cn.peon.core.file.FileUtil;
import cn.peon.core.kit.StringUtil;
import cn.peon.core.kit.template.FreemarkerTempleteUtil;
import cn.peon.core.log.LOG;

public class SQLme_MYSQL_JFINAL {
	private static String mainUrl="/jfinal/main.properties";
	private static String pn=Ukit1.getPjPath();//"jfinal_demo";//"el"
	private static String packPath="main\\java\\com\\demo";
	private static String webapp="src\\main\\webapp\\";//WebRoot\\
	
	
	public static void main(String[] args) throws Exception {
		//全局环境变量
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
		File pojomodel = new File(pn+conf.getTemplateurl());
//		if (!target.exists()) {
//			target.mkdir();
//		}
		//添加自定义工具
		{
			content.put("var", var);//用户自定义变量
			content.put("string_kit", new StringUtil());
			content.put("log_kit", new LOG());
			content.put("freemarker_kit", new FreemarkerTempleteUtil());
			content.put("ukit1", new Ukit1());
		}
		
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
//		FileUtil.delDirs(new File(pn+"src\\"+packPath+""+"\\service\\"));
//		FileUtil.delDirs(new File(pn+"src\\"+packPath+""+"\\admin\\"));
		//以下部分渲染模板
		for (String key : tablemap.keySet()) {
			
			String names = key.replaceAll(configration.getPrefix(), "");
			String fname=StringUtil.noUnderLineAndUpFirstChar(names);
			Object root = tablemap.get(key);
			content.put("root", root);
			{//model
				String templateName="Auth.ftl";
				File fi = FileUtil.createDir(pn+"src\\"+packPath+""+"\\service\\"+names+"\\model", false);
				File targetFile=FileUtil.createFile(fi,fname+".java");
				genearchFile(new File(pojomodel,"model"),templateName, targetFile, content,templateencode);
			}
			{//controller
				String templateName="AuthController.ftl";
				File fi = FileUtil.createDir(pn+"src\\"+packPath+"\\admin"+"\\"+names, false);
				File targetFile=FileUtil.createFile(fi,fname+"Controller.java");
				genearchFile(new File(pojomodel,"model"),templateName, targetFile, content,templateencode);
			}
			{//Validator
				String templateName="AuthValidator.ftl";
				File fi = FileUtil.createDir(pn+"src\\"+packPath+"\\admin"+"\\"+names, false);
				File targetFile=FileUtil.createFile(fi,fname+"Validator.java");
				genearchFile(new File(pojomodel,"model"),templateName, targetFile, content,templateencode);
			}
          /*  {//Service
                String templateName="AuthService.ftl";
                File fi = FileUtil.createDir(pn+"src\\"+packPath+""+"\\service\\"+names, false);
                File targetFile=FileUtil.createFile(fi,fname+"Service.java");
                genearchFile(new File(pojomodel,"model"),templateName, targetFile, content,templateencode);
            }*/
			
			/*
			{//page_form
				String templateName="_form.ftl";
				File fi = FileUtil.createDir(pn+webapp+""+names, false);
				File targetFile=FileUtil.createFile(fi,"_form.ftl");
				genearchFile(new File(pojomodel,"page"),templateName, targetFile, content,templateencode);
			}
			{//page_form
				String templateName="add.ftl";
				File fi = FileUtil.createDir(pn+webapp+""+names, false);
				File targetFile=FileUtil.createFile(fi,"add.ftl");
				genearchFile(new File(pojomodel,"page"),templateName, targetFile, content,templateencode);
			}
			{//page_form
				String templateName="edit.ftl";
				File fi = FileUtil.createDir(pn+webapp+""+names, false);
				File targetFile=FileUtil.createFile(fi,"edit.ftl");
				genearchFile(new File(pojomodel,"page"),templateName, targetFile, content,templateencode);
			}
			{//page_form
				String templateName="index.ftl";
				File fi = FileUtil.createDir(pn+webapp+""+names, false);
				File targetFile=FileUtil.createFile(fi,"index.ftl");
				genearchFile(new File(pojomodel,"page"),templateName, targetFile, content,templateencode);
			}
			*/
         /*   {//page_form
                String templateName="list.ftl";
                File fi = FileUtil.createDir(pn+webapp+""+names, false);
                File targetFile=FileUtil.createFile(fi,names+"List.jsp");
                genearchFile(new File(pojomodel,"page"),templateName, targetFile, content,templateencode);
            }*/
       /*     {//page_form
                String templateName="listJs.ftl";
                File fi = FileUtil.createDir(pn+webapp+""+names, false);
                File targetFile=FileUtil.createFile(fi,names+"list.js");
                genearchFile(new File(pojomodel,"page"),templateName, targetFile, content,templateencode);
            }*/
          /*  {//page_form
                String templateName="detail.ftl";
                File fi = FileUtil.createDir(pn+webapp+""+names, false);
                File targetFile=FileUtil.createFile(fi,names+"Detail.jsp");
                genearchFile(new File(pojomodel,"page"),templateName, targetFile, content,templateencode);
            }*/
          /*  {//page_form
                String templateName="detailJs.ftl";
                File fi = FileUtil.createDir(pn+webapp+""+names, false);
                File targetFile=FileUtil.createFile(fi,names+"Detail.js");
                genearchFile(new File(pojomodel,"page"),templateName, targetFile, content,templateencode);
            }*/
			
//			{//mapper
//				String templateName="mapper.ftl";
//				File targetFile=FileUtil.createFile(dir_mapper,StringUtil.lowerFirstChar(fname)+"Mapper.xml");
//				genearchFile(pojomodel,templateName, targetFile, content,templateencode);
//			}
//			{//service
//				String templateName="service.ftl";
//				File targetFile=FileUtil.createFile(dir_service,fname+"Service.java");
//				genearchFile(pojomodel,templateName, targetFile, content,templateencode);
//			}
//			{//serviceimpl
//				String templateName="serviceimpl.ftl";
//				File targetFile=FileUtil.createFile(dir_serviceimpl,fname+"ServiceImpl.java");
//				genearchFile(pojomodel,templateName, targetFile, content,templateencode);
//			}
//			{//servicetest
//				String templateName="servicetest.ftl";
//				File targetFile=FileUtil.createFile(dir_servicetest,fname+"ServiceImplTests.java");
//				genearchFile(pojomodel,templateName, targetFile, content,templateencode);
//			}
//			{//dao
//				String templateName="dao.ftl";
//				File targetFile=FileUtil.createFile(dir_dao,fname+"Dao.java");
//				genearchFile(pojomodel,templateName, targetFile, content,templateencode);
//			}
//			{//daoimpl
//				String templateName="daoimpl.ftl";
//				File targetFile=FileUtil.createFile(dir_daoimpl,fname+"DaoImpl.java");
//				genearchFile(pojomodel,templateName, targetFile, content,templateencode);
//			}
		}
		content.put("root", bm);
		{//dbconfig
			String templateName="dbconfig.ftl";
			File targetFile=FileUtil.createFile(new File(pn+webapp+"WEB-INF\\dbconfig"));
			Configration db = in.getConfigration();
			content.put("db", db);
			genearchFile(pojomodel,templateName, targetFile, content,templateencode);
		}
		{//common
			String templateName="index.ftl";
			File targetFile=FileUtil.createFile(new File(pn+webapp+"common\\index.ftl"));
			genearchFile(new File(pojomodel,"common"),templateName, targetFile, content,templateencode);
		}
		{//commonconfig
			String templateName="CommonConfig.ftl";
			//E:\java_web\workspace\jfinal_gen\src\main\java\com\qdingnet
			//E:/java_web/workspace/jfinal_gen\src\com\qdinngnet
			File targetFile=FileUtil.createFile(new File(pn+"src\\"+packPath+"\\common\\CommonConfig.java"));
			genearchFile(new File(pojomodel,"common"),templateName, targetFile, content,templateencode);
		}

       /* {//page_form
            String templateName="step.ftl";
            File fi = FileUtil.createDir(pn+webapp, false);
            File targetFile=FileUtil.createFile(fi,"step.txt");
            genearchFile(new File(pojomodel,"page"),templateName, targetFile, content,templateencode);
        }*/
//		{//service.xml
//			String templateName="servicexml.ftl";
//			File targetFile=FileUtil.createFile(dir_springxml,"service.xml");
//			
//			genearchFile(pojomodel,templateName, targetFile, content,templateencode);
//		}
//		{//pkcheck.txt
//			String templateName="pkcheck.ftl";
//			File targetFile=FileUtil.createFile(dir_other,"主键检查.txt");
//			genearchFile(pojomodel,templateName, targetFile, content,templateencode);
//		}
//		{//test.txt
//			String templateName="test_ukit.ftl";
//			File targetFile=FileUtil.createFile(dir_test,"test_ukit.txt");
//			genearchFile(pojomodel,templateName, targetFile, content,templateencode);
//		}
//		{//test_velocity.txt
//			String templateName="test_velocity.vm";
//			File targetFile=FileUtil.createFile(dir_test,"test_velocity.txt");
//			genearchFile(pojomodel,templateName, targetFile, content,templateencode);
//		}
		in.getConnection().close();
		LOG.log("done!!!!!!!!!");
//		WinRun.open(target.getAbsolutePath());
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
		String ext=FileUtil.getExt(templateName);
		if (ext.equals("ftl")) {
			FreemarkerTempleteUtil.genearchFile(pojomodel, templateName, targetFile, content, templateencode);
		}
		if (ext.equals("vm")) {
//			VelocityTempleteUtil.genearchFile(pojomodel, templateName, targetFile, content, templateencode);
		}
	}
	
}


