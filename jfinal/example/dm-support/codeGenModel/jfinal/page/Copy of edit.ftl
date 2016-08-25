<${"#"}include "/page/common/_layout.ftl" />
<${"@"}layout>
<h1><a href="/${root.name}">${root.name}列表</a> ---&gt; 修改
</h1>
<div >
	<form action="/${root.name}/update" method="post">
		<${"#"}include "_form.ftl" />
	</form>
</div>
</${"@"}layout>