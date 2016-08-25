package com.demo.admin.t_user;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.demo.service.t_user.model.TUser;
/**
 * BlogValidator.
 */
public class TUserValidator extends Validator {
	
	protected void validate(Controller controller) {
		validateRequiredString("t_user.vipType", "vipType_msg", "请输入vipType(1.普通会员 2.普通vip 3.白金vip 4.钻石vip)!");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(TUser.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/t_user/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/t_user/update"))
			controller.render("edit.ftl");
	}
}
