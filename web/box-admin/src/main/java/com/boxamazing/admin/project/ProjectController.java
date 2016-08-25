package com.boxamazing.admin.project;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.common.AppConfig;
import com.boxamazing.common.FileUtil;
import com.boxamazing.common.StringUtil;
import com.boxamazing.common.interceptor.UserUtil;
import com.boxamazing.service.code.Areas;
import com.boxamazing.service.code.Cities;
import com.boxamazing.service.code.Provinces;
import com.boxamazing.service.product.model.Product;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.project.model.ProjectAuditRecord;
import com.boxamazing.service.project.model.ProjectCategory;
import com.boxamazing.service.project.model.ProjectProgress;
import com.boxamazing.service.project.model.ProjectRecomm;
import com.boxamazing.service.sms.MessageService;
import com.boxamazing.service.sms.model.NoticeTemplate;
import com.boxamazing.service.subscribe.model.Subscribe;
import com.boxamazing.service.u.model.PUser;
import com.boxamazing.service.user.model.User;
import com.boxamazing.service.util.Constant;
import com.boxamazing.service.util.OrdersUtils;
import com.boxamazing.service.util.SqlUtil;
import com.boxamazing.util.ResultJson;
import com.jfinal.aop.Before;
import com.jfinal.core.JfinalxController;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

/**
 * 项目管理模块
 */
public class ProjectController extends JfinalxController {
	
	private static final Log log = LogFactory.getLog(ProjectController.class);
	
	/**
	 * by HECJ
	 */
    public void index() {
    	try {
    		int pageNumber = getParaToInt(0, 1);
    		String uPhone = getPara("uPhone");
    		String statusObj = getPara("status");
    		String name = getPara("name");
    		
    		log.info("pageNumber{}uPhone{}statusObj{}name{},:"+pageNumber+","+uPhone+","+statusObj+","+name);
    		
    		Map<String,Object> params = new HashMap<String,Object>();
    		params.put("page", pageNumber);
    		params.put("size", 10);
    		params.put("uPhone", uPhone);
    		if(!StringUtil.isNullOrEmpty(statusObj)){
    			params.put("status", StringEscapeUtils.escapeSql(statusObj));
    		}
    		if(!StringUtil.isNullOrEmpty(name)){
    			params.put("name", name);
    		}
    		
        	Page<Project> recordList = Project.dao.findRecordByAdminAndCondition(params);
            
        	setAttr("recordList", recordList);
            setAttr("uPhone", uPhone);
            setAttr("status", statusObj);
            setAttr("name", name);
            
            renderFreeMarker("index.ftl");
		} catch (Exception e) {
			e.printStackTrace();
			forwardAction("/main");
		}
    }
    
    /**
     * to初审页面
     * by HECJ
     */
    public void toPassapply(){
    	try {
			
    		Long project_id = getParaToLong(0);
    		log.info("project_id{}:"+project_id);
    		Project project = Project.dao.findById(project_id);

    		//附件加入静态域名
    		if(project.getStr("afile")!=""){
    			project.set("afile", Constant.STATIC_URL+project.getStr("afile"));
    		}

    		setAttr("project", project);
    		User user = User.dao.findById(project.getLong("user_id"));
    		setAttr("proUser", user);
    		
    		render("/page/project/passapply.ftl");
    		
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			redirect("/project");
		}
    }

