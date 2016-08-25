package com.boxamazing.admin.user;

import com.boxamazing.service.user.model.User;
import com.jfinal.core.JfinalxController;
import com.jfinal.validate.Validator;

public class UserValidator extends Validator {

	@Override
	protected void handleError(JfinalxController arg0) {
		arg0.keepModel(User.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/user/save"))
			arg0.render("add.ftl");
		else if (actionKey.equals("/user/update"))
			arg0.render("edit.ftl");
	}

	@Override
	protected void validate(JfinalxController arg0) {
		validateRequiredString("user.username", "u_msg", "请输入user(用户名)!");
		validateRequiredString("user.password", "p_msg", "请输入p(密码)!");
		validateRequiredString("user.nickname","n_msg","请输入nickname(昵称)");
		validateRequiredString("user.status", "rid_msg", "请输入用户状态(status)!");
		validateRequiredString("user.phone", "phone_msg", "请输入phone(手机号)!");
		validateRequiredString("user.email", "email_msg", "请输入email(邮箱)!");
		validateRequiredString("user.sex","sex_msg","请选择sex(性别)");
		//validateRequiredString("user.status", "per_msg", "请输入(个人说明)!");
		validateRequiredString("user.realname", "real_msg", "请输入realname(真实姓名)!");
		validateRequiredString("user.idno", "idno_msg", "请输入idno(身份证号)!");
		
	}

}
