<#include "../common/_layout.ftl"/>
<@layout>
<h3>blog列表
</h3>
<a href="/blog/ajax_blog_list">blog ajax列表</a>
<a href="/blog/add">新增blog</a>
<div class="table_box">
	<table class="list">
		<tbody>
			<tr>
				<th width="10%">id()</th>
				<th width="10%">title()</th>
				<th width="10%">content()</th>
			 
				<th width="12%">操作</th>
			</tr>
			
			<#list blog_page.getList() as o>
			<tr>
					<td style="text-align:left;">${(o.id)!}</td>
					<td style="text-align:left;">${(o.title)!}</td>
					<td style="text-align:left;">${(o.content)!}</td>
				<td style="text-align:left;">
					&nbsp;&nbsp;<a href="/blog/delete/${o.id}" onclick="return confirm('您正在执行删除操作，是否继续？')">删除</a>
					&nbsp;&nbsp;<a href="/blog/edit/${o.id}">修改</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
	<#include "../common/_paginate.ftl" />
	<@paginate currentPage=blog_page.pageNumber totalPage=blog_page.totalPage actionUrl="/blog/" />
</div>
</@layout>