package cn.peon.core.db.mysql.meta;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.peon.core.db.common.connect.InitDBInfo;
import cn.peon.core.kit.StringUtil;



/**行对象
 * @author wangchao
 *
 */
public class ColumnMe {
	private String name;
	private String type;
	private String comment;//字段注释
	private int len;//长度
	private int scal;//小数位精度
	private boolean ispk;//是否是主键
	private boolean isfk;//是否是外键
	private boolean isAutonumber;//是否自动编号
	private int canNull;//是否可以为空
	private boolean canwrite;//是否可以写入
	private TableMe table;
	private InitDBInfo initdb;
	private String className;//java字段类型名称
	
public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	//	static Properties db2jdbctypemap; 
//	static Properties db2javatypemap; 
//	static Properties db2testdataMapping; 
	public ColumnMe(InitDBInfo initdb) {
		this.initdb=initdb;
//		if(db2jdbctypemap==null){
//			db2jdbctypemap = BaseMe.db2jdbctypemap;
//			db2javatypemap = BaseMe.db2javatypemap;
//			db2testdataMapping = BaseMe.db2testdataMapping;
//		}
	}
	public ColumnMe() {
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public TableMe getTable() {
		return table;
	}
	public void setTable(TableMe table) {
		this.table = table;
	}
	public String getName() {
		return name;
	}
	/**格式化后的name
	 * @return
	 */
	public String getFname() {
//		return StringUtil.noUnderLineAndUpFirstChar(name);
		return StringUtil.uperFirstChar(name);
	}
	/**格式化后的name
	 * @return
	 */
	public String getLfname() {
//		return StringUtil.lowerFirstChar(getFname());
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public String getFtype() {
//		System.out.println(type);
		return getJavaTypeByColoumnType(type);
	}
	public String getJdbctype() {
		String[] ts = type.split(" ");
		return ts[0];
	}
	public String getTestdata() {
		return BaseMe.db2testdataMapping.getProperty(type);
	}
	public String getJavatype() {
		return getJavaTypeByColoumnType(type);
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	 
	public boolean getIsAutonumber() {
		return isAutonumber;
	}
	public void setIsAutonumber(boolean isAutonumber) {
		this.isAutonumber = isAutonumber;
	}
	public int getCanNull() {
		return canNull;
	}
	public void setCanNull(int canNull) {
		this.canNull = canNull;
	}
	public boolean isCanwrite() {
		return canwrite;
	}
	public void setCanwrite(boolean canwrite) {
		this.canwrite = canwrite;
	}
	public int getScal() {
		return scal;
	}
	public void setScal(int scal) {
		this.scal = scal;
	}
	public String getJavaTypeByColoumnType(String type) {
			return BaseMe.db2javatypemap.get(type).toString();
	}
	public String getColoumnComment() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sq="SELECT   COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"+table.getName()+"'AND column_name = '"+name+"'";
        ResultSet rs = initdb.getConnection().preparedQuery(sq);   
        String jg="";
        if (rs.next()) {
        	jg=(rs.getString("COLUMN_COMMENT"));
		}
        rs.close();
    
	return  jg;
	}
	
	public Object getDefaultValue() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sq="SELECT   COLUMN_DEFAULT  FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"+table.getName()+"'AND column_name = '"+name+"'";
        ResultSet rs = initdb.getConnection().preparedQuery(sq);   
        Object defaultValue = "";
        if (rs.next()) {
        	defaultValue=(rs.getObject("COLUMN_DEFAULT"));
		}
        rs.close();
		return defaultValue;
	}
	public void setAutonumber(boolean isAutonumber) {
		this.isAutonumber = isAutonumber;
	}
	public boolean getIspk() {
		return ispk;
	}
	public void setIspk(boolean ispk) {
		this.ispk = ispk;
	}
	public boolean getIsfk() {
		return isfk;
	}
	public void setIsfk(boolean isfk) {
		this.isfk = isfk;
	}
}

