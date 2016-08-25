package com.boxamazing.service.util;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class MathCalcUtil {
    
    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;
    
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    //减法 
    public static BigDecimal sub(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    public static BigDecimal add(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    //乘法
    public static BigDecimal mul(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    //乘法
    public static BigDecimal mul(String v1,String v2, int scale){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    
   
    public static double div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }
       
    public static double div(double v1,double v2,int scale){
        if(scale<0){
        	return BigDecimal.ZERO.doubleValue();
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    public static BigDecimal div(String v1, String  v2,int scale){
    	  if(scale<0){
    		  return BigDecimal.ZERO;
          }
    	  try {
    		  BigDecimal b1 = new BigDecimal(v1);
              BigDecimal b2 = new BigDecimal(v2);
              return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
        
    }
       
    public static double round(double v,int scale){
    	if(scale<0){
    		return BigDecimal.ZERO.doubleValue();
    	}
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
      
    public static float convertsToFloat(double v){
	    BigDecimal b = new BigDecimal(v);
	    return b.floatValue();
    }
       
    public static int convertsToInt(double v){
    	BigDecimal b = new BigDecimal(v);
        return b.intValue();
    }

    public static long convertsToLong(double v){
    	BigDecimal b = new BigDecimal(v);
        return b.longValue();
    }

    public static double returnMax(double v1,double v2){
    	BigDecimal b1 = new BigDecimal(v1);
    	BigDecimal b2 = new BigDecimal(v2);
        return b1.max(b2).doubleValue();
    }

    public static double returnMin(double v1,double v2){
    	BigDecimal b1 = new BigDecimal(v1);
    	BigDecimal b2 = new BigDecimal(v2);
        return b1.min(b2).doubleValue();
    }

    public static int compareTo(double v1,double v2){
    	BigDecimal b1 = new BigDecimal(Double.toString(v1));
    	BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.compareTo(b2);
    }
    
    
    public static long objectToLong(Object value) {
		if (null == value) {
			return 0;
		} else {
			return Long.valueOf(value.toString().replaceAll(",", ""));
		}

	}

    public static double objectToDouble(Object value) {
		if (null == value) {
			return 0.0;
		} else {
			return Double.valueOf(value.toString().replaceAll(",", ""));
		}
	}
    
    /**
     *
     * @param multiplier 乘数
     * @param multiplicand 被乘数
     * @param divisor 被除数
     * @param scale  保留位数
     * @return (multiplier * multiplicand)/divisor
     */
    public static BigDecimal mulAndDiv(String multiplier, String multiplicand, String divisor, int scale){
    	if(StringUtils.isBlank(multiplier)||StringUtils.isBlank(multiplicand) || StringUtils.isBlank(divisor)){
    		 return BigDecimal.ZERO;
    	}
    	if(!NumberUtils.isNumber(multiplier)|| !NumberUtils.isNumber(multiplicand)|| !NumberUtils.isNumber(divisor)){
    		 return BigDecimal.ZERO;
    	}
    	BigDecimal divisorB = new BigDecimal(divisor);
    	if(divisorB.compareTo(BigDecimal.ZERO) != 1){
    		 return BigDecimal.ZERO;
    	}
    	BigDecimal multiplierB = new BigDecimal(multiplier);
    	BigDecimal multiplicandB = new BigDecimal(multiplicand);
    	return multiplierB.multiply(multiplicandB).divide(divisorB, scale, BigDecimal.ROUND_HALF_UP);
    }
    
    
    /**
     * 取余数  string
     * @param v1
     * @param v2
     * @param scale
     * @return  string
     */
    public static String strRemainder2Str(String v1,String v2, int scale){
        if (scale < 0) {
        	return BigDecimal.ZERO.toString();
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.remainder(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }
    
    /**
     * 判断两个数是否可以整除
     * @param v1
     * @param v2
     * @param scale
     * @return true 可以整除 false 不可以整除
     */
    public static boolean divIsible(String v1,String v2, int scale){
    	  if (scale < 0) {
              return false;
          }
          BigDecimal b1 = new BigDecimal(v1);
          BigDecimal b2 = new BigDecimal(v2);
          BigDecimal c =  b1.remainder(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);
          return c.compareTo(BigDecimal.ZERO) == 0;
    }
    
    
    /**
     * 精确的除法运算。除不尽时，由scale参数指 定精度 四舍五入。求两个可以整除数的商
     *
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @param scale
     *            表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static long strDiv(String v1, String v2, int scale) {
    	if( !divIsible(v1, v2, scale)){
    		 throw new IllegalArgumentException("不能整除");
    	}
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).longValue();
    }
    
    public static BigDecimal strToBigDecimal(String str, int scale){
    	if(StringUtils.isBlank(str)){
    		return BigDecimal.ZERO;
    	}
    	str  = str.replaceAll(",", "");
    	if(StringUtils.isBlank(str)|| !NumberUtils.isNumber(str)){
    		return BigDecimal.ZERO;
    	}
    	return new BigDecimal(str).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    
    public static void main(String[] args) {
    	long  time = System.currentTimeMillis();
    	Date date = new Date(2099, 12, 29, 59, 59, 59);
    	System.out.println(date.getTime());
    	System.out.println(time);
        System.out.println(MathCalcUtil.strToBigDecimal("1.00", 2).toString());
        System.out.println(MathCalcUtil.strToBigDecimal("1.00", 2).doubleValue());
    }

}
