package cn.peon.core.db.mysql.meta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import cn.peon.core.db.common.connect.InitDBInfo;
import cn.peon.core.kit.StringUtil;


/**表对象
 * @author wangchao
 *
 */
public class TableMe {
	public final static String virtualtype="虚拟表";
	
	private String type;
	private String name;
	private BaseMe base;// 所属数据库
	private List<ColumnMe> columnlist;
	private String comment;// 注释

	List<ColumnMe> pks;// 主键集

	List<ImportColumn> iks;// 引入外键集

	List<ExportColumn> eks;// 引出外键集

	InitDBInfo initdb;
	public TableMe() {
	}
	public TableMe(InitDBInfo initdb) {
		this.initdb=initdb;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}
	/**格式化后的name
	 * @return
	 */
	public String getFname() {
//		return StringUtil.noUnderLineAndUpFirstChar(name.replaceFirst(initdb.getConfigration().getPrefix(), ""));
//		String[] s=StringExt.splitBy(name,"_");
//		String fname=s[s.length-1];
//		return fname;
		return StringUtil.uperFirstChar(name.replaceFirst(initdb.getConfigration().getPrefix(), ""));
	}
	public String getLfname() {
		return StringUtil.lowerFirstChar(getFname());
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ColumnMe> getColumnlist() {
		return columnlist;
	}

	public void setColumnlist(List<ColumnMe> columnlist) {
		this.columnlist = columnlist;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<ColumnMe> getPks() {
		return pks;
	}

	public void setPks(List<ColumnMe> pks) {
		this.pks = pks;
	}

	public List<ImportColumn> getIks() {
		return iks;
	}

	public void setIks(List<ImportColumn> iks) {
		this.iks = iks;
	}

	public List<ExportColumn> getEks() {
		return eks;
	}

	public void setEks(List<ExportColumn> eks) {
		this.eks = eks;
	}

	public BaseMe getBase() {
		return base;
	}

	public void setBase(BaseMe base) {
		this.base = base;
	}

	public String getCreateTableSqlString() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		String sq = "SHOW CREATE TABLE "
			+ name;
		ResultSet rs = initdb.getConnection().preparedQuery(sq);
		String jg = "";
		if (rs.next()) {
			jg = (rs.getString("CREATE TABLE"));
		}

		return jg;
	}

	public String getTableComment() throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		String sq = "SELECT  TABLE_COMMENT   FROM INFORMATION_SCHEMA.TABLES WHERE   table_name = '"
				+ name + "'";
		ResultSet rs = initdb.getConnection().preparedQuery(sq);
		String jg = "";
		if (rs.next()) {
			jg = (rs.getString("TABLE_COMMENT"));
		}
		return jg;

	}

}
