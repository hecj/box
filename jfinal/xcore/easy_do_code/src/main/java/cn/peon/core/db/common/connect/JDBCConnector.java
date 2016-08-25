package cn.peon.core.db.common.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**数据库连接器
 * @author wangchao
 *
 */
public class JDBCConnector {
	private  Connection con=null;
	/**根据配置初始化数据库连接
	 * @param prop
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private Map<String, String> prop=null;
	public JDBCConnector(Map<String, String> prop) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		if (con==null) {
			String dbname=prop.get("jdbc.dabasname");
			if (null==dbname) {
				dbname="";
			}
			String sp = "/";//分隔符
			String driverclass=prop.get("jdbc.driverclass");
			if (driverclass.indexOf("oracle")!=-1) {
				sp=":";
			}
			String url=prop.get("jdbc.url")+sp+dbname;
			String name=prop.get("jdbc.username");
			String pwd=prop.get("jdbc.password");
			Class.forName(driverclass).newInstance();   
			con = DriverManager.getConnection(url, name, pwd);
			this.prop=prop;
		}
	}
	/**获取连接对象
	 * @return
	 */
	public  Connection getcon() {
         return con; 
	}
	/**释放连接
	 * @throws SQLException
	 */
	public  void close() throws SQLException {
		con.close();
	}
	/**执行sql
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public ResultSet preparedQuery(String sql) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        PreparedStatement ps = con.prepareStatement(sql);   
        return  ps.executeQuery();   
	}
	  /**获取所有的当前数据库连接下的数据库名称列表
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<String> getAllDatabaseNames() throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		ArrayList<String> l = new ArrayList<String>();
		ResultSet rs = preparedQuery(prop.get("jdbc.showdatabases"));
		while (rs.next()) {
			String s = rs.getString(1);
			l.add(s);
		}
		return l;
	}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Map<String, String> prop=new HashMap<String, String>();
		prop.put("jdbc.url", "jdbc:mysql://42.96.145.106:3306");
		prop.put("jdbc.dabasname", "");
		prop.put("jdbc.username", "root");
		prop.put("jdbc.password", "123456");
		prop.put("jdbc.driverclass", "com.mysql.jdbc.Driver");
		prop.put("jdbc.testsql", "select 'success'");
		prop.put("jdbc.showdatabases", "show databases");
		
		JDBCConnector jc = new JDBCConnector(prop);
        ArrayList<String> l = jc.getAllDatabaseNames();
        for (String string : l) {
			System.out.println(string);
		}
        jc.close();
	}

}
