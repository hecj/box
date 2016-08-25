package ${var.base_pack_url}.web.${var.base_module_name};
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.peon.jfinal.core.XssInterceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import ${var.base_pack_url}.${var.base_module_name}.api.${root.fname}Service;
import ${var.base_pack_url}.${var.base_module_name}.api.${root.fname}_;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.JfinalxController;
import com.jfinal.plugin.activerecord.Page;

/**
 * ${root.fname}Controller
 * 所有 sql 与业务逻辑写在Service 中，尽量少写在 Controller 中
 */
${"@"}Controller
${"@"}Scope("prototype")//切记这里的多例标记 非常重要 除非共享controller 否则都建议使用单例  
public class ${root.fname}Controller extends JfinalxController {
	
	${"@"}Reference(version="1.0.0")
	private ${root.fname}Service ${root.name}Service;
	
	
	/**
	 * 首页列表
	 */
	public void index() {
		
		Page<Map<String, Object>> ${root.name}_page = ${root.name}Service.findPage(getParaToInt(0,1), fatchPs(), getMap(${root.fname}_.class)," id desc ");
		
		setAttr("${root.name}_page", ${root.name}_page);
		render("list_${root.name}.ftl");
	}
	
	
	/**
	 * 添加页面
	 */
	public void add(){
		render("add_${root.name}.ftl");
	}

	
	/**
	 * 修改页面
	 */
	public void edit(){
		Long id = getParaToLong(0,-1L);
		if (id>0) {
			Map<String, Object> ${root.name} = ${root.name}Service.findById(id);
			setAttr("${root.name}", ${root.name});
		}
		render("edit_${root.name}.ftl");
	}
	
	
	/**
	 * 保存动作
	 */
	${"@"}Before(XssInterceptor.class)
	public void save() {
		Map<String, Object> m = getMap("${root.name}.",${root.fname}_.class,new String[]{"-${root.name}.id"});
		System.out.println(m);
		Long id = ${root.name}Service.save(m);
		if (id!=null&&id>0) {
			System.out.println("save ok");
		}
		redirect("/${root.name}");
	}
	
	
	/**
	 * 更新动作
	 */
	${"@"}Before(XssInterceptor.class)
	public void update() {
		Map<String, Object> m = getMap("${root.name}.",${root.fname}_.class);
		System.out.println(m);
		boolean rs = ${root.name}Service.update(m);
		System.out.println("update :"+rs);
		redirect("/${root.name}");
	}
	
	
	/**
	 * 删除动作
	 */
	public void delete() {
		Long id = getParaToLong(0,-1L);
		if (id>0) {
			boolean rs = ${root.name}Service.deleteById(id);
			System.out.println("删除结果："+rs);
		}
		redirect("/${root.name}");
	}
	
	
	/**
	 * ajax删除动作
	 */
	${"@"}ActionKey("/${root.name}/a/del")
	public void del() {
		Long id = getParaToLong(0,-1L);
		setAttr("code", "-1");
		if (id>0) {
			boolean rs = ${root.name}Service.deleteById(id);
			System.out.println("删除结果："+rs);
			setAttr("code", "1");
		}
		setAttr("msg", "删除成功!");
		
		renderJson();
	}
	
	
	/**
	 * ajax列表页面
	 */
	public void ajax_${root.name}_list(){
		render("ajax_${root.name}_list.ftl");
	}
	
	
	/**
	 * ajax列表 
	 */
	public void ajax_${root.name}_list_page(){
		
		Page<Map<String, Object>> ${root.name}_page = ${root.name}Service.findPage(fatchPn(), fatchPs(), getMap(${root.fname}_.class)," id desc ");
		
		setAttr("${root.name}_page", ${root.name}_page);
		render("ajax_${root.name}_list_page.ftl");
	}
	
	
}


