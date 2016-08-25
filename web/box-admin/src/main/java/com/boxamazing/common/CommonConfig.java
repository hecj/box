package com.boxamazing.common;

import com.alipay.config.AlipayConfig;
import com.boxamazing.admin.QueryController;
import com.boxamazing.admin.StaticsController;
import com.boxamazing.admin.balanceUser.BalanceUserController;
import com.boxamazing.admin.controller.CommentController;
import com.boxamazing.admin.controller.OrdersController;
import com.boxamazing.admin.controller.SendEmailRecordController;
import com.boxamazing.admin.controller.SendSmsRecordController;
import com.boxamazing.admin.controller.TaskMessageQueueController;
import com.boxamazing.admin.controller.code.ZipCodeController;
import com.boxamazing.admin.controller.pay.AlipayController;
import com.boxamazing.admin.controller.pay.AlipayRecordController;
import com.boxamazing.admin.controller.pay.OrdersRefundController;
import com.boxamazing.admin.controller.pay.WithdrawalsController;
import com.boxamazing.admin.plugin.QuartzPlugin;
import com.boxamazing.admin.project.ProjectController;
import com.boxamazing.admin.sys.CompanyController;
import com.boxamazing.admin.sys.DbsController;
import com.boxamazing.admin.sys.MController;
import com.boxamazing.admin.sys.MsgController;
import com.boxamazing.admin.sys.OluController;
import com.boxamazing.admin.sys.PUserController;
import com.boxamazing.admin.sys.QController;
import com.boxamazing.admin.sys.QoController;
import com.boxamazing.admin.sys.RController;
import com.boxamazing.admin.upload.UploadController;
import com.boxamazing.admin.user.UserCertifyController;
import com.boxamazing.admin.user.UserController;
import com.boxamazing.admin.user.UserFeedBackController;
import com.boxamazing.common.interceptor.AuthInterceptor;
import com.boxamazing.common.interceptor.LogInterceptor;
import com.boxamazing.common.interceptor.LoginInterceptor;
import com.boxamazing.common.interceptor.SessionInterceptor;
import com.boxamazing.service.balance_reg.model.Balance_reg;
import com.boxamazing.service.code.Areas;
import com.boxamazing.service.code.Cities;
import com.boxamazing.service.code.Provinces;
import com.boxamazing.service.comment.model.Comment;
import com.boxamazing.service.config.model.Config;
import com.boxamazing.service.dbs.model.Dbs;
import com.boxamazing.service.delivery.model.Delivery;
import com.boxamazing.service.delivery.model.Delivery_b;
import com.boxamazing.service.error.DealException;
import com.boxamazing.service.m.model.M;
import com.boxamazing.service.msg.model.Msg;
import com.boxamazing.service.ol.model.Ol;
import com.boxamazing.service.olu.model.Olu;
import com.boxamazing.service.orders.model.OrderAddress;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.orders.model.OrdersRefund;
import com.boxamazing.service.orders.model.ShipMode;
import com.boxamazing.service.pay.model.AlipayOrderRefundRecord;
import com.boxamazing.service.pay.model.AlipayRecord;
import com.boxamazing.service.pay.model.Fundrecord;
import com.boxamazing.service.pay.model.RechargeDetailPre;
import com.boxamazing.service.pay.model.RechargeDetailRecord;
import com.boxamazing.service.pay.model.Withdrawals;
import com.boxamazing.service.pay.model.WithdrawalsDetail;
import com.boxamazing.service.product.model.Product;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.project.model.ProjectAuditRecord;
import com.boxamazing.service.project.model.ProjectCategory;
import com.boxamazing.service.project.model.ProjectProgress;
import com.boxamazing.service.project.model.ProjectRecomm;
import com.boxamazing.service.q.model.Q;
import com.boxamazing.service.qh.model.Qh;
import com.boxamazing.service.qo.model.Qo;
import com.boxamazing.service.r.model.R;
import com.boxamazing.service.sms.model.NoticeTemplate;
import com.boxamazing.service.sms.model.SendEmailRecord;
import com.boxamazing.service.sms.model.SendSmsRecord;
import com.boxamazing.service.sms.model.TaskMessageQueue;
import com.boxamazing.service.subscribe.model.Subscribe;
import com.boxamazing.service.system_message.model.SystemMessage;
import com.boxamazing.service.u.model.PUser;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.user.model.UserFeedBack;
import com.boxamazing.service.util.Constant;
import com.boxamazing.util.DateUtil;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;

