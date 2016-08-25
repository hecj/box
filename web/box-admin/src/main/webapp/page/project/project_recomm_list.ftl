<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
  	<#include "/page/common/base/include.ftl"/>
</head>
<body>
<div class="place" style="width:100%">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/project/toProjectRecommList">推荐管理</a></li>
    </ul>
</div>
<div class="rightinfo">
	
    <div class="tools">
        <ul class="toolbar">
        	<select class="dfinput" name="type" style="width:100px;">
        		<#if name ??>
        			<option value="aphone">手机号</option>
        			<option value="name" selected>项目名称</option>
        		<#else>
        			<option value="aphone" selected>手机号</option>
        			<option value="name">项目名称</option>
        		</#if>
        	</select>
        	<#if aphone??>
        			<input type="text" value="${aphone!}" onchange="aaa()" name="search_name" style="width:150px;" class="dfinput"/>
        	<#else>
        			<input type="text" value="${name!}" onchange="aaa()" name="search_name" style="width:150px;" class="dfinput"/>
        	</#if>
        	&nbsp;&nbsp;&nbsp;
        	<label>项目状态：</label>
        	<select class="dfinput" name="status" style="width:170px;">
        			<option value="-1">--请选择--</option>
        			<#if status ?? && (status =="7")>
        				<option value="7" selected>产品预热</option>
        			<#else>
        				<option value="7">产品预热</option>
        			</#if>
        			<#if status ?? && (status =="8")>
        				<option value="8" selected>产品众筹中</option>
        			<#else>
        				<option value="8">产品众筹中</option>
        			</#if>
        			<#if status ?? && (status =="10")>
        				<option value="10" selected>产品众筹结束成功</option>
        			<#else>
        				<option value="10">产品众筹结束成功</option>
        			</#if>
        	</select>
        	&nbsp;&nbsp;&nbsp;&nbsp;
        	<button class="btn" onclick="recomm.query()">查询</button>
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	<button class="btn" onclick="recomm.recommQuery()">查询推荐</button>
        </ul>
    </div>
    <table class="tablelist" style="width:100%">
        <thead>
        <tr>
            <th style="text-align:center;width:5%;" title="">id</th>
            <th style="text-align:center;width:40%;" title="项目名称">项目名称</th>
            <th style="text-align:center;width:10%;" title="发布者">发布者</th>
            <th style="text-align:center;width:10%;" title="手机号码">手机号码</th>
            <th style="text-align:center;width:12%;" title="项目状态">项目状态</th>
            <th style="text-align:center;width:10%;" title="推荐状态">推荐位置</th>
            <th style="text-align:center;color:red;width:30%;">操作</th>
        </tr>
        </thead>
        <tbody>
        <#if recordList ??>
            <#list recordList.list as p>
            <tr>
	            <td style="text-align:center">${p.id!}</td>
	            <td>${p.name!}</td>
	            <td style="text-align:center">
	            	<#if p.user ??>
	            		${p.user.nickname!}
	            	</#if>
	            </td>
	            <td style="text-align:center">${p.aphone!}</td>
	            <td style="text-align:center">
	            		<#if p.status = 0 >草稿</#if>
	                	<#if p.status = 1 >初审提交</#if>
	                	<#if p.status = 2 >初审未通过</#if>
	                	<#if p.status = 3 >初审核通过/复审草</#if>
	                	<#if p.status = 4 >提交复审</#if>
	                	<#if p.status = 5 >复审打回</#if>
	                	<#if p.status = 6 >复审通过</#if>
	                	<#if p.status = 7 >产品预热</#if>
	                	<#if p.status = 8 >产品众筹中</#if>
	                	<#if p.status = 9 >产品众筹结束失败</#if>
	                	<#if p.status = 10 >产品众筹结束成功</#if>
	            </td>
	            <#if p.projectRecomm ??>
	            	<#assign pr=p.projectRecomm>
	            	<td style="text-align:center">
	            		<#if pr.position  == 0>
	            			项目列表
	            		<#elseif pr.position  == 1>
	            			首页
	            		</#if>
	            	</td>
		            <td style="text-align:left">
		            		<#if pr.position  == 0>
		            			<a onclick="recomm.submit(${p.id!},1)" class="tablelink">推荐至首页</a>&nbsp;&nbsp;&nbsp;&nbsp;
		            		</#if>
		            		<a onclick="recomm.submit(${p.id!},-1)" class="tablelink"><font color="red">取消推荐</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
		            		
		            </td>
	            <#else>
	            	<td style="text-align:center">-</td>
	            	<td><a onclick="recomm.submit(${p.id!},0)" class="tablelink">推荐</a></td>
	            </#if>
	            
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
    <#include "/page/common/_paginate_new.ftl" />
	<@paginate currentPage=recordList.pageNumber totalPage=recordList.totalPage totalRow=recordList.totalRow actionUrl="/project/toProjectRecommList/" urlParas="?aphone=${aphone!}&name=${name!}&recommQuery=${recommQuery!}&status=${status!}" />
</div>
<script type="text/javascript" src="/static/js/page/project/project_recomm_list.js?v=20151007"></script>
</body>
</html>
