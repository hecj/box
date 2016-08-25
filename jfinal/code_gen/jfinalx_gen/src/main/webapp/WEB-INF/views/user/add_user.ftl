<#include "../common/_layout.ftl" />
<@layout>
<h1>Blog管理 ---&gt; 创建Blog
</h1>
<div>
	<form action="/user/save" method="post">
		<#include "_form_user.ftl" />
	</form>
</div>
</@layout>