    /**
     * 初审是否通过
     * up HECJ
     */
    @Before(POST.class)
    public void passapply() {
        final Integer id = getParaToInt(0);
        String audit_type = getPara("audit_type");
        final String message = getPara("message");
        log.info("project_id{},status{}"+id+","+audit_type);
        try {
        	
        	 final PUser puser = UserUtil.getU(getSession());
        	 String username = puser.getStr("username");
             log.info("项目初审审核处理人username："+username);
        	 final Project project = Project.dao.findById(id);
        	 User user = User.dao.findById(project.getLong("user_id"));
        	 final int status = project.getInt("status");
             if ( status != 1 && status != 2 && status != 3) {
            	 log.info("该项目状态，不可提交审核，id{},status{}:"+id+""+status);
            	 renderJson(new ResultJson(-1l, "该项目状态，不可提交审核"));
            	 return;
             }
             if (audit_type.equals("pass")) {
                 //初审通过
            	 if(project.getInt("status") == 3){
            		 log.info("初审已通过，无需重复审核。product_id{}:"+id);
            	 }else{

            		 Db.tx(new IAtom() {
						@Override
						public boolean run() throws SQLException {
							project.set("status", 3).update();
							
							// 审核记录
							ProjectAuditRecord record = new ProjectAuditRecord();
							record.put("project_id", id);
							record.put("operator", puser.getLong("id"));
							record.put("status_after", 3);
							record.put("status_before", status);
							record.put("remark", "初审通过");
							record.put("message", message);
							record.put("create_at", System.currentTimeMillis());
							
							record.save();
							
							return true;
						}
					});
            		
            		try{
	            		// 模版
	     				String content = NoticeTemplate.dao.findByTempName("template_apply_project_success").getStr("content");
	     				content = content.replace("%nickname%", user.getStr("nickname"));
	     				content = content.replace("%project_name%", project.getStr("name"));
	     				// 初审项目 发送 系统消息、短信
	     				MessageService messageService = new MessageService();
	     				Map<String,String> contentParams = new HashMap<String,String>();
	     				contentParams.put("system", content);
	     				contentParams.put("phone", content);
	     				messageService.sendNoticeMessage(user.getLong("id"), "", contentParams);
            		}catch(Exception ex){
            			ex.printStackTrace();
            		}
            	 }
             } else if (audit_type.equals("nopass")) {
            	//初审未通过
             	Db.tx(new IAtom() {
					@Override
					public boolean run() throws SQLException {
						 
		            	 project.set("status", 2).update();
						
						// 审核记录
						ProjectAuditRecord record = new ProjectAuditRecord();
						record.put("project_id", id);
						record.put("operator", puser.getLong("id"));
						record.put("status_after", 2);
						record.put("status_before", status);
						record.put("remark", "初审不通过");
						record.put("message", message);
						record.put("create_at", System.currentTimeMillis());
						
						record.save();
						
						return true;
					}
				});
             	
             	try{
	             	// 模版
					String content = NoticeTemplate.dao.findByTempName("template_apply_project_fail").getStr("content");
					content = content.replace("%nickname%", user.getStr("nickname"));
	 				content = content.replace("%project_name%", project.getStr("name"));
					// 初审项目 发送 系统消息、短信
					MessageService messageService = new MessageService();
					Map<String,String> contentParams = new HashMap<String,String>();
					contentParams.put("system", content);
					contentParams.put("phone", content);
					messageService.sendNoticeMessage(user.getLong("id"), "", contentParams);
             	}catch(Exception ex){
        			ex.printStackTrace();
        		}
             }
             
             renderJson(new ResultJson(200l, "success"));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderJson(new ResultJson(-100000l, e.getMessage()));
		}
    }

    /**
     * to复审页面
     * by HECJ
     */
    public void toPassproject(){
    	try {
			
    		Long project_id = getParaToLong(0);
    		log.info("project_id{}:"+project_id);
    		Project project = Project.dao.findById(project_id);
    		setAttr("project", project);
    		User user = User.dao.findById(project.getLong("user_id"));
    		setAttr("proUser", user);
    		
    		List<ProjectCategory> categoryList = ProjectCategory.dao.findAll();
    		setAttr("categoryList", categoryList);
    		// 静态域名
    		setAttr("STATIC_URL", Constant.STATIC_URL);
    		
    		String imagesText = project.getStr("images");
    		if(!StringUtil.isNullOrEmpty(imagesText)){
    			String[] images = project.getStr("images").split(",");
        		setAttr("images", images);
    		}

    		// 省市区
    		List<Provinces> provincesList = Provinces.dao.findAll();
        	setAttr("provincesList", provincesList);
        	
        	String province = project.getStr("province");
        	if(!StringUtil.isNullOrEmpty(province)){
        		
        		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
        		Provinces provinces = Provinces.dao.findCacheByProvinceId(province);
        		if(provinces.getInt("type") == 2){
    				// 直辖市
    				List<Cities> citiesList = Cities.dao.findCitiesByProvinceId(province);
    				for(Cities cities : citiesList){
    					List<Areas> areasList = Areas.dao.findAreasByCtiy(cities.getStr("cityid"));
    					for(Areas areas : areasList){
    						Map<String,String> row = new HashMap<String,String>();
    						row.put("key", areas.getStr("areaid"));
    						row.put("value", areas.getStr("area"));
    						data.add(row);
    					}
    				}
    			}else{
    				List<Cities> citiesList = Cities.dao.findCitiesByProvinceId(province);
    				for(Cities cities : citiesList){
    					Map<String,String> row = new HashMap<String,String>();
    					row.put("key", cities.getStr("cityid"));
    					row.put("value", cities.getStr("city"));
    					data.add(row);
    				}
    			}
        		setAttr("citiesList", data);
        	}
    		
    		render("/page/project/passproject.ftl");
    		
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			redirect("/project");
		}
    }


