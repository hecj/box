package com.boxamazing.service.sms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.boxamazing.service.sms.model.AuthToken;
import com.boxamazing.service.sms.model.NoticeTemplate;
import com.boxamazing.service.sms.model.SendEmailRecord;
import com.boxamazing.service.user.model.User;
import com.jfinal.kit.PropKit;

public class EmailService {
	
	private static final Log log = LogFactory.getLog(EmailService.class);
	
	@SuppressWarnings("finally")
	public static boolean sendEmail(String urlMd5,String email,int type, String action){
		String content = null;
		String template_invoke_name = null;
		//邮件模板
		NoticeTemplate  noticeTemplete = new NoticeTemplate();

		if("bind".equals(action)){
			template_invoke_name = PropKit.get("e_smtp_template_bind");
		}else if("findPwd".equals(action)){
			template_invoke_name = PropKit.get("e_smtp_template_findPwd");
		}else if("changeBind".equals(action)){
			template_invoke_name = PropKit.get("e_smtp_template_changeBind");
		}else{
			return false;
		}
		
		String urlPattern = String.valueOf(((PropKit.get("e_smtp_back_address")!=null)&&(!"".equals(PropKit.get("e_smtp_back_address"))))?PropKit.get("e_smtp_back_address").substring(7):"localhost/");
		noticeTemplete = NoticeTemplate.dao.findByTempName(template_invoke_name);
		content = noticeTemplete.getStr("content");
	
		log.info("正在发送邮件："+urlMd5+"邮箱号为："+email+"类型为:"+type+"内容为："+content);
		
		//url    
		final String url = PropKit.get("e_smtp_url");
		//用户名  
		final String apiUser =PropKit.get("e_smtp_apiUser");
		//密码  
		final String apiKey =  PropKit.get("e_smtp_apiKey");
		//收件人邮箱
		//final String rcpt_to = email;
		HttpPost httpost = new HttpPost(url);
	    HttpClient httpclient = new DefaultHttpClient();
	    
	 
	    
	    JSONArray uuids = new JSONArray();
	    uuids.put(urlMd5);
	    
	    JSONArray to = new JSONArray();
	    to.put(email);
	    
	    JSONArray name = new JSONArray();
	    name.put(email);
	    
	    JSONArray urlPatt = new JSONArray();
	    urlPatt.put(urlPattern);
	    
	    JSONObject sub = new JSONObject();
	    sub.put("%uuid%", uuids);
	    sub.put("%name%", name);
	    sub.put("%localhost%", urlPatt);
	    
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("sub", sub);
	    jsonObject.put("to", to);
	    
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("api_user", apiUser));
	    params.add(new BasicNameValuePair("api_key", apiKey));
	    params.add(new BasicNameValuePair("substitution_vars", jsonObject.toString()));
	    params.add(new BasicNameValuePair("template_invoke_name", template_invoke_name));
	    params.add(new BasicNameValuePair("from", PropKit.get("e_smtp_from")));
	    params.add(new BasicNameValuePair("subject", noticeTemplete.getStr("title").toString()));
	    params.add(new BasicNameValuePair("html", content));
	    //是否发送成功
	    int isSucc = 1;
	    try {
			httpost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			try {
				HttpResponse response = httpclient.execute(httpost);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String obj = EntityUtils.toString(response.getEntity());

					// 获取返回的json数据,解析返回值
					JSONObject json = new JSONObject(obj);
					String result = json.get("message").toString();
					
					if ("success".equals(result)) {
						//发送成功
						isSucc = 0;
						System.out.print("发送成功");
					} else {
//						发送失败
						isSucc = 1;
						System.out.print("发送失败");
					}
					
				} else {
					System.err.println("error");
					return false;
				}
			   
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				log.info(e);
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				log.info(e);
				return false;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.info(e);
			return false;
		}finally{
			Long user_id = null;
			if("bind".equals(action)){
				user_id = User.dao.findByValidEmail(email).getLong("id");
			}else if("findPwd".equals(action)){
				user_id = User.dao.findByEmail(email).getLong("id");
			}
			//创建发送记录表
			SendEmailRecord sendEmailRecord = new SendEmailRecord();
			//修改content
			content = content.replace("%name%", email).replace("%uuid%", urlMd5).replace("%localhost%", urlPattern);;
			sendEmailRecord.set("title", action)
					.set("content", content).set("sender", -1)
					.set("sender_email", PropKit.get("e_smtp_from"))
					.set("reciver", user_id)
					.set("reciver_email", email)
					.set("type", type)
					.set("id_delete", 0)
					.set("isSucc", isSucc)
					.set("create_at", System.currentTimeMillis())
					.save();
			
			//获取用户user_id
			//如果发送成功，才会存入token表
			if(isSucc==0){
				//创建发送token表
				AuthToken authToken = new AuthToken();
				authToken.set("user_id", user_id)
							.set("token",urlMd5)
							.set("type",1)
							.set("is_verify",0)
							.set("valid_at",(long)(System.currentTimeMillis()+1000 * 60 * 60 * 24))
							.set("create_at",System.currentTimeMillis())
							.save();
			}
			
			 httpost.releaseConnection();
			 return true;
		}
	}
	
	
	
	
	//发送一般通知类邮件
	public static void sendNoticeEmail(String email,String content,String action,String nickname) {
		//模板名称
		String template_invoke_name = null;
		
		if("setPwd_succ".equals(action)){
			template_invoke_name = PropKit.get("e_smtp_template_setPwd_succ");
		}else if("payPwd".equals(action)){
			template_invoke_name = PropKit.get("e_smtp_template_payPwd");
		}
		
		NoticeTemplate noticeTemplete = NoticeTemplate.dao.findByTempName(template_invoke_name);
		content = noticeTemplete.getStr("content");
		
		//url    
		final String url = PropKit.get("e_smtp_url");
		//用户名  
		final String apiUser =PropKit.get("e_smtp_apiUser");
		//密码  
		final String apiKey =  PropKit.get("e_smtp_apiKey");
		//收件人邮箱
		//final String rcpt_to = email;
		HttpPost httpost = new HttpPost(url);
	    HttpClient httpclient = new DefaultHttpClient();
		
	    JSONArray to = new JSONArray();
	    to.put(email);
	    
	    JSONArray name = new JSONArray();
	    name.put(nickname);
		
	    JSONObject sub = new JSONObject();
	    sub.put("%name%", name);
		
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("sub", sub);
	    jsonObject.put("to", to);
	    
	    //创建发送信息
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("api_user", apiUser));
	    params.add(new BasicNameValuePair("api_key", apiKey));
	    params.add(new BasicNameValuePair("substitution_vars", jsonObject.toString()));
	    params.add(new BasicNameValuePair("template_invoke_name", template_invoke_name));
	    params.add(new BasicNameValuePair("from", PropKit.get("e_smtp_from")));
	    params.add(new BasicNameValuePair("subject", noticeTemplete.getStr("title").toString()));
	    params.add(new BasicNameValuePair("html", content));
	    
	    try {
			httpost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			try {
				HttpResponse response = httpclient.execute(httpost);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String obj = EntityUtils.toString(response.getEntity());

					// 获取返回的json数据,解析返回值
					JSONObject json = new JSONObject(obj);
					String result = json.get("message").toString();
					
					if ("success".equals(result)) {
						log.info("发送成功......");
					} else {
						log.info("发送失败！！！！");
					}
					
				} else {
					log.info("系统服务器异常,请立即检查！！！！");
				}
			   
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				log.info(e+"系统服务器异常");
				
			} catch (IOException e) {
				
				e.printStackTrace();
				log.info(e+"系统服务器异常");
			}
		} catch (UnsupportedEncodingException e1) {
			log.info(e1+"系统服务器异常");
			e1.printStackTrace();
		} finally{
			httpost.releaseConnection();
		}
		
	}
}
