package com.boxamazing.webfront.controller.image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.boxamazing.webfront.common.BaseController;
import com.boxamazing.webfront.util.ImageGenerator;
import com.boxamazing.webfront.util.ResultJson;

public class ImageController extends BaseController {

	ImageGenerator imageGenerator = new ImageGenerator();

	/**
	 * 生成图片
	 * 
	 * @throws IOException
	 */
	public void index() throws IOException {
		ResultJson resultJson = new ResultJson();
		HttpServletResponse response = getResponse();
		response.reset();
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/gif");

		// 生成的图片
		BufferedImage image = imageGenerator.createImage();

		// 随机字符串
		String randString = imageGenerator.getRandString();
		// 存入session
		setSessionAttr("randString", randString);
		
		// 创建输出流
		OutputStream out = response.getOutputStream();
		// 将图片写入到输出流中去
		ImageIO.write(image, "JPG", out);

		// 强制刷新
		out.flush();
		// 关闭输出流
		out.close();
		renderNull();
	}

	/**
	 * 验证随机码
	 * 
	 * @return resultJson
	 * 
	 */
	public void verify() {
		ResultJson resultJson = new ResultJson();
		String randString = (String) getSession().getAttribute("randString");
		String randcode = getPara("rancode");
		if (!randString.equalsIgnoreCase(randcode)) {
			resultJson.setCode(404L);
		} else {
			resultJson.setCode(200L);
		}
		renderJson(resultJson);

	}
	

}
