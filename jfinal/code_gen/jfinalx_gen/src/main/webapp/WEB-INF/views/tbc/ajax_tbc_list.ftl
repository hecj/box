<#include "../common/_layout.ftl"/>
<@layout>
<h3>tbc ajax列表
</h3>
	<a href="/tbc">返回tbc列表</a>
	<a href="/tbc/add">新增tbc</a>
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
			tbcPage(1);
		}
		//ajax分页请求
		function tbcPage(pageNumber){
			var data=jQuery(".table_filter_box").serializedom().json;
			data.pageNumber=pageNumber;
			jQuery(".table_box").load( "/tbc/ajax_tbc_list_page", data );
		}
		
		//ajax删除
		function ajax_del(id){
			var cp=jQuery(".table_box").find(".currentPage").val();
			jQuery.get("/tbc/a/del/"+id,function(){
				tbcPage(cp);
			});
		}
	</script>

</@layout>


