package com.boxamazing.admin.sys;

import com.jfinal.core.JfinalxController;
import com.jfinal.validate.Validator;
import com.boxamazing.service.qh.model.Qh;
/**
 * BlogValidator.
 */
public class QhValidator extends Validator {
	
	protected void validate(JfinalxController controller) {
	}
	
	protected void handleError(JfinalxController controller) {
		controller.keepModel(Qh.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/qh/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/qh/update"))
			controller.render("edit.ftl");
	}
}
