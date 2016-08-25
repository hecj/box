package com.boxamazing.admin.sys;

import com.jfinal.core.JfinalxController;
import com.jfinal.validate.Validator;
import com.boxamazing.service.u.model.PUser;
/**
 * BlogValidator.
 */
public class UValidator extends Validator {
	
	protected void validate(JfinalxController controller) {
		validateRequiredString("puser.username", "u_msg", "请输入u(用户名)!");
		validateRequiredString("puser.password", "p_msg", "请输入p(密码)!");
		validateRequiredString("puser.role_id", "rid_msg", "请输入rid(角色id)!");
	}
	
	protected void handleError(JfinalxController controller) {
		controller.keepModel(PUser.class,"puser");
		
		String actionKey = getActionKey();
		if (actionKey.equals("/u/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/u/update"))
			controller.render("edit.ftl");
	}
}
