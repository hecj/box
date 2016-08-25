package com.boxamazing.webfront.controller.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.PasswordUtil;
import com.boxamazing.service.sms.EmailService;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.TokenUtil;
import com.boxamazing.webfront.common.BaseController;
import com.boxamazing.webfront.interceptor.LoginInterceptor;
import com.boxamazing.webfront.util.CheckNum;
import com.boxamazing.webfront.util.MD5;
import com.boxamazing.webfront.util.UserUtil;
import com.boxamazing.webfront.util.constant.EmailType;
import com.jfinal.aop.Before;
/**
 * UserController ()
 */

@Before({LoginInterceptor.class}) 

public class UserController extends BaseController {
	
	private static final Log log = LogFactory.getLog(UserController.class);
	
    public void index() {
        setAttr("pg", User.dao._page(getParaToInt(0, 1), 10));
        //render("login.ftl");
    }

    @Before(UserValidator.class)
    public void save() {
        getModel(User.class).save();
        redirect("/user");
    }

    public void edit() {
        setAttr("user", User.dao.findById(getParaToInt()));
    }

    @Before(UserValidator.class)
    public void update() {
        getModel(User.class).update();
        redirect("/user");
    }
    
    
	/**
	 * 第三方登录之后设置密码
	 */
	public void resetPwd(){
		log.info("手机号为"+getPara("phone")+"的用户正在设置登录密码");
		User user = User.dao.findByUsername(getPara("phone"));
		String phone = user.getStr("phone");
		if(getPara("phone") == null || !getPara("phone").equals(phone)){
			renderText("phoneError");
			return;
		}
		try{			
			user.set("password", PasswordUtil.encryptPassword(getPara("password")));
			user.update();
			log.info("手机号为"+getPara("phone")+"的用户登录密码设置完成");
			renderText("true");
			
		}catch(Exception e){
			e.printStackTrace();
			log.info("手机号为"+getPara("phone")+"的用户登录密码设置失败");
			renderText("false");
			return;
		}
		
	}
	
	/**
	 * 修改密码
	 */
	public void changePwd() {
		User user = UserUtil.getUser(getSession());
		String surepassword = getPara("surepassword");
		String newPassword = getPara("newPassword");
		
		if(!PasswordUtil.encryptPassword(getPara("oldPassword")).equals(
				user.get("password"))){
			renderText("false");
			return;
		}
		
		if(!newPassword.equals(surepassword)){
			renderText("noEqual");
			return;
		}
		user.set("password",PasswordUtil.encryptPassword(newPassword));
		user.update();
		renderText("true");
	}
	
	/**
	 * 安全中心--绑定待验证邮箱
	 */
	public void bindValidEmail(){
		User user = UserUtil.getUser(getSession());
		log.info("用户【id:"+ user.get("id") +"】正在绑定邮箱");
		String email = getPara("email");
		String code = getPara("code");
		String randString = (String) getSession().getAttribute("randString");
		if(code == null || !code.equalsIgnoreCase(randString)){
			renderText("codeNotEq");
			return;
		}
		
		if (email == null && !CheckNum.isEmail(email)) {
			log.info("邮件格式不正确" + email);
			renderText("errorType");
			return;
		}
			
		User n_user = User.dao.findByEmail(email);
		if(n_user != null){
			log.info("该邮箱已注册:" + email);
			renderText("registed");
			return;
		}
		
		//保存验证邮箱，并且发送验证邮件
		user.set("valid_email", email).set("email",  null).update();
		if (sendEmail(user, email)) {
			log.info(email+"验证邮件发送成功");
			renderText("true");
			return;
		}
		renderText("false");
	}
	
	private Boolean sendEmail(User user, String email){
		if(email == null || !CheckNum.isEmail(email)){
			return false;
		}
		String uuid = TokenUtil.generalToken();
		String urlMd5 = MD5.sign(uuid, email, "UTF-8");
		urlMd5 = urlMd5.concat(MD5.sign("bind", "hezi", "UTF-8"));
		return EmailService.sendEmail(urlMd5, email, EmailType.bind_message, "bind");
	}
}


