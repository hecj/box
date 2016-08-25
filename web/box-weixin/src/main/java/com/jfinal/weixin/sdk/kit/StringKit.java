package com.jfinal.weixin.sdk.kit;

/**
 * StringKit.
 * Created by pchome on 2015/8/4.
 */
public class StringKit {

    /**
     * 检测字符串是否为空.
     * @param string
     * @return
     */
    public static boolean isEmpty(String string){
        if(null == string || string.length() <= 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String string){
        if(null != string && string.length() > 0){
            return true;
        }
        return false;
    }

    public static  boolean isEmpty(String ... strings) {
        for(String s : strings){
            if(isEmpty(s)) return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String ... strings){
        for(String s : strings){
            if(isEmpty(s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllEmpty(String... strings){
        for(String string : strings) {
            if(!isEmpty(string)){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        String a = null;
        String b = "";
        String c = "c";

        System.out.printf(isEmpty(a) + "");
        System.out.printf(isEmpty(b) + "");
        System.out.printf(isEmpty(c) + "");

        System.out.printf(isAllEmpty(a,b) +"");
        System.out.printf(isAllEmpty(a,b, c) +"");

    }
}
