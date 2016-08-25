package com.demo.test.service.impl;

import cn.peon.common_core.core.BaseMapServiceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.test.api.TbcService;
import com.demo.test.api.Tbc_;


@Service(version="1.0.0") 
public class TbcServiceImpl extends BaseMapServiceImpl implements TbcService  {
	@Override
	public String getTname() {
		return Tbc_._table_name;
	}
	

}
