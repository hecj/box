package com.boxamazing.service.user.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.util.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class UserFeedBack extends Model<UserFeedBack>{
	public static final UserFeedBack dao = new UserFeedBack();
	private static final Log log = LogFactory.getLog(UserFeedBack.class);
	
	//获取单个用户反馈记录
	public UserFeedBack findSingleRecord(Long userid){
		UserFeedBack userFeedBack = UserFeedBack.dao.findFirst("select * from user_feedback where isdelete = 0 and user_id = ? ",userid);
		return userFeedBack;
	}
	
	//获取所有未删除反馈
	public List<UserFeedBack> findAll(){
		List<UserFeedBack> list = new ArrayList<UserFeedBack>();
		list = UserFeedBack.dao.find("select * from user_feedback where isdelete = 0");
		return list;
	}
	
	//后端分页管理
	public Page<UserFeedBack> findRecordByCondition(Map<String, Object> params) {
		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		int is_delete =  Integer.parseInt(params.get("is_delete").toString());
		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());

		String selectSQL = "select user_feedback.*,user.nickname";

		StringBuffer condition = new StringBuffer(" from user_feedback ");
		condition.append(" left join user on user_feedback.user_id = user.id ");

		List<Object> sqlParams = new ArrayList<Object>();

		
		condition.append(" where user_feedback.is_delete = ? ");
		sqlParams.add(is_delete);
		
		condition.append(" order by user_feedback.id desc ");

		log.info(" 查询用户反馈列表 condition{} : " + condition.toString());
		try {
			Page<UserFeedBack> list = UserFeedBack.dao.paginate(pageNumber, pageSize, selectSQL, condition.toString(), SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error("is_delete{},pageObj{},sizeObj{},uPhone{}:" + is_delete + "," + pageObj + "," + sizeObj);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	//批量删除
	public boolean deleteAll(List<Long> ids){
		//TODO 
		log.info("批量删除的个数为："+ids.size());
		//遍历list
		for(int i=0;i<ids.size();i++){
			
		}
		return true;
	}
	
	/**
	 * 根据id删除多个对象
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteByIds(String... ids) {
		String[] ay = ArrayUtil.getPrePareArray(ids.length);
		String str = StringUtil.join(ay, ",");
		return Db.update("update user_feedback set is_delete = 1 where id in (" + str + ")", ids);
	}

	public int deleteOne(String id) {
		return Db.update("update user_feedback set is_delete = 1 where id =?",id);
		
	}
	//根据id获取多个对象
	public List<UserFeedBack> findByIds(String[] ids) {
		String str = StringUtil.join(ids, ",");
		return UserFeedBack.dao.find("select * from user_feedback where is_delete = 0 and id in (" + str + ") order by create_at desc , id desc");
	}

	
	public User getUser(){
		return User.dao.findById(getLong("user_id"));
	}

	
}