    /**
     * 复审是否通过
     * by HECJ
     */
    public void passproject() {
    	final Long id = getParaToLong(0);
        String audit_type = getPara("audit_type");
        final String message = getPara("message");
        log.info("project_id{},status{}"+id+","+audit_type);
        try {
        	
        	 final PUser puser = UserUtil.getU(getSession());
        	 String username = puser.getStr("username");
             log.info("项目复审审核处理人username："+username);
        	 final Project project = Project.dao.findById(id);
        	 User user = User.dao.findById(project.getLong("user_id"));
        	 final int status = project.getInt("status");
             if ( status != 4 && status != 5 && status != 6) {
            	 log.info("该项目状态，不可提交审核，id{},status{}:"+id+""+status);
            	 renderJson(new ResultJson(-1l, "该项目状态，不可提交审核"));
            	 return;
             }
             if (audit_type.equals("pass")) {
                 //复审通过
            	 if(project.getInt("status") == 6){
            		 log.info("初审已通过，无需重复审核。product_id{}:"+id);
            	 }else{

            		 Db.tx(new IAtom() {
						@Override
						public boolean run() throws SQLException {
							project.set("status", 6).set("starttime",System.currentTimeMillis()).update();
							
							// 审核记录
							ProjectAuditRecord record = new ProjectAuditRecord();
							record.put("project_id", id);
							record.put("operator", puser.getLong("id"));
							record.put("status_after", 6);
							record.put("status_before", status);
							record.put("remark", "复审通过");
							record.put("message", message);
							record.put("create_at", System.currentTimeMillis());
							
							record.save();
							
							return true;
						}
					});
            		 
            		try{
     	             	// 模版
     					String content = NoticeTemplate.dao.findByTempName("template_recheck_apply_project_success").getStr("content");
     					content = content.replace("%nickname%", user.getStr("nickname"));
     	 				content = content.replace("%project_name%", project.getStr("name"));
     					// 复审项目 发送 系统消息、短信
     					MessageService messageService = new MessageService();
     					Map<String,String> contentParams = new HashMap<String,String>();
     					contentParams.put("system", content);
     					contentParams.put("phone", content);
     					messageService.sendNoticeMessage(user.getLong("id"), "", contentParams);
                  	}catch(Exception ex){
             			ex.printStackTrace();
             		}
            		 
            	 }
             } else if (audit_type.equals("nopass")) {
            	//复审未通过
             	Db.tx(new IAtom() {
					@Override
					public boolean run() throws SQLException {
						 
		            	 project.set("status", 5).update();
						
						// 审核记录
						ProjectAuditRecord record = new ProjectAuditRecord();
						record.put("project_id", id);
						record.put("operator", puser.getLong("id"));
						record.put("status_after", 5);
						record.put("status_before", status);
						record.put("remark", "复审不通过");
						record.put("message", message);
						record.put("create_at", System.currentTimeMillis());
						
						record.save();
						
						return true;
					}
				});
             	
             	try{
 	             	// 模版
 					String content = NoticeTemplate.dao.findByTempName("template_recheck_apply_project_fail").getStr("content");
 					content = content.replace("%nickname%", user.getStr("nickname"));
 	 				content = content.replace("%project_name%", project.getStr("name"));
 					// 复审项目 发送 系统消息、短信
 					MessageService messageService = new MessageService();
 					Map<String,String> contentParams = new HashMap<String,String>();
 					contentParams.put("system", content);
 					contentParams.put("phone", content);
 					messageService.sendNoticeMessage(user.getLong("id"), "", contentParams);
              	}catch(Exception ex){
         			ex.printStackTrace();
         		}
             	
             }
             
             renderJson(new ResultJson(200l, "success"));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderJson(new ResultJson(-100000l, e.getMessage()));
		}
    }

