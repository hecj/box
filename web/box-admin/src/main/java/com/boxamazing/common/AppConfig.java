package com.boxamazing.common;

import java.util.HashMap;
import java.util.Map;

/**
 * AppConfig. 保存app.conf内容
 * Created by jhl on 15/8/29.
 */
public class AppConfig {
    public static Map<String, String> appConf = new HashMap<String, String>();
    public static AppConfig appConfig = new AppConfig();

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        return appConfig;
    }

    public String get(String key) {
        return appConf.get(key);
    }

    public void put(String key, String value) {
        appConf.put(key, value);
    }

    public Integer getInt(String key) {
        return Integer.parseInt(appConf.get(key));
    }

    public Long getLong(String key) {
        return Long.parseLong(appConf.get(key));
    }
}
