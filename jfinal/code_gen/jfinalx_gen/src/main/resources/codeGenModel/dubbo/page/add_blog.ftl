<${"#"}include "../common/_layout.ftl" />
<${"@"}layout>
<h1>Blog管理 ---&gt; 创建Blog
</h1>
<div>
	<form action="/${root.name}/save" method="post">
		<${"#"}include "_form_${root.name}.ftl" />
	</form>
</div>
</${"@"}layout>