package com.boxamazing.webfront.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boxamazing.service.user.model.User;

public class CookieUtil {
	//保存cookie时的cookieName
	private final static String cookieDomainName = "hezi";
	//加密cookie时自定义码
	private final static String webKey = "123456";
	//设置最大失效时间  7天
	private final static long cookieMaxAge = 60 * 60 * 24 * 2 ;
	
	/**
	 * 
	 * 保存cookie
	 * @param user
	 */
	public static List<String> saveCookie(HttpServletRequest request,HttpServletResponse response,User user){
		
		
		//cookie的有效期
		long validTime = System.currentTimeMillis() + (cookieMaxAge * 5000);
		//MD5加密用户详细信息
		String cookieValueWithMd5 =MD5.getOneMd5(user.getStr("phone"),user.getStr("password"),validTime,webKey,"utf-8");
		//将要被保存的完整的Cookie值
		String cookieValue = user.getStr("phone") + ":" + validTime + ":" + cookieValueWithMd5;
		List<String> list = new ArrayList<String>();
		list.add(cookieDomainName);	
		list.add(cookieValue);
		list.add(cookieMaxAge+"");
		return list;
	}
	
	 public static User readCookieAndLogin(HttpServletRequest request,HttpServletResponse response){
		 Cookie cookies[] = request.getCookies();
		 String cookieValue = null;
		 
		 if(cookies!=null){
            for(int i=0;i<cookies.length;i++){
               if (cookieDomainName.equals(cookies[i].getName())) {
                  cookieValue = cookies[i].getValue();
                  break;
               }
            }
		  }
		 //如果cookieValue为空,返回,
		 if(cookieValue==null){
		 	return null;
		 }
		 
		 String cookieValues[] = cookieValue.split(":");
		 //非法方式进入网站
		 if(cookieValues.length!=3){
//             response.setContentType("text/html;charset=utf-8");
//             PrintWriter out = response.getWriter();
//             out.println("你正在用非正常方式进入本站...");
//             out.close();
             return null;
		 }
		 
		//判断是否在有效期内,过期就删除Cookie
		 long validTimeInCookie = new Long(cookieValues[1]); 
		 if(validTimeInCookie < System.currentTimeMillis()){
			 //cookie失效
			 return null;
		 }
		 
		//取出cookie中的用户名,并到数据库中检查这个用户名,
		 String phone = cookieValues[0];
		 //查询数据库中是否还有该信息
		 
		 User user = User.dao.findUser(phone);
		 //如果user返回不为空,就取出密码,使用用户名+密码+有效时间+ webSiteKey进行MD5加密
		 if(user!=null){
			 String md5ValueInCookie = cookieValues[2];
			 String md5ValueFromUser = MD5.getOneMd5(user.getStr("phone"), user.getStr("password"), validTimeInCookie, webKey, "utf-8");
			 if(md5ValueInCookie.equals(md5ValueFromUser)){
				 //TODO 
				 return user;
			 }
		 }else{
			 //返回null
			 return null;
		 }
		return user;
	 }
	 
	 public static void removeCookie(HttpServletRequest request,HttpServletResponse response){
		 Cookie cookies[] = request.getCookies();
		
		 if(cookies!=null){
            for(int i=0;i<cookies.length;i++){
               if (cookieDomainName.equals(cookies[i].getName())) {
            	   cookies[i].setValue(null);
                  break;
               }
            }
		  }
	 }
}