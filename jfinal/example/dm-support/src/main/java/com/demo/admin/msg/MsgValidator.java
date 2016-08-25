package com.demo.admin.msg;

import com.jfinal.core.JfinalxController;
import com.jfinal.validate.Validator;
import com.demo.service.msg.model.Msg;
/**
 * BlogValidator.
 */
public class MsgValidator extends Validator {
	
	protected void validate(JfinalxController controller) {
	}
	
	protected void handleError(JfinalxController controller) {
		controller.keepModel(Msg.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/msg/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/msg/update"))
			controller.render("edit.ftl");
	}
}
