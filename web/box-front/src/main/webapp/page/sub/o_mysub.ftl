<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta name="renderer" content="webkit">
    <title>我的关注</title>
    <META http-equiv=Content-Type content="text/html; charset=UTF-8">
    
    <#--<#include "/page/common/base/include.ftl">-->
    <script type="text/javascript" src="/static/js/common/jquery/jquery.js"></script>
	
</head>
<body>
	<#include "/page/common/head.ftl" />
	<#if (subscribeList?size>0)>
		<table cellpadding="10">
		    <tr>
		        <td>项目名称</td>
		        <td>日期</td>
		        <td>操作</td>
		    </tr>
			    <#list subscribeList as s>
		        <tr>
		            <td>
		            	<a href="/project/detail/${s.project_id!-1}"><img src="${s.cover_image!}"/>${s.name!}</a>
		            </td>
		            <td>${s.create_at!}</td>
		            <td><a href="javascript:;" onclick="subscribeFun(${s.project_id!-1},2)">取消关注</a></td>
		        </tr>
			    </#list>
		</table>
		<br>
		<div id="subscribePage">分页位置</div>
	<#else>
		暂无关注的项目
	</#if>
	<br><br>
	<#include "/page/common/foot.ftl" />
</body>
	<script type="text/javascript" src="/static/js/common/paging/paging.js"></script>
	<script type="text/javascript" src="/static/js/page/sub/mysub.js"></script>
	<script type="text/javascript">
		//加载分页
		$("#subscribePage").createPage({
			pageCount : ${totalPage},
			current : ${pageNumber},
			backFn : function(p) {
				console.log(p);
				window.location="/sub/mysub/"+p;
			}
		});
	</script>
</html>