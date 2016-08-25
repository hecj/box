<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    <#include "/page/common/base/include.ftl">
<style>
</style>
</head>  
<body>
<div class="place" style="width:100%">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/message/sendSmsRecord">短信发送记录</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
        	手机号：
        	<input type="text" name="phone" value="${phone!}" style="width:200px;" class="dfinput"/>
        	<button class="btn" onclick="querySendSmsRecordFun()">查询</button>
        </ul>
    </div>
    <table class="tablelist" style="width:100%">
        <thead>
        <tr>
            <th style="text-align:center;width:5%;" title="">Id</th>
            <th style="text-align:center;width:10%;" title="">手机号</th>
            <th style="text-align:center;width:60%;" title="">发送内容</th>
            <th style="text-align:center;width:10%;" title="">返回报文</th>
            <th style="text-align:center;width:10%;" title="">发送时间</th>
        </tr>
        </thead>
        <tbody>
        <#if sendSmsRecordPage ??>
            <#list sendSmsRecordPage.list as ar>
       		<tr>
       			<td>${ar.id!}</td>
       			<td>${ar.phone!}</td>
       			<td>${ar.content!}</td>
       			<td>${ar.result!}</td>
       			<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
       			<td>${ar.create_at?number_to_datetime}</td>
       		</tr>
       		</#list>
        </#if>
        </tbody>
    </table>
    <#include "/page/common/_paginate_new.ftl" />
    <@paginate currentPage=sendSmsRecordPage.pageNumber totalPage=sendSmsRecordPage.totalPage totalRow=sendSmsRecordPage.totalRow actionUrl="/message/sendSmsRecord/" urlParas="?phone=${phone!}" />
</div>

<script type="text/javascript" src="/static/js/page/message/sendSmsRecord/index.js"></script>
	
</body>
</html>
