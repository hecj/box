package com.boxamazing.webfront.controller.project;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.comment.model.Comment;
import com.boxamazing.service.comment_support.model.Comment_support;
import com.boxamazing.service.fans.model.Fans;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.project.model.ProjectProgress;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.Constant;
import com.boxamazing.service.util.FormatUtil;
import com.boxamazing.webfront.common.BaseController;
import com.boxamazing.webfront.interceptor.LoginInterceptor;
import com.boxamazing.webfront.util.Code;
import com.boxamazing.webfront.util.ResultJson;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * 项目详情(介绍,进度,评论,支持名单)
 * 
 * @author XuXD
 * 
 */
public class ProjectDetailController extends BaseController {
	private static final Log log = LogFactory.getLog(ProjectDetailController.class);

	private static final int COMMENT_PAGE_SIZE = PropKit.use("page_size.properties").getInt("commentPageSize");
	private static final int SUPPORT_PAGE_SIZE = PropKit.use("page_size.properties").getInt("supportPageSize");
	private static final int REPLY_PAGE_SIZE = PropKit.use("page_size.properties").getInt("replyPageSize");

	/**
	 * 众筹项目-项目展示页-项目介绍详情 获取项目介绍
	 * 
	 * @author XuXD
	 */
	public void getContext() {
		// 获取项目ID
		long projectId = getParam(0, -1L);

		// 项目视频连接
		String video = "";
		// 项目介绍
		String content = "";
		// 项目最新动态
		ProjectProgress projectProgress = null;
		try {
			Project project = Project.dao.findById(projectId);
			video = project.getStr("video");
			content = project.getStr("context");
			projectProgress = ProjectProgress.dao.findLatestOne(projectId);
		} catch (Exception e) {
			log.error("获取项目介绍信息异常", e);
			renderJson(new ResultJson(Code.F_100000, "加载失败"));
			return;
		}

		// 日期格式化
		try {
			if (projectProgress != null) {
				String format = FormatUtil.MyDateFormat("MM-dd", projectProgress.getLong("progress_at"));
				projectProgress.set("progress_at", format);
			}
		} catch (Exception e) {
			log.error("日期格式化异常", e);
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("content", content);
		map.put("video", video);
		map.put("projectProgress", projectProgress);

		renderJson(new ResultJson(Code.S_200, map, "加载失败"));
	}

	/**
	 * 众筹项目-项目展示页-项目进展 获取项目进度
	 * 
	 * @author XuXD
	 */
	public void getSchedule() {
		// 获取项目ID
		long projectId = getParam(0, -1L);

		// 获取项目进度
		List<ProjectProgress> projectProgressList = null;
		try {
			projectProgressList = ProjectProgress.dao.findByProjectId(projectId);
		} catch (Exception e) {
			log.error("获取项目进度异常", e);
			renderJson(new ResultJson(Code.F_100000, "加载失败"));
			return;
		}

		// 日期格式化
		try {
			for (ProjectProgress progress : projectProgressList) {
				if (progress.getLong("progress_at") != null) {
					String format = FormatUtil.MyDateFormat("MM-dd", progress.getLong("progress_at"));
					progress.set("progress_at", format);
				}
			}
		} catch (Exception e) {
			log.error("日期格式化异常", e);
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("projectProgressList", projectProgressList);
		map.put("projectProgressDynamic", ProjectProgress.dao.getDynamic(projectId));

		renderJson(new ResultJson(Code.S_200, map, "加载成功"));
	}

	/**
	 * 众筹项目-项目展示页-评论 获取项目一级评论
	 * 
	 * @author XuXD
	 */
	public void getComment() {
		// 获取项目ID
		long projectId = getParam(0, -1L);

		// 获取分页页数
		int page = getParam(1, 1);

		// 获取项目评论
		Page<Comment> commentPage = null;
		try {
			if ("only".equals(getParam("mode", "")))
				commentPage = Comment.dao.findCommentByPage(projectId, Project.dao.getUserId(projectId), page, COMMENT_PAGE_SIZE);
			else
				commentPage = Comment.dao.findCommentByPage(projectId, page, COMMENT_PAGE_SIZE);
		} catch (Exception e) {
			log.error("获取项目评论异常", e);
			renderJson(new ResultJson(Code.F_100000, "加载失败"));
			return;
		}

		// 日期格式化
		try {
			for (Comment comment : commentPage.getList()) {
				if (comment.getLong("create_at") != null) {
					String format = FormatUtil.MyCommentDateFormat(comment.getLong("create_at"));
					comment.set("create_at", format);
				}
			}
		} catch (Exception e) {
			log.error("日期格式化异常", e);
		}

		log.info("page:[pageNumber:" + commentPage.getPageNumber() + ",pageSize:" + commentPage.getPageSize() + ",totalPage:"
				+ commentPage.getTotalPage() + ",totalRow:" + commentPage.getTotalRow() + "]");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("commentList", commentPage.getList());
		map.put("pageNumber", commentPage.getPageNumber());
		map.put("pageSize", commentPage.getPageSize());
		map.put("totalPage", commentPage.getTotalPage());
		map.put("totalRow", commentPage.getTotalRow());
		map.put("commentDynamic", Comment.dao.getDynamic(projectId));
		map.put("STATIC_URL", Constant.STATIC_URL);
		map.put("loginUser", UserUtil.getUser(getSession()));

		renderJson(new ResultJson(Code.S_200, map, "加载成功"));
	}

	/**
	 * 众筹项目-项目展示页-支持 获取项目支持者
	 * 
	 * @author XuXD
	 */
	public void getSupport() {
		// 获取项目ID
		long projectId = getParam(0, -1L);

		// 获取分页页数
		int page = getParam(1, 1);

		// 获取项目订单
		Page<Orders> orderPage = null;
		try {
			orderPage = Orders.dao.findByPage(projectId, page, SUPPORT_PAGE_SIZE);
		} catch (Exception e) {
			log.error("获取项目订单异常", e);
			renderJson(new ResultJson(Code.F_100000, "加载失败"));
			return;
		}

		// 日期格式化
		try {
			for (Orders orders : orderPage.getList()) {
				if (orders.getLong("pay_at") != null) {
					String format = FormatUtil.MyDateFormat("yyyy-MM-dd HH:mm:ss", orders.getLong("pay_at"));
					orders.set("pay_at", format);
				}
			}
		} catch (Exception e) {
			log.error("日期格式化异常", e);
		}

		log.info("page:[pageNumber:" + orderPage.getPageNumber() + ",pageSize:" + orderPage.getPageSize() + ",totalPage:"
				+ orderPage.getTotalPage() + ",totalRow:" + orderPage.getTotalRow() + "]");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("orderList", orderPage.getList());
		map.put("pageNumber", orderPage.getPageNumber());
		map.put("pageSize", orderPage.getPageSize());
		map.put("totalPage", orderPage.getTotalPage());
		map.put("totalRow", orderPage.getTotalRow());
		map.put("ordersDynamic", Orders.dao.getDynamic(projectId));
		map.put("STATIC_URL", Constant.STATIC_URL);

		renderJson(new ResultJson(Code.S_200, map, "加载成功"));
	}

	/**
	 * 获取二三级评论
	 * 
	 * @author XuXD
	 */
	public void getReply() {
		// 获取评论ID
		long commentId = getParam(0, -1L);

		// 获取分页页数
		int page = getParam(1, 1);

		// 获取回复列表
		Page<Comment> replayPage = null;
		try {
			replayPage = Comment.dao.findReplyByPage(commentId, page, REPLY_PAGE_SIZE);
		} catch (Exception e) {
			log.error("获取回复异常", e);
			renderJson(new ResultJson(Code.F_100000, "加载失败"));
			return;
		}

		// 日期格式化
		try {
			for (Comment comment : replayPage.getList()) {
				if (comment.getLong("create_at") != null) {
					String format = FormatUtil.MyCommentDateFormat(comment.getLong("create_at"));
					comment.set("create_at", format);
				}
			}
		} catch (Exception e) {
			log.error("日期格式化异常", e);
		}

		log.info("page:[pageNumber:" + replayPage.getPageNumber() + ",pageSize:" + replayPage.getPageSize() + ",totalPage:"
				+ replayPage.getTotalPage() + ",totalRow:" + replayPage.getTotalRow() + "]");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("replayList", replayPage.getList());
		map.put("pageNumber", replayPage.getPageNumber());
		map.put("pageSize", replayPage.getPageSize());
		map.put("totalPage", replayPage.getTotalPage());
		map.put("totalRow", replayPage.getTotalRow());

		renderJson(new ResultJson(Code.S_200, map, "加载成功"));
	}

	/**
	 * 发表评论
	 * 
	 * @author XuXD
	 */
	@Before(LoginInterceptor.class)
	public void post() {
		// 获取登录用户ID
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderJson(new ResultJson(Code.F_1, "请登录"));
			return;
		}
		long userId = user.getLong("id");

		// 获取项目ID
		long projectId = getParam(0, -1L);

		// 获取评论内容
		String content = getParam("content", "");

		// 数据校验
		if (null == content || content.isEmpty()) {
			renderJson(new ResultJson(Code.F_02, "请输入内容"));
			return;
		}
		if (content.length() > 300) {
			renderJson(new ResultJson(Code.F_02, "不能超过300个字符"));
			return;
		}

		// 判断权限
		Boolean res = false;
		try {
			res = Comment.dao.isSupport(projectId, userId);
			if (!res) {
				renderJson(new ResultJson(Code.F_02, "您没有对该项目进行支持，不能进行评论"));
				return;
			}
		} catch (Exception e) {
			log.error("判断权限异常", e);
			renderJson(new ResultJson(Code.F_100000, "评论失败"));
			return;
		}

		// 添加评论
		boolean result = false;
		try {
			String ip = getRemortIp(getRequest());
			result = new Comment().set("project_id", projectId).set("user_id", userId).set("create_at", System.currentTimeMillis())
					.set("content", content).set("level", 1).set("ip", ip).save();
			if (!result) {
				log.info("添加评论失败");
				renderJson(new ResultJson(Code.F_100000, "评论失败"));
				return;
			}
		} catch (Exception e) {
			log.error("添加评论异常", e);
			renderJson(new ResultJson(Code.F_100000, "评论失败"));
			return;
		}

		renderJson(new ResultJson(Code.S_200, "评论成功"));
	}

	/**
	 * 发表回复
	 * 
	 * @author XuXD
	 */
	@Before(LoginInterceptor.class)
	public void reply() {
		// 获取登录用户ID
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderJson(new ResultJson(Code.F_1, "请登录"));
			return;
		}
		long userId = user.getLong("id");

		// 获取项目ID
		long projectId = getParam(0, -1L);

		// 获取一级评论ID
		long parentId = getParam(1, -1L);

		// 获取二级评论用户ID
		long secondUserId = getParam(2, 0);
		
		// 获取二级评论ID
		long secondId = getParam(3, 0);

		// 获取回复内容
		String content = getParam("content", "");

		// 数据校验
		if (null == content || content.isEmpty()) {
			renderJson(new ResultJson(Code.F_02, "请输入内容"));
			return;
		}
		if (content.length() > 300) {
			renderJson(new ResultJson(Code.F_02, "不能超过300个字符"));
			return;
		}

		// 判断权限
		Boolean res = false;
		try {
			res = Comment.dao.isSupport(projectId, userId);
			if (!res) {
				renderJson(new ResultJson(Code.F_02, "请先支持项目，再来评论"));
				return;
			}
		} catch (Exception e) {
			log.error("判断权限异常", e);
			renderJson(new ResultJson(Code.F_100000, "回复失败"));
			return;
		}

		// 添加回复内容
		Boolean isAddComment = false;
		try {
			int level = parentId == 0 ? 1 : secondUserId == 0 ? 2 : 3;
			String ip = getRemortIp(getRequest());
			isAddComment = new Comment().set("project_id", projectId).set("user_id", userId).set("content", content)
					.set("parent_id", parentId).set("second_id", secondId).set("second_user_id", secondUserId).set("level", level)
					.set("create_at", System.currentTimeMillis()).set("ip", ip).save();
			if (!isAddComment) {
				log.info("添加回复内容失败");
				renderJson(new ResultJson(Code.F_100000, "回复失败"));
				return;
			}
		} catch (Exception e) {
			log.error("添加回复内容异常", e);
			renderJson(new ResultJson(Code.F_100000, "回复失败"));
			return;
		}

		// 更新父评论
		Boolean isUpdateComment = false;
		Comment parentComment = null;
		try {
			parentComment = new Comment().findById(parentId);
			isUpdateComment = parentComment.set("reply_num", parentComment.getInt("reply_num") + 1).update();
			if (!isUpdateComment) {
				log.info("更新父评论失败");
				renderJson(new ResultJson(Code.F_100000, "回复失败"));
				return;
			}
		} catch (Exception e) {
			log.error("更新父评论异常");
			renderJson(new ResultJson(Code.F_100000, "回复失败"));
			return;
		}
		
		renderJson(new ResultJson(Code.S_200, "回复成功"));
	}

