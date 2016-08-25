package com.demo.admin.tb;

import com.jfinal.aop.Before;
import com.demo.common.BaseController;
import com.demo.service.tb.model.Tb;
/**
 * TbController (模型)
 */
public class TbController extends BaseController {
	public void index() {
		setAttr("pg", Tb.dao._page(getParaToInt(0, 1), 10));
		render("index.ftl");
	}
	
	public void add() {
	}
	
	@Before(TbValidator.class)
	public void save() {
		getModel(Tb.class).save();
		redirect("/tb");
	}
	
	public void edit() {
		setAttr("tb", Tb.dao.findById(getParaToInt()));
	}
	
	@Before(TbValidator.class)
	public void update() {
		getModel(Tb.class).update();
		redirect("/tb");
	}
	
	public void delete() {
		Tb.dao.deleteById(getParaToInt());
		redirect("/tb");
	}
	
	public void deleteByIds() {
		Tb.dao.deleteByIds(getParaValues("id"));
		redirect("/tb");
	}
}


