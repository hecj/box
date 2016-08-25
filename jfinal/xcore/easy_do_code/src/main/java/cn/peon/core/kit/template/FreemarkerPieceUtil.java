package cn.peon.core.kit.template;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerPieceUtil {
	static{
//		freemarker = new freemarkere
	}
	public static String rendPiece(Map<String, Object> context, String reader) throws IOException, TemplateException  {
		//创建一个模版对象
           Template t = new Template(null, new StringReader(reader), null);
           //创建插值的Map
          
           StringWriter write=new StringWriter();
           //执行插值，并输出到指定的输出流中
           t.process(context, write);
           write.flush();
           write.close();
           return write.toString();
	} 
	
	
	   public static void main(String[] args) throws Exception{
		   Map<String, Object> context=new HashMap<String, Object>();
			context.put("name", "cz中 的事发生地方");
			String reader="${name}vvv";
			String str = rendPiece(context, reader);
			
			System.out.println(str);
   }
}
