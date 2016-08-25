package com.demo.web.index;

import org.springframework.stereotype.Component;

import com.jfinal.core.JfinalxController;

/**
 * IndexController
 */
@Component
public class IndexController extends JfinalxController {
	public void index() {
		render("index.ftl");
	}
	
}



