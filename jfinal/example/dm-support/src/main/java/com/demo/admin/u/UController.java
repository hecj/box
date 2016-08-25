package com.demo.admin.u;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.demo.common.BaseController;
import com.demo.common.interceptor.UserUtil;
import com.demo.service.r.model.R;
import com.demo.service.u.model.U;
/**
 * UController (用户)
 */
public class UController extends BaseController {
	public void index() {
		setAttr("pg", U.dao._page(getParaToInt(0, 1), 10));
		List<R> rl = R.dao.find("select * from r order by id desc");
		setAttr("rl", rl);
		render("index.ftl");
	}
	
	public void add() {
		List<R> rl = R.dao.find("select * from r order by id desc");
		setAttr("rl", rl);
	}
	
	@Before(UValidator.class)
	public void save() {
		U model = getModel(U.class);
		model.save();
		setAttr("_rs", model);
		redirect("/u");
	}
	
	public void updatepwd() {
		setAttr("u", U.dao.findById(UserUtil.getU(getSession()).get("id")));
		
	}
	
	public void updatep() {
		String oldp = getPara("oldp");
		String newp = getPara("newp");
		if (StrKit.isBlank(oldp)) {
			setAttr("msg", "原密码不能为空"); 
		}else  if (StrKit.isBlank(newp)) {
			setAttr("msg", "新密码不能为空"); 
		} else {
			
			U u = U.dao.findById(UserUtil.getU(getSession()).get("id"));
			if (oldp.equals(u.get("p"))) {
				u.set("p", newp);
				u.update();
				setAttr("msg", "密码修改成功,请重新登录"); 
				getSession().invalidate();
			}else {
				setAttr("msg", "原密码不匹配"); 
			}
		}
		render("updatepwd.ftl");
	}
	
	
	public void edit() {
		setAttr("u", U.dao.findById(getParaToInt()));
		List<R> rl = R.dao.find("select * from r order by id desc");
		setAttr("rl", rl);
	}
	
	@Before(UValidator.class)
	public void update() {
		getModel(U.class).update();
		redirect("/u");
	}
	
	public void delete() {
		U.dao.deleteById(getParaToInt());
		redirect("/u");
	}
	
	public void deleteByIds() {
		U.dao.deleteByIds(getParaValues("id"));
		redirect("/u");
	}

	@Override
	public String getMn() {
		return "用户";
	}
}


