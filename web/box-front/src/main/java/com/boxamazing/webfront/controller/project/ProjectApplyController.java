package com.boxamazing.webfront.controller.project;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.code.Areas;
import com.boxamazing.service.code.Cities;
import com.boxamazing.service.code.Provinces;
import com.boxamazing.service.comment.model.Comment;
import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.fans.model.Fans;
import com.boxamazing.service.open_user.model.OpenUser;
import com.boxamazing.service.product.model.Product;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.project.model.ProjectAuditRecord;
import com.boxamazing.service.project.model.ProjectCategory;
import com.boxamazing.service.project.model.ProjectProgress;
import com.boxamazing.service.sms.MessageService;
import com.boxamazing.service.sms.model.NoticeTemplate;
import com.boxamazing.service.subscribe.model.Subscribe;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.Constant;
import com.boxamazing.webfront.interceptor.BindInterceptor;
import com.boxamazing.webfront.interceptor.LoginInterceptor;
import com.boxamazing.webfront.util.Code;
import com.boxamazing.webfront.util.FileUtil;
import com.boxamazing.webfront.util.OpenUserUtil;
import com.boxamazing.webfront.util.ResultJson;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.aop.Before;
import com.jfinal.core.JfinalxController;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * <h1>审核Controller</h1> <br />
 * <p/>
 * agreement -> create -> doCreate
 * <p/>
 * #产品状态表# ================================ 状态 含义
 * -------------------------------- 0 全部 1 初审中 2 初审失败 3 初审通过
 * -------------------------------- 4 复审中 5 复审失败 6 复审通过
 * -------------------------------- 7 预热 8 众筹中 9 众筹失败 10 众筹成功
 * ================================ Created by jhl on 15/8/26.
 */

@Before({ LoginInterceptor.class, BindInterceptor.class })
public class ProjectApplyController extends JfinalxController {
	private static final Log log = LogFactory
			.getLog(ProjectApplyController.class);

	/**
	 * 协议条款
	 */
	public void agreement() {
		OpenUser openUser = OpenUserUtil.getOpenUser(getSession());
		User user = UserUtil.getUser(getSession());
		if ((null == user) && (openUser == null)) {
			redirect("/login");
			return;
		} else if ((null == user) && (openUser != null)) {
			redirect("/user/bind");
		} else {
			renderFreeMarker("agreement.ftl");
		}
	}

