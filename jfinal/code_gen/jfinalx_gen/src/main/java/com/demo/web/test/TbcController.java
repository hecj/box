package com.demo.web.test;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.peon.jfinal.core.XssInterceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.test.api.TbcService;
import com.demo.test.api.Tbc_;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.JfinalxController;
import com.jfinal.plugin.activerecord.Page;

/**
 * TbcController
 * 所有 sql 与业务逻辑写在Service 中，尽量少写在 Controller 中
 */
@Controller
@Scope("prototype")//切记这里的多例标记 非常重要 除非共享controller 否则都建议使用单例  
public class TbcController extends JfinalxController {
	
	@Reference(version="1.0.0")
	private TbcService tbcService;
	
	
	/**
	 * 首页列表
	 */
	public void index() {
		
		Page<Map<String, Object>> tbc_page = tbcService.findPage(getParaToInt(0,1), fatchPs(), getMap(Tbc_.class)," id desc ");
		
		setAttr("tbc_page", tbc_page);
		render("list_tbc.ftl");
	}
	
	
	/**
	 * 添加页面
	 */
	public void add(){
		render("add_tbc.ftl");
	}

	
	/**
	 * 修改页面
	 */
	public void edit(){
		Long id = getParaToLong(0,-1L);
		if (id>0) {
			Map<String, Object> tbc = tbcService.findById(id);
			setAttr("tbc", tbc);
		}
		render("edit_tbc.ftl");
	}
	
	
	/**
	 * 保存动作
	 */
	@Before(XssInterceptor.class)
	public void save() {
		Map<String, Object> m = getMap("tbc.",Tbc_.class,new String[]{"-tbc.id"});
		System.out.println(m);
		Long id = tbcService.save(m);
		if (id!=null&&id>0) {
			System.out.println("save ok");
		}
		redirect("/tbc");
	}
	
	
	/**
	 * 更新动作
	 */
	@Before(XssInterceptor.class)
	public void update() {
		Map<String, Object> m = getMap("tbc.",Tbc_.class);
		System.out.println(m);
		boolean rs = tbcService.update(m);
		System.out.println("update :"+rs);
		redirect("/tbc");
	}
	
	
	/**
	 * 删除动作
	 */
	public void delete() {
		Long id = getParaToLong(0,-1L);
		if (id>0) {
			boolean rs = tbcService.deleteById(id);
			System.out.println("删除结果："+rs);
		}
		redirect("/tbc");
	}
	
	
	/**
	 * ajax删除动作
	 */
	@ActionKey("/tbc/a/del")
	public void del() {
		Long id = getParaToLong(0,-1L);
		setAttr("code", "-1");
		if (id>0) {
			boolean rs = tbcService.deleteById(id);
			System.out.println("删除结果："+rs);
			setAttr("code", "1");
		}
		setAttr("msg", "删除成功!");
		
		renderJson();
	}
	
	
	/**
	 * ajax列表页面
	 */
	public void ajax_tbc_list(){
		render("ajax_tbc_list.ftl");
	}
	
	
	/**
	 * ajax列表 
	 */
	public void ajax_tbc_list_page(){
		
		Page<Map<String, Object>> tbc_page = tbcService.findPage(fatchPn(), fatchPs(), getMap(Tbc_.class)," id desc ");
		
		setAttr("tbc_page", tbc_page);
		render("ajax_tbc_list_page.ftl");
	}
	
	
}


