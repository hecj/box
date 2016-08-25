package com.demo.admin.r;

import com.jfinal.aop.Before;
import com.demo.common.BaseController;
import com.demo.service.r.model.R;
/**
 * RController (角色)
 */
public class RController extends BaseController {
	public void index() {
		setAttr("pg", R.dao._page(getParaToInt(0, 1), 10));
		render("index.ftl");
	}
	
	public void add() {
	}
	
	@Before(RValidator.class)
	public void save() {
		getModel(R.class).save();
		redirect("/r");
	}
	
	public void edit() {
		setAttr("r", R.dao.findById(getParaToInt()));
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
}


