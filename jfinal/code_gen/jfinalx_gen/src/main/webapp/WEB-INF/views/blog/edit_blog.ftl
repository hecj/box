<#include "../common/_layout.ftl" />
<@layout>
<h1>blog管理 ---&gt; 修改blog
</h1>
<div class="form_box">
	<form action="/blog/update" method="post">
		<#include "_form_blog.ftl" />
	</form>
</div>
</@layout>