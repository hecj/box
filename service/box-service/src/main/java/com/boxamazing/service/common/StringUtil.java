package com.boxamazing.service.common;



import java.util.Date;
import java.util.UUID;


/**
 * StringUtil <br />
 * 由easy_to_code移植过来的.
 */
public class StringUtil {
    /**
     * 替换下划线 并且将第一个字母大写
     */
    public static String noUnderLineAndUpFirstChar(String s) {
        String[] zs = splitBy(s,"_");
        String rs="";
        for (String z : zs) {
            rs+=uperFirstChar(z);
        }
        return rs;
    }
    /**
     * 分割
     */
    public static String[] splitBy(String s,String by) {
        String[] zs = s.split(by);
        return zs;
    }
    /**
     * 将第一个字母大写
     */
    public static String uperFirstChar(String s) {
        return s.substring(0, 1).toUpperCase()+s.substring(1);
    }
    /**
     * 将第一个字母小写
     */
    public static String lowerFirstChar(String s) {
        return s.substring(0, 1).toLowerCase()+s.substring(1);
    }
    /**
     * 是否为null或者为空
     */
    public static boolean isNullOrEmpty(String s) {
        return isNull(s)||isEmpty(s);
    }

    public static boolean isNullOrEmpty(String ... list) {
        for(String s : list) {
            if(isNullOrEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否为null
     */
    public static boolean isNull(String s) {
        return null==s;
    }

    /**
     * 是否为Null
     * @param s
     * @return
     */
    public static boolean isNull(Object s) {
        return null == s;
    }

    /**
     * 是否为Null
     * @param list
     * @return
     */
    public static boolean isNull(Object ... list) {
        for(Object o: list) {
            if(isNull(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否为空
     */
    public static boolean isEmpty(String s) {
        return "".equals(s);
    }
    /**将某一位置的字符变成小写
     * @param cph
     * @param i
     * @return
     */
    public static String lowerByIndex(String cph, int i) {
        return cph.substring(0, i)+lowerFirstChar(cph.substring(i));
    }
    /**重复字符
     * @param size
     * @param string 要重复的字符
     * @param split 分隔符
     * @return
     */
    public static String repeatString(int size, String string, String split) {
        String str="";
        for (int i = 0; i < size; i++) {
            str+=split+string;
        }
        if (size>0) {
            str=str.substring(1);
        }
        return str;
    }
    /**去首空格
     * @param textContent
     * @return
     */
    public static String trimFirst(String textContent) {
        if (isNullOrEmpty(textContent)) {
            return textContent;
        }
        return textContent.replaceFirst("\\s+", "");
    }
    /**去尾空格
     * @param textContent
     * @return
     */
    public static String trimEnd(String textContent) {
        if (isNullOrEmpty(textContent)) {
            return textContent;
        }
        return textContent.replaceAll("(\\s*)$", "");
    }
    /**去首尾空格
     * @param textContent
     * @return
     */
    public static String trimFirstAndEnd(String textContent) {
        return trimEnd(trimFirst(textContent));
    }

    /**去首尾空格
     * @param textContent
     * @return
     */
    public static String trimCRLF(String textContent) {
        if (isNullOrEmpty(textContent)) {
            return textContent;
        }
        return textContent.replaceAll("[\\t\\n\\r]", "");
    }
    /**
     * 获取uuid
     * @return
     */
    public static  String getUuid() {
        String uuid=UUID.randomUUID().toString();
        return uuid;
    }

    public static void main(String[] args) {
        String sql="  \r\n   \r\n  \r\n   INSERT    \r\n   \r\n   INTO user   VALUES (     null, 'zz' , 11   ) \r\n\r\n\r\n  \r\n";
        System.out.println(trimFirstAndEnd(trimCRLF(sql))+"---");
        System.out.println(getUuid() + "_" + TimeUtil.onlyFormat(new Date()));
    }
    /**如果为空字符内容就返回  默认值
     * @param strin
     * @return
     */
    public static String returnIfEmpty(String str,String def) {
        if (isNullOrEmpty(str)) {
            return def;
        }
        return str;
    }
    /**数组中间字符拼接
     * @param ids
     * @param string
     */
    public static String join(String[] ids, String sp) {
        if (ids==null||ids.length==0) {
            return "";
        }
        String str="";
        for (String id : ids) {
            str+=id+sp;
        }
        return str.substring(0, str.length()-sp.length());
    }
}
