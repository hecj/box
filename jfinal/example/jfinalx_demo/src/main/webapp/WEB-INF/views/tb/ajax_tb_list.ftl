<#include "../common/_layout.ftl"/>
<@layout>
<h3>tb ajax列表
</h3>
	<a href="/tb">返回tb列表</a>
	<a href="/tb/add">新增tb</a>
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
			tbPage(1);
		}
		//ajax分页请求
		function tbPage(pageNumber){
			var data=jQuery(".table_filter_box").serializedom().json;
			data.pageNumber=pageNumber;
			jQuery(".table_box").load( "/tb/ajax_tb_list_page", data );
		}
		
		//ajax删除
		function ajax_del(id){
			var cp=jQuery(".table_box").find(".currentPage").val();
			jQuery.get("/tb/a/del/"+id,function(){
				tbPage(cp);
			});
		}
	</script>

</@layout>


