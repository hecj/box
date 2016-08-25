package com.demo.test.service.impl;

import cn.peon.common_core.core.BaseMapServiceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.test.api.UserService;
import com.demo.test.api.User_;


@Service(version="1.0.0") 
public class UserServiceImpl extends BaseMapServiceImpl implements UserService  {
	@Override
	public String getTname() {
		return User_._table_name;
	}
	

}
