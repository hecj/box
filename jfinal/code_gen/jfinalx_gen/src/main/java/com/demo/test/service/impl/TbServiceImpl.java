package com.demo.test.service.impl;

import cn.peon.common_core.core.BaseMapServiceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.test.api.TbService;
import com.demo.test.api.Tb_;


@Service(version="1.0.0") 
public class TbServiceImpl extends BaseMapServiceImpl implements TbService  {
	@Override
	public String getTname() {
		return Tb_._table_name;
	}
	

}
