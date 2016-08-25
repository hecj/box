package cn.peon.core.db.common.config;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import cn.peon.core.file.FileUtil;

/**
 * properties文件读取
 * @author wangchao
 *
 */
public class ConfigReader {
	private Properties properties=null;
	private String filename="";
	public ConfigReader(String filename){
		this.filename=filename;
		System.out.println(filename);
		properties=FileUtil.GetProperties(filename);
	}
	/**获取属性properties
	 * @return
	 */
	public Properties getProperties() {
		return properties;
	}
	/**获取属性map格式的properties
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, String> getConfig() {
		return new HashMap(properties);
	}
	/**从properties文件中读取一个Configration
	 * @return
	 */
	public Configration readConfigration() {
		Configration cfg = new Configration();
		Properties p = null;
		p = new ConfigReader(this.filename).getProperties();
		cfg.setJdbcurl(p.getProperty("jdbc.url"));
		cfg.setJdbcdabasename(p.getProperty("jdbc.dabasname"));
		cfg.setJdbcusername(p.getProperty("jdbc.username"));
		cfg.setJdbcpassword(p.getProperty("jdbc.password"));
		
		cfg.setOutputbaseurl(p.getProperty("output.baseurl"));
		cfg.setTemplateurl(p.getProperty("template.url"));
		cfg.setTemplateencoding(p.getProperty("template.encoding"));
		
		cfg.setTablenames(p.getProperty("table.names"));
		cfg.setTablevirtualTables(p.getProperty("table.virtualTables"));
		cfg.setTablemode(p.getProperty("table.mode"));
		
		
		cfg.setDb2jdbcMapping(p.getProperty("jdbc.db2jdbcMapping"));
		cfg.setDb2javaMapping(p.getProperty("jdbc.db2javaMapping"));
		cfg.setDb2testdataMapping(p.getProperty("jdbc.db2testdataMapping"));
		cfg.setVar(p.getProperty("var"));
		cfg.setPrefix(p.getProperty("prefix"));
		return cfg;
	}
	public static void main(String[] args) throws IOException, URISyntaxException {
		Properties p = new ConfigReader("/simple/main.properties").getProperties();
		Set<Object> tkeys = p.keySet();
		for (Object k:tkeys) {
			System.out.println(k);
		}
		Map<String, String> hm = new ConfigReader("/simple/main.properties").getConfig();
		System.out.println(hm.get(hm.keySet().iterator().next()));
		System.out.println(hm.values());
	}
}
