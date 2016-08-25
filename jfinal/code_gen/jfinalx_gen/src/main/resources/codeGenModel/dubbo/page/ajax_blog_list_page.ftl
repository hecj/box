	<table class="list">
		<tbody>
			<tr>
				<#list root.columnlist as col>
				<th width="10%">${col.name}(${col.comment?if_exists})</th>
				</#list>
			 
				<th width="12%">操作</th>
			</tr>
			
			<${"#"}list ${root.name}_page.getList() as o>
			<tr>
				<#list root.columnlist as col>
					<td style="text-align:left;">${"$"}{(o.${col.name})!}</td>
				</#list>
				<td style="text-align:left;">
					&nbsp;&nbsp;<a href="javascript:ajax_del(${"$"}{o.id})" onclick="return confirm('您正在执行删除操作，是否继续？')">删除</a>
					&nbsp;&nbsp;<a href="/${root.name}/edit/${"$"}{o.id}">修改</a>
				</td>
			</tr>
			</${"#"}list>
		</tbody>
	</table>
	<input class="currentPage" style="display:none;" value="${"$"}{${root.name}_page.pageNumber}">
	<${"#"}include "../common/_paginate_ajax.ftl" />
	<${"@"}paginate currentPage=${root.name}_page.pageNumber totalPage=${root.name}_page.totalPage href_str="${root.name}Page" />