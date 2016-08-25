package com.jfinal.weixin.sdk.jfinal;

import com.jfinal.aop.Before;
import com.jfinal.core.JfinalxController;
import com.jfinal.weixin.sdk.api.ApiConfig;

/**
 * 所有使用 Api 的 controller 需要继承此类
 */
public abstract class ApiController extends JfinalxController {
	public abstract ApiConfig getApiConfig();
}
