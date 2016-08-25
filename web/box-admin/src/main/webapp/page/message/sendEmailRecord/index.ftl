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
        <li><a href="/message/sendEmailRecord">邮件发送记录</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
        	收件人邮箱：
        	<input type="text" name="reciver_email" value="${reciver_email!}" style="width:200px;" class="dfinput"/>
        	收件人UID：
        	<input type="text" name="reciver" value="${reciver!}" style="width:200px;" class="dfinput"/>
        	<button class="btn" onclick="querySendEmailRecordFun()">查询</button>
        </ul>
    </div>
    <table class="tablelist" style="width:100%">
        <thead>
        <tr>
            <th style="text-align:center;width:5%;" title="">Id</th>
            <th style="text-align:center;width:5%;" title="">收件人UID</th>
            <th style="text-align:center;width:7%;" title="">收件人邮箱</th>
            <th style="text-align:center;width:10%;" title="">标题</th>
            <th style="text-align:center;width:30%;" title="">内容</th>
            <th style="text-align:center;width:3%;" title="">类型</th>
            <th style="text-align:center;width:7%;" title="">发送状态</th>
            <th style="text-align:center;width:10%;" title="">创建时间</th>
        </tr>
        </thead>
        <tbody>
        <#if sendEmailRecordPage ??>
            <#list sendEmailRecordPage.list as ar>
       		<tr>
       			<td>${ar.id!}</td>
       			<td>${ar.reciver!}</td>
       			<td>${ar.reciver_email!}</td>
       			<td>${ar.title!}</td>
       			<td>${ar.content!}</td>
       			<td>${ar.type!}</td>
       			<td style="text-align:center;">
       				<#if ar.isSucc??>
       				<#if ar.isSucc = 0>
       				  成功
       				<#elseif ar.isSucc = 1>
       				  失败
       				</#if>
       				</#if>
       			</td>
       			<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
       			<td>${ar.create_at?number_to_datetime}</td>
       		</tr>
       		</#list>
        </#if>
        </tbody>
    </table>
    <#include "/page/common/_paginate_new.ftl" />
    <@paginate currentPage=sendEmailRecordPage.pageNumber totalPage=sendEmailRecordPage.totalPage totalRow=sendEmailRecordPage.totalRow actionUrl="/message/sendEmailRecord/" urlParas="?reciver=${reciver!}&reciver_email=${reciver_email!}" />
</div>

<script type="text/javascript" src="/static/js/page/message/sendEmailRecord/index.js"></script>
	
</body>
</html>
