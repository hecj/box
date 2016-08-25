package com.demo.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.demo.common.interceptor.LogInterceptor;
import com.demo.common.interceptor.UserUtil;
import com.demo.service.msg.model.Msg;
import com.demo.service.ol.model.Ol;
import com.demo.service.r.model.R;
import com.demo.service.u.model.U;
import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;

/**
 * CommonController
 */
public class CommonController extends BaseController {

	@ClearInterceptor(ClearLayer.ALL)
	public void noauth() {
		render("noauth.ftl");
	}
	@ClearInterceptor(ClearLayer.ALL)
	public void login() {
		render("login.ftl");
	}
	public void logout() throws IOException {
		UserUtil.removeU(getSession());
		getResponse().sendRedirect("/login");
	}
	@Before(LogInterceptor.class)
	@ClearInterceptor(ClearLayer.ALL)
	public void dologin() throws IOException {
		List<U> ul = U.dao.find("select * from u where u=? and p=? ",getPara("u"),getPara("p"));
		if (ul!=null&&ul.size()>0) {
			U u = ul.get(0);
			if (u.getInt("stat")==-1) {
				setAttr("msg", "您当前账户已经被禁用，请联系管理员!");
				login();
			}else {
				UserUtil.setU(u,getSession());
				getResponse().sendRedirect("/");
			}
		}else {
			setAttr("msg", "用户名/密码 有误");
			login();
		}
	}
	public void index() {
		render("main.ftl");
	}

	public void top() {
		Object uid=UserUtil.getU(getSession()).get("id");
		Msg findFirst = Msg.dao.findFirst("select count(id) from msg where tid=? and tt IS NULL",uid);
		setAttr("msg_count", findFirst);
		mm.put(uid+"",findFirst.getLong("count(id)"));
		render("top.ftl");
	}

	public void left() {
		render("left.ftl");
	}

	public void dongtai() {
//		setAttr("pg", Ol.dao._page(getParaToInt(0, 1), 10));
		setAttr("pg",  Ol.dao.paginate(getParaToInt(0, 1), 10, "select *", "from ol order by id desc"));		
		render("dongtai.ftl");
	}
	public static HashMap<String, Long> mm=new HashMap<String, Long>();
	public void getmsgcount() {
		U u = UserUtil.getU(getSession());
		String id = u.get("id")+"";
		Long cou = 0L;
		mm.get("id");
		if (!StrKit.isBlank(id)) {
			cou=mm.get(id);
		}
		if (cou==null) {
			cou=0L;
		}
		renderText(cou+"");
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
	@Override
	public String getMn() {
		return "主模块";
	}
}
