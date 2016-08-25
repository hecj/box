package cn.peon.core.db.common.config;

/**数据库配置文件
 * @author wangchao
 *
 */
public class Configration {
	// 数据库连接
	String jdbcurl;
	String jdbcusername;
	String jdbcpassword;
	String jdbcdabasename;

	String tablenames;
	String tablemode;
	String tablevirtualTables;//虚拟表文件路径
	
	String db2javaMapping;
	String db2jdbcMapping;
	String db2testdataMapping;
	String var;//用户自定义变量 
	String prefix;//前缀修饰
	
	String outputbaseurl;//输出文件夹路径
	String templateurl;//模板路径
	String templateencoding;//模板编码格式 

	public String getJdbcurl() {
		return jdbcurl;
	}
	public String getTablemode() {
		return tablemode;
	}

	public void setTablemode(String tablemode) {
		this.tablemode = tablemode;
	}

	public void setJdbcurl(String jdbcurl) {
		this.jdbcurl = jdbcurl;
	}

	public String getJdbcusername() {
		return jdbcusername;
	}

	public void setJdbcusername(String jdbcusername) {
		this.jdbcusername = jdbcusername;
	}

	public String getJdbcpassword() {
		return jdbcpassword;
	}

	public void setJdbcpassword(String jdbcpassword) {
		this.jdbcpassword = jdbcpassword;
	}

	public String getJdbcdabasename() {
		return jdbcdabasename;
	}

	public void setJdbcdabasename(String jdbcdabasename) {
		this.jdbcdabasename = jdbcdabasename;
	}

	public String getTablenames() {
		return tablenames;
	}

	public void setTablenames(String tablenames) {
		this.tablenames = tablenames;
	}


	public String getDb2javaMapping() {
		return db2javaMapping;
	}

	public void setDb2javaMapping(String db2javaMapping) {
		this.db2javaMapping = db2javaMapping;
	}

	public String getDb2jdbcMapping() {
		return db2jdbcMapping;
	}

	public void setDb2jdbcMapping(String db2jdbcMapping) {
		this.db2jdbcMapping = db2jdbcMapping;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getDb2testdataMapping() {
		return db2testdataMapping;
	}

	public void setDb2testdataMapping(String db2testdataMapping) {
		this.db2testdataMapping = db2testdataMapping;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getOutputbaseurl() {
		return outputbaseurl;
	}

	public void setOutputbaseurl(String outputbaseurl) {
		this.outputbaseurl = outputbaseurl;
	}

	public String getTemplateencoding() {
		return templateencoding;
	}

	public void setTemplateencoding(String templateencoding) {
		this.templateencoding = templateencoding;
	}

	public String getTemplateurl() {
		return templateurl;
	}
	public void setTemplateurl(String templateurl) {
		this.templateurl = templateurl;
	}
	public static void main(String[] args) {

	}
	public String getTablevirtualTables() {
		return tablevirtualTables;
	}
	public void setTablevirtualTables(String tablevirtualTables) {
		this.tablevirtualTables = tablevirtualTables;
	}

}
