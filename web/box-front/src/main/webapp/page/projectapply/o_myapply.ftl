<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta name="renderer" content="webkit">
    <title>商品详情</title>
    <META http-equiv=Content-Type content="text/html; charset=UTF-8">
    
    <style type="text/css">

        /* 表格css样式
         */
        table {
            font-family: verdana, arial, sans-serif;
            font-size: 11px;
            color: #333333;
            border-width: 1px;
            border-color: #666666;
            border-collapse: collapse;
        }

        table th {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #666666;
            background-color: #dedede;
        }

        table td {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #666666;
            background-color: #ffffff;
        }
    </style>
    
    <!-- 表格样式 -->
	
	<link href="/static/css/tablesorter/style.css" rel="stylesheet" type="text/css">
	
</head>

<body>
<!--	<#include "/page/common/head.ftl" />  -->
<br/><br/>
<div>
    
    <table class="tablesorter">
        <thead>
        	<tr>
	            <th>id</th>
	            <th>项目名称</th>
	            <th>发布时间</th>
	            <th>目标/进度</th>
	            <th>项目状态
	            	<select name="status" id="status" onchange="findByStatus()">
	            		<#list statusMap.keySet() as state>
	            			<option value="${state!}" <#if status==state> selected = "selected"</#if>>${statusMap.get(state)!}</option>
	            		</#list>
	            	</select>
	            </th>
	            <th>结算状态</th>
	            <th>操作</th>
        	</tr>
        </thead>
        <tbody>
        	<#setting datetime_format="yyyy/MM/dd"/>
        	<#if pageProject ??>
		    	<#list pageProject.getList() as p><tr>
		            <td>${p.id!}</td>
		            <td>${p.name!}</td>
		            <td>${(p.createtime?number_to_datetime)!}</td>
		            <td>${p.fundnow!}/${p.fundgoal!} ${p.progress!}%</td>
		            <td> 
		            	<#if p.id??>
		            		<#if p.status == 1>
		            			初审审核中
		            		<#elseif p.status == 4>
		            			复审审核中
		            		<#else>
		            			${p.statusName!}
		            		</#if>
		            	</#if>
		            </td>
		            <td>
		            	<#if p.id??>
		            		<#if p.status == 10 && p.settlement == 0>
		            			<a href="" onclick="alert(您的申请已提交)">申请结算</a>
		            		<#elseif p.status == 10 && p.settlement == 1>
		            			<a href="">已结算</a>
		            		<#else>
		            			不可结算
		            		</#if>
		            	</#if>
		            </td>
		           
		            <td>
		                <#if p.id??>
		                    <#if p.status == 0 || p.settlement == 1>
		                        <a href="/projectapply/deleteapply/${p.id}">删除</a>
		                    </#if>
		                    
		                    <#if p.status == 2 >
		                     	<a onmouseover="findExecute(${p.id!},${p.status!})">查看驳回原因</a>
		                        <a href="/projectapply/deleteapply/${p.id}">删除</a>	                       
		                    </#if>
		                    
		                    
		                    <!-- 继续填复审表/回报表/提交申请 -->
		                    <#if (p.status == 3 ) &&(isCertify==2)>
		                        <a href="/projectapply/editproject/${p.id}">补充资料</a>
		                    </#if>
		                    <#if (p.status == 6)&&(isCertify==2)>
		                    	等待上线
		                    </#if>
		                    <#if (p.status == 5 ) &&(isCertify==2)>
		                   		<a onmouseover="findExecute(${p.id!},${p.status!})">查看驳回原因</a>
		                        <a href="/projectapply/editproject/${p.id}">编辑</a>
		                    </#if>
		                    
		                    <!-- 预览 -->
		                    <#if (p.status >= 7) && (p.status <= 10) &&(isCertify==2)>
		                        <a href="/projectapply/preview/${p.id}">预览</a>
		                    </#if>
		                    
		                    
		                    <#if (isCertify==0)&&(p.status==3)>
		                    	<a href="/usercertify/certify">去认证</a>
		                    <#elseif (isCertify==1)&&(p.status==3)>
		                    	认证中
		                    <#else>
		                  		
		                    </#if>
		                </#if>
		            </td>
		        </tr>
		    	</#list>
		    </#if>
    	</tbody>
    </table>
</div>
<div id="pagemyapply">
	
</div>

<br/>
<div>${errorMsg!}</div><br/><br/><br/><br/>

<#include "/page/common/foot.ftl" />

</body>
		<script type="text/javascript" src="/static/js/common/jquery/jquery.js"></script>	
		<script type="text/javascript" src="/static/js/page/projectapply/myapply.js"></script>
		<script type="text/javascript" src="/static/js/common/paging/paging.js"></script>
		<script>
			$("#pagemyapply").createPage({
				pageCount:${pageProject.totalPage},
				current:${pageProject.pageNumber},
				backFn:function(p){
					var status = $("#status").val()
					location = "/projectapply/myapply/"+status+"-"+p;
			}});
		</script>
</html>