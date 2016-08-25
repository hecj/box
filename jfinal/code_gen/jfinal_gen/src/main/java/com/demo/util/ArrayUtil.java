package com.demo.util;

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
	
}
