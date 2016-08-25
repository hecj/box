package com.jfinal.weixin.demo;

import java.util.Map;

import com.boxamazing.weixin.interceptor.WxApiConfigInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.CallbackIpApi;
import com.jfinal.weixin.sdk.api.CustomServiceApi;
import com.jfinal.weixin.sdk.api.MenuApi;
import com.jfinal.weixin.sdk.api.QrcodeApi;
import com.jfinal.weixin.sdk.api.ShorturlApi;
import com.jfinal.weixin.sdk.api.TemplateMsgApi;
import com.jfinal.weixin.sdk.api.UserApi;
import com.jfinal.weixin.sdk.ext.WxUtils;
import com.jfinal.weixin.sdk.jfinal.ApiController;

@ClearInterceptor(ClearLayer.ALL)
public class WeixinApiController extends ApiController {
	
	/**
	 * 如果要支持多公众账号，只需要在此返回各个公众号对应的  ApiConfig 对象即可
	 * 可以通过在请求 url 中挂参数来动态从数据库中获取 ApiConfig 属性值
	 */
	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();
		
		// 配置微信 API 相关常量
		ac.setToken(PropKit.get("token"));
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));
		
		/**
		 *  是否对消息进行加密，对应于微信平台的消息加解密方式：
		 *  1：true进行加密且必须配置 encodingAesKey
		 *  2：false采用明文模式，同时也支持混合模式
		 */
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
		return ac;
	}

	/**
	 * 获取公众号菜单
	 */
	public void getMenu() {
		ApiConfigKit.setThreadLocalApiConfig(getApiConfig());
		ApiResult apiResult = MenuApi.getMenu();
		if (apiResult.isSucceed())
			renderText(apiResult.getJson());
		else
			renderText(apiResult.getErrorMsg());
	}

	/**
	 * 创建菜单
	 */
	public void createMenu()
	{
		ApiConfigKit.setThreadLocalApiConfig(getApiConfig());
		String str = getPara("json");
		if(str== null ||  str.length() > 0){
			str = "{\"button\":[ \n"
					// click　点击事件
					+ "{\"type\":\"click\",\"name\":\"点击推事件\",\"key\":\"10000\"},\n"
					// view 跳转URL
					+ "{\"type\":\"view\",\"name\":\"跳转URL\",\"url\":\"http://114.66.196.204:10002\"},\n"
					/**
					//scancode_push：扫码推事件
					+ "{\"type\":\"scancode_push\",\"name\":\"扫码推事件\",\"key\":\"rselfmenu_0_1\"},\n"
					//scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框
					+ "{\"type\":\"scancode_waitmsg\",\"name\":\"扫码推事件\",\"key\":\"rselfmenu_0_2\"},\n"
                    //pic_sysphoto：弹出系统拍照发图
					+ "{\"type\":\"pic_sysphoto\",\"name\":\"拍照\",\"key\":\"rselfmenu_0_4\"},\n"
					//pic_photo_or_album：弹出拍照或者相册发图
					+ "{\"type\":\"pic_photo_or_album\",\"name\":\"拍照相册\",\"key\":\"rselfmenu_1_1\"},\n"
					//location_select：弹出地理位置选择器
					+ "{\"type\":\"location_select\",\"name\":\"地理位置\",\"key\":\"reselfmenu_2_0\"},\n"
					//pic_weixin：弹出微信相册发图器
					+ "{\"type\":\"pic_weixin\",\"name\":\"相册发图\",\"key\":\"rselfmenu_2_1\"},\n"
					//media_id：下发消息（除文本消息）
					+ "{\"type\":\"media_id\",\"name\":\"下发消息\",\"url\":\"http://114.66.196.204:10002\"},\n"
					 */

					+ "{\"type\":\"view\",\"name\":\"绑定账号\",\"url\":\"http://peon.cn/api/\"} \n"
					+ "]\n" +
					"}";
			System.out.println(str);
		}
		ApiResult apiResult = MenuApi.createMenu(str);
		if (apiResult.isSucceed())
			renderText(apiResult.getJson());
		else
			renderText(apiResult.getErrorMsg());
	}

	/**
	 * 获取公众号关注用户
	 */
	public void getFollowers()
	{
		ApiResult apiResult = UserApi.getFollows();
		renderText(apiResult.getJson());
	}

	/**
	 * 获取用户信息
	 */
	public void getUserInfo()
	{
		ApiResult apiResult = UserApi.getUserInfo("ohbweuNYB_heu_buiBWZtwgi4xzU");
		renderText(apiResult.getJson());
	}

	/**
	 * 发送模板消息
	 */
	public void sendMsg()
	{
		String str = " {\n" +
				"           \"touser\":\"ohbweuNYB_heu_buiBWZtwgi4xzU\",\n" +
				"           \"template_id\":\"9SIa8ph1403NEM3qk3z9-go-p4kBMeh-HGepQZVdA7w\",\n" +
				"           \"url\":\"http://www.sina.com\",\n" +
				"           \"topcolor\":\"#FF0000\",\n" +
				"           \"data\":{\n" +
				"                   \"first\": {\n" +
				"                       \"value\":\"恭喜你购买成功！\",\n" +
				"                       \"color\":\"#173177\"\n" +
				"                   },\n" +
				"                   \"keyword1\":{\n" +
				"                       \"value\":\"去哪儿网发的酒店红包（1个）\",\n" +
				"                       \"color\":\"#173177\"\n" +
				"                   },\n" +
				"                   \"keyword2\":{\n" +
				"                       \"value\":\"1元\",\n" +
				"                       \"color\":\"#173177\"\n" +
				"                   },\n" +
				"                   \"remark\":{\n" +
				"                       \"value\":\"欢迎再次购买！\",\n" +
				"                       \"color\":\"#173177\"\n" +
				"                   }\n" +
				"           }\n" +
				"       }";
		ApiResult apiResult = TemplateMsgApi.send(str);
		renderText(apiResult.getJson());
	}

	/**
	 * 获取参数二维码
	 */
	public void getQrcode()
	{
		String str = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
		ApiResult apiResult = QrcodeApi.create(str);
		renderText(apiResult.getJson());

//        String str = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"123\"}}}";
//        ApiResult apiResult = QrcodeApi.create(str);
//        renderText(apiResult.getJson());
	}

	/**
	 * 长链接转成短链接
	 */
	public void getShorturl()
	{
		String str = "{\"action\":\"long2short\"," +
				"\"long_url\":\"http://wap.koudaitong.com/v2/showcase/goods?alias=128wi9shh&spm=h56083&redirect_count=1\"}";
		ApiResult apiResult = ShorturlApi.getShorturl(str);
		renderText(apiResult.getJson());
	}

	/**
	 * 获取客服聊天记录
	 */
	public void getRecord()
	{
		String str = "{\n" +
				"    \"endtime\" : 987654321,\n" +
				"    \"pageindex\" : 1,\n" +
				"    \"pagesize\" : 10,\n" +
				"    \"starttime\" : 123456789\n" +
				" }";
		ApiResult apiResult = CustomServiceApi.getRecord(str);
		renderText(apiResult.getJson());
	}

	/**
	 * 获取微信服务器IP地址
	 */
	public void getCallbackIp()
	{
		ApiResult apiResult = CallbackIpApi.getCallbackIp();
		renderText(apiResult.getJson());
	}
	
	
	@Before(WxApiConfigInterceptor.class)
	public void getSign(){
		String oldurl=getPara("url");
		String durl=oldurl.split("[\\#]")[0].replaceAll("(index.jsp)$", "");//.replaceAll("[\\/]$", "");
		  System.out.print("durl:"+durl);  
		Map<String,String> m=WxUtils.getSign(durl); 
//		renderJson(m);
		
		String javascriptText="var wx_timestamp='"+m.get("timestamp")+"';";
		  javascriptText+="var wx_nonceStr='"+m.get("nonceStr")+"';";
		  javascriptText+="var wx_jsapi_ticket='"+m.get("jsapi_ticket")+"';";
		  javascriptText+="var wx_url='"+m.get("url")+"';";
		  javascriptText+="var wx_signature='"+m.get("signature")+"';";
		  javascriptText+="var wx_share_appid='"+PropKit.get("appId")+"';";
		  
		renderJavascript(javascriptText);
//		var wx_nonceStr="<%=m.get("nonceStr")%>";
//		var wx_jsapi_ticket="<%=m.get("jsapi_ticket")%>";
//		var wx_url="<%=m.get("url")%>";
//		var wx_signature="<%=m.get("signature")%>";
//		var durl="<%=durl%>";
//		var oldurl="<%=oldurl%>";
	}
}

