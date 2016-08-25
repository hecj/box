<${"#"}include "../common/_layout.ftl"/>
<${"@"}layout>
<h3>${root.name} ajax列表
</h3>
	<a href="/${root.name}">返回${root.name}列表</a>
	<a href="/${root.name}/add">新增${root.name}</a>
	<div class="table_filter_box">
		id:<input name="id"> &nbsp;
		title:<input name="title"> &nbsp;
		content:<input name="content"> &nbsp;
		<button onclick="search();">检索</button> &nbsp; 
	</div>
	<div class="table_box">
	</div>
	
	<script>
		
		jQuery(function(){
			search();
		});
		//检索
		function search(){
			${root.name}Page(1);
		}
		//ajax分页请求
		function ${root.name}Page(pageNumber){
			var data=jQuery(".table_filter_box").serializedom().json;
			data.pageNumber=pageNumber;
			jQuery(".table_box").load( "/${root.name}/ajax_${root.name}_list_page", data );
		}
		
		//ajax删除
		function ajax_del(id){
			var cp=jQuery(".table_box").find(".currentPage").val();
			jQuery.get("/${root.name}/a/del/"+id,function(){
				${root.name}Page(cp);
			});
		}
	</script>

</${"@"}layout>


