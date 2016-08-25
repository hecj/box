package com.demo.web.test;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.test.api.BlogService;
import com.demo.test.api.Blog_;
import com.jfinal.core.JfinalxController;
import com.jfinal.plugin.activerecord.Page;

/**
 * BlogController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@Controller
@Scope("prototype")//切记这里的多例标记 非常重要 除非共享controller 否则都建议使用单例 
public class TestController extends JfinalxController {
	
	
	
	@Reference(version="1.0.0")
	private BlogService blogService;
	public void index() {
		System.out.println(blogService);
		
		
		Page<Map<String, Object>> blog_page = blogService.findPage(fatchPn(), fatchPs(), getMap(Blog_.class),"id desc");
		
		setAttr("blog_page", blog_page);
		render("hello.ftl");
	}
}


