package com.boxamazing.admin.sys;

import java.util.List;

import com.jfinal.aop.Before;
import com.boxamazing.common.BaseController;
import com.boxamazing.service.q.model.Q;
import com.boxamazing.service.qo.model.Qo;
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
		Q model = getModel(Q.class);
		model.save();
		setAttr("_rs", model);
		redirect("/q");
	}
	
	public void edit() {
		setAttr("q", Q.dao.findById(getParaToLong()));
		List<Qo> find = Qo.dao.find("select GROUP_CONCAT(f) as fs from qo where qid=?",getParaToLong());
		if (find!=null&find.size()>0) {
			setAttr("qostr", find.get(0).getStr("fs"));
		}
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

	@Override
	public String getMn() {
		return "查询管理";
	}
}


