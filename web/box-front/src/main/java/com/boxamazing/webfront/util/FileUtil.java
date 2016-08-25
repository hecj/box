package com.boxamazing.webfront.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.boxamazing.webfront.controller.upload.UploadController;

/**
 * 简述：文件读写<br />
 * 由easy_to_code移植
 * 
 * @author wangchao
 * 
 */
public class FileUtil {
	
	 private static final Logger LOG = Logger.getLogger(FileUtil.class);
	/**
	 * 创建文件夹
	 * 
	 * @param s
	 * @param overMode
	 *            是否覆盖
	 * @return
	 */
	public static File createDir(String s, boolean overMode) {
		File f = new File(s);
		if (overMode) {
			delDirs(f);
		}
		if (!f.exists()) {
			f.mkdirs();
		}
		return f;
	}

	/**
	 * 覆盖性创建文件夹
	 * 
	 * @param root
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static File createDir(File root, String filename) throws IOException {
		return createDir(root, filename, true);
	}

	/**
	 * 创建文件夹
	 * 
	 * @param root
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static File createDir(File root, String filename, boolean overMode)
			throws IOException {
		return createDir(new File(root, filename).getAbsolutePath(), overMode);
	}

	/**
	 * 覆盖性创建文件
	 * 
	 * @param f
	 * @param
	 * @return
	 * @throws IOException
	 */
	public static File createFile(File dir, String fname) throws IOException {
		return createFile(dir, fname, true);
	}

	/**
	 * 创建文件
	 * 
	 * @param f
	 * @param
	 * @return
	 * @throws IOException
	 */
	public static File createFile(File dir, String fname, boolean overMode)
			throws IOException {
		return createFile(new File(dir, fname), overMode);
	}

	/**
	 * 覆盖性创建文件
	 * 
	 * @param f
	 * @param
	 * @return
	 * @throws IOException
	 */
	public static File createFile(File f) throws IOException {
		return createFile(f, true);
	}

	/**
	 * 创建文件
	 * 
	 * @param f
	 * @param overMode
	 *            是否覆盖
	 * @return
	 * @throws IOException
	 */
	public static File createFile(File f, boolean overMode) throws IOException {
		if (overMode) {
			f.delete();
		}
		File p = f.getParentFile();
		if (!p.exists()) {
			p.mkdirs();
		}
		if (!f.exists()) {
			f.createNewFile();
		}
		return f;
	}

	/**
	 * 写入内容
	 * 
	 * @param s
	 * @param f
	 * @param encode
	 * @throws IOException
	 */
	public static void writeTo(String s, File f, String encode)
			throws IOException {
		StreamHelp.writeTo(s, new FileOutputStream(f), encode);
	}

	/**
	 * 读取文件的内容，并将文件内容以字符串的形式返回。
	 * 
	 * @param fileName
	 * @param srcEncoding
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readFile(String fileName, String encode)
			throws IOException {
		File file = new File(fileName);
		return readFile(file, encode);
	}

	public static String readFile(File file, String encode)
			throws FileNotFoundException, IOException {
		if (!file.exists()) {
			LOG.error("目标文件不存在");
			return "";
		}
		if (!file.isFile()) {
			LOG.error("目标是一个文件夹");
			return "";
		}
		FileInputStream input = new FileInputStream(file);
		StringBuffer context = StreamHelp.getContext(input, encode);
		return context.toString();
	}

	/**
	 * 删除文件夹 包括内容
	 * 
	 * @param f
	 */
	public static void delDirs(File f) {
		if (f.exists()) {
			if (f.isFile()) {
				f.delete();
			} else {
				File sz[] = f.listFiles();
				if (sz.length < 1) {
					f.delete();
				} else {
					for (File file : sz) {
						delDirs(file);
					}
					f.delete();
				}
			}
		}
	}

