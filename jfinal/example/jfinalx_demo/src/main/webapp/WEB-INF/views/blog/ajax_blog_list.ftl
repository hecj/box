<#include "../common/_layout.ftl"/>
<@layout>
<h3>Blog ajax列表
</h3>
	<a href="/blog">返回Blog列表</a>
	<a href="/blog/add">新增Blog</a>
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
			blogPage(1);
		}
		//ajax分页请求
		function blogPage(pageNumber){
			var data=jQuery(".table_filter_box").serializedom().json;
			data.pageNumber=pageNumber;
			jQuery(".table_box").load( "/blog/ajax_blog_list_page", data );
		}
		
		//ajax删除
		function ajax_del(id){
			var cp=jQuery(".table_box").find(".currentPage").val();
			jQuery.get("/blog/a/del/"+id,function(){
				blogPage(cp);
			});
		}
	</script>

</@layout>


