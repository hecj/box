package cn.peon.common_core.aop.an;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *   自定删除义缓存
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface X_REL_FLUSH {
	
	
	/**删除相关缓存key，多个用,隔开
	 * @return
	 */
	String keys() default "";
 
}