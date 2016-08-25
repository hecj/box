package com.demo.admin.q;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.demo.service.q.model.Q;
/**
 * BlogValidator.
 */
public class QValidator extends Validator {
	
	protected void validate(Controller controller) {
		validateRequiredString("q.dbid", "dbid_msg", "请输入dbid(关联数据库id)!");
		validateRequiredString("q.sql", "sql_msg", "请输入sql(sql语句)!");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Q.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/q/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/q/update"))
			controller.render("edit.ftl");
	}
}
