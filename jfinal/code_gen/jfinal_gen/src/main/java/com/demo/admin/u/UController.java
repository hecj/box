package com.demo.admin.u;

import com.jfinal.aop.Before;
import com.demo.common.BaseController;
import com.demo.service.u.model.U;
/**
 * UController (用户)
 */
public class UController extends BaseController {
	public void index() {
		setAttr("pg", U.dao._page(getParaToInt(0, 1), 10));
		render("index.ftl");
	}
	
	public void add() {
	}
	
	@Before(UValidator.class)
	public void save() {
		getModel(U.class).save();
		redirect("/u");
	}
	
	public void edit() {
		setAttr("u", U.dao.findById(getParaToInt()));
	}
	
	@Before(UValidator.class)
	public void update() {
		getModel(U.class).update();
		redirect("/u");
	}
	
	public void delete() {
		U.dao.deleteById(getParaToInt());
		redirect("/u");
	}
	
	public void deleteByIds() {
		U.dao.deleteByIds(getParaValues("id"));
		redirect("/u");
	}
}


