package com.boxamazing.admin;

import java.util.List;

import com.jfinal.aop.Before;
import com.boxamazing.common.BaseController;
import com.boxamazing.service.q.model.Q;
import com.boxamazing.service.qo.model.Qo;
import com.boxamazing.service.r.model.R;
import com.boxamazing.service.u.model.PUser;
/**
 * 统计查询
 */
public class StaticsController extends BaseController {
	public void index() {
		
		List<Q> ql = Q.findQListByTypeStat(1, 0);
//		List<Qo> qol = Qo.dao.find("select * from qo");
		
		setAttr("ql",ql);
//		setAttr("qol",qol);
		 
		render("index.ftl");
	}

	@Override
	public String getMn() {
		return "统计查询";
	}
	
	
	/*public void add() {
		List<R> rl = R.dao.find("select * from r order by id desc");
		setAttr("rl", rl);
	}
	
	@Before(UValidator.class)
	public void save() {
		getModel(U.class).save();
		redirect("/u");
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
	}*/
}


