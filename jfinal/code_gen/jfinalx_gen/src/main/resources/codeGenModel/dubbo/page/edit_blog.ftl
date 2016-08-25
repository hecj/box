<${"#"}include "../common/_layout.ftl" />
<${"@"}layout>
<h1>${root.name}管理 ---&gt; 修改${root.name}
</h1>
<div class="form_box">
	<form action="/${root.name}/update" method="post">
		<${"#"}include "_form_${root.name}.ftl" />
	</form>
</div>
</${"@"}layout>