package com.boxamazing.admin.sys;

import java.util.ArrayList;
import java.util.Date;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.boxamazing.common.BaseController;
import com.boxamazing.common.CommonController;
import com.boxamazing.common.interceptor.UserUtil;
import com.boxamazing.service.msg.model.Msg;
import com.boxamazing.service.u.model.PUser;
/**
 * MsgController (消息)
 */
public class MsgController extends BaseController {
	public void index() {
		setAttr("pg", Msg.dao._page(getParaToInt(0, 1), 10));

		Object id = UserUtil.getU(getSession()).get("id");
		setAttr("pg",Msg.dao.paginate(getParaToInt(0, 1), 10, "select *", "from msg where  tid=? order by id desc",id));
		
		Db.update("update msg set tt=? where tid=?",new Date(),id);
		CommonController.mm.put(id+"", 0L);
		render("index.ftl");
	}
	public void history() {
		String frid = getRequest().getParameter("frid");
		Object id = UserUtil.getU(getSession()).get("id");
		setAttr("pg",Msg.dao.paginate(getParaToInt("page", 1), 10, "select *", "from msg where  (tid=? and fid=?) or ( tid=? and fid=?)  order by id desc",id,frid,frid,id));
		setAttr("frid", frid);
		//Db.update("update msg set tt=? where tid=?",new Date(),id);
		CommonController.mm.put(id+"", 0L);
		render("history.ftl");
	}
	
	public void add() {
		setAttr("ul", PUser.dao.find("select * from u where stat=0"));
		setAttr("tid", getPara("msg.tid"));
		setAttr("tu", getPara("msg.tu"));
	}
	
	@Before(MsgValidator.class)
	public void send() {
		Msg model = getModel(Msg.class);
		model.put("fid", UserUtil.getU(getSession()).get("id"));
		model.put("fu", UserUtil.getU(getSession()).get("u"));
		model.put("ft", new Date());
		model.save();
		setAttr("msg", "发送成功");
		setAttr("ul", new ArrayList());
		Long c = CommonController.mm.get(model.getLong("tid")+"");
		if (c==null) {
			c=0L;
		}
		CommonController.mm.put(model.getLong("tid")+"", c+1);
		
		
		redirect("/msg");
		
	}
	
//	@Before(MsgValidator.class)
//	public void read() {
//		Long id = getParaToLong();
//		Msg msg = Msg.dao.findById(id);
//		msg.put("stat", "1");
//		msg.update();
//		getModel(Msg.class).update();
//		redirect("/msg");
//	}
//	
	
	/*public void edit() {
		setAttr("msg", Msg.dao.findById(getParaToInt()));
	}
	
	
	public void delete() {
		Msg.dao.deleteById(getParaToInt());
		redirect("/msg");
	}
	
	public void deleteByIds() {
		Msg.dao.deleteByIds(getParaValues("id"));
		redirect("/msg");
	}
*/
	@Override
	public String getMn() {
		return "消息模块";
	}
}


