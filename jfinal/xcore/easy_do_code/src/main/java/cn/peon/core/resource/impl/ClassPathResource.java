package cn.peon.core.resource.impl;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import cn.peon.core.kit.StringUtil;
import cn.peon.core.resource.Resource;

public class ClassPathResource implements Resource{
	private  String uri;//唯一标示符
	public ClassPathResource(String uri) {
		this.uri=uri;
	}
	public InputStream getInputStream() {
		InputStream is =ClassPathResource.class.getResourceAsStream(this.uri); 
		return is;
	}
	public URL getURL() {
		   URL clzURL = this.getClass().getResource(this.uri);
		   return clzURL;
	}
	public File getFile() {
		 URL ul = getURL();
		 if (ul==null) {
			return null;
		}
		String uf = ul.getFile();
		if (StringUtil.isNullOrEmpty(uf)) {
			return null;
		}
		return new File(uf);
	}
	public boolean exists() {
		return false;
//		return getURL().getFile();
	}
	public static void main(String[] args) {
		ClassPathResource cl = new ClassPathResource("/wc/conf/mime.properties");
		URL ul = cl.getURL();
		System.out.println(ul);
		String f = ul.getFile();
		System.err.println(f);
	}
	public long lastModified() {
		return 0;
	}
}

