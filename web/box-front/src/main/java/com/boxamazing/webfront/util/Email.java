package com.boxamazing.webfront.util;

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
import org.json.JSONStringer;


import com.boxamazing.service.sms.model.AuthToken;
import com.boxamazing.service.sms.model.SendEmailRecord;
import com.boxamazing.service.user.model.User;
import com.jfinal.kit.PropKit;
import com.oreilly.servlet.MailMessage;


/**
 * 发送短信 type 0 -- 找回密码    1  日常邮件
 * @author pc
 *
 */
public class Email  {
	
	private static final Log log = LogFactory.getLog(Email.class);
	
	@SuppressWarnings("finally")
	public static boolean sendEmail(String urlMd5,String email,int type){
		
		String content = "您好，您正在使用邮箱找回密码，请点击以下链接 <a href='http://localhost/verifyEmailTrue?uuid=%uuid%'>http://localhost/verifyEmailTrue?uuid=%uuid%</a>如果点击未响应，请复制链接至地址栏即可";
		if(0==type){
			log.info("正在发送找回密码邮件："+urlMd5+"邮箱号为："+email+"类型为:"+type);
		}else{
			log.info("正在发送普通邮件："+urlMd5+"邮箱号为："+email+"类型为:"+type);
		}
		
		//url    
		final String url = PropKit.get("e_smtp_url");
		//用户名  
		final String apiUser =PropKit.get("e_smtp_apiUser");
		//密码  
		final String apiKey =  PropKit.get("e_smtp_apiKey");
		//收件人邮箱
		final String rcpt_to = email;
		
		
		HttpPost httpost = new HttpPost(url);
	    HttpClient httpclient = new DefaultHttpClient();
	    
	 
	    
	    JSONArray uuids = new JSONArray();
	    uuids.put(urlMd5);
	    
	    JSONArray to = new JSONArray();
	    to.put(email);
	    
	    JSONArray name = new JSONArray();
	    name.put(email);
	    
	    JSONObject sub = new JSONObject();
	    sub.put("%uuid%", uuids);
	    sub.put("%name%", name);
	    
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("sub", sub);
	    jsonObject.put("to", to);
	    
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("api_user", apiUser));
	    params.add(new BasicNameValuePair("api_key", apiKey));
	    params.add(new BasicNameValuePair("substitution_vars", jsonObject.toString()));
	    params.add(new BasicNameValuePair("template_invoke_name", "test_temple_findPwd"));
	    params.add(new BasicNameValuePair("from", PropKit.get("e_smtp_from")));
	    params.add(new BasicNameValuePair("subject", "邮件找回密码"));
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
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.info(e);
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.info(e);
				return false;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info(e);
			return false;
		}finally{
			//创建发送记录表
			SendEmailRecord sendEmailRecord = new SendEmailRecord();
			sendEmailRecord.set("title", PropKit.get("e_smtp_subject"))
					.set("content", content).set("sender", -1)
					.set("sender_email", PropKit.get("e_smtp_from"))
					.set("reciver", 1)
					.set("reciver_email", email)
					.set("type", type)
					.set("id_delete", 0)
					.set("isSucc", isSucc)
					.set("create_at", System.currentTimeMillis())
					.save();
			
			//获取用户user_id
			//如果发送成功，才会存入token表
			if(isSucc==0){
				Long user_id = User.dao.findByEmail(email).getLong("id");
				//创建发送token表
				AuthToken authToken = new AuthToken();
				authToken.set("user_id", user_id)
							.set("token",urlMd5)
							.set("type",1)
							.set("is_verify",0)
							.set("valid_at",(long)(System.currentTimeMillis()+1000 * 60 * 60 * 48))
							.set("create_at",System.currentTimeMillis())
							.save();
			}
			
			 httpost.releaseConnection();
			 return true;
		}
	}
	
	
	public static void send_template() throws ClientProtocolException, IOException {

	    final String url = "http://sendcloud.sohu.com/webapi/mail.send_template.json";

    	//url
  		//final String url = PropKit.get("e_smtp_url");
  		//用户名
  		final String apiUser = "postmaster@duomei.sendcloud.org" ;
  		//密码
  		final String apiKey = "uOvfw3t5de6BpYzD";	

//	    List<A> dataList = new ArrayList<A>();
//	    dataList.add(new A("to1@domain.com", "user1", "1000"));
//	    dataList.add(new A("to2@domain.com", "user2", "2000"));
//
//	    final String vars = convert(dataList);
//  		List dataList = new ArrayList();
//  		dataList.add(0, "603349448@qq.com");
//  		final String vars = convert(dataList);
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httpost = new HttpPost(url);

	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("api_user", apiUser));
	    params.add(new BasicNameValuePair("api_key", apiKey));
	    params.add(new BasicNameValuePair("substitution_vars", "603349448@qq.com"));
	    params.add(new BasicNameValuePair("template_invoke_name", "test_template"));
	    params.add(new BasicNameValuePair("from", "sendcloud@sendcloud.org"));
	    params.add(new BasicNameValuePair("fromname", "SendCloud"));
	    params.add(new BasicNameValuePair("subject", "SendCloud java template"));
	    params.add(new BasicNameValuePair("html", "欢迎使用SendCloud"));
	    params.add(new BasicNameValuePair("resp_email_id", "true"));

	    httpost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

	    HttpResponse response = httpclient.execute(httpost);

	    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
	        System.out.println(EntityUtils.toString(response.getEntity()));
	    } else {
	        System.err.println("error");
	    }

	    httpost.releaseConnection();
	}
	
	
}
