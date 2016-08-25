package com.boxamazing.service.project.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.code.Areas;
import com.boxamazing.service.code.Cities;
import com.boxamazing.service.code.Provinces;
import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.config.model.Config;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.FormatUtil;
import com.boxamazing.service.util.MathCalcUtil;
import com.boxamazing.service.util.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * Project Model Created by jhl on 15/8/12.
 */
@SuppressWarnings("serial")
public class Project extends Model<Project> {

	public static final Project dao = new Project();
	private static final Log log = LogFactory.getLog(Project.class);

	/**
	 * 获取发起人id
	 * 
	 * @author XuXD
	 * @param projectId
	 * @return
	 */
	public Long getUserId(Long projectId) {
		log.info("[projectId:" + projectId + "]");
		try {
			Project project = dao.findById(projectId);
			return project.getLong("user_id");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取发起人id", e);
		}
		return -1L;
	}

	/**
	 * 通过id获取项目
	 * 
	 * @author XuXD
	 * @param ids
	 * @return
	 */
	public List<Project> getPrjectByIds(Object[] id) {
		String ids = ArrayUtil.arrayToString(id);
		log.info("[ids:" + ids + "]");
		String sql = "select p.*,u.nickname from project p ,user u where p.user_id = u.id and p.id in (" + ids + ")";
		try {
			List<Project> projectList = dao.find(sql);
			return projectList;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过id获取项目异常", e);
		}
		return new ArrayList<Project>();
	}

	/**
	 * 获取最新项目
	 * 
	 * @author XuXD
	 * @param size
	 * @return
	 */
	public List<Project> getNewProject(Integer size,List<Long> projectIds) {
		log.info("[size:" + size + "]");
		StringBuffer sql = new StringBuffer("select * from  project where status = 8 and isdelete=0 ");
		try {
			if(projectIds.size() > 0){
				sql.append(" and id not in ("+SqlUtil.withSplit2(projectIds, ",")+")");
			}
			sql.append(" order by starttime desc limit ?");
			
			List<Project> list = Project.dao.find(sql.toString(), size);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取最新项目异常", e);
		}
		return new ArrayList<Project>();
	}
	
	/**
	 * 获取推荐项目(列表页)
	 */
	public List<Project> getPrjectRecommByIds(Integer position,Integer size){
		String sql = "select p.*,u.nickname from project_recomm pr left join project p on p.id = pr.project_id left join user u on p.user_id = u.id where position= ? and p.status in (7,8,10) order by pr.update_at desc limit ?";		
		try {
			List<Project> projectList = Project.dao.find(sql,position,size);
			return projectList;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过id获取项目异常", e);
		}
		return new ArrayList<Project>();
	}

	/**
	 * 通过名称获取项目
	 * 
	 * @author XuXD
	 * @param para
	 * @return
	 */
	public List<Project> getPrjectByName(String name) {
		log.info("[name：" + name + "]");
		String sql = "select * from project where isdelete = 0 and name like ?";
		try {
			List<Project> list = Project.dao.find(sql, "%" + name + "%");
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过名称获取项目异常", e);
		}
		return new ArrayList<Project>();
	}

	/**
	 * 通过用户获取项目
	 * 
	 * @author XuXD
	 * @param userId
	 * @return
	 */
	public List<Project> getPrjectByUser(Long userId) {
		log.info("[userId:" + userId + "]");
		String sql = "SELECT * FROM project WHERE user_id = ? AND isdelete = 0";
		try {
			List<Project> list = Project.dao.find(sql, userId);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过用户获取项目异常", e);
		}
		return new ArrayList<Project>();
	}

	/**
	 * 通过用户,状态获取项目
	 * 
	 * @param uid
	 * @param status
	 * @return
	 */
	public List<Project> getPrjectByUser_State(Long userId, Integer[] state) {
		String states = ArrayUtil.arrayToString(state);
		log.info("[userId:" + userId + ",states:" + states + "]");
		String sql = "SELECT * FROM box.project WHERE user_id = ? and status in(" + states + ") and isdelete = 0";
		try {
			List<Project> list = Project.dao.find(sql, userId);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过用户,状态获取项目异常", e);
		}
		return new ArrayList<Project>();
	}
	
