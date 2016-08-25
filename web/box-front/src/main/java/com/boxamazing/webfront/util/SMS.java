package com.boxamazing.webfront.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.capinfo.crypt.Md5;

public class SMS {
	
	private static final Log log = LogFactory.getLog(SMS.class);
	private static final Pattern p = Pattern.compile("</?(A|a)(\n|.)*?>");
	
	public static void main(String[] args) {
		sendSms("hello","18518502775",0l,"01");
	}
	
	public static boolean sendSms(String content, String phone, Long userId,String type) {
		if ("02".equals(type)) {
			log.info("正在发送广告类型短信：phone=" + phone + ",content=" + content
					+ ",type=" + type + ",userId=" + userId);
		} else {
			log.info("正在发送通知类型短信：phone=" + phone + ",content=" + content
					+ ",type=" + type + ",userId=" + userId);
		}

		BufferedReader in = null;
		OutputStreamWriter out = null;
		HttpURLConnection connection = null;
		String result = "";
		URL url = null;
		try {
			Matcher m = p.matcher(content);
			content = m.replaceAll("");
			content = content.replace("[这里]", "");
			String v_servicecode = "0011"	;
			String v_mobile = phone;

			String v_content = "【多美贷】 " + content;
			v_content = v_content.replaceAll("%", "％");
			log.info("短信发送：phone:" + phone + ",content:" + v_content);

			Md5 md5 = new Md5("");
			md5.hmac_Md5(v_servicecode + v_mobile + v_content,"test");
			byte[] b = md5.getDigest();
			String v_mac = md5.stringify(b);
			
			url = new URL("http://api.yizhifubj.com/merchant/ack/serviceSmsApi.jsp");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			out = new OutputStreamWriter(connection.getOutputStream(), "GBK");
			out.write("v_servicecode=" + v_servicecode + "&v_mobile="
					+ v_mobile + "&v_mac=" + v_mac + "&v_content=" + v_content
					+ "&v_smstype=" + type);
			out.flush();
			out.close();
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "GBK"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			log.info("短信返回结果：" + result);
			if (null == userId) {
				userId = -1L;
			}

//			TSmsSendHistory sms = new TSmsSendHistory();
//			sms.setUserId(userId);
//			sms.setSendTime(new Date());
//			sms.setResult(result);
//			sms.setPhone(phone);
//			sms.setContent(v_content);
//			dao.save(sms);

			String status = "";
			Document doc = null;
			doc = DocumentHelper.parseText(result);
			Element rootElt = doc.getRootElement(); // 获取根节点
			
			@SuppressWarnings("rawtypes")
			Iterator iter = rootElt.elementIterator();
			while (iter.hasNext()) {
				Element item = (Element) iter.next();
				if (item.getName().equals("status")) {
					status = item.getText();
				}
			}

			if (null != status && !"".equals(status) && status.equals("0")) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return false;
	}
	
}
