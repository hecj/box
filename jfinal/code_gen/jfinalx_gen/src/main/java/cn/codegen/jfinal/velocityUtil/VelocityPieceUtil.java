package cn.codegen.jfinal.velocityUtil;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VelocityPieceUtil {
	private  static VelocityEngine velocity;
	private static String logTag="rend_log";
	static{
		velocity = new VelocityEngine();
	}
	public static String rendPiece(Map<String, Object> context,String reader) throws IOException {
		VelocityContext vcontext = new VelocityContext(context);
		Writer writer = new StringWriter();
		velocity.evaluate(vcontext, writer, logTag, reader);
		writer.flush();
		writer.close();
		return writer.toString();
	}
	public static void main(String[] args) throws IOException {
		
		Map<String, Object> context=new HashMap<String, Object>();
		context.put("name", "cz中 的事发生地方");
		String reader="${name}vvv";
		String str = rendPiece(context, reader);
		System.out.println(str);
	}
}
