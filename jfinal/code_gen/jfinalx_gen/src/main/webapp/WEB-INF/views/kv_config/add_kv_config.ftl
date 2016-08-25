<#include "../common/_layout.ftl" />
<@layout>
<h1>Blog管理 ---&gt; 创建Blog
</h1>
<div>
	<form action="/kv_config/save" method="post">
		<#include "_form_kv_config.ftl" />
	</form>
</div>
</@layout>