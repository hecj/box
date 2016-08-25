<#include "../common/_layout.ftl" />
<@layout>
<h1>kv_config管理 ---&gt; 修改kv_config
</h1>
<div class="form_box">
	<form action="/kv_config/update" method="post">
		<#include "_form_kv_config.ftl" />
	</form>
</div>
</@layout>