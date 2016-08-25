package com.demo.test.service;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import cn.peon.common_core.aop.an.X_TX;

import com.demo.test.api.BlogService;
import com.demo.test.service.impl.BlogServiceImpl;

@Component
public class MemcachedSpringTest {

	private static final int EXP_36000 = 5;
	private ApplicationContext app;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private BlogService  blogService;

	public void test()    {
		try {
			//			System.out.println(blogService.getTname());
			
			Map<String, Object> map=blogService.findById(3L);
			map.put("id", null);
			blogService.save(map);
			
			System.out.println("---------------------------------------------------------en");
			System.out.println(blogService.findById(3L));
			System.out.println(blogService.findById(3L));
			System.out.println(blogService.findById(3L));
			System.out.println(blogService.findById(3L));
			System.out.println(blogService.findById(3L));
			System.out.println(blogService.findById(3L));
			System.out.println(blogService.findById(3L));
//			System.out.println(blogService.getClass());
//			Map<String, Object> map=blogService.findById(3L);
//			map.put("id", null);
//			blogService.save(map);
//			try {
//				blogService.saveThreeAndTxTest();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			Method m = blogService.getClass().getMethod("txtest");
			System.out.println(m.getAnnotations().length);
			if(true)
			return;
			
			// 设置/获取
			String key = "zlex";
			
			memcachedClient.set(key, EXP_36000, "set/get");
			Thread.sleep(6);
			System.out.println(memcachedClient.get(key));
			Thread.sleep(3000);
			System.out.println(memcachedClient.get(key));
			memcachedClient.set(key, EXP_36000,memcachedClient.get(key));
			Thread.sleep(3000);
			System.out.println(memcachedClient.get(key));
			memcachedClient.set(key, EXP_36000,memcachedClient.get(key));
			Thread.sleep(3000);
			System.out.println(memcachedClient.get(key)+"---af");
			
			
			for (int i = 0; i < 100; i++) {
				if(true)break;
				memcachedClient.set(key, EXP_36000, "set/get");
				System.out.println(memcachedClient.get(key));
				memcachedClient.set(key, EXP_36000, "set/get2222");
				System.out.println(memcachedClient.get(key));

				
				// 替换
				memcachedClient.replace(key, EXP_36000, "replace");
//				memcachedClient.replace(key, EXP_36000, "replace");
				System.out.println(memcachedClient.get(key));
				// 移除
				memcachedClient.delete(key);
				System.out.println(memcachedClient.get(key));
				System.out.println("end---");
				
				
			}
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
