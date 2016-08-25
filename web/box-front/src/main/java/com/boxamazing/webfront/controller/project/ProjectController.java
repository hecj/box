package com.boxamazing.webfront.controller.project;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.comment.model.Comment;
import com.boxamazing.service.fans.model.Fans;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.product.model.Product;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.project.model.ProjectCategory;
import com.boxamazing.service.project.model.ProjectProgress;
import com.boxamazing.service.project.model.ProjectRecomm;
import com.boxamazing.service.subscribe.model.Subscribe;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.Constant;
import com.boxamazing.webfront.common.AppConfig;
import com.boxamazing.webfront.common.BaseController;
import com.boxamazing.webfront.util.ResultJson;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * Project Controller HECJ
 */
public class ProjectController extends BaseController {

	private static final Log log = LogFactory.getLog(ProjectController.class);

	/**
	 * 项目列表主页 by HECJ
	 */
	public void index() {
		// 项目类别
		long category = getParam("category", -1l);
		// 项目状态
		int status = getParam("status", -1);
		//获取是否为失败项目
		//int isFail = getParam("id2",0);
		// 获取分页信息
		int page = getParam("page", 1);
		int size = getParam("size", PropKit.getInt("page_size", 9));

		try {
			// Condition
			Map<String, Object> params = new HashMap<String, Object>();
			// category=-1 表示所有类型
			if (category != -1) {
				params.put("category", category + "");
			} else {
				// 推荐项目(选择全部时才显示)
				params.put("category", -1);
//				List<Long> idList = ProjectRecomm.dao.getProjectRecomm(0, PropKit.getInt("recommend_number", 6));
//				List<Project> recommList = Project.dao.getPrjectByIds(idList.toArray());
				List<Project> recommList = Project.dao.getPrjectRecommByIds(0,PropKit.getInt("recommend_number", 6));
				setAttr("recommList", recommList);
			}
			// status=-1 表示所有状态
			if (status != -1) {
//				if(isFail!=0){
//					params.put("status", status+","+isFail);
//				}else{
					params.put("status", status + "");
//				}
			} else {
				params.put("status", "7,8,10");
			}
			// 项目类型
			List<ProjectCategory> projectTypeList = ProjectCategory.dao.findAll();
			setAttr("projectTypeList", projectTypeList);
			
			// 分页页数
			params.put("page", page);
			params.put("size", size);

			// 项目列表
			Page<Project> pagep = Project.dao.findRecordsByCondition(params);
			setAttr("projectList", pagep.getList());
			setAttr("category", category);
			setAttr("status", status+"");
			setAttr("totalPage",pagep.getTotalPage());
			setAttr("pageNumber",pagep.getPageNumber());
			
			setAttr("selectText","");
		} catch (Exception e) {
			log.error("查询项目列表出错" + e.getMessage());
			e.printStackTrace();
		}
		// 静态URL
		setAttr("STATIC_URL", Constant.STATIC_URL);
		render("n_index.ftl");
	}

