package com.demo.admin.tbc;

import com.jfinal.aop.Before;
import com.demo.common.BaseController;
import com.demo.service.tbc.model.Tbc;
/**
 * TbcController (模型字段)
 */
public class TbcController extends BaseController {
	public void index() {
		setAttr("pg", Tbc.dao._page(getParaToInt(0, 1), 10));
		render("index.ftl");
	}
	
	public void add() {
	}
	
	@Before(TbcValidator.class)
	public void save() {
		getModel(Tbc.class).save();
		redirect("/tbc");
	}
	
	public void edit() {
		setAttr("tbc", Tbc.dao.findById(getParaToInt()));
	}
	
	@Before(TbcValidator.class)
	public void update() {
		getModel(Tbc.class).update();
		redirect("/tbc");
	}
	
	public void delete() {
		Tbc.dao.deleteById(getParaToInt());
		redirect("/tbc");
	}
	
	public void deleteByIds() {
		Tbc.dao.deleteByIds(getParaValues("id"));
		redirect("/tbc");
	}
}


