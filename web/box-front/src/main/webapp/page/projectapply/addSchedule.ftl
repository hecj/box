<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <meta name="renderer" content="webkit">
    <title>商品详情</title>
    <META http-equiv=Content-Type content="text/html; charset=UTF-8">
    <#include "/page/common/base/include.ftl">
    <script type="text/javascript" src="/static/js/DatePicker.js"></script>
</head>
<body>

<#include "/page/common/head.ftl" />

<br/>
<br/>
<div>
	添加项目进度<br/>
	<form action="/projectapply/addSchedule" method="post">
    	<table>
    		<tr>
    			<td>项目</td>
    			<td>时间</td>
    			<td>详情</td>
    		</tr>
    		<tr>
    			<td><input type="text" name="pname" value="${p.aname}" readonly="true"><input type="hidden" name="pid" value="${p.id}"> </td>
    			<td><input type="text" name="time" onfocus="setday(this,'yyyy-MM-dd','1900-01-01','2050-01-01',1)" readonly="readonly"/></td>
    			<td><input type="text" name="info"></td>
    		</tr>
    	</table>
    	<input type="submit" value="提交">
 	</form>
</div>

<br/>
<br/>


<#include "/page/common/foot.ftl" />

</body>
</html>