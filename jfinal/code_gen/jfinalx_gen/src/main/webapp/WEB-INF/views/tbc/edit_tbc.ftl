<#include "../common/_layout.ftl" />
<@layout>
<h1>tbc管理 ---&gt; 修改tbc
</h1>
<div class="form_box">
	<form action="/tbc/update" method="post">
		<#include "_form_tbc.ftl" />
	</form>
</div>
</@layout>