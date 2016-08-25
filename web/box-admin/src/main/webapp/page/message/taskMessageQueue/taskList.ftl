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
        <li><a href="/message/taskMessageQueue/taskList/">发送通知记录</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
        </ul>
    </div>
    <table class="tablelist" style="width:100%">
        <thead>
        <tr>
            <th style="text-align:center;width:5%;" title="">Id</th>
            <th style="text-align:center;width:10%;" title="">任务类型</th>
            <th style="text-align:center;width:7%;" title="">用户集合</th>
            <th style="text-align:center;width:10%;" title="">手机号集合</th>
            <th style="text-align:center;width:5%;" title="">用户类型</th>
            <th style="text-align:center;width:10%;" title="">标题</th>
            <th style="text-align:center;width:20%;" title="">内容</th>
            <th style="text-align:center;width:6%;" title="">状态</th>
            <!-- 
            <th style="text-align:center;width:3%;" title="执行次数">次数</th>
            -->
            <th style="text-align:center;width:10%;" title="">失效时间</th>
            <th style="text-align:center;width:10%;" title="">创建时间</th>
        </tr>
        </thead>
        <tbody>
        <#if taskMessageQueuePage ??>
            <#list taskMessageQueuePage.list as ar>
       		<tr>
       			<td>${ar.id!}</td>
       			<td>
       				<#if ar.message_type??>
       				<#if ar.message_type?index_of("system")!=-1>
       				系统消息&nbsp;&nbsp;
       				</#if>
       				<#if ar.message_type?index_of("email")!=-1>
       				邮件&nbsp;&nbsp;
       				</#if>
       				<#if ar.message_type?index_of("phone")!=-1>
					短信
					</#if>
					</#if>
       			</td>
       			<td>${ar.user_ids!}</td>
       			<td>${ar.phones!}</td>
       			<td><#if ar.user_type??>
       				<#if ar.user_type = 1>
       				全部用户
       				<#elseif ar.user_type = 2>
       				注册用户
       				<#elseif ar.user_type = 3>
       				认证用户
       				<#elseif ar.user_type = 4>
       				第三方游客
       				</#if>
       				</#if>
       			</td>
       			<td>${ar.title!}</td>
       			<td>${ar.content!}</td>
       			<td>
	       			<#if ar.status??>
	       				<#if ar.status = 0>
	       				未执行
	       				<#elseif ar.status = 1>
	       				未执行
	       				<#elseif ar.status = 2>
	       				执行完成
	       				</#if>
       				</#if>
       			</td>
       			<!-- 
       			<td>${ar.count!}</td>
       			-->
       			<td>${ar.invalid_at?number_to_datetime}</td>
       			<td>${ar.create_at?number_to_datetime}</td>
       		</tr>
       		</#list>
        </#if>
        </tbody>
    </table>
    <#include "/page/common/_paginate_new.ftl" />
    <@paginate currentPage=taskMessageQueuePage.pageNumber totalPage=taskMessageQueuePage.totalPage totalRow=taskMessageQueuePage.totalRow actionUrl="/message/taskMessageQueue/taskList/" urlParas="?" />
</div>
<script>
	
	$(function(){
 		$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100' style='text-align:center'>暂无数据</td></tr>")}
 	});
</script>
</body>
</html>
