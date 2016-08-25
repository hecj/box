<${"#"}include "../common/_layout.ftl"/>
<${"@"}layout>
<h3>${root.name}列表
</h3>
<a href="/${root.name}/ajax_${root.name}_list">${root.name} ajax列表</a>
<a href="/${root.name}/add">新增${root.name}</a>
<div class="table_box">
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
					&nbsp;&nbsp;<a href="/${root.name}/delete/${"$"}{o.id}" onclick="return confirm('您正在执行删除操作，是否继续？')">删除</a>
					&nbsp;&nbsp;<a href="/${root.name}/edit/${"$"}{o.id}">修改</a>
				</td>
			</tr>
			</${"#"}list>
		</tbody>
	</table>
	<${"#"}include "../common/_paginate.ftl" />
	<${"@"}paginate currentPage=${root.name}_page.pageNumber totalPage=${root.name}_page.totalPage actionUrl="/${root.name}/" />
</div>
</${"@"}layout>