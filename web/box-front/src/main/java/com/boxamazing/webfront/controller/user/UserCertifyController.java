package com.boxamazing.webfront.controller.user;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.fans.model.Fans;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.Constant;
import com.boxamazing.webfront.interceptor.LoginInterceptor;
import com.boxamazing.webfront.util.ResultJson;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.aop.Before;
import com.jfinal.core.JfinalxController;

/**
 * 用户实名认证
 * Created by jhl on 15/8/25.
 */
@Before(LoginInterceptor.class)
public class UserCertifyController extends JfinalxController {
	private static final Log log = LogFactory.getLog(UserCertifyController.class);
    /**
     * 开始用户认证
     */
    public void certify() {
        User user = UserUtil.getUser(getSession());
        if (null == user) {
            renderError(403);
            return;
        }
      
        User u = User.dao.findById(user.getLong("id"));
        int status = u.getInt("certify");
       
    	// 获取粉丝数量
		Long fans_count = Fans.dao.getFansCountByPromoder(user.getLong("id"));
		setAttr("fans", fans_count);
    	setAttr("user",u);
    	log.info(u.getStr("phone"));
    	//姓名加 *
    	if(u.getStr("realname")!=null&&!"".equals(u.getStr("realname"))){
    		if(u.getStr("realname").length()<=0){
    			log.error("该用户尚未进行实名认证,真实性有待考虑");
    			redirect("/");
    			return;
    		}
    		setAttr("userreal",u.getStr("realname").substring(0,1)+"**");
    	}
    	if(u.getStr("idno")!=null&&!"".equals(u.getStr("idno"))){
    		//身份证加*
    		if(u.getStr("idno").length()!=18){
    			log.info("身份证位数有误,请检查数据库");
    			redirect("/");
    			return;
    		}else{
    			setAttr("useridno",u.getStr("idno").substring(0, 6)+"********"+u.getStr("idno").substring(14));
    		}
    	}
    	// 静态URL
    	setAttr("certify_now",status);
    	log.info("当前状态为："+status);
    	setAttr("STATIC_URL", Constant.STATIC_URL);
        renderFreeMarker("certify.ftl");
       
    }

    /**
     * 提交验证信息
     */
    @SuppressWarnings("null")
	public void doCertify() {

        User user = UserUtil.getUser(getSession());
        if (null == user) {
            renderError(403);
            return;
        }
        //刷新session
        User newUser = User.dao.findById(user.getLong("id"));
        UserUtil.setUser(newUser, getSession());
       
        String realname = getPara("realname");
        String idno = getPara("ID-number");
        int sex = getParaToInt("sex");
        String idphoto = getPara("idphoto","");
       
        List<User> list = User.dao.isSameIdno(idno);
        ResultJson resultJson = new ResultJson();
        if((list!=null&&list.size()>0&&!list.get(0).getLong("id").equals(newUser.getLong("id")))||(idno.length()!=18)){
        	resultJson.setCode(404L);
        }else{
        	newUser.set("realname", realname).set("idno", idno).set("sex", sex)
					.set("idphoto", idphoto).set("certify", 1).update();
			resultJson.setCode(200L);
        }
        
        renderJson(resultJson);
        
    }
}
