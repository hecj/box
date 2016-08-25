package com.boxamazing.admin.sys;

import com.boxamazing.util.MenuSorter;
import com.jfinal.aop.Before;
import com.boxamazing.common.BaseController;
import com.boxamazing.service.m.model.M;
import com.boxamazing.service.q.model.Q;
import com.boxamazing.service.r.model.R;

/**
 * RController (角色)
 */
public class RController extends BaseController {
	public void index() {
		setAttr("pg", R.dao._page(getParaToInt(0, 1), 10));
		setAttr("qs", Q.dao.find("select * from q "));
		setAttr("ms", M.dao.find("select * from m "));
		render("index.ftl");
	}
	
	public void add() {
		setAttr("qs", Q.dao.find("select * from q "));
		setAttr("ms", M.dao.find("select * from m "));
	}
	
	@Before(RValidator.class)
	public void save() {
		R model = getModel(R.class);
		model.save();
		setAttr("_rs", model);
		redirect("/r");
	}
	
	public void edit() {
		setAttr("r", R.dao.findById(getParaToInt()));  //通过R查询指定角色的qs, ms
		setAttr("qs", Q.dao.find("select * from q ")); //查询语句
		setAttr("ms", MenuSorter.sort(M.dao.find("select * from m "))); //菜单Menu权限
	}


	
	@Before(RValidator.class)
	public void update() {
		getModel(R.class).update();
		redirect("/r");
	}
	
	public void delete() {
		R.dao.deleteById(getParaToInt());
		redirect("/r");
	}
	
	public void deleteByIds() {
		R.dao.deleteByIds(getParaValues("id"));
		redirect("/r");
	}

	@Override
	public String getMn() {
		return "角色";
	}
}


