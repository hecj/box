package cn.peon.core.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

import cn.peon.core.kit.StringUtil;
import cn.peon.core.kit.TimeUtil;

public class LOG {
	private static String log_file="";//要输出日志的文件夹
	private static boolean log_out=true;
//	private static String layout=innerPrifixString+"L--["+innerPrifixString+"D]:"+innerPrifixString+"M";
	private static String layout="%L--[%D]:%M";
	private static ArrayList<String> layout_units=new ArrayList<String>();
	private static File logfiles=null;//日志文件夹
	private static String currentLOG_CLASS_PATH=LOG.class.getName();  
	private static String levels="info,debug,warn,error";
	private static String ALLLEVELS="info,debug,warn,error";
	private static Map<String, Boolean> levelmap=new HashMap<String, Boolean>();
	private static FileOutputStream logfileout=null;
	private static SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	private static String day=df.format(new Date());//当每次天数改变时候 就分割文件
	private static Vector<String> msgs=new Vector<String>();//日志消息队列
	
	private static ReentrantLock loglock=new ReentrantLock();//日志写入写出锁
	private static boolean init_flag=false;//是否已经初始化标记
	private static File target_file=null;//当前目标log文件
//	private static file
	static{
		initsupportLevels();
	}
public static void init2() {
	init_flag=true;
	if (!StringUtil.isNullOrEmpty(log_file)) {
		createLogDirs();
		checkAndCreateLogFileOutStream();
	}
	
	layout_units.clear();
//	initsupportLevels();
	initlayoutunits();
//	checkStream();
}
private static void checkAndCreateLogFileOutStream() {
	String stm = TimeUtil.cnFormat(new Date());
	target_file = new File(log_file,stm.replaceAll("\\s+", "_")+".log");
	if (target_file.exists()&&target_file.isFile()) {
		
	}else {
		try {
			target_file.createNewFile();
			logfileout=new FileOutputStream(target_file);
		} catch (IOException e) {
			System.err.println("创建日志文件失败"+stm+".log");
			e.printStackTrace();
		}
	}
}
	/**使用map初始化
	 * @param p
	 */
	public static void init(Map<String, String> p) {
		init_flag=true;
//			log.file=E://test/
//			log.out=true
//			log.layout=%L--[%D](%S):%M
		log_file=p.get("log.file");
		log_out="true".equals(p.get("log.out"));
		layout=p.get("log.layout");
		levels=p.get("log.levels");
		
		if (!StringUtil.isNullOrEmpty(log_file)) {
			createLogDirs();
		}
		
		layout_units.clear();
//		initsupportLevels();
		initlayoutunits();
		checkStream();
//		LOG.info("日志配置成功");
	}
	private static void createLogDirs() {
		if (!StringUtil.isNullOrEmpty(log_file)) {
			logfiles = new File(log_file);
			if (!logfiles.exists()) {
				logfiles.mkdirs();
			}
		}
	}
	/**
	 * 确保流正常
	 */
	private static void checkStream() {
		if (!StringUtil.isNullOrEmpty(log_file)) {
			if (!init_flag) {
				init2();
				init_flag=true;
			}
			if (null!=logfileout) {
				checkAndCreateLogFileOutStream();
			}
		}
		
	}
	/**
	 * 写出内容 可以考虑使用nio
	 */
	private static void flushStream(){
		
	/*	   FileChannel fc = logfileout.getChannel();
		    //创建一个buffer并把准备写的数据填充进去;
		    ByteBuffer bb = ByteBuffer.allocate(1024);
		    //数据源
		    byte[] message = {1,2,42,'a',3};
		    bb.put(message);
		    bb.flip();
		    fc.write(bb); 
		    fc.close();*/
		try {
			if (msgs.size()>0) {
				for (String m : msgs) {
					try {
						logfileout.write((m+"\r\n").getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				logfileout.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally{
			msgs.clear();
//			target_file.setLastModified(new Date().getTime());
		}
		
	}
	
	private static void closeStream() {
		if (null!=logfileout) {
			try {
				logfileout.flush();
				logfileout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static void initsupportLevels() {
		String[] sz = ALLLEVELS.split(",");
		for (String s : sz) {
			levelmap.put(s, -1!=levels.indexOf(s));
		}
		
	}
	private static void initlayoutunits() {
		if(-1!=layout.indexOf("%L")){
			layout_units.add("%L");
		}
		if(-1!=layout.indexOf("%D")){
			layout_units.add("%D");
		}
		if(-1!=layout.indexOf("%S")){
			layout_units.add("%S");
		}
		if(-1!=layout.indexOf("%M")){
			layout_units.add("%M");
		}
//		layout=layout.replaceAll("%", innerPrifixString);//防止替换内容时候受到影响  
	}
	public static void log(Object s){
		info(s);
	}
	public static void log(Object s,String level){
		if (level.equals("")||level.equals("info")) {
			info(s);
		}else if (level.equals("warn")) {
			warn(s);
		}else if (level.equals("debug")) {
			debug(s);
		}else if (level.equals("error")) {
			error(s);
		}else {
			info(s);
		}
	}
	/**加入日志 写入 或者输出
	 * @param string
	 * @param obj
	 */
	private static void append(String level, Object obj) {
		checkInit();
		HashMap<String, String> unitvalues=new HashMap<String, String>();
		for (String lu : layout_units) {
			if(("%L").equals(lu)){
				unitvalues.put("%L", level);
			}
			if(("%D").equals(lu)){
				unitvalues.put("%D", currentTime());
			}
			if(("%S").equals(lu)){
				unitvalues.put("%S", stackinfo());
			}
			if(("%M").equals(lu)){
				unitvalues.put("%M", null==obj?"":obj.toString());
			}
		}
		String nr = rendlayout(unitvalues);
		if (log_out) {//打印输出
			System.out.println(nr);
		}
		
		if (!StringUtil.isNullOrEmpty(log_file)) {
			
			loglock.lock();
			//检查是否不是当天的日志了
			String day2=df.format(new Date());
			if (!day2.equals(day)) {
				day=day2;
				closeStream();
				checkAndCreateLogFileOutStream();
			}
			
			
			try {
				if (null!=logfiles) {//输出日志文件
					msgs.add(nr);
					flushStream();
				}
				
			}finally{
				loglock.unlock();
			}
			
			
		}
		
	}
	/**
	 * 检查是否已经初始化
	 */
	private static void checkInit() {
		if(!init_flag){
			System.out.println("检查到日志未初始化!!先初始化");
			init2();
		}
	}
	private static String rendlayout(HashMap<String, String> unitvalues) {
		String txt=layout;
		if (null!=unitvalues&&unitvalues.size()>0) {
			String msg = "";
			for (Entry<String, String> it : unitvalues.entrySet()) {
				String k = it.getKey();
				String v = it.getValue();
				if(!("%M").equals(k)){
					txt=txt.replace(k, v);
				}else {
					msg=v;
				}
			}
			txt=txt.replace("%M", msg);
		}
		return txt;
	}
	public static void debug(Object s) {
		if (levelmap.get("debug")) append("debug",s);
	}
	
	public static void warn(Object s) {
		if (levelmap.get("warn")) append("warn",s);
	}
	public static void error(Object s) {
		if (levelmap.get("error")) append("error",s);
	}
	public static void info(Object s){
		if (levelmap.get("info")) append("info",s);
	}
	
	
	 /**执行的时间 %d
	 * @return
	 */
	private static String currentTime() {
		return TimeUtil.defFormat(new Date());
	}
	/**收集 当前调用的堆栈信息  %s
	 * @param text
	 * @return
	 */
	public static String stackinfo(){  
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace(); 
		int i = getLOGstackIndex(stacks);
        StackTraceElement stackTraceElement = stacks[(i+1)];
		String callerClass = stackTraceElement.getClassName();  
        String callerMethod = stackTraceElement.getMethodName();  
        int callerMethodLine = stackTraceElement.getLineNumber();  
       return "("+callerClass+".java:"+callerMethodLine+")"+callerMethod;  
    }
	
	/**获取到当前类所在的栈序号 下一级就应该是调用者的序号了
	 * @param stacks
	 * @return
	 */
	private static int getLOGstackIndex(StackTraceElement[] stacks) {
		int ix = stacks.length-1;  
		for (;ix > 0;ix--) {
			StackTraceElement frame = stacks[ix];  
			String cname = frame.getClassName();  
			if (cname.equals(currentLOG_CLASS_PATH)) {  
				break;  
			}  
		}
		return ix; 

	}
	public static void main(String[] args) {
		log("%L--[%D]:%M"); 
	}
}
