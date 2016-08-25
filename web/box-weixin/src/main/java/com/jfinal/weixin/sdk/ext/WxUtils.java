package com.jfinal.weixin.sdk.ext;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.AccessTokenApi;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.kit.ParaMap;

public class WxUtils {
	   
	private static final Log log = LogFactory.getLog(WxUtils.class);
	
 
    private static String withOutPrivilege(String response2) {
    	response2=response2.replace("[", "\"");
    	response2=response2.replace("]", "\"");
		return response2;
	}
    
	  

	public static long prevAccessTokenFlushTime = 0;// 上次刷新时间
	public static String prevAccessToken = null;// 上次请求的AccessToken
	 
     
	
//	private static String apiUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=";

	public static ApiResult getTicket() {
		String apiUrl = "http://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="+AccessTokenApi.getAccessToken().getAccessToken();
//		ParaMap pm = ParaMap.create("access_token", AccessTokenApi.getAccessToken().getAccessToken()).put("type", "jsapi");
		return new ApiResult(HttpKit.get(apiUrl));//, pm.getData()));
	}
	
	
	public static void main(String[] args) {
//		WxConstantConfig.appid="wxe27e1431f2b1fa4e";
//		WxConstantConfig.appsecret="84ad9e7876457a31b5a6eb00de52f713";
//		WxConstantConfig.token="duomeidai";
//		String url="http://www.duomeidai.com";
//		Map<String, String> si = getSign(url);
//		log.info(si);
	}
	
	public static Map<String, String> getSign(String url) {
		ApiResult synJsapiTicket = getSynJsapiTicket();
		String jsapi_ticket=synJsapiTicket.get("ticket");
		Map<String, String> si = WxSignUtil.sign(jsapi_ticket, url);
		log.info("si:"+si);
		return si;
	}
	
	
	private static long prevJsapiTicketFlushTime=0;//上次刷新时间
	
	private static ApiResult jsapiTicketData = null;
	/**
	 * jsapi_ticket（有效期7200秒，开发者必须在自己的服务全局缓存jsapi_ticket）
	 */
	public static ApiResult getSynJsapiTicket(){
		
		long currentTimeMillis = System.currentTimeMillis();
		if (currentTimeMillis - prevJsapiTicketFlushTime > 1*60*60*1000) {//如果以超过1个小时未刷新 AccessToken
			log.info("刷新之前：jsapiTicketData："+jsapiTicketData);
			log.info("刷新jsapi_ticket：prevJsapiTicketFlushTime："+prevJsapiTicketFlushTime+",currentTimeMillis:"+currentTimeMillis);
			prevJsapiTicketFlushTime=currentTimeMillis;
			jsapiTicketData = getTicket();
			log.info("刷新之后：jsapiTicketData："+jsapiTicketData);
		}
		return jsapiTicketData;
	}
	
	
	
	 public static ApiConfig getApiConfig() {

        ApiConfig ac = new ApiConfig();

        // 配置微信 API 相关常量
        ac.setToken(PropKit.get("token"));
        ac.setAppId(PropKit.get("appId"));
        ac.setAppSecret(PropKit.get("appSecret"));

        ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
        ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
        return ac;
    }
}
