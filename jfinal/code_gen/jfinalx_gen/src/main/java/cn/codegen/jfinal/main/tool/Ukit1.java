package cn.codegen.jfinal.main.tool;

public class Ukit1 {
	public Ukit1() {
		System.out.println("用户工具初始化!");
	}
	public String getUpcase(String s) {
		return s.toUpperCase();
	}
	public String low(String s) {
		return s.toLowerCase();
	}
	public static String getPjPath(){
//		E:/java_web/workspace/jfinal_gen/target/classes/com/codegen/main/
		String pa=Ukit1.class.getResource("").toString().replaceFirst("file:/", "").split("WebRoot/WEB-INF")[0];
		if (pa.contains("/target/")) {//maven路径
			return pa.split("target/")[0];
		}
		return pa;
				
	}
}
