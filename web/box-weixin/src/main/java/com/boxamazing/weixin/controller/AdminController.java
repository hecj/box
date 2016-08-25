package com.boxamazing.weixin.controller;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.JfinalxController;

/**
 * 微信管理界面.
 * Created by pchome on 2015/8/3.
 */
@ClearInterceptor(ClearLayer.ALL)
public class AdminController extends JfinalxController {
    public void index() {
        render("index.ftl");
    }
}
