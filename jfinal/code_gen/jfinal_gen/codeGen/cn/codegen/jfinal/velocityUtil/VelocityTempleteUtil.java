package cn.codegen.jfinal.velocityUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;


public class VelocityTempleteUtil {
	static{
		  Velocity.init();
	}
	public static void genearchFile(File pojomodel,String templateName, File targetFile, Map<String, Object> content,String templateencode) throws IOException, FileNotFoundException {
		OutputStream ou=new FileOutputStream(targetFile);
		//		OutputStream ou=System.out;
		genearchModel(pojomodel, templateName, ou, content,templateencode);
	}
	public static void genearchModel(File file,String templateName ,OutputStream ou,Map<String, Object> content,String templateencode) throws IOException {
		
		Writer out = new OutputStreamWriter(ou);
        Template template = Velocity.getTemplate(file+File.separator+templateName,templateencode);
        VelocityContext ctx = new VelocityContext(content);
        template.merge(ctx, out);
        out.flush();
		out.close();

	}

}
