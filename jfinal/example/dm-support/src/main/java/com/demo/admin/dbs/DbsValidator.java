package com.demo.admin.dbs;

import com.jfinal.core.JfinalxController;
import com.jfinal.validate.Validator;
import com.demo.service.dbs.model.Dbs;
/**
 * BlogValidator.
 */
public class DbsValidator extends Validator {
	
	protected void validate(JfinalxController controller) {
	}
	
	protected void handleError(JfinalxController controller) {
		controller.keepModel(Dbs.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/dbs/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/dbs/update"))
			controller.render("edit.ftl");
	}
}
