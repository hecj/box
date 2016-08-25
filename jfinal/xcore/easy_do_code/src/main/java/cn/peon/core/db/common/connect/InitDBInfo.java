package cn.peon.core.db.common.connect;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.peon.core.db.common.config.ConfigReader;
import cn.peon.core.db.common.config.Configration;
import cn.peon.core.db.mysql.meta.BaseMe;
import cn.peon.core.db.mysql.meta.ColumnMe;
import cn.peon.core.db.mysql.meta.ExportColumn;
import cn.peon.core.db.mysql.meta.ImportColumn;
import cn.peon.core.db.mysql.meta.TableMe;
import cn.peon.core.kit.StringUtil;
import cn.peon.core.log.LOG;


public class InitDBInfo {
	private Map<String, String> virtualTablesMap=new HashMap<String, String>();//虚拟表map
	private String initPropertiesPath="/main.properties";
	
	private Configration configration = new Configration();
	private JDBCConnector connection;
	private DatabaseMetaData metadata;

	public InitDBInfo(String path) {// 初始化配置文件项
		initPropertiesPath=path;
		ConfigReader cfr = new ConfigReader(initPropertiesPath);
		configration = cfr.readConfigration();
		try {
			connection=new JDBCConnector(cfr.getConfig());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**初始化数据库对象
	 * @return
	 * @throws Exception
	 */
	public BaseMe initbaseMe() throws Exception {
		metadata = connection.getcon().getMetaData();
		BaseMe bm = new BaseMe();
		bm.setDatabasename(configration.getJdbcdabasename());
		bm.setProductname(metadata.getDatabaseProductName());
		bm.setVerson(metadata.getDatabaseProductVersion());
		bm.setTablesMap(inittableMe(bm));
		bm.setConf(configration);
		
		return bm;
	}

	/**初始化数据库中的表对象
	 * @param bm
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, TableMe> inittableMe(BaseMe bm) throws Exception {
		HashMap<String, TableMe> tmap=new HashMap<String, TableMe>();
		ResultSet rs1 = metadata.getTables(bm.getDatabasename(), null, null,	null);
		
		String tnames[] = getConfigration().getTablenames().split(",");
		while (rs1.next()) {
			if ("TABLE".equals(rs1.getString(4))) {//是否只要表 排除视图
				
				TableMe tm = new TableMe(this);
				tm.setBase(bm);
				tm.setName(rs1.getString(3));
				tm.setType(rs1.getString(4));
				tm.setComment(tm.getTableComment());
				if (getConfigration().getTablenames().matches("\\*")) {
					tmap.put(tm.getName(),tm);
				} else {
					for (String string : tnames) {
						if (string.equals(tm.getName())) {
							tmap.put(tm.getName(),tm);
						}
					}
				}
				
				LOG.info("获取了表："+tm.getName()+"---"+tm.getType());
				
			}
		}
		bm.setTablesMap(tmap);
		rs1.close();
		
		String tablevirtualTables = configration.getTablevirtualTables();
		if(!StringUtil.isNullOrEmpty(tablevirtualTables)){//添加虚拟表
			ConfigReader cf = new ConfigReader(tablevirtualTables);
			this.virtualTablesMap = cf.getConfig();
			Set<String> ks = virtualTablesMap.keySet();
			for (String key : ks) {
				TableMe tm = new TableMe(this);
				tm.setBase(bm);
				tm.setName(key);
				tm.setType(TableMe.virtualtype);//设置是虚拟表类型
				tm.setComment(virtualTablesMap.get(key));
				tmap.put(tm.getName(),tm);
				
			}
			
		}
		
		
		Set<String> tk = bm.getTablesMap().keySet();
		if (tk.size()>0) {
			has_finish_len=0;
			for (String k : tk) {
				TableMe t = bm.getTablesMap().get(k);
				
				syncColumn(t);
				LOG.info("添加了获取表字段任务："+k);
			}
		}
//		synchronized (has_finish_len) {
			do{
				Thread.sleep(1000);
				LOG.warn("等待异步任务执行完成...."+has_finish_len+"/"+tk.size());
			}while (has_finish_len<tk.size()) ;
//		}
		return tmap;
	}
	public static Integer has_finish_len = 0;//已经完成任务量
	private synchronized void has_finish_len_add() {
		LOG.info("has_finish_len++");
		has_finish_len+=1;
	}
	/**异步的设置表的结构
	 * @param t
	 * @throws Exception
	 */
	private void syncColumn(TableMe t) {
		Thread tr = new Thread( new C_R(t));
		tr.start();
	}
	private class C_R implements Runnable  {
		private TableMe t;
		public C_R(TableMe t) {
			this.t=t;
		}
		public void run() {
			try {
				LOG.info(t.getName()+"---ok--"+has_finish_len+"----start");
				synchronized (connection) {
					t.setColumnlist(initColoumn(t));
				}
//				synchronized (has_finish_len) {
				has_finish_len_add();
//				}
				LOG.info(t.getName()+"---ok--"+has_finish_len+"----end");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**初始化列对象
	 * @throws Exception */
	public List<ColumnMe> initColoumn(TableMe tm) throws Exception {
		//主键序列
		List<String> plist = Arrays.asList(new String[]{"id"});//getPrimaryKeys(tm.getName());
		List<ImportColumn> imlist = getImportedKeys(tm.getName());
		List<ExportColumn> exlist = getExportedKeys(tm.getName());

		List<ColumnMe> pks=new ArrayList<ColumnMe>();
		List<ImportColumn> iks=new ArrayList<ImportColumn>();
		List<ExportColumn> eks=new ArrayList<ExportColumn>();
		
		String sql="select * from `"
				+ tm.getName()+"` where 0=1";
		if (TableMe.virtualtype.equals(tm.getType())) {
			sql=tm.getComment().split("\\|")[1]+" limit 0,1";;
		}
		
		ResultSet rs = connection.preparedQuery(sql);
		ResultSetMetaData rsme = rs.getMetaData();

		int columnCount = rsme.getColumnCount();
		List<ColumnMe> collist = new ArrayList<ColumnMe>();
		for (int i = 1; i <= columnCount; i++) {
			ColumnMe col = new ColumnMe(this);
			col.setTable(tm);
			String className = rsme.getColumnClassName(i);
			if ("[B".equals(className)) {
				className="byte[]";
			}
			col.setClassName(className);
			col.setName(rsme.getColumnName(i));
			col.setType(rsme.getColumnTypeName(i));
			col.setLen(rsme.getPrecision(i));
			col.setScal(rsme.getScale(i));
			col.setIsAutonumber(rsme.isAutoIncrement(i));
			col.setCanNull(rsme.isNullable(i));
			col.setCanwrite(rsme.isReadOnly(i));
			col.setComment(col.getColoumnComment());
			for (String p : plist) {
				if (p.equals(col.getName())) {
					pks.add(col);
					col.setIspk(true);
					continue;
				}
			}
			for (ImportColumn im : imlist) {
				if (im.getColumnName().equals(col.getName())) {
					im.setColumn(col);
					im.setFkTable(tm.getBase().getTablesMap().get(im.getFkTableName()));
					iks.add(im);
					col.setIsfk(true);
					continue;
				}
			}
			collist.add(col);
			// System.out.println(col.getComment());
		}
		rs.close();
		
		for (ExportColumn ex : exlist) {
			ex.setFkTable(tm.getBase().getTablesMap().get(ex.getFkTableName()));
			eks.add(ex);
		}
		
		tm.setPks(pks);
		tm.setIks(iks);
		tm.setEks(eks);
		
		return collist;
	}
	
	public BaseMe initAndGetBaseMe() throws Exception {
		return initbaseMe();
	}

	public Configration getConfigration() {
		return configration;

	}

	public static void main(String[] args) throws Exception {
		System.out.println(ConfigReader.class.getResource("/simple/main.properties").getPath().substring(1));
		BaseMe d = new InitDBInfo("/simple/main.properties").initbaseMe();
		System.out.println(d.getDatabasename());
	}
	
	/**获取主键列*/
	public  List<String> getPrimaryKeys(String tableName) throws Exception {
	    ArrayList<String> keyList = new ArrayList<String>();
	    if (virtualTablesMap.containsKey(tableName)) {//是虚拟表 主键直接取出
	    	String key = virtualTablesMap.get(tableName).split("\\|")[0];
	    	keyList.addAll(Arrays.asList(key.split("\\,")));
	    	return keyList;
		}
	    ResultSet rsKey = metadata.getPrimaryKeys(null, null, tableName); //获取指定表的主键列信息
	    while (rsKey.next()){
	        keyList.add(rsKey.getString(4));
	        LOG.log(tableName+"---主键-->"+rsKey.getString(4));
	    }
	    rsKey.close();
	    return keyList;
	}
	
	/**获取import外键列*/
	public  List<ImportColumn> getImportedKeys(String tableName) throws Exception {
	    ArrayList<ImportColumn> keyList = new ArrayList<ImportColumn>();
	    ResultSet rsKey = metadata.getImportedKeys(null, null, tableName);
	    while (rsKey.next()){
	    	ImportColumn im = new ImportColumn();
	    	im.setFkName(rsKey.getString(12));//外键关联名 12
	    	im.setFkTableName(rsKey.getString(3));//外键关联表名 3
	    	im.setFkTableKey(rsKey.getString(4));//外键关联表被关联主键名 4
	    	im.setColumnName(rsKey.getString(8));//本表引入外键名 8
	    	
	        keyList.add(im);
	        LOG.log(tableName+":"+rsKey.getString(8)+"--> "+(rsKey.getString(3))+"---import外键--------"+rsKey.getString(4));
	    }
	    rsKey.close();
	    return keyList;
	}

	/**获取export外键列*/
	public  List<ExportColumn> getExportedKeys(String tableName) throws Exception {
	    ArrayList<ExportColumn> keyList = new ArrayList<ExportColumn>();
	    ResultSet rsKey = metadata.getExportedKeys(null, null, tableName);
	    while (rsKey.next()){
	    	ExportColumn ex = new ExportColumn();
	    	ex.setPkName(rsKey.getString(4)) ;//被关联的主键名 4
	    	ex.setFkName(rsKey.getString(12)) ;//外键关联名 12
	    	ex.setFkTableName(rsKey.getString(7));//关联表名称 7
	    	ex.setFkTableFk(rsKey.getString(8));//关联表的外键名 8
	        keyList.add(ex);
	        LOG.log(tableName+":"+rsKey.getString(4)+"-->"+(rsKey.getString(7))+"---export外键--------"+(rsKey.getString(8)));    
	    }
	    rsKey.close();
	    return keyList;
	}
	public String getInitPropertiesPath() {
		return initPropertiesPath;
	}
	public  DatabaseMetaData getMetadata() {
		return metadata;
	}
	public JDBCConnector getConnection() {
		return connection;
	}
	
}