	/**
	 * 重写内容
	 * 
	 * @param s
	 * @param f
	 * @param encode
	 * @throws IOException
	 */
	public static void reWriteTo(String s, File f, String encode)
			throws IOException {
		writeTo(s, createFile(f, true), encode);
	}

	/**
	 * 拷贝文件夹
	 * 
	 * @param src
	 * @param targetDir
	 * @throws IOException
	 */
	public static void copyDirectiory(File src, String targetDir, FileFilter fi)
			throws IOException {
		// 新建目标目录
		createDir(targetDir, false);
		// 获取源文件夹当前下的文件或目录
		File[] file = src.listFiles(fi);
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(
						new File(targetDir).getAbsolutePath() + File.separator
								+ file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				String dir1 = src.getAbsolutePath() + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(new File(dir1), dir2);
			}
		}
	}

	/**
	 * 拷贝文件夹
	 * 
	 * @param src
	 * @param targetDir
	 * @throws IOException
	 */
	public static void copyDirectiory(File src, String targetDir)
			throws IOException {
		// 新建目标目录
		createDir(targetDir, false);
		// 获取源文件夹当前下的文件或目录
		File[] file = src.listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(
						new File(targetDir).getAbsolutePath() + File.separator
								+ file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				String dir1 = src.getAbsolutePath() + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(new File(dir1), dir2);
			}
		}
	}

	/**
	 * 拷贝文件
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File targetFile)
			throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		FileOutputStream output = new FileOutputStream(targetFile);
		StreamHelp.streamCopy(input, output, null);
	}

	/**
	 * 读取Properties文件
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Properties GetProperties(String filename) {
		InputStream in = null;
		Properties props = new Properties();
		try {
			File initfile = seachFile(filename);
			if (initfile.exists()) {
				in = new FileInputStream(initfile);
			} else {
				in = FileUtil.class.getClassLoader().getResourceAsStream(
						filename);
				if (in == null) {

					in = new FileInputStream(initfile);
				}
			}
			props.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LOG.info(filename + "文件不存在");
		} catch (IOException e) {
			e.printStackTrace();
			LOG.info("属性文件读写时候出错");
		}
		return props;
	}

	/**
	 * 获取项目中的某个文件目录
	 * 
	 * @param filename
	 * @return
	 */
	public static File seachFile(String filename) {
		File initfile = new File(filename);
		if (!initfile.exists()) {
			String path = FileUtil.class.getResource("/").getFile()
					.substring(1)
					+ filename;
			initfile = new File(path);
			if (!initfile.exists()) {
				initfile = new File(initfile.getAbsolutePath().replace(
						"test-classes", "classes"));
			}
		}
		return initfile;
	}

	/**
	 * 获取文件名后缀
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExt(String filename) {
		String[] s = filename.split("\\.");
		String ext = "";
		if (s.length > 1) {
			ext = s[s.length - 1];
		}
		return ext;
	}

	/**
	 * 检查文件后缀名是否符合
	 * 
	 * @param name
	 * @param suffix
	 * @return
	 */
	public static boolean checkSuffix(String name, String suffix) {
		return null != name ? name.endsWith(suffix) : false;

	}

	/**
	 * 根据后缀结尾过滤出所有的子文件列表
	 * 
	 * @param dir
	 * @param suffix
	 * @return
	 */
	public static List<File> listAllFiles(File dir, final String suffix) {
		final ArrayList<File> li = new ArrayList<File>();
		if (!dir.exists()) {
			return null;
		} else {
			if (dir.isDirectory()) {

				File[] fl = dir.listFiles();
				for (File f : fl) {
					if (f.isFile()) {
						if (checkSuffix(f.getName(), suffix)) {
							li.add(f);
						}
					} else {
						li.addAll(listAllFiles(f, suffix));
					}
				}

			} else {
				if (checkSuffix(dir.getName(), suffix)) {
					li.add(dir);
				}
			}
			return li;
		}

	}


}
