<#include "../common/_layout.ftl"/>
<@layout>
<h3>Blog列表
</h3>
<a href="/blog/ajax_blog_list">Blog ajax列表</a>
<a href="/blog/add">新增Blog</a>
<div class="table_box">
	<table class="list">
		<tbody>
			<tr>
				<th width="4%">id</th>
				<th width="35%">标题</th>
				<th width="12%">操作</th>
			</tr>
			
			<#list blog_page.getList() as x>
			<tr>
				<td style="text-align:left;">${x.id}</td>
				<td style="text-align:left;">${x.title}</td>
				<td style="text-align:left;">
					&nbsp;&nbsp;<a href="/blog/delete/${x.id}" onclick="return confirm('您正在执行删除操作，是否继续？')">删除</a>
					&nbsp;&nbsp;<a href="/blog/edit/${x.id}">修改</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
	<#include "../common/_paginate.ftl" />
	<@paginate currentPage=blog_page.pageNumber totalPage=blog_page.totalPage actionUrl="/blog/" />
</div>
</@layout>