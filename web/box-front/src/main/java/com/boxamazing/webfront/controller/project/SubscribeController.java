package com.boxamazing.webfront.controller.project;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.comment.model.Comment;
import com.boxamazing.service.fans.model.Fans;
import com.boxamazing.service.orders.model.Orders;
import com.boxamazing.service.subscribe.model.Subscribe;
import com.boxamazing.service.user.model.User;
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
 * 产品详情页 赞功能 Created by jhl on 15/8/13.
 */
public class SubscribeController extends BaseController {

	private static final Log log = LogFactory.getLog(SubscribeController.class);

	private static final int SUBSCRIBE_PAGE_SIZE = PropKit.use("page_size.properties").getInt("subscribePageSize");

	/**
	 * 通过ID-TYPE查看订阅(关注)人数.
	 */
	public void getCount() {
		Long pid = getParaToLong(0);
		Long count = Subscribe.dao.getSubCountByPid(pid);
		renderJson(count);
	}

	/**
	 * 用户uid订阅projectdId信息 by HECJ 关注项目,取消关注项目
	 */
	@Before(LoginInterceptor.class)
	public void sub() {
		Long user_id = -1l;
		Long project_id = -1l;
		try {
			User user = UserUtil.getUser(getSession());
			if (user == null) {
				renderJson(new ResultJson(-1L, "未登录"));
				return;
			}
			user_id = user.getLong("id");
			project_id = getParaToLong("project_id");

			// 1:关注，2：取消
			int sub_type = getParam("sub_type", -1);

			List<Subscribe> subList = Subscribe.dao.findByProjectIdAndUserId(project_id, user_id);
			// 关注
			if (sub_type == 1) {
				if (subList != null && subList.size() > 0) {
					log.info("用户已关注 user_id{},project_id{},sub_type{}:" + user_id + "," + project_id + "," + sub_type);
				} else {
					Subscribe subscribe = new Subscribe();
					subscribe.set("project_id", project_id);
					subscribe.set("user_id", user_id);
					subscribe.set("create_at", System.currentTimeMillis());
					subscribe.save();
					log.info("用户成功 user_id{},project_id{},sub_type{}:" + user_id + "," + project_id + "," + sub_type);
					renderJson(new ResultJson(Code.S_200, "关注成功"));
					return;
				}
			} else if (sub_type == 2) {
				if (subList.size() > 0) {
					subList.get(0).delete();
					log.info("用户取消关注成功 user_id{},project_id{},sub_type{}:" + user_id + "," + project_id + "," + sub_type);
					renderJson(new ResultJson(Code.S_200, "取消关注成功"));
					return;
				} else {
					log.info("用户未关注，无需取消 user_id{},project_id{},sub_type{}:" + user_id + "," + project_id + "," + sub_type);
				}
			} else {
				log.info("操作有误 user_id{},project_id{},sub_type{}:" + user_id + "," + project_id + "," + sub_type);
				renderJson(new ResultJson(Code.F_1, "请求不存在"));
				return;
			}
		} catch (Exception e) {
			log.error("用户关注/取消关注 user_id{},project_id{}:" + user_id + "," + project_id);
			log.error(e.getMessage());
			e.printStackTrace();
			renderJson(new ResultJson(Code.F_100000, "请求超时"));
		}
	}

	/**
	 * 通过uid-projectIds,查看此用户是否已经订阅(关注)
	 */
	public void isSub() {
		Long pid = getParaToLong(0);
		Long user_id = getParaToLong(1);
		renderJson(Subscribe.dao.isSub(pid, user_id));
	}

	/**
	 * 个人中心-我的关注项目
	 * 
	 * @author XuXD
	 */
	@Before(LoginInterceptor.class)
	public void mysub() {
		// 获取登录用户信息
		User user = UserUtil.getUser(getSession());
		setAttr("user", user);
		long userId = user.getLong("id");

		// 获取分页页数
		int page = getParam(0, 1);

		// 获取关注项目
		Page<Subscribe> subscribePage = null;
		try {
			subscribePage = Subscribe.dao.findSubscribeByPage(userId, page, SUBSCRIBE_PAGE_SIZE);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取我关注的项目异常", e);
			return;
		}
		if (subscribePage == null) {
			log.info("subscribePage is null");
			setAttr("msg", "subscribePage is null");
			renderJson();
			return;
		}

		// 日期格式化
		for (Subscribe subscribe : subscribePage.getList()) {
			String format = FormatUtil.MyDateFormat("yyyy-MM-dd HH:mm:ss", subscribe.getLong("create_at"));
			subscribe.set("create_at", format);
		}
		
		log.info("page:[pageNumber:" + subscribePage.getPageNumber() + ",pageSize:" + subscribePage.getPageSize() + ",totalPage:"
				+ subscribePage.getTotalPage() + ",totalRow:" + subscribePage.getTotalRow() + "]");
		setAttr("subscribeList", subscribePage.getList());
		setAttr("pageNumber", subscribePage.getPageNumber());
		setAttr("pageSize", subscribePage.getPageSize());
		setAttr("totalPage", subscribePage.getTotalPage());
		setAttr("totalRow", subscribePage.getTotalRow());

		render("mysub.ftl");
	}
}
