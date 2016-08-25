package com.boxamazing.service.delivery.model;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.plugin.activerecord.Model;

public class Delivery_b  extends Model<Delivery_b>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(Delivery_b.class);
	
	public static final Delivery_b dao = new Delivery_b();
	
}
