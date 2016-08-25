<#include "../common/_layout.ftl" />
<@layout>
<h1>user管理 ---&gt; 修改user
</h1>
<div class="form_box">
	<form action="/user/update" method="post">
		<#include "_form_user.ftl" />
	</form>
</div>
</@layout>