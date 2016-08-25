package com.jfinal.weixin.sdk.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * CodeAPI.
 * Created by pchome on 2015/8/3.
 */
public class CodeApi {
    private static final String URL_SAMPLE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={appid}&redirect_uri={redirect_uri}&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\n";

    /**
     * 生成授权URL.
     *
     * @param appid
     * @param redirectUrl
     * @return
     */
    public static String getCode(String appid, String redirectUrl) {
        String url = URL_SAMPLE.replace("{appid}", appid);
        try {
            url = url.replace("{redirect_uri}", URLEncoder.encode(redirectUrl, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(CodeApi.getCode("wxa0ae3fb796c22120", "http://wc.duomeidai.com/api"));
    }
}
