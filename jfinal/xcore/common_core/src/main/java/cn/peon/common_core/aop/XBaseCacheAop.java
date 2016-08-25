//package cn.peon.common_core.aop;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.concurrent.TimeoutException;
//
//import net.rubyeye.xmemcached.GetsResponse;
//import net.rubyeye.xmemcached.MemcachedClient;
//import net.rubyeye.xmemcached.exception.MemcachedException;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//
//import cn.peon.common_core.aop.an.X_OBJ_CACHE;
//import cn.peon.common_core.aop.an.X_OBJ_FLUSH;
//import cn.peon.common_core.aop.an.X_REL_FLUSH;
//import cn.peon.common_core.aop.an.X_REL_CACHE;
//import cn.peon.common_core.core.BaseMapDao;
//import cn.peon.common_core.core.BaseMapService;
//import cn.peon.common_core.core.BaseMapServiceImpl;
//import cn.peon.common_core.util.SortJsonKit;
//
//import com.jfinal.kit.StrKit;
//import com.jfinal.log.Logger;
//import com.jfinal.plugin.activerecord.SqlReporter;
//
///**
// * 缓存事务切面
// * 	obj 对象缓存
// *  rel 关联缓存
// * 现在一直一个切面的bug  就是如果在子类中调用父类方法 会导致切面拦截失效
// * @author Bird
// *
// */
//public class XBaseCacheAop {
//	/**
//	 * 关联key对应
//	 */
//	public static final String ONLY_MAPPING_KEY="ONLY_MAPPING_KEY_";
//	/**
//	 * 缓存开关 默认是关闭的
//	 */
//	protected boolean openCache=false;
//	
//	public void setOpenCache(boolean openCache) {
//		this.openCache = openCache;
//	}
//	
//	@Autowired
//	private MemcachedClient memcachedClient;
//	private int TryTime=10;//默认尝试原子操作10次 如果仍然失败 就只能报error了
//	
//	private static final Logger log = Logger.getLogger(XBaseCacheAop.class);
//	
//	/**公用OBJ删除缓存切点
//	 * @param an
//	 */
//	@Pointcut(value="execution(* cn.peon.common_core.core.*.*(..)) && @annotation(an)",argNames="an")
//	protected void baseObjFlushMethod(X_OBJ_FLUSH an){}
//	/**公用OBJ读取缓存切点
//	 * @param an
//	 */
//	@Pointcut(value="execution(* cn.peon.common_core.core.*.*(..)) && @annotation(an)",argNames="an")
//	protected void baseObjCacheMethod(X_OBJ_CACHE an){}
//	/**公用REL删除缓存切点
//	 * @param an
//	 */
//	@Pointcut(value="execution(* cn.peon.common_core.core.*.*(..)) && @annotation(an)",argNames="an")
//	protected void baseRelFlushMethod(X_REL_FLUSH an){}
//	/**公用REL读取缓存切点
//	 * @param an
//	 */
//	@Pointcut(value="execution(* cn.peon.common_core.core.*.*(..)) && @annotation(an)",argNames="an")
//	protected void baseRelCacheMethod(X_REL_CACHE an){}
// 
//	/**读取REL切面注解处理
//	 * @param pjp
//	 * @param an
//	 * @return
//	 * @throws Throwable
//	 */
//	public Object baseRelCacheAop(ProceedingJoinPoint pjp,X_REL_CACHE an) throws Throwable{
//		long time_tx_start = System.currentTimeMillis();
////		try {
////			log.info("读取缓存切面执行开始");
////		} catch (Exception e) {
////		}
//		long time_process_start = System.currentTimeMillis();
////		log.info("目标执行开始");
//		Object obj_cache =null;
//		String cachekey = null;
//		try {
//			String key = an.keys();//关联的key
//			key = getCacheKeyPrefix(pjp.getThis(), pjp.getSignature(), key);//检测是否是默认key配置
//			cachekey = getCacheKey(pjp.getThis(),pjp.getSignature(),pjp.getArgs(), key);
//			
//			//检查缓存中是否有结果
//			obj_cache = memcachedClient.get(cachekey);//cache中的结果
//			
//			
//			
//			String[] ks = key.split(",");
//			for (String k:ks) {//如果是多个key就要分开操作
//				if (!StrKit.isBlank(k)) {
//					addKeyMappingForTry(k,cachekey);//添加到key的map中，保证原子操作
//				}
//			}
//			
//			
//		} catch (Exception e) {
//			log.error("获取缓存时候出错:"+cachekey);
//			e.printStackTrace();
//		}
//		
//		
//		
//		Object object =null;
//		if (obj_cache!=null) {
//			log.info("缓存中有结果==>完整的key为："+cachekey+">>,内容为："+obj_cache);
//			object=obj_cache;
//		}else {
//			object = pjp.proceed();//执行对象方法
//			log.info("缓存中无结果==>完整的key为："+cachekey+">>,数据库查出内容为："+object);
//		}
//		
//		
//		
//		if (!an.only_read()) {//如果不是只读模式 就更新一下缓存内容
//			if (obj_cache!=null) {//如果缓存中已经有了 就使用add 
//				memcachedClient.add(cachekey, an.seconds(), object);
//			}else {//没有内容 使用set设置
//				memcachedClient.set(cachekey, an.seconds(), object);
//				
//			}
//		}
//		
//		log.info("stat:方法执行结束("+pjp.getSignature()+"),共消耗:"+(System.currentTimeMillis()-time_process_start)+"毫秒");
//		
//		try {
//			log.info("stat:读取缓存切面执行结束("+pjp.getSignature()+"),共消耗:"+(System.currentTimeMillis()-time_tx_start)+"毫秒");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return object;
//	}
//	
//	
//	/**添加到缓存的keymapping操作中 多次尝试
//	 * @param key
//	 * @param cachekey
//	 * @return 
//	 */
//	private boolean addKeyMappingForTry(String key, String cachekey) {
//		
//		initKeyMapping(key);//先初始化一下 防止报错
//		
//		int i = 0;
//		while (  i < TryTime ) {//多次尝试原子操作添加keymaping
//			i++;
//			if(casSetKeyMapping(key, cachekey)){
////				log.info(""+key+":"+cachekey+"  映射添加成功");
//				return true;
//			}else {
//				log.error("第"+i+"次尝试失败，"+key+":"+cachekey+"  映射添加失败了");
//			}
//			
//		}
//		log.error(""+key+":"+cachekey+"  映射添加最终失败了");
//		return false;
//	}
//	
//	
//	/**初始化方法 防止报错
//	 * @param key
//	 * @param cachekey
//	 */
//	private void initKeyMapping(String key ) {
//		try {
//			memcachedClient.add(ONLY_MAPPING_KEY+key, 0, new HashSet<String>());
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//	/**原子添加key mapping映射
//	 * @param key
//	 * @param cachekey
//	 * @return 
//	 * @throws TimeoutException
//	 * @throws InterruptedException
//	 * @throws MemcachedException
//	 */
//	private boolean casSetKeyMapping(String key, String cachekey) {
//		try {
//			GetsResponse<HashSet<String>> result = memcachedClient.gets(ONLY_MAPPING_KEY+key);  
//			long cas = result.getCas();  
//			HashSet<String> value = result.getValue();
//			value.add(cachekey);
//			// 尝试将a的值更新为2  
//			if (!memcachedClient.cas(ONLY_MAPPING_KEY+key, 0, value, cas)) {  
//				return false;
//			}else {
//				return true;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	
//	}
//	
//	
//	/**获取缓存key
//	 * @param obj
//	 * @param signature
//	 * @param args
//	 * @param key
//	 * @return
//	 */
//	private String getCacheKey(Object obj,Signature signature,Object[] args, String key) {
//		String suf = getCacheKeySuffix(signature, args);
//		return "{"+key+"}"+suf;
//	}
//	
//	/**获取OBJ缓存key
//	 * @param obj
//	 * @param signature
//	 * @param args
//	 * @param key
//	 * @return
//	 */
//	private String getObjCacheKey( Object[] args, String key) {
//		String suf = "";;
//		if (args!=null&&args.length>0) {
//			suf=String.valueOf(args[0]);
//		}else {
//			log.error("获取obj缓存key出错：方法参数第一个必须为id");
//		}
//		
//		return "{"+key+"}"+suf;
//	}
//	
//	
//	/**获取缓存key前缀
//	 * @param obj
//	 * @param signature
//	 * @param key
//	 * @return
//	 */
//	private String getCacheKeyPrefix(Object obj, Signature signature, String key) {
//		if (StrKit.isBlank(key)) {//如果没有设置关联key 则使用默认key
//			if (obj instanceof BaseMapService) {
//				key=((BaseMapServiceImpl)obj).getTname();
//			}else if (obj instanceof BaseMapDao) {
//				key=((BaseMapDao)obj).getTname();
//			}else{
//				log.error(signature.toString()+"未配置缓存的key");
//			}
//		}
//		return key;
//	}
//	
//	
//	/**获取缓存key后缀
//	 * @param signature
//	 * @param args
//	 * @return
//	 */
//	private String getCacheKeySuffix(Signature signature, Object[] args) {
//		String cachekey;
//		cachekey = "";//缓存的关联key结构
//		
//		cachekey+=signature.toString();//追加方法签名
//		
//		cachekey+=SortJsonKit.toJson(args);//追加参数json
//		
//		cachekey=StringUtils.trimAllWhitespace(cachekey);
//		return cachekey;
//	}
//	
//	/**获取OBJ缓存key后缀
//	 * @param signature
//	 * @param args
//	 * @return
//	 */
//	private String getObjCacheKeySuffix( Object[] args) {
//		return StringUtils.trimAllWhitespace(SortJsonKit.toJson(args));//返回参数json
//	}
//	
//	/**删除REL切面注解处理
//	 * @param pjp
//	 * @param an
//	 * @return
//	 * @throws Throwable
//	 */
//	public Object baseRelFlushAop(ProceedingJoinPoint pjp,X_REL_FLUSH an) throws Throwable{
//		long time_tx_start = System.currentTimeMillis();
////		try {
////			log.info("删除缓存切面执行开始");
////		} catch (Exception e) {
////		}
//		long time_process_start = System.currentTimeMillis();
////		log.info("目标执行开始");
//		Object object = null;
//		try {
//			object = pjp.proceed();//执行对象方法
//		} catch (Exception e) {
//			throw e;
//		}finally{
//			
//			String keys = an.keys();
//			if (!StrKit.isBlank(keys)) {
//				String[] ks = keys.split("[\\,]");
//				for (String key : ks) {//循环刷新cache
//					if (StrKit.isBlank(key)) {
//						log.error("无法刷新缓存，未配置对应的key："+pjp.getSignature().toString()+",keys:"+keys);
//					}else {
//						deleteKeyMappingForTry(key);
//					}
//				}
//			}else {
//				String key = getCacheKeyPrefix(pjp.getThis(), pjp.getSignature(),"" );
//				if (StrKit.isBlank(key)) {
//					log.error("无法刷新缓存，未配置对应的key："+pjp.getSignature().toString());
//				}else {
//					deleteKeyMappingForTry(key);
//				}
//			}
//			
//			
//			
//			
//		}
//		log.info("stat:方法执行结束("+pjp.getSignature()+"),共消耗:"+(System.currentTimeMillis()-time_process_start)+"毫秒");
//		
//		try {
//			log.info("stat:删除REL缓存切面执行结束("+pjp.getSignature()+"),共消耗:"+(System.currentTimeMillis()-time_tx_start)+"毫秒");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return object;
//	}
//	
//	
//	/**尝试删除一个key mapping
//	 * @param key
//	 * @return 
//	 */
//	private HashSet<String> deleteKeyMappingForTry(String key) {
//		initKeyMapping(key);//先初始化一下 防止报错
//		HashSet<String> hs = new HashSet<String>();
//		try {
//			hs = memcachedClient.get(ONLY_MAPPING_KEY+key);
//			memcachedClient.delete(ONLY_MAPPING_KEY+key);
//			
////			log.info(key+"的所有关联key为："+hs);
//			
//			for (String k: hs) {
//				memcachedClient.delete(k);
//			}
//			log.info("删除所有 "+key+" 关联的缓存完毕");
//		} catch (Exception e) {
//			log.error("删除key mapping 失败："+key);
//			e.printStackTrace();
//			
//		}
//		return hs;
//	}
//	
//	
//	
//	
//	/**读取OBJ切面注解处理
//	 * @param pjp
//	 * @param an
//	 * @return
//	 * @throws Throwable
//	 */
//	public Object baseObjCacheAop(ProceedingJoinPoint pjp,X_OBJ_CACHE an) throws Throwable{
//		long time_tx_start = System.currentTimeMillis();
////		try {
////			log.info("读取OBJ缓存切面执行开始");
////		} catch (Exception e) {
////		}
//		long time_process_start = System.currentTimeMillis();
////		log.info("目标执行开始");
//		Object obj_cache =null;
//		String cachekey = null;
//		try {
//			String key = an.key();//关联的key
//			key = getCacheKeyPrefix(pjp.getThis(), pjp.getSignature(), key);//检测是否是默认key配置
//			cachekey = getObjCacheKey(pjp.getArgs(), key);
//			
//			//检查缓存中是否有结果
//			obj_cache = memcachedClient.get(cachekey);//cache中的结果
//			
//			
//			
//		} catch (Exception e) {
//			log.error("获取缓存时候出错:"+cachekey);
//			e.printStackTrace();
//		}
//		
//		
//		
//		Object object =null;
//		if (obj_cache!=null) {
//			log.info("obj缓存中有结果("+pjp.getSignature()+")==>完整的key为："+cachekey+">>,内容为："+obj_cache);
//			object=obj_cache;
//		}else {
//			object = pjp.proceed();//执行对象方法
//			log.info("obj缓存中无结果("+pjp.getSignature()+")==>完整的key为："+cachekey+">>,数据库查出内容为："+object);
//		}
//		
//		
//		
//		if (!an.only_read()) {//如果不是只读模式 就更新一下缓存内容
//			if (obj_cache!=null) {//如果缓存中已经有了 就使用add 
//				memcachedClient.add(cachekey, an.seconds(), object);
//			}else {//没有内容 使用set设置
//				memcachedClient.set(cachekey, an.seconds(), object);
//				
//			}
//		}
////		log.info("添加对象缓存完毕:"+cachekey);
//		
//		log.info("stat:方法执行结束("+pjp.getSignature()+"),共消耗:"+(System.currentTimeMillis()-time_process_start)+"毫秒");
//		
//		try {
//			log.info("stat:读取Obj缓存切面执行结束("+pjp.getSignature()+"),共消耗:"+(System.currentTimeMillis()-time_tx_start)+"毫秒");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return object;
//	}
//	
//	
//	/**删除REL切面注解处理
//	 * @param pjp
//	 * @param an
//	 * @return
//	 * @throws Throwable
//	 */
//	public Object baseObjFlushAop(ProceedingJoinPoint pjp,X_OBJ_FLUSH an) throws Throwable{
//		long time_tx_start = System.currentTimeMillis();
////		try {
////			log.info("删除缓存切面执行开始");
////		} catch (Exception e) {
////		}
//		long time_process_start = System.currentTimeMillis();
////		log.info("目标执行开始");
//		Object object = null;
//		try {
//			object = pjp.proceed();//执行对象方法
//		} catch (Exception e) {
//			throw e;
//		}finally{
//			
//			String key = an.key();
//			String cachekey = getObjCacheKey(pjp.getArgs(), key);
//			if (StrKit.isBlank(key)) {
//				key = getCacheKeyPrefix(pjp.getThis(), pjp.getSignature(),"" );
//			}
//			if (StrKit.isBlank(key)) {
//				log.error("无法刷新OBJ缓存，未配置对应的key："+pjp.getSignature().toString());
//			}else {
//				memcachedClient.delete(cachekey);//删除对象缓存
////				log.info("删除对象缓存完毕:"+cachekey);
//			}
//			
//			
//			
//		}
//		log.info("stat:方法执行结束("+pjp.getSignature()+"),共消耗:"+(System.currentTimeMillis()-time_process_start)+"毫秒");
//		
//		try {
//			log.info("stat:删除缓存切面执行结束("+pjp.getSignature()+"),共消耗:"+(System.currentTimeMillis()-time_tx_start)+"毫秒");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return object;
//	}
//	
//	 
//}
