package com.demo.admin.r;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.demo.service.r.model.R;
/**
 * BlogValidator.
 */
public class RValidator extends Validator {
	
	protected void validate(Controller controller) {
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(R.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/r/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/r/update"))
			controller.render("edit.ftl");
	}
}
