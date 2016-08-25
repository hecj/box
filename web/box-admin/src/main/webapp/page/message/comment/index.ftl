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
        <li><a href="/message/comment">用户评论</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
        	输入评论内容：
        	<input type="text" name="content" value="${content!}" style="width:200px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	项目名称：
        	<input type="text" name="project_name" value="${project_name!}" style="width:200px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	<button class="btn" onclick="queryFun()">查询</button>
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	<select class="dfinput" name="operator_type" style="width:100px;">
        		<option value="1">允许显示</option>
        		<option value="2">禁止显示</option>
        		<option value="3">删除评论</option>
        	</select>
        	<button class="btn" onclick="operatorFun()">确定</button>
        </ul>
    </div>
    <table class="tablelist" style="width:100%">
        <thead>
        <tr>
            <th style="text-align:center;width:2%;" title=""><input type="checkbox" name="comment_checkbox_group"/></th>
            <th style="text-align:center;width:3%;" title="">Id</th>
            <th style="text-align:center;width:10%;" title="">用户名</th>
            <th style="text-align:center;width:10%;" title="">评论项目</th>
            <th style="text-align:center;width:10%;" title="">IP地址</th>
            <th style="text-align:center;width:10%;" title="">评论时间</th>
            <th style="text-align:center;width:3%;" title="">状态</th>
            <th style="text-align:center;width:10%;" title="">操作</th>
        </tr>
        </thead>
        <tbody>
        <#if commentPage ??>
            <#list commentPage.list as c>
       		<tr>
       			<td style="text-align:center;"><input type="checkbox" name="comment_checkbox" value="${c.id!}"/></td>
       			<td>${c.id!}</td>
       			<td>${c.nickname!}</td>
       			<td>${c.project_name!}</td>
       			<td>${c.ip!}</td>
       			<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
       			<td>${c.create_at?number_to_datetime}</td>
       			<td>
       				<#if c.is_delete = 0>
       				  显示
       				<#elseif c.is_delete = 1>
       				 隐藏
       				 <#elseif c.is_delete = 2>
       				 删除
       				</#if>
       			</td>
       			<td>
       				<a href="/message/comment/detail/${c.id!}" class="tablelink">查看详情</a>
       				|
       				<a onclick="deleteCommnent(${c.id!})" class="tablelink">移除</a>
       			</td>
       		</tr>
       		</#list>
        </#if>
        </tbody>
    </table>
    <#include "/page/common/_paginate_new.ftl" />
    <@paginate currentPage=commentPage.pageNumber totalPage=commentPage.totalPage totalRow=commentPage.totalRow actionUrl="/message/comment/" urlParas="?project_id=${project_id!}&project_name=${project_name!}&nickname=${content!}&phone=${content!}" />
</div>

<script type="text/javascript" src="/static/js/page/message/comment/index.js"></script>
	
</body>
</html>
