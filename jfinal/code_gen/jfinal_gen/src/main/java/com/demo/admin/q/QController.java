package com.demo.admin.q;

import com.jfinal.aop.Before;
import com.demo.common.BaseController;
import com.demo.service.q.model.Q;
/**
 * QController (通用查询)
 */
public class QController extends BaseController {
	public void index() {
		setAttr("pg", Q.dao._page(getParaToInt(0, 1), 10));
		render("index.ftl");
	}
	
	public void add() {
	}
	
	@Before(QValidator.class)
	public void save() {
		getModel(Q.class).save();
		redirect("/q");
	}
	
	public void edit() {
		setAttr("q", Q.dao.findById(getParaToInt()));
	}
	
	@Before(QValidator.class)
	public void update() {
		getModel(Q.class).update();
		redirect("/q");
	}
	
	public void delete() {
		Q.dao.deleteById(getParaToInt());
		redirect("/q");
	}
	
	public void deleteByIds() {
		Q.dao.deleteByIds(getParaValues("id"));
		redirect("/q");
	}
}


