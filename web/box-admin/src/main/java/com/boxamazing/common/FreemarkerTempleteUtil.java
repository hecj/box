package com.boxamazing.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 由easy_to_code移植
 */
public class FreemarkerTempleteUtil {
	private static Configuration cfg;
	static {
		cfg = new Configuration();
		cfg.setObjectWrapper(new DefaultObjectWrapper());
	}

	public static void genearchFile(File pojomodel, String templateName,
			File targetFile, Map<String, Object> content, String templateencode)
			throws IOException, FileNotFoundException {
		OutputStream ou = new FileOutputStream(targetFile);
		// OutputStream ou=System.out;
		genearchModel(pojomodel, templateName, ou, content, templateencode);
	}

	public static void genearchModel(File file, String templateName,
			OutputStream ou, Map<String, Object> content, String templateencode)
			throws IOException {
		cfg.setEncoding(Locale.getDefault(), templateencode);
		cfg.setDirectoryForTemplateLoading(file);
		Template temp = cfg.getTemplate(templateName);
		Writer out = new OutputStreamWriter(ou);
		try {
			temp.process(content, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		}

		out.flush();
		out.close();

	}

}