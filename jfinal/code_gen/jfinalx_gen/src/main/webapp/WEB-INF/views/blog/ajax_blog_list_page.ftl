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
					&nbsp;&nbsp;<a href="javascript:ajax_del(${o.id})" onclick="return confirm('您正在执行删除操作，是否继续？')">删除</a>
					&nbsp;&nbsp;<a href="/blog/edit/${o.id}">修改</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
	<input class="currentPage" style="display:none;" value="${blog_page.pageNumber}">
	<#include "../common/_paginate_ajax.ftl" />
	<@paginate currentPage=blog_page.pageNumber totalPage=blog_page.totalPage href_str="blogPage" />