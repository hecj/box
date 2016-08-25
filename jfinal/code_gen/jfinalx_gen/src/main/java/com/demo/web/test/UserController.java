package com.demo.web.test;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.peon.jfinal.core.XssInterceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.test.api.UserService;
import com.demo.test.api.User_;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.JfinalxController;
import com.jfinal.plugin.activerecord.Page;

/**
 * UserController
 * 所有 sql 与业务逻辑写在Service 中，尽量少写在 Controller 中
 */
@Controller
@Scope("prototype")//切记这里的多例标记 非常重要 除非共享controller 否则都建议使用单例  
public class UserController extends JfinalxController {
	
	@Reference(version="1.0.0")
	private UserService userService;
	
	
	/**
	 * 首页列表
	 */
	public void index() {
		
		Page<Map<String, Object>> user_page = userService.findPage(getParaToInt(0,1), fatchPs(), getMap(User_.class)," id desc ");
		
		setAttr("user_page", user_page);
		render("list_user.ftl");
	}
	
	
	/**
	 * 添加页面
	 */
	public void add(){
		render("add_user.ftl");
	}

	
	/**
	 * 修改页面
	 */
	public void edit(){
		Long id = getParaToLong(0,-1L);
		if (id>0) {
			Map<String, Object> user = userService.findById(id);
			setAttr("user", user);
		}
		render("edit_user.ftl");
	}
	
	
	/**
	 * 保存动作
	 */
	@Before(XssInterceptor.class)
	public void save() {
		Map<String, Object> m = getMap("user.",User_.class,new String[]{"-user.id"});
		System.out.println(m);
		Long id = userService.save(m);
		if (id!=null&&id>0) {
			System.out.println("save ok");
		}
		redirect("/user");
	}
	
	
	/**
	 * 更新动作
	 */
	@Before(XssInterceptor.class)
	public void update() {
		Map<String, Object> m = getMap("user.",User_.class);
		System.out.println(m);
		boolean rs = userService.update(m);
		System.out.println("update :"+rs);
		redirect("/user");
	}
	
	
	/**
	 * 删除动作
	 */
	public void delete() {
		Long id = getParaToLong(0,-1L);
		if (id>0) {
			boolean rs = userService.deleteById(id);
			System.out.println("删除结果："+rs);
		}
		redirect("/user");
	}
	
	
	/**
	 * ajax删除动作
	 */
	@ActionKey("/user/a/del")
	public void del() {
		Long id = getParaToLong(0,-1L);
		setAttr("code", "-1");
		if (id>0) {
			boolean rs = userService.deleteById(id);
			System.out.println("删除结果："+rs);
			setAttr("code", "1");
		}
		setAttr("msg", "删除成功!");
		
		renderJson();
	}
	
	
	/**
	 * ajax列表页面
	 */
	public void ajax_user_list(){
		render("ajax_user_list.ftl");
	}
	
	
	/**
	 * ajax列表 
	 */
	public void ajax_user_list_page(){
		
		Page<Map<String, Object>> user_page = userService.findPage(fatchPn(), fatchPs(), getMap(User_.class)," id desc ");
		
		setAttr("user_page", user_page);
		render("ajax_user_list_page.ftl");
	}
	
	
}


