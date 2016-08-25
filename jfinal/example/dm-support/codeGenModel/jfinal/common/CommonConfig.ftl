package ${var.base_pack_url}.common;

import java.util.Date;

<#list root.tablesMap?keys as key >
import ${var.base_pack_url}.service.${root.tablesMap[key].name}.model.${root.tablesMap[key].fname};
import ${var.base_pack_url}.admin.${root.tablesMap[key].name}.${root.tablesMap[key].fname}Controller;
</#list>

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
/**
 * API引导式配置
 */
public class CommonConfig extends JFinalConfig {
	
	private static Date sd;
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("dbconfig");
		me.setDevMode(getPropertyToBoolean("devMode", false));
		me.setViewType(ViewType.FREE_MARKER);
		me.setFreeMarkerViewExtension(".ftl");
		me.setBaseViewPath("/page");
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", CommonController.class);
		
		<#list root.tablesMap?keys as key >
		me.add("/${root.tablesMap[key].name}", ${root.tablesMap[key].fname}Controller.class);
		</#list>
		
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
		me.add(c3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		
		<#list root.tablesMap?keys as key >
		arp.addMapping("${root.tablesMap[key].name}", ${root.tablesMap[key].fname}.class);
		</#list>
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) { 
		sd = new Date();
		System.out.println(sd);
		//JFinal.start(getPjPath()+"src/main/webapp", 80, "/", 5);
		JFinal.start("WebRoot", 80, "/", 5);
		
	}
	@Override
	public void afterJFinalStart() {
//		super.afterJFinalStart();
		Date tl = new Date();
		System.out.println(tl);
	}
	
	 /**获取项目路径，为了兼容idea开发获取项目路径  正式发布时候 去掉
     * @return
     */
    public static String getPjPath(){
        String pa=CommonConfig.class.getResource("").toString().replaceFirst("file:/", "").split("WebRoot/WEB-INF")[0];
        if (pa.contains("/target/classes/")) {//maven路径
            return pa.split("target/classes")[0];
        }
        if (pa.contains("/target/test-classes/")) {//maven路径
            return pa.split("target/test-classes")[0];
        }
        return pa;

    }
}