/**
 * API引导式配置
 */
public class CommonConfig extends JFinalConfig {

    /**
     * 配置常量
     */
    public void configConstant(Constants me) {
        // 加载少量必要配置，随后可用getProperty(...)获取值
        loadPropertyFile("app.conf");
        me.setDevMode(getPropertyToBoolean("devMode", false));
        me.setViewType(ViewType.FREE_MARKER);
        me.setFreeMarkerViewExtension(".ftl");
        me.setBaseViewPath("/page");
        
        // 上传文件最大10M
        me.setMaxPostSize(10*1024*1024);
    }

    /**
     * 配置路由
     */
    public void configRoute(Routes me) {
        me.add("/", CommonController.class);

        me.add("/dbs", DbsController.class);
        me.add("/u", PUserController.class);
        me.add("/m", MController.class);
        me.add("/qo", QoController.class);
        me.add("/r", RController.class);
        me.add("/q", QController.class);
        me.add("/user", UserController.class);
        me.add("/query", QueryController.class);
        me.add("/statics", StaticsController.class);
        me.add("/company", CompanyController.class);
        me.add("/msg", MsgController.class);
        me.add("/olu", OluController.class);
        me.add("/project", ProjectController.class);
        me.add("/usercertify", UserCertifyController.class);
        me.add("/balanceUser", BalanceUserController.class);
        me.add("/upload", UploadController.class);
        me.add("/zipcode", ZipCodeController.class);
        me.add("/orders", OrdersController.class);
        me.add("/alipayRecord", AlipayRecordController.class);
        me.add("/message/sendSmsRecord", SendSmsRecordController.class);
        me.add("/message/sendEmailRecord", SendEmailRecordController.class);
        me.add("/message/comment", CommentController.class);
        me.add("/message/taskMessageQueue", TaskMessageQueueController.class);
        me.add("/pay/ordersRefund", OrdersRefundController.class);
        me.add("/pay/withdrawals", WithdrawalsController.class);
        me.add("/userFeedBack", UserFeedBackController.class);
        me.add("/alipay", AlipayController.class);
    }

    /**
     * 配置插件
     */
    public void configPlugin(Plugins me) {
        // 配置C3p0数据库连接池插件
        C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());

        c3p0Plugin.setMaxPoolSize(2);
        c3p0Plugin.setMinPoolSize(2);
        me.add(c3p0Plugin);

        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        me.add(arp);

        arp.setShowSql(true);
        arp.addMapping("puser", PUser.class);
        arp.addMapping("user", User.class);
        arp.addMapping("balance_reg", Balance_reg.class);
        arp.addMapping("project", Project.class);
        arp.addMapping("product", Product.class);
        arp.addMapping("project_recomm",ProjectRecomm.class);
        arp.addMapping("subscribe",Subscribe.class);
        arp.addMapping("project_progress",ProjectProgress.class);
        arp.addMapping("project_category",ProjectCategory.class);
        arp.addMapping("project_audit_record",ProjectAuditRecord.class);
        arp.addMapping("comment","id" ,Comment.class);
        arp.addMapping("config", "key", Config.class);
        arp.addMapping("provinces", Provinces.class);
        arp.addMapping("cities", Cities.class);
        arp.addMapping("areas", Areas.class);
        arp.addMapping("orders", Orders.class);
        arp.addMapping("order_address", OrderAddress.class);
        arp.addMapping("ship_mode", ShipMode.class);
        arp.addMapping("delivery", Delivery.class);
        arp.addMapping("delivery_b", Delivery_b.class);
        arp.addMapping("alipay_record", AlipayRecord.class);
        arp.addMapping("send_sms_record", SendSmsRecord.class);
        arp.addMapping("send_email_record", SendEmailRecord.class);
        arp.addMapping("orders_refund", OrdersRefund.class);
        arp.addMapping("task_message_queue", TaskMessageQueue.class);
        arp.addMapping("system_message", SystemMessage.class);
        arp.addMapping("user_feedback", UserFeedBack.class);
        arp.addMapping("alipay_order_refund_record", AlipayOrderRefundRecord.class);
        arp.addMapping("fundrecord", Fundrecord.class);
        arp.addMapping("notice_template", NoticeTemplate.class);
        arp.addMapping("recharge_detail_pre", RechargeDetailPre.class);
        arp.addMapping("withdrawals",Withdrawals.class);
        arp.addMapping("withdrawals_detail",WithdrawalsDetail.class);
        arp.addMapping("recharge_detail_record", RechargeDetailRecord.class);
        arp.addMapping("deal_exception", DealException.class);
        
