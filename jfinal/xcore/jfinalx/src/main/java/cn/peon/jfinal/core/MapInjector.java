

package cn.peon.jfinal.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.core.TypeConverter;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Logger;


/**
 * map注入
 */
public final class MapInjector {

	/**参数注入
	 * @param name
	 * @param request
	 * @param filter
	 * @return
	 */
	public static Map<String, Object> inject(String name,HttpServletRequest request, Map<String, Class<?>> filter,boolean skipConvertError) {

		Map<String, Object> o=new HashMap<String, Object>();
		if (filter==null||filter.size()<=0) {
			return o;
		}else{
			
			String modelNameAndDot = name ;
			if (!modelNameAndDot.endsWith(".")) {
				
				modelNameAndDot=name+ ".";
			}
			Map<String, String[]> parasMap = request.getParameterMap();
			for (Entry<String, String[]> e : parasMap.entrySet()) {
				String paraKey = e.getKey();
				if (paraKey.startsWith(modelNameAndDot)||StrKit.isBlank(name)) {
					String paraName = paraKey;
					if (!StrKit.isBlank(name)) {
						paraName=paraKey.substring(modelNameAndDot.length());
					}
					Class colType = filter.get(paraName);
					if (colType == null){
						Logger log = Logger.getLogger("jfinalx");
						log.debug("The model attribute " + paraKey + " is not exists.");
						continue;
					}
					String[] paraValue = e.getValue();
					try {
						// Object value = Converter.convert(colType, paraValue != null ? paraValue[0] : null);
						Object value = paraValue[0] != null ? TypeConverter.convert(colType, paraValue[0]) : null;
						o.put(paraName, value);
					} catch (Exception ex) {
						if (skipConvertError == false)
							throw new RuntimeException("Can not convert parameter: " + modelNameAndDot + paraName, ex);
					}
				}
			}
			
			return o;
		}
		
	
		
		
		
	}
	
}

