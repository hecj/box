package com.demo.test.service.impl;

import cn.peon.common_core.core.BaseMapServiceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.test.api.BlogService;
import com.demo.test.api.Blog_;

@Service(version="1.0.0") 
public class BlogServiceImpl extends BaseMapServiceImpl implements BlogService  {

	public String getTname() {
		return Blog_._table_name;
	}

 

}
