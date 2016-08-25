<${"#"}include "/page/common/_layout.ftl"/>
<${"@"}layout>
<h1>${root.name}列表&nbsp;&nbsp;
<a href="/${root.name}/add">添加</a>
</h1>
<div class="table_box">
	<table class="list">
		<tbody>
			<tr>
				<#list root.columnlist as col>
				<th width="10%">${col.name}(${col.comment?if_exists})</th>
				</#list>
				<th width="10%">操作</th>
			</tr>
			
			<${"#"}list pg.getList() as ${root.name}>
			<tr>
				<#list root.columnlist as col>
				<td style="text-align:center;">${"$"}{${root.name}.${col.name}!}</td>
				</#list>
				<td style="text-align:center;">
					&nbsp;&nbsp;<a href="/${root.name}/delete/${"$"}{${root.name}.id}">删除</a>
					&nbsp;&nbsp;<a href="/${root.name}/edit/${"$"}{${root.name}.id}">修改</a>
				</td>
			</tr>
			</${"#"}list>
		</tbody>
	</table>
	<${"#"}include "/page/common/_paginate.ftl" />
	<${"@"}paginate currentPage=pg.pageNumber totalPage=pg.totalPage actionUrl="/${root.name}/" />
</div>
</${"@"}layout>