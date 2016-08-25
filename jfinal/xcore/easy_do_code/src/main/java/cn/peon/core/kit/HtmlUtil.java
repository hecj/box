package cn.peon.core.kit;

/**html处理工具
 * @author wangchao
 *
 */
public class HtmlUtil {
	/**
	 * html文本只保留br、p标签 
	 * 
	 */
	public static String clear(String htmlStr) {
		return htmlStr.replaceAll("<script.*</script>", "").replaceAll(
				"<style.*</style>", "").replaceAll("'", "''").replaceAll(
				"<(/?p|br[^>]*)>", "[--$1--]").replaceAll("<[^<>]+>", " ")
				.replaceAll("\\[--([^-]+)--\\]", "<$1>");
	}
}
