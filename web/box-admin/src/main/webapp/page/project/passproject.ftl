<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
    <#include "/page/common/base/include.ftl">
<script type="text/javascript">
	
	jQuery(function(){
		app.project_id = ${project.id!};
	});
	
</script>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/project">项目管理</a></li>
        <li><a href="/project/toPassproject/${project.id!}">复审|查看</a></li>
    </ul>
</div>
    
<div class="formbody">
    
    <div id="usual1" class="usual">
		<div>    	
    		发布人：<label>${proUser.nickname!}</label>&nbsp;&nbsp;&nbsp;
    		手机号：<label>${proUser.phone!}</label>
    	</div>
    	<br>
  	<div>
  	<div class="xline"></div>
    <br>
    <ul class="forminfo">
    	<li>
    		<label>项目名称：</label>
    		<cite>
    			<input id="a" type="text" class="dfinput" name="project.name" value="${project.name!}"></input>
   	    	</cite>
   	    </li>
    	<li>
    		<label>项目描述：</label>
    		<cite>
				<textarea cols="" rows="" class="textinput" name="project.desc">${project.desc!}</textarea>
			</cite>
   	    </li>
   	    <li>
    		<label>众筹金额：</label>
    		<cite>
    			<input type="text" class="dfinput" name="project.fundgoal" value="${project.fundgoal!}"></input>
   	    	</cite>
   	    </li>
   	    <li>
    		<label>众筹天数：</label>
    		<cite>
    			<input type="text" class="dfinput" name="project.days" value="${project.days!}"></input>
   	    	</cite>
   	    </li>
   	    <li>
    		<label>类别：</label>
    		<cite>
				<#list categoryList as c>
					<#if c.id == project.category_id>
						<input type="radio" name="project.category_id" value="${c.id!}" checked>${c.name!}&nbsp;&nbsp;
					<#else>
						<input type="radio" name="project.category_id" value="${c.id!}">${c.name!}&nbsp;&nbsp;
					</#if>
				</#list>

   	    	</cite>
   	    </li>
   	    <li>
    		<label>项目地点：</label>
    		<cite>
    			<select name="project.province" id="" class="dfinput" style="width:170px">
					<option value="-1">请选择省份</option>
					<#if provincesList??>								
					<#list provincesList as pv>
						<#if project.province ?? && pv.provinceid = project.province>
							<option value="${pv.provinceid!}" selected = "selected">${pv.province!}</option>
						<#else>
							<option value="${pv.provinceid!}">${pv.province!}</option>
						</#if>
					</#list>
					</#if>
				</select>
				<select name="project.city" id="" class="dfinput" style="width:170px">
					<option value="-1">请选择</option>
					<#if citiesList??>	
					<#list citiesList as ct>
						<#if project.city ?? && ct.key = project.city>
							<option value="${ct.key!}" selected = "selected">${ct.value!}</option>
						<#else>
							<option value="${ct.key!}">${ct.value!}</option>
						</#if>
					</#list>
					</#if>
				</select>
   	    	</cite>
   	    </li>
   	    <li>
    		<form id="uploadFileForm" action="/project/uploadProductPicture" method="post" enctype="multipart/form-data">
		    	<label>封面图片：<b></b></label>
		    	<cite>
		    		<input type="button" class="dfinput" style="width:100px" onclick="uploadPictureFun(this)" value="上传图片"/>
		    		<input type="file" name="product_images" style="display:none" value="${project.cover_image!}" onchange="uploadFileFun()"/>&nbsp;&nbsp;&nbsp;支持jpg,jpeg,png,gif(大小不超过500k,600*600)
		    	</cite>
	    	</form>
   	    </li>
   	    <li>
	    	<label>&nbsp;</label>
	    	<cite>
	    		<input type="hidden" name="project.cover_image" value="${project.cover_image!}"/>
	    		<div style="width:151px;height:151px;border:1px solid;margin-left:85px"><img src="${STATIC_URL!}${project.cover_image!}" width="150" height="150" id="cover_image"></div>
	     	</cite>
	     </li>
	     <li>
		    <label>说明图片：<b></b></label>
		    <cite>
		    	<input type="button" class="dfinput" style="width:100px" onclick="app.addPicture()" value="添加图片"/>
		    	&nbsp;&nbsp; 支持jpg,jpeg,png,gif(大小不超过500k,600*600)
		    </cite>
   	    </li>
   	    <li>
    		<label>&nbsp;</label>
    		<cite>
    			<div>
	    			<table id="picture_table">
	    				<tr>
	    				    <#if images ??>
	    					<#list images as image>
	    					<td width="170px">
		    					<form action="/project/uploadProductPicture" method="post" enctype="multipart/form-data">
							    	<input type="hidden" name="images" value="${image!}"/>
							    	<input type="file" name="product_images" style="display:none" onchange="app.upload_change(this)"/>
								    <div style="width:151px;height:151px;border:1px solid;margin-left:0px">
								   		<img src="${STATIC_URL}${image!}" width="150" height="150">
								   	</div>
							    </form>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							    <a onclick="app.upload_picture(this)">上传</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="app.del_picture(this)">删除</a>
	    					</td>
	    					</#list>
	    					</#if>
	    				</tr>
	    			</table>
    			</div>
   	    	</cite>
   	    </li>
   	    <li>
    		<label>项目介绍视频：</label>
    		<cite>
    			<input type="text" class="dfinput" name="project.video" value="${project.video!}"></input>
   	    	</cite>
   	    </li>
   	    
   	    <li>
    		<label>项目详情：</label>
    		<cite>
    			<#--<textarea cols="" rows="" class="textinput" name="project.context">${project.context!}</textarea>-->
   	    		<div id="context" class="context" style="height:400px;">
					${project.context!}
				</div>
   	    	</cite>
   	    </li>
   	    
   	    <li>
   		 	<label>&nbsp;</label>
   		 	<input name="" id="update_submit" onclick="app.updateSumit()" type="button" class="btn" value="修改信息"/>
   		 	&nbsp;&nbsp;<input name="" type="button" class="btn" value="重置信息" onclick="app.resetInfo()"/>
   		</li>
   	 <ul>
   	 <#if (project.status == 4 || project.status == 5 || project.status == 6)>
   	 <br>
   	 <div class="xline"></div>
   	 <br>
   	 <ul class="forminfo">
   	    <li>
    		<label>&nbsp;</label>
    		<cite>
    			<input type="radio" name="audit_type" value="pass">审核通过&nbsp;&nbsp;
    			<input type="radio" name="audit_type" value="nopass">审核不通过
   	    	</cite>
   	    </li>
   	    
   	    <li>
    		<label>审核意见: </label>
    		<cite>
				<textarea cols="" rows="" class="textinput" name="message"></textarea>
   	    	</cite>
   	    </li>
   	    
  		<li>
   		 	<label>&nbsp;</label>
   		 	<input name="" type="button" class="btn" id="submit_btn" value="提交审核" onclick="app.auditSumit()"/>
   		 </li>
    </ul>
    </#if>
    </div>
    
	</div> 
 <script type="text/javascript" src="/static/js/common/jquery/jquery.form.js"></script>
 <script type="text/javascript" src="/static/js/page/project/passproject.js?v=20151129"></script>
 
 <link rel="stylesheet" href="/static/js/common/kindeditor-4.1.10/themes/default/default.css" />
 <script type="text/javascript" src="/static/js/common/kindeditor-4.1.10/kindeditor-all-min-plus.js"></script>
  <script charset="utf-8" src="/static/js/common/kindeditor-4.1.10/lang/zh_CN.js"></script>
</body>
</html>
