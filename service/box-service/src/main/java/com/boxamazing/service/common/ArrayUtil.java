package com.boxamazing.service.common;

public class ArrayUtil {

    /**获取一定长度的？数组
     * @param len
     * @return
     */
    public static String[] getPrePareArray(int len) {
        String[] sz=new String[len];
        for (int i=0;i<len;i++) {
            sz[i]="?";
        }
        return sz;
    }
    
    /**
     * 将数组转换成可以in查询的字符串
     * @author XuXD
     * @param ids
     * @return
     */
	public static String arrayToString(Object[] ids){
    	if(ids == null || ids.length<1)
    		return "";
    	StringBuilder s = new StringBuilder();
    	for (int i = 0; i < ids.length; i++) {
    		s.append("'"+ids[i]+"'");
			if (i<ids.length-1) {
				s.append(",");
			}
		}
    	return s.toString();
    }

}