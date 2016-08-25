package cn.peon.core.file.jar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import cn.peon.core.io.StreamHelp;
import cn.peon.core.log.LOG;
import cn.peon.core.os.win.WinRun;

public class JarUtil {
	public final static String encode = "UTF-8";
	// 利用jarInputStream生成jar文件写入内容
	public static void writeJar(String jarname,File src,String target,boolean append) throws Exception {

		// 定义一个jaroutputstream流
//		String jarname = "E://tomcat//webapps//bdlp//WEB-INF//lib//ant1.jar";
		JarOutputStream outstream = new JarOutputStream(new FileOutputStream(jarname,append));

		// jar中的每一个文件夹 每一个文件 都是一个jarEntry
		
		if (src==null||(!src.exists())) {
			LOG.error("要写入jar包的源文件不存在!!");
			return;
		}
		if (src.isFile()) {
			writefile(src, target, outstream);
		}else if (src.isDirectory()) {
			writedir(src, target, outstream);
		}
		outstream.flush();
		// 最后不能忘记关闭流
		outstream.close();
	}

	/**写入一个文件夹
	 * @param src
	 * @param target
	 * @param outstream
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void writedir(File src, String target,
			JarOutputStream outstream) throws FileNotFoundException,
			IOException {
		File[] fl = src.listFiles();
		for (File file : fl) {
			if (file.isFile()) {
				writefile(src, target+"/"+file.getName(), outstream);
			}else {
				writedir(file, target+"/"+file.getName(), outstream);
			}
		}
	}

	/**写入单文件
	 * @param src
	 * @param target
	 * @param outstream
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void writefile(File src, String target,
			JarOutputStream outstream) throws FileNotFoundException,
			IOException {
		JarEntry entry = new JarEntry(target);
		outstream.putNextEntry(entry);
		FileInputStream input=new FileInputStream(src);
		StreamHelp.streamCopy(input, outstream, true, false,null);
	}

	// 要读取jar包中某一个已知路径的文件内容

	// 像上面一样 name 相当于路径 比如 bang/bb.xml

	public static StringBuffer getContent(String jarname,String name) throws Exception {
		JarFile file = new JarFile(jarname);
		ZipEntry entry = file.getEntry(name);
		InputStream input = file.getInputStream(entry);
		return StreamHelp.getContext(input, encode);
	}
	public static void main(String[] args) throws Exception {
		String jarname = "E://test//test.jar";
		File src=new File("D://data");
		String target = "hello";
		writeJar(jarname, src, target,true);
		WinRun.open("E:\\test");
	}

}
