<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    <#include "/page/common/base/include.ftl">
<style>
</style>
</head>  
<body>
<div class="place" style="width:115%">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/project">项目管理</a></li>
    </ul>
</div>
<div class="rightinfo">
	
    <div class="tools">
        <ul class="toolbar">
        	手机号：
        	<input type="text" name="uPhone" value="${uPhone!}" style="width:150px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	项目名称：
        	<input type="text" name="name" value="${name!}" style="width:150px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	状态：
        	<select class="dfinput" name="status" style="width:170px;">
        		<option value="-1">--请选择--</option>
        		
        		<#if status ?? && status == "0">
        				<option value="0" selected>草稿</option>
        		<#else>
        				<option value="0">草稿</option>
        		</#if>
        		
        		<#if status ?? && status == "1">
        				<option value="1" selected>初审提交</option>
        		<#else>
        				<option value="1">初审提交</option>
        		</#if>
        		
        		<#if status ?? && status == "2">
        				<option value="2" selected>初审未通过</option>
        		<#else>
        				<option value="2">初审未通过</option>
        		</#if>
        		
        		<#if status ?? && status == "3">
        				<option value="3" selected>初审核通过/复审草稿</option>
        		<#else>
        				<option value="3">初审核通过/复审草稿</option>
        		</#if>
        		
        		<#if status ?? && status == "4">
        			<option value="4" selected>提交复审</option>
        		<#else>
        			<option value="4">提交复审</option>
        		</#if>
        		
        		<#if status ?? && status == "5">
        			<option value="5" selected>复审打回</option>
        		<#else>
        			<option value="5">复审打回</option>
        		</#if>
        		
        		<#if status ?? && status == "6">
        			<option value="6" selected>复审通过</option>
        		<#else>
        			<option value="6">复审通过</option>
        		</#if>
        		
        		<#if status ?? && status == "7">
        			<option value="7" selected>产品预热</option>
        		<#else>
        			<option value="7">产品预热</option>
        		</#if>
        		
        		<#if status ?? && status == "8">
        			<option value="8" selected>产品众筹中</option>
        		<#else>
        			<option value="8">产品众筹中</option>
        		</#if>
        		
        		<#if status ?? && status == "9">
        			<option value="9" selected>产品众筹结束失败</option>
        		<#else>
        			<option value="9">产品众筹结束失败</option>
        		</#if>
        		
        		<#if status ?? && status == "10">
        			<option value="10" selected>产品众筹结束成功</option>
        		<#else>
        			<option value="10">产品众筹结束成功</option>
        		</#if>
        	</select>
        	&nbsp;&nbsp;
        	<button class="btn" onclick="queryProjectFun()">查询</button>
        </ul>
    </div>
    <table class="tablelist" style="width:115%">
        <thead>
        <tr>
        <#--<th ><input name="" type="checkbox" onclick="checkAll(this.checked,'')"/></th>-->
            <th style="text-align:center;width:5%;" title="">id</th>
            <th style="text-align:center;width:8%;" title="名称">名称</th>
            <th style="text-align:center;width:17%;" title="内容">内容</th>
            <th style="text-align:center;width:5%;" title="筹款金额(元)">筹款金额(元)</th>
            <th style="text-align:center;width:5%;" title="已筹金额(元)">已筹金额(元)</th>
            <th style="text-align:center;width:5%;" title="参与人数">参与人数</th>
            <th style="text-align:center;width:5%;" title="手机号">手机号</th>
            <th style="text-align:center;width:10%;" title="状态">状态</th>
            <th style="text-align:center;width:5%;" title="认证状态">认证状态</th>
            <th style="text-align:center;color:red;width:5%;">结算</th>
            <th style="text-align:center;color:red;width:5%;">初审</th>
            <th style="text-align:center;color:red;width:5%;">复审</th>
            <th style="text-align:center;color:red;width:15%;">操作</th>
        </tr>
        </thead>
        <tbody>
        <#if recordList ??>
            <#list recordList.list as p>
            	<tr>
	                <td>${p.id!}</td>
	                <td title="${p.name!}">
	                	<#if p.name??>
	                		<#if p.name ? length lt 10>
	                			${p.name}
	                		<#else>
	                			 ${p.name?substring(0,10)}...
	                		</#if>
	                	</#if>
	                </td>
	                <td title="${p.acontext!}">
	                	<#if p.desc??&&p.desc!="">
	                		<#if p.desc ? length lt 20>
	                			${p.desc}
	                		<#else>
	                			 ${p.desc?substring(0,20)}...
	                		</#if>
	                	<#elseif p.acontext??>
	                		<#if p.acontext ? length lt 20>
	                			${p.acontext}
	                		<#else>
	                			 ${p.acontext?substring(0,20)}...
	                		</#if>
	                	<#else>
	                	
	                	</#if>
	                </td>
	                <td>
	                		${p.fundgoal!}
	                </td>
	                <td>
	                		${p.fundnow!}
	                </td>
	                <td>
	                		${p.fundpcount!}
	                </td>
	                <td>${p.aphone!}</td>
	                <td style="text-align:center;">
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
	                <td style="text-align:center;">
	                	<#if p.certify ?? && p.certify = 0><font color="red">未认证</font></#if>
	                	<#if p.certify ?? && p.certify = 1>申请中</#if>
	                	<#if p.certify ?? && p.certify = 2>认证通过</#if>
	                </td>
	                <td style="text-align:center;">
	                	<#if p.settlement ?? && p.settlement = 1 >
                			已结算
                		<#else>
                	</#if>
	                </td>
	                <td style="text-align:center;">
	                    <#if p.status == 1>
	                    	<a href="/project/toPassapply/${p.id!}" class="tablelink"><font color="green">审核</font></a>
	                    <#else>
	                        <a href="/project/toPassapply/${p.id!}" class="tablelink">查看</a>
	                    </#if>
	                </td>
	                <td style="text-align:center;">
	                    <#if p.status == 4 || p.status == 5 || p.status == 6 >
	                    	<!--
	                        <a href="/project/passproject/${p.id!}-pass" class="tablelink">通过</a>&nbsp;
	                        <a href="/project/passproject/${p.id!}-nopass" class="tablelink">不通过</a>
	                        -->
	                        <a href="/project/toPassproject/${p.id!}" class="tablelink"><font color="green">审核</font></a>
	                     <#elseif (p.status >= 7) >
	                       	<a href="/project/toPassproject/${p.id!}" class="tablelink">查看</a>
	                    <#else>
	                        -
	                    </#if>
	                </td>
                <td>
                
                <#if (p.status == 6) || (p.status == 7) || (p.status==8) >
                	<a href="/project/productSet/${p.id!}" class="tablelink">回报设置</a>&nbsp;|
                </#if>
                
                <#if (p.status == 7) || (p.status == 8) || (p.status == 9) || (p.status == 10) >
                	<a href="/project/progress/${p.id!}" class="tablelink">项目进展</a>&nbsp;|
                </#if>
                
                <#if (p.status == 6)>
                    <a onclick="statusFun6(${p.id!})" class="tablelink">开始预热</a> |
                </#if>
                <#--
                <#if (p.status == 7)>
                    <a  onclick="statusFun7(${p.id!})" class="tablelink">结束预热</a> |
                </#if>
                -->
                <#if (p.status == 6) || (p.status == 7)>
                    <a onclick="projectonline(${p.id!})" class="tablelink">项目上线</a> |
                </#if>
                <#if (p.status == 8)>
                    <a onclick="projectshutdown(${p.id!})" class="tablelink">项目结束</a> |
                </#if>
                
                <#if (p.status == 8) || (p.status == 9) || (p.status == 10) >
                	<a href="/orders/?project_id=${p.id!}&project_name=${p.name!}" class="tablelink">支持列表</a>|
                </#if>
                <#if (p.status == 10) >
                	<#if p.settlement ?? && p.settlement = 1 >
                	
                	<#else>
                		<#if p.isBalance>
                			<a onclick="balanceFun(${p.id!})" class="tablelink">结算</a>
                		</#if>
                	</#if>
                </#if>
                
                </td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
    <#include "/page/common/_paginate_new.ftl" />
	<@paginate currentPage=recordList.pageNumber totalPage=recordList.totalPage totalRow=recordList.totalRow actionUrl="/project/" urlParas="?uPhone=${uPhone!}&name=${name!}&status=${status!}" />
</div>

<script type="text/javascript" src="/static/js/page/project/index.js?v=20151216"></script>
	
</body>
</html>
