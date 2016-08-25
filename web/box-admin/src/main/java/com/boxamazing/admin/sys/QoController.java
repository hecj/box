package com.boxamazing.admin.sys;

import java.util.HashMap;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.boxamazing.common.BaseController;
import com.boxamazing.service.q.model.Q;
import com.boxamazing.service.qo.model.Qo;
/**
 * QoController (查询关联的字段选项)
 */
public class QoController extends BaseController {
	public void index() {
		setAttr("pg", Qo.dao._page(getParaToInt(0, 1), 10));
		render("index.ftl");
	}
	
	public void add() {
		setAttr("ql", Q.findAllQList());
	}
	public void addop() {
		
		
		String f = getPara("f");
		Long id = getParaToLong();
		if (f!=null&&(!StrKit.isBlank(f))) {
			List<Qo> ql = Qo.findQoListByQidF(id, f);
			if(ql!=null && ql.size()>0 ){
				Qo qo = ql.get(0);
				setAttr("qo",qo);
				setAttr("ql", Q.findAllQList());
				render("edit.ftl");
				return;
			}
		} 
			
		Record qo=new Record();
		qo.set("f", f);
		qo.set("qid", id);
		setAttr("qo",qo);
		setAttr("ql", Q.findAllQList());
		render("add.ftl");
		
	
	}
	
	@Before(QoValidator.class)
	public void save() {
		Qo model = getModel(Qo.class);
		model.save();
		setAttr("_rs", model);
		redirect("/qo");
	}
	
	public void edit() {
		setAttr("ql", Q.findAllQList());
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

	@Override
	public String getMn() {
		return "查询关联字段";
	}
}


