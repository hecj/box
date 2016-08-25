<#include "../common/_layout.ftl" />
<@layout>
<h1>t_user管理 ---&gt; 修改t_user
</h1>
<div class="form_box">
	<form action="/t_user/update" method="post">
		<#include "_form_t_user.ftl" />
	</form>
</div>
</@layout>