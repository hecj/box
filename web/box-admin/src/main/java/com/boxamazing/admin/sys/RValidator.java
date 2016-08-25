package com.boxamazing.admin.sys;

import com.jfinal.core.JfinalxController;
import com.jfinal.validate.Validator;
import com.boxamazing.service.r.model.R;
/**
 * BlogValidator.
 */
public class RValidator extends Validator {
	
	protected void validate(JfinalxController controller) {
	}
	
	protected void handleError(JfinalxController controller) {
		controller.keepModel(R.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/r/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/r/update"))
			controller.render("edit.ftl");
	}
}
