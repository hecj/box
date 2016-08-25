package ${var.base_pack_url}.${var.base_module_name}.service;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.jfinal.core.Const;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.SqlReporter;
import com.jfinal.plugin.activerecord.dialect.Dialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.spring.SpringPlugin;


 
public class Provider {
	
	public static Log log = LogFactory.getLog(Provider.class);
	
	
    public static void main(String[] args) throws Exception {
    	Provider pr=new Provider();
    	pr.jfinalStart();
 
        System.out.println("test service started..........");
        
        
       // pr.test();
        
        int w = System.in.read(); // 按任意键退出
        if (((char)w)=='e') {
			pr.jfinalStop();
		}
        
        
        
    }
    FileSystemXmlApplicationContext ctx ;
   /** private   void test() {
    	CacheTest cacheTest = (CacheTest) ctx.getBean("cacheTest");
    	cacheTest.run();
    	
	}**/
	private void jfinalStop() {
    	if(springplugin!=null){
    		springplugin.stop();
    	}
    	if(arp!=null){
    		arp.stop();
    	}
		if(c3p0Plugin!=null){
			c3p0Plugin.stop();
		}
		System.out.println("jfinal plugin closed!!!!");
	}
    C3p0Plugin c3p0Plugin;
    ActiveRecordPlugin arp;
    SpringPlugin springplugin;
//    http://alibaba.github.io/dubbo-doc-static/User+Guide-zh.htm#UserGuide-zh-%E5%BF%AB%E9%80%9F%E5%90%AF%E5%8A%A8
    public void jfinalStart(){
    	loadPropertyFile("config.txt");
		
		// 配置C3p0数据库连接池插件
		c3p0Plugin = new C3p0Plugin(getProperty("db.url"), getProperty("db.user"), getProperty("db.password").trim());
		
		
		// 配置ActiveRecord插件
		arp = new ActiveRecordPlugin(c3p0Plugin);
//		arp.addMapping("blog", Blog.class);	// 映射blog 表到 Blog模型
		
//		loadClass();
		
		boolean devMode=getPropertyToBoolean("devMode", false);
		arp.setDevMode(devMode);
		//设置打印sql
		arp.setShowSql(true);
		SqlReporter.setLogger(true);
		SqlReporter.setFormartsql(true);//设置格式化sql
		Dialect.setShowParams(true);//设置打印参数
		
		
		ctx = new FileSystemXmlApplicationContext("classpath*:spring/*.xml");
		springplugin = new SpringPlugin(ctx);
		
		c3p0Plugin.start();
		arp.start();
		springplugin.start();
		
		
		System.out.println("jfinal plugin ok");
    }
    
    
    
    /**
     * 预加载一些class
     * @return 
     */
    /*private Object[] loadClass() {
		return new Object[]{
				new Blog_()
		};
	}*/
	public Properties loadPropertyFile(String fileName) {
		return loadPropertyFile(fileName, Const.DEFAULT_ENCODING);
	}
    protected Prop prop = null;
    public Properties loadPropertyFile(String fileName, String encoding) {
		prop = PropKit.use(fileName, encoding);
		return prop.getProperties();
	}
	public Boolean getPropertyToBoolean(String key, Boolean defaultValue) {
		return getProp().getBoolean(key, defaultValue);
	}
	private Prop getProp() {
		if (prop == null)
			throw new IllegalStateException("Load propties file by invoking loadPropertyFile(String fileName) method first.");
		return prop;
	}
	public String getProperty(String key) {
		return getProp().get(key);
	}
	
}