	/**
	 * 赞
	 * 
	 * @author XuXD
	 */
	@Before(LoginInterceptor.class)
	public void support() {
		// 获取登录用户ID
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderJson(new ResultJson(Code.F_1, "请登录"));
			return;
		}
		long userId = user.getLong("id");

		// 获取评论ID
		long commentId = getParam(0, -1L);

		// 赞、取消赞
		try {
			Comment comment = Comment.dao.findById(commentId);
			Comment_support support = Comment_support.dao.getSupport(commentId, userId);
			if (null != support) {
				//删除赞记录
				support.delete();
			} else {
				//添加赞记录
				new Comment_support().set("comment_id", commentId).set("user_id", userId)
									 .set("create_at", System.currentTimeMillis()).save();
			}
			//更新评论
			comment.set("support_num", Comment_support.dao.getCount(commentId)).update();
			
			renderJson(new ResultJson(Code.S_200, comment.getLong("support_num")));
		} catch (Exception e) {
			log.error("赞异常", e);
			renderJson(new ResultJson(Code.F_100000, "赞失败"));
		}
	}

	/**
	 * 删除评论
	 * 
	 * @author XuXD
	 * @date 2015-10-29
	 */
	@Before(LoginInterceptor.class)
	public void del() {
		// 获取评论ID
		long commentId = getParam(0, -1L);

		try {
			// 删除评论
			Comment comment = Comment.dao.findById(commentId);
			boolean result = comment.set("is_delete", 1).update();
			if (!result) {
				log.info("删除评论失败");
				renderJson(new ResultJson(Code.F_100000, "删除失败"));
				return;
			}
			// 判断级别
			if (comment.getInt("level") != 1) {
				// 评论数减一
				Comment parentComment = Comment.dao.findById(comment.getLong("parent_id"));
				result = parentComment.set("reply_num", parentComment.getInt("reply_num")-1).update();
				log.info("父评论评论数减一:"+result);
			} else {
				// 删除子评论
				long parentId = comment.getLong("id");
				int count = Comment.dao.delByParentId(parentId);
				log.info("id:"+parentId+":删除子评论"+count+"条");
			}
		} catch (Exception e) {
			log.error("删除评论异常", e);
			renderJson(new ResultJson(Code.F_100000, "删除失败"));
			return;
		}

		renderJson(new ResultJson(Code.S_200, "删除成功"));
	}
	
	/**
	 * 当前登录用户关注项目发起人
	 */
	@Before(LoginInterceptor.class)
	public void sub(){
		Long userid = -1l;
		Long promoderid = -1l;
		try {
			User user = UserUtil.getUser(getSession());
			if (user == null) {
				renderJson(new ResultJson(-1L, "未登录"));
				return;
			}
			userid = user.getLong("id");
			promoderid = getParaToLong("promoderid");

			// 1:关注，2：取消
			int sub_type = getParam("sub_type", -1);

			List<Fans> subList = Fans.dao.findByPromoderIdAndUserId(promoderid, userid);
			// 关注
			if (sub_type == 1) {
				if (subList != null && subList.size() > 0) {
					log.info("用户已关注项目发起人user_id{},promoderid{},sub_type{}:" + userid + "," + promoderid + "," + sub_type);
					renderJson(new ResultJson(-20L, "已关注"));
				} else {
					Fans fans = new Fans();
					fans.set("promoderid", promoderid);
					fans.set("userid", userid);
					fans.set("create_at", System.currentTimeMillis());
					fans.save();
					log.info("用户关注项目发起人成功 user_id{},promoderid{},sub_type{}:" + userid + "," + promoderid + "," + sub_type);
					renderJson(new ResultJson(Code.S_200, "关注成功"));
				}
			} else if (sub_type == 2) {
				if (subList.size() > 0) {
					subList.get(0).delete();
					log.info("用户取消关注项目发起人成功 user_id{},promoderid{},sub_type{}:" + userid + "," + promoderid + "," + sub_type);
					renderJson(new ResultJson(Code.S_200, "取消关注成功"));
				} else {
					log.info("用户未关注项目发起人，无需取消 user_id{},promoderid{},sub_type{}:" + userid + "," + promoderid + "," + sub_type);
				}
			} else {
				log.info("操作有误 user_id{},promoderid{},sub_type{}:" + userid + "," + promoderid + "," + sub_type);
				renderJson(new ResultJson(-21L, "操作有误"));
			}
		} catch (Exception e) {
			log.error("用户关注/取消关注项目发起人 user_id{},promoderid{}:" + userid + "," + promoderid);
			log.error(e.getMessage());
			renderJson(new ResultJson(Code.F_100000, "请求超时"));
		}
	}
}
