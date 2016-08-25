<#include "../common/_layout.ftl" />
<@layout>
<h1>Blog管理 ---&gt; 创建Blog
</h1>
<div>
	<form action="/t_user/save" method="post">
		<#include "_form_t_user.ftl" />
	</form>
</div>
</@layout>