<#include "../common/_layout.ftl"/>
<@layout>
<h3>kv_config列表
</h3>
<a href="/kv_config/ajax_kv_config_list">kv_config ajax列表</a>
<a href="/kv_config/add">新增kv_config</a>
<div class="table_box">
	<table class="list">
		<tbody>
			<tr>
				<th width="10%">id(编号)</th>
				<th width="10%">k(键)</th>
				<th width="10%">v(值)</th>
			 
				<th width="12%">操作</th>
			</tr>
			
			<#list kv_config_page.getList() as o>
			<tr>
					<td style="text-align:left;">${(o.id)!}</td>
					<td style="text-align:left;">${(o.k)!}</td>
					<td style="text-align:left;">${(o.v)!}</td>
				<td style="text-align:left;">
					&nbsp;&nbsp;<a href="/kv_config/delete/${o.id}" onclick="return confirm('您正在执行删除操作，是否继续？')">删除</a>
					&nbsp;&nbsp;<a href="/kv_config/edit/${o.id}">修改</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
	<#include "../common/_paginate.ftl" />
	<@paginate currentPage=kv_config_page.pageNumber totalPage=kv_config_page.totalPage actionUrl="/kv_config/" />
</div>
</@layout>