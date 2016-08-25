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
					&nbsp;&nbsp;<a href="javascript:ajax_del(${o.id})" onclick="return confirm('您正在执行删除操作，是否继续？')">删除</a>
					&nbsp;&nbsp;<a href="/tb/edit/${o.id}">修改</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
	<input class="currentPage" style="display:none;" value="${tb_page.pageNumber}">
	<#include "../common/_paginate_ajax.ftl" />
	<@paginate currentPage=tb_page.pageNumber totalPage=tb_page.totalPage href_str="tbPage" />