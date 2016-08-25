package com.demo.admin.r;

import com.jfinal.aop.Before;
import com.demo.common.BaseController;
import com.demo.service.m.model.M;
import com.demo.service.q.model.Q;
import com.demo.service.r.model.R;
/**
 * RController (角色)
 */
public class RController extends BaseController {
	public void index() {
		setAttr("pg", R.dao._page(getParaToInt(0, 1), 10));
		setAttr("qs", Q.dao.find("select * from q "));
		setAttr("ms", M.dao.find("select * from m "));
		render("index.ftl");
	}
	
	public void add() {
		setAttr("qs", Q.dao.find("select * from q "));
		setAttr("ms", M.dao.find("select * from m "));
	}
	
	@Before(RValidator.class)
	public void save() {
		R model = getModel(R.class);
		model.save();
		setAttr("_rs", model);
		redirect("/r");
	}
	
	public void edit() {
		setAttr("r", R.dao.findById(getParaToInt()));
		setAttr("qs", Q.dao.find("select * from q "));
		setAttr("ms", M.dao.find("select * from m "));
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

	@Override
	public String getMn() {
		return "角色";
	}
}


