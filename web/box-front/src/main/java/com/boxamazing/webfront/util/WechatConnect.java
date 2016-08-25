package com.boxamazing.webfront.util;

import java.net.URLEncoder;
import java.util.Properties;

import com.qq.connect.QQConnectException;
import com.qq.connect.utils.http.HttpClient;
import com.qq.connect.utils.http.PostParameter;
import com.qq.connect.utils.json.JSONException;
import com.qq.connect.utils.json.JSONObject;


public class WechatConnect {
	private static Properties props;
	private HttpClient client = new HttpClient();
	private String accessToken = "";
	private String expiresIn = "";
	private String refreshToken = "";
	private String openId = "";
	private String scope = "";
	private String unionid = "";
	
	private String nickname = "";
	private String sex = "";
	private String province = "";
	private String city = "";
	private String country = "";
	private String headimgurl = "";
	private String privilege = "";
	
	static {
		props = new Properties();
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("wechatconnectconfig.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 请求获取code
	 * @author XuXD
	 * @date 2015-10-21
	 * @return
	 * @throws Exception
	 */
	public String getAuthorizeURL() throws Exception {
		return (new StringBuilder().append(props.getProperty("authorizeURL").trim()).append("?appid=")
				.append(props.getProperty("AppID").trim()).append("&redirect_uri=")
				.append(URLEncoder.encode(props.getProperty("redirect_URI"), "UTF-8")).append("&response_type=").append("code")
				.append("&scope=").append(props.getProperty("scope").trim()).toString());
	}

	/**
	 * 根据code获取accesstoken
	 * @author XuXD
	 * @date 2015-10-21
	 * @param code
	 * @return
	 * @throws QQConnectException
	 * @throws JSONException
	 */
	public String getAccessToken(String code) throws QQConnectException, JSONException {
		String accessTokenURL = props.getProperty("accessTokenURL").trim();
		PostParameter[] parameter = new PostParameter[] {new PostParameter("appid", props.getProperty("AppID").trim()),new PostParameter("secret", props.getProperty("AppSecret").trim()),new PostParameter("code", code),new PostParameter("grant_type", "authorization_code") };
		JSONObject json = client.post(accessTokenURL,parameter,Boolean.valueOf(false)).asJSONObject();
		
		accessToken = json.getString("access_token");
		expiresIn = json.getString("expires_in");
		refreshToken = json.getString("refresh_token");
		openId = json.getString("openid");
		scope = json.getString("scope");
		unionid = json.getString("unionid");
		
		return accessToken;
	}
	
	/**
	 * 获取用户信息
	 * @author XuXD
	 * @date 2015-10-21
	 * @return
	 * @throws QQConnectException
	 * @throws JSONException
	 */
	public Boolean getUserInfo() throws QQConnectException, JSONException{
		String getUserInfoURL = props.getProperty("getUserInfoURL").trim();
		PostParameter[] parameter = new PostParameter[] {new PostParameter("access_token", accessToken),new PostParameter("openid", openId)};
		JSONObject json = client.post(getUserInfoURL,parameter).asJSONObject();
		
		nickname = json.getString("nickname");
		sex = json.getString("sex");
		province = json.getString("province");
		city = json.getString("city");
		country = json.getString("country");
		headimgurl = json.getString("headimgurl");
		privilege = json.getString("privilege");
		
		return true;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getOpenId() {
		return openId;
	}

	public String getScope() {
		return scope;
	}

	public String getUnionid() {
		return unionid;
	}

	public String getNickname() {
		return nickname;
	}

	public String getSex() {
		return sex;
	}

	public String getProvince() {
		return province;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public String getPrivilege() {
		return privilege;
	}
	
}
