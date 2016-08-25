package com.demo.admin.ol;

import com.jfinal.core.JfinalxController;
import com.jfinal.validate.Validator;
import com.demo.service.ol.model.Ol;
/**
 * BlogValidator.
 */
public class OlValidator extends Validator {
	
	protected void validate(JfinalxController controller) {
	}
	
	protected void handleError(JfinalxController controller) {
		controller.keepModel(Ol.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/ol/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/ol/update"))
			controller.render("edit.ftl");
	}
}
