package com.boxamazing.service.orders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boxamazing.service.common.StringUtil;

/**
 * 状态简单工厂<br />
 * Created by liyanlong on 15/11/02.
 * <p/>
 * #订单状态表#
 * ================================
 * 状态			含义
 * --------------------------------
 * 0			未付款
 * 1			已付款
 * 2			交易关闭
 * 3			交易失效
 * --------------------------------
 * 4			已发货
 * 5			已收货
 * 6			等待退款
 * 7			已退款
 * ================================
 */
public class OrderStatus {
	
	/**
	 * 枚举所有状态
	 * by liyanlong
	 */
    private static final Map<Integer,String> statusMap = new HashMap<Integer,String>();
    static {
    	statusMap.put(0, "未付款");
    	statusMap.put(1, "已付款");
    	statusMap.put(2, "交易关闭");
    	statusMap.put(3, "交易失效");
    	statusMap.put(4, "已发货");
    	statusMap.put(5, "已收货");
    	statusMap.put(6, "等待退款");
    	statusMap.put(7, "退款");
    }
    
    /**
     * 通过int状态获取信息
     * up liyanlong
     */
    public static String getStatus(Integer statusId) {
    	String status = statusMap.get(statusId);
        if (StringUtil.isNullOrEmpty(status)) {
            return "未知状态";
        }
        return status;
    }
    
    /**
     * 
     * @return map
     */
   public static Map<Integer, String> findAllMap(){
	   Map<Integer, String>  status = new HashMap<Integer, String>();
	   status = statusMap; 
	   return status;
   }
   

}
