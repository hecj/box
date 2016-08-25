<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
	<link href="/static/js/laydate/need/laydate.css" rel="stylesheet" type="text/css"/>
  	<#include "/page/common/base/include.ftl">
    <script type="text/javascript" src="/static/js/laydate/laydate.js"></script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/project">项目管理</a></li>
        <li><a href="/project/progress/${project.id!}">项目进展</a></li>
        <li><a href="/project/goEditProgress/${progress.id!}">编辑进展</a></li>
    </ul>
</div>
    
<div class="formbody">
    
    <div id="usual1" class="usual">
		<div>    	
    		项目名称：<label>${project.name!}</label>&nbsp;&nbsp;&nbsp;
    		筹款金额：<label>${project.fundgoal!}</label>&nbsp;&nbsp;&nbsp;
    		发布人：<label>${proUser.nickname!}</label>&nbsp;&nbsp;&nbsp;
    		手机号：<label>${proUser.phone!}</label>
    		
    	</div>
    	<br>
    
    
  	<div>
    <ul class="forminfo">
    	<li>
    		<label>进展信息<b>*</b></label>
    		<cite>
    			<input type="text" value="${progress.info!}" name="progress.info" class="dfinput"/>
   	    	</cite>
   	    </li>
   	    
   	    <li>
    		<label>时间<b>*</b></label>
    		<cite>
    			<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
				<#if progress.progress_at ??>
					<input type="text" value="${progress.progress_at?number_to_datetime}" readonly  class="laydate-icon" name="progress.progress_at" class="dfinput" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" placeholder="请输入日期"/>
				<#else>
					<input type="text" value="" readonly  class="laydate-icon" name="progress.progress_at" class="dfinput" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" placeholder="请输入日期"/>
				</#if>
    		</cite>
   	    </li>

  		<li>
   		 	<label>&nbsp;</label>
   		 	<input name="" type="button" class="btn" id="btn_progress" value="保存" onclick="editProgressFun(${progress.project_id!},${progress.id!})"/>&nbsp;&nbsp
   		 	<input name="" type="button" onclick="resetFormFun()" class="btn" value="重置"/>
   		 </li>
    </ul>
    </div> 
    
	</div> 
 <script type="text/javascript" src="/static/js/page/project/progress_edit.js?v=20151208"></script>
</body>

</html>
