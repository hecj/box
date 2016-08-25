package com.boxamazing.service.code;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.plugin.activerecord.Model;

/**
 * 邮政编码
 * @author HECJ
 */
public class Zipcode extends Model<Zipcode> {

	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(Zipcode.class);
    
	public static final Zipcode dao = new Zipcode();
   
    
}