	/**
	 * 通过用户,状态获取项目
	 * 
	 * @param uid
	 * @param status
	 * @return
	 */
	public List<Project> getPrjectByUserState(Long userId,Integer status, Integer page) {
		
		log.info("userId:" + userId + ",status:" + status );
		List<Project> list = new ArrayList();
		String sql = "SELECT * FROM box.project WHERE user_id = ? and isdelete = 0";
		if(status!=0){
			sql = sql.concat(" and status = ?");
			try{
				list = Project.dao.find(sql, userId, status.intValue());
			}catch(Exception e){
				e.printStackTrace();
				log.error("通过用户,状态获取项目异常", e);
			}
		}else{
			try{
				list = Project.dao.find(sql, userId);
			}catch(Exception e){
				e.printStackTrace();
				log.error("通过用户,状态获取项目异常", e);
			}
		}
		if(list!=null){
			return list;
		}else{
			return null;
		}
		
		
		
	}


	/**
	 * 获取轮播图路径POJO
	 * 
	 * @author XuXD
	 * @return
	 */
	public List<String> getTurnImages() {
		String images = getStr("images");
		try {
			if (images != null)
				return Arrays.asList(images.split(","));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取轮播图路径POJO异常", e);
		}
		return new ArrayList<String>();
	}

	/**
	 * 通过当前时间和结束时间计算剩余天数POJO
	 * 
	 * @author XuXD
	 * @return
	 */
	public Long getBeLeftDays() {
		try {
			if (null != getLong("expirestime")) {
				// 获取结束时间
				long expires = getLong("expirestime");
				// 获取当前时间
				long create = System.currentTimeMillis();
				// 计算剩余天数
				long days = (expires - create) / (1000 * 3600 * 24);
				if (days < 0) {
					log.info("days < 0 [days:" + days + "]");
					days = 0L;
				}
				return days;
			} else {
				log.info("expirestime is null");
				return Long.valueOf(getInt("days"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过当前时间和结束时间计算剩余天数POJO异常", e);
		}
		return 0L;
	}

	/**
	 * 通过完成率和目标计算进度POJO
	 * 
	 * @author XuXD
	 * @return
	 */
	public double getProgress() {
		try {
			double goal = this.getBigDecimal("fundgoal").doubleValue();
			double now = this.getBigDecimal("fundnow").doubleValue();
			if (goal != 0) {
				double scale = MathCalcUtil.div(now, goal)*100;
				return FormatUtil.truncationDigits(scale, 2);
			} else {
				log.info("除数为0 [goal:" + goal + ",now:" + now + "]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过完成率和目标计算进度POJO异常", e);
		}
		return 0;
	}

	/**
	 * 获取格式化开始时间POJO
	 * 
	 * @author XuXD
	 * @return
	 */
	public String getStartTimeFormat() {
		try {
			Long starttime = getLong("starttime");
			if (starttime != null) {
				return FormatUtil.MyDateFormat("yyyy年MM月dd日", starttime);
			} else {
				log.info("starttime is null");
				return "预热中";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取格式化开始时间POJO异常", e);
		}
		return "";
	}

	/**
	 * 获取格式化结束时间POJO
	 * 
	 * @author XuXD
	 * @return
	 */
	public String getExpiresTimeFormat() {
		try {
			Long expirestime = getLong("expirestime");
			if (expirestime != null) {
				return FormatUtil.MyDateFormat("yyyy年MM月dd日", expirestime);
			} else {
				log.info("expirestime is null");
				return "预热中";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取格式化结束时间POJO异常", e);
		}
		return "";
	}

	/**
	 * 获取省份名称 by YJ
	 */
	public String getProvincesName() {
		try {
			return Provinces.dao.findCacheByProvinceId(this.getStr("province")).getStr("province");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取市名 by YJ
	 */
	public String getCityNames() {
		try {
			Provinces provin = Provinces.dao.findCacheByProvinceId(this.getStr("province"));
			int type = provin.getInt("type");
			if (type == 2) {
				return Areas.dao.findAreasByCityid(this.getStr("city")).getStr("area");
			} else {
				return Cities.dao.findCacheByCityid(this.getStr("city")).getStr("city");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 基本分页方法
	 */
	public Page<Project> _page(int pn, int ps) {
		return dao.paginate(pn, ps, "select *", "from project where isdelete = 0 order by id desc");
	}

	/**
	 * 根据id删除多个对象
	 */
	public int deleteByIds(String... ids) {
		String[] ay = ArrayUtil.getPrePareArray(ids.length);
		String str = StringUtil.join(ay, ",");
		return Db.update("delete from project where id in (" + str + ")", ids);
	}

	/**
	 * 通过状态获取项目
	 * 
	 * @param status
	 * @return
	 */
	public List<Project> findByStatus(Integer state) {
		return Project.dao.find("SELECT * FROM project WHERE status = ? and isdelete = 0", state);
	}

	/**
	 * 通过状态获取项目
	 * 
	 * @param status
	 * @return
	 */
	public List<Project> findByStatus(String... status) {
		String[] ay = ArrayUtil.getPrePareArray(status.length);
		String str = StringUtil.join(ay, ",");
		return Project.dao.find("SELECT * FROM project WHERE isdelete = 0 and status in(" + str + ") ", status);
	}

	/**
	 * 获取状态名称POJO
	 * 
	 * @return
	 */
	public String getStatusName() {
		return ProjectStatus.getStatus(this.getInt("status"));
	}
	
	/**
	 * 获取状态名称POJO
	 * 
	 * @return
	 */
	public String getStatuName(Integer status) {
		return ProjectStatus.getStatus(status);
	}

	/**
	 * 通过状态查找状态文字 POJO
	 * 
	 * @return
	 */
	public String getStatusText() {
		return ProjectStatus.getStatus(this.getInt("status"));
	}

	/*
	 * 获取系统推荐项目
	 */

	public Project findRecommond() {
		// 获取推荐项目的配置信息
		Config config = Config.dao.findById("indexRecommendProjectId");
		// 获取推荐项目的value值，该value即为该项目的id
		String value = config.getStr("value");
		// 根据id获取项目
		Project project = Project.dao.findById(value);
		return project;
	}

	// 根据类型选择项目
	public List<Project> getMethodByType(Integer typeid) {
		List list = Project.dao.find("select * from project where category_id =? and isdelete=?", typeid, 0);
		return list;
	}

	// 根据状态选择项目
	public List<Project> getMethodByStatus(Integer status) {
		List list = Project.dao.find("select * from project where status =? and isdelete=?", status, 0);
		return list;
	}

	// 根据状态和类型选择项目
	public List<Project> getMethod(Integer typeid, Integer status) {
		List list = Project.dao.find("select * from project where isdelete=? and category_id =? and status = ?", 0, typeid, status);
		return list;
	}

	/**
	 * 1.获取所有项目 2.分页 by HECJ
	 */
	public List<Project> findProjectByCondition(Map<String, Object> params) {

		Object category = params.get("category");
		Object status = params.get("status");
		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");

		StringBuffer condition = new StringBuffer("select * from project where 1=1 and isdelete=0 ");
		if (!StringUtil.isNull(category)) {
			condition.append(" and category_id =" + category);
		}
		if (!StringUtil.isNull(status)) {
			condition.append(" and status =" + status);
		} else {
			condition.append(" and status in (7,8,10)");
		}

		// 分页
		int page = 1;
		if (!StringUtil.isNull(pageObj)) {
			page = Integer.parseInt(pageObj.toString());
		}
		int size = 9;
		if (StringUtil.isNull(sizeObj)) {
			size = Integer.parseInt(sizeObj.toString());
		}
		int start = size * (page - 1);

		condition.append(" limit " + start + "," + size);

		log.info(" 查询项目列表 condition{} : " + condition);
		List<Project> list = dao.find(condition.toString());

		log.info(" list size : " + list.size());
		return list;
	}

	/**
	 * 1.级联查询项目 2.分页 by HECJ
	 */
	public List<Record> findRecordByCondition(Map<String, Object> params) {

		Object category = params.get("category");
		Object status = params.get("status");
		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		// Object typeObj = params.get("type");

		StringBuffer condition = new StringBuffer(
				"SELECT p.id,p.user_id,u.nickname,p.status,p.cover_image,p.name,p.desc,p.fundgoal,p.fundnow,p.starttime,p.expirestime,p.days,CEIL((p.expirestime-UNIX_TIMESTAMP(NOW())*1000)/24/60/60/1000) as surplus_days,FLOOR(p.fundnow*100/p.fundgoal) progress FROM  project p LEFT JOIN user u ON (p.user_id = u.id) where 1=1 and p.isdelete=0 ");
		if (!StringUtil.isNull(category)) {
			condition.append(" and p.category_id =" + category);
		}
		if (!StringUtil.isNull(status)) {
			condition.append(" and p.status =" + status);
		} else {
			condition.append(" and p.status in (7,8,10)");
		}

		// 分页
		int page = 1;
		if (!StringUtil.isNull(pageObj)) {
			page = Integer.parseInt(pageObj.toString());
		}
		int size = 9;
		if (!StringUtil.isNull(sizeObj)) {
			size = Integer.parseInt(sizeObj.toString());
		}
		int start = size * (page - 1);

		condition.append(" order by p.id desc limit " + start + "," + size);

		log.info(" 查询项目列表 condition{} : " + condition.toString());

		List<Record> list = Db.find(condition.toString());

		log.info(" list size : " + list.size());

		return list;
	}

	public List findProjectByList(ArrayList<Long> arrayList) {
		List list = new ArrayList();
		for (Long id : arrayList) {
			list.add(Project.dao.find("select * from project where isdelete = ? and  id =?", 0, id));
		}
		return list;
	}

	public List<Project> findAllByUser(String username) {
		return Project.dao.find("select * from project where uid =? and isdelete = ?", username, 0);
	}

	/**
	 * 1.级联查询项目 2.分页 by HECJ
	 */
	public List<Record> findRecordByIds(List<Long> ids) {

		StringBuffer condition = new StringBuffer(
				"SELECT p.id,p.user_id,u.nickname,p.status,p.province,p.city,p.name,p.desc,p.fundgoal,p.fundnow,p.starttime,p.expirestime,p.days,CEIL((p.expirestime-UNIX_TIMESTAMP(NOW())*1000)/24/60/60/1000) as surplus_days,FLOOR(p.fundnow*100/p.fundgoal) progress FROM project p LEFT JOIN user u ON (p.user_id = u.id) where 1=1 and p.isdelete=0");
		condition.append(" and p.id in (" + SqlUtil.withSplit(ids, ",") + ")");
		log.info(" 查询项目列表 condition{} : " + condition.toString());
		List<Record> list = Db.find(condition.toString());
		log.info(" list size : " + list.size());
		return list;
	}

	/**
	 * 1.级联查询项目<br>
	 * 2.分页<br>
	 * 后端查询用的<br>
	 * by HECJ
	 */
	public Page<Project> findRecordByAdminAndCondition(Map<String, Object> params) {

		String category = (String) params.get("category");
		String status = (String) params.get("status");
		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		String uPhone = (String) params.get("uPhone");
		String name = (String) params.get("name");
		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());

		String selectSQL = "SELECT p.id,p.user_id,u.nickname,u.certify,p.aphone,u.phone,p.status,p.province,p.city,p.settlement,p.aname,p.fundpcount,p.acontext,p.name,p.desc,p.fundgoal,p.fundnow,p.cover_image,p.starttime,p.expirestime,p.days,CEIL((p.expirestime-UNIX_TIMESTAMP(NOW())*1000)/24/60/60/1000) as surplus_days,FLOOR(p.fundnow*100/p.fundgoal) progress";

		StringBuffer condition = new StringBuffer(" FROM project p LEFT JOIN user u ON (p.user_id = u.id) where 1=1 ");

		List<Object> sqlParams = new ArrayList<Object>();

		if (!StringUtil.isNullOrEmpty(category)) {
			condition.append(" and p.category_id = ?");
			sqlParams.add(category);
		}
		if (!StringUtil.isNullOrEmpty(status)) {
			condition.append(" and p.status in ( ? )");
			sqlParams.add(status);
		}

		if (!StringUtil.isNullOrEmpty(uPhone)) {
			condition.append(" and p.aphone = ? ");
			sqlParams.add(uPhone);
		}
		
		if (!StringUtil.isNullOrEmpty(name)) {
			condition.append(" and p.name like ? ");
			sqlParams.add("%"+name+"%");
		}

		condition.append(" order by p.id desc ");
		log.info(" 查询项目列表 condition{} : " + condition.toString());
		log.info("params{}:" + sqlParams.toArray());
		try {
			Page<Project> list = dao.paginate(pageNumber, pageSize, selectSQL, condition.toString(), SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error("category{},status{},pageObj{},sizeObj{},uPhone{}:" + category + "," + status + "," + pageObj + "," + sizeObj + ","
					+ uPhone);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取类别名称 by HECJ
	 */
	public String getCategoryText() {
		ProjectCategory category = ProjectCategory.dao.findById(this.getLong("category_id"));
		if (category != null) {
			return category.get("name");
		}
		return null;
	}

	/**
	 * 1.级联查询项目<br>
	 * 2.分页<br>
	 * 后端推荐项目查询<br>
	 * by HECJ
	 */
	public Page<Project> findRecordByRecommAndCondition(Map<String, Object> params) {

		String ids = (String) params.get("ids");
		String category = (String) params.get("category");
		String status = (String) params.get("status");
		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		String aphone = (String) params.get("aphone");
		String name = (String) params.get("name");
		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());

		String selectSQL = "SELECT p.*";

		StringBuffer condition = new StringBuffer(" FROM project p where 1=1 and p.isdelete=0");

		List<Object> sqlParams = new ArrayList<Object>();

		if (!StringUtil.isNullOrEmpty(category)) {
			condition.append(" and p.category_id = ?");
			sqlParams.add(category);
		}
		if (!StringUtil.isNullOrEmpty(status)) {
			condition.append(" and p.status in (" + status + ")");
			// sqlParams.add(status);
		}

		if (!StringUtil.isNullOrEmpty(aphone)) {
			condition.append(" and p.aphone = ? ");
			sqlParams.add(aphone);
		}

		if (!StringUtil.isNullOrEmpty(name)) {
			condition.append(" and p.name like ? ");
			sqlParams.add("%" + name + "%");
		}

		if (!StringUtil.isNullOrEmpty(ids)) {
			condition.append(" and p.id in (" + ids + ")");
			// sqlParams.add(ids);
		}

		condition.append(" order by p.id desc ");

		log.info(" 查询推荐项目列表 condition{} : " + condition.toString());
		log.info("params{}:" + sqlParams);
		try {
			Page<Project> list = dao.paginate(pageNumber, pageSize, selectSQL, condition.toString(), SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error("category{},status{},pageObj{},sizeObj{},aPhone{}:" + category + "," + status + "," + pageObj + "," + sizeObj + ","
					+ aphone);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 该项目推荐信息
	 * @return
	 */

	public ProjectRecomm getProjectRecomm() {
		return ProjectRecomm.dao.findByProjectId(this.getLong("id"));
	}

	/**
	 * 发布人信息
	 * @author by HECJ
	 */

	public User getUser() {
		try {
			return User.dao.findById(this.getLong("user_id"));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 众筹金额千分位格式化 by HECJ
	 */
	public String getFundgoalFormat() {

		Long id = -1l;
		try {
			id = this.getLong("id");
			BigDecimal fundgoal = this.getBigDecimal("fundgoal");
			return FormatUtil.thousandPoints(fundgoal.longValue());
		} catch (Exception e) {
			log.error("众筹金额千分位格式化时出错，project_id{}:" + id);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return String.valueOf(0);
	}

	/**
	 * 众筹金额千分位格式化 by HECJ
	 */
	public String getFundnowFormat() {

		Long id = -1l;
		try {
			id = this.getLong("id");
			BigDecimal fundnow = this.getBigDecimal("fundnow");
			return FormatUtil.thousandPoints(fundnow.longValue());
		} catch (Exception e) {
			log.error("已筹金额千分位格式化时出错，project_id{}:" + id);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return String.valueOf(0);
	}

	/**
	 * 
	 * 
	 * 列表页分页查询<br>
	 * by YJ
	 */
	public Page<Project> findRecordsByCondition(Map<String, Object> params) {

		String category = (String) params.get("category").toString()+"";
		String status = (String) params.get("status").toString()+"";
		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		String search = (String) params.get("search");

		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());

		String selectSQL = "SELECT p.*,u.nickname";

		StringBuffer condition = new StringBuffer(" FROM  project p LEFT JOIN user u ON (p.user_id = u.id) where 1=1 and p.isdelete=0 ");

		List<Object> sqlParams = new ArrayList<Object>();

		if (!StringUtil.isNullOrEmpty(category)&&!"-1".equals(category)) {
			condition.append(" and p.category_id = ?");
			sqlParams.add(category);
		}
		if (!StringUtil.isNullOrEmpty(status)) {
			condition.append(" and p.status in (" + status + ")");

		}
		if (!StringUtil.isNullOrEmpty(search)) {
			condition.append(" and p.name like ? "); 
//			String cond = "";
//			for(char c : search.toCharArray()){
//				cond += "%"%;
//			}
//			
//			String cond = "%";
//			for(char c : search.toCharArray()){
//				cond += c + "%";
//			}
			sqlParams.add("%" + search + "%");
		}

		condition.append(" order by p.id desc, p.createtime desc , p.starttime desc");

		log.info(" 查询推荐项目列表 condition{} : " + condition.toString());
		log.info("params{}:" + sqlParams);
		try {
			Page<Project> list = dao.paginate(pageNumber, pageSize, selectSQL, condition.toString(), SqlUtil.toObjects(sqlParams));
			return list;
		} catch (Exception e) {
			log.error("category{},status{},pageObj{},sizeObj{}:" + category + "," + status + "," + pageObj + "," + sizeObj);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 我的发起
	 * by YJ
	 * @param params
	 * @return page
	 */
	public Page<Project> findRecordsByStatusAndUid(Map<String, Object> params) {
		
		String status = (String) params.get("status");
		Object pageObj = params.get("page");
		Object sizeObj = params.get("size");
		Object uid = params.get("uid");

		int pageNumber = Integer.parseInt(pageObj.toString());
		int pageSize = Integer.parseInt(sizeObj.toString());
		String userid = uid.toString();
		String selectSQL = "SELECT p.*,u.nickname";

		StringBuffer condition = new StringBuffer(" FROM  project p LEFT JOIN user u ON (p.user_id = u.id) where 1=1 and p.isdelete=0 ");

		if (!StringUtil.isNullOrEmpty(status)) {
			//如果不为0  则查询特定状态，如果为0  查询全部
			if(!"0".equals(status)){
				condition.append(" and p.status in (" + status + ")");
			}
		}
		condition.append(" and p.user_id = "+userid+" order by p.createtime desc");

		log.info(" 查询推荐项目列表 condition{} : " + condition.toString());
		
		try {
			Page<Project> list = dao.paginate(pageNumber, pageSize, selectSQL, condition.toString());
			return list;
		} catch (Exception e) {
			log.error("uid,status{},pageObj{},sizeObj{}:" + userid + "," + status + "," + pageObj + "," + sizeObj);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}


	public int findNum() {
		String sql = "select * from project where 1=1 and isdelete = 0 and status in (7,8,10)";
		List list = Project.dao.find(sql);
		return list.size();
	}

	/**
	 * 
	 * @param userid
	 * @return
	 */
	public List<Integer> findAllStatus(long userid){
		List<Project> list = Project.dao.find("select distinct status from project where user_id=?",userid);
		//return list;
		return null;
	}
	
	/**
	 * 查询众筹中到期项目IdList
	 */
	public List<Long> findProjectByStatusAndExpirestime(){
		String querySQL = "select p.id from project p where p.status = 8 and p.expirestime < UNIX_TIMESTAMP(NOW())*1000 ";
		try {
			List<Long> idList = Db.query(querySQL);
			return idList;
		} catch (Exception e) {
			log.error("查询众筹中到期项目失败："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 判断项目众筹成功的项目是否可以结算
	 * 查询该项目订单状态（1：已付款，4：待收货，6：等待退款）
	 */
	public boolean getIsBalance(){
		try {
			long rows = Db.queryLong("select count(o.id) from orders o where o.project_id = ? and o.status in (1,4,6)",new Object[]{this.getLong("id")});
			log.info("project id : " + this.getLong("id")+","+rows);
			if(rows > 0){
				return false;
			} else{
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
