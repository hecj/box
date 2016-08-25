package com.boxamazing.service.excel;

import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jxl.write.WritableWorkbook;

/**
 * @类功能说明：创建excel的接口，具体生成何种excel，由子类完成
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @作者：He Chaojie
 * @创建时间：2013-8-2 上午11:09:29
 * @版本：V1.0
 */
public interface CreateExcel {
	
	/**
	 * 日志
	 */
	public Log log = LogFactory.getLog(CreateExcel.class);
	
	/**
	 * @函数功能说明 创建sheet
	 * @修改作者名字 He Chaojie  
	 * @修改时间 2013-8-2
	 * @修改内容
	 * @参数： @param sheet
	 * @参数： @return    
	 * @return boolean   
	 * @throws
	 */
	public WritableWorkbook create(AbstractSheet abstractSheet, OutputStream os);
	
}
