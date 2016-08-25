<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
  	<#include "/page/common/base/include.ftl">
</head>

<body>
<style>
	
.forminfo li label {
	text-align: right;
}

</style>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/user">用户管理</a></li>
        <li><a href="/user/detail/${user.id!}">查看</a></li>
    </ul>
</div>
    
<div class="formbody">
    <ul class="forminfo">
    	<li>
    		<label>Id：</label>
    		<cite>${user.id!}</cite>
   	    </li>
    	<li>
    		<label>昵称：</label>
    		<cite>${user.nickname!}</cite>
   	    </li>
    	<li>
    		<label>状态：</label>
    		<cite>
				<#if user.status == 1>
					正常
				<#else>
				        异常
				</#if>
    		</cite>
   	    </li>
    	<li>
    		<label>余额：</label>
    		<cite>${user.balance!}</cite>
   	    </li>
    	<li>
    		<label>头像：</label>
    		<cite>
    			<#if user.picture??>
    			<a href="${user.picture!}" target="_blank"><img src="${user.picture!}" width=100 height=100 ></a>
    			<#else>
    				<img src="" width=100 height=100 >
    			</#if>
    		</cite>
   	    </li>
    	<li>
    		<label>姓名：</label>
    		<cite>${user.realname!}</cite>
   	    </li>
    	<li>
    		<label>性别：</label>
    		<cite>
    			<#if user.status ??>
	    			<#if user.status == 1>
						男
					<#elseif user.status == 2>
					        女
					<#else>
					    未知
					</#if>
				</#if>
    		</cite>
   	    </li>
    	<li>
    		<label>证件类型：</label>
    		<cite>
    			<#if user.idtype ??>
	    			<#if user.idtype == 0>
						身份证
					<#else>
					    未知
					</#if>
				</#if>
    		</cite>
   	    </li>
   	    <li>
    		<label>证件号码：</label>
    		<cite>${user.idno!}</cite>
   	    </li>
    	<li>
    		<label>身份证照片：</label>
    		<cite>
    			<#if user.idphoto??>
    			<a href="${user.idphoto!}" target="_blank"><img src="${user.idphoto!}" width=100 height=100 ></a>
    			<#else>
    				<img src="" width=100 height=100 >
    			</#if>
    		</cite>
   	    </li>
    	<li>
    		<label>手机号：</label>
    		<cite>${user.phone!}</cite>
   	    </li>
    	<li>
    		<label>邮箱：</label>
    		<cite>${user.email!}</cite>
   	    </li>
    	<li>
    		<label>个人签名：</label>
    		<cite>${user.note!}</cite>
   	    </li>
    	<li>
    		<label>注册时间：</label>
    		<cite>${user.create_at?number_to_datetime}</cite>
   	    </li>
    	<li>
    		<label>&nbsp;</label>
    		<cite><a href="/user" class="tablelink">返回</a></cite>
   	    </li>
   	</ul>
</div>
</body>

</html>
