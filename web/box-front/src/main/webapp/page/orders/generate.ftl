<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<title>订单确认页</title>
	<#-- 
	<#include "/page/common/base/include.ftl">
	 --> 
	<script type="text/javascript" src="/static/js/common/jquery/jquery.js"></script>
</head>

<body>
	
	封面图片：<img src="${STATIC_URL!}${project.cover_image!}" width=120 height=120><br>
	项目名称：${project.name!}<br>
	回报内容：${product.desc!}<br>
	支付费用：${product.fund!}<br>
	配送费用：<#if (product.postage == 0)>
				免费
			<#else>
				product.postage
			</#if><br>
      回报天数：项目成功结束后${product.send_days!}天<br>
   <hr>
   应付金额：${product.fund!}<br>
   
   
   <hr>
   <#if (product.type == 0)>
   		实物
   		<#if receiveAddressList??>
   			<#list receiveAddressList as ra>
				<hr>
   				姓名:${ra.name!}<br>
   				联系方式:${ra.phone!}<br>
   				省份:${ra.province!}<br>
   				城市:${ra.city!}<br>
   				县城:${ra.area!}<br>
   				邮编:${ra.zipcode!}<br>
   				详细地址:${ra.detail_address!}<br>
   			</#list>
   		</#if>
   <#else>
   	      虚物
   </#if>
   <hr>
   	收货地址：
   	姓名<input />
   	联系电话<input />
   	收货地址
   	省份
   	<select name="province">
   		<option value="-1" >--请选择--</option>
   		<#if provincesList ??>
   		<#list provincesList as pl>
   		<option value="${pl.provinceid!}">${pl.province!}</option>
   		</#list>
   		</#if>
   	</select>
   	城市
   	<select name="city">
   		<option value="-1">--请选择--</option>
   	</select>
   	县城
   	<select name="area">
   		<option value="-1">--请选择--</option>
   	</select>
   	<br>
   	详细地址<input>
   <hr>
   
   <a href="/orders/checkout/${product.id!}">提交订单</a>
   
   <script type="text/javascript" src="/static/js/page/orders/generate.js"></script>
</body>
</html>
