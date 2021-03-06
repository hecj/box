package com.demo.common;

import cn.peon.core.kit.StringUtil;

import com.jfinal.core.JfinalxController;

/**
 * controller基类
 */
public abstract class BaseController extends JfinalxController {
	protected static String VIEW_MSG_TYPE_ERROR="error";
	protected static String VIEW_MSG_TYPE_WARN="warn";
	protected static String VIEW_MSG_TYPE_TIP="tip";
	/**操作后返回页面提示
	 * @param type
	 * @param msg
	 */
	protected void addViewMsg(String type,String msg) {
		if (StringUtil.isEmpty(type)||StringUtil.isEmpty(msg)) {
			return;
		}
		setAttr("VIEW_MSG_TYPE", type);
		setAttr("VIEW_MSG_MSG", msg);
	}
	
	/**模块名称定义
	 * @return
	 */
	public abstract String getMn();
}