	/**
	 * 产品详情页
	 * 
	 * @author XuXD
	 */
	public void detail() {
		// 获取登录用户
		User user = UserUtil.getUser(getSession());
		setAttr("user", user);

		// 获取项目ID
		long projectId = getParam(0, -1L);
		setAttr("projectId", projectId);

		// 获取跳转table
		int table = getParam("table", 1);
		setAttr("table", table);
		
		// 获取项目信息
		Project project = null;
		try {
			project = Project.dao.findById(projectId);
		} catch (Exception e) {
			log.error("get project exception", e);
			renderText("get project exception");
			return;
		}
		if (project == null) {
			log.info("project is null");
			renderError(404);
		}
		
		// 校验项目状态  预热  众筹中  众筹成功  众筹结束
		int status = project.getInt("status");
		log.info("status : " + status);
		if(!(status == 7 || status == 8 || status == 9 || status == 10)){
			log.info("项目状态不可以查看详情 status ："+ status);
			renderError(404);
			return;
		}
		
		setAttr("project", project);

		// 获取项目发布人
		try {
			User projectUser = User.dao.findById(project.getLong("user_id"));
			setAttr("projectUser", projectUser);
			setAttr("projectUserFans", projectUser.getFansNum());
		} catch (Exception e) {
			log.error("get projectUser exception", e);
			renderText("get projectUser exception");
			return;
		}

		// 关注数
		long count = 0;
		try {
			count = Subscribe.dao.getSubCountByPid(projectId);
		} catch (Exception e) {
			log.error("get count exception", e);
			renderText("get count exception");
			return;
		}
		setAttr("subscribeNum", count);

		// 是否关注
		boolean isSub = false;
		if (null != user) {
			try {
				isSub = Subscribe.dao.isSub(projectId, user.getLong("id"));
			} catch (Exception e) {
				log.error("get isSub exception", e);
				renderText("get isSub exception");
				return;
			}
		} else {
			isSub = false;
		}
		setAttr("issub", isSub);

		// 获取回报
		List<Product> wayList = null;
		try {
			wayList = Product.dao.findByProjectId(projectId);
		} catch (Exception e) {
			log.error("get wayList exception", e);
			renderText("get wayList exception");
			return;
		}
		if (wayList == null) {
			log.info("wayList is null");
			renderText("wayList is null");
			return;
		}
		setAttr("wayList", wayList);
		
		// 粉丝关注数
		long fans_count = 0;
		try {
			fans_count = Fans.dao.getFansCountByPromoder(project.getLong("user_id"));
		} catch (Exception e) {
			log.error("get count exception", e);
			renderText("get count exception");
			return;
		}
		setAttr("fans", fans_count);

		// 当前登录用户是否为当前项目发起人的粉丝
		boolean fans_isSub = false;
		if (null != user) {
			try {
				fans_isSub = Fans.dao.isSub(project.getLong("user_id"), user.getLong("id"));
			} catch (Exception e) {
				log.error("get isSub exception", e);
				renderText("get isSub exception");
				return;
			}
		} else {
			fans_isSub = false;
		}
		setAttr("fans_isSub", fans_isSub);

		// SNS
		String url = AppConfig.getInstance().get("rootUrl");
		setAttr("snsUrl", url + "project/detail/" + projectId);
		setAttr("snsPic", Constant.STATIC_URL + project.getStr("cover_image"));
		setAttr("snsTitle", project.getStr("name"));
		setAttr("snsDesc", project.getStr("desc"));

		// 获取动态
		setAttr("projectProgressDynamic", ProjectProgress.dao.getDynamic(projectId));
		setAttr("commentDynamic", Comment.dao.getDynamic(projectId));
		setAttr("ordersDynamic", Orders.dao.getDynamic(projectId));

		setAttr("STATIC_URL", Constant.STATIC_URL);
		render("detail.ftl");
	}

