package com.boxamazing.service.sms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.pay.model.AlipayRecord;
import com.boxamazing.service.util.SqlUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 发送短信记录表
 * @author HECJ
 */
public class SendSmsRecord extends Model<SendSmsRecord> {
	
	private static final long serialVersionUID = 1L;
	
	public static final SendSmsRecord dao = new SendSmsRecord();
	
private static final Log log = LogFactory.getLog(AlipayRecord.class);
	
	/**
	 * 短信记录
	 */
	public Page<SendSmsRecord> findBySendSmsRecordParams(Map<String, Object> params) {

		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		String phone = (String) params.get("phone");
		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());

		String selectSQL = "select ar.* ";

		StringBuffer condition = new StringBuffer(" from send_sms_record ar where 1=1");

		List<Object> sqlParams = new ArrayList<Object>();

		if (!StringUtil.isNullOrEmpty(phone)) {
			condition.append(" and ar.phone = ? ");
			sqlParams.add(phone);
		}

		condition.append(" order by ar.id desc ");

		log.info(" 查询短信发送列表 condition{} : " + condition.toString());
		try {
			Page<SendSmsRecord> list = dao.paginate(pageNumber, pageSize, selectSQL, condition.toString(), SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error("status{},pageObj{},sizeObj{}:" + pageObj + "," + sizeObj);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}


}
