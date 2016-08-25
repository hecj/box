package com.demo.admin.tbc;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.demo.service.tbc.model.Tbc;
/**
 * BlogValidator.
 */
public class TbcValidator extends Validator {
	
	protected void validate(Controller controller) {
		validateRequiredString("tbc.tbid", "tbid_msg", "请输入tbid(模型id)!");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Tbc.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/tbc/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/tbc/update"))
			controller.render("edit.ftl");
	}
}