	/**
	 * 根据类型和状态加载更多
	 */
	public void findByCategory() {
		// 项目类别
		long category = -1l;
		try {
			category = getParaToLong("category", -1l);
		} catch (Exception e) {
			log.info("项目类别转换异常category{}：" + category);
			e.printStackTrace();
		}

		// 项目状态
		int status = -1;
		try {
			status = getParaToInt("status", -1);
		} catch (Exception e) {
			log.error("项目状态转换异常status{}：" + status + "\n" + e);
		}

		// 获取分页信息
		int page = 1;
		try {
			page = getParaToInt("page", 1);
		} catch (Exception e) {
			log.info("页数转换异常page{}：" + page);
			e.printStackTrace();
		}

		// 获取每页显示项目数
		int size = 9;
		try {
			size = getParaToInt("size", 9);
		} catch (Exception e) {
			log.info("数目转换异常size{}:" + size);
			e.printStackTrace();
		}

		ResultJson resultJson = new ResultJson();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			
			if (category != -1) {
				params.put("category", category + "");
			}else{
				params.put("category", -1);
			}
			if (status != -1) {
				params.put("status", status);
			} else {
				params.put("status", "7,8,10");
			}

			// 判断是否是刷新页面还是ajax加载更多 0 刷新页面 1 加载更多
			// params.put("type", 1);
			params.put("page", page);
			params.put("size", size);
			

			// 项目列表

			resultJson.setCode(200L);
			Page<Project> projectList = Project.dao.findRecordsByCondition(params);
			List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();

			for (Project p : projectList.getList()) {
				String provinceName = p.getProvincesName();
				String cityName = p.getCityNames();
				Double progress = p.getProgress();
				Long lastday = p.getBeLeftDays();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("provinceName", provinceName);
				map.put("cityName", cityName);
				map.put("progress", progress);
				map.put("lastday", lastday);
				map.put("pageNumber", projectList.getPageNumber());
				map.put("pageSize", projectList.getPageSize());
				map.put("totalPage", projectList.getTotalPage());
				map.put("totalRow", projectList.getTotalRow());
				map.put("proj", p);
				newList.add(map);
			}
			resultJson.setData(newList);
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.setCode(404L);

		}
		renderJson(resultJson);

	}

	/*
	 * 根据条件查询 public void select() {
	 * 
	 * int category_id = getParaToInt("categord_id"); int status =
	 * getParaToInt("status");
	 * 
	 * List list = new ArrayList(); if (( status!=-1)&& ((category_id!=-1))){
	 * list = Project.dao.getMethod(category_id, status); } else if
	 * ((status==-1) && (category_id != -1)) { list =
	 * Project.dao.getMethodByType(category_id); } else if ((status!=-1) &&
	 * (category_id == -1)) { list = Project.dao.getMethodByStatus(status); }
	 * else { // list = Project.dao.findAll(); }
	 * 
	 * List<String> s = ProjectStatus.findAll(); // Map<Integer, String> m =
	 * ProjectType.getData();
	 * 
	 * if (category_id == -1) { setAttr("categoryId", "全部"); } else { //
	 * setAttr("categoryId", m.get(category_id)); }
	 * 
	 * if(status==7){ setAttr("statusId",s.get(0)); }else if(status==8){
	 * setAttr("statusId",s.get(1)); }else if(status==10){
	 * setAttr("statusId",s.get(2)); }else{ setAttr("statusId","所有项目"); }
	 * 
	 * setAttr("status", s); // setAttr("map", m);
	 * 
	 * setAttr("list", list); render("find.ftl"); }
	 */

	/**
	 * 搜索
	 * 
	 * @author XuXD
	 */
	public void findValue() {
		// 获取搜索参数并转码
		String search = "";
		try {
			search = URLDecoder.decode(getParam("search", ""), "UTF-8");
			log.info("[搜索参数:" + search + "]");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("[decode exception]", e);
			renderText("暂无数据");
			return;
		}

		// 项目类别
		long category = getParam("category", -1l);
		// 项目状态
		int status = getParam("status", -1);
		// 获取分页信息
		int page = getParam("page", 1);
		int size = getParam("size", PropKit.getInt("page_size", 9));

		try {
			// Condition
			Map<String, Object> params = new HashMap<String, Object>();
			// category=-1 表示所有类型
			if (category != -1) {
				params.put("category", category + "");
			} else {
				params.put("category", -1);
				// 推荐项目(选择全部时才显示)
				List<Project> recommList = Project.dao.getPrjectRecommByIds(0,PropKit.getInt("recommend_number", 6));
				setAttr("recommList", recommList);
//				List<Long> idList = ProjectRecomm.dao.getProjectRecomm(1, PropKit.getInt("recommend_number", 6));
//				List<Project> recommList = Project.dao.getPrjectByIds(idList.toArray());
//			    setAttr("recommList", recommList);
			}
			// status=-1 表示所有状态
			if (status != -1) {
				params.put("status", status + "");
			} else {
				params.put("status", "7,8,10");
			}
			// 项目类型
			List<ProjectCategory> projectTypeList = ProjectCategory.dao.findAll();
			setAttr("projectTypeList", projectTypeList);
			// 分页页数
			params.put("page", page);
			params.put("size", size);
			// 搜索参数
			params.put("search", search);

			// 项目列表
			Page<Project> pagep = Project.dao.findRecordsByCondition(params);
			setAttr("projectList", pagep.getList());
			setAttr("category", category);
			setAttr("status", status);
			setAttr("totalPage",pagep.getTotalPage());
			setAttr("pageNumber",pagep.getPageNumber());
			setAttr("selectText",search);
		} catch (Exception e) {
			log.error("查询项目列表出错" + e.getMessage());
			e.printStackTrace();
		}

		render("n_index.ftl");
	}
}
