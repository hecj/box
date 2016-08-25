<#include "../common/_layout.ftl" />
<@layout>
<h1>tb管理 ---&gt; 修改tb
</h1>
<div class="form_box">
	<form action="/tb/update" method="post">
		<#include "_form_tb.ftl" />
	</form>
</div>
</@layout>