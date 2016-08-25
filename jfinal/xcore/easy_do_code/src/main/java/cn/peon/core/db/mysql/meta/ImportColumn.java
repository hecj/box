package cn.peon.core.db.mysql.meta;
/**引入的外键列*/
public class ImportColumn {
	private String fkName;//外键关联名 12
	private String fkTableName;//外键关联表名 3
	private String fkTableKey;//外键关联表被关联主键名 4
	private String columnName;//本表引入外键名 8
	private ColumnMe column;//对应列
	
	private TableMe fkTable;//外键关联表对象
	
	public TableMe getFkTable() {
		return fkTable;
	}
	public void setFkTable(TableMe fkTable) {
		this.fkTable = fkTable;
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
	public String getFkTableKey() {
		return fkTableKey;
	}
	public void setFkTableKey(String fkTableKey) {
		this.fkTableKey = fkTableKey;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public ColumnMe getColumn() {
		return column;
	}
	public void setColumn(ColumnMe column) {
		this.column = column;
	}
	
	
}
