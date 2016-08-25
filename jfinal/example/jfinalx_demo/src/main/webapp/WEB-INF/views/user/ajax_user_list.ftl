<#include "../common/_layout.ftl"/>
<@layout>
<h3>user ajax列表
</h3>
	<a href="/user">返回user列表</a>
	<a href="/user/add">新增user</a>
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
			userPage(1);
		}
		//ajax分页请求
		function userPage(pageNumber){
			var data=jQuery(".table_filter_box").serializedom().json;
			data.pageNumber=pageNumber;
			jQuery(".table_box").load( "/user/ajax_user_list_page", data );
		}
		
		//ajax删除
		function ajax_del(id){
			var cp=jQuery(".table_box").find(".currentPage").val();
			jQuery.get("/user/a/del/"+id,function(){
				userPage(cp);
			});
		}
	</script>

</@layout>


