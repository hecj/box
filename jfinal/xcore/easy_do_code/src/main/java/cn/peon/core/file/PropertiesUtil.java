package cn.peon.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	/**
	 * 加载一个属性文件
	 * @return 
	 * @throws IOException 
	 */
	public static Properties load(String f) throws IOException {
		return load(new File(f));
	}
	/**
	 * 加载一个属性文件
	 * @return 
	 * @throws IOException 
	 */
	public static Properties load(File f) throws IOException {
		FileInputStream fis = new FileInputStream(f);//属性文件流  
		return loadStream( fis);
	}
	/**
	 * 加载一个属性文件流
	 * @return 
	 * @throws IOException 
	 */
	public static Properties loadStream(InputStream fis)
			throws IOException {
		Properties prop = new Properties();//属性集合对象  
		prop.load(fis);//将属性文件流装载到Properties对象中  
		fis.close();
		return prop;
	}
	/**
	 * 写入一个属性文件
	 * @throws IOException 
	 */
	public static void store(Properties prop,String f) throws IOException {
		store(prop, new File(f));
	}
	/**
	 * 写入一个属性文件
	 * @throws IOException 
	 */
	public static void store(Properties prop, File f)
			throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(f); 
		storeStream(prop, fos);
	}
	/**
	 * 写入一个属性文件流
	 * @throws IOException 
	 */
	public static void storeStream(Properties prop, FileOutputStream fos)
			throws IOException {
		prop.store(fos, "no comment"); 
		fos.flush();
		fos.close();
	}
	public static void main(String[] args) throws IOException {
		String f = "D:/test/test.properties";
		Properties p = load(f);
		p.put("中文属性", "修改baseFilePath的属性值\r\n"+"sdfsdf");
		System.out.println(p.get("中文属性"));
		store(p, f);
	}
}
