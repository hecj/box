package com.boxamazing.admin.sys;

import com.boxamazing.common.BaseController;
import com.boxamazing.service.olu.model.Olu;

public class OluController extends BaseController {

	@Override
	public String getMn() {
		// TODO Auto-generated method stub
		return "用户日志";
	}
	
	/*
	 * 用户日志
	 */
	public void index(){
		setAttr("pg",Olu.dao.paginate(getParaToInt(0,1),10, "select * ", "from olu order by id desc"));
		render("infoUser.ftl");
	}
	
	/*
	 * 根据输入日期查询用户日志
	 */
	public void selectInfoUser() {
		int length = 10;
		setAttr("pg",Olu.dao.selectInfoUser(getParaToInt(0,1),length,getPara("uInfo"),getParaToDate("firstTime"),getParaToDate("lastTime")));
		render("infoUser.ftl");
	}
	
	/*
	 * 日志导出
	 */
	
}
