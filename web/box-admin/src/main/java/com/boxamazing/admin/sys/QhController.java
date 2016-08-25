package com.boxamazing.admin.sys;

import com.jfinal.aop.Before;
import com.boxamazing.common.BaseController;
import com.boxamazing.service.qh.model.Qh;
/**
 * QhController (查询历史)
 */
public class QhController extends BaseController {
	public void index() {
		setAttr("pg", Qh.dao._page(getParaToInt(0, 1), 10));
		render("index.ftl");
	}
	
	public void add() {
	}
	
	@Before(QhValidator.class)
	public void save() {
		Qh model = getModel(Qh.class);
		model.save();
		setAttr("_rs", model);
		redirect("/qh");
	}
	
	public void edit() {
		setAttr("qh", Qh.dao.findById(getParaToInt()));
	}
	
	@Before(QhValidator.class)
	public void update() {
		getModel(Qh.class).update();
		redirect("/qh");
	}
	
	public void delete() {
		Qh.dao.deleteById(getParaToInt());
		redirect("/qh");
	}
	
	public void deleteByIds() {
		Qh.dao.deleteByIds(getParaValues("id"));
		redirect("/qh");
	}

	@Override
	public String getMn() {
		return "查询历史记录";
	}
}


