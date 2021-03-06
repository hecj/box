package com.boxamazing.admin.sys;

import com.jfinal.aop.Before;
import com.boxamazing.common.BaseController;
import com.boxamazing.service.m.model.M;
/**
 * MController (菜单)
 */
public class MController extends BaseController {
	public void index() {
		setAttr("pg", M.dao._page(getParaToInt(0, 1), 10));
		render("index.ftl");
	}
	
	public void add() {
	}
	
	@Before(MValidator.class)
	public void save() {
		M model = getModel(M.class);
		model.save();
		setAttr("_rs", model);
		redirect("/m");
	}
	
	public void edit() {
		setAttr("m", M.dao.findById(getParaToInt()));
	}
	
	@Before(MValidator.class)
	public void update() {
		getModel(M.class).update();
		redirect("/m");
	}
	
	public void delete() {
		M.dao.deleteById(getParaToInt());
		redirect("/m");
	}
	
	public void deleteByIds() {
		M.dao.deleteByIds(getParaValues("id"));
		redirect("/m");
	}

	@Override
	public String getMn() {
		return "菜单";
	}
}


