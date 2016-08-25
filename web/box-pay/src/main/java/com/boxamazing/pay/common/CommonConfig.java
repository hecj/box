package com.boxamazing.pay.common;

import java.util.Date;

import com.boxamazing.pay.controller.IndexController;
import com.boxamazing.pay.controller.PayController;
import com.boxamazing.service.balance_reg.model.Balance_reg;
import com.boxamazing.service.comment.model.Comment;
import com.boxamazing.service.comment_support.model.Comment_support;
import com.boxamazing.service.config.model.Config;
import com.boxamazing.service.olu.model.Olu;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.pay.model.RechargeDetailPre;
import com.boxamazing.service.pay.model.RechargeDetailRecord;
import com.boxamazing.service.pay.model.Withdrawals;
import com.boxamazing.service.payPwd.model.UserPayPwd;
import com.boxamazing.service.product.model.Product;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.subscribe.model.Subscribe;
import com.boxamazing.service.user.model.User;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

/**
 * JFinal配置.
 */
public class CommonConfig extends JFinalConfig {

    @Override
    /**
     * 配置常量
     */
    public void configConstant(Constants constants) {
    	
    	constants.setFreeMarkerTemplateUpdateDelay(1);
    	
    	constants.setDevMode(true);
    	
        loadPropertyFile("app.conf");

    }

    /**
     * 配置路由
     */
    @Override
    public void configRoute(Routes routes) {

      
        routes.add("/", IndexController.class);
        routes.add("/pay", PayController.class);
    }

    /**
     * 配置插件
     *
     * @param plugins
     */
    @Override
    public void configPlugin(Plugins plugins) {
        //配置C3p0数据库连接池
        C3p0Plugin c3p0Plugin = new C3p0Plugin(
                getProperty("jdbcUrl"),
                getProperty("user"),
                getProperty("password").trim()
        );
        c3p0Plugin.setMaxPoolSize(2);
        c3p0Plugin.setMinPoolSize(2);
        plugins.add(c3p0Plugin);

        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        plugins.add(arp);

        arp.setShowSql(true);
        arp.addMapping("user", "id", User.class);
        arp.addMapping("project", "id", Project.class);
        arp.addMapping("olu", Olu.class);
        arp.addMapping("config", "key", Config.class);

        arp.addMapping("product", Product.class);
        arp.addMapping("subscribe", "pid", Subscribe.class);
//        arp.addMapping("support", Support.class);
        arp.addMapping("comment", Comment.class);
        arp.addMapping("comment_support", Comment_support.class);
        arp.addMapping("balance_reg", Balance_reg.class);
        arp.addMapping("orders", Orders.class);
        arp.addMapping("user_pay_pwd", UserPayPwd.class);
        arp.addMapping("recharge_detail_pre", RechargeDetailPre.class);
        arp.addMapping("recharge_detail_record", RechargeDetailRecord.class);
        arp.addMapping("withdrawals", Withdrawals.class);
    }

    /**
     * 配置全局拦截器
     *
     * @param interceptors
     */
    @Override
    public void configInterceptor(Interceptors interceptors) {

    }

    /**
     * 配置处理器.
     *
     * @param handlers
     */
    @Override
    public void configHandler(Handlers handlers) {

    }

    @Override
    public void afterJFinalStart() {

    }

    /**
     * 启动JFinal Server.
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("JFinal Server started:" + new Date());
        JFinal.start("src/main/webapp", 82, "/", 5);
    }
}
