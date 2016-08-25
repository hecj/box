package cn.peon.jfinal.core;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = super.getParameterMap();
		HashMap<String, String[]> newmap = new HashMap<String, String[]>();
		for (String key : map.keySet()) {
			String[] v = map.get(key);
			if (v!=null&&v.length>0) {
				for (int i = 0; i < v.length; i++) {
					String string = v[i];
					v[i]=getXssValue(string);
				}
			}
			newmap.put(key, v);
		}
		return map;
	}
	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = getXssValue(values[i]);
		}
		return encodedValues;
	}
	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		return getXssValue(value);
	}

	private String getXssValue(String value) {
		if (value == null) {
			return null;
		}
		return cleanXSS(value);
	}

	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null)
			return null;
		return cleanXSS(value);
	}

	private String cleanXSS(String value) {
		// You'll need to remove the spaces from the html entities below
//		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
//		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
//		value = value.replaceAll("'", "& #39;");
//		value = value.replaceAll("&", "&amp;");
//		value = value.replaceAll("\"", "&quot;");
//		StringEscapeUtils.escapeJavaScript(value)
		 return StringEscapeUtils.escapeHtml(value);
		
	/*	value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
				"\"\"");
		value = value.replaceAll("script", "");*/
//		return value;
	}

	public static void main(String[] args) {
		String str="<script>alert('1');</script>";
		String a=StringEscapeUtils.escapeHtml(str);
		System.out.println(a);
		  a=StringEscapeUtils.escapeJavaScript(a);
		System.out.println(a);
		
		String sql=" ' insert into sadfasd what where 1=?";
		System.out.println(StringEscapeUtils.escapeSql(sql));
	}

}