/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.weixin.demo;

import com.boxamazing.service.openiduid.model.Openiduid;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.wxuser.model.Wxuser;
import com.boxamazing.weixin.controller.AdminController;
import com.boxamazing.weixin.controller.BindController;
import com.boxamazing.weixin.controller.RootController;
import com.boxamazing.weixin.interceptor.LoginInterceptor;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.jfinal.weixin.sdk.api.ApiConfigKit;

public class WeixinConfig extends JFinalConfig {
	
	/**
	 * 如果生产环境配置文件存在，则优先加载该配置，否则加载开发环境配置文件
	 * @param pro 生产环境配置文件
	 * @param dev 开发环境配置文件
	 */
	public void loadProp(String pro, String dev) {
		try {
			PropKit.use(pro);
		}
		catch (Exception e) {
			PropKit.use(dev);
		}
	}
	
	public void configConstant(Constants me) {
		loadProp("a_little_config_pro.txt", "a_little_config.txt");
		loadPropertyFile("dbconfig");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		
		// ApiConfigKit 设为开发模式可以在开发阶段输出请求交互的 xml 与 json 数据
		ApiConfigKit.setDevMode(me.getDevMode());

		me.setViewType(ViewType.FREE_MARKER);
		me.setFreeMarkerViewExtension(".ftl");
		me.setBaseViewPath("/page");
	}
	
	public void configRoute(Routes me) {
		me.add("/msg", WeixinMsgController.class);
		me.add("/api", WeixinApiController.class, "/api");

		me.add("/", RootController.class);
		me.add("/bind", BindController.class);
		me.add("/admin", AdminController.class);
	}
	
	public void configPlugin(Plugins me) {
		// C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		// me.add(c3p0Plugin);
		
		// EhCachePlugin ecp = new EhCachePlugin();
		// me.add(ecp);
		//配置C3p0数据库连接池
		C3p0Plugin c3p0Plugin = new C3p0Plugin(
				getProperty("jdbcUrl"),
				getProperty("user"),
				getProperty("password").trim()
		);
		c3p0Plugin.setMaxPoolSize(2);
		c3p0Plugin.setMinPoolSize(2);
		me.add(c3p0Plugin);

		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);

		arp.setShowSql(true);
		arp.addMapping("wxuser", "openid", Wxuser.class);
		//arp.addMapping("openiduid", "openid, uid", Openiduid.class);
		arp.addMapping("user", "id", User.class);
	}
	
	public void configInterceptor(Interceptors me) {
		me.add(new LoginInterceptor());
		
	}
	
	public void configHandler(Handlers me) {
		
	}
	
	public static void main(String[] args) {
		JFinal.start("WebRoot", 8080, "/", 5);
	}
}
