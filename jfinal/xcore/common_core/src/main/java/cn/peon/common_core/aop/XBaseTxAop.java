//package cn.peon.common_core.aop;
//
//import java.sql.Connection;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Set;
//import java.util.TreeSet;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Pointcut;
//
//import cn.peon.common_core.aop.an.X_TX;
//
//import com.jfinal.kit.StrKit;
//import com.jfinal.log.Logger;
//import com.jfinal.plugin.activerecord.ActiveRecordException;
//import com.jfinal.plugin.activerecord.Config;
//import com.jfinal.plugin.activerecord.DbKit;
//import com.jfinal.plugin.activerecord.NestedTransactionHelpException;
//import com.jfinal.plugin.activerecord.SqlReporter;
//
///**
// * 数据库事务切面
// * 现在一直一个切面的bug  就是如果在子类中调用父类方法 会导致切面拦截失效
// * @author Bird
// *
// */
//public class XBaseTxAop {
//	
//	/**
//	 * 事务开关 默认是关闭的
//	 */
//	protected boolean openTx=false;
//	
//	public void setOpenTx(boolean openTx) {
//		this.openTx = openTx;
//	}
//
//	private static final Logger log = Logger.getLogger(XBaseTxAop.class);
//	
//	/**公用事务切点
//	 * @param an
//	 */
//	@Pointcut(value="execution(* cn.peon.common_core.core.*.*(..)) && @annotation(an)",argNames="an")
//	protected void baseTxMethod(X_TX an){}//定义一个切入点
//	/**公用删除缓存切点
//	 * @param an
//	 */
// 
//	protected int getTransactionLevel(Config config) {
//		return config.getTransactionLevel();
//	}
//	
//	public Object baseTxAop(ProceedingJoinPoint pjp,X_TX an) throws Throwable{
//		
//		long time_tx_start = System.currentTimeMillis();
////		try {
////			log.info("事务执行开始");
////		} catch (Exception e) {
////		}
//		
//		//获取数据库名称
//		String dbs = an.dbs();
//		String[] dbsz=null;
//		
//		if (StrKit.notBlank(dbs)) {
//			dbsz = dbs.split("[\\,]");
////			log.info("使用具体连接配置："+dbs);
////			for (String db : dbsz) {
////				
//////				RunHandler r=new RunHandler() {
//////					
//////					@Override
//////					public void handle() {
//////						IAtom atom=new IAtom() {
//////							
//////							public boolean run() throws SQLException {
//////								return false;
//////							}
//////						};
//////						boolean rs = Db.use(db).tx(atom);
//////					}
//////				};
////				
////				
////			}
//		}else{
////			log.debug("使用了默认连接");
//		}
//		
//		Object object =null;
//		//处理事务
//		{
//			//开启数据库事务
//			ArrayList<Config> configs=new ArrayList<Config>();
//			if (dbsz==null||dbsz.length<=0) {//默认连接 
//				configs.add( DbKit.getConfig());
//				
//			}else {//明确配置了连接
//				if (dbsz.length==1&&"*".equals(dbsz[0])) {//所有连接
//					Collection<Config> vs = DbKit.getConfigNameToConfig().values();
//					configs=new ArrayList<Config>(vs);
//				}else {//所有指定连接
//					
//					for(String db:dbsz){
//						Config c = DbKit.getConfig(db);
//						if (c!=null) {
//							configs.add(c);
//						}else {
//							log.error("无法处理配置的事务："+db+" 数据源未找到指定配置 ，请检查!");
//						}
//					}
//					
//				}
//				
//			}
//			
//			
//			
//			
////			Connection conn = config.getThreadLocalConnection();
////			if (conn !null) {	// Nested transaction support
////				try {
////					if (conn.getTransactionIsolation() < getTransactionLevel(config))
////						conn.setTransactionIsolation(getTransactionLevel(config));
////					
////					log.debug("目标执行开始");
////					object = pjp.proceed();//执行该方法
////					log.debug("目标执行结束");
////					return object;
////				} catch (SQLException e) {
////					throw new ActiveRecordException(e);
////				}
////			}
//			
//			HashMap<String, Boolean> oldAutoCommitMap = new HashMap<String, Boolean>();
//			try {
//				
//				for (Config config : configs) {//遍历配置 进行事务开启
//					
//					Boolean autoCommit = null;
//					Connection conn;
//					conn = config.getConnection();
//					autoCommit = conn.getAutoCommit();
//					config.setThreadLocalConnection(conn);
//					conn.setTransactionIsolation(getTransactionLevel(config));	// conn.setTransactionIsolation(transactionLevel);
//					conn.setAutoCommit(false);
////					log.info("设置数据库："+config.getName()+" 自动提交为false");
//					oldAutoCommitMap.put(config.getName(),autoCommit);
//				}
//				
//				long time_process_start = System.currentTimeMillis();
////				log.info("目标执行开始");
//				object = pjp.proceed();//执行对象方法
//				log.info("stat:方法执行结束("+pjp.getSignature()+"),共消耗:"+(System.currentTimeMillis()-time_process_start)+"毫秒");
//				
//				for (Config config : configs) {//遍历配置 连接关闭
//					Connection conn;
//					conn = config.getConnection();
//					conn.commit();
////					log.info("关闭连接:"+config.getName());
//				}
//			} catch (NestedTransactionHelpException e) {
//				for (Config config : configs) {//遍历配置 进行事务回滚
//					Connection conn;
//					conn = config.getConnection();
//					if (conn != null) try {conn.rollback();} catch (Exception e1) {e1.printStackTrace();}
//				}
//				
//			} catch (Throwable t) {
//				for (Config config : configs) {//遍历配置 进行事务回滚  mark 这里其实可以根据异常类型来决定是否进行回滚和业务处理，容错机制
//					Connection conn;
//					conn = config.getConnection();
//					if (conn != null) try {conn.rollback();} catch (Exception e1) {e1.printStackTrace();}
//				}
//				log.error("事务处理中出现异常("+pjp.getSignature()+"),事务回滚完毕");
//				throw new ActiveRecordException(t);
//			}
//			finally {
//				try {
//					for (Config config : configs) {//遍历配置 关闭所有连接
//						Connection conn;
//						conn = config.getConnection();
//						if (conn != null) {
//							Boolean autoCommit=oldAutoCommitMap.get(config.getName());
//							if (autoCommit != null)
//								conn.setAutoCommit(autoCommit);
//							conn.close();
//						}
//					}
//				} catch (Throwable t) {
//					t.printStackTrace();	// can not throw exception here, otherwise the more important exception in previous catch block can not be thrown
//				}
//				finally {
//					
//					for (Config config : configs) {//遍历配置 进行事务threadlocal连接移除
//						config.removeThreadLocalConnection();	// prevent memory leak
//					}
//				}
//				configs=null;
//			}
//			
//			try {
//				log.info("stat:事务执行结束("+pjp.getSignature()+"),共消耗:"+(System.currentTimeMillis()-time_tx_start)+"毫秒");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//		}
//		
//		return object;
//	}
//	
// 
//}
