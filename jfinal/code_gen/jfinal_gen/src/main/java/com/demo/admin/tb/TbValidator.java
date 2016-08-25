package com.demo.admin.tb;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.demo.service.tb.model.Tb;
/**
 * BlogValidator.
 */
public class TbValidator extends Validator {
	
	protected void validate(Controller controller) {
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Tb.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/tb/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/tb/update"))
			controller.render("edit.ftl");
	}
}
