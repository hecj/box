package com.alibaba.dubbo.config.annotation;

import java.lang.reflect.Field;

import org.springframework.context.ApplicationContext;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.JfinalxController;
import com.jfinal.plugin.spring.Inject;
import com.jfinal.plugin.spring.IocInterceptor;

/**
 * IocInterceptor.
 */
public class DubboInterceptor implements Interceptor {
	
	public static ApplicationContext ctx;
	
	public void intercept(ActionInvocation ai) {
		JfinalxController controller = ai.getController();
		Field[] fields = controller.getClass().getDeclaredFields();
		for (Field field : fields) {
			Object bean = null;
			if (field.isAnnotationPresent(Reference.class)){
				String[] beanNamesForType = IocInterceptor.ctx.getBeanNamesForType(field.getType());
				if (beanNamesForType!=null&&beanNamesForType.length>0) {
					String beanname = beanNamesForType[0];
					bean = IocInterceptor.ctx.getBean(beanname);
				}
				if (bean==null) {
					bean = IocInterceptor.ctx.getBean(field.getName());
				}
			}
			try {
				if (bean != null) {
					field.setAccessible(true);
					field.set(controller, bean);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		ai.invoke();
	}
}
