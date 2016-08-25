package com.demo.admin.qo;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.demo.service.qo.model.Qo;
/**
 * BlogValidator.
 */
public class QoValidator extends Validator {
	
	protected void validate(Controller controller) {
		validateRequiredString("qo.qid", "qid_msg", "请输入qid(关联查询id)!");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Qo.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/qo/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/qo/update"))
			controller.render("edit.ftl");
	}
}
