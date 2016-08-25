package com.demo.web.common;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.demo.web.index.IndexController;
import com.demo.web.test.UserController;
import com.demo.web.test.TbcController;
import com.jfinal.aop.Interceptor;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Action;
import com.jfinal.core.ControllerInstancer;
import com.jfinal.core.JFinal;
import com.jfinal.core.JfinalxController;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.SqlReporter;
import com.jfinal.plugin.activerecord.dialect.Dialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.spring.SpringPlugin;

/**
 * API引导式配置
 */
public class WebDemoConfig extends JFinalConfig {
	FileSystemXmlApplicationContext ctx;
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("config.txt");
		me.setDevMode(getPropertyToBoolean("devMode", false));
		
		ControllerInstancer controllerInstancer=new ControllerInstancer() {
			
			@Override
			public JfinalxController instance(Action action) throws InstantiationException,
					IllegalAccessException {
				JfinalxController bean = (JfinalxController) ctx.getBean(action.getControllerClass());
				if (bean==null) {
					System.err.println(action+"未找到spring中配置的bean!!");
				}
				return bean;
			}
		};
		me.setControllerInstancer(controllerInstancer);
		
		me.setFreeMarkerViewExtension("ftl");
		me.setBaseViewPath("/WEB-INF/views");
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", IndexController.class, "/index");//默认首页
		// 第三个参数省略时默认与第一个参数值相同，表示视图页面的路径
		me.add("/user", UserController.class);	//		
		me.add("/tbc", TbcController.class);	//模型字段		
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		
		ctx = new FileSystemXmlApplicationContext("classpath*:spring/*.xml");
		me.add(new SpringPlugin(ctx));
		System.out.println("jfinal plugin ok");
		
				

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
		JFinal.start("src/main/webapp", 80, "/", -1);
	}
	
	@Override
	public void beforeJFinalStop() {
		super.beforeJFinalStop();
	}
}
