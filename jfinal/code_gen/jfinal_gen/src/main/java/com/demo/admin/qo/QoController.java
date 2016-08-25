package com.demo.admin.qo;

import com.jfinal.aop.Before;
import com.demo.common.BaseController;
import com.demo.service.qo.model.Qo;
/**
 * QoController (查询关联的字段选项)
 */
public class QoController extends BaseController {
	public void index() {
		setAttr("pg", Qo.dao._page(getParaToInt(0, 1), 10));
		render("index.ftl");
	}
	
	public void add() {
	}
	
	@Before(QoValidator.class)
	public void save() {
		getModel(Qo.class).save();
		redirect("/qo");
	}
	
	public void edit() {
		setAttr("qo", Qo.dao.findById(getParaToInt()));
	}
	
	@Before(QoValidator.class)
	public void update() {
		getModel(Qo.class).update();
		redirect("/qo");
	}
	
	public void delete() {
		Qo.dao.deleteById(getParaToInt());
		redirect("/qo");
	}
	
	public void deleteByIds() {
		Qo.dao.deleteByIds(getParaValues("id"));
		redirect("/qo");
	}
}


