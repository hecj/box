package com.boxamazing.common;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.core.JfinalxController;

/**
 * controller基类
 */
public abstract class BaseController extends JfinalxController {
	protected static String VIEW_MSG_TYPE_ERROR = "error";
	protected static String VIEW_MSG_TYPE_WARN = "warn";
	protected static String VIEW_MSG_TYPE_TIP = "tip";

	/**
	 * 操作后返回页面提示
	 * 
	 * @param type
	 * @param msg
	 */
	protected void addViewMsg(String type, String msg) {
		if (StringUtil.isEmpty(type) || StringUtil.isEmpty(msg)) {
			return;
		}
		setAttr("VIEW_MSG_TYPE", type);
		setAttr("VIEW_MSG_MSG", msg);
	}

	/**
	 * 模块名称定义
	 * 
	 * @return
	 */
	public String getMn(){
		return null;
	};
	
	/**
	 * 编码获取参数
	 */
	public String getParam(HttpServletRequest request,String key) {
		try{
			return new String(request.getParameter(key).getBytes("ISO-8859-1"),"UTF-8");
		}catch(UnsupportedEncodingException ex){
			ex.printStackTrace();
		}
		return "";
	}
	
}
