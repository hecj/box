package com.boxamazing.admin.sys;

import com.jfinal.core.JfinalxController;
import com.jfinal.validate.Validator;
import com.boxamazing.service.m.model.M;
/**
 * BlogValidator.
 */
public class MValidator extends Validator {
	
	protected void validate(JfinalxController controller) {
	}
	
	protected void handleError(JfinalxController controller) {
		controller.keepModel(M.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/m/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/m/update"))
			controller.render("edit.ftl");
	}
}
