package com.boxamazing.webfront.util;

import java.io.Serializable;
import java.util.Random;

//昵称更改
public class NicknameUtil implements Serializable {

	
	/**
	 * 产生随机字符
	 * @param length  限定长度
	 * @param flag   是否采用特殊处理（0  只产生随机数字    1  产生随机数字 +大小写字母）
	 * @param str  需要进行字符拼接的目标字符串
	 * @author YJ
	 * @return
	 */
	public static String createRandString(int length,int flag,String str) {
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		Random random = new Random();
		int len = str.length();
		if(flag==0){
			//只随机数字
			for(int i=0;i<length-len;i++){
				sb.append(random.nextInt(10));
			}
		}else{
			//随机大小写字母+数字
			// 如果随机数为0,生成数字,为1 大写字母  2 小写字母
			for (int i = 0; i < length-len; i++) {
				int ranNum = random.nextInt(3);
				if (ranNum == 0) {
					sb.append(random.nextInt(10));
				}else if(ranNum==1){
					//大写字母
					sb.append((char) (random.nextInt(26) + 65));
				}else{
					//小写字母
					sb.append((char) (random.nextInt(26) + 97));
				}
			}
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
}
