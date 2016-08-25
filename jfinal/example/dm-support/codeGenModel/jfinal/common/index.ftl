<${"#"}include "/page/common/_layout.ftl"/>
<${"@"}layout>
<h1>目录</h1>
<div class="table_box">
	<#list root.tablesMap?keys as key >
	<li><a href="/${root.tablesMap[key].name}"><b>${root.tablesMap[key].name}管理(${root.tablesMap[key].comment?if_exists})</b></a></li>
	</#list>
</div>
</${"@"}layout>