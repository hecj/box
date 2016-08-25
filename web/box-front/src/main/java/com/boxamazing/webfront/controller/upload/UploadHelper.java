package com.boxamazing.webfront.controller.upload;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Upload帮助类
 * Created by jhl on 15/8/20.
 */
public class UploadHelper {

    /**
     * 生成随机图片名儿
     *
     * @param uid
     * @return
     */
    public static String genRandomPicName(String uid) {
        return genRandomName(uid, ".jpg");
    }

    /**
     * 生成随机图片名儿
     *
     * @param uid
     * @return
     */
    public static String genRandomName(String uid, String suffix) {
        return DigestUtils.sha1Hex(System.currentTimeMillis() + uid) + suffix;
    }

    /**
     * 通过文件名获取后缀
     *
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        String suffix;
        fileName = fileName.replaceAll("/", "");
        String[] tmp = fileName.split("\\.");
        if (tmp.length == 0) {
            suffix = "";
        } else {
            suffix = "." + tmp[tmp.length - 1];
        }
        return suffix;
    }
}
