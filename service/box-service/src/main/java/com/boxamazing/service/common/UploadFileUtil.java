package com.boxamazing.service.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 上传文件工具类
 * Created by jhl on 15/8/18.
 */
public class UploadFileUtil {

    public static String getUploadPath(String uid) {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy/MM/dd");
        String datePath = dataFormat.format(new Date());
        return datePath + "/" + uid + "/";
    }
}
