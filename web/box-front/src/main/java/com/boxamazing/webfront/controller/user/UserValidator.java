package com.boxamazing.webfront.controller.user;

import com.boxamazing.service.user.model.User;
import com.jfinal.core.JfinalxController;
import com.jfinal.validate.Validator;

/**
 * UserValidator.
 */
public class UserValidator extends Validator {

    protected void validate(JfinalxController controller) {
        validateRequiredString("user.id", "userid_msg", "请输入userid(网站用户ID)!");
    }

    protected void handleError(JfinalxController controller) {
        controller.keepModel(User.class);

        String actionKey = getActionKey();
        if (actionKey.equals("/user/save"))
            controller.render("add.ftl");
        else if (actionKey.equals("/user/update"))
            controller.render("edit.ftl");
    }
}
