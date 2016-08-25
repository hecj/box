package cn.peon.common_core.aop.an;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.peon.common_core.cons.PeonBaseConst;

/*
 *   自定读取义缓存
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface X_OBJ_CACHE {
	
	/**缓存key
	 * @return
	 */
	String key() default "";
	
	/**如果为true标示只读取，不更新缓存有效时间
	 * @return
	 */
	boolean only_read() default false;
	
	/**有效时间 默认为12小时，当	only_read为 false时候有效
	 * @return
	 */
	int seconds() default PeonBaseConst.DEFAULT_CACHE_SECONDS;
	
}