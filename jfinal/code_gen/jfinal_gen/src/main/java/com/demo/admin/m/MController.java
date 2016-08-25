package com.demo.admin.m;

import com.jfinal.aop.Before;
import com.demo.common.BaseController;
import com.demo.service.m.model.M;
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
		getModel(M.class).save();
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
}


