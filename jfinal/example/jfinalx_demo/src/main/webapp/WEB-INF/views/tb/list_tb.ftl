<#include "../common/_layout.ftl"/>
<@layout>
<h3>tb列表
</h3>
<a href="/tb/ajax_tb_list">tb ajax列表</a>
<a href="/tb/add">新增tb</a>
<div class="table_box">
	<table class="list">
		<tbody>
			<tr>
				<th width="10%">id()</th>
				<th width="10%">n(模型名)</th>
				<th width="10%">cm(注释)</th>
				<th width="10%">eg()</th>
			 
				<th width="12%">操作</th>
			</tr>
			
			<#list tb_page.getList() as o>
			<tr>
					<td style="text-align:left;">${(o.id)!}</td>
					<td style="text-align:left;">${(o.n)!}</td>
					<td style="text-align:left;">${(o.cm)!}</td>
					<td style="text-align:left;">${(o.eg)!}</td>
				<td style="text-align:left;">
					&nbsp;&nbsp;<a href="/tb/delete/${o.id}" onclick="return confirm('您正在执行删除操作，是否继续？')">删除</a>
					&nbsp;&nbsp;<a href="/tb/edit/${o.id}">修改</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
	<#include "../common/_paginate.ftl" />
	<@paginate currentPage=tb_page.pageNumber totalPage=tb_page.totalPage actionUrl="/tb/" />
</div>
</@layout>