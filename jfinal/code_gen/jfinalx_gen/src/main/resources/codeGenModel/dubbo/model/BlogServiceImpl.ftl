package ${var.base_pack_url}.${var.base_module_name}.service.impl;

import cn.peon.common_core.core.BaseMapServiceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import ${var.base_pack_url}.${var.base_module_name}.api.${root.fname}Service;
import ${var.base_pack_url}.${var.base_module_name}.api.${root.fname}_;


${"@"}Service(version="1.0.0") 
public class ${root.fname}ServiceImpl extends BaseMapServiceImpl implements ${root.fname}Service  {
	${"@"}Override
	public String getTname() {
		return ${root.fname}_._table_name;
	}
	

}
