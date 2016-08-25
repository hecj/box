package com.boxamazing.admin.sys;

import com.jfinal.core.JfinalxController;
import com.jfinal.validate.Validator;
import com.boxamazing.service.qo.model.Qo;
/**
 * BlogValidator.
 */
public class QoValidator extends Validator {
	
	protected void validate(JfinalxController controller) {
		validateRequiredString("qo.qid", "qid_msg", "请输入qid(关联查询id)!");
	}
	
	protected void handleError(JfinalxController controller) {
		controller.keepModel(Qo.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/qo/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/qo/update"))
			controller.render("edit.ftl");
	}
}
