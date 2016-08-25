package com.boxamazing.admin.sys;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.boxamazing.common.BaseController;
import com.boxamazing.common.interceptor.UserUtil;
import com.boxamazing.service.common.PasswordUtil;
import com.boxamazing.service.r.model.R;
import com.boxamazing.service.u.model.PUser;
/**
 * PUserController 后台用户
 * by HECJ
 */
public class PUserController extends BaseController {
	
	private static final Log log = LogFactory.getLog(PUserController.class);
	
	public void index() {
		setAttr("pg", PUser.dao._page(getParaToInt(0, 1), 10));
		List<R> rl = R.findAllR("id", "sort");
		setAttr("rl", rl);
		render("index.ftl");
	}
	
	public void add() {
		List<R> rl = R.findAllR("id", "desc");
		setAttr("rl", rl);
	}
	
	@Before(UValidator.class)
	public void save() {
		try {
			PUser model = getModel(PUser.class,"puser");
			model.set("password", PasswordUtil.encryptPassword(model.getStr("password")));
			model.save();
			setAttr("_rs", model);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		redirect("/u");
	}
	
	/**
	 * 修改密码
	 * by HECJ
	 */
	public void updatepwd() {
		try {
			Long id = UserUtil.getU(getSession()).get("id");
			log.info("正在修改后台用户密码id{}："+id);
			setAttr("puser", PUser.dao.findById(id));
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		render("updatepwd.ftl");
	}
	
	/**
	 * 修改密码提交
	 */
	public void updatep() {
		String oldp = getPara("oldp");
		String newp = getPara("newp");
		if (StrKit.isBlank(oldp)) {
			setAttr("msg", "原密码不能为空"); 
		}else  if (StrKit.isBlank(newp)) {
			setAttr("msg", "新密码不能为空"); 
		} else {
			
			PUser puser = PUser.dao.findById(UserUtil.getU(getSession()).get("id"));
			if (oldp.equals(puser.get("password"))) {
				puser.set("password", PasswordUtil.encryptPassword(newp));
				puser.update();
				setAttr("msg", "密码修改成功,请重新登录"); 
				getSession().invalidate();
			}else {
				setAttr("msg", "原密码不匹配"); 
			}
		}
		render("updatepwd.ftl");
	}
	
	
	/**
	 * 去修改页面
	 * by HECJ
	 */
	public void edit() {
		
		try {
			Long id = getParaToLong();
			log.info("正在修改后台账户信息id{}:"+id);
			setAttr("puser", PUser.dao.findById(id));
			List<R> rl = R.findAllR("id", "desc");
			setAttr("rl", rl);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		render("edit.ftl");
	}
	
	public void update() {
		try {
			PUser puser = getModel(PUser.class,"puser");
			puser.set("password", PasswordUtil.encryptPassword(puser.getStr("password")));
			puser.update();
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		redirect("/u");
	}
	
	public void delete() {
		PUser.dao.deleteById(getParaToInt());
		redirect("/u");
	}
	
	public void deleteByIds() {
		PUser.dao.deleteByIds(getParaValues("id"));
		redirect("/u");
	}

	@Override
	public String getMn() {
		return "用户";
	}
}


