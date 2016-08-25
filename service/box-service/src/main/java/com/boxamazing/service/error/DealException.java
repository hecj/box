package com.boxamazing.service.error;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.plugin.activerecord.Model;

public class DealException extends Model<DealException> {
	
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(DealException.class);

	public static final DealException dao = new DealException();

}
