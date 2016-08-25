<#include "../common/_layout.ftl"/>
<@layout>
<h3>tbc列表
</h3>
<a href="/tbc/ajax_tbc_list">tbc ajax列表</a>
<a href="/tbc/add">新增tbc</a>
<div class="table_box">
	<table class="list">
		<tbody>
			<tr>
				<th width="10%">id()</th>
				<th width="10%">tbid(模型id)</th>
				<th width="10%">cn(属性名称)</th>
				<th width="10%">ty(数据类型)</th>
				<th width="10%">de(默认值)</th>
				<th width="10%">fl(标记(是否是主|空|增))</th>
				<th width="10%">cm(注释)</th>
				<th width="10%">egg(示例数据)</th>
			 
				<th width="12%">操作</th>
			</tr>
			
			<#list tbc_page.getList() as o>
			<tr>
					<td style="text-align:left;">${(o.id)!}</td>
					<td style="text-align:left;">${(o.tbid)!}</td>
					<td style="text-align:left;">${(o.cn)!}</td>
					<td style="text-align:left;">${(o.ty)!}</td>
					<td style="text-align:left;">${(o.de)!}</td>
					<td style="text-align:left;">${(o.fl)!}</td>
					<td style="text-align:left;">${(o.cm)!}</td>
					<td style="text-align:left;">${(o.egg)!}</td>
				<td style="text-align:left;">
					&nbsp;&nbsp;<a href="/tbc/delete/${o.id}" onclick="return confirm('您正在执行删除操作，是否继续？')">删除</a>
					&nbsp;&nbsp;<a href="/tbc/edit/${o.id}">修改</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
	<#include "../common/_paginate.ftl" />
	<@paginate currentPage=tbc_page.pageNumber totalPage=tbc_page.totalPage actionUrl="/tbc/" />
</div>
</@layout>