package com.demo.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;

/**
 * CommonController
 */
public class CommonController extends BaseController {

	public void index() {
		render("main.ftl");
	}

	public void top() {
		render("top.ftl");
	}

	public void left() {
		render("left.ftl");
	}

	public void right() {
		render("right.ftl");
	}

	/**
	 * 文件下载
	 */
	public void downFile() {
		File file=new File("C:\\Users\\WC\\Downloads\\new_wx(2)_20141029224317\\jfinal_gen\\WebRoot\\static\\image\\ok2.png");
		if(file.exists()) {
			renderFile(file);
		}else {
			renderText("文件不存在");
		}
	}
	/**
	 * 上传文件
	 */
	public void upFile() {
		getParaNames();
		Date date = new Date();
		String path = new SimpleDateFormat("yyyy/MM/dd").format(date);
		UploadFile file = getFile("imgFile", PathKit.getWebRootPath() + "/temp");
		File source = file.getFile();
		String fileName = file.getFileName();
		String extension = fileName.substring(fileName.lastIndexOf("."));
		String prefix;
		if (".png".equals(extension) || ".jpg".equals(extension)
				|| ".gif".equals(extension)) {
			prefix = "img";
			fileName = date.getTime() + "_" + (new Random().nextInt(10000))
					+ extension;
		} else {
			prefix = "file";
		}
		int flag = -1;
		String msg= "";
		try {
			FileInputStream fis = new FileInputStream(source);
			File targetDir = new File(PathKit.getWebRootPath() + "/static/uploadFile/" + prefix
					+ "/u/" + path);
			if (!targetDir.exists()) {
				targetDir.mkdirs();
			}
			File target = new File(targetDir, fileName);
			if (!target.exists()) {
				target.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(target);
			byte[] bts = new byte[300];
			while (fis.read(bts, 0, 300) != -1) {
				fos.write(bts, 0, 300);
			}
			fos.close();
			fis.close();
			
			flag=1;
			msg="/static/uploadFile/" + prefix + "/u/" + path + "/" + fileName;
			source.delete();
		} catch (FileNotFoundException e) {
			flag=-1;
			msg="上传出现错误，请稍后再上传";
		} catch (IOException e) {
			flag=-1;
			msg="文件写入服务器出现错误，请稍后再上传";
		}finally{
			renderHtml("<script>top.back('"+prefix+"','"+flag+"','"+msg+"')</script>");
		}
	}
}