    /**
     * 是否通过-产品预热
     * by HECJ
     */
    public void passstatus7() {
        Integer id = getParaToInt(0);
        String status = getPara(1);
        log.info("项目产品预热：project_id{},status{}"+id+","+status);
        
        try {
        	 Project project = Project.dao.findById(id);
             if (project.getInt("status") != 6 && project.getInt("status") != 7) {
                 renderError(403);
                 return;
             }
             if (status.equals("pass")) {
                 //产品预热
             	project.set("status", 7).update();
             } else {
                 //结束预热
             	project.set("status", 6).update();
             }
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
        redirect("/project");
    }

    /**
     * 项目上线
     * by HECJ
     */
    public void projectonline() {
        Integer id = getParaToInt(0);
        PUser puser = UserUtil.getU(getSession());
        
        log.info("项目上线project_id{},"+id);
        String username = puser.getStr("username");
        log.info("项目上线处理人username："+username);
        try {
        	Project project = Project.dao.findById(id);
            if (project.getInt("status") != 6 && project.getInt("status") != 7) {
                renderError(403);
                return;
            }
            Integer days = project.getInt("days");
            log.info("众筹天数days："+days);
            Long startTime = System.currentTimeMillis();
            log.info("众筹开始时间："+startTime);
            Long expirestime = startTime + days*24*60*60*1000l;
            log.info("众筹结束时间："+expirestime);
            project.set("status", 8).set("starttime", startTime).set("expirestime", expirestime).update();
            setAttr("msg", "项目" + id + "开始众筹");
            log.info("项目已上线project_id ："+id);
		} catch (Exception e) {
			e.printStackTrace();
		}
        redirect("/project");
    }

    /**
     * 项目结束
     */
    public void projectshutdown() {
        Integer id = getParaToInt(0);
        Project project = new Project().findById(id);
        PUser puser = UserUtil.getU(getSession());
        log.info("项目结束project_id{},"+id);
        String username = puser.getStr("username");
        log.info("项目结束处理人username："+username);
        if (project.getInt("status") != 8) {
            renderError(403);
            return;
        }
        BigDecimal fundgoal = project.getBigDecimal("fundgoal");
        BigDecimal fundnow = project.getBigDecimal("fundnow");
        int result = fundnow.compareTo(fundgoal);

        if (result < 0) {
            new Project().findById(id)
                    .set("status", 9).update();
            setAttr("msg", "项目" + id + "众筹结束,9");
        } else {
            new Project().findById(id).set("status", 10).update();
            setAttr("msg", "项目" + id + "众筹结束,10");
        }
        redirect("/project");
    }

    /**
     * 预览
     */
    public void preview() {
        //产品详情
        Long pid = getParaToLong(0);
        setAttr("pid", pid);

        Project project = Project.dao.findById(pid);
        setAttr("p", project);
        if (null == project) {
            renderError(404);
            return;
        }

        //关注数
        Long count = Subscribe.dao.getSubCountByPid(pid);
        if (null != count) {
            setAttr("subscribeNum", count.getLong("num"));
        } else {
            setAttr("subscribeNum", 0);
        }

        //SNS
        String uri = AppConfig.getInstance().get("fronturi");
        setAttr("snsUrl", uri + "/project/detail/" + pid);
        setAttr("snsPic", uri + project.getStr("pic0"));
        setAttr("snsTitle", project.getStr("name"));
        setAttr("snsDesc", project.getStr("desc"));

        //众酬阶梯价格
        List<Product> wayList = Product.dao.findByProjectId(pid);
        setAttr("wayList", wayList);

        //项目进展(公告历史)

        List<ProjectProgress> notice_historyList = ProjectProgress.dao.findByProjectId(pid);
        setAttr("notice_historyList", notice_historyList);


        //支持者名单
        /*
        List<Record> supports = Support.dao.getSupportListByPid(pid);
        setAttr("supports", supports);

        setAttr("fundpcount", supports.size());
        */

        //产品评论
//        List<Comment> comments = Comment.dao.findComment(pid);
//        setAttr("comments", comments);


        renderFreeMarker("preview.ftl");
    }
    
    public void downloadFile(){
    	
    	System.out.println("=======下载文件=======");
    	renderNull();
    }
    
    /**
     * 回报设置
     * by HECJ
     */
    public void productSet(){
    	
    	Long project_id = -1l;
    	try {
    		project_id = getParaToLong(0);
    		//项目
    		Project project = Project.dao.findById(project_id);
    		setAttr("project", project);
    		//发布人
    		User proUser = User.dao.findById(project.getLong("user_id"));
    		setAttr("proUser", proUser);
    		//回报列表
    		List<Product> productList = Product.dao.findByProjectId(project_id);
    		setAttr("productList", productList);
    		
		} catch (Exception e) {
			log.error("project_id{}:"+project_id);
			e.printStackTrace();
		}
    	
    	render("/page/project/product_set.ftl");
    }
    
    /**
     * 添加回报
     * AJAX
     * by HECJ
     */
    @Before(POST.class)
    public void addProduct(){
    	
    	try {
    		
    		Long project_id = getParaToLong(0);
    		Product product = getModel(Product.class, "product");
    		
    		if(product.getBigDecimal("fund").compareTo(new BigDecimal("0.01"))<0){
    			renderJson(new ResultJson(-1l, "回报金额超限"));
    			return;
    		}
    		
    		// 校验金额已满
    		Project project = Project.dao.findById(project_id);
    		BigDecimal fundgoal = project.getBigDecimal("fundgoal");
    		
    		//回报列表
    		List<Product> productList = Product.dao.findByProjectId(project_id);
    		// 已设置总金额
    		BigDecimal totalAmount = new BigDecimal("0");
    		for(Product p: productList){
    			BigDecimal tempSum = p.getBigDecimal("fund").multiply(new BigDecimal(p.getInt("totalnum")));
    			totalAmount = totalAmount.add(tempSum);
    		}
    		log.info("已设置回报筹款金额totalAmount{}"+totalAmount);
    		
    		// 本次设置筹款金额
    		/*BigDecimal currAmount = product.getBigDecimal("fund").multiply(new BigDecimal(product.getInt("totalnum")));
    		log.info("本次设置筹款金额currAmount{}："+currAmount);
    		if(totalAmount.add(currAmount).compareTo(fundgoal) > 0){
    			log.info("筹款金额超限fundgoal{},totalAmount{},currAmount{}："+fundgoal+","+totalAmount+","+currAmount);
    			renderJson(new ResultJson(-1l, "筹款金额超限"));
    			return ;
    		}*/
    		
    		product.put("project_id", project_id);
    		product.put("existnum", 0);
    		product.put("is_delete", 0);
    		product.put("create_at", System.currentTimeMillis());
    		
    		//将临时目录图片cp到pro目录
    		String upload_file_temps_dir = PropKit.use("file.properties").get("upload_file_temps_dir");
    		String upload_images_dir = PropKit.use("file.properties").get("upload_images_dir");
    		String static_upload_images_url = PropKit.use("file.properties").get("static_upload_images_url");
    		
    		File tempFile = new File(upload_file_temps_dir+"/"+product.getStr("picture"));
    		File proFile = new File(upload_images_dir+"/"+product.getStr("picture"));
    		FileUtil.copyFile(tempFile, proFile);
    		tempFile.delete();
    		
    		log.info(tempFile+" cp ->" + proFile);
    		
    		product.set("picture", static_upload_images_url + "/" + product.getStr("picture"));
    		product.save();
    		log.info("添加回报成功project_id{}："+project_id);
    		renderJson(new ResultJson(200l, "success"));
    		return;
    	} catch (Exception e) {
			log.error("添加回报失败："+e.getMessage());
			e.printStackTrace();
			renderJson(new ResultJson(-100000l, "添加回报失败"));
		}
    }
    
    /**
     * 删除回报submit
     * by HECJ
     */
    public void delProduct(){
    	
    	Long project_id = -1l;
		Long product_id = getParaToLong(1);
    	try {
    		project_id = getParaToLong(0);
    		Product model = Product.dao.findById(product_id);
    		if(model != null){
    			model.set("is_delete", 1);
    			model.update();
    			log.info("删除回报成功product_id{}:"+product_id);
    		}
		} catch (Exception e) {
			
			e.printStackTrace();
			forwardAction("/project");
		}
    	forwardAction("/project/productSet/"+project_id);
    }
    
    /**
     * 编辑回报go
     * by HECJ
     */
    public void goEditProduct(){
		
    	try {
    		Long product_id = getParaToLong(0);
    		Product model = Product.dao.findById(product_id);
    		
    		setAttr("product", model);
    		//加入静态域名
    		setAttr("picture_url", Constant.STATIC_URL+model.getStr("picture"));
    		
    		render("/page/project/edit_product.ftl");
		} catch (Exception e) {
			e.printStackTrace();
			forwardAction("/project");
		}
    }
    
    /**
     * 修改回报sub
     * AJAX
     * by HECJ
     */
    @Before(POST.class)
    public void editProductSub(){
    	
    	try {
    		
    		Long product_id = getParaToLong(0);
    		// 老数据
    		Product oldProduct = Product.dao.findById(product_id);
    		Long project_id = oldProduct.getLong("project_id");
    		
    		// 新数据
    		Product newProduct = getModel(Product.class, "product");
    		
    		if(newProduct.getBigDecimal("fund").compareTo(new BigDecimal("0.01"))<0){
    			renderJson(new ResultJson(-1l, "回报金额超限"));
    			return;
    		}
    		
    		// 赋值
    		oldProduct.set("type", newProduct.get("type"));
    		oldProduct.set("fund", newProduct.get("fund"));
    		oldProduct.set("desc", newProduct.get("desc"));
    		oldProduct.set("postage", newProduct.get("postage"));
    		oldProduct.set("send_days", newProduct.get("send_days"));
    		oldProduct.set("totalnum", newProduct.get("totalnum"));
    		oldProduct.set("is_invoice", newProduct.get("is_invoice"));
    		oldProduct.set("remark", newProduct.get("remark"));
    		oldProduct.set("update_at", System.currentTimeMillis());
    		
    		String static_upload_images_url = PropKit.use("file.properties").get("static_upload_images_url");
    		String picture = newProduct.get("picture");
    		// 判断是新上传还是修改
    		if(picture.contains(static_upload_images_url)){
    			oldProduct.set("picture", picture);
    		} else{
    			//将临时目录图片cp到pro目录
        		String upload_file_temps_dir = PropKit.use("file.properties").get("upload_file_temps_dir");
        		String upload_images_dir = PropKit.use("file.properties").get("upload_images_dir");
        		
        		File tempFile = new File(upload_file_temps_dir+"/"+picture);
        		File proFile = new File(upload_images_dir+"/"+picture);
        		FileUtil.copyFile(tempFile, proFile);
        		tempFile.delete();
        		
        		log.info(tempFile+" cp ->" + proFile);
        		oldProduct.set("picture", static_upload_images_url +"/"+ picture);
    		}
    		
    		// 校验金额已满
    		Project project = Project.dao.findById(project_id);
    		BigDecimal fundgoal = project.getBigDecimal("fundgoal");
    		
    		//回报列表
    		List<Product> productList = Product.dao.findByProjectId(project_id);
    		// 已设置总金额
    		BigDecimal totalAmount = new BigDecimal("0");
    		for(Product p: productList){
    			if(p.getLong("id").compareTo(product_id) == 0){
    				continue;
    			}
    			BigDecimal tempSum = p.getBigDecimal("fund").multiply(new BigDecimal(p.getInt("totalnum")));
    			totalAmount = totalAmount.add(tempSum);
    		}
    		log.info("已设置回报筹款金额totalAmount{}"+totalAmount);
    		
    		// 本次设置筹款金额
    		/*BigDecimal currAmount = newProduct.getBigDecimal("fund").multiply(new BigDecimal(newProduct.getInt("totalnum")));
    		log.info("本次设置筹款金额currAmount{}："+currAmount);
    		if(totalAmount.add(currAmount).compareTo(fundgoal) > 0){
    			log.info("筹款金额超限fundgoal{},totalAmount{},currAmount{}："+fundgoal+","+totalAmount+","+currAmount);
    			renderJson(new ResultJson(-1l, "筹款金额超限"));
    			return ;
    		}*/
    		
    		oldProduct.update();
    		log.info("修改回报成功project_id{}："+project_id);
    		renderJson(new ResultJson(200l, "success"));
    		return;
    	} catch (Exception e) {
			log.error("修改回报失败："+e.getMessage());
			e.printStackTrace();
			renderJson(new ResultJson(-100000l, "修改回报失败"));
		}
    }
    
    /**
     * 上传回报图片
     * by HECJ
     */
    @Before(POST.class)
    public void uploadProductPicture() throws IOException{
    	
    	File file = null;
    	BufferedInputStream bis = null;
    	BufferedOutputStream out = null;
    	try {
    		
    		// 静态文件临时目录
    		String upload_file_temps_dir = PropKit.use("file.properties").get("upload_file_temps_dir");
    		UploadFile uploadFile = getFile("product_images", upload_file_temps_dir , 10000*1024);
    		file = uploadFile.getFile();
    		
    		String file_name = file.getName();
    		String ext = FileUtil.getExt(file.getName());
    		if(ext==null || "".equals(ext)){
    			log.info("上传文件类型不正确{}:"+file_name);
    			renderJson(new ResultJson(-1L,"上传文件类型不正确"));
    			return;
    		}
    		//验证上传文件类型
    		String upload_images_file_types = PropKit.use("file.properties").get("upload_images_file_types");
    		if(!upload_images_file_types.contains(ext.toLowerCase())){
    			log.info("上传文件类型不正确{}:"+file_name);
    			renderJson(new ResultJson(-1L,"上传文件类型不正确"));
    			boolean b = file.delete();
    			log.info("删除文件："+b);
    			return;
    		}
    		
    		String new_file_name = OrdersUtils.getRandomPictureName()+"."+ext;
    		
    		FileInputStream fis = new FileInputStream(file);
    		bis = new BufferedInputStream(fis);
    		byte[] bs =new byte[bis.available()];
    		bis.read(bs);
    		
    		//写入文件
    		OutputStream fos = new FileOutputStream(new File(upload_file_temps_dir+"/"+new_file_name));
    		out = new BufferedOutputStream(fos);
    		out.write(bs);
    		
    		ResultJson result = new ResultJson();
        	result.setCode(200l);
        	Map<String,Object> data = new HashMap<String,Object>();
        	// 静态域名
        	String static_url = Constant.STATIC_URL;
        	// 相对静态域名路径
        	String static_upload_file_temp_url = PropKit.use("file.properties").get("static_upload_file_temp_url");
        	if(static_upload_file_temp_url != null && !static_upload_file_temp_url.equals("")){
        		static_url += static_upload_file_temp_url+"/";
        	}
        	static_url += new_file_name;
        	log.info("图片地址{}:"+static_url);
        	data.put("image_url", static_url);
        	data.put("image_name", new_file_name);
        	result.setData(data);
        	result.setMessage("success");
        	
        	renderJson(result);
    	} catch (Exception e) {
			log.error("上传文件失败");
			e.printStackTrace();
			renderJson(new ResultJson(-100000L,"上传图片失败"));
		} finally{
			
			if(file != null && file.exists()){
				file.delete();
			}
			
			if(bis != null){
				bis.close();
			}
			
			if(out != null){
				out.flush();
	    		out.close();
			}
		}
    }
    
    /**
     * 项目进展页面
     * by HECJ
     */
    public void progress(){
    	
    	Long project_id = -1l;
    	try {
			
    		project_id = getParaToLong(0);
    		log.info("项目进展project_id{}:"+project_id);
    		Project project = Project.dao.findById(project_id);
    		setAttr("project", project);
    		List<ProjectProgress> progressList = ProjectProgress.dao.findByProjectId(project_id);
    		setAttr("progressList", progressList);
    		User proUser = User.dao.findById(project.getLong("user_id"));
    		setAttr("proUser", proUser);
    		render("/page/project/progress_set.ftl");
		} catch (Exception e) {
			log.error("项目进展project_id{}:"+project_id);
			log.error(e.getMessage());
			e.printStackTrace();
			forwardAction("/project");
		}
    }
    
    /**
     * 添加项目进展
     * by HECJ
     */
    @Before(POST.class)
    public void addProgress(){
    	
    	try {
    		
    		Long project_id = getParaToLong(0);
    		Project project = Project.dao.findById(project_id);
    		if(project == null){
    			log.info("项目不存在project_id{}:"+project_id);
    			ResultJson json = new ResultJson(-1l, "项目不存在");
    			renderJson(json);
    			return ;
    		}
    		ProjectProgress progress = getModel(ProjectProgress.class, "progress");
    		progress.put("project_id", project_id);
    		progress.put("is_delete", 0);
    		progress.put("create_at", System.currentTimeMillis());
    		progress.put("update_at", System.currentTimeMillis());
    		progress.save();
    		log.info("添加进展成功project_id{}:"+project_id);
    		ResultJson json = new ResultJson(200l, "success");
    		renderJson(json);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			ResultJson json = new ResultJson(-100000l, "添加项目进展失败");
    		renderJson(json);
		}
    	
    }
    
    /**
     * 删除进度
     * by HECJ
     */
    public void delProgress(){
    	
    	try {
			
    		Long project_id  = getParaToLong(0);
    		Long progress_id  = getParaToLong(1);
    		ProjectProgress progress = ProjectProgress.dao.findById(progress_id);
    		progress.set("is_delete", 1);
    		progress.set("update_at", System.currentTimeMillis());
    		progress.update();
    		log.info("删除进展成功id："+progress_id);
    		forwardAction("/project/progress/"+project_id);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			forwardAction("/project");
		}
    }
    
    /**
     * go编辑进度
     * by HECJ
     */
    public void goEditProgress(){
    	
    	try {
    		
    		Long progress_id  = getParaToLong(0);
    		ProjectProgress progress = ProjectProgress.dao.findById(progress_id);
    		setAttr("progress", progress);
    		Project project = Project.dao.findById(progress.getLong("project_id"));
    		setAttr("project", project);
    		User proUser = User.dao.findById(project.getLong("user_id"));
    		setAttr("proUser", proUser);
    		render("/page/project/progress_edit.ftl");
    	} catch (Exception e) {
    		log.error(e.getMessage());
    		e.printStackTrace();
    		forwardAction("/project");
    	}
    }
    
    /**
     * 编辑进度
     * by HECJ
     */
    @Before(POST.class)
    public void editProgress(){
    	
    	try {
    		
    		Long progress_id = getParaToLong(0);
    		ProjectProgress oldProgress = ProjectProgress.dao.findById(progress_id);
    		
    		ProjectProgress newProgress = getModel(ProjectProgress.class, "progress");
    		oldProgress.set("info", newProgress.get("info"));
    		oldProgress.set("progress_at", newProgress.get("progress_at"));
    		oldProgress.set("update_at", System.currentTimeMillis());
    		oldProgress.update();
    		log.info("编辑进展成功progress_id{}:"+progress_id);
    		ResultJson json = new ResultJson(200l, "success");
    		renderJson(json);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			ResultJson json = new ResultJson(-100000l, "编辑进度失败");
    		renderJson(json);
		}
    	
    }
    
    
    /**
     * 编辑项目
     * by HECJ
     */
    @Before(POST.class)
    public void editProject(){
    	
    	try {
    		Long project_id = getParaToLong(0);
    		
    		Project newProject = getModel(Project.class, "project");
    		
    		Project oldProject = Project.dao.findById(project_id);
    		oldProject.set("name", newProject.get("name"));
    		oldProject.set("desc", newProject.get("desc"));
    		oldProject.set("fundgoal", newProject.get("fundgoal"));
    		oldProject.set("days", newProject.get("days"));
    		oldProject.set("category_id", newProject.get("category_id"));
    		oldProject.set("video", newProject.get("video"));
    		oldProject.set("context", newProject.get("context"));
    		oldProject.set("province", newProject.get("province"));
    		oldProject.set("city", newProject.get("city"));
    		
    		String cover_image = newProject.get("cover_image");
    		String static_upload_images_url = PropKit.use("file.properties").get("static_upload_images_url");
    		// 新上传封面
    		if(!cover_image.contains(static_upload_images_url)){
    			//将临时目录图片cp到pro目录
        		String upload_file_temps_dir = PropKit.use("file.properties").get("upload_file_temps_dir");
        		String upload_images_dir = PropKit.use("file.properties").get("upload_images_dir");
        		
        		File tempFile = new File(upload_file_temps_dir+"/"+cover_image);
        		File proFile = new File(upload_images_dir+"/"+cover_image);
        		FileUtil.copyFile(tempFile, proFile);
        		tempFile.delete();
        		
        		log.info(tempFile+" cp ->" + proFile);
        		oldProject.set("cover_image", static_upload_images_url +"/"+ cover_image);
    		}else{
    			oldProject.set("cover_image", cover_image);
    		}
    		
    		// 说明图片集合
    		String[] images = getParaValues("images");
    		// 拼接图片集合
    		String images_list = "";
    		for(String image : images){
    			
    			// 新上传图片
        		if(!image.contains(static_upload_images_url)){
        			//将临时目录图片cp到pro目录
            		String upload_file_temps_dir = PropKit.use("file.properties").get("upload_file_temps_dir");
            		String upload_images_dir = PropKit.use("file.properties").get("upload_images_dir");
            		
            		File tempFile = new File(upload_file_temps_dir+"/"+image);
            		File proFile = new File(upload_images_dir+"/"+image);
            		FileUtil.copyFile(tempFile, proFile);
            		tempFile.delete();
            		
            		log.info(tempFile+" cp ->" + proFile);
            		images_list += "," + (static_upload_images_url +"/"+ image);
        		}else{
        			images_list += "," + image;
        		}
    		}
    		images_list = images_list.replaceFirst(",", "");
    		oldProject.set("images", images_list);
    		int days = oldProject.getInt("days");
    		log.info("days:"+days);
    		if(oldProject.getInt("status") == 8){
    			long starttime = oldProject.getLong("starttime");
    			log.info("众筹开始时间starttime："+starttime);
    			long expirestime = starttime + days*24*60*60*1000l;
    			log.info("项目截至时间："+expirestime);
    			oldProject.set("expirestime", expirestime);
    		}
    		
    		oldProject.update();
    		log.info("项目更新成功project_id{}:"+project_id);
    		ResultJson json = new ResultJson(200l, "success");
    		renderJson(json);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			ResultJson json = new ResultJson(-100000l, "编辑项目失败");
    		renderJson(json);
		}
    	
    }
    
    /**
     * to项目推荐列表
     * by HECJ
     */
    public void toProjectRecommList(){
    	try {
    		
    		String recommQuery = getPara("recommQuery");
    		log.info("recommQuery{}:"+recommQuery);
    		if("recommQuery".equals(recommQuery)){
    			//推荐查询
    			List<Long> projectIds = ProjectRecomm.dao.getProjectIds();
    			if(projectIds == null || projectIds.size() == 0){
    				log.info("暂无推荐的项目，请设置后查询。");
    			}else{
    				String ids = SqlUtil.withSplit(projectIds, ",");
    				Map<String,Object> params = new HashMap<String,Object>();
    				params.put("status", " 7,8 " );
    				params.put("ids", ids);
    				int page = getParaToInt(0,1);
    				params.put("page", page);
            		params.put("size", 10);
    				Page<Project> recordList = Project.dao.findRecordByRecommAndCondition(params);
            		setAttr("recordList", recordList);
            		setAttr("recommQuery", recommQuery);
    			}
    			
    		}else{
    			//普通查询
    			String aphone = getPara("aphone");
    			log.info("aphone{}:"+aphone);
        		int page = getParaToInt(0,1);
        		String name = getPara("name");
        		String status = getPara("status");
        		log.info("name{}:"+name);
        		Map<String,Object> params = new HashMap<String,Object>();
        		params.put("aphone", aphone);
        		params.put("name", name);
        		if(StringUtil.isNullOrEmpty(status)){
        			params.put("status", " 7,8,10 " );
        		}else{
        			params.put("status", Integer.parseInt(status)+"");
        		}
        		params.put("page", page);
        		params.put("size", 10);
        		
        		Page<Project> recordList = Project.dao.findRecordByRecommAndCondition(params);
        		setAttr("recordList", recordList);
        		if(aphone !=null && !aphone.equals("")){
        			setAttr("aphone", aphone);
        		}
        		if(name !=null && !name.equals("")){
        			setAttr("name", name);
        		}
        		setAttr("status", status);
    		}
    		
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
    	render("/page/project/project_recomm_list.ftl");
    }
    
    /**
     * 项目推荐设置
     * @author by HECJ
     * @param position
     * -1取消推荐
     * 0 推荐至项目列表
     * 1 推荐至首页
     */
    public void projectRecommSub(){
    	
    	String logs = "";
    	try {
			final Long project_id = getParaToLong(0);
			int position = getParaToInt(1);
			
			logs = "project_id{},position{}"+project_id+","+position;
			log.info(logs);
			
			if(position == -1){
				// 取消推荐
				ProjectRecomm recomm = ProjectRecomm.dao.findByProjectId(project_id);
				if(recomm != null){
					recomm.delete();
					log.info("项目取消推荐成功");
				}
			}else if(position == 0){
				// 推荐
				ProjectRecomm recomm = ProjectRecomm.dao.findByProjectId(project_id);
				if(recomm != null){
					log.info("项目已推荐，无需重复推荐");
				}else{
					Project project = Project.dao.findById(project_id);
					ProjectRecomm re = new ProjectRecomm();
					re.set("project_id", project_id);
					re.set("position", 0);
					re.set("category_id", project.getLong("category_id"));
					re.set("create_at", System.currentTimeMillis());
					re.set("update_at", System.currentTimeMillis());
					re.save();
					log.info("项目推荐成功");
				}
			}else if(position == 1){
				
				Db.tx(new IAtom() {
					@Override
					public boolean run() throws SQLException {
						// 推荐至首页
						Db.update("delete from project_recomm where position = 1");
						ProjectRecomm recomm = ProjectRecomm.dao.findByProjectId(project_id);
						if(recomm != null){
							recomm.set("position", 1);
							recomm.set("update_at", System.currentTimeMillis());
							recomm.update();
							log.info("项目推荐至首页成功");
						}else{
							log.info("项目未推荐，不可直接推荐至首页");
						}
						
						return true;
					}
				});
				
			}
    		
		} catch (Exception e) {
			log.error(e.getMessage()+logs);
			e.printStackTrace();
		}
    	redirect("/project/toProjectRecommList");
    }
    
    /**
     * 结算
     */
    public void doBalance(){
    	
    	long project_id = getParaToLong(0);
    	log.info(" 正在处理结算 project_id :" +project_id);
    	try {
    		
    		Project project = Project.dao.findById(project_id);
    		if(project.getIsBalance()){
    			project.set("settlement", 1);
    			boolean exeBlo = project.update();
    			log.info("结算处理结果 exeBlo ："+exeBlo);
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	redirect("/project");
    
    }
    
    
}
