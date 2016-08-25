package com.boxamazing.service.sms.model;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.util.SqlUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 发送邮件记录表
 * @author HECJ
 */
public class SendEmailRecord extends Model<SendEmailRecord> {
	private static final Log log = LogFactory.getLog(SendEmailRecord.class);
	private static final long serialVersionUID = 1L;
	
	public static final SendEmailRecord dao = new SendEmailRecord();
	
	/**
	 * 根据发送邮箱查询发送一般邮件的记录
	 * @param email
	 * @return 
	 * @v 20151211
	 */
	public SendEmailRecord findByEmail(String email){
		log.info("查询发送邮箱为"+email+"的发送记录");
		String sql = "select * from send_email_record where reciver_email = ? and id_delete = 0 and isSucc = 0 order by create_at desc";
		List<SendEmailRecord> list = new ArrayList();
		list = SendEmailRecord.dao.find(sql,email);
		if(list!=null&&list.size()>0){
			if(list.get(0)!=null){
				return list.get(0);
			}
			return null;
		}else{
			return null;
		}
	}
	
	
	public SendEmailRecord findByReciver(Long userid){
		log.info("查询发送用户id为"+userid+"的发送记录");
		String sql = "select * from send_email_record where reciver = ? and type = 0 and id_delete = 0 and isSucc = 0 order by create_at desc";
		List<SendEmailRecord> list = new ArrayList();
		list = SendEmailRecord.dao.find(sql,userid);
		if(list.get(0)!=null){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 短信记录
	 */
	public Page<SendEmailRecord> findBySendEmailRecordParams(Map<String, Object> params) {

		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		String reciver_email = (String) params.get("reciver_email");
		Long reciver = (Long) params.get("reciver");
		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());

		String selectSQL = "select se.* ";

		StringBuffer condition = new StringBuffer(" from send_email_record se where 1=1");

		List<Object> sqlParams = new ArrayList<Object>();

		if (!StringUtil.isNullOrEmpty(reciver_email)) {
			condition.append(" and se.reciver_email = ? ");
			sqlParams.add(reciver_email);
		}

		if (reciver != null) {
			condition.append(" and se.reciver = ? ");
			sqlParams.add(reciver);
		}

		condition.append(" order by se.id desc ");

		log.info(" 查询邮件发送列表 condition{} : " + condition.toString());
		try {
			Page<SendEmailRecord> list = dao.paginate(pageNumber, pageSize, selectSQL, condition.toString(), SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error("status{},pageObj{},sizeObj{}:" + pageObj + "," + sizeObj);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
