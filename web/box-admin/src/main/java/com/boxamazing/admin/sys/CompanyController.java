package com.boxamazing.admin.sys;

import com.boxamazing.common.BaseController;
import com.jfinal.render.Render;


/**
 * 测试权限的controller
 * @author pchome
 *
 */
public class CompanyController extends BaseController{
	
	public void index(){
		setAttr("name", "hello");
		//render("company.ftl");
		//不用render即转向index.ftl目录
	}

	@Override
	public String getMn() {
		// TODO Auto-generated method stub
		return null;
	}

}
