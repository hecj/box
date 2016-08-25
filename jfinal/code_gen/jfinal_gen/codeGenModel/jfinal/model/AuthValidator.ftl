package ${var.base_pack_url}.admin.${root.name};

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import ${var.base_pack_url}.service.${root.name}.model.${root.fname};
/**
 * BlogValidator.
 */
public class ${root.fname}Validator extends Validator {
	
	protected void validate(Controller controller) {
		<#list root.columnlist as col>
		<#if col.canNull = 0 && !col.ispk>
		validateRequiredString("${root.name}.${col.name}", "${col.name}_msg", "请输入${col.name}(${col.comment?if_exists})!");
		</#if>
		</#list>
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(${root.fname}.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/${root.name}/save"))
			controller.render("add.ftl");
		else if (actionKey.equals("/${root.name}/update"))
			controller.render("edit.ftl");
	}
}
