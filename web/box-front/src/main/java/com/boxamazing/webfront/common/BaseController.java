package com.boxamazing.webfront.common;

import javax.servlet.http.HttpServletRequest;

import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.user.model.User;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.core.JfinalxController;

/**
 * Controller 基类. Created by pchome on 2015/7/28.
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
	 * 刷新用户session数据
	 */
	public void flushSessionUser(Long user_id, HttpServletRequest request) throws Exception {
		User u = User.dao.findById(user_id);
		UserUtil.setUser(u, request.getSession());
	}

	/**
	 * 获取来源IP地址
	 */
	public static String getRemortIp(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
	
	public String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}

	/**
	 * 获取参数:int
	 * 
	 * @author XuXD
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public int getParam(String name, int defaultValue) {
		try {
			return getParaToInt(name, defaultValue);
		} catch (Exception e) {
			System.out.println("参数类型转换异常:[name:" + name + ",defaultValue:" + defaultValue + "]");
			return defaultValue;
		}
	}

	/**
	 * 获取参数:int
	 * 
	 * @author XuXD
	 * @param index
	 * @param defaultValue
	 * @return
	 */
	public int getParam(int index, int defaultValue) {
		try {
			return getParaToInt(index, defaultValue);
		} catch (Exception e) {
			System.out.println("参数类型转换异常:[index:" + index + ",defaultValue:" + defaultValue + "]");
			return defaultValue;
		}
	}

	/**
	 * 获取参数:long
	 * 
	 * @author XuXD
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public long getParam(String name, long defaultValue) {
		try {
			return getParaToLong(name, defaultValue);
		} catch (Exception e) {
			System.out.println("参数类型转换异常:[name:" + name + ",defaultValue:" + defaultValue + "]");
			return defaultValue;
		}
	}

	/**
	 * 获取参数:long
	 * 
	 * @author XuXD
	 * @param index
	 * @param defaultValue
	 * @return
	 */
	public long getParam(int index, long defaultValue) {
		try {
			return getParaToLong(index, defaultValue);
		} catch (Exception e) {
			System.out.println("参数类型转换异常:[index:" + index + ",defaultValue:" + defaultValue + "]");
			return defaultValue;
		}
	}
	
	/**
	 * 获取参数:String
	 * @author XuXD
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public String getParam(String name, String defaultValue) {
		try {
			return getPara(name, defaultValue);
		} catch (Exception e) {
			System.out.println("参数类型转换异常:[name:" + name + ",defaultValue:" + defaultValue + "]");
			return defaultValue;
		}
	}
	
	/**
	 * 获取参数:String
	 * @author XuXD
	 * @param index
	 * @param defaultValue
	 * @return
	 */
	public String getParam(int index, String defaultValue) {
		try {
			return getPara(index, defaultValue);
		} catch (Exception e) {
			System.out.println("参数类型转换异常:[index:" + index + ",defaultValue:" + defaultValue + "]");
			return defaultValue;
		}
	}
	
	/**
     * 获取basePath路径
     */
    public String getBasePath(HttpServletRequest request){
    	String path = request.getContextPath();
    	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		return basePath;
    }

}
