package cn.codegen.main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.peon.core.file.FileUtil;
import cn.peon.core.io.StreamHelp;
import cn.peon.core.kit.TimeUtil;
import cn.peon.core.log.LOG;
import cn.peon.core.os.win.WinRun;

/**
 * 数据库备份
 */
public class DBbackup {

	private static String target = "192.168.0.35";
	private static String username = "root";
	private static String password = "root";
	
	//文件输出位置
	private static String outputPath = "E:/DBbackup/" + TimeUtil.cnFormat(new Date()) + "/";

	/**
	 *  备份一个库
	 * 
	 */
	public static void backup(String databaseName) {
		try {
			String cmd="mysqldump.exe -h" + target + " -u" + username + " -p" + password + " " + databaseName;
			InputStream input =WinRun.executeAndGetInput(cmd);
//			StringBuffer sb = StreamHelp.getContext(input, "UTF-8");
//
//			String context= sb.toString();
			FileOutputStream output = new FileOutputStream(outputPath + databaseName + ".sql");
			StreamHelp.streamCopy(input, output,"UTF-8");
			
		} catch (Exception e) {
			LOG.error("备份出错");
			e.printStackTrace();
		}
	}




	/**辅助获取库名称
	 * @return
	 */
	public static List<String> splitList(String k) {
		String[] list = k.split("\\,");
		return Arrays.asList(list);
	}

	public static void main(String[] args) throws IOException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		FileUtil.createDir(outputPath,false);
		
//		String initPropertiesPath="/simple/main.properties";
//		InitDBInfo in = new InitDBInfo(initPropertiesPath);
//		JDBCConnector jc = in.getConnection();
		
//		initConWithprop(in.getConfigration()); 
		
		String k="el";
//		splitList(k);
		List<String> list =splitList(k);// jc.getAllDatabaseNames();
//		jc.close();
		
		for (String databaseName : list) {
			backup(databaseName);
			LOG.info(databaseName+" 备份完毕");
		}

		WinRun.open(outputPath);
	}

//
//	/**适配初始化
//	 * @param configration
//	 */
//	private static void initConWithprop(Configration configration) {
//			target=configration.getJdbcurl().split("//")[1].split(":")[0];
//			username=configration.getJdbcusername();
//			password=configration.getJdbcpassword();
//	}


}
