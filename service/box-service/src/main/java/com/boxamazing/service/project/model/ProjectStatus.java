package com.boxamazing.service.project.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boxamazing.service.common.StringUtil;

/**
 * 状态简单工厂<br />
 * Created by jhl on 15/8/19.
 * <p/>
 * #产品状态表#
 * ================================
 * 状态			含义
 * --------------------------------
 * 0			初审草稿
 * 1			初审提交
 * 2			初审未通过
 * 3			初审核通过/复审草稿
 * --------------------------------
 * 4			提交复审
 * 5			复审打回
 * 6			复审通过
 * --------------------------------
 * 7			产品预热
 * 8			产品众筹中
 * 9			产品众筹结束失败
 * 10			产品众筹结束成功
 * ================================
 */
public class ProjectStatus {
	/**
	 * 初审草稿
	 */
	public final static int FIRST_DRAFTS = 0;
	
	/**
	 * 初审提交
	 */
	public final static int FIRST_SUBMIT = 1;
	
	/**
	 * 初审未通过
	 */
	public final static int FIRST_NO_PASS = 2;
	
	/**
	 * 初审核通过/复审草稿
	 */
	public final static int FIRST_PASS = 3;
	
	/**
	 * 提交复审
	 */
	public final static int SECOND_SUBMIT = 4;
	
	/**
	 * 复审打回
	 */
	public final static int SECOND_NO_PASS = 5;
	
	/**
	 * 复审通过
	 */
	public final static int SECOND_PASS = 6;
	
	/**
	 * 产品预热
	 */
	public final static int PREHEAT = 7;
	
	/**
	 * 产品众筹中
	 */
	public final static int CROWDFUNDING = 8;
	
	/**
	 * 产品众筹结束失败
	 */
	public final static int CROWDFUNDED_FAILURE = 9;
	
	/**
	 * 产品众筹结束成功
	 */
	public final static int CROWDFUNDED_SUCCESS = 10;

	
	/**
	 * 枚举所有状态
	 * by HECJ
	 */
    private static final Map<Integer,String> statusMap = new HashMap<Integer,String>();
    static {
    	statusMap.put(0, "全部");
    	statusMap.put(1, "初审中");
    	statusMap.put(2, "初审失败");
    	statusMap.put(3, "初审通过");
    	statusMap.put(4, "复审中");
    	statusMap.put(5, "复审失败");
    	statusMap.put(6, "复审通过");
    	statusMap.put(7, "预热");
    	statusMap.put(8, "众筹中");
    	statusMap.put(9, "众筹失败");
    	statusMap.put(10, "众筹成功");
    }
    
    /**
     * 通过int状态获取信息
     * up HECJ
     */
    public static String getStatus(Integer statusId) {
    	String status = statusMap.get(statusId);
        if (StringUtil.isNullOrEmpty(status)) {
            return "未知状态";
        }
        return status;
    }
    
    /**
     * up HECJ
     */
    public static List<String> findAll(){
    	List<String> list = new ArrayList();
    	list.add(statusMap.get(7));
    	list.add(statusMap.get(8));
    	list.add(statusMap.get(10));
    	return list;
 	
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
