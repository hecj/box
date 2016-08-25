package com.boxamazing.service.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.sms.model.SendSmsRecord;
import com.jfinal.kit.PropKit;

public class SmsService {

	private static Log log = LogFactory.getLog(SmsService.class);

	@SuppressWarnings("finally")
	public static boolean sendSms(String content, String phone)
			throws UnsupportedEncodingException {

		log.info("正在发送通知类型短信：phone=" + phone + ",content=" + content);
		BufferedReader in = null;
		OutputStreamWriter out = null;
		HttpURLConnection connection = null;
		String result = "";

		// 从配置文件获取
		String servicecode = PropKit.get("s_servicecode");
		String username = PropKit.get("s_username");
		String password = PropKit.get("s_password");
		String smsUrl = PropKit.get("s_url");

		String sign = PropKit.get("s_sign");
		// 如果短信模板没有 + 标志【盒子尖叫】 //需要从配置文件中获取
		if (!content.startsWith(sign)) {
			content = sign + content;
		}

		// 是否发送成功
		boolean isSend = false;
		URL url = null;
		try {
			boolean real_send = PropKit.getBoolean("sms_real_send");
			log.info("real_send:" + real_send);
			if (real_send) {
				String str = URLEncoder.encode(content, "gb2312");
				url = new URL(smsUrl + "username=" + username + "&password="
						+ password + "&phone=" + phone + "&message=" + str
						+ "&epid=" + servicecode);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setUseCaches(false);
				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				out = new OutputStreamWriter(connection.getOutputStream(),
						"gb2312");
				out.flush();
				out.close();

				in = new BufferedReader(new InputStreamReader(
						connection.getInputStream(), "gb2312"));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}

				log.info("发送短信返回内容：" + result);

				if ("00".equals(result.toString())) {
					isSend = true;
				}
			} else {
				isSend = true;
			}
		} catch (Exception e) {
			log.error("正在发送通知类型短信失败！");
			log.error(e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			SendSmsRecord sms = new SendSmsRecord();
			sms.set("user_id", StringEscapeUtils.escapeSql(phone));
			sms.set("phone", StringEscapeUtils.escapeSql(phone));
			sms.set("content", StringEscapeUtils.escapeSql(content));
			sms.set("result", result);
			sms.set("create_at", System.currentTimeMillis());
			sms.save();
			if (isSend) {
				log.info("发送成功！");
				return isSend;
			} else {
				log.error("发送失败！");
				return false;

			}

		}
	}

}