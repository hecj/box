package com.boxamazing.admin.user;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.common.BaseController;
import com.boxamazing.service.excel.CreateExcel;
import com.boxamazing.service.excel.SimpleCreateExcel;
import com.boxamazing.service.excel.SimpleSheet;
import com.boxamazing.service.u.model.PUser;
import com.boxamazing.service.user.model.UserFeedBack;
import com.boxamazing.util.ResultJson;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class UserFeedBackController extends BaseController{
	private static final Log log = LogFactory.getLog(UserController.class);
	
	//查询所有用户反馈记录（分页显示）
	
	public void index(){
		int pageNumber = getParaToInt(0, 1);
		int is_delete  = getParaToInt(1, 0);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", pageNumber);
		params.put("size", 10);
		params.put("is_delete", is_delete);
		
		Page<UserFeedBack> userFeedBackList = UserFeedBack.dao.findRecordByCondition(params);
    	setAttr("userFeedBackList", userFeedBackList);
        setAttr("is_delete", is_delete);
        render("index.ftl");
	}
	
	
	//查看用户反馈详细信息
	public void detail(){
		String id = getPara(0);
		log.info("查询id为："+id);
		UserFeedBack userFeedBack = UserFeedBack.dao.findById(id);
		setAttr("userFeedBack",userFeedBack);
		render("detail.ftl");
	}
	
	//批量删除用户反馈信息
	public void deleteByIds() {
		UserFeedBack.dao.deleteByIds(getParaValues("id"));
		redirect("/userFeedBack");
		return;
	}
	
	//单个删除
	public void delete(){
		String id = getPara("id");
		log.info("查询id为："+id);
		UserFeedBack.dao.deleteOne(id);
		redirect("/userFeedBack");
	}
	
	//导出excel
	public void export() throws IOException{
		String[] ids = getParaValues("id");
		List<UserFeedBack> feedBackList = UserFeedBack.dao.findByIds(ids);
		HttpServletResponse response = getResponse();
		OutputStream os = response.getOutputStream();// 取得输出流  
        response.reset();// 清空输出流  
  
        //不能用用中文设置 filename，会出错    文件名用时间来定义
        String fileName = ""+System.currentTimeMillis()+".xls";
        response.setHeader("Content-disposition", "attachment; filename="+fileName);// 设定输出文件头  
        response.setContentType("application/msexcel");// 定义输出类型  
        
		CreateExcel createExcel = new SimpleCreateExcel();
		
		SimpleSheet sheet = new SimpleSheet();
		sheet.setColNames(new String[]{"id号","用户id","反馈内容","联系方式","时间"});
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Vector<String[]> datas = new Vector<String[]>();
		for(int i=0;i<feedBackList.size();i++){
			UserFeedBack userFeedBack = (UserFeedBack)(feedBackList.get(i));
			String id = userFeedBack.getLong("id").toString();
			String userid = userFeedBack.getLong("user_id").toString();
			String content = userFeedBack.get("content","匿名");
			String remark = userFeedBack.get("remark","匿名");
			String time =simpleDateFormat.format(userFeedBack.get("create_at"));
			datas.add(new String[]{id,userid,content,remark,time});
		}
		sheet.setDatas(datas);
		sheet.setSheetName("表格名称");
		
		try {
			WritableWorkbook w = createExcel.create(sheet,os);
			w.write();
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		renderNull();
	}


}
