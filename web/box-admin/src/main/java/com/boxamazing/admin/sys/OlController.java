package com.boxamazing.admin.sys;

import com.boxamazing.common.BaseController;
import com.boxamazing.service.ol.model.Ol;
/**
 * OlController (操作日志)
 */
public class OlController extends BaseController {
	public void index() {
		setAttr("pg", Ol.dao._page(getParaToInt(0, 1), 10));
		render("index.ftl");
	}

	@Override
	public String getMn() {
		return "操作日志";
	}
	
	/*public void add() {
	}
	
	@Before(OlValidator.class)
	public void save() {
		getModel(Ol.class).save();
		redirect("/ol");
	}
	
	public void edit() {
		setAttr("ol", Ol.dao.findById(getParaToInt()));
	}
	
	@Before(OlValidator.class)
	public void update() {
		getModel(Ol.class).update();
		redirect("/ol");
	}
	
	public void delete() {
		Ol.dao.deleteById(getParaToInt());
		redirect("/ol");
	}
	
	public void deleteByIds() {
		Ol.dao.deleteByIds(getParaValues("id"));
		redirect("/ol");
	}*/
}


