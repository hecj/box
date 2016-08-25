package cn.peon.core.db.mysql.meta;

import java.util.HashMap;
import java.util.Properties;

import cn.peon.core.db.common.config.Configration;


/**数据库对象
 * @author wangchao
 *
 */
public class BaseMe {
	private Configration conf;
	private String productname;
	private String type;
	private String verson;
	private HashMap<String,TableMe> tablesMap;
	private String databasename;// 数据库名称
	public static Properties var;//自定义变量
	public static Properties db2jdbctypemap; //数据库对应jdbc类型映射
	public static Properties db2javatypemap; //数据库对应java类型映射
	public static Properties db2testdataMapping; //数据库对应测试数据映射
	public BaseMe() {
		
	}
	
	public String getDatabasename() {
		return databasename;
	}

	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}
	public HashMap<String, TableMe> getTablesMap() {
		return tablesMap;
	}

	public void setTablesMap(HashMap<String, TableMe> tablesMap) {
		this.tablesMap = tablesMap;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVerson() {
		return verson;
	}

	public void setVerson(String verson) {
		this.verson = verson;
	}

	public Configration getConf() {
		return conf;
	}

	public void setConf(Configration conf) {
		this.conf = conf;
	}


}
