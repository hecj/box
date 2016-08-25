package ${var.base_pack_url}.admin.${root.name};

import com.jfinal.aop.Before;
import com.demo.common.BaseController;
import ${var.base_pack_url}.service.${root.name}.model.${root.fname};
/**
 * ${root.fname}Controller (${root.comment?if_exists})
 */
public class ${root.fname}Controller extends BaseController {
	public void index() {
		<#-- addViewMsg(getPara("__type"), getPara("__msg")); -->
		setAttr("pg", ${root.fname}.dao._page(getParaToInt(0, 1), 10));
		render("index.ftl");
	}
	
	public void add() {
	}
	
	@Before(${root.fname}Validator.class)
	public void save() {
		getModel(${root.fname}.class).save();
		redirect("/${root.name}");
	}
	
	public void edit() {
		setAttr("${root.name}", ${root.fname}.dao.findById(getParaToInt()));
	}
	
	@Before(${root.fname}Validator.class)
	public void update() {
		getModel(${root.fname}.class).update();
		redirect("/${root.name}");
	}
	
	public void delete() {
		${root.fname}.dao.deleteById(getParaToInt());
		redirect("/${root.name}");
	}
	
	public void deleteByIds() {
		${root.fname}.dao.deleteByIds(getParaValues("id"));
		redirect("/${root.name}");
		<#-- addViewMsg(VIEW_MSG_TYPE_TIP,"hi~,删除成功啦!"); -->
		<#-- redirect("/${root.name}?type="+VIEW_MSG_TYPE_TIP+"&msg="+"hi~,删除成功啦!",true); -->
	}
}


