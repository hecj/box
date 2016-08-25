<#include "../common/_layout.ftl" />
<@layout>
<h1>Blog管理 ---&gt; 创建Blog
</h1>
<div>
	<form action="/tbc/save" method="post">
		<#include "_form_tbc.ftl" />
	</form>
</div>
</@layout>