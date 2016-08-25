package com.demo.admin.t_user;

import com.jfinal.aop.Before;
import com.demo.common.BaseController;
import com.demo.service.t_user.model.TUser;
/**
 * TUserController ()
 */
public class TUserController extends BaseController {
	public void index() {
		setAttr("pg", TUser.dao._page(getParaToInt(0, 1), 10));
		render("index.ftl");
	}
	
	public void add() {
	}
	
	@Before(TUserValidator.class)
	public void save() {
		getModel(TUser.class).save();
		redirect("/t_user");
	}
	
	public void edit() {
		setAttr("t_user", TUser.dao.findById(getParaToInt()));
	}
	
	@Before(TUserValidator.class)
	public void update() {
		getModel(TUser.class).update();
		redirect("/t_user");
	}
	
	public void delete() {
		TUser.dao.deleteById(getParaToInt());
		redirect("/t_user");
	}
	
	public void deleteByIds() {
		TUser.dao.deleteByIds(getParaValues("id"));
		redirect("/t_user");
	}
}


