package com.demo.admin.u;

import com.jfinal.core.JfinalxController;
import com.jfinal.validate.Validator;
import com.demo.service.u.model.U;
/**
 * BlogValidator.
 */
public class UValidator extends Validator {
	
	protected void validate(JfinalxController controller) {
		validateRequiredString("u.u", "u_msg", "请输入u(用户名)!");
		validateRequiredString("u.p", "p_msg", "请输入p(密码)!");
		validateRequiredString("u.rid", "rid_msg", "请输入rid(角色id)!");
	}
	
	protected void handleError(JfinalxController controller) {
		controller.keepModel(U.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/u/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/u/update"))
			controller.render("edit.ftl");
	}
}