        /**
         * 配置第二个数据源
         * TODO 做权限管理时需重构如下表
         * by HECJ
         */
        C3p0Plugin c3p0Plugin_admin = new C3p0Plugin(getProperty("jdbcUrl_admin"), getProperty("user_admin"), getProperty("password_admin").trim());
        c3p0Plugin_admin.setMaxPoolSize(2);
        c3p0Plugin_admin.setMinPoolSize(2);
        me.add(c3p0Plugin_admin);
        // 配置ActiveRecord插件
        ActiveRecordPlugin arp_admin = new ActiveRecordPlugin("admin",c3p0Plugin_admin);
        me.add(arp_admin);
        arp_admin.setShowSql(true);
        arp_admin.addMapping("dbs", Dbs.class);
        arp_admin.addMapping("m", M.class);
        arp_admin.addMapping("msg", Msg.class);
        arp_admin.addMapping("ol", Ol.class);
        arp_admin.addMapping("olu", Olu.class);
        arp_admin.addMapping("q", Q.class);
        arp_admin.addMapping("qh", Qh.class);
        arp_admin.addMapping("qo", Qo.class);
        arp_admin.addMapping("r", R.class);
        
        // 缓存策略
        me.add(new EhCachePlugin());
        // 任务
        me.add(new QuartzPlugin());
        
    }

    /**
     * 配置全局拦截器
     */
    public void configInterceptor(Interceptors me) {
        me.add(new LoginInterceptor());
        me.add(new AuthInterceptor());
        me.add(new LogInterceptor());
        me.add(new SessionInterceptor());
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
        //JFinal.start(getPjPath()+"src/main/webapp", 80, "/", 5);
        JFinal.start("src/main/webapp", 81, "/", 300);

    }

    @Override
    public void afterJFinalStart() {
		System.out.println(" - 系统已启动 -  初始化常量 - "+DateUtil.currentDatetime());
		Constant.STATIC_URL = getProperty("static_url");
		Constant.WEBROOT_URL = getProperty("WEBROOT_URL");
		
		/*--------支付宝接口配置----------*/
		AlipayConfig.seller_email = getProperty("alipay_seller_email");
		AlipayConfig.partner = getProperty("alipay_PID");
		AlipayConfig.key = getProperty("alipay_Key");
		Constant.refund_order_notify_url = getProperty("refund_order_notify_url");
		Constant.withdrawals_order_notify_url = getProperty("withdrawals_order_notify_url");
		
    }

    /**
     * 获取项目路径，为了兼容idea开发获取项目路径  正式发布时候 去掉
     *
     * @return
     */
    public static String getPjPath() {
        String pa = CommonConfig.class.getResource("").toString().replaceFirst("file:/", "").split("WebRoot/WEB-INF")[0];
        if (pa.contains("/target/classes/")) {//maven路径
            return pa.split("target/classes")[0];
        }
        if (pa.contains("/target/test-classes/")) {//maven路径
            return pa.split("target/test-classes")[0];
        }
        return pa;
    }
}
