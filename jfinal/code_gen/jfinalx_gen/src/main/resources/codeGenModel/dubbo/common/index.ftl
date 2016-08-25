<${"#"}include "../common/_layout.ftl"/>
<${"@"}layout>
<h3>想要快速开发，又要支持服务集群？选我就对了</h3>
<div class="table_box">
	
	<br><br><br>
	
	来看看下面的小例子吧~<br>
	<#list root.tablesMap?keys as key >
	<li><a href="/${root.tablesMap[key].name}"><b>${root.tablesMap[key].name}管理(${root.tablesMap[key].comment?if_exists})</b></a></li>
	</#list>
</div>
</${"@"}layout>

