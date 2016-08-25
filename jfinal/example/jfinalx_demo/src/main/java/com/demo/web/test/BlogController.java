package com.demo.web.test;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.peon.jfinal.core.XssInterceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.test.api.BlogService;
import com.demo.test.api.Blog_;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.JfinalxController;
import com.jfinal.plugin.activerecord.Page;

/**
 * BlogController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@Controller
@Scope("prototype")//切记这里的多例标记 非常重要 除非共享controller 否则都建议使用单例  
public class BlogController extends JfinalxController {
	
	@Reference(version="1.0.0")
	private BlogService blogService;
	
	
	/**
	 * 首页列表
	 */
	public void index() {
		
		Page<Map<String, Object>> blog_page = blogService.findPage(getParaToInt(0,1), fatchPs(), getMap(Blog_.class)," id desc ");
		
		setAttr("blog_page", blog_page);
		render("list_blog.ftl");
	}
	
	
	/**
	 * 添加页面
	 */
	public void add(){
		render("add_blog.ftl");
	}

	
	/**
	 * 修改页面
	 */
	public void edit(){
		Long id = getParaToLong(0,-1L);
		if (id>0) {
			Map<String, Object> blog = blogService.findById(id);
			setAttr("blog", blog);
		}
		render("edit_blog.ftl");
	}
	
	
	/**
	 * 保存动作
	 */
	@Before(XssInterceptor.class)
	public void save() {
		Map<String, Object> m = getMap("blog.",Blog_.class,new String[]{"-blog.id"});
		Long id = blogService.save(m);
		redirect("/blog");
	}
	
	
	/**
	 * 更新动作
	 */
	@Before(XssInterceptor.class)
	public void update() {
		Map<String, Object> m = getMap("blog.",Blog_.class);
		boolean rs = blogService.update(m);
		redirect("/blog");
	}
	
	
	/**
	 * 删除动作
	 */
	public void delete() {
		Long id = getParaToLong(0,-1L);
		if (id>0) {
			boolean rs = blogService.deleteById(id);
			System.out.println("删除结果："+rs);
		}
		redirect("/blog");
	}
	
	
	/**
	 * ajax删除动作
	 */
	@ActionKey("/blog/a/del")
	public void del() {
		Long id = getParaToLong(0,-1L);
		setAttr("code", "-1");
		if (id>0) {
			boolean rs = blogService.deleteById(id);
			System.out.println("删除结果："+rs);
			setAttr("code", "1");
		}
		setAttr("msg", "删除成功!");
		
		renderJson();
	}
	
	
	/**
	 * ajax列表页面
	 */
	public void ajax_blog_list(){
		render("ajax_blog_list.ftl");
	}
	
	
	/**
	 * ajax列表 
	 */
	public void ajax_blog_list_page(){
		
		Page<Map<String, Object>> blog_page = blogService.findPage(fatchPn(), fatchPs(), getMap(Blog_.class)," id desc ");
		
		setAttr("blog_page", blog_page);
		render("ajax_blog_list_page.ftl");
	}
	
	
}


