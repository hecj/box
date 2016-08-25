package cn.peon.core.db.mysql.meta;
/**引出的外键列*/
public class ExportColumn {
	private String pkName;//被关联的主键名 4
	private String fkName;//外键关联名 12
	private String fkTableName;//关联表名称 7
	private String fkTableFk;//关联表的外键名 8
	
	private TableMe fkTable;//关联表对象
	
	public TableMe getFkTable() {
		return fkTable;
	}
	public void setFkTable(TableMe fkTable) {
		this.fkTable = fkTable;
	}
	public String getPkName() {
		return pkName;
	}
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
	public String getFkName() {
		return fkName;
	}
	public void setFkName(String fkName) {
		this.fkName = fkName;
	}
	public String getFkTableName() {
		return fkTableName;
	}
	public void setFkTableName(String fkTableName) {
		this.fkTableName = fkTableName;
	}
	public String getFkTableFk() {
		return fkTableFk;
	}
	public void setFkTableFk(String fkTableFk) {
		this.fkTableFk = fkTableFk;
	}
	
	
}
