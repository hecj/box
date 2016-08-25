package com.demo.admin.m;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.demo.service.m.model.M;
/**
 * BlogValidator.
 */
public class MValidator extends Validator {
	
	protected void validate(Controller controller) {
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(M.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/m/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/m/update"))
			controller.render("edit.ftl");
	}
}
