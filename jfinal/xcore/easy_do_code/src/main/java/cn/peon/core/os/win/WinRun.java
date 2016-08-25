package cn.peon.core.os.win;

import java.io.IOException;
import java.io.InputStream;

/**
 * 简述：win系统命令耦合
 * 
 * @author wangchao
 * 
 */
public class WinRun {

	/**
	 * 执行命令返回输入流
	 * 
	 * @param cmd
	 * @return
	 * @throws IOException
	 */
	public static InputStream executeAndGetInput(String cmd) throws IOException {
		Process process = execute(cmd);
		InputStream input = process.getInputStream();
		return input;
	}

	/**
	 * 执行命令返回进程
	 * 
	 * @param cmd
	 * @return
	 * @throws IOException
	 */
	public static Process execute(String[] cmd) throws IOException {
		return Runtime.getRuntime().exec(cmd);
	}
	/**
	 * 执行命令返回进程
	 * 
	 * @param cmd
	 * @return
	 * @throws IOException
	 */
	public static Process execute(String cmd) throws IOException {
		return Runtime.getRuntime().exec(cmd);
	}
	/**打开文件夹浏览器
	 * @param path
	 * @throws IOException
	 */
	public static void open(String path) throws IOException{
		String cmd[] = { "explorer.exe", path.replaceAll("\\/", "\\\\")};
		execute(cmd);
	}
	    
}
