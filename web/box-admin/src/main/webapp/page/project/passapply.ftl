<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
  	<#include "/page/common/base/include.ftl">
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/project">项目管理</a></li>
        <li><a href="/project/toPassapply/${project.id!}">初审|查看</a></li>
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
	<div class="xline"/>
	<br>
    <ul class="forminfo">
    	<li>
    		<label>项目名称：</label>
    		<cite>
    			${project.aname!}
   	    	</cite>
   	    </li>
    	<li>
    		<label>项目描述：</label>
    		<cite>
				<textarea style="width:900px;height:80px;resize:none;font-style: normal;" readonly >${project.acontext!}</textarea>
			</cite>
   	    </li>
   	    
   	    <li>
    		<label>联系电话：</label>
    		<cite>
    			${project.aphone!}
   	    	</cite>
   	    </li>
   	    <li>
    		<label>附件：</label>
    		
    		<#if project.afile!=""> 
	    		<cite>
	    			<a href="${project.afile!}" target="_blank" class="tablelink">下载</a>
	   	    	</cite>
	   	    <#else>
	   	    	<cite>
	    			无
	   	    	</cite>
   	    	</#if>
   	    	
   	    </li>
   	    </ul>
   	    <br>
   	    <#if project.status == 1>
   	    <div class="xline"/>
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
   		 	<input name="" type="button" class="btn" id="submit_btn" value="提交" onclick="auditSumit(${project.id!})"/>
   		 </li>
    	</ul>
    	</#if>
    </div>
    
	</div> 
 <script type="text/javascript" src="/static/js/page/project/passapply.js"></script>
</body>

</html>
