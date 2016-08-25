package com.boxamazing.webfront.common;

import java.util.Date;

import com.alipay.config.AlipayConfig;
import com.boxamazing.service.balance_reg.model.Balance_reg;
import com.boxamazing.service.code.Areas;
import com.boxamazing.service.code.Cities;
import com.boxamazing.service.code.Provinces;
import com.boxamazing.service.comment.model.Comment;
import com.boxamazing.service.comment_support.model.Comment_support;
import com.boxamazing.service.config.model.Config;
import com.boxamazing.service.delivery.model.Delivery;
import com.boxamazing.service.delivery.model.Delivery_b;
import com.boxamazing.service.fans.model.Fans;
import com.boxamazing.service.msg.model.MessageConfig;
import com.boxamazing.service.open_user.model.OpenUser;
import com.boxamazing.service.open_user.model.OpenUserRelation;
import com.boxamazing.service.orders.model.OrderAddress;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.orders.model.OrdersRefund;
import com.boxamazing.service.pay.model.AlipayRecord;
import com.boxamazing.service.pay.model.Fundrecord;
import com.boxamazing.service.pay.model.RechargeDetailPre;
import com.boxamazing.service.pay.model.RechargeDetailRecord;
import com.boxamazing.service.pay.model.Withdrawals;
import com.boxamazing.service.payPwd.model.UserPayPwd;
import com.boxamazing.service.private_message.model.PrivateMessage;
import com.boxamazing.service.product.model.Product;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.project.model.ProjectAuditRecord;
import com.boxamazing.service.project.model.ProjectCategory;
import com.boxamazing.service.project.model.ProjectProgress;
import com.boxamazing.service.project.model.ProjectRecomm;
import com.boxamazing.service.sms.model.AuthToken;
import com.boxamazing.service.sms.model.NoticeTemplate;
import com.boxamazing.service.sms.model.SendEmailRecord;
import com.boxamazing.service.sms.model.SendSmsRecord;
import com.boxamazing.service.subscribe.model.Subscribe;
import com.boxamazing.service.system_message.model.SystemMessage;
import com.boxamazing.service.user.model.ReceiveAddress;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.user.model.UserFeedBack;
import com.boxamazing.service.user.model.UserLettleContent;
import com.boxamazing.service.user.model.UserLettleDialog;
import com.boxamazing.service.util.Constant;
import com.boxamazing.webfront.controller.IndexController;
import com.boxamazing.webfront.controller.balance_reg.Balance_regController;
import com.boxamazing.webfront.controller.code.ZipCodeController;
import com.boxamazing.webfront.controller.image.ImageController;
import com.boxamazing.webfront.controller.open_user.OpenUserForQQ;
import com.boxamazing.webfront.controller.open_user.OpenUserForWeChat;
import com.boxamazing.webfront.controller.open_user.OpenUserForWeiBo;
import com.boxamazing.webfront.controller.orders.OrdersController;
import com.boxamazing.webfront.controller.pay.AlipayController;
import com.boxamazing.webfront.controller.pay.PaymentController;
import com.boxamazing.webfront.controller.personal.PersonalController;
import com.boxamazing.webfront.controller.personal.WithdrawalsController;
import com.boxamazing.webfront.controller.project.ProductApplyController;
import com.boxamazing.webfront.controller.project.ProjectApplyController;
import com.boxamazing.webfront.controller.project.ProjectController;
import com.boxamazing.webfront.controller.project.ProjectDetailController;
import com.boxamazing.webfront.controller.project.SubscribeController;
import com.boxamazing.webfront.controller.upload.UploadController;
import com.boxamazing.webfront.controller.user.ReceiveAddressController;
import com.boxamazing.webfront.controller.user.UserCertifyController;
import com.boxamazing.webfront.controller.user.UserController;
import com.boxamazing.webfront.controller.user.UserFeedBackController;
import com.boxamazing.webfront.handler.SkipFilterHandler;
import com.boxamazing.webfront.interceptor.LogInterceptor;
import com.boxamazing.webfront.interceptor.SessionInterceptor;
import com.boxamazing.webfront.plugin.QuartzPlugin;
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
 * JFinal配置.
 * Created by pchome on 2015/7/28.
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

        AppConfig.getInstance().put("rootUrl", getProperty("rootUrl"));

        constants.setDevMode(true);
        constants.setViewType(ViewType.FREE_MARKER);

        constants.setFreeMarkerViewExtension(".ftl");
        constants.setBaseViewPath("/page");
        
        constants.setError404View("/page/common/404.html");
        constants.setError500View("/page/common/404.html");
    }

    /**
     * 配置路由
     */
    @Override
    public void configRoute(Routes routes) {

        /* 首页 */
        routes.add("/", IndexController.class);
       /* 用户登录页 */
        // routes.add("/login", LoginController.class);
        routes.add("/user", UserController.class);
        /* 产品介绍 */
        routes.add("/project", ProjectController.class);
        /*众筹方式*/
        routes.add("/productapply", ProductApplyController.class);
        /*随机验证图片码*/
        routes.add("/image", ImageController.class);
        /* 订阅-关注*/
        routes.add("/sub", SubscribeController.class);
        /* 项目详情(介绍,进度,评论,支持名单) */
        routes.add("/projectDetail", ProjectDetailController.class);
        /* 个人中心 */
        routes.add("/personal", PersonalController.class);
        /* 账户信息 */
        routes.add("/balance_reg", Balance_regController.class);
        /*初审*/
        routes.add("/projectapply", ProjectApplyController.class);
        /*上传文件*/
        routes.add("/upload", UploadController.class);
        /*处理数据上传支付宝接口*/
        routes.add("/payment", PaymentController.class);
        /*我的订单管理*/
        routes.add("/orders", OrdersController.class);
        /*用户实名认证*/
        routes.add("/usercertify", UserCertifyController.class);
        /*第三方登录qq*/
        routes.add("/OpenUserForQQ",OpenUserForQQ.class);
        /*第三方登录weibo*/
        routes.add("/OpenUserForWeiBo",OpenUserForWeiBo.class);
        /*第三方登录微信*/
        routes.add("/OpenUserForWeChat",OpenUserForWeChat.class);
        /*编码处理*/
        routes.add("/zipcode",ZipCodeController.class);
        /*收货地址*/
        routes.add("/receiveAddress",ReceiveAddressController.class);
        /*支付回调*/
        routes.add("/alipay",AlipayController.class);
        /*用户反馈*/
        routes.add("/userFeedBack",UserFeedBackController.class);
        /*用户提现*/
        routes.add("/withdrawals", WithdrawalsController.class);
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
        c3p0Plugin.setMaxPoolSize(50);
        c3p0Plugin.setMinPoolSize(10);
        plugins.add(c3p0Plugin);

        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        plugins.add(arp);

        arp.setShowSql(true);
        arp.addMapping("user", "id", User.class);
        arp.addMapping("project_category", ProjectCategory.class);
        arp.addMapping("project", "id", Project.class);
        arp.addMapping("config", "key", Config.class);
        arp.addMapping("product", Product.class);
        arp.addMapping("subscribe", Subscribe.class);
        arp.addMapping("project_progress", ProjectProgress.class);
        arp.addMapping("comment", Comment.class);
        arp.addMapping("comment_support", Comment_support.class);
        arp.addMapping("balance_reg", Balance_reg.class);
        arp.addMapping("orders", Orders.class);
        arp.addMapping("user_pay_pwd", UserPayPwd.class);
        arp.addMapping("recharge_detail_pre", RechargeDetailPre.class);
        arp.addMapping("recharge_detail_record", RechargeDetailRecord.class);
        arp.addMapping("withdrawals", Withdrawals.class);
        arp.addMapping("send_sms_record", SendSmsRecord.class);
        arp.addMapping("send_email_record", SendEmailRecord.class);
        arp.addMapping("notice_template", NoticeTemplate.class);
        arp.addMapping("open_user", OpenUser.class);
        arp.addMapping("open_user_relation", OpenUserRelation.class);
        arp.addMapping("project_recomm", ProjectRecomm.class);
        arp.addMapping("provinces", Provinces.class);
        arp.addMapping("cities", Cities.class);
        arp.addMapping("areas", Areas.class);
        arp.addMapping("message_config", MessageConfig.class);
        arp.addMapping("private_message", PrivateMessage.class);
        arp.addMapping("order_address", OrderAddress.class);
        arp.addMapping("receive_address", ReceiveAddress.class);
        arp.addMapping("auth_token", AuthToken.class);
        arp.addMapping("project_audit_record", ProjectAuditRecord.class);
        arp.addMapping("alipay_record", AlipayRecord.class);
        arp.addMapping("fundrecord", Fundrecord.class);
        arp.addMapping("fans", Fans.class);
        arp.addMapping("delivery", Delivery.class);
        arp.addMapping("delivery_b", Delivery_b.class);
        arp.addMapping("system_message", SystemMessage.class);
        arp.addMapping("orders_refund", OrdersRefund.class);
        arp.addMapping("user_feedback", UserFeedBack.class);
        arp.addMapping("user_lettle_dialog", UserLettleDialog.class);
        arp.addMapping("user_lettle_content", UserLettleContent.class);
        
        // 缓存策略
        plugins.add(new EhCachePlugin());
        
        plugins.add(new QuartzPlugin());
    }

    /**
     * 配置全局拦截器
     * @param interceptors
     */
    @Override
    public void configInterceptor(Interceptors interceptors) {
        interceptors.add(new LogInterceptor());
    	// session拦截器
    	interceptors.add(new SessionInterceptor());
    }

    /**
     * 配置处理器.
     *
     * @param handlers
     */
    @Override
    public void configHandler(Handlers handlers) {
    	handlers.add(new SkipFilterHandler());
    }

    @Override
    public void afterJFinalStart() {
    	System.out.println(" - 系统已启动 -  初始化常量 - "+new Date());
		Constant.STATIC_URL = getProperty("static_url");
		Constant.WEBROOT_URL = getProperty("WEBROOT_URL");
		
		/*--------支付宝接口配置----------*/
		AlipayConfig.seller_email = getProperty("alipay_seller_email");
		AlipayConfig.partner = getProperty("alipay_PID");
		AlipayConfig.key = getProperty("alipay_Key");
		Constant.pay_order_notify_url = getProperty("pay_order_notify_url");
		
		/*第三方配置信息*/
		Constant.qq_lable = getProperty("qq_lable");
		Constant.weibo_lable = getProperty("weibo_lable");
		Constant.wechat_AppID = getProperty("wechat_AppID");
		Constant.wechat_redirect_URI = getProperty("wechat_redirect_URI");
    }

    /**
     * 启动JFinal Server.
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("JFinal Server started:" + new Date());
        JFinal.start("src/main/webapp", 80, "/", 5);
    }
}
