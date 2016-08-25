package com.boxamazing.admin.sys;

import com.jfinal.aop.Before;
import com.boxamazing.common.BaseController;
import com.boxamazing.service.dbs.model.Dbs;
/**
 * DbsController (数据源管理)
 */
public class DbsController extends BaseController {
	public void index() {
		setAttr("pg", Dbs.dao._page(getParaToInt(0, 1), 10));
		render("index.ftl");
	}
	
	public void add() {
	}
	
	@Before(DbsValidator.class)
	public void save() {
		Dbs model = getModel(Dbs.class);
		model.save();
		setAttr("_rs", model);
		redirect("/dbs");
	}
	
	public void edit() {
		setAttr("dbs", Dbs.dao.findById(getParaToInt()));
	}
	
	@Before(DbsValidator.class)
	public void update() {
		getModel(Dbs.class).update();
		redirect("/dbs");
	}
	
	public void delete() {
		Dbs.dao.deleteById(getParaToInt());
		redirect("/dbs");
	}
	
	public void deleteByIds() {
		Dbs.dao.deleteByIds(getParaValues("id"));
		redirect("/dbs");
	}

	@Override
	public String getMn() {
		return "数据源配置";
	}
}


