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
        <li><a href="/usercertify">实名审核</a></li>
        <li><a href="/usercertify/toCertifyApply/${user.id!}">审核</a></li>
    </ul>
</div>
    
<div class="formbody">
    
    <ul class="forminfo">
    	<li>
    		<label>真实姓名：</label>
    		<cite>
    			${user.realname!}
   	    	</cite>
   	    </li>
    	<li>
    		<label>身份证号：</label>
    		<cite>
    			${user.idno!}
   	    	</cite>
   	    </li>
    	<li>
    		<label>性别：</label>
    		<cite>
    			<#if user.sex ?? && user.sex == 1>
            		男
            	<#elseif user.sex ?? && user.sex == 2>	
            		女
            	</#if>
   	    	</cite>
   	    </li>
   	    <li>
    		<label>身份证照片：</label>
    		<cite>
    			<img src="${user.idphoto!}" width="150px" height="150px">
    			
   	    	</cite>
   	    </li>
   	    <li>
    		<label>手机号码：</label>
    		<cite>
				${user.phone!}    			
   	    	</cite>
   	    </li>
   	    <li>
    		<label>邮箱：</label>
    		<cite>
    			${user.email!}
   	    	</cite>
   	    </li>
   	    </ul>
   	    <br>
   	    <#if user.certify?? && user.certify == 1>
   	    <div class="xline"/>
   	    <br><br>
   	    <ul class="forminfo">
   	    <li>
    		<label>&nbsp;</label>
    		<cite>
    			<input type="radio" name="audit_type" value="pass">审核通过&nbsp;&nbsp;
    			<input type="radio" name="audit_type" value="nopass">审核不通过
   	    	</cite>
   	    </li>
   	    <br>
  		<li>
   		 	<label>&nbsp;</label>
   		 	<input name="" type="button" class="btn" id="submit_btn" value="提交" onclick="auditSumit(${user.id!})"/>
   		 </li>
    	</ul>
    	</#if>
    </div>
    
	</div> 
 <script type="text/javascript" src="/static/js/page/usercertify/certify-apply.js"></script>
</body>

</html>
