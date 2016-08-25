<${"#"}include "/page/common/_layout.ftl" />
<${"@"}layout>
<h1>
	<a href="/${root.name}">${root.name}列表</a> ---&gt; 添加
</h1>
<div class="form_box">
	<form action="/${root.name}/save" method="post">
		<${"#"}include "_form.ftl" />
	</form>
</div>
</${"@"}layout>