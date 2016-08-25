package cn.peon.jfinal.core;

/**sql格式化工具 --完善中
 * @author wangchao
 *
 */
public class SqlFormatUtil {
	public static String KEYS = "INSERT|UPDATE|DELETE|SELECT|FROM|INNER|LEFT|RIGHT|WHERE|GROUP|ORDER|UNION|VALUES|SET|FOR|GO|WITH|Declare|else if|if|else|begin|end|CREATE|HAVING|exec|INTO"; // used to split lines
	public static String KEYS2 = "and|or|by|sp_executesql|outer|join|on|min|max|sum|count|avg|convert|datepart|top|not|in|all|IS|NULL|sp_prepexec|declare|exists|off|dateadd|datediff|getdate|as";
	
	public static String format(String sql) {
		sql=sql.replaceAll("[\\t\\n\\r]", " ")
				.replaceAll("\\s+", " ")
				.replaceAll("\\s?\\,\\s?", ",")
				.replaceAll("\\(\\s+", "(")
				.replaceAll("\\s+\\)", ")");
		return sql;
	}
	
	public static void main(String[] args) {
		String sql="  \r\n   \r\n  \r\n   INSERT    \r\n   \r\n   INTO user   VALUES (     null, 'zz' , 11   ) \r\n\r\n\r\n  \r\n";
		System.out.println(sql);
		String rs=format(sql);
		System.out.println(rs);
	}
}