	/**
	 * create 创建初审表单
	 */
	public void createapply() {
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderError(403);
			return;
		}
		setAttr("user", user);
		renderFreeMarker("createapply.ftl");
	}

	/**
	 * doCreate 提交初审表单 by HECJ
	 */
	@Before(POST.class)
	public void saveapply() {
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderError(403);
			return;
		}

		try {
			String aname = getPara("aname");
			String acontext = getPara("acontext");
			String aphone = getPara("aphone");
			String afile = getPara("afile", "");
			Long user_id = user.getLong("id");
			

			// 上传文件临时目录
			String upload_file_temps_dir = PropKit.use("file.properties").get(
					"upload_file_temps_dir");
			// 上传文件存放目录
			String upload_files_dir = PropKit.use("file.properties").get(
					"upload_files_dir");
			String static_upload_files_url = PropKit.use("file.properties")
					.get("static_upload_files_url");
			// 创建临时路径
			String tempFileDir = afile;
			// 复制文件，删除临时文件
			if (!"".equals(tempFileDir)) {
				File tempFile = new File(upload_file_temps_dir + "/" + afile);
				File proFile = new File(upload_files_dir + "/" + afile);
				tempFileDir = static_upload_files_url + "/" + tempFileDir;
				FileUtil.copyFile(tempFile, proFile);
				tempFile.delete();
			}

			Project project = new Project().set("user_id", user_id)
					.set("status", 1)
					.set("aname", StringEscapeUtils.escapeSql(aname))
					.set("name",StringEscapeUtils.escapeSql(aname))
					.set("acontext", StringEscapeUtils.escapeSql(acontext))
					.set("aphone", StringEscapeUtils.escapeSql(aphone))
					.set("afile", tempFileDir).set("settlement", 0)
					.set("createtime", System.currentTimeMillis());
			boolean result = project.save();
			if (result) {
				log.info("用户为:" + user.get("username") + "创建项目：" + aname
						+ "---状态是：初审提交成功");
				// 模版
				String content = NoticeTemplate.dao.findByTempName("template_create_project").getStr("content");
				content = content.replace("%nickname%", user.getNickName());
				content = content.replace("%project_name%", aname);
				content = content.replace("%time%", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm"));
				// 创建项目 发送 系统消息、短信
				MessageService messageService = new MessageService();
				Map<String,String> contentParams = new HashMap<String,String>();
				contentParams.put("system", content);
				contentParams.put("phone", content);
				messageService.sendNoticeMessage(user_id, "", contentParams);
				
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("project_id", project.getLong("id"));
				renderJson(new ResultJson(200l, data, "success"));
				return;
			} else {
				log.info("用户为:" + user.get("username") + "创建项目：" + aname
						+ "---状态是：初审提交失败");
				renderJson(new ResultJson(-1l, "处理失败"));
				return;
			}

		} catch (Exception e) {
			log.info("项目初审提交失败{}:" + e.getMessage());
			e.printStackTrace();
			renderJson(new ResultJson(-100000l, e.getMessage()));
		}
	}

	/**
	 * 提交初审返回结果 by HECJ
	 */
	@Before(POST.class)
	public void apply_result() {

		try {
			User user = UserUtil.getUser(getSession());
			long user_id = user.getLong("id");
			long project_id = getParaToLong("project_id");
			log.info("project_id{}:" + project_id);
			Project project = Project.dao.findById(project_id);
			if (project == null) {
				log.info("项目不存在，请查明原因。");
				renderError(403);
				return;
			}
			log.info("user_id{}:" + user_id);
			if (user_id != project.getLong("user_id").longValue()) {
				log.info("该项目发起人和登录人不一致，非法请求。user_id{},project_id{}:" + user_id
						+ "," + project_id);
				renderError(403);
				return;
			}
			render("/page/projectapply/createapply-result.ftl");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderError(403);
		}
	}

	/**
	 * 我发起的项目
	 */
	public void myapply() {
		Long userid = UserUtil.getUser(getSession()).getLong("id");
		User user = User.dao.findById(userid);
		if (null == user) {
			renderError(403);
			return;
		}
		//更新session
		UserUtil.setUser(user, getSession());
		//获取当前页数
		Integer page = getParaToInt(0);
		if(page == null){
			page = 1;
		}
		
		//参数列表
		HashMap params = new HashMap();
		params.put("status", "0");
		params.put("page", page);
		params.put("size", 10);
		params.put("uid",user.getLong("id"));
		
		Page<Project> pageProject = Project.dao.findRecordsByStatusAndUid(params);
		
		
		// 获取状态集合(取消筛选)
//		Map<Integer, String> statusMap = new HashMap<Integer, String>();
//		statusMap = ProjectStatus.findAllMap();
//		HashSet<Integer> statusSet = new HashSet<Integer>();
//
//		setAttr("statusMap", statusMap);
//		//setAttr("status",Integer.parseInt(status));
		setAttr("pageProject", pageProject);
		setAttr("totalPage",pageProject.getTotalPage());
		setAttr("pageNumber",pageProject.getPageNumber());
		setAttr("isCertify", Integer.parseInt(user.getInt("certify").toString()));
		//获取粉丝数量
		Long fans_count = Fans.dao.getFansCountByPromoder(user.getLong("id"));
		setAttr("fans", fans_count);
		setAttr("STATIC_URL", Constant.STATIC_URL);
		render("myapply.ftl");
	}

	/**
	 * 删除申请
	 */
	public void deleteapply() {

		// 校验User
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderError(403);
			return;
		}

		// 校验产品
		try {
			Integer id = getParaToInt(0);
			log.info("删除id为" + id + "的产品信息");
			Project project = new Project().findById(id);
			if (null == project) {
				renderError(403);
				return;
			}
			if (!project.getLong("user_id").equals(user.getLong("id"))) {
				renderError(403);
				return;
			}

			Integer status = project.getInt("status");
			if (5 != status && 2 != status && 9 != status) {
				renderError(403);
				return;
			}

			// 删除
			boolean result = project.set("isdelete", 1).update();
			if (result) {
				log.info("用户：" + user.get("username") + "删除号码" + id + "的产品信息成功");
				setAttr("result", result);
			} else {
				log.error("用户：" + user.get("username") + "删除号码" + id
						+ "的产品信息失败");
			}
			//setAttr("msg", "删除成功");

			List<Project> projects = Project.dao.getPrjectByUser(user
					.getLong("id"));
			redirect("/projectapply/myapply");
			return;
		} catch (Exception e) {
			log.error("输入参数异常");
			e.printStackTrace();
			renderError(403);
		}
		renderNull();

	}

	/**
	 * 编辑项目复审表 状态 3 & 5
	 */
	public void editproject() {

		User user = UserUtil.getUser(getSession());

		// 校验产品
		try {
			Integer id = getParaToInt(0);
			log.info("编辑id为" + id + "的产品信息");
			Project project = Project.dao.findById(id);
			if (null == project) {
				log.info("要查找的项目不存在，project{}:" + id);
				renderError(403);
				return;
			}
			if (!project.getLong("user_id").equals(user.getLong("id"))) {
				log.info("该项目不是用户发起的项目，请核实。user_id{},project_id{}:"
						+ user.getLong("id") + "," + id);
				renderError(403);
				return;
			}

			Integer status = project.getInt("status");
			log.info("status{}:" + status);
			if (3 != status && 5 != status) {
				log.info("该项目状态不可编辑。status{}:" + status);
				renderError(403);
				return;
			}

			List<Provinces> provincesList = Provinces.dao.findAll();
			setAttr("provincesList", provincesList);

			String province = project.getStr("province");
			if (!StringUtil.isNullOrEmpty(province)) {

				List<Map<String, String>> data = new ArrayList<Map<String, String>>();
				Provinces provinces = Provinces.dao
						.findCacheByProvinceId(province);
				if (provinces.getInt("type") == 2) {
					// 直辖市
					List<Cities> citiesList = Cities.dao
							.findCitiesByProvinceId(province);
					for (Cities cities : citiesList) {
						List<Areas> areasList = Areas.dao
								.findAreasByCtiy(cities.getStr("cityid"));
						for (Areas areas : areasList) {
							Map<String, String> row = new HashMap<String, String>();
							row.put("key", areas.getStr("areaid"));
							row.put("value", areas.getStr("area"));
							data.add(row);
						}
					}
				} else {
					List<Cities> citiesList = Cities.dao
							.findCitiesByProvinceId(province);
					for (Cities cities : citiesList) {
						Map<String, String> row = new HashMap<String, String>();
						row.put("key", cities.getStr("cityid"));
						row.put("value", cities.getStr("city"));
						data.add(row);
					}
				}
				setAttr("citiesList", data);
			}

			String images = project.get("images");
			if (!StringUtil.isNullOrEmpty(images)) {
				String[] imagesList = images.split(",");
				if (imagesList.length > 0) {
					setAttr("images0", imagesList[0]);
				}
				if (imagesList.length > 1) {
					setAttr("images1", imagesList[1]);
				}
				if (imagesList.length > 2) {
					setAttr("images2", imagesList[2]);
				}
				if (imagesList.length > 3) {
					setAttr("images3", imagesList[3]);
				}
				if (imagesList.length > 4) {
					setAttr("images4", imagesList[4]);
				}
			}

			setAttr("project", project);
			setAttr("projectCategoryList", ProjectCategory.dao.findAll());
			setAttr("STATIC_URL", Constant.STATIC_URL);

			render("/page/projectapply/cf-project-recheck.ftl");
		} catch (Exception e) {
			log.error("输入参数异常");
			e.printStackTrace();
			renderError(403);
		}
	}

	/**
	 * 保存项目复审表
	 */
	@Before(POST.class)
	public void saveproject() {

		// 检测用户
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderError(403);
			return;
		}
		setAttr("user", user);

		// 检测project
		try {
			long user_id = user.getLong("id");
			log.info("用户user_id{}:" + user_id);
			Integer id = getParaToInt("id");
			log.info(" project_id{}:" + id);
			if (null == id) {
				renderError(403);
				return;
			}

			Project project = new Project().findById(id);
			if (null == project) {
				renderError(403);
				return;
			}

			if (!project.getLong("user_id").equals(user.getLong("id"))) {
				renderError(403, "not yours sorry");
				return;
			}

			// 检测project状态.
			Integer status = project.getInt("status");
			if (status != 3 && status != 5) {
				renderJson(new ResultJson(-1l, "该项目状态不可编辑"));
				return;
			}
			BigDecimal fundGoal = new BigDecimal(getPara("fundgoal"));
			log.info("fundGoal{}:" + fundGoal);
			if (fundGoal.compareTo(Constant.fundGoalBegin) < 0
					|| fundGoal.compareTo(Constant.fundGoalEnd) > 0) {
				log.info("筹款金额超出范围fundGoal{}:" + fundGoal);
				renderJson(new ResultJson(-2l, "筹款金额超出范围"));
				return;
			}

			String category_id = getPara("category_id");
			String name = getPara("name");
			String desc = getPara("desc");
			Integer days = getParaToInt("days");
			String province = getPara("province");
			String city = getPara("city");
			String cover_image = getPara("cover_image");
			String video = getPara("video");
			String context = getPara("context");

			String static_upload_images_url = PropKit.use("file.properties")
					.get("static_upload_images_url");
			// 新上传封面
			if (!cover_image.contains(static_upload_images_url)) {
				// 将临时目录图片cp到pro目录
				String upload_file_temps_dir = PropKit.use("file.properties")
						.get("upload_file_temps_dir");
				String upload_images_dir = PropKit.use("file.properties").get(
						"upload_images_dir");

				File tempFile = new File(upload_file_temps_dir + "/"
						+ cover_image);
				File proFile = new File(upload_images_dir + "/" + cover_image);
				FileUtil.copyFile(tempFile, proFile);
				tempFile.delete();

				log.info(tempFile + " cp ->" + proFile);
				cover_image = static_upload_images_url + "/" + cover_image;
			}

			// 说明图片集合
			String[] images = new String[] {};
			String imagesList = getPara("images");
			if (!StringUtil.isNullOrEmpty(imagesList)) {
				images = imagesList.split(",");
			}

			// 拼接图片集合
			String images_list = "";
			for (String image : images) {
				if (image == null || image.equals("")) {
					continue;
				}
				// 新上传图片
				if (!image.contains(static_upload_images_url)) {
					// 将临时目录图片cp到pro目录
					String upload_file_temps_dir = PropKit.use(
							"file.properties").get("upload_file_temps_dir");
					String upload_images_dir = PropKit.use("file.properties")
							.get("upload_images_dir");

					File tempFile = new File(upload_file_temps_dir + "/"
							+ image);
					File proFile = new File(upload_images_dir + "/" + image);
					FileUtil.copyFile(tempFile, proFile);
					tempFile.delete();

					log.info(tempFile + " cp ->" + proFile);
					images_list += ","
							+ (static_upload_images_url + "/" + image);
				} else {
					images_list += "," + image;
				}
			}
			images_list = images_list.replaceFirst(",", "");

			project.set("name", name).set("desc", desc)
					.set("fundgoal", fundGoal).set("days", days)
					.set("category_id", category_id).set("video", video)
					.set("context", context).set("province", province)
					.set("city", city).set("cover_image", cover_image)
					.set("images", images_list);

			project.set("status", 4);
			boolean result = project.update();
			if (result) {
				log.info("复审草稿保存成功 project_id{}:" + id);
				
				// 复审编辑 发送系统消息和短信
				try{
 	             	// 模版
 					String content = NoticeTemplate.dao.findByTempName("template_save_project").getStr("content");
 					content = content.replace("%nickname%", user.getStr("nickname"));
 	 				content = content.replace("%project_name%", project.getStr("name"));
 	 				content = content.replace("%time%", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm"));
 					// 复审项目 发送 系统消息、短信
 					MessageService messageService = new MessageService();
 					Map<String,String> contentParams = new HashMap<String,String>();
 					contentParams.put("system", content);
 					contentParams.put("phone", content);
 					messageService.sendNoticeMessage(user.getLong("id"), "", contentParams);
              	}catch(Exception ex){
         			ex.printStackTrace();
         		}
				
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("project_id", project.getLong("id"));
				renderJson(new ResultJson(200l, data, "success"));
				return;
			} else {
				log.info("复审草稿保存失败 project_id{}:" + id);
				renderJson(new ResultJson(-2l, "处理失败"));
				return;
			}

		} catch (Exception e) {
			log.error("获取参数异常：" + e.getMessage());
			e.printStackTrace();
			renderJson(new ResultJson(-100000l, e.getMessage()));
		}

	}

	/**
	 * 提交初审返回结果 by HECJ
	 */
	@Before(POST.class)
	public void save_result() {

		try {
			User user = UserUtil.getUser(getSession());
			long user_id = user.getLong("id");
			long project_id = getParaToLong("project_id");
			log.info("project_id{}:" + project_id);
			Project project = Project.dao.findById(project_id);
			if (project == null) {
				log.info("项目不存在，请查明原因。");
				renderError(403);
				return;
			}
			log.info("user_id{}:" + user_id);
			if (user_id != project.getLong("user_id").longValue()) {
				log.info("该项目发起人和登录人不一致，非法请求。user_id{},project_id{}:" + user_id
						+ "," + project_id);
				renderError(403);
				return;
			}
			render("/page/projectapply/cf-project-recheck-result.ftl");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderError(403);
		}
	}

	/**
	 * 预览 /projectapply/preview/1
	 */
	public void preview() {
		// 产品详情
		Long pid = getParaToLong(0);
		setAttr("pid", pid);

		User user = UserUtil.getUser(getSession());
		if (null == user || null == pid) {
			renderError(403);
			return;
		}
		// setAttr("uid", user.get("id"));
		setAttr("user", user);

		Project project = Project.dao.findById(pid);
		setAttr("p", project);
		if (null == project) {
			renderError(404);
			return;
		}

		// 关注数
		Long count = Subscribe.dao.getSubCountByPid(pid);
		if (null != count) {
			setAttr("subscribeNum", count.getLong("num"));
		} else {
			setAttr("subscribeNum", 0);
		}

		// SNS
		setAttr("snsUrl", "http://www.duomeidai.com/borrowDetail.action?id=291");
		setAttr("snsPic", "http://www.duomeidai.com/img/web_new/ad1.jpg");
		setAttr("snsPic",
				"http://img30.360buyimg.com/cf/jfs/t1657/350/455727835/235675/c5c158f5/55a3a2afN9f864054.jpg");
		setAttr("snsTitle", "借款企业属于襄阳市高新区重点招商引资企业");
		setAttr("snsDesc",
				"借款企业属于襄阳市高新区重点招商引资企业,是集科、工、贸一体化的高新科技企业。长期致力于水泥添加剂及矿渣微粉活化剂的研究开发,生产制造和推广应用。");

		// 众酬阶梯价格
		List<Product> wayList = Product.dao.findByProjectId(pid);
		setAttr("wayList", wayList);

		// 项目进展(公告历史)
		List<ProjectProgress> notice_historyList = ProjectProgress.dao
				.findByProjectId(pid);
		setAttr("notice_historyList", notice_historyList);

		// 支持者名单

		// 产品评论
		List<Comment> comments = Comment.dao.findComment(pid);
		setAttr("comments", comments);

		renderFreeMarker("preview.ftl");
	}

	/**
	 * 提交复审
	 */
	public void submitproject() {

		/** User检测 */
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderError(403);
			return;
		}

		/** Project检测 */
		Integer pid = getParaToInt(0);
		Project p = new Project().findById(pid);
		if (p == null || !p.getLong("user_id").equals(user.getLong("id"))) {
			renderError(403);
			return;
		}

		/** 用户检测 */
		boolean isCertifyId = User.dao.isCertify(user.getStr("username"));
		if (!isCertifyId) {
			forwardAction("/usercertify/certify");
			return;
		}

		/** Project 状态检测 */
		Integer status = p.getInt("status");
		if (status != 3 && status != 5) {
			renderError(403);
			return;
		}

		/** 更新为提交状态 */
		boolean result = p.set("status", 4).update();
		setAttr("result", result);
		setAttr("msg", "您的项目已提交");
		renderFreeMarker("result.ftl");
	}

	/**
	 * 查询审批意见 by YJ 2015/10/21
	 */
	public void findExecute() {
		ResultJson resultJson = new ResultJson();
		long project_id = getParaToLong("project_id");
		int status = getParaToInt("status");
		ProjectAuditRecord par = new ProjectAuditRecord();
		par = ProjectAuditRecord.dao.findByProject(project_id, status);
		if (par != null) {
			resultJson.setCode(Code.S_200);
			resultJson.setMessage(par.getStr("message"));
		} else {
			resultJson.setCode(Code.F_100000);
			resultJson.setMessage("休息一下吧");
		}

		renderJson(resultJson);
	}
}
