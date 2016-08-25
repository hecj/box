<#include "../common/_layout.ftl"/>
<@layout>
<h3>kv_config ajax列表
</h3>
	<a href="/kv_config">返回kv_config列表</a>
	<a href="/kv_config/add">新增kv_config</a>
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
			kv_configPage(1);
		}
		//ajax分页请求
		function kv_configPage(pageNumber){
			var data=jQuery(".table_filter_box").serializedom().json;
			data.pageNumber=pageNumber;
			jQuery(".table_box").load( "/kv_config/ajax_kv_config_list_page", data );
		}
		
		//ajax删除
		function ajax_del(id){
			var cp=jQuery(".table_box").find(".currentPage").val();
			jQuery.get("/kv_config/a/del/"+id,function(){
				kv_configPage(cp);
			});
		}
	</script>

</@layout>


