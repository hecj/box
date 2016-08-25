package com.boxamazing.admin.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.common.BaseController;
import com.boxamazing.common.StringUtil;
import com.boxamazing.common.interceptor.UserUtil;
import com.boxamazing.service.comment.model.Comment;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.u.model.PUser;
import com.boxamazing.service.user.model.User;
import com.boxamazing.util.ResultJson;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

/**
 * 评论管理
 */
public class CommentController extends BaseController {
 
	public static Log log = LogFactory.getLog(CommentController.class);
	
	/**
	 * 评论列表
	 */
	public void index(){
		PUser puser = UserUtil.getU(getSession());
		log.info(puser.get("username")+"["+puser.toString()+"]:SendEmailRecordController--index(), 评论管理");
		try {
			Long page  = 1L;
			if(this.isParaExists(0)){
				page = getParaToLong(0);
			}
			
			Long project_id = getParaToLong("project_id");
			Long user_id = getParaToLong("user_id");
			String project_name = getPara("project_name");
			String content = getPara("content");
			String phone = getPara("phone");
			
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("page", page);
			params.put("size", 10);
			if(project_id != null){
				params.put("project_id", project_id);
			}
			if(user_id != null){
				params.put("user_id", user_id);
			}
			if(!StringUtil.isNullOrEmpty(project_name)){
				params.put("project_name", project_name);
			}
			if(!StringUtil.isNullOrEmpty(content)){
				params.put("content", content);
			}
			if(!StringUtil.isNullOrEmpty(phone)){
				params.put("phone", phone);
			}

			Page<Comment> commentPage = Comment.dao.findByCommentParams(params);
			setAttr("commentPage", commentPage);
			setAttr("project_id", project_id);
			setAttr("project_name", project_name);
			setAttr("content", content);
			setAttr("phone", phone);
			setAttr("user_id", user_id);
			
			render("/page/message/comment/index.ftl");
		} catch (Exception e) {
			log.error(e);
			renderError(403);
		}
		
	}
	
	/**
	 * 评论详情
	 */
	public void detail(){
		Long comment_id = getParaToLong(0);
		try {
			log.info(" comment_id : " +comment_id );
			Comment comment = Comment.dao.findById(comment_id);
			setAttr("comment", comment);
			Project project = Project.dao.findById(comment.getLong("project_id"));
			setAttr("project", project);
			User user = User.dao.findById(comment.getLong("user_id"));
			setAttr("user", user);
			render("/page/message/comment/detail.ftl");
		} catch (Exception e) {
			renderError(403);
			e.printStackTrace();
		}
	}
	
	/**
	 * 批量隐藏评论
	 */
	@Before(POST.class)
	public void hidden(){
		final String[] comment_ids = getParaValues("comment_id");
		try {
			
			for(String comment_id : comment_ids){
				log.info("正在隐藏评论："+comment_id);
				Comment comment = Comment.dao.findById(comment_id);
				if(comment.getInt("is_delete") != 1){
					// 隐藏评论
					comment.set("is_delete", 1);
					comment.update();
					
					// 一级评论，子评论一块处理
					if(comment.getInt("level") == 1){
						log.info("一级评论时，将子评论一起处理");
						Db.update("update comment set is_delete = 1 where parent_id = ? and project_id = ?",new Object[]{comment_id,comment.getLong("project_id")});
					}else{
						log.info("二级和三级评论回复一级评论恢复数量");
						// 二级和三级 更改 回复数量
						Comment parentComment = Comment.dao.findById(comment.getLong("parent_id"));
						parentComment.set("reply_num", parentComment.getInt("reply_num")-1).update();
					}
				}
			}
			renderJson(new ResultJson(200l, "success"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new ResultJson(-100000l, "服务器异常"));
		}
		
	}
	
	/**
	 * 批量删除评论
	 */
	@Before(POST.class)
	public void delete(){
		final String[] comment_ids = getParaValues("comment_id");
		try {
			
			for(String comment_id : comment_ids){
				log.info("正在删除评论："+comment_id);
				Comment comment = Comment.dao.findById(comment_id);
				if(comment.getInt("is_delete") != 2){
					// 删除评论
					comment.set("is_delete", 2);
					comment.update();
					
					// 一级评论，子评论一块处理
					if(comment.getInt("level") == 1){
						log.info("一级评论时，将子评论一起处理");
						Db.update("update comment set is_delete = 2 where parent_id = ? and project_id = ?",new Object[]{comment_id,comment.getLong("project_id")});
					} else{
						log.info("二级和三级评论回复一级评论恢复数量");
						// 二级和三级 更改 回复数量
						Long reply_num = Db.queryLong("select count(*) from comment where parent_id=? and is_delete=0 and project_id = ? ",new Object[]{comment.getLong("parent_id"),comment.getLong("project_id")});
						log.info("reply_num："+reply_num);
						Comment parentComment = Comment.dao.findById(comment.getLong("parent_id"));
						parentComment.set("reply_num", reply_num.intValue()).update();
					}
				}
			}
			renderJson(new ResultJson(200l, "success"));
		} catch (Exception e) {
			renderJson(new ResultJson(-100000l, "服务器异常"));
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 批量显示评论
	 */
	@Before(POST.class)
	public void show(){
		final String[] comment_ids = getParaValues("comment_id");
		try {
			
			for(String comment_id : comment_ids){
				log.info("正在显示评论："+comment_id);
				Comment comment = Comment.dao.findById(comment_id);
				if(comment.getInt("is_delete") != 0){
					// 显示评论
					comment.set("is_delete", 0);
					comment.update();
					
					// 一级评论，子评论一块处理
					if(comment.getInt("level") == 1){
						log.info("一级评论时，将子评论一起处理");
						Db.update("update comment set is_delete = 0 where parent_id = ? and project_id = ?",new Object[]{comment_id,comment.getLong("project_id")});
					}else{
						log.info("二级和三级评论回复一级评论恢复数量");
						// 二级和三级 更改 回复数量
						Comment parentComment = Comment.dao.findById(comment.getLong("parent_id"));
						parentComment.set("reply_num", parentComment.getInt("reply_num")+1).update();
					}
				}
			}
			renderJson(new ResultJson(200l, "success"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new ResultJson(-100000l, "服务器异常"));
		}
		
	}
	
	
}
